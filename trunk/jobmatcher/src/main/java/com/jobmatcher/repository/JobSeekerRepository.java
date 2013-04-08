package com.jobmatcher.repository;

import com.jobmatcher.domain.JobSeeker;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = JobSeeker.class)
public interface JobSeekerRepository {

    List<com.jobmatcher.domain.JobSeeker> findAll();
}
