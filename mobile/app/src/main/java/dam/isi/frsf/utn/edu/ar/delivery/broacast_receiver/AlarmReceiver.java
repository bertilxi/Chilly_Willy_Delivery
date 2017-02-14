package dam.isi.frsf.utn.edu.ar.delivery.broacast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dam.isi.frsf.utn.edu.ar.delivery.service.NotificationService;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentNotificationService = new Intent(context, NotificationService.class);
        context.startService(intentNotificationService);
    }
}
