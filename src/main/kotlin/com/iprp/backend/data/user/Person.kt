package com.iprp.backend.data.user

import org.springframework.data.annotation.Id

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
open class Person(
    @Id
    val id: String,
    val firstname: String,
    val lastname: String
)