package com.example.samplefcmpushnotification

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        // Handle FCM messages here
        Log.d(TAG, "From: ${message.from}")

        // Check if the message contains data
        message.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + message.data.values.toString())
        }

        // Check if the message contains a notification payload
        message.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            val messageTitle = it.title
            val messageBody = it.body

            val builder = NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "onMessageReceived: POST_NOTIFICATION PERMISSION NOT_GRANTED")
                // here to request the missing permissions

            }
            Log.d(TAG, "onMessageReceived: POST_NOTIFICATION PERMISSION GRANTED")
            NotificationManagerCompat.from(this).notify(1, builder.build())
        }
    }

    override fun onNewToken(token: String) {
        // Handle new or refreshed FCM registration token
        Log.d(TAG, "Refreshed token: $token")
        // You may want to send this token to your server for further use
    }

    companion object {
        private const val TAG = "PushNotificationService"
    }

}