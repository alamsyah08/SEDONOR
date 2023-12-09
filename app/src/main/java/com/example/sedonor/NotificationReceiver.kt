package com.example.sedonor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    private fun showNotification(context: Context) {
        if (checkNotificationPermission(context)) {
            val builder = NotificationCompat.Builder(context, "reminder_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder")
                .setContentText("Waktunya untuk melakukan sesuatu!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                notify(1, builder.build())
            }
        } else {
            // Handle the case where notification permission is not granted
            // You may prompt the user to grant the notification permission
            // or handle it in a way that is appropriate for your app.
        }
    }

    private fun checkNotificationPermission(context: Context): Boolean {
        val notificationPermission = "android.permission.NOTIFICATION"
        val result = context.checkCallingOrSelfPermission(notificationPermission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}
