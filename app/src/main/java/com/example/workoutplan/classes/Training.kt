package com.example.workoutplan.classes

import java.io.Serializable

class Training(val name: String) : Serializable {
    var exercises: ArrayList<Exercise> = arrayListOf()

    fun addExercise(exercise: Exercise): Training {
        exercises.add(exercise)
        return this
    }

    fun isDone(): Boolean {
        return exercises.all(Exercise::isDone)
    }
}