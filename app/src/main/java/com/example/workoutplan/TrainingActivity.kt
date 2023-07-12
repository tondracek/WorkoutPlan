package com.example.workoutplan

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutplan.classes.Exercise
import com.example.workoutplan.classes.ExerciseArrayList
import com.example.workoutplan.classes.Training
import com.example.workoutplan.ui.theme.WorkoutPlanTheme

class TrainingActivity : ComponentActivity() {
    private fun returnToMainLayout(updatedExercises: ArrayList<Exercise>) {
        val resultIntent = Intent()
        resultIntent.putExtra("updatedExercises", ExerciseArrayList(updatedExercises))
        setResult(Activity.RESULT_OK, resultIntent)

        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val training: Training = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("training", Training::class.java)!!
        } else {
            intent.getSerializableExtra("training") as Training
        }

        setContent {
            WorkoutPlanTheme {
                TrainingLayout(training = training, returnFunction = ::returnToMainLayout)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTrainingActivity() {
    WorkoutPlanTheme {
        TrainingLayoutPreview()
    }
}