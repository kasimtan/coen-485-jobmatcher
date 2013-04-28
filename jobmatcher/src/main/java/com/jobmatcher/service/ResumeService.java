package com.jobmatcher.service;

import com.jobmatcher.domain.Resume;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.Resume.class })
public interface ResumeService {

	public abstract long countAllResumes();


	public abstract void deleteResume(Resume resume);


	public abstract Resume findResume(BigInteger id);


	public abstract List<Resume> findAllResumes();


	public abstract List<Resume> findResumeEntries(int firstResult, int maxResults);


	public abstract void saveResume(Resume resume);


	public abstract Resume updateResume(Resume resume);

}
