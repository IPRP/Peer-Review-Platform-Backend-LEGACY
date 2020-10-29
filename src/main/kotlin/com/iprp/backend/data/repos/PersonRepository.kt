package com.iprp.backend.data.repos

import com.iprp.backend.data.user.Person
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
interface PersonRepository : MongoRepository<Person, String> {
    fun findByFirstname(firstname: String)
}