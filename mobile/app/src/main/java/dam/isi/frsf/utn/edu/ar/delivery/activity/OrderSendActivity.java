package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.async.future.FutureCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Location;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class OrderSendActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Order order;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        order = (Order) extras.get(getString(R.string.order_key));

        View sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phoneEditText = (EditText) findViewById(R.id.phone_edittext);
                String phone = phoneEditText.getText().toString();
                if (phone == null || phone.isEmpty()) {
                    phoneEditText.setError("Ingrese un numero de teléfono, por favor");
                    return;
                }

                Location location = order.getDestination();

                if(location == null){
                    phoneEditText.setError("Haga click sobre su dirección e intente nuevamente");
                    return;
                }

                order.setPhone(phone);
                Switch mSwitch = (Switch) findViewById(R.id.switch_has_change);
                Boolean hasChange = mSwitch.isChecked();
                order.setHasChange(hasChange);
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
                Date today = Calendar.getInstance().getTime();
                String orderDate = df.format(today);
                order.setRequestTime(orderDate);
                DataService dataService = new DataService(getApplicationContext());
                try {
                    dataService.addOrder(order).setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            Log.d("MIRAME", "onAddOrderCompleted: " + result);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_destination);
        mapFragment.getMapAsync(OrderSendActivity.this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng santaFe = new LatLng(-31.619276, -60.683970);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(santaFe, 13);
        mMap.animateCamera(cameraUpdate);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Tu ubicación"));

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                mMap.animateCamera(cameraUpdate);

                order.withDestination(new Location()
                        .withLatitude(latLng.latitude)
                        .withLongitude(latLng.longitude));
            }
        });
    }
}
