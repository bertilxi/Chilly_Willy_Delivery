package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class MainActivity extends AppCompatActivity {

    DataService data = new DataService(MainActivity.this);

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MIRAME", "onCreate: llegue aca");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appConstants.deviceID =
                Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Toast.makeText(MainActivity.this, appConstants.deviceID, Toast.LENGTH_LONG).show();

        // the device open a new session
        try {
            data.openSession(appConstants.deviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnOrder = (Button) findViewById(R.id.btnOrder);
        Button btnLocation = (Button) findViewById(R.id.btnLocation);
        Button btnReview = (Button) findViewById(R.id.btnReview);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoOrderActivity();
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLocationActivity();
            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoReviewActivity();
            }
        });

    }

    private void gotoOrderActivity() {
        Intent i = new Intent(this, OrderActivity.class);
        startActivity(i);
    }

    private void gotoLocationActivity() {
        Intent i = new Intent(this, LocationActivity.class);
        startActivity(i);
    }

    private void gotoReviewActivity() {
        Intent i = new Intent(this, ReviewActivity.class);
        startActivity(i);
    }
}
