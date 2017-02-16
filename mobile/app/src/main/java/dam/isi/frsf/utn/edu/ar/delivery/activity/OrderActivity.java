package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.constants.OrderActivityConstants;
import dam.isi.frsf.utn.edu.ar.delivery.model.Addin;
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerType;
import dam.isi.frsf.utn.edu.ar.delivery.model.Flavor;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.model.OrderItem;
import dam.isi.frsf.utn.edu.ar.delivery.model.Sauce;
import dam.isi.frsf.utn.edu.ar.delivery.model.Topping;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;
import dam.isi.frsf.utn.edu.ar.delivery.utility.Formatter;

public class OrderActivity extends AppCompatActivity {

    List<OrderItem> orderItems;
    List<Flavor> flavors;
    List<ContainerType> containers;
    List<Sauce> sauces;
    List<Topping> toppings;
    ListView listViewItems;
    ListView listviewToppings;
    ListView listviewSauces;
    ListView listViewContainers;
    GridView gridViewFlavors;
    ViewGroup insertPoint;
    int contentState = -1;
    OrderItem itemInConstruction;
    DataService dataService;
    TextView textViewTotal;
    Integer containerPositionChecked = null;
    ArrayList<Boolean> flavorPositionsChecked = null;
    ArrayList<Boolean> toppingsPositionsChecked = null;
    Integer saucePositionChecked = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);

        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        insertPoint = (ViewGroup) findViewById(R.id.view_insert_point);

        dataService = new DataService(getApplicationContext());
        orderItems = new ArrayList<>();

        processSavedState(savedInstanceState);
        if(contentState == -1) {
            contentState = OrderActivityConstants.CONTENT_CONTAINERS;
        }

        switch (contentState) {
            case OrderActivityConstants.CONTENT_ORDER_ITEMS:
                loadOrderItems();
                break;
            case OrderActivityConstants.CONTENT_CONTAINERS:
                loadContainers();
                break;
            case OrderActivityConstants.CONTENT_FLAVORS:
                loadFlavors();
                break;
            case OrderActivityConstants.CONTENT_ADDINS:
                loadAddins();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (contentState){
            case OrderActivityConstants.CONTENT_ORDER_ITEMS:
                menu.setGroupVisible(R.id.group_containers_menu, false);
                menu.setGroupVisible(R.id.group_flavors_menu, false);
                menu.setGroupVisible(R.id.group_addins_menu, false);
                menu.setGroupVisible(R.id.group_order_item_menu, true);
                return true;
            case OrderActivityConstants.CONTENT_FLAVORS:
                menu.setGroupVisible(R.id.group_order_item_menu, false);
                menu.setGroupVisible(R.id.group_containers_menu, false);
                menu.setGroupVisible(R.id.group_addins_menu, false);
                menu.setGroupVisible(R.id.group_flavors_menu, true);
                return true;
            case OrderActivityConstants.CONTENT_CONTAINERS:
                menu.setGroupVisible(R.id.group_order_item_menu, false);
                menu.setGroupVisible(R.id.group_flavors_menu, false);
                menu.setGroupVisible(R.id.group_addins_menu, false);
                menu.setGroupVisible(R.id.group_containers_menu, true);
                return true;
            case OrderActivityConstants.CONTENT_ADDINS:
                menu.setGroupVisible(R.id.group_order_item_menu, false);
                menu.setGroupVisible(R.id.group_containers_menu, false);
                menu.setGroupVisible(R.id.group_flavors_menu, false);
                menu.setGroupVisible(R.id.group_addins_menu, true);
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moar_icecream:
                loadContainers();
                return true;

            case R.id.container_ready:
                if(listViewContainers.getAdapter() == null){
                    return true;
                }
                int posContainer = listViewContainers.getCheckedItemPosition();
                if(posContainer == -1) {
                    Toast.makeText(OrderActivity.this, "Selecciona un recipiente", Toast.LENGTH_SHORT).show();
                    return true;
                }
                ContainerType selectedContainer = (ContainerType) listViewContainers.getAdapter().getItem(posContainer);
                itemInConstruction = new OrderItem();
                itemInConstruction.setContainerType(selectedContainer);
                loadFlavors();
                return true;

            case R.id.flavors_ready:
                int len = gridViewFlavors.getCount();
                SparseBooleanArray checkedFlavor = gridViewFlavors.getCheckedItemPositions();
                List<Flavor> selectedFlavors = new ArrayList<>();
                int selectedFlavorsQuantity = 0;
                for (int i = 0; i < len; i++) {
                    if (checkedFlavor.get(i)) {
                        selectedFlavors.add(flavors.get(i));
                        selectedFlavorsQuantity++;
                    }
                }
                if(selectedFlavorsQuantity == 0) {
                    Toast.makeText(OrderActivity.this,
                            "Elegí un sabor antes de continuar",
                            Toast.LENGTH_SHORT)
                            .show();
                    return true;
                }
                if (selectedFlavorsQuantity > itemInConstruction.getContainerType().getMaxFlavors()) {
                    Toast.makeText(OrderActivity.this,
                            "Solo podés elegir "+ itemInConstruction.getContainerType().getMaxFlavors(),
                            Toast.LENGTH_SHORT)
                            .show();
                    return true;
                }
                if (selectedFlavorsQuantity < itemInConstruction.getContainerType().getMaxFlavors() &&
                        itemInConstruction.getContainerType().getVariableQuantityOfFlavors() == false) {
                    Toast.makeText(OrderActivity.this,
                            "Debés elegir "+itemInConstruction.getContainerType().getMaxFlavors()+" sabores",
                            Toast.LENGTH_SHORT)
                            .show();
                    return true;
                }
                itemInConstruction.setFlavors(selectedFlavors);
                loadAddins();
                return true;

            case R.id.addins_ready:
                if(listviewSauces.getAdapter() == null){
                    return true;
                }
                int posSauce = listviewSauces.getCheckedItemPosition();
                Sauce selectedSauce = (Sauce) listviewSauces.getAdapter().getItem(posSauce);
                List<Topping> selectedToppings = new ArrayList<>();
                SparseBooleanArray checkedTopping = listviewToppings.getCheckedItemPositions();
                int selectedToppingsQuantity = 0;
                for (int i = 0; i < listviewToppings.getCount(); i++) {
                    if (checkedTopping.get(i)) {
                        selectedToppings.add(toppings.get(i));
                        selectedToppingsQuantity++;
                    }
                }
                itemInConstruction.setSauce(selectedSauce);
                itemInConstruction.setToppings(selectedToppings);
                orderItems.add(itemInConstruction);
                itemInConstruction = null;
                loadOrderItems();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        switch (contentState){
            case OrderActivityConstants.CONTENT_ORDER_ITEMS:
                super.onBackPressed();
                break;
            case OrderActivityConstants.CONTENT_CONTAINERS:
                if(orderItems.size() > 0) {
                    loadOrderItems();
                } else {
                    super.onBackPressed();
                }
                break;
            case OrderActivityConstants.CONTENT_FLAVORS:
                loadContainers();
                break;
            case OrderActivityConstants.CONTENT_ADDINS:
                loadFlavors();
                break;
        }
    }

    private void loadOrderItems() {
        contentState = OrderActivityConstants.CONTENT_ORDER_ITEMS;
        LayoutInflater inflater = getLayoutInflater();
        View orderItemsView = inflater.inflate(R.layout.listview_order_item, null);
        insertPoint.removeAllViews();
        insertPoint.addView(orderItemsView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        listViewItems = (ListView) orderItemsView.findViewById(R.id.listview_order_item);
        listViewItems.setAdapter(new OrderAdapter(orderItems));

        getSupportActionBar().setTitle("TU PEDIDO");
        invalidateOptionsMenu();

        textViewTotal = (TextView) orderItemsView.findViewById(R.id.total);
        calculateTotal();

        Button orderReadyButton = (Button) findViewById(R.id.order_ready_button);
        orderReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order orden = new Order().withItems(orderItems);
                Intent intent = new Intent(OrderActivity.this, OrderSendActivity.class);
                intent.putExtra(getString(R.string.order_key),orden);
                startActivityForResult(intent,1);

            }
        });
    }

    private void loadContainers() {
        contentState = OrderActivityConstants.CONTENT_CONTAINERS;
        LayoutInflater inflater = getLayoutInflater();
        View containersView = inflater.inflate(R.layout.listview_containers, null);
        insertPoint.removeAllViews();
        insertPoint.addView(containersView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listViewContainers = (ListView) containersView.findViewById(R.id.listview_containers);
        fillListViewContainers();
        getSupportActionBar().setTitle("ELEGÍ TU FAVORITO");
        invalidateOptionsMenu();
    }

    private void fillListViewContainers() {
        if(containers != null){
            ContainersAdapter containersAdapter = new ContainersAdapter(containers);
            listViewContainers.setAdapter(containersAdapter);
            return;
        }
        try {
            dataService.getContainers().setCallback(new FutureCallback<List<ContainerType>>() {
                @Override
                public void onCompleted(Exception e, List<ContainerType> result) {
                    if(result == null){
                        Log.d("MIRAME", "onCompleted: RESULTADO GET CONTENEDORES VACIO");
                        fillListViewContainers();
                        return;
                    }
                    containers = result;
                    ContainersAdapter containersAdapter = new ContainersAdapter(containers);
                    listViewContainers.setAdapter(containersAdapter);
                    if(containerPositionChecked != null) {
                        listViewContainers.setItemChecked(containerPositionChecked,true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFlavors() {
        contentState = OrderActivityConstants.CONTENT_FLAVORS;
        LayoutInflater inflater = getLayoutInflater();
        gridViewFlavors = (GridView) inflater.inflate(R.layout.gridview_flavor_choice,null);
        insertPoint.removeAllViews();
        insertPoint.addView(gridViewFlavors, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        gridViewFlavors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView checkedIcon = (ImageView) view.findViewById(R.id.flavor_checked);
                if(gridViewFlavors.isItemChecked(i)) {
                    checkedIcon.setVisibility(View.VISIBLE);
                } else {
                    checkedIcon.setVisibility(View.INVISIBLE);
                }
            }
        });

        DataService dataService = new DataService(getApplicationContext());
        if(flavors != null){
            FlavorsAdapter flavorsAdapter = new FlavorsAdapter(flavors);
            gridViewFlavors.setAdapter(flavorsAdapter);
        } else {
            try {
                dataService.getFlavors().setCallback(new FutureCallback<List<Flavor>>() {
                    @Override
                    public void onCompleted(Exception e, List<Flavor> result) {
                        flavors = result;
                        FlavorsAdapter flavorsAdapter = new FlavorsAdapter(flavors);
                        gridViewFlavors.setAdapter(flavorsAdapter);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getSupportActionBar().setTitle("ELEGÍ LOS SABORES");
        invalidateOptionsMenu();
    }

    private void loadAddins() {
        contentState = OrderActivityConstants.CONTENT_ADDINS;
        LayoutInflater inflater = getLayoutInflater();
        View addinsView = inflater.inflate(R.layout.listview_addins, null);
        insertPoint.removeAllViews();
        insertPoint.addView(addinsView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listviewSauces = (ListView) addinsView.findViewById(R.id.listview_sauces);
        listviewToppings = (ListView) addinsView.findViewById(R.id.listview_toppings);

        DataService dataService = new DataService(getApplicationContext());

        if(sauces != null){
            SaucesAdapter saucesAdapter = new SaucesAdapter(sauces);
            listviewSauces.setAdapter(saucesAdapter);
            listviewSauces.setItemChecked(0,true);
        } else {
            try {
                dataService.getSauces().setCallback(new FutureCallback<List<Sauce>>() {
                    @Override
                    public void onCompleted(Exception e, List<Sauce> result) {
                        sauces = new ArrayList<>();
                        sauces.add(new Sauce().withImgURL("").withLabel(getString(R.string.no_sauce_label)));
                        sauces.addAll(result);
                        SaucesAdapter saucesAdapter = new SaucesAdapter(sauces);
                        listviewSauces.setAdapter(saucesAdapter);
                        listviewSauces.setItemChecked(0,true);
                        if(saucePositionChecked != null) {
                            listviewSauces.setItemChecked(saucePositionChecked, true);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (toppings != null){
            ToppingsAdapter toppingsAdapter = new ToppingsAdapter(toppings);
            listviewToppings.setAdapter(toppingsAdapter);
        } else {
            try {
                dataService.getToppings().setCallback(new FutureCallback<List<Topping>>() {
                    @Override
                    public void onCompleted(Exception e, List<Topping> result) {
                        Log.d("MIRAME", "onCompleted: result está vacío? "+(result==null?"SI":"NO"));
                        toppings = result;
                        ToppingsAdapter toppingsAdapter = new ToppingsAdapter(toppings);
                        listviewToppings.setAdapter(toppingsAdapter);
                        if(toppingsPositionsChecked != null) {
                            for(int i = 0; i < toppings.size(); i++) {
                                listviewToppings.setItemChecked(i, toppingsPositionsChecked.get(i));
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        listviewToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(listviewToppings.isItemChecked(i)) {
                    view.setBackgroundColor(ContextCompat.getColor(OrderActivity.this, R.color.pressed_color));
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        getSupportActionBar().setTitle("MEJORÁ TU HELADO");
        invalidateOptionsMenu();
    }

    class ToppingsAdapter extends ArrayAdapter<Topping> {
        LayoutInflater inflater;

        ToppingsAdapter(List<Topping> toppings) {
            super(OrderActivity.this, R.layout.listview_addins_row, toppings);
            inflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if(row == null) {
                row = inflater.inflate(R.layout.listview_addins_row, parent, false);
            }
            ToppingHolder holder = (ToppingHolder) row.getTag();
            if(holder == null) {
                holder = new ToppingHolder(row);
                row.setTag(holder);
            }
            Ion.with(holder.toppingPic)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position).getCompleteImgURL());
            holder.textViewName.setText(this.getItem(position).getLabel());

            return row;
        }

        class ToppingHolder {
            TextView textViewName = null;
            ImageView toppingPic = null;
            ToppingHolder(View row) {
                textViewName = (TextView) row.findViewById(R.id.addin_name);
                toppingPic = (ImageView) row.findViewById(R.id.imageview_addin);
            }
        }
    }

    class SaucesAdapter extends ArrayAdapter<Sauce> {
        LayoutInflater inflater;

        SaucesAdapter(List<Sauce> sauces) {
            super(OrderActivity.this, R.layout.listview_addins_row, sauces);
            inflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if(row == null) {
                row = inflater.inflate(R.layout.listview_addins_row, parent, false);
            }
            SauceHolder holder = (SauceHolder) row.getTag();
            if(holder == null) {
                holder = new SauceHolder(row);
                row.setTag(holder);
            }
            if(this.getItem(position).getLabel() != getString(R.string.no_sauce_label)){
                Ion.with(holder.saucePic)
                        .fitCenter()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .load(this.getItem(position).getCompleteImgURL());
            }
            holder.textViewName.setText(this.getItem(position).getLabel());

            return row;
        }

        class SauceHolder {
            TextView textViewName = null;
            ImageView saucePic = null;
            SauceHolder(View row) {
                textViewName = (TextView) row.findViewById(R.id.addin_name);
                saucePic = (ImageView) row.findViewById(R.id.imageview_addin);
            }
        }
    }
    
    class ContainersAdapter extends ArrayAdapter<ContainerType> {
        LayoutInflater inflater;

        ContainersAdapter(List<ContainerType> containers) {
            super(OrderActivity.this, R.layout.listview_containers_row, containers);
            inflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if(row == null) {
                row = inflater.inflate(R.layout.listview_containers_row, parent, false);
            }
            ContainerHolder holder = (ContainerHolder) row.getTag();

            if(holder == null) {
                holder = new ContainerHolder(row);
                row.setTag(holder);
            }

            Ion.with(holder.containerPic)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position).getCompleteImgURL());
            holder.containerName.setText(this.getItem(position).getLabel());
            holder.containerPrice.setText(NumberFormat.getCurrencyInstance(Locale.US).format(
                    this.getItem(position).getPriceInCents()*0.01
            ));

            return row;
        }

        class ContainerHolder {
            ImageView containerPic = null;
            TextView containerName = null;
            TextView containerPrice = null;
            ContainerHolder(View row){
                containerPic = (ImageView) row.findViewById(R.id.imageview_addin);
                containerName = (TextView) row.findViewById(R.id.containerName);
                containerPrice = (TextView) row.findViewById(R.id.container_price);
            }
        }
    }

    class FlavorsAdapter extends ArrayAdapter<Flavor> {
        LayoutInflater inflater;

        FlavorsAdapter(List<Flavor> flavors) {
            super(OrderActivity.this, R.layout.gridview_cell_flavor_choice, flavors);
            inflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View cell = convertView;
            if (cell == null) {
                cell = inflater.inflate(R.layout.gridview_cell_flavor_choice, parent, false);
            }
            FlavorHolder holder = (FlavorHolder) cell.getTag();
            if (holder == null) {
                holder = new FlavorHolder(cell);
                cell.setTag(holder);
            }

            if(flavorPositionsChecked != null && flavorPositionsChecked.get(position)) {
                holder.checkedIcon.setVisibility(View.VISIBLE);
            }

            Ion.with(holder.flavorPic)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position).getCompleteImgURL());
            holder.textViewName.setText(this.getItem(position).getLabel());

            return (cell);
        }
    }

    class FlavorHolder {
        ImageView flavorPic = null;
        TextView textViewName = null;
        ImageView checkedIcon = null;

        FlavorHolder(View cell) {
            this.flavorPic = (ImageView) cell.findViewById(R.id.flavor_picture);
            this.textViewName = (TextView) cell.findViewById(R.id.flavor_name);
            this.checkedIcon = (ImageView) cell.findViewById(R.id.flavor_checked);
        }
    }

    class OrderAdapter extends ArrayAdapter<OrderItem> {

        LayoutInflater inflater;

        OrderAdapter(List<OrderItem> orderItems) {
            super(OrderActivity.this, R.layout.listview_row_order_item, orderItems);
            inflater = LayoutInflater.from(getContext());
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            View row = convertView;
            if (row == null) {
                row = inflater.inflate(R.layout.listview_row_order_item, parent, false);
            }
            OrderHolder holder = (OrderHolder) row.getTag();
            if (holder == null) {
                holder = new OrderHolder(row);
                row.setTag(holder);
                holder.numberPickerQuantity.setMinValue(1);
                holder.numberPickerQuantity.setMaxValue(10);

                holder.numberPickerQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        int myPosition = (int) picker.getTag();
                        getItem(myPosition).setQuantity(newVal);
                        calculateTotal();
                    }
                });
            }

            Ion.with(holder.containerPic)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position).getContainerType().getCompleteImgURL());
            holder.textViewContainer.setText(this.getItem(position).getContainerType().getLabel());
            holder.textViewFlavors.setText(Formatter.buildStringFromList(this.getItem(position).getFlavors()));
            List<Addin> addins = new ArrayList<Addin>();
            addins.addAll(this.getItem(position).getToppings());
            if(this.getItem(position).getSauce().getLabel() != getString(R.string.no_sauce_label)) {
                addins.add(this.getItem(position).getSauce());
            }
            holder.textViewAddins.setText(Formatter.buildStringFromList(addins));
            holder.numberPickerQuantity.setValue(this.getItem(position).getQuantity());
            holder.numberPickerQuantity.setTag(position);

            return (row);
        }

        class OrderHolder {
            ImageView containerPic = null;
            TextView textViewContainer = null;
            TextView textViewFlavors = null;
            TextView textViewAddins = null;
            NumberPicker numberPickerQuantity = null;

            OrderHolder(View row) {
                this.containerPic = (ImageView) row.findViewById(R.id.imageview_order_item_picture);
                this.textViewContainer = (TextView) row.findViewById(R.id.textview_order_item_container);
                this.textViewFlavors = (TextView) row.findViewById(R.id.textview_order_item_flavors);
                this.textViewAddins = (TextView) row.findViewById(R.id.textview_order_item_addins);
                this.numberPickerQuantity = (NumberPicker) row.findViewById(R.id.item_quantity);
            }
        }
    }

    private void calculateTotal() {
        int totalCents = 0;
        for(OrderItem o: orderItems){
            totalCents += o.getContainerType().getPriceInCents() * o.getQuantity();
        }
        textViewTotal.setText(NumberFormat.getCurrencyInstance(Locale.US).format( totalCents * 0.01 ));
    }

    private void processSavedState(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            return;
        }
        if(savedInstanceState.containsKey(getString(R.string.order_content_key))) {
            contentState = (int) savedInstanceState.get(getString(R.string.order_content_key));
            switch (contentState) {
                case OrderActivityConstants.CONTENT_CONTAINERS:
                    containerPositionChecked = savedInstanceState.getInt(getString(R.string.container_selection_key));
                    break;
                case OrderActivityConstants.CONTENT_FLAVORS:
                    flavorPositionsChecked = (ArrayList<Boolean>) savedInstanceState.getSerializable(getString(R.string.flavors_selection_key));
                    break;
                case OrderActivityConstants.CONTENT_ADDINS:
                    toppingsPositionsChecked = (ArrayList<Boolean>) savedInstanceState.getSerializable(getString(R.string.toppings_selection_key));
                    saucePositionChecked = savedInstanceState.getInt(getString(R.string.sauce_selection_key));
            }
        }
        if(savedInstanceState.containsKey(getString(R.string.item_in_construction_key))) {
            itemInConstruction = (OrderItem) savedInstanceState.get(getString(R.string.item_in_construction_key));
        }
        if(savedInstanceState.containsKey(getString(R.string.order_items_key))) {
            orderItems = (ArrayList<OrderItem>) savedInstanceState.get(getString(R.string.order_items_key));
        }
        if(savedInstanceState.containsKey(getString(R.string.containers_key))) {
            containers = (ArrayList<ContainerType>) savedInstanceState.get(getString(R.string.containers_key));
        }
        if(savedInstanceState.containsKey(getString(R.string.flavors_key))) {
            flavors = (ArrayList<Flavor>) savedInstanceState.get(getString(R.string.flavors_key));
        }
        if(savedInstanceState.containsKey(getString(R.string.sauces_key))) {
            sauces = (ArrayList<Sauce>) savedInstanceState.get(getString(R.string.sauces_key));
        }
        if(savedInstanceState.containsKey(getString(R.string.toppings_key))) {
            toppings = (ArrayList<Topping>) savedInstanceState.get(getString(R.string.toppings_key));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.order_content_key),contentState);
        outState.putSerializable(getString(R.string.item_in_construction_key),itemInConstruction);
        outState.putSerializable(getString(R.string.order_items_key), (ArrayList<OrderItem>) orderItems);
        outState.putSerializable(getString(R.string.containers_key), (ArrayList<ContainerType>) containers);
        outState.putSerializable(getString(R.string.flavors_key), (ArrayList<Flavor>) flavors);
        outState.putSerializable(getString(R.string.sauces_key), (ArrayList<Sauce>) sauces);
        outState.putSerializable(getString(R.string.toppings_key), (ArrayList<Topping>) toppings);
        switch (contentState) {
            case OrderActivityConstants.CONTENT_CONTAINERS:
                outState.putInt(getString(R.string.container_selection_key), listViewContainers.getCheckedItemPosition());
                break;
            case OrderActivityConstants.CONTENT_FLAVORS:

                ArrayList<Boolean> FlaChecked = new ArrayList<Boolean>();
                SparseBooleanArray FlaCheckedSparce = gridViewFlavors.getCheckedItemPositions();
                for(int i = 0; i < gridViewFlavors.getCount(); i++) {
                    FlaChecked.add(FlaCheckedSparce.get(i));
                }
                outState.putSerializable(getString(R.string.flavors_selection_key), FlaChecked);
                break;
            case OrderActivityConstants.CONTENT_ADDINS:
                ArrayList<Boolean> checked = new ArrayList<Boolean>();
                SparseBooleanArray checkedSparce = listviewToppings.getCheckedItemPositions();
                for(int i = 0; i < listviewToppings.getCount(); i++) {
                    checked.add(checkedSparce.get(i));
                }
                outState.putSerializable(getString(R.string.toppings_selection_key), checked);
                outState.putInt(getString(R.string.sauce_selection_key), listviewSauces.getCheckedItemPosition());
        }
    }
}
