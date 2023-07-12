package com.example.workoutplan.classes

import java.io.Serializable

class Exercise(val name: String) : Serializable {
    val sets: ArrayList<ExerciseSet> = arrayListOf()

    fun isDone(): Boolean {
        return doneSets() == setsCount()
    }

    fun doneSets(): Int {
        return sets.stream().filter(ExerciseSet::done).count().toInt()
    }

    fun addSet(exerciseSet: ExerciseSet): Exercise {
        sets.add(exerciseSet)
        return this
    }

    fun setsCount(): Int = sets.size
}

data class ExerciseArrayList(val exercises: ArrayList<Exercise>) : Serializable