package com.example.workoutplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutplan.ui.TrainingLayoutPreview
import com.example.workoutplan.ui.theme.WorkoutPlanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutPlanTheme {
                TrainingLayoutPreview()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    WorkoutPlanTheme {
        TrainingLayoutPreview()
    }
}