package com.jobmatcher.service;

import com.jobmatcher.domain.JobSeeker;
import com.jobmatcher.repository.JobSeekerRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class JobSeekerServiceImpl implements JobSeekerService {

	@Autowired
    JobSeekerRepository jobSeekerRepository;

	public long countAllJobSeekers() {
        return jobSeekerRepository.count();
    }

	public void deleteJobSeeker(JobSeeker jobSeeker) {
        jobSeekerRepository.delete(jobSeeker);
    }

	public JobSeeker findJobSeeker(BigInteger id) {
        return jobSeekerRepository.findOne(id);
    }

	public List<JobSeeker> findAllJobSeekers() {
        return jobSeekerRepository.findAll();
    }

	public List<JobSeeker> findJobSeekerEntries(int firstResult, int maxResults) {
        return jobSeekerRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveJobSeeker(JobSeeker jobSeeker) {
        jobSeekerRepository.save(jobSeeker);
    }

	public JobSeeker updateJobSeeker(JobSeeker jobSeeker) {
        return jobSeekerRepository.save(jobSeeker);
    }
}
