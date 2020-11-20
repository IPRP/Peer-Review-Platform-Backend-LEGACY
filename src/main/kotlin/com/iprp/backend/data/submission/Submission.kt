package com.iprp.backend.data.submission

import org.springframework.data.annotation.Id

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class Submission {
    @Id
    lateinit var id: String
        private set
}