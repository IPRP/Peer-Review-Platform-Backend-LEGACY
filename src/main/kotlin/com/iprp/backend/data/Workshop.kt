package com.iprp.backend.data

import com.iprp.backend.data.user.Student
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-19
 */
class Workshop(@DBRef var students: MutableList<Student>) {

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun removeStudent(student: Student) {
        students.remove(student)
    }
}