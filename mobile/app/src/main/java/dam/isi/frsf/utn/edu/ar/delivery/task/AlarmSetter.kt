package dam.isi.frsf.utn.edu.ar.delivery.task

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.broacast_receiver.AlarmReceiver
import java.util.*

object AlarmSetter {
    fun configureAlarm(context: Context) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        val intent = Intent(context, AlarmReceiver::class.java)
        val id = context.resources.getInteger(R.integer.alarm_intent_id)
        val pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 16)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY * 7, pi)
    }
}
