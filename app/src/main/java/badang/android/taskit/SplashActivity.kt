package badang.android.taskit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.HomeActivity
import badang.android.taskit.feature_task.data.local.data_store.DataPreferences

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isHomeStarted = DataPreferences.getData(Constant.Key.START_HOME)

     /*   if (isHomeStarted) {
            val intent = Intent(this, ToDoActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }*/

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }
}