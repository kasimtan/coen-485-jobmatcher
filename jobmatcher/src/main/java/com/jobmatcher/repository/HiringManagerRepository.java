package com.jobmatcher.repository;

import com.jobmatcher.domain.HiringManager;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = HiringManager.class)
public interface HiringManagerRepository {

    List<com.jobmatcher.domain.HiringManager> findAll();
}
