package com.iprp.backend.data.repos

import com.iprp.backend.data.user.Student
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-05
 */
interface StudentRepository : MongoRepository<Student, String>