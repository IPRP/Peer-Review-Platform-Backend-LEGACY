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

    companion object {
        fun fromList(criteria: List<Map<String, String>>): ReviewCriteria {
            val processedCriteria = mutableListOf<ReviewCriterionTuple>()

            for(criterion in criteria) {
                if(criterion.containsKey("type") && criterion.containsKey("content")) {
                    val content = criterion["content"] ?: error("")
                    val check = (criterion["type"] ?: error("")).toLowerCase()
                    var type: ReviewCriterionType? = null
                    when(check) {
                        "point"         -> type = ReviewCriterionType.Point
                        "grade"         -> type = ReviewCriterionType.Grade
                        "percentage"    -> type = ReviewCriterionType.Percentage
                    }
                    if (type != null) {
                        processedCriteria.add(ReviewCriterionTuple(type, content))
                    }
                }
            }

            return ReviewCriteria(processedCriteria)
        }
    }
}

data class ReviewCriterionTuple(var type: ReviewCriterionType, var content: String)

enum class ReviewCriterionType {
    Point,      // 1 - 10
    Grade,      // A, B, C or "Sehr Gut", "Gut", "Befriedigend", ...
    Percentage  // 0 - 100 %
}