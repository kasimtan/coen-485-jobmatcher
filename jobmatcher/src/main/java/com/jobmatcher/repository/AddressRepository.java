package com.jobmatcher.repository;

import com.jobmatcher.domain.Addresses;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Addresses.class)
public interface AddressRepository {

    List<com.jobmatcher.domain.Addresses> findAll();
}
