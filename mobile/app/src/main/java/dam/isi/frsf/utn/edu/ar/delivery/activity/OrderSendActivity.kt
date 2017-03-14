package dam.isi.frsf.utn.edu.ar.delivery.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.model.Location
import dam.isi.frsf.utn.edu.ar.delivery.model.Order
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService
import java.text.SimpleDateFormat
import java.util.*

class OrderSendActivity : AppCompatActivity(), OnMapReadyCallback {

    private var order: Order? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_send)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val extras = intent.extras
        order = extras.get(getString(R.string.order_key)) as Order

        val sendButton = findViewById(R.id.send_button)
        sendButton.setOnClickListener(View.OnClickListener {
            val phoneEditText = findViewById(R.id.phone_edittext) as EditText
            val phone = phoneEditText.text.toString()
            if (phone.isEmpty()) {
                phoneEditText.error = "Ingrese un numero de teléfono, por favor"
                return@OnClickListener
            }
            val location = order?.destination

            if (location == null) {
                phoneEditText.error = "Haga click sobre su dirección e intente nuevamente"
                return@OnClickListener
            }

            order?.phone = phone
            val mSwitch = findViewById(R.id.switch_has_change) as Switch
            val hasChange = mSwitch.isChecked
            order?.hasChange = hasChange
            val df = SimpleDateFormat("dd/MM/yy HH:mm", Locale("es-AR"))
            val today = Calendar.getInstance().time
            val orderDate = df.format(today)
            order?.requestTime = orderDate
            val dataService = DataService(applicationContext)
            try {
                dataService.addOrder(order!!).setCallback { _, result -> Log.d("MIRAME", "onAddOrderCompleted: " + result) }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        })

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map_destination) as SupportMapFragment
        mapFragment.getMapAsync(this@OrderSendActivity)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val santaFe = LatLng(-31.619276, -60.683970)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(santaFe, 13f)
        mMap!!.animateCamera(cameraUpdate)

        mMap!!.setOnMapLongClickListener { latLng ->
            mMap!!.clear()
            mMap!!.addMarker(MarkerOptions()
                    .position(latLng)
                    .title("Tu ubicación"))

            val camUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16f)
            mMap!!.animateCamera(camUpdate)

            order!!.withDestination(Location()
                    .withLatitude(latLng.latitude)
                    .withLongitude(latLng.longitude))
        }
    }
}
