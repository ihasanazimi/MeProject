package ir.ha.meproject.ui.fragments.temp1

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import java.util.UUID
import java.util.concurrent.TimeUnit

object WorkManagerUtil {

    // اجرای یک کار ساده بلافاصله
    inline fun <reified T : ListenableWorker> startOneTimeWork(
        context: Context,
        inputData: Data? = null,
        tag: String? = null
    ) {
        val workRequest = OneTimeWorkRequestBuilder<T>()
            .apply {
                inputData?.let { setInputData(it) }
                tag?.let { addTag(it) }
            }.build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    // اجرای یک کار تکراری با شرایط مشخص
    inline fun <reified T : ListenableWorker> startPeriodicWork(
        context: Context,
        interval: Long,
        timeUnit: TimeUnit,
        inputData: Data? = null,
        tag: String? = null
    ) {
        val workRequest = PeriodicWorkRequestBuilder<T>(interval, timeUnit)
            .apply {
                inputData?.let { setInputData(it) }
                tag?.let { addTag(it) }
            }.build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    // اجرای یک کار یک بار با شرایط خاص (Constraints)
    inline fun <reified T : ListenableWorker> startOneTimeWorkWithConstraints(
        context: Context,
        constraints: Constraints,
        inputData: Data? = null,
        tag: String? = null
    ) {
        val workRequest = OneTimeWorkRequestBuilder<T>()
            .apply {
                setConstraints(constraints)
                inputData?.let { setInputData(it) }
                tag?.let { addTag(it) }
            }.build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    // لغو کردن کارها با توجه به تگ
    fun cancelWorkByTag(context: Context, tag: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag(tag)
    }

    // لغو کردن تمامی کارها
    fun cancelAllWork(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
    }

    // بررسی وضعیت یک کار با توجه به ID آن
    fun getWorkStatusById(context: Context, workId: UUID): LiveData<WorkInfo> {
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(workId)
    }
}


/**
 *فرض کنید یک Worker به نام MyWorker دارید:
 *
 * class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
 *     override fun doWork(): Result {
 *         // انجام کار اینج        ا
 *         return Result.success()
 *     }
 * }
 *
 */


/**
 *اجرای یک کار ساده یک‌بار:
 * WorkManagerUtil.startOneTimeWork<MyWorker>(
 *     context = this
 * )
 *
 */


/**
 *  اجرای یک کار تکراری:
 * WorkManagerUtil.startPeriodicWork<MyWorker>(
 *     context = this,
 *     interval = 15,
 *     timeUnit = TimeUnit.MINUTES
 * )
 *
 */


/**
 *  اجرای یک کار با شرایط خاص (مثلاً فقط وقتی به وای‌فای متصل هستیم)
 * val constraints = Constraints.Builder()
 *     .setRequiredNetworkType(NetworkType.UNMETERED)
 *     .build()
 *
 * WorkManagerUtil.startOneTimeWorkWithConstraints<MyWorker>(
 *     context = this,
 *     constraints = constraints
 * )
 *
 */

