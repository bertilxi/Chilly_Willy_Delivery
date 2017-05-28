package dam.isi.frsf.utn.edu.ar.delivery.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.koushikdutta.async.future.FutureCallback
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.activity.MainActivity
import dam.isi.frsf.utn.edu.ar.delivery.model.Deal

class NotificationService : Service() {

    internal var deals: List<Deal>? = null
    private var data: DataService? = null
    internal var builder: NotificationCompat.Builder? = null
    internal var pendingIntentMain: PendingIntent? = null
    internal var tries = 0

    override fun onCreate() {
        super.onCreate()

        data = DataService(applicationContext)
        getLastDeal()

        val intentMain = Intent(this, MainActivity::class.java)
        pendingIntentMain = PendingIntent.getActivity(this, 2, intentMain, PendingIntent.FLAG_UPDATE_CURRENT)

        builder = NotificationCompat.Builder(this)
    }

    private fun getLastDeal() {
        try {
            data!!.lastDeal.setCallback(FutureCallback<List<Deal>> { _, result ->
                if (result == null) {
                    Log.d("MIRAME", "onGetDealCompleted: FALLÓ")
                    if (tries++ > 5) {
                        getLastDeal()
                    }
                    return@FutureCallback
                } else {
                    Log.d("MIRAME", "onGetDealCompleted: FUNCIONÓ")
                }
                deals = result
                builder!!
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle(deals!![0].title)
                        .setColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                        // .setStyle(NotificationCompat.BigTextStyle().bigText(deals!![0].description))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntentMain)

                val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                builder?.setSound(alarmSound)

                val notification = builder?.build()
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(3, notification)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }
}
