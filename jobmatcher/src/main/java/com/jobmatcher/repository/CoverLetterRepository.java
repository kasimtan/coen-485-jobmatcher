package com.jobmatcher.repository;

import com.jobmatcher.domain.CoverLetter;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = CoverLetter.class)
public interface CoverLetterRepository {

    List<com.jobmatcher.domain.CoverLetter> findAll();
}
