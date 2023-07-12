package com.example.workoutplan

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutplan.classes.Training
import com.example.workoutplan.ui.theme.WorkoutPlanTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(trainingResultActivity: TrainingResultActivity) {
    val context = LocalContext.current
    val loadedTrainings: ArrayList<Training> = loadTrainings()
    val trainingWeek: Int = 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = colorScheme.primary,
        ) {
            Text(
                modifier = Modifier.padding(32.dp),
                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                textAlign = TextAlign.Center,
                text = "Training Week: $trainingWeek",
            )
        }
        Column(
            Modifier
                .fillMaxWidth(0.7f)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            for (training in loadedTrainings) {
                val isDone = remember {
                    mutableStateOf(training.isDone())
                }
                Card(
                    modifier = Modifier.padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.secondaryContainer,
                        contentColor = colorScheme.onSecondaryContainer
                    ),
                    onClick = {
                        val trainingIntent = Intent(context, TrainingActivity::class.java)
                        trainingIntent.putExtra("training", training)
                        context.startActivity(trainingIntent)

                        trainingResultActivity.launch(trainingIntent, training, isDone)
                    },
                    enabled = !isDone.value,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = training.name,
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.outline_play_circle_24),
                            contentDescription = "Start this training",
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier.padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.primary,
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Edit your Trainings",
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_add_circle_24),
                    contentDescription = "Edit your trainings",
                )
            }
        }
    }
}


fun loadTrainings(): ArrayList<Training> {
    return arrayListOf(exampleTraining(), exampleTraining(), exampleTraining())
}

@Preview
@Composable
fun MainLayoutPreview() {
    WorkoutPlanTheme {
        MainLayout(TrainingResultActivity(ComponentActivity()))
    }
}