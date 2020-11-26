package com.iprp.backend.data

import com.iprp.backend.data.grade.GradeCollection
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.SubmissionRound
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
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
    var roundEnd: LocalDateTime,
    val anonymous: Boolean,

    val students: MutableList<String>, // Student
    val teachers: MutableList<String>, // Teacher
    var criteria: String, // ReviewCriteria
    val rounds: MutableList<String>, // SubmissionRound
    var grades: MutableList<String> // GradeCollection
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