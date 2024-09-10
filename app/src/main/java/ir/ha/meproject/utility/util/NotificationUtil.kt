package ir.ha.meproject.utility.util

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ir.ha.meproject.utility.extensions.showToast

object NotificationUtil {

    private var notificationUpdateNotifBuilder: NotificationCompat.Builder?= null


    fun showBasicNotification(
        context: Context,
        notificationManager : NotificationManagerCompat ,
        channelId: String,
        title: String,
        message: String,
        icon: Int,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        intent: Intent? = null,
        notificationId: Int = 1
    ) {
        val pendingIntent: PendingIntent? = intent?.let {
            PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)

        val notificationManager = NotificationManagerCompat.from(context)


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            showToast(context,"Permissions not allowed")
            return
        }

        notificationManager.notify(notificationId, builder.build())
    }

    fun showNotificationWithAction(
        context: Context,
        notificationManager : NotificationManagerCompat ,
        channelId: String,
        title: String,
        message: String,
        icon: Int,
        actionText: String,
        actionIntent: Intent,
        notificationId: Int = 2
    ) {
        val actionPendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(0, actionText, actionPendingIntent)
            .setAutoCancel(false)

        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            showToast(context,"Permissions not allowed")
            return
        }

        notificationManager.notify(notificationId, builder.build())
    }

    fun showProgressNotification(
        context: Context,
        notificationManager : NotificationManagerCompat ,
        channelId: String,
        title: String,
        icon: Int,
        progressMax: Int,
        progressCurrent: Int,
        notificationId: Int = 3
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(progressMax, progressCurrent, false)

        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            showToast(context,"Permissions not allowed")
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

    fun updateProgressNotification(
        context: Context,
        notificationManager : NotificationManagerCompat ,
        channelId: String,
        title: String,
        icon: Int,
        progressMax: Int,
        progressCurrent: Int,
        notificationId: Int
    ) {

        if (notificationUpdateNotifBuilder == null){
            notificationUpdateNotifBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setProgress(progressMax, progressCurrent, false)
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            showToast(context,"Permissions not allowed")
            return
        }

        notificationUpdateNotifBuilder?.build()?.let { notificationManager.notify(notificationId, it) }
    }

    fun showHighPriorityNotification(
        context: Context,
        notificationManager : NotificationManagerCompat ,
        channelId: String,
        title: String,
        message: String,
        icon: Int,
        notificationId: Int = 4
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)

        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            showToast(context,"Permissions not allowed")
            return
        }

        notificationManager.notify(notificationId, builder.build())
    }

    fun showBigPictureNotification(
        context: Context,
        notificationManager : NotificationManagerCompat ,
        channelId: String,
        title: String,
        message: String,
        bigPicture: Bitmap,
        icon: Int,
        notificationId: Int = 5
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigPicture))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(false)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            showToast(context,"Permissions not allowed")
            return
        }

        notificationManager.notify(notificationId, builder.build())
    }
}