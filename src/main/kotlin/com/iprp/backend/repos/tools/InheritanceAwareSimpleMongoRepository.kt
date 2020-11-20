package com.iprp.backend.repos.tools

import org.bson.Document
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.query.MongoEntityInformation
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository
import java.io.Serializable


class InheritanceAwareSimpleMongoRepository<T, ID : Serializable?>(private val entityInformation: MongoEntityInformation<T, ID>,
                                                                   private val mongoOperations: MongoOperations) : SimpleMongoRepository<T, ID>(entityInformation, mongoOperations) {
    private var classCriteriaDocument: Document? = null
    private var classCriteria: Criteria? = null
    override fun count(): Long {

        classCriteria?.let {
            return mongoOperations.getCollection(
                    entityInformation.collectionName).countDocuments(
                    classCriteriaDocument!!)
        }

        return super.count()
    }

    override fun findAll(): List<T> {

        classCriteria?.let {
            return mongoOperations.find(Query().addCriteria(classCriteria!!),
                    entityInformation.javaType,
                    entityInformation.collectionName)
        }

        return super.findAll()
    }

    init {
        if (entityInformation.javaType.isAnnotationPresent(TypeAlias::class.java)) {
            classCriteria = Criteria.where("_class").`is`(entityInformation.javaType.getAnnotation(TypeAlias::class.java).value)
            classCriteriaDocument = classCriteria!!.getCriteriaObject()
        } else {
            classCriteriaDocument = Document()
            classCriteria = null
        }
    }
}