package dam.isi.frsf.utn.edu.ar.delivery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.model.Order

class LocateOrderAdapter(context: Context, orders: List<Order>) : ArrayAdapter<Order>(context, R.layout.listview_row_order_item, orders) {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var row = convertView
        if (row == null) {
            row = inflater.inflate(R.layout.listview_row_order, parent, false)
        }

        var holder: OrderHolder? = row!!.tag as OrderHolder?
        if (holder == null) {
            holder = OrderHolder(row)
            row.tag = holder
        }

        holder.dateTextView!!.text = getItem(position).requestTime

        return row
    }

    internal inner class OrderHolder(row: View) {
        var dateTextView: TextView? = null

        init {
            this.dateTextView = row.findViewById(R.id.textview_order_date) as TextView
        }
    }

}