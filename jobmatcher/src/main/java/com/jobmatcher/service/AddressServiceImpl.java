package com.jobmatcher.service;

import com.jobmatcher.domain.Addresses;
import com.jobmatcher.repository.AddressRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	@Autowired
    AddressRepository addressRepository;

	public long countAllAddresseses() {
        return addressRepository.count();
    }

	public void deleteAddresses(Addresses addresses) {
        addressRepository.delete(addresses);
    }

	public Addresses findAddresses(BigInteger id) {
        return addressRepository.findOne(id);
    }

	public List<Addresses> findAllAddresseses() {
        return addressRepository.findAll();
    }

	public List<Addresses> findAddressesEntries(int firstResult, int maxResults) {
        return addressRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveAddresses(Addresses addresses) {
        addressRepository.save(addresses);
    }

	public Addresses updateAddresses(Addresses addresses) {
        return addressRepository.save(addresses);
    }
}
