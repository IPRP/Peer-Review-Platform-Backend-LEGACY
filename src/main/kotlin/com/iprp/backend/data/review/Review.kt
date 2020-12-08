package com.iprp.backend.data.review

import org.springframework.data.annotation.Id

/**
 * A review a students submits for a given submission.
 * Grades List correspondents to the criteria of [ReviewCriteria].
 *
 * @author Kacper Urbaniec
 * @version 2020-10-29
 */
class Review(
    var done: Boolean,
    var feedback: String,
    var points: List<Int>,
    val maxPoints: Int,
    val criteria: String, // ReviewCriteria

    val student: String, // Student, "The Reviewer"
    val submission: String, // Submission
    val workshop: String, // Workshop
) {
    @Id
    lateinit var id: String
        private set
}