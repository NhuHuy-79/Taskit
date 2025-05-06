package badang.android.taskit.core

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import badang.android.taskit.R
import com.google.android.material.snackbar.Snackbar

object AppHelper {

    fun showToast(context: Context, @StringRes res: Int?= null, message: String? = null) {
        if (res != null) {
            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
        }

        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showSnackBar(context: Context? = null, view: View, @StringRes res: Int) {
        Snackbar.make(view, res, 800).setBackgroundTint(
            context?.getColor(R.color.color_primary) ?: 0
        ).setTextColor(
            context?.getColor(R.color.white) ?:0
        )
            .show()

    }

    fun showSnackBar(context: Context? = null, view: View, message: String) {
        Snackbar.make(view, message, 600).setBackgroundTint(
            context?.getColor(R.color.black) ?: 0
        ).setTextColor(
            context?.getColor(R.color.white) ?:0
        )
            .show()

    }

    fun printLog(tag: String, message: String){
        Log.d(tag, message)
    }

}