package com.example.quickworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickworkout.databinding.ActivityExcerciseBinding
import com.example.quickworkout.databinding.DialogCustomeBackConformationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExcerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExcerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var excerciseTimer: CountDownTimer? = null
    private var excerciseProgress = 0
    private var restTimerDuration:Long = 10
    private var excerciseTimerDuration:Long= 30

    private var excerciseList :ArrayList<ExcerciseModel>?=null
    private var currentExcercisePosition = -1
    private var tts:TextToSpeech? = null
    private var player:MediaPlayer? = null


    private var excerciseAdapter:ExcerciseStatusAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //to add custom app bar in our activity
        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener{
            customDialogForBackButton()
        }
        tts = TextToSpeech(this,this)
        excerciseList= Constants.defaultExcerciseList()

        setupRestView()
        setupExcerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
//        super.onBackPressed()


    }
    private fun customDialogForBackButton(){
        Log.i("Custom dialog","CUTOM DIALOG OPEN")
        val customDialog = Dialog(this)
        val dialogBinding =DialogCustomeBackConformationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{
            this@ExcerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }
    private fun setupExcerciseStatusRecyclerView(){
    binding?.rvExerciseStatus?.layoutManager =LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    excerciseAdapter = ExcerciseStatusAdapter(excerciseList!!)
    binding?.rvExerciseStatus?.adapter = excerciseAdapter
    }
    //show rest view
    //rest for 10 seconds
    //show upcoming excercise
    private fun setupRestView(){

    try {
    val soundURI= Uri.parse("android.resource://com.example.quickworkout/"+R.raw.app_src_main_res_raw_press_start )
    player = MediaPlayer.create(applicationContext,soundURI)
    player?.isLooping = false
    player?.start()
} catch (e:Exception){
    e.printStackTrace()
}
        Thread.sleep(1000)

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExceciseName?.visibility = View.INVISIBLE
        binding?.flProgressBarExcercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility=View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.VISIBLE
        binding?.tvUpcomingExerciseName?.text =excerciseList!![currentExcercisePosition + 1].getName()
        speakOut("TAKE A REST FOR TEN SECONDS")


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

        speakOut("GET READY FOR "+excerciseList!![currentExcercisePosition].getName())
        binding?.ivImage?.setImageResource(excerciseList!![currentExcercisePosition].getImage())
        binding?.tvExceciseName?.text = excerciseList!![currentExcercisePosition].getName()

        setExcerciseProgressBar()
    }

    private fun setRestProgressBar(){
        Thread.sleep(2000)

        binding?.progressBar?.progress =restProgress
        restTimer = object:CountDownTimer(restTimerDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = ( 10-restProgress ).toString()
                speakOut(binding?.tvTimer?.text.toString())

            }

            override fun onFinish() {
                currentExcercisePosition++
                //when rest time finish
                excerciseList!![currentExcercisePosition].setIsSelected(true)
                excerciseAdapter!!.notifyDataSetChanged()


                setupExcerciseView()
            }
        }.start()
    }

    private fun setExcerciseProgressBar(){
        Thread.sleep(3000)
        binding?.progressBarExcercise?.progress = excerciseProgress
        excerciseTimer = object:CountDownTimer((excerciseTimerDuration*1000).toLong(),1000){
            override fun onTick(millisUntilFinished: Long) {

                excerciseProgress++
                binding?.progressBarExcercise?.progress = 30 - excerciseProgress
                binding?.tvTimerExcercise?.text = ( 30-excerciseProgress ).toString()
                speakOut(binding?.tvTimerExcercise?.text.toString())


            }

            override fun onFinish() {



                if(currentExcercisePosition < excerciseList!!.size-1){
                    //when excercise finish
                    excerciseList!![currentExcercisePosition].setIsSelected(false)
                    excerciseList!![currentExcercisePosition].setIsCompleted(true)

                    excerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()

                }else{
                    finish()
                    Toast.makeText(this@ExcerciseActivity,"Yeah!, You completed the Excercise",Toast.LENGTH_LONG)
                    val intent = Intent(this@ExcerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
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


        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        binding = null
//        currentExcercisePosition=0


if(player != null){
    player!!.stop()
}
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){

            val result = tts?.setLanguage(Locale.US)

            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED
            ){
                Log.e("TTS","The language specified is not supported")

            }
        }else{
            Log.e("TTS","Initialization failed!")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}

