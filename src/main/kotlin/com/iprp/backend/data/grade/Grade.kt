package com.iprp.backend.data.grade

import org.springframework.data.annotation.Id

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class Grade {
    @Id
    lateinit var id: String
        private set
}