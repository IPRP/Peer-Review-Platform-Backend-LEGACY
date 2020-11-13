package com.iprp.backend.data.repos

import com.iprp.backend.data.user.Teacher
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-05
 */
interface TeacherRepository : MongoRepository<Teacher, String>