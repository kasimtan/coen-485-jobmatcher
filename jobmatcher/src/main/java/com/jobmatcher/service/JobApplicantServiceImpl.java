package com.jobmatcher.service;

import com.jobmatcher.domain.JobApplicant;
import com.jobmatcher.repository.JobApplicantRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class JobApplicantServiceImpl implements JobApplicantService {

	@Autowired
    JobApplicantRepository jobApplicantRepository;

	public long countAllJobApplicants() {
        return jobApplicantRepository.count();
    }

	public void deleteJobApplicant(JobApplicant jobApplicant) {
        jobApplicantRepository.delete(jobApplicant);
    }

	public JobApplicant findJobApplicant(BigInteger id) {
        return jobApplicantRepository.findOne(id);
    }

	public List<JobApplicant> findAllJobApplicants() {
        return jobApplicantRepository.findAll();
    }

	public List<JobApplicant> findJobApplicantEntries(int firstResult, int maxResults) {
        return jobApplicantRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveJobApplicant(JobApplicant jobApplicant) {
        jobApplicantRepository.save(jobApplicant);
    }

	public JobApplicant updateJobApplicant(JobApplicant jobApplicant) {
        return jobApplicantRepository.save(jobApplicant);
    }
}
