package com.iprp.backend.data.repos.tools


import org.springframework.data.mongodb.core.query.Criteria.where

import org.bson.Document
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.query.MongoEntityInformation
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository
import java.io.Serializable


/**
 * Custom Inheritance Aware MongoRepository
 * See: https://medium.com/@mladen.maravic/spring-data-mongodb-my-take-on-inheritance-support-102361c08e3d
 *
 * @author Kacper Urbaniec
 * @version 2020-11-16
 */
class InheritanceAwareSimpleMongoRepository<T, ID : Serializable?>(
        metadata: MongoEntityInformation<T, ID>,
       mongoOperations: MongoOperations) : SimpleMongoRepository<T, ID>(
        metadata,
        mongoOperations
) {
    private val mongoOperations: MongoOperations
    private val entityInformation: MongoEntityInformation<T, ID>
    private var classCriteriaDocument: Document? = null
    private var classCriteria: Criteria? = null

    override fun count(): Long {
        classCriteria?.let {
            return mongoOperations.getCollection(entityInformation.collectionName).countDocuments(classCriteriaDocument!!)
        }
        return super.count()
    }

    override fun findAll(): MutableList<T?> {
        classCriteria?.let {
            return mongoOperations.find(Query().addCriteria(classCriteria!!),
                    entityInformation.javaType,
                    entityInformation.collectionName)
        }
        return super.findAll()
    }

    init {
        this.mongoOperations = mongoOperations
        this.entityInformation = metadata
        if (entityInformation.javaType.isAnnotationPresent(TypeAlias::class.java)) {
            classCriteria = where("_class").`is`(entityInformation.javaType.getAnnotation(TypeAlias::class.java).value)
            classCriteriaDocument = classCriteria!!.criteriaObject
        } else {
            classCriteriaDocument = Document()
            classCriteria = null
        }
    }
}