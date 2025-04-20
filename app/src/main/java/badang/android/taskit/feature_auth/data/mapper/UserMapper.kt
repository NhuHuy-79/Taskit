package badang.android.taskit.feature_auth.data.mapper

import badang.android.taskit.feature_auth.domain.model.User
import com.google.firebase.auth.FirebaseUser


fun FirebaseUser.toDomain(): User = User(
    uid = this.uid,
    email = this.email ?: "unknown@email.com"
)