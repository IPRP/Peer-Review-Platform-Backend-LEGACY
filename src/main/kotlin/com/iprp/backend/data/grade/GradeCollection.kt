package com.iprp.backend.data.grade

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.user.Student
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 * Used to retrieve the overall grade of a student from a workshop.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class GradeCollection(
    var grade: Int?,

    val grades: MutableList<String>, // Grade
    val student: String, // Student
    val workshop: String, // Workshop
) {
    @Id
    lateinit var id: String
        private set
}