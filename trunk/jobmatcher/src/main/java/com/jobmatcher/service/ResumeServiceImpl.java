package com.jobmatcher.service;

import com.jobmatcher.domain.Resume;
import com.jobmatcher.repository.ResumeRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

	@Autowired
    ResumeRepository resumeRepository;

	public long countAllResumes() {
        return resumeRepository.count();
    }

	public void deleteResume(Resume resume) {
        resumeRepository.delete(resume);
    }

	public Resume findResume(BigInteger id) {
        return resumeRepository.findOne(id);
    }

	public List<Resume> findAllResumes() {
        return resumeRepository.findAll();
    }

	public List<Resume> findResumeEntries(int firstResult, int maxResults) {
        return resumeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveResume(Resume resume) {
        resumeRepository.save(resume);
    }

	public Resume updateResume(Resume resume) {
        return resumeRepository.save(resume);
    }
}
