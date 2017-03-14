package dam.isi.frsf.utn.edu.ar.delivery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.koushikdutta.ion.Ion
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.model.Topping

class ToppingsAdapter(context: Context,toppings: List<Topping>) : ArrayAdapter<Topping>(context, R.layout.listview_addins_row, toppings) {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = inflater.inflate(R.layout.listview_addins_row, parent, false)
        }
        var holder: ToppingHolder? = row!!.tag as ToppingHolder
        if (holder == null) {
            holder = ToppingHolder(row)
            row.tag = holder
        }
        Ion.with(holder.toppingPic!!)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .load(this.getItem(position)!!.completeImgURL)
        holder.textViewName!!.text = this.getItem(position)!!.label

        return row
    }

    internal inner class ToppingHolder(row: View) {
        var textViewName: TextView? = null
        var toppingPic: ImageView? = null

        init {
            textViewName = row.findViewById(R.id.addin_name) as TextView
            toppingPic = row.findViewById(R.id.imageview_addin) as ImageView
        }
    }
}