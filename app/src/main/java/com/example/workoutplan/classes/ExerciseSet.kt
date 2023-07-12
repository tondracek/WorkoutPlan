package com.example.workoutplan.classes

import java.io.Serializable

interface ExerciseSet: Serializable {
    var done: Boolean
}

class WeightSet(val weight: Int, val reps: Int, val unit: String = "Kg") : ExerciseSet {
    override var done: Boolean = false
}

class TimedSet(val seconds: Int) : ExerciseSet {
    override var done: Boolean = false
}