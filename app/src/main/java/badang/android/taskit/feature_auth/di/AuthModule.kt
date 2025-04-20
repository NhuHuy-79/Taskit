package badang.android.taskit.feature_auth.di

import badang.android.taskit.feature_auth.data.repository.AuthRepositoryImp
import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

/*
@Module
@InstallIn(Singleton::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImp(firebaseAuth, firestore)
    }

}*/
