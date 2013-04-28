package com.jobmatcher.service;

import com.jobmatcher.domain.SavedJobApplicant;
import com.jobmatcher.repository.SavedJobApplicantRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SavedJobApplicantServiceImpl implements SavedJobApplicantService {

	@Autowired
    SavedJobApplicantRepository savedJobApplicantRepository;

	public long countAllSavedJobApplicants() {
        return savedJobApplicantRepository.count();
    }

	public void deleteSavedJobApplicant(SavedJobApplicant savedJobApplicant) {
        savedJobApplicantRepository.delete(savedJobApplicant);
    }

	public SavedJobApplicant findSavedJobApplicant(BigInteger id) {
        return savedJobApplicantRepository.findOne(id);
    }

	public List<SavedJobApplicant> findAllSavedJobApplicants() {
        return savedJobApplicantRepository.findAll();
    }

	public List<SavedJobApplicant> findSavedJobApplicantEntries(int firstResult, int maxResults) {
        return savedJobApplicantRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveSavedJobApplicant(SavedJobApplicant savedJobApplicant) {
        savedJobApplicantRepository.save(savedJobApplicant);
    }

	public SavedJobApplicant updateSavedJobApplicant(SavedJobApplicant savedJobApplicant) {
        return savedJobApplicantRepository.save(savedJobApplicant);
    }
}
