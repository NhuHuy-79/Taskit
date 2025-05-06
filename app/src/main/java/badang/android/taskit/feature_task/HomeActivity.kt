package badang.android.taskit.feature_task

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import badang.android.taskit.ToDoActivity
import badang.android.taskit.core.Constant
import badang.android.taskit.databinding.ActivityHomeBinding
import badang.android.taskit.feature_task.data.local.data_store.DataPreferences

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnStart = binding.btnGetStarted
        btnStart.setOnClickListener {
            val intent = Intent(this, ToDoActivity::class.java )
            startActivity(intent)
            DataPreferences.saveData(Constant.Key.START_HOME, true)
            finish()
        }
    }
}