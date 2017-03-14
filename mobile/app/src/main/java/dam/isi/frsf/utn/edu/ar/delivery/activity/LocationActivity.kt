package dam.isi.frsf.utn.edu.ar.delivery.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.koushikdutta.async.future.FutureCallback
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.adapter.LocateOrderAdapter
import dam.isi.frsf.utn.edu.ar.delivery.model.Order
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService
import java.util.*

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null
    private var noOrders: AlertDialog.Builder? = null
    private var data: DataService? = null
    private var orders: List<Order> = ArrayList()
    private var mListView: ListView? = null
    private var mLocateOrderAdapter: LocateOrderAdapter? = null
    private val mLocation = LatLng(-31.619276, -60.683970)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        data = DataService(applicationContext)

        noOrders = AlertDialog.Builder(this@LocationActivity)
                .setTitle("No hiciste pedidos recientes")
                .setMessage("Haz un pedido y luego vuelve a intentarlo")
                .setPositiveButton(android.R.string.ok) { _, _ -> }

        try {

            data!!.orders.setCallback(FutureCallback<List<Order>> { _, result ->
                val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.view?.visibility = View.GONE
                mapFragment.getMapAsync(this@LocationActivity)

                if (result == null || result.isEmpty()) {
                    noOrders!!.show()
                    return@FutureCallback
                }

                orders = result.reversed()
                mListView = findViewById(R.id.location_order_list) as ListView
                mLocateOrderAdapter = LocateOrderAdapter(applicationContext, orders)
                mListView?.adapter = mLocateOrderAdapter

                mListView?.onItemClickListener = AdapterView.OnItemClickListener { _, v, position, _ ->
                    val destination = orders[position].destination
                    val currentLocation = orders[position].lastLocation
                    val destinationLatLng: LatLng
                    val currentLocationLatLng: LatLng
                    try {
                        destinationLatLng = LatLng(destination?.latitude!!, destination.longitude!!)
                        currentLocationLatLng = LatLng(currentLocation?.latitude!!, currentLocation.longitude!!)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(v.context, "Error: no es posible visualizar el mapa", Toast.LENGTH_SHORT).show()
                        return@OnItemClickListener
                    }

                    mapFragment.view!!.visibility = View.VISIBLE
                    updateMap(destinationLatLng, currentLocationLatLng)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onMapReady(mMap: GoogleMap) {

        googleMap = mMap

        googleMap!!.addMarker(MarkerOptions()
                .position(mLocation)
                .title("Chilly Willy")
                .snippet("heladeria")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.logo))
        )

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLocation, 15f)
        googleMap!!.animateCamera(cameraUpdate)

    }

    private fun updateMap(destination: LatLng, currentLocation: LatLng) {
        googleMap!!.addMarker(MarkerOptions()
                .position(destination)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.home))
                .title(""))

        googleMap!!.addMarker(MarkerOptions()
                .position(currentLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.delivery))
                .title(""))

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 13f)
        googleMap!!.animateCamera(cameraUpdate)
    }

}
