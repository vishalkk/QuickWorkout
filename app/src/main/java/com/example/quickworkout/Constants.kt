package com.example.quickworkout

object Constants {

    fun defaultExcerciseList() :ArrayList<ExcerciseModel>{
        val excerciseList = ArrayList<ExcerciseModel>()
        val jumpingJacks =ExcerciseModel(1,"Jumping Jacks",R.drawable.ic_jumping_jacks,false,false)
        excerciseList.add(jumpingJacks)
        val wallSit  =ExcerciseModel(2,"Wall Sit ",R.drawable.ic_wall_sit,false,false)
        excerciseList.add(wallSit)
        val pushUp  =ExcerciseModel(1,"Push Up",R.drawable.ic_push_up,false,false)
        excerciseList.add(pushUp)
        val abdominalCrunch  =ExcerciseModel(1,"Abdominal Crunch",R.drawable.ic_abdominal_crunch,false,false)
        excerciseList.add(abdominalCrunch )
        val stepUpOnChair  =ExcerciseModel(1,"Step up on chair",R.drawable.ic_step_up_onto_chair,false,false)
        excerciseList.add(stepUpOnChair )
        val tricepDipOnChair  =ExcerciseModel(1,"Tricep dip on chair",R.drawable.ic_triceps_dip_on_chair,false,false)
        excerciseList.add(tricepDipOnChair )
        val plank   =ExcerciseModel(1,"Plank ",R.drawable.ic_plank,false,false)
        excerciseList.add(plank )
        val highKneesRunningInPlace   =ExcerciseModel(1,"High knee running In Place",R.drawable.ic_high_knees_running_in_place,false,false)
        excerciseList.add(highKneesRunningInPlace  )
        val lunges   =ExcerciseModel(1,"Lunges ",R.drawable.ic_lunge,false,false)
        excerciseList.add(lunges  )
        val pushupAndRotation   =ExcerciseModel(1,"Push up and rotation",R.drawable.ic_push_up_and_rotation,false,false)
        excerciseList.add(pushupAndRotation  )
        val sidePlank   = ExcerciseModel(1,"Side plank",R.drawable.ic_side_plank,false,false)
        excerciseList.add(sidePlank  )

        return excerciseList
    }
}