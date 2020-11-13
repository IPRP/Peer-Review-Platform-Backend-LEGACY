package com.iprp.backend.data.user

import org.springframework.data.annotation.Id

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
class Teacher(id: String, firstname: String, lastname: String) : Person(id, firstname, lastname)