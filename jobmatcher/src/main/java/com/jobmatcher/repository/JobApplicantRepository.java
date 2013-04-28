package com.jobmatcher.repository;

import com.jobmatcher.domain.JobApplicant;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = JobApplicant.class)
public interface JobApplicantRepository extends PagingAndSortingRepository<JobApplicant, BigInteger> {

    List<com.jobmatcher.domain.JobApplicant> findAll();
}
