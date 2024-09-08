package ir.ha.meproject.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadService : Service() {

    private val CHANNEL_ID = "download_channel"
    private val CHANNEL_NAME = "Download Notifications"
    private var notification_id = 1
    private var isDownloading = false
    private val  notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isDownloading) {
            return START_NOT_STICKY
        }

        val fileUrl = intent?.getStringExtra("fileUrl") ?: ""
        var fileName = intent?.getStringExtra("fileName") ?: "downloaded_file"

        // Create the notification channel (Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW).apply {
                description = "Download Notification Channel"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("$fileName Downloading...")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setProgress(100, 0, false)
            .setOngoing(true) // Important for foreground services
            .build()

        // Call startForeground immediately with a valid notification
        startForeground(notification_id, notification)

        if (fileUrl.isNotEmpty()) {
            isDownloading = true
            fileName = getUniqueFileName(fileName)
            CoroutineScope(Dispatchers.IO).launch {
                startDownload(fileUrl, fileName)
            }
        }

        return START_NOT_STICKY
    }


    private fun getUniqueFileName(fileName: String): String {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        var uniqueFileName = fileName
        var counter = 1

        while (File(downloadsDir, uniqueFileName).exists()) {
            uniqueFileName = fileName.substringBeforeLast(".") + "($counter)." + fileName.substringAfterLast(".")
            counter++
        }

        return uniqueFileName
    }

    private suspend fun startDownload(fileUrl: String, fileName: String) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val tempFile = File(downloadsDir, "temp_$fileName")
        val finalFile = File(downloadsDir, fileName)

        try {
            val url = URL(fileUrl)
            val connection = url.openConnection()
            connection.connect()

            val fileLength = connection.contentLength

            val input = BufferedInputStream(url.openStream())
            val output = FileOutputStream(tempFile)

            val data = ByteArray(1024)
            var total: Long = 0
            var count: Int

            while (input.read(data).also { count = it } != -1) {
                total += count
                output.write(data, 0, count)

                val progress = (total * 100 / fileLength).toInt()
                withContext(Dispatchers.Main) {
                    // Update progress notification
                    showNotification(
                        this@DownloadService,
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        notification_id, // Use the same ID for progress notification
                        "$fileName Downloading...",
                        "Progress: $progress%",
                        android.R.drawable.stat_sys_download,
                        progress,
                        autoCancel = false
                    )
                }
            }

            output.flush()
            output.close()
            input.close()

            val renameSuccessful = tempFile.renameTo(finalFile)
            if (renameSuccessful) {
                withContext(Dispatchers.Main) {
                    // Update to completion notification using the same ID
                    showNotification(
                        this@DownloadService,
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        notification_id, // Same ID to replace the progress notification
                        "Download completed",
                        "Tap to open",
                        android.R.drawable.stat_sys_download_done,
                        pendingIntent = PendingIntent.getActivity(
                            this@DownloadService, 0, Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(FileProvider.getUriForFile(this@DownloadService, "${packageName}.provider", finalFile), contentResolver.getType(FileProvider.getUriForFile(this@DownloadService, "${packageName}.provider", finalFile)))
                                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                            },
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        ),
                        autoCancel = false /* Automatically cancel the notification when tapped */,
                        importantLevel = NotificationManager.IMPORTANCE_HIGH
                    )
                }
            } else {
                Log.e("DownloadService", "Failed to rename file")
                withContext(Dispatchers.Main) {
                    showNotification(
                        this@DownloadService,
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        notification_id, // Same ID for error notification
                        "Download failed",
                        "An error occurred during download.",
                        android.R.drawable.stat_notify_error,
                        importantLevel = NotificationManager.IMPORTANCE_HIGH,
                        autoCancel = false
                    )
                }
            }

            isDownloading = false
            stopForeground(true)
            stopSelf()

        } catch (e: Exception) {
            Log.e("DownloadService", "startDownload: ${e.message}")

            isDownloading = false
            withContext(Dispatchers.Main) {
                showNotification(
                    this@DownloadService,
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    notification_id, // Same ID for error notification
                    "Download failed",
                    "An error occurred during download.",
                    android.R.drawable.stat_notify_error,
                    importantLevel = NotificationManager.IMPORTANCE_HIGH,
                    autoCancel = false
                )
            }
            stopForeground(true)
            stopSelf()
        }
    }



    private fun showNotification(
        context: Context,
        channelId: String,
        channelName: String,
        notificationId: Int,
        title: String,
        message: String,
        smallIcon: Int,
        progress: Int? = null,
        pendingIntent: PendingIntent? = null,
        autoCancel: Boolean = false,
        importantLevel : Int = NotificationManager.IMPORTANCE_LOW
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importantLevel)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(smallIcon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(autoCancel)

        if (progress != null) {
            builder.setProgress(100, progress, false)
        } else {
            builder.setProgress(0, 0, false)
        }

        val notification = builder.build()
        notificationManager.notify(notificationId, notification)
    }
}

