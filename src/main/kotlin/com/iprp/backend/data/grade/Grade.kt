package com.iprp.backend.data.grade

import com.iprp.backend.data.submission.SubmissionRound
import org.springframework.data.annotation.Id

/**
 * Stores the grades a student got in a [SubmissionRound]. Hence the name.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class Grade(
    var gradeSubmission: Int?,
    var gradeReview: Int?,
    var grade: Int?,

    val student: String,
    val submission: String,
    val workshop: String
) {
    @Id
    lateinit var id: String
        private set
}