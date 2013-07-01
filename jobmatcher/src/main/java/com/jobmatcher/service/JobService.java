package com.jobmatcher.service;

import com.jobmatcher.domain.Job;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.Job.class })
public interface JobService {

	public abstract long countAllJobs();


	public abstract void deleteJob(Job job);


	public abstract Job findJob(BigInteger id);


	public abstract List<Job> findAllJobs();


	public abstract List<Job> findJobEntries(int firstResult, int maxResults);


	public abstract void saveJob(Job job);


	public abstract Job updateJob(Job job);
	
	public abstract List<Job> findJobsByKeyword(String searchKeyWord);

}
