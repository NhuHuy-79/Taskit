package badang.android.taskit.feature_auth.data.repository

import badang.android.taskit.feature_auth.domain.model.User
import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import badang.android.taskit.feature_auth.utils.AuthResult
import badang.android.taskit.feature_auth.utils.DbConstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await



class AuthRepositoryImp (
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {

    override suspend fun getUser(): User? {
        val firebaseUser = auth.currentUser ?: return null
        val userDocument = db.collection(DbConstant.COLLECTION_USERS)
            .document(firebaseUser.uid)
            .get()
            .await()

        if (!userDocument.exists()) {
            return null
        }

        val user = userDocument.toObject(User::class.java) ?.copy(
            uid = firebaseUser.uid
        ) ?: return null

        return user
    }

    override suspend fun signInWithFirebase(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success("Sign In successfully!")
        } catch (ex: Exception){
            AuthResult.Failure(ex)
        }
    }

    override suspend fun signUpWithFirebase(
        email: String,
        name: String,
        password: String
    ): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return AuthResult.Failure(Exception("User is null!"))

            val user = User(uid = firebaseUser.uid, name = name, email = email)
            db.collection(DbConstant.COLLECTION_USERS)
                .document(user.uid)
                .set(user)
                .await()

            AuthResult.Success("Login Successfully")
        }catch (ex: Exception) {
            AuthResult.Failure(ex)
        }
    }

    override suspend fun sendResetEmail(email: String): AuthResult {
       return try {
           val result = auth.sendPasswordResetEmail(email)
           /* Need to be reminded*/
           return AuthResult.Success("Successfully")
       } catch (ex:Exception){
           AuthResult.Failure(ex)
       }
    }

    override suspend fun resetPassword(password: String): AuthResult {
        return try {
            val user = auth.currentUser ?: return AuthResult.Failure(Exception("User not found!"))
            user.updatePassword(password)
            AuthResult.Success("Successfully")
        } catch (ex: Exception) {
            AuthResult.Failure(ex)
        }
    }

    override fun signOut(): AuthResult {
        return try {
            auth.signOut()
            AuthResult.Success("Sign out successfully!")
        } catch (ex: Exception) {
            AuthResult.Failure(ex)
        }
    }


}