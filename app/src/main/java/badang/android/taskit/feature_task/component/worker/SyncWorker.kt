package badang.android.taskit.feature_task.component.worker


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.io.IOException
import java.util.concurrent.TimeoutException

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            Result.success()
        } catch (e:IOException){
             Result.retry()
        } catch (e: TimeoutException){
             Result.retry()
        } catch (e: Exception) {
            Result.failure()
        }
    }


}