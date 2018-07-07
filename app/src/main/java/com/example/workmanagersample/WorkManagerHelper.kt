package com.example.workmanagersample

import android.arch.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkStatus
import com.example.workmanagersample.workers.BrushTeethWorker
import com.example.workmanagersample.workers.FlossWorker
import java.util.concurrent.TimeUnit

private const val BRUSH_TEETH_NAME = "Brush"
private const val FLOSS_NAME = "Floss"

class WorkManagerHelper(private val workManager: WorkManager) {

    fun brushTeethPeriodically() { // hygiene is important :)
        workManager
            .enqueueUniquePeriodicWork(
                BRUSH_TEETH_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                createBrushTeethRequest()
            )
    }

    fun stopBrushingTeethPeriodically() {
        workManager.cancelUniqueWork(BRUSH_TEETH_NAME)
    }

    fun getBrushTeethStatus(): LiveData<List<WorkStatus>> =
        workManager.getStatusesForUniqueWork(BRUSH_TEETH_NAME)

    fun flossOneTime() {
        val flossRequest = createFlossRequest()
        workManager
            .beginUniqueWork(FLOSS_NAME, ExistingWorkPolicy.KEEP, flossRequest)
            .enqueue()
    }

    fun getFlossingStatus(): LiveData<List<WorkStatus>> =
        workManager.getStatusesForUniqueWork(FLOSS_NAME)

    fun prune() = workManager.pruneWork()

    fun cancelAll() = workManager.cancelAllWork()
}

private fun createBrushTeethRequest(): PeriodicWorkRequest {
    val constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()

    return PeriodicWorkRequestBuilder<BrushTeethWorker>(12, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()
}

private fun createFlossRequest(): OneTimeWorkRequest {
    val constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()

    return OneTimeWorkRequestBuilder<FlossWorker>()
        .setConstraints(constraints)
        .build()
}
