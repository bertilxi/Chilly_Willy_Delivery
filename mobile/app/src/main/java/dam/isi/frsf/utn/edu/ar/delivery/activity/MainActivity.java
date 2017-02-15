package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants;

import static dam.isi.frsf.utn.edu.ar.delivery.task.AlarmSetter.configureAlarm;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MIRAME", "onCreate: llegue aca");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appConstants.deviceID =
                Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        appConstants.localPath = getString(R.string.local_path);

        Toast.makeText(MainActivity.this, appConstants.deviceID, Toast.LENGTH_LONG).show();

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

        configureAlarm(getApplicationContext());
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

    private void addNotification() {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
