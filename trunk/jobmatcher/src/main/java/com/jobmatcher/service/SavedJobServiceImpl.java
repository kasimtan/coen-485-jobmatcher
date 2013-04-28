package com.jobmatcher.service;

import com.jobmatcher.domain.SavedJob;
import com.jobmatcher.repository.SavedJobRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SavedJobServiceImpl implements SavedJobService {

	@Autowired
    SavedJobRepository savedJobRepository;

	public long countAllSavedJobs() {
        return savedJobRepository.count();
    }

	public void deleteSavedJob(SavedJob savedJob) {
        savedJobRepository.delete(savedJob);
    }

	public SavedJob findSavedJob(BigInteger id) {
        return savedJobRepository.findOne(id);
    }

	public List<SavedJob> findAllSavedJobs() {
        return savedJobRepository.findAll();
    }

	public List<SavedJob> findSavedJobEntries(int firstResult, int maxResults) {
        return savedJobRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveSavedJob(SavedJob savedJob) {
        savedJobRepository.save(savedJob);
    }

	public SavedJob updateSavedJob(SavedJob savedJob) {
        return savedJobRepository.save(savedJob);
    }
}
