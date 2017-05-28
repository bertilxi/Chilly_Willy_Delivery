package dam.isi.frsf.utn.edu.ar.delivery.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.*
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.adapter.ContainersAdapter
import dam.isi.frsf.utn.edu.ar.delivery.adapter.SaucesAdapter
import dam.isi.frsf.utn.edu.ar.delivery.adapter.ToppingsAdapter
import dam.isi.frsf.utn.edu.ar.delivery.constants.OrderActivityConstants
import dam.isi.frsf.utn.edu.ar.delivery.model.*
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService
import dam.isi.frsf.utn.edu.ar.delivery.utility.Formatter
import java.text.NumberFormat
import java.util.*

class OrderActivity : AppCompatActivity() {

    internal var orderItems: MutableList<OrderItem>? = null
    internal var flavors: List<Flavor>? = null
    internal var containers: List<ContainerType>? = null
    internal var sauces: MutableList<Sauce>? = null
    internal var toppings: List<Topping>? = null
    internal var listviewToppings: ListView? = null
    internal var listviewSauces: ListView? = null
    internal var listViewContainers: ListView? = null
    internal var listViewItems: ListView? = null
    internal var gridViewFlavors: GridView? = null
    internal var insertPoint: ViewGroup? = null
    internal var contentState = -1
    internal var itemInConstruction: OrderItem? = null
    internal var dataService: DataService? = null
    internal var textViewTotal: TextView? = null
    internal var containerPositionChecked: Int? = null
    internal var flavorPositionsChecked: ArrayList<Boolean>? = null
    internal var toppingsPositionsChecked: ArrayList<Boolean>? = null
    internal var saucePositionChecked: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG)

        setContentView(R.layout.activity_order)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        insertPoint = findViewById(R.id.view_insert_point) as ViewGroup

        dataService = DataService(applicationContext)
        orderItems = ArrayList<OrderItem>()

        processSavedState(savedInstanceState)
        if (contentState == -1) {
            contentState = OrderActivityConstants.CONTENT_CONTAINERS
        }

        when (contentState) {
            OrderActivityConstants.CONTENT_ORDER_ITEMS -> loadOrderItems()
            OrderActivityConstants.CONTENT_CONTAINERS -> loadContainers()
            OrderActivityConstants.CONTENT_FLAVORS -> loadFlavors()
            OrderActivityConstants.CONTENT_ADDINS -> loadAddins()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_order_activity, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        when (contentState) {
            OrderActivityConstants.CONTENT_ORDER_ITEMS -> {
                menu.setGroupVisible(R.id.group_containers_menu, false)
                menu.setGroupVisible(R.id.group_flavors_menu, false)
                menu.setGroupVisible(R.id.group_addins_menu, false)
                menu.setGroupVisible(R.id.group_order_item_menu, true)
                return true
            }
            OrderActivityConstants.CONTENT_FLAVORS -> {
                menu.setGroupVisible(R.id.group_order_item_menu, false)
                menu.setGroupVisible(R.id.group_containers_menu, false)
                menu.setGroupVisible(R.id.group_addins_menu, false)
                menu.setGroupVisible(R.id.group_flavors_menu, true)
                return true
            }
            OrderActivityConstants.CONTENT_CONTAINERS -> {
                menu.setGroupVisible(R.id.group_order_item_menu, false)
                menu.setGroupVisible(R.id.group_flavors_menu, false)
                menu.setGroupVisible(R.id.group_addins_menu, false)
                menu.setGroupVisible(R.id.group_containers_menu, true)
                return true
            }
            OrderActivityConstants.CONTENT_ADDINS -> {
                menu.setGroupVisible(R.id.group_order_item_menu, false)
                menu.setGroupVisible(R.id.group_containers_menu, false)
                menu.setGroupVisible(R.id.group_flavors_menu, false)
                menu.setGroupVisible(R.id.group_addins_menu, true)
                return true
            }
            else -> return true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.moar_icecream -> {
                loadContainers()
                return true
            }

            R.id.container_ready -> {
                if (listViewContainers!!.adapter == null) {
                    return true
                }
                val posContainer = listViewContainers?.checkedItemPosition
                if (posContainer == -1) {
                    Toast.makeText(this@OrderActivity, "Selecciona un recipiente", Toast.LENGTH_SHORT).show()
                    return true
                }
                val selectedContainer = listViewContainers?.adapter?.getItem(posContainer!!) as ContainerType
                itemInConstruction = OrderItem()
                itemInConstruction!!.containerType = selectedContainer
                loadFlavors()
                return true
            }

            R.id.flavors_ready -> {
                val len = gridViewFlavors?.count
                val checkedFlavor = gridViewFlavors?.checkedItemPositions
                val selectedFlavors = ArrayList<Flavor>()
                var selectedFlavorsQuantity = 0
                for (i in 0..len?.minus(1)!!) {
                    if (checkedFlavor!!.get(i)) {
                        selectedFlavors.add(flavors!![i])
                        selectedFlavorsQuantity++
                    }
                }
                if (selectedFlavorsQuantity == 0) {
                    Toast.makeText(this@OrderActivity,
                            "Elegí un sabor antes de continuar",
                            Toast.LENGTH_SHORT)
                            .show()
                    return true
                }
                if (selectedFlavorsQuantity > itemInConstruction!!.containerType?.maxFlavors!!) {
                    Toast.makeText(this@OrderActivity,
                            "Solo podés elegir " + itemInConstruction!!.containerType?.maxFlavors!!,
                            Toast.LENGTH_SHORT)
                            .show()
                    return true
                }
                if (selectedFlavorsQuantity < itemInConstruction!!.containerType!!.maxFlavors!! && itemInConstruction?.containerType?.variableQuantityOfFlavors == false) {
                    Toast.makeText(this@OrderActivity,
                            "Debés elegir " + itemInConstruction!!.containerType?.maxFlavors + " sabores",
                            Toast.LENGTH_SHORT)
                            .show()
                    return true
                }
                itemInConstruction!!.flavors = selectedFlavors
                loadAddins()
                return true
            }

            R.id.addins_ready -> {
                if (listviewSauces?.adapter == null) {
                    return true
                }
                val posSauce = listviewSauces?.checkedItemPosition
                val selectedSauce = listviewSauces?.adapter?.getItem(posSauce!!) as Sauce
                val checkedTopping = listviewToppings?.checkedItemPositions
                val selectedToppings = (0..listviewToppings?.count!!.minus(1))
                        .filter { checkedTopping!!.get(it) }
                        .mapTo(ArrayList<Topping>()) { toppings!![it] }
                itemInConstruction!!.sauce = selectedSauce
                itemInConstruction!!.toppings = selectedToppings
                orderItems?.add(itemInConstruction!!)
                itemInConstruction = null
                loadOrderItems()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        when (contentState) {
            OrderActivityConstants.CONTENT_ORDER_ITEMS -> super.onBackPressed()
            OrderActivityConstants.CONTENT_CONTAINERS -> if (orderItems!!.size > 0) {
                loadOrderItems()
            } else {
                super.onBackPressed()
            }
            OrderActivityConstants.CONTENT_FLAVORS -> loadContainers()
            OrderActivityConstants.CONTENT_ADDINS -> loadFlavors()
        }
    }

    private fun loadOrderItems() {
        contentState = OrderActivityConstants.CONTENT_ORDER_ITEMS
        val inflater = layoutInflater
        val orderItemsView = inflater.inflate(R.layout.listview_order_item, null)
        insertPoint?.removeAllViews()
        insertPoint?.addView(orderItemsView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        listViewItems = orderItemsView.findViewById(R.id.listview_order_item) as ListView
        listViewItems?.adapter = OrderAdapter(orderItems!!)

        supportActionBar!!.title = "TU PEDIDO"
        invalidateOptionsMenu()

        textViewTotal = orderItemsView.findViewById(R.id.total) as TextView
        calculateTotal()

        val orderReadyButton = findViewById(R.id.order_ready_button) as Button
        orderReadyButton.setOnClickListener {
            val orden = Order().withItems(orderItems!!)
            val intent = Intent(this@OrderActivity, OrderSendActivity::class.java)
            intent.putExtra(getString(R.string.order_key), orden)
            startActivityForResult(intent, 1)
        }
    }

    private fun loadContainers() {
        contentState = OrderActivityConstants.CONTENT_CONTAINERS
        val inflater = layoutInflater
        val containersView = inflater.inflate(R.layout.listview_containers, null)
        insertPoint?.removeAllViews()
        insertPoint?.addView(containersView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        listViewContainers = containersView.findViewById(R.id.listview_containers) as ListView
        fillListViewContainers()
        supportActionBar!!.title = "ELEGÍ TU FAVORITO"
        invalidateOptionsMenu()
    }

    private fun fillListViewContainers() {
        if (containers != null) {
            val containersAdapter = ContainersAdapter(applicationContext, containers!!)
            listViewContainers?.adapter = containersAdapter
            return
        }
        try {
            dataService?.containers?.setCallback(FutureCallback<List<ContainerType>> { _, result ->
                if (result == null) {
                    Log.d("MIRAME", "onCompleted: RESULTADO GET CONTENEDORES VACIO")
                    fillListViewContainers()
                    return@FutureCallback
                }
                containers = result
                val containersAdapter = ContainersAdapter(applicationContext, containers!!)
                listViewContainers?.adapter = containersAdapter
                if (containerPositionChecked != null) {
                    listViewContainers?.setItemChecked(containerPositionChecked!!, true)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun loadFlavors() {
        contentState = OrderActivityConstants.CONTENT_FLAVORS
        val inflater = layoutInflater
        gridViewFlavors = inflater.inflate(R.layout.gridview_flavor_choice, null) as GridView
        insertPoint?.removeAllViews()
        insertPoint?.addView(gridViewFlavors, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        gridViewFlavors?.onItemClickListener = AdapterView.OnItemClickListener { _, view, i, _ ->
            val checkedIcon = view.findViewById(R.id.flavor_checked) as ImageView
            if (gridViewFlavors!!.isItemChecked(i)) {
                checkedIcon.visibility = View.VISIBLE
            } else {
                checkedIcon.visibility = View.INVISIBLE
            }
        }

        val dataService = DataService(applicationContext)
        if (flavors != null) {
            val flavorsAdapter = FlavorsAdapter(applicationContext, flavors!!)
            gridViewFlavors?.adapter = flavorsAdapter
        } else {
            try {
                dataService.flavors.setCallback { _, result ->
                    flavors = result
                    val flavorsAdapter = FlavorsAdapter(applicationContext, flavors!!)
                    gridViewFlavors?.adapter = flavorsAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        supportActionBar!!.title = "ELEGÍ LOS SABORES"
        invalidateOptionsMenu()
    }

    private fun loadAddins() {
        contentState = OrderActivityConstants.CONTENT_ADDINS
        val inflater = layoutInflater
        val addinsView = inflater.inflate(R.layout.listview_addins, null)
        insertPoint?.removeAllViews()
        insertPoint?.addView(addinsView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        listviewSauces = addinsView.findViewById(R.id.listview_sauces) as ListView
        listviewToppings = addinsView.findViewById(R.id.listview_toppings) as ListView

        val dataService = DataService(applicationContext)

        if (sauces != null) {
            val saucesAdapter = SaucesAdapter(applicationContext, sauces!!)
            listviewSauces!!.adapter = saucesAdapter
            listviewSauces!!.setItemChecked(0, true)
        } else {
            try {
                dataService.sauces.setCallback { _, result ->
                    sauces = ArrayList<Sauce>()
                    sauces!!.add(Sauce().withImgURL("").withLabel(getString(R.string.no_sauce_label)))
                    sauces!!.addAll(result)
                    val saucesAdapter = SaucesAdapter(applicationContext, sauces!!)
                    listviewSauces?.adapter = saucesAdapter
                    listviewSauces?.setItemChecked(0, true)
                    if (saucePositionChecked != null) {
                        listviewSauces?.setItemChecked(saucePositionChecked!!, true)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        if (toppings != null) {
            val toppingsAdapter = ToppingsAdapter(applicationContext, toppings!!)
            listviewToppings?.adapter = toppingsAdapter
        } else {
            try {
                dataService.toppings.setCallback { _, result ->
                    Log.d("MIRAME", "onCompleted: result está vacío? " + if (result == null) "SI" else "NO")
                    toppings = result
                    val toppingsAdapter = ToppingsAdapter(applicationContext, toppings!!)
                    listviewToppings?.adapter = toppingsAdapter
                    if (toppingsPositionsChecked != null) {
                        for (i in toppings!!.indices) {
                            listviewToppings?.setItemChecked(i, toppingsPositionsChecked!![i])
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        listviewToppings?.onItemClickListener = AdapterView.OnItemClickListener { _, view, i, _ ->
            if (listviewToppings?.isItemChecked(i)!!) {
                view.setBackgroundColor(ContextCompat.getColor(this@OrderActivity, R.color.pressed_color))
            } else {
                view.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        supportActionBar!!.title = "MEJORÁ TU HELADO"
        invalidateOptionsMenu()
    }

    internal inner class FlavorsAdapter(context: Context, flavors: List<Flavor>) : ArrayAdapter<Flavor>(context, R.layout.gridview_cell_flavor_choice, flavors) {

        var inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var cell = convertView
            if (cell == null) {
                cell = inflater.inflate(R.layout.gridview_cell_flavor_choice, parent, false)
            }
            var holder: FlavorHolder? = cell!!.tag as FlavorHolder?
            if (holder == null) {
                holder = FlavorHolder(cell)
                cell.tag = holder
            }

            if (flavorPositionsChecked != null && flavorPositionsChecked!![position]) {
                holder.checkedIcon!!.visibility = View.VISIBLE
            }

            Ion.with(holder.flavorPic!!)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position)!!.completeImgURL)
            holder.textViewName!!.text = this.getItem(position)!!.label

            return cell
        }

        internal inner class FlavorHolder(cell: View) {
            var flavorPic: ImageView? = null
            var textViewName: TextView? = null
            var checkedIcon: ImageView? = null

            init {
                this.flavorPic = cell.findViewById(R.id.flavor_picture) as ImageView
                this.textViewName = cell.findViewById(R.id.flavor_name) as TextView
                this.checkedIcon = cell.findViewById(R.id.flavor_checked) as ImageView
            }
        }
    }

    internal inner class OrderAdapter(orderItems: List<OrderItem>) : ArrayAdapter<OrderItem>(this@OrderActivity, R.layout.listview_row_order_item, orderItems) {

        var inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            var row = convertView
            if (row == null) {
                row = inflater.inflate(R.layout.listview_row_order_item, parent, false)
            }
            var holder: OrderHolder? = row!!.tag as OrderHolder?
            if (holder == null) {
                holder = OrderHolder(row)
                row.tag = holder
                holder.numberPickerQuantity!!.minValue = 1
                holder.numberPickerQuantity!!.maxValue = 10

                holder.numberPickerQuantity!!.setOnValueChangedListener { picker, _, newVal ->
                    val myPosition = picker.tag as Int
                    getItem(myPosition)!!.quantity = newVal
                    calculateTotal()
                }
            }

            Ion.with(holder.containerPic!!)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position)!!.containerType?.completeImgURL)
            holder.textViewContainer!!.text = this.getItem(position)!!.containerType?.label
            holder.textViewFlavors!!.text = Formatter.buildStringFromList(this.getItem(position)!!.flavors)
            val addins = ArrayList<Addin>()
            addins.addAll(this.getItem(position).toppings!!)
            if (this.getItem(position)!!.sauce?.label != getString(R.string.no_sauce_label)) {
                addins.add(this.getItem(position).sauce!!)
            }
            holder.textViewAddins!!.text = Formatter.buildStringFromList(addins)
            holder.numberPickerQuantity!!.value = this.getItem(position)!!.quantity!!
            holder.numberPickerQuantity!!.tag = position

            return row
        }

        internal inner class OrderHolder(row: View) {
            var containerPic: ImageView? = null
            var textViewContainer: TextView? = null
            var textViewFlavors: TextView? = null
            var textViewAddins: TextView? = null
            var numberPickerQuantity: NumberPicker? = null

            init {
                this.containerPic = row.findViewById(R.id.imageview_order_item_picture) as ImageView
                this.textViewContainer = row.findViewById(R.id.textview_order_item_container) as TextView
                this.textViewFlavors = row.findViewById(R.id.textview_order_item_flavors) as TextView
                this.textViewAddins = row.findViewById(R.id.textview_order_item_addins) as TextView
                this.numberPickerQuantity = row.findViewById(R.id.item_quantity) as NumberPicker
            }
        }
    }

    private fun calculateTotal() {
        val totalCents = orderItems!!.sumBy { it.containerType?.priceInCents?.times(it.quantity!!)!! }
        textViewTotal?.text = NumberFormat.getCurrencyInstance(Locale.US).format(totalCents * 0.01)
    }

    private fun processSavedState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            return
        }
        if (savedInstanceState.containsKey(getString(R.string.order_content_key))) {
            contentState = savedInstanceState.get(getString(R.string.order_content_key)) as Int
            when (contentState) {
                OrderActivityConstants.CONTENT_CONTAINERS -> containerPositionChecked = savedInstanceState.getInt(getString(R.string.container_selection_key))
                OrderActivityConstants.CONTENT_FLAVORS -> flavorPositionsChecked = savedInstanceState.getSerializable(getString(R.string.flavors_selection_key)) as ArrayList<Boolean>
                OrderActivityConstants.CONTENT_ADDINS -> {
                    toppingsPositionsChecked = savedInstanceState.getSerializable(getString(R.string.toppings_selection_key)) as ArrayList<Boolean>
                    saucePositionChecked = savedInstanceState.getInt(getString(R.string.sauce_selection_key))
                }
            }
        }
        if (savedInstanceState.containsKey(getString(R.string.item_in_construction_key))) {
            itemInConstruction = savedInstanceState.get(getString(R.string.item_in_construction_key)) as OrderItem
        }
        if (savedInstanceState.containsKey(getString(R.string.order_items_key))) {
            orderItems = savedInstanceState.get(getString(R.string.order_items_key)) as ArrayList<OrderItem>
        }
        if (savedInstanceState.containsKey(getString(R.string.containers_key))) {
            containers = savedInstanceState.get(getString(R.string.containers_key)) as ArrayList<ContainerType>
        }
        if (savedInstanceState.containsKey(getString(R.string.flavors_key))) {
            flavors = savedInstanceState.get(getString(R.string.flavors_key)) as ArrayList<Flavor>
        }
        if (savedInstanceState.containsKey(getString(R.string.sauces_key))) {
            sauces = savedInstanceState.get(getString(R.string.sauces_key)) as ArrayList<Sauce>
        }
        if (savedInstanceState.containsKey(getString(R.string.toppings_key))) {
            toppings = savedInstanceState.get(getString(R.string.toppings_key)) as ArrayList<Topping>
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(getString(R.string.order_content_key), contentState)
        outState.putSerializable(getString(R.string.item_in_construction_key), itemInConstruction)
        outState.putSerializable(getString(R.string.order_items_key), orderItems as ArrayList<OrderItem>)
        outState.putSerializable(getString(R.string.containers_key), containers as ArrayList<ContainerType>?)
        outState.putSerializable(getString(R.string.flavors_key), flavors as ArrayList<Flavor>?)
        outState.putSerializable(getString(R.string.sauces_key), sauces as ArrayList<Sauce>?)
        outState.putSerializable(getString(R.string.toppings_key), toppings as ArrayList<Topping>?)
        when (contentState) {
            OrderActivityConstants.CONTENT_CONTAINERS -> outState.putInt(getString(R.string.container_selection_key), listViewContainers!!.checkedItemPosition)
            OrderActivityConstants.CONTENT_FLAVORS -> {

                val FlaCheckedSparce = gridViewFlavors?.checkedItemPositions
                val FlaChecked = (0..gridViewFlavors?.count?.minus(1)!!).mapTo(ArrayList<Boolean>()) { FlaCheckedSparce!!.get(it) }
                outState.putSerializable(getString(R.string.flavors_selection_key), FlaChecked)
            }
            OrderActivityConstants.CONTENT_ADDINS -> {
                val checkedSparse = listviewToppings?.checkedItemPositions
                val checked = (0..listviewToppings?.count?.minus(1)!!).mapTo(ArrayList<Boolean>()) { checkedSparse!!.get(it) }
                outState.putSerializable(getString(R.string.toppings_selection_key), checked)
                outState.putInt(getString(R.string.sauce_selection_key), listviewSauces!!.checkedItemPosition)
            }
        }
    }
}
