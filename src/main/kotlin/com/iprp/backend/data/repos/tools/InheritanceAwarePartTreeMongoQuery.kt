package com.iprp.backend.data.repos.tools

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.query.ConvertingParameterAccessor
import org.springframework.data.mongodb.repository.query.MongoQueryMethod
import org.springframework.data.mongodb.repository.query.PartTreeMongoQuery
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider
import org.springframework.expression.spel.standard.SpelExpressionParser

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-16
 */
class InheritanceAwarePartTreeMongoQuery(method: MongoQueryMethod, mongoOperations: MongoOperations, expressionParser: SpelExpressionParser, evaluationContextProvider: QueryMethodEvaluationContextProvider)
    : PartTreeMongoQuery(method, mongoOperations, expressionParser, evaluationContextProvider) {

    private val inheritanceCriteria: Criteria?

    protected override fun createQuery(accessor: ConvertingParameterAccessor): Query {
        val query = super.createQuery(accessor)
        inheritanceCriteria?.let {
            query.addCriteria(inheritanceCriteria)
        }
        return query
    }

    protected override fun createCountQuery(accessor: ConvertingParameterAccessor): Query {
        val query = super.createCountQuery(accessor)
        inheritanceCriteria?.let {
            query.addCriteria(inheritanceCriteria)
        }
        return query
    }

    init {
        inheritanceCriteria = if (method.entityInformation.javaType.isAnnotationPresent(TypeAlias::class.java)) where("_class")
                .`is`(method.entityInformation.javaType.getAnnotation(TypeAlias::class.java).value) else null
    }
}