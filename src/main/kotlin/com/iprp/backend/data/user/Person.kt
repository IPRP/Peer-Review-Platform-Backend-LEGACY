package com.iprp.backend.data.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-05
 */

@Document(collection = "person")
open class Person(
    @Id
    val id: String,
    val firstname: String,
    val lastname: String
)