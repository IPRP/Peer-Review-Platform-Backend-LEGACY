package com.iprp.backend.data

import com.iprp.backend.data.grade.Grade
import com.iprp.backend.data.submission.SubmissionRound
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

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
    @DBRef var students: MutableList<Student>,
    @DBRef var teachers: MutableList<Teacher>,
    @DBRef var rounds: MutableList<SubmissionRound>,
    @DBRef var grades: MutableList<Grade>
) {
    @Id
    lateinit var id: String
        private set


    fun addStudent(student: Student) {
        students.add(student)
    }

    fun removeStudent(student: Student) {
        students.remove(student)
    }
}