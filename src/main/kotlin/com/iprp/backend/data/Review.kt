package com.iprp.backend.data

import com.iprp.backend.data.user.Student
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
class Review(
    @DBRef
    val student: Student,
) {
    @Id
    lateinit var id: String
        private set
}