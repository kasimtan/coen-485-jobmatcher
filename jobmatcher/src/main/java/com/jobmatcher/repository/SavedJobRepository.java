package com.jobmatcher.repository;

import com.jobmatcher.domain.SavedJob;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = SavedJob.class)
public interface SavedJobRepository {

    List<com.jobmatcher.domain.SavedJob> findAll();
}
