package badang.android.taskit.feature_task.utils

import android.icu.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object AlarmUtils {

    fun convertToLong(hour: Int, minute: Int): Long? {
        if (hour == -1 || minute == -1) return null

        val now = Calendar.getInstance()
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (hour < now.get(Calendar.HOUR_OF_DAY) ||
            (hour == now.get(Calendar.HOUR_OF_DAY) && minute <= now.get(Calendar.MINUTE))) {
            alarmTime.add(Calendar.DAY_OF_YEAR, 1)
        }

        return alarmTime.timeInMillis
    }

    fun convertToString(alarmTime: Long?): String{
        if (alarmTime != null) {
            val date = Date(alarmTime)
            val alarmString = SimpleDateFormat("HH:mm", Locale.getDefault())
            return alarmString.format(date)
        }

        else return ""
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