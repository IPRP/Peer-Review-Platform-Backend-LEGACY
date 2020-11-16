package com.iprp.backend.data.user

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document


/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
@Document(collection = "person")
@TypeAlias("student")
class Student(id: String, firstname: String, lastname: String, var group: String) : Person(id, firstname, lastname)