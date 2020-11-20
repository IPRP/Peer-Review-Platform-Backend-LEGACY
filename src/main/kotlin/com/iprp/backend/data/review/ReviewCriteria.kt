package com.iprp.backend.data.review

import org.springframework.data.annotation.Id

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */
class ReviewCriteria(
    val criteria: List<ReviewCriterionTuple>
) {
    @Id
    lateinit var id: String
        private set
}

data class ReviewCriterionTuple(var type: ReviewCriterionType, var content: String)

enum class ReviewCriterionType {
    Point,      // 1 - 10
    Grade,      // A, B, C or "Sehr Gut", "Gut", "Befriedigend", ...
    Percentage  // 0 - 100 %
}