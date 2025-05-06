package badang.android.taskit.di

import badang.android.taskit.feature_auth.data.repository.AuthRepositoryImp
import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepositoryImp(
       firebaseAuth: FirebaseAuth,
       firestore: FirebaseFirestore
    ):AuthRepository {
        return AuthRepositoryImp(firebaseAuth, firestore)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore() = FirebaseFirestore.getInstance()

}