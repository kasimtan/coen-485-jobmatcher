// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.service;

import com.jobmatcher.domain.Resume;
import com.jobmatcher.repository.ResumeRepository;
import com.jobmatcher.service.ResumeServiceImpl;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ResumeServiceImpl_Roo_Service {
    
    declare @type: ResumeServiceImpl: @Service;
    
    declare @type: ResumeServiceImpl: @Transactional;
    
    @Autowired
    ResumeRepository ResumeServiceImpl.resumeRepository;
    
    public long ResumeServiceImpl.countAllResumes() {
        return resumeRepository.count();
    }
    
    public void ResumeServiceImpl.deleteResume(Resume resume) {
        resumeRepository.delete(resume);
    }
    
    public Resume ResumeServiceImpl.findResume(BigInteger id) {
        return resumeRepository.findOne(id);
    }
    
    public List<Resume> ResumeServiceImpl.findAllResumes() {
        return resumeRepository.findAll();
    }
    
    public List<Resume> ResumeServiceImpl.findResumeEntries(int firstResult, int maxResults) {
        return resumeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public void ResumeServiceImpl.saveResume(Resume resume) {
        resumeRepository.save(resume);
    }
    
    public Resume ResumeServiceImpl.updateResume(Resume resume) {
        return resumeRepository.save(resume);
    }
    
}
