package ir.ha.meproject.ui.fragments.temp1

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters


class UploadWorker(context : Context,params : WorkerParameters):CoroutineWorker(context,params) {

    val TAG = this::class.java.simpleName

    override suspend fun doWork(): Result {

        for(i : Int in 0..6000){
            Log.i(TAG,"Uploading $i")
        }

        // Return Result.success() if the work finished successfully
        // Return Result.failure() if the work failed
        // Return Result.retry() if the work should be retried

        return Result.success()
    }

}


