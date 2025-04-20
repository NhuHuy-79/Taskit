package badang.android.taskit.common

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

object UiHelper {

    fun showToast(context: Context, @StringRes res: Int) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, @StringRes res: Int) {
        Snackbar.make(view, res, Snackbar.LENGTH_SHORT).show()

    }

}