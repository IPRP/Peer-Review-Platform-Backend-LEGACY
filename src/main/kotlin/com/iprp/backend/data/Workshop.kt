package com.iprp.backend.data

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

/**
 * Represents a Workshop.
 * Workshops consists of multiple [SubmissionRound]s.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-19
 */
class Workshop(
    var title: String,
    var content: String,
    var end: LocalDateTime,
    val anonymous: Boolean,

    val students: MutableList<String>, // Student
    val teachers: MutableList<String>, // Teacher
    var criteria: String, // ReviewCriteria
) {
    @Id
    lateinit var id: String
        private set


    /**
    fun addStudent(studentId: String) {
        students.add(studentId)
    }

    fun removeStudent(studentId: String) {
        students.remove(studentId)
    }*/
}