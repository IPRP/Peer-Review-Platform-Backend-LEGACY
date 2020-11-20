package com.iprp.backend.data.submission

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.grade.GradeRound
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 * Represents a "round" or "wave" of submissions in a Workshop.
 * A workshop consists of multiple rounds to allow users multiple submissions.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class SubmissionRound(
    @DBRef val workshop: Workshop,
    @DBRef val submissions: List<Submission>,
    @DBRef val grades: List<GradeRound>
) {
    @Id
    lateinit var id: String
        private set
}