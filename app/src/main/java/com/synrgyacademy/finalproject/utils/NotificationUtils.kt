package com.synrgyacademy.finalproject.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import com.synrgyacademy.finalproject.R
import io.karn.notify.Notify

object NotificationUtils {
    fun createNotification(context: Context, header: String, title: String, text: String, activityClass: Class<*>) {
        Notify
            .with(context)
            .header {
                icon = R.mipmap.ic_rounded
                headerText = header
            }
            .meta { // this: Payload.Meta
                // Launch the specified activity once the notification is clicked.
                clickIntent = PendingIntent.getActivity(context,
                    0,
                    Intent(context, activityClass),
                    PendingIntent.FLAG_IMMUTABLE)
            }
            .content { // this: Payload.Content.Default
                this.title = title
                this.text = text
            }
            .alerting("default") {
                sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
            .show()
    }
}