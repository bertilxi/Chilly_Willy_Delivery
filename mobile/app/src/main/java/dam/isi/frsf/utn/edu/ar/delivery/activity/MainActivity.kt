package dam.isi.frsf.utn.edu.ar.delivery.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.NotificationCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import java.util.Calendar

import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants

import dam.isi.frsf.utn.edu.ar.delivery.task.AlarmSetter.configureAlarm

class MainActivity : AppCompatActivity() {

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MIRAME", "onCreate: llegue aca")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appConstants.deviceID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        appConstants.localPath = getString(R.string.local_path)
        appConstants.staticPath = appConstants.localPath!! + "static/"

        Toast.makeText(this@MainActivity, appConstants.deviceID, Toast.LENGTH_LONG).show()

        val btnOrder = findViewById(R.id.btnOrder) as Button
        val btnLocation = findViewById(R.id.btnLocation) as Button
        val btnReview = findViewById(R.id.btnReview) as Button

        btnOrder.setOnClickListener { gotoOrderActivity() }
        btnLocation.setOnClickListener { gotoLocationActivity() }
        btnReview.setOnClickListener { gotoReviewActivity() }

        configureAlarm(applicationContext)
    }

    private fun gotoOrderActivity() {
        val i = Intent(this, OrderActivity::class.java)
        startActivity(i)
    }

    private fun gotoLocationActivity() {
        val i = Intent(this, LocationActivity::class.java)
        startActivity(i)
    }

    private fun gotoReviewActivity() {
        val i = Intent(this, ReviewActivity::class.java)
        startActivity(i)
    }
}
