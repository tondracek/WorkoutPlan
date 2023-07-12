package com.example.workoutplan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutplan.classes.Exercise
import com.example.workoutplan.classes.TimedSet
import com.example.workoutplan.classes.Training
import com.example.workoutplan.classes.WeightSet
import com.example.workoutplan.ui.ExerciseView
import com.example.workoutplan.ui.theme.WorkoutPlanTheme
import com.example.workoutplan.ui.theme.done_button
import com.example.workoutplan.ui.theme.on_done_button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingLayout(training: Training, returnFunction: (ArrayList<Exercise>) -> Unit) {
    val doneExercises = remember {
        mutableStateOf(0)
    }

    fun update() {
        doneExercises.value = training.exercises.count { it.isDone() }
    }

    fun isDone(): Boolean {
        return doneExercises.value == training.exercises.size
    }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = training.name)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isDone()) {
                        Button(
                            onClick = {
                                returnFunction(training.exercises)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = done_button)
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "DONE!",
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                color = on_done_button
                            )
                        }
                    } else {
                        Text(
                            text = "${doneExercises.value}/${training.exercises.size}",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            for (exercise in training.exercises) {
                ExerciseView(exercise = exercise) { update() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingLayoutPreview() {
    WorkoutPlanTheme {
        TrainingLayout(exampleTraining()) {}
    }
}

fun exampleTraining(): Training {
    val training = Training("Preview Training")

    training.addExercise(
        Exercise(name = "Pull ups")
            .addSet(WeightSet(0, 10))
            .addSet(WeightSet(0, 8))
            .addSet(WeightSet(0, 6))
    )

    training.addExercise(
        Exercise(name = "Bench")
            .addSet(WeightSet(100, 10))
            .addSet(WeightSet(120, 8))
            .addSet(WeightSet(120, 6))
    )
    training.addExercise(
        Exercise(name = "Plank")
            .addSet(TimedSet(3))
            .addSet(TimedSet(10))
            .addSet(TimedSet(60))
    )
    training.addExercise(
        Exercise(name = "Squat")
            .addSet(WeightSet(150, 10))
            .addSet(WeightSet(40, 8))
            .addSet(WeightSet(40, 6))
    )

    return training
}