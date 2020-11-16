package com.iprp.backend.data

import org.springframework.data.annotation.Id

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
class Review(
    @Id
    val id: String,
    val title: String,
)