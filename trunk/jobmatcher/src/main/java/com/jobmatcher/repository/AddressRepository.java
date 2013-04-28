package com.jobmatcher.repository;

import com.jobmatcher.domain.Addresses;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooMongoRepository(domainType = Addresses.class)
public interface AddressRepository extends PagingAndSortingRepository<Addresses, BigInteger> {

    List<com.jobmatcher.domain.Addresses> findAll();
}
