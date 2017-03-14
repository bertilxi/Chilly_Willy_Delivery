package dam.isi.frsf.utn.edu.ar.delivery.broacast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

import dam.isi.frsf.utn.edu.ar.delivery.service.NotificationService

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MIRAME", "onReceive alarma: llegamos aca")
        val intentNotificationService = Intent(context, NotificationService::class.java)
        context.startService(intentNotificationService)
    }
}
