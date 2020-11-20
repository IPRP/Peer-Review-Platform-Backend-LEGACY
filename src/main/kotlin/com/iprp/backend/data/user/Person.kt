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
    id: String,
    val firstname: String,
    val lastname: String
) {
    @Id
    var id: String = id
        private set
}