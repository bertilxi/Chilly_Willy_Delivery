package dam.isi.frsf.utn.edu.ar.delivery.broacast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dam.isi.frsf.utn.edu.ar.delivery.task.AlarmSetter.configureAlarm

class RestartAlarmAtBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if ("android.intent.action.BOOT_COMPLETED" == intent.action) {
            configureAlarm(context)
        }
    }
}