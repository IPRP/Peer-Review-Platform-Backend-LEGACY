package com.iprp.backend.data.repos

import com.iprp.backend.data.Workshop
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-19
 */
interface WorkshopRepository : MongoRepository<Workshop, String>