package com.jobmatcher.repository;

import com.jobmatcher.domain.HiringManager;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = HiringManager.class)
public interface HiringManagerRepository extends PagingAndSortingRepository<HiringManager, BigInteger> {

    List<com.jobmatcher.domain.HiringManager> findAll();
}
