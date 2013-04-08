package com.jobmatcher.repository;

import com.jobmatcher.domain.SavedJobApplicant;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = SavedJobApplicant.class)
public interface SavedJobApplicantRepository {

    List<com.jobmatcher.domain.SavedJobApplicant> findAll();
}
