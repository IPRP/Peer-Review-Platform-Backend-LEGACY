package com.iprp.backend.data.repos.tools

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories




/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-16
 */
@Configuration
@EnableMongoRepositories(repositoryBaseClass = InheritanceAwareSimpleMongoRepository::class, repositoryFactoryBeanClass = InheritanceAwareMongoRepositoryFactoryBean::class)
class MongoConfigurer