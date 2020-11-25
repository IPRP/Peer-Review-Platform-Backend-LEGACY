package com.iprp.backend.data.review

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.user.Student
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

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
    var grades: List<Int>,
    @DBRef val student: Student,
    @DBRef val criteria: ReviewCriteria,
    @DBRef val submission: Submission,
    @DBRef val workshop: Workshop,
) {
    @Id
    lateinit var id: String
        private set
}