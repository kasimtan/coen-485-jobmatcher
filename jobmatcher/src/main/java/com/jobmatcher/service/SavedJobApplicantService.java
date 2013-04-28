package com.jobmatcher.service;

import com.jobmatcher.domain.SavedJobApplicant;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.SavedJobApplicant.class })
public interface SavedJobApplicantService {

	public abstract long countAllSavedJobApplicants();


	public abstract void deleteSavedJobApplicant(SavedJobApplicant savedJobApplicant);


	public abstract SavedJobApplicant findSavedJobApplicant(BigInteger id);


	public abstract List<SavedJobApplicant> findAllSavedJobApplicants();


	public abstract List<SavedJobApplicant> findSavedJobApplicantEntries(int firstResult, int maxResults);


	public abstract void saveSavedJobApplicant(SavedJobApplicant savedJobApplicant);


	public abstract SavedJobApplicant updateSavedJobApplicant(SavedJobApplicant savedJobApplicant);

}
