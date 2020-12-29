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

    fun maxPoints(): Int {
        var maxPoints = 0
        criteria.forEach { criterion -> maxPoints += criterion.weight }
        return maxPoints
    }

    companion object {
        fun fromList(criteria: List<Map<String, String>>): ReviewCriteria {
            val processedCriteria = mutableListOf<ReviewCriterionTuple>()

            for(criterion in criteria) {
                if (criterion.containsKey("type") && criterion.containsKey("title")
                    && criterion.containsKey("content") && criterion.containsKey("weight")) {
                    val title = criterion["title"] ?: ""
                    val content = criterion["content"] ?: ""
                    val check = (criterion["type"] ?: "").toLowerCase()
                    val weight = (criterion["weight"] ?: "").toIntOrNull() ?: continue
                    var type: ReviewCriterionType? = null
                    when(check) {
                        "point"         -> type = ReviewCriterionType.Point
                        "grade"         -> type = ReviewCriterionType.Grade
                        "percentage"    -> type = ReviewCriterionType.Percentage
                        "truefalse"     -> type = ReviewCriterionType.TrueFalse
                    }
                    if (type != null) {
                        processedCriteria.add(ReviewCriterionTuple(type, title, content, weight))
                    }
                }
            }

            return ReviewCriteria(processedCriteria)
        }
    }
}

// "weight" is in fact a float, but for consistency it is stored as a integer
// to get the "true" value divide through 10.0
data class ReviewCriterionTuple(var type: ReviewCriterionType, var title: String, var content: String, var weight: Int)

enum class ReviewCriterionType {
    Point,      // 1 - 10
    Grade,      // A, B, C or "Sehr Gut", "Gut", "Befriedigend", ...
    Percentage,  // 0 - 100 %
    TrueFalse,
}