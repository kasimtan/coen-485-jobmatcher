package com.jobmatcher.service;

import com.jobmatcher.domain.JobApplicant;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.JobApplicant.class })
public interface JobApplicantService {

	public abstract long countAllJobApplicants();


	public abstract void deleteJobApplicant(JobApplicant jobApplicant);


	public abstract JobApplicant findJobApplicant(BigInteger id);


	public abstract List<JobApplicant> findAllJobApplicants();


	public abstract List<JobApplicant> findJobApplicantEntries(int firstResult, int maxResults);


	public abstract void saveJobApplicant(JobApplicant jobApplicant);


	public abstract JobApplicant updateJobApplicant(JobApplicant jobApplicant);

}
