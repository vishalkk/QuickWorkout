package com.example.quickworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.quickworkout.databinding.ActivityExcerciseBinding

class ExcerciseActivity : AppCompatActivity() {
    private var binding: ActivityExcerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var excerciseTimer: CountDownTimer? = null
    private var excerciseProgress = 0

    private var excerciseList :ArrayList<ExcerciseModel>?=null
    private var currentExcercisePosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        excerciseList= Constants.defaultExcerciseList()
        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }
        setupRestView()
    }
    private fun setupRestView(){

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExceciseName?.visibility = View.INVISIBLE
        binding?.flProgressBarExcercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility=View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.VISIBLE
        binding?.tvUpcomingExerciseName?.text =excerciseList!![currentExcercisePosition + 1].getName()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress =0
        }
        setRestProgressBar()
    }

    private fun setupExcerciseView(){
        binding?.flRestView?.visibility= View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExceciseName?.visibility = View.VISIBLE
        binding?.flProgressBarExcercise?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.upcomingLabel?.visibility=View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.INVISIBLE
        if(excerciseTimer != null){
            excerciseTimer?.cancel()
            excerciseProgress =0
        }
        binding?.ivImage?.setImageResource(excerciseList!![currentExcercisePosition].getImage())
        binding?.tvExceciseName?.text = excerciseList!![currentExcercisePosition].getName()

        setExcerciseProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress =restProgress
        restTimer = object:CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = ( 10-restProgress ).toString()

            }

            override fun onFinish() {
                currentExcercisePosition++
                setupExcerciseView()
            }
        }.start()
    }

    private fun setExcerciseProgressBar(){
        binding?.progressBarExcercise?.progress = excerciseProgress
        excerciseTimer = object:CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {

                excerciseProgress++
                binding?.progressBarExcercise?.progress = 30 - excerciseProgress
                binding?.tvTimerExcercise?.text = ( 30-excerciseProgress ).toString()

            }

            override fun onFinish() {

                if(currentExcercisePosition < excerciseList!!.size-1){
                    setupRestView()

                }else{
                    Toast.makeText(this@ExcerciseActivity,"Yeah!, You completed the Excercise",Toast.LENGTH_LONG)
                }
            }
        }.start()
    }
    override fun onDestroy(){
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress=0
        }
        if(excerciseTimer != null){
            excerciseTimer?.cancel()
            excerciseProgress=0
        }
        binding = null
//        currentExcercisePosition=0
    }

}

