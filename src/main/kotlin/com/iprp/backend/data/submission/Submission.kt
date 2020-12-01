package com.iprp.backend.data.submission

import com.iprp.backend.data.review.Review
import com.iprp.backend.data.Workshop
import com.iprp.backend.data.user.Student
import org.bson.types.Binary
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 * Submission of a task in a given [SubmissionRound].
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class Submission(
    var done: Boolean,
    var title: String?,
    var commment: String?,
    val attachments: List<Binary>,

    val workshop: String, // Workshop
    val student: String, // Student
    // "assigned"
    val reviews: List<String> // Review

) {
    @Id
    lateinit var id: String
        private set
}