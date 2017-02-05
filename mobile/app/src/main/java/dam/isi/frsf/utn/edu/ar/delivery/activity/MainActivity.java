package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.ReviewActivity;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        setContentView(R.layout.activity_main);

        Button btnOrder = (Button) findViewById(R.id.btnOrder);
        Button btnLocation = (Button) findViewById(R.id.btnLocation);
        Button btnReview = (Button) findViewById(R.id.btnReview);
        btnOrder.setOnClickListener(view -> gotoOrderActivity());
        btnLocation.setOnClickListener(view -> gotoLocationActivity());
        btnReview.setOnClickListener(view -> gotoReviewActivity());

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
