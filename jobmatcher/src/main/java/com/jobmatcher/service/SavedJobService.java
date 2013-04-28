package com.jobmatcher.service;

import com.jobmatcher.domain.SavedJob;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.SavedJob.class })
public interface SavedJobService {

	public abstract long countAllSavedJobs();


	public abstract void deleteSavedJob(SavedJob savedJob);


	public abstract SavedJob findSavedJob(BigInteger id);


	public abstract List<SavedJob> findAllSavedJobs();


	public abstract List<SavedJob> findSavedJobEntries(int firstResult, int maxResults);


	public abstract void saveSavedJob(SavedJob savedJob);


	public abstract SavedJob updateSavedJob(SavedJob savedJob);

}
