package com.jobmatcher.repository;

import com.jobmatcher.domain.JobSeeker;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = JobSeeker.class)
public interface JobSeekerRepository extends PagingAndSortingRepository<JobSeeker, BigInteger> {

    List<com.jobmatcher.domain.JobSeeker> findAll();
}
