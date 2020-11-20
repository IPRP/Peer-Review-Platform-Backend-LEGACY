package com.iprp.backend.data.repos

import com.iprp.backend.data.Review
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-16
 */
interface ReviewRepository : MongoRepository<Review, String> {
    fun findByStudentId(student_id: String): List<Review>
}