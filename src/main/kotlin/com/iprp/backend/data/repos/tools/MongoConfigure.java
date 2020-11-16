package com.iprp.backend.data.repos.tools;

import com.iprp.backend.data.repos.PersonRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


// Base package classes attribute is needed, weird
// See: https://stackoverflow.com/a/48315823/12347616
@Configuration
@EnableMongoRepositories(
        basePackageClasses = PersonRepository.class,
        repositoryBaseClass = InheritanceAwareSimpleMongoRepository.class,
        repositoryFactoryBeanClass = InheritanceAwareMongoRepositoryFactoryBean.class)
public class MongoConfigure { }
