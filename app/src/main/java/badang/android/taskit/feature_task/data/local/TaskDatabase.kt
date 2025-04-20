package badang.android.taskit.feature_task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TaskEntity::class], exportSchema = false, version = 2)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE task_new (
                id TEXT PRIMARY KEY NOT NULL,
                content TEXT NOT NULL,
                time INTEGER NOT NULL, 
                isDone INTEGER NOT NULL, 
                isEnabled INTEGER NOT NULL,  
                isSynced INTEGER NOT NULL  
            )
        """)

        db.execSQL("""
            INSERT INTO task_new (id, content, time, isDone, isEnabled, isSynced)
            SELECT id, content, time, isDone, isHide, isSynced FROM task
        """)

        db.execSQL("DROP TABLE task")
        db.execSQL("ALTER TABLE task_new RENAME TO task")
    }
}

