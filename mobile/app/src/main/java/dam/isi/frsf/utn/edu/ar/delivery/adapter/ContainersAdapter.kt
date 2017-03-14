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
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerType
import java.text.NumberFormat
import java.util.*

class ContainersAdapter(context: Context,  containers: List<ContainerType>) : ArrayAdapter<ContainerType>(context, R.layout.listview_containers_row, containers) {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = inflater.inflate(R.layout.listview_containers_row, parent, false)
        }
        var holder: ContainerHolder? = row!!.tag as ContainerHolder?

        if (holder == null) {
            holder = ContainerHolder(row)
            row.tag = holder
        }

        Ion.with(holder.containerPic!!)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .load(this.getItem(position)!!.completeImgURL)
        holder.containerName!!.text = this.getItem(position)!!.label
        holder.containerPrice!!.text = NumberFormat.getCurrencyInstance(Locale.US).format(
                this.getItem(position)!!.priceInCents * 0.01
        )

        return row
    }

    internal inner class ContainerHolder(row: View) {
        var containerPic: ImageView? = null
        var containerName: TextView? = null
        var containerPrice: TextView? = null

        init {
            containerPic = row.findViewById(R.id.imageview_addin) as ImageView
            containerName = row.findViewById(R.id.containerName) as TextView
            containerPrice = row.findViewById(R.id.container_price) as TextView
        }
    }
}