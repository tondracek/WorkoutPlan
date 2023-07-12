package com.example.workoutplan

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutplan.classes.ExerciseArrayList
import com.example.workoutplan.classes.Training
import com.example.workoutplan.ui.theme.WorkoutPlanTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val trainingResultActivity = TrainingResultActivity(this)

        setContent {
            WorkoutPlanTheme {
                MainLayout(trainingResultActivity)
            }
        }
    }
}

class TrainingResultActivity(parentActivity: ComponentActivity) {
    private var training: Training? = null
    private var isDoneMutableState: MutableState<Boolean>? = null

    private val trainingResultActivity = parentActivity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        run {
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val updatedExercisesArrayList: ExerciseArrayList? =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            data.getSerializableExtra(
                                "updatedExercises",
                                ExerciseArrayList::class.java
                            )
                        } else {
                            data.getSerializableExtra("updatedExercises") as ExerciseArrayList
                        }

                    if (updatedExercisesArrayList != null && training != null) {
                        val updatedExercises = updatedExercisesArrayList.exercises

                        training!!.exercises = updatedExercises
                        isDoneMutableState!!.value = training!!.isDone()
                    }
                }
            }
        }
    }

    fun launch(intent: Intent, training: Training, isDoneMutableState: MutableState<Boolean>) {
        this.training = training
        this.isDoneMutableState = isDoneMutableState

        trainingResultActivity.launch(intent)
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    WorkoutPlanTheme {
        MainLayoutPreview()
    }
}