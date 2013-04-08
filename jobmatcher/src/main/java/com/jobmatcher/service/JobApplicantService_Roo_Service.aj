// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.service;

import com.jobmatcher.domain.JobApplicant;
import com.jobmatcher.service.JobApplicantService;
import java.math.BigInteger;
import java.util.List;

privileged aspect JobApplicantService_Roo_Service {
    
    public abstract long JobApplicantService.countAllJobApplicants();    
    public abstract void JobApplicantService.deleteJobApplicant(JobApplicant jobApplicant);    
    public abstract JobApplicant JobApplicantService.findJobApplicant(BigInteger id);    
    public abstract List<JobApplicant> JobApplicantService.findAllJobApplicants();    
    public abstract List<JobApplicant> JobApplicantService.findJobApplicantEntries(int firstResult, int maxResults);    
    public abstract void JobApplicantService.saveJobApplicant(JobApplicant jobApplicant);    
    public abstract JobApplicant JobApplicantService.updateJobApplicant(JobApplicant jobApplicant);    
}
