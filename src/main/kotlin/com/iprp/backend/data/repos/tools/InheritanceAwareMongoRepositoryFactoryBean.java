package com.iprp.backend.data.repos.tools;

import java.io.Serializable;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class InheritanceAwareMongoRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
        MongoRepositoryFactoryBean<T, S, ID> {

    public InheritanceAwareMongoRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }


    @NotNull
    @Override
    protected RepositoryFactorySupport getFactoryInstance(@NotNull MongoOperations operations) {
        return new InheritanceAwareMongoRepositoryFactory(operations);
    }

}