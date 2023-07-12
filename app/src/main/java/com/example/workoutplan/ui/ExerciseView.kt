package com.example.workoutplan.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workoutplan.classes.Exercise
import com.example.workoutplan.classes.TimedSet
import com.example.workoutplan.classes.WeightSet
import com.example.workoutplan.ui.theme.WorkoutPlanTheme

@Composable
fun ExerciseView(exercise: Exercise, updateParent: () -> Unit) {
    val expanded = remember {
        mutableStateOf(true)
    }
    val doneCount = remember {
        mutableStateOf(0)
    }

    fun update() {
        // TODO: more effectively using boolean prevState and less updateParent()
        doneCount.value = exercise.doneSets()
        updateParent()

        if (exercise.isDone()) {
            expanded.value = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            Modifier.background(MaterialTheme.colorScheme.primaryContainer),
        ) {
            ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) {
                ExerciseHeader(
                    name = exercise.name,
                    setsCount = exercise.setsCount(),
                    modifier = Modifier.clickable { expanded.value = !expanded.value },
                    doneCount = doneCount
                )
                AnimatedVisibility(visible = expanded.value) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for (set in exercise.sets) {
                            SetView(exerciseName = exercise.name, set = set, ::update)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseHeader(
    name: String, setsCount: Int, modifier: Modifier = Modifier, doneCount: MutableState<Int>
) {
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                Text(text = name, modifier = Modifier.weight(1f))
                Text(text = "${doneCount.value}/$setsCount")
            }
        }
    }
}

@Preview
@Composable
fun ExerciseUIPreview() {

    WorkoutPlanTheme {
        val exercise = Exercise("Exercise")
            .addSet(WeightSet(20, 8))
            .addSet(WeightSet(15, 8))
            .addSet(TimedSet(90))

        ExerciseView(exercise = exercise) {}
    }
}