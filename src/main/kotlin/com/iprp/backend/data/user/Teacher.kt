package com.iprp.backend.data.user

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Represents a student in the Peer Review system.
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
@Document(collection = "person")
@TypeAlias("teacher")
class Teacher(id: String, firstname: String, lastname: String) : Person(id, firstname, lastname)