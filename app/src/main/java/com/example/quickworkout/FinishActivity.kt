package com.example.quickworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quickworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private var binding:ActivityFinishBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //to add custom app bar in our activity
        setSupportActionBar(binding?.toolbarFinishActivity)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener{
            finish()
        }
    }
}