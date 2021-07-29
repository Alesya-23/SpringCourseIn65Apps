package com.coursein65apps.mynotfirstappkotlin

import android.app.AlarmManager
import android.app.AlarmManager.INTERVAL_DAY
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class ContactBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID = "channelID"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val contactDetailId = intent.getIntExtra("ID", -1)
        val nameContact = intent.getStringExtra("textNotification")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    context.getString(com.coursein65apps.mynotfirstappkotlin.R.string.name_notification_channel),
                    importance
                ).apply {
                    description =
                        context.getString(com.coursein65apps.mynotfirstappkotlin.R.string.description_notification)
                }
            notificationManager.createNotificationChannel(channel)
        }
        //Интент для активити, которую мы хотим запускать при нажатии на уведомление
        val intentReply = Intent(context, MainActivity::class.java)
        intentReply.apply {
            putExtra("CONTACT_DETAIL_ID", intent.getIntExtra("ID", -1))
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intentReply, FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(context.getString(com.coursein65apps.mynotfirstappkotlin.R.string.title_notification))
            .setContentText("Сегодня день рождения у $nameContact")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        notificationManager.notify(contactDetailId, builder.build())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        val pendingIntentRepeatNotification =
            PendingIntent.getBroadcast(context, contactDetailId, intentReply, FLAG_UPDATE_CURRENT)

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            INTERVAL_DAY * 24 * 365,
            pendingIntentRepeatNotification
        )
    }

    fun cancelAlarmNotificationContact(context: Context, intent: Intent) {
        val contactDetailId = intent.getIntExtra("ID", -1)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            contactDetailId, intent,
            FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }
}
