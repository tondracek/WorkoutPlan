package com.example.workoutplan

class Exercise {
    val name: String
    val sets: ArrayList<ExerciseSet>

    constructor(name: String) {
        this.name = name
        sets = arrayListOf()
    }

    constructor(name: String, sets: ArrayList<ExerciseSet>) {
        this.name = name
        this.sets = sets
    }

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
