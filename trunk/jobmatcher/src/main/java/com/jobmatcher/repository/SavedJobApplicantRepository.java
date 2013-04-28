package com.jobmatcher.repository;

import com.jobmatcher.domain.SavedJobApplicant;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = SavedJobApplicant.class)
public interface SavedJobApplicantRepository extends PagingAndSortingRepository<SavedJobApplicant, BigInteger> {

    List<com.jobmatcher.domain.SavedJobApplicant> findAll();
}
