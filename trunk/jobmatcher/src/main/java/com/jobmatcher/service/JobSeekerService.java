package com.jobmatcher.service;

import com.jobmatcher.domain.JobSeeker;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.JobSeeker.class })
public interface JobSeekerService {

	public abstract long countAllJobSeekers();


	public abstract void deleteJobSeeker(JobSeeker jobSeeker);


	public abstract JobSeeker findJobSeeker(BigInteger id);


	public abstract List<JobSeeker> findAllJobSeekers();


	public abstract List<JobSeeker> findJobSeekerEntries(int firstResult, int maxResults);


	public abstract void saveJobSeeker(JobSeeker jobSeeker);


	public abstract JobSeeker updateJobSeeker(JobSeeker jobSeeker);

}
