package badang.android.taskit.feature_auth.data.repository

import badang.android.taskit.core.Constant
import badang.android.taskit.feature_auth.domain.model.User
import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import badang.android.taskit.feature_auth.domain.model.AuthResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout


class AuthRepositoryImp(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {

    private val timeOutRequest: Long = 5000

    override fun getUser(): Flow<User?> = flow {
        try {
            withTimeout(timeOutRequest) {
                val firebaseUser = auth.currentUser
                if (firebaseUser == null) {
                    emit(null)
                    return@withTimeout
                }

                val userDocument = db.collection(Constant.FireStore.COLLECTION_USER)
                    .document(firebaseUser.uid)
                    .get()
                    .await()

                if (!userDocument.exists()) {
                    emit(null)
                    return@withTimeout
                }

                val user = userDocument.toObject(User::class.java)
                emit(user)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }


    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            withTimeout(timeOutRequest){
                auth.signInWithEmailAndPassword(email, password).await()
            }
            AuthResult.Success
        } catch (ex: FirebaseAuthInvalidUserException) {
            AuthResult.Failure(AuthResult.Error.USER_NOT_EXIST)
        } catch (ex: FirebaseAuthInvalidCredentialsException) {
            AuthResult.Failure(AuthResult.Error.INCORRECT_EMAIL_OR_PASSWORD)
        } catch (ex: FirebaseNetworkException) {
            AuthResult.Failure(AuthResult.Error.NETWORK_ERROR)
        } catch (e:TimeoutCancellationException ){
            AuthResult.Failure(AuthResult.Error.TIME_OUT)
        } catch (ex: Exception) {
            AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
        }
    }


    override suspend fun register(
        email: String,
        name: String,
        password: String,
        confirm: String
    ): AuthResult {
        return try {
            withTimeout(timeOutRequest) {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser =
                    result.user ?: return@withTimeout AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)

                /*Get user credential from Firebase user to FireStore - collection users - document uid*/
                val user = User(name = name, email = email)
                db.collection(Constant.FireStore.COLLECTION_USER)
                    .document(firebaseUser.uid)
                    .set(user)
            }

            AuthResult.Success

        } catch (ex: FirebaseAuthUserCollisionException) {
            AuthResult.Failure(AuthResult.Error.USER_ALREADY_EXISTS)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            AuthResult.Failure(AuthResult.Error.INVALID_EMAIL)
        } catch (e: FirebaseNetworkException) {
            AuthResult.Failure(AuthResult.Error.NETWORK_ERROR)
        } catch (e:TimeoutCancellationException ){
            AuthResult.Failure(AuthResult.Error.TIME_OUT)
        } catch (e: Exception) {
            AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
        }
    }

    override suspend fun sendResetEmail(email: String): AuthResult {
        return try {
            withTimeout(timeOutRequest) {
                auth.sendPasswordResetEmail(email).await()
            }
            AuthResult.Success

        } catch (e:TimeoutCancellationException ){
            AuthResult.Failure(AuthResult.Error.TIME_OUT)
        } catch (ex: Exception) {
            ex.printStackTrace()
            AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
        }
    }


    override suspend fun changePassword(password: String): AuthResult {
        return try {
            withTimeout(timeOutRequest){
                val user = auth.currentUser ?: return@withTimeout AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
                user.updatePassword(password)
            }
            AuthResult.Success
        } catch (e:TimeoutCancellationException ){
            AuthResult.Failure(AuthResult.Error.TIME_OUT)
        }  catch (ex: Exception) {
            AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
        }
    }

    override suspend fun signOut(): AuthResult {
        return try {
            auth.signOut()
            AuthResult.Success
        } catch (e: TimeoutCancellationException) {
            AuthResult.Failure(AuthResult.Error.TIME_OUT)
        } catch (ex: Exception) {
            AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
        }
    }

}