package com.jobmatcher.repository;

import com.jobmatcher.domain.SavedJob;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = SavedJob.class)
public interface SavedJobRepository extends PagingAndSortingRepository<SavedJob, BigInteger> {

    List<com.jobmatcher.domain.SavedJob> findAll();
}
