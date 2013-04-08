package com.jobmatcher.repository;

import com.jobmatcher.domain.Job;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Job.class)
public interface JobRepository {

    List<com.jobmatcher.domain.Job> findAll();
}
