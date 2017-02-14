package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.koushikdutta.async.future.FutureCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class OrderSendActivity extends AppCompatActivity {

    Order order;

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
                if(phone == null || phone.isEmpty()){
                    phoneEditText.setError("Ingrese un numero de telefono, por favor");
                    return;
                }
                order.setPhone(phone);
                CheckedTextView checkedTextViewHasChange = (CheckedTextView) findViewById(R.id.checkedTextView_hasChange);
                Boolean hasChange = checkedTextViewHasChange.isChecked();
                order.setHasChange(hasChange);
                DataService dataService = new DataService(OrderSendActivity.this);
                try {
                    dataService.addOrder(order).setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            Log.d("MIRAME", "onAddOrderCompleted: "+ result);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                

                finish();
            }
        });
    }

}
