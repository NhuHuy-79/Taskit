package badang.android.taskit.feature_task.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TaskEntity::class], exportSchema = false, version = 3)
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

val MIGRATION_2_3 = object : Migration(2,3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE task_new (
                id TEXT PRIMARY KEY NOT NULL,
                content TEXT NOT NULL,
                time INTEGER, 
                isDone INTEGER NOT NULL,
                isAlarmed INTEGER NOT NULL,
                isSynced INTEGER NOT NULL
            )
        """)

        db.execSQL("""
            INSERT INTO task_new (id, content, time, isDone, isAlarmed, isSynced)
            SELECT id, content, time, isDone, isEnabled, isSynced FROM task
        """)

        db.execSQL("DROP TABLE task")
        db.execSQL("ALTER TABLE task_new RENAME TO task")
    }

}

