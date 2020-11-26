package com.iprp.backend.repos.tools

import com.iprp.backend.repos.PersonRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


// Base package classes attribute is needed, weird
// See: https://stackoverflow.com/a/48315823/12347616
@Configuration
@EnableMongoRepositories(basePackageClasses = [PersonRepository::class], repositoryBaseClass = InheritanceAwareSimpleMongoRepository::class, repositoryFactoryBeanClass = InheritanceAwareMongoRepositoryFactoryBean::class)
open class MongoConfigure