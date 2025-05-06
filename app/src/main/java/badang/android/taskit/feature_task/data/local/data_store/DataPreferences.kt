package badang.android.taskit.feature_task.data.local.data_store

import android.content.Context
import android.content.SharedPreferences
import badang.android.taskit.core.Constant


object DataPreferences{
    private lateinit var startedData: SharedPreferences
    private lateinit var syncList: SharedPreferences

    private val taskIdSet:Set<String> = emptySet()

    fun addTask(taskId: String){
        val newSet = taskIdSet.toMutableSet()
        newSet.add(taskId)
        syncList.edit().putStringSet(Constant.Key.SYNC_TASKS, newSet).apply()
    }

    fun getList() = syncList.getStringSet(Constant.Key.SYNC_TASKS, emptySet())?.toMutableList()

    fun init(context: Context){
        startedData = context.getSharedPreferences(Constant.Key.START_HOME, Context.MODE_PRIVATE)
        syncList = context.getSharedPreferences(Constant.Key.SYNC_TASKS, Context.MODE_PRIVATE)
    }

    fun saveData(key: String, value: Boolean){
        startedData.edit().putBoolean(key, value).apply()
    }

    fun getData(key: String) : Boolean {
        return startedData.getBoolean(key, false)
    }
}