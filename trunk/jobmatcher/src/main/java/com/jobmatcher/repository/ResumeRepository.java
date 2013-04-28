package com.jobmatcher.repository;

import com.jobmatcher.domain.Resume;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = Resume.class)
public interface ResumeRepository extends PagingAndSortingRepository<Resume, BigInteger> {

    List<com.jobmatcher.domain.Resume> findAll();
}
