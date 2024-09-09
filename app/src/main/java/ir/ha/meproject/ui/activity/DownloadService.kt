package ir.ha.meproject.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ir.ha.meproject.utility.extensions.isOreoPlus
import ir.ha.meproject.utility.util.NotificationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadService : Service() {

    private val CHANNEL_ID = "download_channel"
    private val CHANNEL_NAME = "Download Notifications"
    private var notification_id = 1
    private var isDownloading = false

    private val notificationManager by lazy { NotificationManagerCompat.from(this) }


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
        if (isOreoPlus()) {
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
            val connection = withContext(Dispatchers.IO) {
                url.openConnection()
            }
            withContext(Dispatchers.IO) {
                connection.connect()
            }

            val fileLength = connection.contentLength

            if (fileLength <= 0) {
                Log.e("DownloadService", "Invalid file length: $fileLength")
                throw Exception("File length is invalid")
            }

            val input = BufferedInputStream(withContext(Dispatchers.IO) {
                url.openStream()
            })
            val output = withContext(Dispatchers.IO) {
                FileOutputStream(tempFile)
            }

            // افزایش اندازه بافر به 8192
            val data = ByteArray(8192)
            var total: Long = 0
            var count: Int
            var lastProgress = 0 // برای ذخیره آخرین مقدار پیشرفت

            try {
                EventBus.getDefault().post(MessageEvent(MessageEnum.STARTED))
                while (withContext(Dispatchers.IO) {
                        input.read(data)
                    }.also { count = it } != -1) {
                    total += count
                    withContext(Dispatchers.IO) {
                        output.write(data, 0, count)
                    }

                    // محاسبه‌ی پیشرفت دانلود
                    val progress = ((total * 100) / fileLength).toInt()

                    // فقط زمانی که پیشرفت تغییر می‌کند، نوتیفیکیشن را به‌روزرسانی کن
                    if (progress != lastProgress) {

                        lastProgress = progress
                        Log.d("DownloadService", "Download progress: $progress%")

                        withContext(Dispatchers.Main) {
                            // Update progress notification
                            NotificationUtil.updateProgressNotification(
                                context = this@DownloadService,
                                notificationManager = notificationManager,
                                channelId = CHANNEL_ID,
                                icon = android.R.drawable.stat_sys_download,
                                title = "$fileName Downloading...",
                                notificationId = notification_id,
                                progressMax = 100,
                                progressCurrent = progress
                            )
                        }

                    }
                }
            } catch (e: Exception) {
                Log.e("DownloadService", "Error during download loop: ${e.message}")
                throw e
            }

            withContext(Dispatchers.IO) {
                output.flush()
                output.close()
                input.close()
            }

            // تغییر نام فایل موقت به فایل نهایی
            val renameSuccessful = tempFile.renameTo(finalFile)
            if (renameSuccessful) {
                withContext(Dispatchers.Main) {
                    // نوتیفیکیشن اتمام دانلود
                    NotificationUtil.showBasicNotification(
                        context = this@DownloadService,
                        notificationManager = notificationManager,
                        channelId = CHANNEL_ID,
                        title = "Download completed",
                        message = "Tap to open",
                        icon = android.R.drawable.stat_sys_download_done,
                        priority = NotificationManager.IMPORTANCE_LOW,
                        notificationId = notification_id,
                        intent = Intent(Intent.ACTION_VIEW)
                    )

                    EventBus.getDefault().post(MessageEvent(MessageEnum.COMPLETED))
                }
            } else {
                Log.e("DownloadService", "Failed to rename file")
                withContext(Dispatchers.Main) {
                    NotificationUtil.showBasicNotification(
                        context = this@DownloadService,
                        notificationManager = notificationManager,
                        channelId = CHANNEL_ID,
                        title = "Download failed",
                        message = "An error occurred during download.",
                        icon = android.R.drawable.stat_notify_error,
                        priority = NotificationManager.IMPORTANCE_HIGH,
                        notificationId = notification_id
                    )
                }
                EventBus.getDefault().post(MessageEvent(MessageEnum.FAILED))
            }

            isDownloading = false

            // تاخیر در توقف foreground
            stopForeground(false)
            stopSelf()

        } catch (e: Exception) {
            Log.e("DownloadService", "startDownload: ${e.message}")

            isDownloading = false
            withContext(Dispatchers.Main) {
                NotificationUtil.showBasicNotification(
                    context = this@DownloadService,
                    notificationManager = notificationManager,
                    channelId = CHANNEL_ID,
                    title = "Download failed",
                    message = "An error occurred during download.",
                    icon = android.R.drawable.stat_notify_error,
                    priority = NotificationManager.IMPORTANCE_HIGH,
                    notificationId = notification_id
                )
            }
            EventBus.getDefault().post(MessageEvent(MessageEnum.FAILED))
            stopForeground(false)
            stopSelf()
        }
    }


    companion object{
        data class MessageEvent(var message : MessageEnum)
        enum class MessageEnum{
            STARTED , COMPLETED , FAILED ,
        }
    }
}
