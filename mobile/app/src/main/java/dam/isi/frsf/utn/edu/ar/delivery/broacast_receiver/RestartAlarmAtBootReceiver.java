package dam.isi.frsf.utn.edu.ar.delivery.broacast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dam.isi.frsf.utn.edu.ar.delivery.activity.MainActivity;

import static dam.isi.frsf.utn.edu.ar.delivery.task.AlarmSetter.configureAlarm;

public class RestartAlarmAtBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            configureAlarm(context);
        }
    }
}