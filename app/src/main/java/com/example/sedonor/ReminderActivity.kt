package com.example.sedonor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.widget.TextView

class ReminderActivity : AppCompatActivity() {

    private lateinit var btnNotif: Button
    private lateinit var tvSisaWaktu: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val sisa = intent.getStringExtra("SISAWAKTU")

        tvSisaWaktu = findViewById(R.id.tvSisaHari)
        tvSisaWaktu.text = sisa

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

    }

    fun btn(view: View){
        makeNotification()
    }

    fun makeNotification(){
        val sisa = intent.getStringExtra("SISAWAKTU")
        val chanelID = "CHANNEL_ID_NOTIFICATION"
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, chanelID)
        builder.setSmallIcon(R.drawable.logo_sedonor2)
            .setContentTitle(sisa + " hari sebelum jadwal donor darah kamu, sudah siap?")
            .setContentText("Untuk menjaga kualitas donor darah, persiapkan diri dengan menjaga kesehatan")
            .setAutoCancel(true).priority = NotificationCompat.PRIORITY_DEFAULT

        var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var notificationChannel: NotificationChannel? = notificationManager.getNotificationChannel(chanelID)
            if(notificationChannel == null){
                var importance: Int = NotificationManager.IMPORTANCE_HIGH
                notificationChannel = NotificationChannel(chanelID, "Some Description", importance)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        notificationManager.notify(0, builder.build())
    }

    fun back(view : View){
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }

}



