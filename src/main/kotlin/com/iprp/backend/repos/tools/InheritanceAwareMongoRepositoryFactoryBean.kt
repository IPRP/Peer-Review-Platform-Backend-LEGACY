package com.iprp.backend.repos.tools

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean
import org.springframework.data.repository.Repository
import org.springframework.data.repository.core.support.RepositoryFactorySupport
import java.io.Serializable


class InheritanceAwareMongoRepositoryFactoryBean<T : Repository<S, ID>?, S, ID : Serializable?>(repositoryInterface: Class<out T>?) : MongoRepositoryFactoryBean<T, S, ID>(repositoryInterface!!) {
    override fun getFactoryInstance(operations: MongoOperations): RepositoryFactorySupport {
        return InheritanceAwareMongoRepositoryFactory(operations)
    }
}