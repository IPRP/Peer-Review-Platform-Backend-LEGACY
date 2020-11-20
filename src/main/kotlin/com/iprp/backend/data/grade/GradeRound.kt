package com.iprp.backend.data.grade

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.submission.SubmissionRound
import com.iprp.backend.data.user.Student
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 * Stores the grades a student got in a [SubmissionRound]. Hence the name.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class GradeRound(
    gradeSubmission: Int,
    gradeReview: Int,
    grade: Int,
    @DBRef val student: Student,
    @DBRef val submission: Submission,
    @DBRef val round: SubmissionRound,
    @DBRef val workshop: Workshop
) {
    @Id
    lateinit var id: String
        private set
}