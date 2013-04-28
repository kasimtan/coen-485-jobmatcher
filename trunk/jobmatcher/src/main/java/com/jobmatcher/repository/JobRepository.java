package com.jobmatcher.repository;

import com.jobmatcher.domain.Job;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = Job.class)
public interface JobRepository extends PagingAndSortingRepository<Job, BigInteger> {

    List<com.jobmatcher.domain.Job> findAll();
}
