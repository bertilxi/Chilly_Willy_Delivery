package dam.isi.frsf.utn.edu.ar.delivery.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;

import java.text.SimpleDateFormat;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.activity.MainActivity;
import dam.isi.frsf.utn.edu.ar.delivery.model.Deal;


public class NotificationService extends IntentService {

    private DataService data;
    Deal deal;

    @Override
    public void onCreate() {
        super.onCreate();

        data = new DataService(this);
        getLastDeal();

        Intent intentMain = new Intent(this, MainActivity.class);
        PendingIntent pendingIntentMain = PendingIntent.getActivity(this, 2, intentMain, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(deal.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(deal.getDescription()))
                .setAutoCancel(true)
                .setContentIntent(pendingIntentMain);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(3, notification);
    }

    private void getLastDeal() {
        try {
            data.getLastDeal().setCallback(new FutureCallback<Deal>() {
                @Override
                public void onCompleted(Exception e, Deal result) {
                    if(result == null){
                        Log.d("MIRAME", "onGetDealCompleted: FALLÓ");
                        getLastDeal();
                        return;
                    } else {
                        Log.d("MIRAME", "onGetDealCompleted: FUNCIONÓ");
                    }
                    deal = result;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
