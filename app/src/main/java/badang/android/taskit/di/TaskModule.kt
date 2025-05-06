package badang.android.taskit.di

import android.content.Context
import androidx.room.Room
import badang.android.taskit.feature_task.data.local.room.MIGRATION_1_2
import badang.android.taskit.feature_task.data.local.room.MIGRATION_2_3
import badang.android.taskit.feature_task.data.local.room.TaskDao
import badang.android.taskit.feature_task.data.local.room.TaskDatabase
import badang.android.taskit.feature_task.data.repository.TaskRepositoryImp
import badang.android.taskit.feature_task.domain.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideRepository(
        auth: FirebaseAuth,
        taskDao: TaskDao,
        db:  FirebaseFirestore
    ): TaskRepository {
        return TaskRepositoryImp(auth, taskDao, db)
    }

    @Provides
    @Singleton
    fun provideDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task.db"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()

    }
}