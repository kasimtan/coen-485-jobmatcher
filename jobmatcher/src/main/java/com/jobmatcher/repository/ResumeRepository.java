package com.jobmatcher.repository;

import com.jobmatcher.domain.Resume;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Resume.class)
public interface ResumeRepository {

    List<com.jobmatcher.domain.Resume> findAll();
}
