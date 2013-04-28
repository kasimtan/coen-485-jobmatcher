package com.jobmatcher.service;

import com.jobmatcher.domain.Job;
import com.jobmatcher.repository.JobRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class JobServiceImpl implements JobService {

	@Autowired
    JobRepository jobRepository;

	public long countAllJobs() {
        return jobRepository.count();
    }

	public void deleteJob(Job job) {
        jobRepository.delete(job);
    }

	public Job findJob(BigInteger id) {
        return jobRepository.findOne(id);
    }

	public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

	public List<Job> findJobEntries(int firstResult, int maxResults) {
        return jobRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveJob(Job job) {
        jobRepository.save(job);
    }

	public Job updateJob(Job job) {
        return jobRepository.save(job);
    }
}
