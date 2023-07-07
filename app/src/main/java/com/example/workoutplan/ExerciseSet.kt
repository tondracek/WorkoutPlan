package com.example.workoutplan

interface ExerciseSet {
    var done: Boolean
}

class WeightSet(val weight: Int, val reps: Int, val unit: String = "Kg") : ExerciseSet {
    override var done: Boolean = false
}

class TimedSet(val seconds: Int) : ExerciseSet {
    override var done: Boolean = false
}