package badang.android.taskit.feature_task.utils

import android.icu.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object AlarmUtils {

    fun convertToLong(hour: Int, minute: Int): Long {
        if (hour == -1 || minute == -1) {
            return -1L
        } else {
            val moment = Calendar.getInstance()
            val alarmTime = moment.apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (alarmTime.before(moment)){
                alarmTime.add(Calendar.DAY_OF_YEAR, 1)
            }

            return alarmTime.timeInMillis
        }
    }

    fun convertToString(alarmTime: Long): String{
        val date = Date(alarmTime)
        val alarmString = SimpleDateFormat("HH:mm", Locale.getDefault())
        return alarmString.format(date)
    }

    fun snoozeTime(minute:Int = 5): Long{
        val moment = Calendar.getInstance()
        val alarmTime = moment.apply {
            set(Calendar.SECOND,0)
            set(Calendar.MINUTE,minute)
        }

        if (alarmTime.before(moment)) {
            alarmTime.add(Calendar.DAY_OF_YEAR,1)
        }

        return alarmTime.timeInMillis
    }

}