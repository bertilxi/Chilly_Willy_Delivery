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
import dam.isi.frsf.utn.edu.ar.delivery.model.Sauce

class SaucesAdapter(context: Context, sauces: List<Sauce>) : ArrayAdapter<Sauce>(context, R.layout.listview_addins_row, sauces) {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = inflater.inflate(R.layout.listview_addins_row, parent, false)
        }
        var holder: SauceHolder? = row!!.tag as SauceHolder?
        if (holder == null) {
            holder = SauceHolder(row)
            row.tag = holder
        }
        if (this.getItem(position)!!.label !== context.getString(R.string.no_sauce_label)) {
            Ion.with(holder.saucePic!!)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position)!!.completeImgURL)
        }
        holder.textViewName!!.text = this.getItem(position)!!.label

        return row
    }

    internal inner class SauceHolder(row: View) {
        var textViewName: TextView? = null
        var saucePic: ImageView? = null

        init {
            textViewName = row.findViewById(R.id.addin_name) as TextView
            saucePic = row.findViewById(R.id.imageview_addin) as ImageView
        }
    }
}