package com.iprp.backend.data.submission

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.grade.Grade
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.time.LocalDateTime

/**
 * Represents a "round" or "wave" of submissions in a Workshop.
 * A workshop consists of multiple rounds to allow users multiple submissions.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class SubmissionRound(
    var end: LocalDateTime,

    val workshop: String, // Workshop
    val submissions: MutableList<String>, // Submission
    val grades: MutableList<String> // Grade
) {
    @Id
    lateinit var id: String
        private set
}