package badang.android.taskit.feature_auth.domain.user_case

import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckUserUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke(): Flow<badang.android.taskit.feature_auth.domain.model.User?> {
        return repository.getUser()
    }
}