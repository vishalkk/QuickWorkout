package com.example.quickworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.quickworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
        //get the dao through the database in the application class
        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }
    /**
     * Function is used to insert the current system date in the sqlite database.
     */
    private  fun addDateToDatabase(historyDao: HistoryDao){
            val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
        }
    }
}