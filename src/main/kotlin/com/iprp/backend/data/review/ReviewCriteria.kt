package com.iprp.backend.data.review

import org.springframework.data.annotation.Id
import java.math.BigDecimal

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

    fun maxPoints(): BigDecimal {
        var maxPoints = BigDecimal(0)
        criteria.forEach { criterion -> maxPoints += criterion.weight * pointRange }
        return maxPoints
    }

    companion object {
        val pointRange = BigDecimal(10)

        fun fromList(criteria: List<Map<String, Any>>): ReviewCriteria {
            val processedCriteria = mutableListOf<ReviewCriterionTuple>()

            for(criterion in criteria) {
                if (criterion.containsKey("type") && criterion.containsKey("title")
                    && criterion.containsKey("content") && criterion.containsKey("weight")) {
                    val title = if (criterion["title"] is String) criterion["title"] as String else ""
                    val content = if (criterion["content"] is String) criterion["content"] as String else ""
                    val check = if (criterion["type"] is String) (criterion["type"] as String).toLowerCase() else ""
                    val weight = when {
                        criterion["weight"] is String -> {
                            val doubleCheck = (criterion["weight"] as String).toDoubleOrNull()
                            if (doubleCheck != null) BigDecimal.valueOf(doubleCheck)
                            else {
                                val intCheck =  (criterion["weight"] as String).toIntOrNull()
                                if (intCheck != null) BigDecimal(intCheck)
                                else BigDecimal.ONE
                            }
                        }
                        criterion["weight"] is Double -> {
                            BigDecimal.valueOf(criterion["weight"] as Double)
                        }
                        criterion["weight"] is Int -> {
                            BigDecimal(criterion["weight"] as Int)
                        }
                        else -> BigDecimal.ONE
                    }
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

        fun calculatePoints(points: BigDecimal, weight: BigDecimal, type: ReviewCriterionType): BigDecimal {
            return when (type) {
                ReviewCriterionType.Point -> {
                    points.multiply(weight)
                }
                ReviewCriterionType.Grade -> {
                    val gradeToPoint = when (points.intValueExact()) {
                        1 -> pointRange
                        2 -> pointRange.multiply(BigDecimal(0.80))
                        3 -> pointRange.multiply(BigDecimal(0.60))
                        4 -> pointRange.divide(BigDecimal(2))
                        else -> BigDecimal.ZERO
                    }
                    gradeToPoint.multiply(weight)
                }
                ReviewCriterionType.Percentage -> {
                    (points.div(pointRange)).multiply(weight)
                }
                ReviewCriterionType.TrueFalse -> {
                    if (points.intValueExact() == 1) {
                        pointRange.multiply(weight)
                    } else {
                        BigDecimal.ZERO
                    }
                }
            }
        }
    }
}

data class ReviewCriterionTuple(
    var type: ReviewCriterionType,
    var title: String,
    var content: String,
    var weight: BigDecimal
)

enum class ReviewCriterionType {
    Point,       // 1 - 10
    Grade,       // 1 (Sehr Gut, A) - 5 (Nicht Genügend, F)
    Percentage,  // 0 - 100 %
    TrueFalse,   // 1 - True, 0 - False
}