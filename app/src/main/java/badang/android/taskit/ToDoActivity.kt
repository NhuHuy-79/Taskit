package badang.android.taskit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import badang.android.taskit.databinding.ActivityTodoBinding

class ToDoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}