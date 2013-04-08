package com.jobmatcher.repository;

import com.jobmatcher.domain.JobApplicant;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = JobApplicant.class)
public interface JobApplicantRepository {

    List<com.jobmatcher.domain.JobApplicant> findAll();
}
