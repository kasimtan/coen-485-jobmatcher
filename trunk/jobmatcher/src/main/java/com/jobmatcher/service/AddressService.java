package com.jobmatcher.service;

import com.jobmatcher.domain.Addresses;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.Addresses.class })
public interface AddressService {

	public abstract long countAllAddresseses();


	public abstract void deleteAddresses(Addresses addresses);


	public abstract Addresses findAddresses(BigInteger id);


	public abstract List<Addresses> findAllAddresseses();


	public abstract List<Addresses> findAddressesEntries(int firstResult, int maxResults);


	public abstract void saveAddresses(Addresses addresses);


	public abstract Addresses updateAddresses(Addresses addresses);

}
