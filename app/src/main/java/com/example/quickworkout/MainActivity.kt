package com.example.quickworkout

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quickworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding? = null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val fLStartButton: FrameLayout = findViewById(R.id.flStart)
        binding?.flStart?.setOnClickListener {
          val intent = Intent(this@MainActivity,ExcerciseActivity::class.java)
            startActivity(intent)
        }
        binding?.flBMI?.setOnClickListener {
            // Launching the BMI Activity
            val intent = Intent(this@MainActivity, BMI::class.java)
            startActivity(intent)
        }
        binding?.flHistory?.setOnClickListener {
            // Launching the History Activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}