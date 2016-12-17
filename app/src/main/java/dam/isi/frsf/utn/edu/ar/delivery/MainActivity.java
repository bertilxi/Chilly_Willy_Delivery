package dam.isi.frsf.utn.edu.ar.delivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnOrder;
    private Button btnLocation;
    private Button btnReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        btnReview = (Button) findViewById(R.id.btnReview);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoOrderActivity();
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLocationActivity();
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoReviewActivity();
            }
        });

    }

    private void gotoOrderActivity(){
        Intent i = new Intent(this, OrderActivity.class);
        startActivity(i);
    }
    private void gotoLocationActivity(){
        Intent i = new Intent(this, LocationActivity.class);
        startActivity(i);
    }
    private void gotoReviewActivity(){
        Intent i = new Intent(this, ReviewActivity.class);
        startActivity(i);
    }
}
