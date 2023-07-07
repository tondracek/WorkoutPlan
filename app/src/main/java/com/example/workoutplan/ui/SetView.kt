package com.example.workoutplan.ui

import android.content.Context
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutplan.ExerciseSet
import com.example.workoutplan.R
import com.example.workoutplan.TimedSet
import com.example.workoutplan.WeightSet
import com.example.workoutplan.ui.theme.done_button
import com.example.workoutplan.ui.theme.on_done_button
import kotlin.math.roundToInt

@Composable
fun SetView(exerciseName: String, set: ExerciseSet, updateParent: () -> Unit) {
    val doneState: MutableState<Boolean> = remember {
        mutableStateOf(set.done)
    }

    val showTimePopup = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (set is WeightSet) {
            Text(text = "Weight: ${set.weight}")
            Text(text = "Reps: ${set.reps}")
        } else if (set is TimedSet) {
            Text(text = "Time: ${set.seconds}")
            IconButton(onClick = {
                showTimePopup.value = !showTimePopup.value
            }) {
                Icon(
                    ImageVector.vectorResource(
                        id = R.drawable.outline_timer_24
                    ),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = "Open timer for this set."
                )
            }
        }
        Checkbox(
            checked = doneState.value,
            onCheckedChange = {
                doneState.value = it
                set.done = it
                updateParent()
            },
        )
    }

    if (showTimePopup.value && set is TimedSet) {
        TimePopup(
            exerciseName = exerciseName,
            timedSet = set,
            doneState = doneState,
            showPopup = showTimePopup
        )
    }
}

@Composable
fun TimePopup(
    exerciseName: String,
    timedSet: TimedSet,
    doneState: MutableState<Boolean>,
    showPopup: MutableState<Boolean>
) {
    val time = remember {
        mutableStateOf(timedSet.seconds * 1000L)
    }

    var timerState = remember {
        mutableStateOf(TimerState.NOT_STARTED)
    }

    val timer = object : CountDownTimer(time.value, 500) {
        override fun onTick(remainingTime: Long) {
            time.value = remainingTime
        }

        override fun onFinish() {
            timerState.value = TimerState.FINISHED
        }
    }

    AlertDialog(
        title = {
            Text(text = exerciseName)
        },
        onDismissRequest = {
            showPopup.value = false
        },
        dismissButton = {
            Button(
                onClick = { showPopup.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                )
            ) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.onError)
            }
        },
        confirmButton = {
            Button(
                onClick = { doneState.value = true; showPopup.value = false },
                enabled = timerState.value == TimerState.FINISHED,
                colors = ButtonDefaults.buttonColors(containerColor = done_button)
            ) {
                Text(text = "Done", color = on_done_button)
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = formatSeconds((time.value / 1000f).roundToInt()),
                    fontSize = 30.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Button(
                    onClick = {
                        timer.start()
                        timerState.value = TimerState.GOING
                    },
                    enabled = timerState.value == TimerState.NOT_STARTED,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Start the timer",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}

fun formatSeconds(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

enum class TimerState {
    NOT_STARTED,
    GOING,
    FINISHED
}

fun vibratePhone(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    if (vibrator?.hasVibrator() == true) {
        val effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(effect)
    }
}