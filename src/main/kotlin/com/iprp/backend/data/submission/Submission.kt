package com.iprp.backend.data.submission

import com.iprp.backend.attachments.Attachment
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

/**
 * Submission of a task in a given [SubmissionRound].
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class Submission(
    var locked: Boolean,
    var date: LocalDateTime,
    var title: String,
    var commment: String,
    var attachments: MutableList<Attachment>, // Doc

    val workshop: String, // Workshop
    val student: String, // Student
    // "assigned"
    val reviews: MutableList<String>, // Review
    val reviewsDone: Boolean,
    val pointsMean: Int?,
    val maxPoints: Int?,
) {
    @Id
    lateinit var id: String
        private set
}