package com.jobmatcher.repository;

import com.jobmatcher.domain.CoverLetter;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = CoverLetter.class)
public interface CoverLetterRepository extends PagingAndSortingRepository<CoverLetter, BigInteger> {

    List<com.jobmatcher.domain.CoverLetter> findAll();
}
