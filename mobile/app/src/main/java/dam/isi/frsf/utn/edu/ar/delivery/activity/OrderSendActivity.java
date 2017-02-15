package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.koushikdutta.async.future.FutureCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Location;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class OrderSendActivity extends AppCompatActivity implements DialogMapFragment.OnCompleteListener  {

    Order order;
    CheckedTextView checkedTextViewHasChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        order = (Order) extras.get(getString(R.string.order_key));

        View sendButton = findViewById(R.id.send_button);
        checkedTextViewHasChange = (CheckedTextView) findViewById(R.id.checkedTextView_hasChange);
        checkedTextViewHasChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedTextViewHasChange.isChecked())
                    checkedTextViewHasChange.setChecked(false);
                else
                    checkedTextViewHasChange.setChecked(true);

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phoneEditText = (EditText) findViewById(R.id.phone_edittext);
                String phone = phoneEditText.getText().toString();
                if (phone == null || phone.isEmpty()) {
                    phoneEditText.setError("Ingrese un numero de telefono, por favor");
                    return;
                }
                order.setPhone(phone);
                Boolean hasChange = checkedTextViewHasChange.isChecked();
                order.setHasChange(hasChange);
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
                Date today = Calendar.getInstance().getTime();
                String orderDate = df.format(today);
                order.setRequestTime(orderDate);
                DataService dataService = new DataService(OrderSendActivity.this);
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

                finish();
            }
        });


        Button buttonAddress = (Button) findViewById(R.id.button_address);

        buttonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMapFragment mapFragment = new DialogMapFragment();
                mapFragment.show(getSupportFragmentManager(), "map");
            }
        });

    }

    @Override
    public void onComplete(LatLng latLng) {
        Location mLocation = new Location()
                .withLatitude(latLng.latitude)
                .withLongitude(latLng.longitude);
        order.setDestination(mLocation);
    }
}
