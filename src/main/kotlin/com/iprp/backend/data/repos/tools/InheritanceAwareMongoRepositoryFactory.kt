package com.iprp.backend.data.repos.tools

import org.springframework.data.mapping.context.MappingContext
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty
import org.springframework.data.mongodb.repository.query.MongoQueryMethod
import org.springframework.data.mongodb.repository.query.StringBasedMongoQuery
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory
import org.springframework.data.projection.ProjectionFactory
import org.springframework.data.repository.core.NamedQueries
import org.springframework.data.repository.core.RepositoryMetadata
import org.springframework.data.repository.query.QueryLookupStrategy
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider
import org.springframework.data.repository.query.RepositoryQuery
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.lang.Nullable
import java.lang.reflect.Method
import java.util.*


class InheritanceAwareMongoRepositoryFactory
/**
 * Creates a new [MongoRepositoryFactory] with the given [MongoOperations].
 *
 * @param operations must not be null.
 */(private val operations: MongoOperations) : MongoRepositoryFactory(operations) {
    /**
     * Switch to our MongoQueryLookupStrategy.
     */
    override fun getQueryLookupStrategy(@Nullable key: QueryLookupStrategy.Key?,
                                        evaluationContextProvider: QueryMethodEvaluationContextProvider): Optional<QueryLookupStrategy> {
        return Optional.of(MongoQueryLookupStrategy(operations, evaluationContextProvider,
                operations.converter.mappingContext))
    }

    /**
     * Taken from the Spring Data for MongoDB source code and modified to return InheritanceAwarePartTreeMongoQuery
     * instead of PartTreeMongoQuery. It's a static private part so copy/paste was the only way...
     */
    private class MongoQueryLookupStrategy(private val operations: MongoOperations, private val evaluationContextProvider: QueryMethodEvaluationContextProvider,
                                           var mappingContext: MappingContext<out MongoPersistentEntity<*>?, MongoPersistentProperty>) : QueryLookupStrategy {
        override fun resolveQuery(method: Method, metadata: RepositoryMetadata, factory: ProjectionFactory,
                                  namedQueries: NamedQueries): RepositoryQuery {
            val queryMethod = MongoQueryMethod(method, metadata, factory, mappingContext)
            val namedQueryName = queryMethod.namedQueryName
            return when {
                namedQueries.hasQuery(namedQueryName) -> {
                    val namedQuery = namedQueries.getQuery(namedQueryName)
                    StringBasedMongoQuery(namedQuery, queryMethod, operations, EXPRESSION_PARSER,
                            evaluationContextProvider)
                }
                queryMethod.hasAnnotatedQuery() -> {
                    StringBasedMongoQuery(queryMethod, operations, EXPRESSION_PARSER, evaluationContextProvider)
                }
                else -> {
                    InheritanceAwarePartTreeMongoQuery(queryMethod, operations, EXPRESSION_PARSER, evaluationContextProvider)
                }
            }
        }
    }

    companion object {
        private val EXPRESSION_PARSER = SpelExpressionParser()
    }
}