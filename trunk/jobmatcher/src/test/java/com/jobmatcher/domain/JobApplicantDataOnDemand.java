package com.jobmatcher.domain;

import com.jobmatcher.service.JobApplicantService;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = JobApplicant.class)
public class JobApplicantDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<JobApplicant> data;

	@Autowired
    CoverLetterDataOnDemand coverLetterDataOnDemand;

	@Autowired
    JobDataOnDemand jobDataOnDemand;

	@Autowired
    JobSeekerDataOnDemand jobSeekerDataOnDemand;

	@Autowired
    ResumeDataOnDemand resumeDataOnDemand;

	@Autowired
    JobApplicantService jobApplicantService;

	public JobApplicant getNewTransientJobApplicant(int index) {
        JobApplicant obj = new JobApplicant();
        setCoverLetter(obj, index);
        setJob(obj, index);
        setJobSeeker(obj, index);
        setResume(obj, index);
        return obj;
    }

	public void setCoverLetter(JobApplicant obj, int index) {
        CoverLetter coverLetter = coverLetterDataOnDemand.getRandomCoverLetter();
        obj.setCoverLetter(coverLetter);
    }

	public void setJob(JobApplicant obj, int index) {
        Job job = jobDataOnDemand.getRandomJob();
        obj.setJob(job);
    }

	public void setJobSeeker(JobApplicant obj, int index) {
        JobSeeker jobSeeker = jobSeekerDataOnDemand.getRandomJobSeeker();
        obj.setJobSeeker(jobSeeker);
    }

	public void setResume(JobApplicant obj, int index) {
        Resume resume = resumeDataOnDemand.getRandomResume();
        obj.setResume(resume);
    }

	public JobApplicant getSpecificJobApplicant(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        JobApplicant obj = data.get(index);
        BigInteger id = obj.getId();
        return jobApplicantService.findJobApplicant(id);
    }

	public JobApplicant getRandomJobApplicant() {
        init();
        JobApplicant obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return jobApplicantService.findJobApplicant(id);
    }

	public boolean modifyJobApplicant(JobApplicant obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = jobApplicantService.findJobApplicantEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'JobApplicant' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<JobApplicant>();
        for (int i = 0; i < 10; i++) {
            JobApplicant obj = getNewTransientJobApplicant(i);
            try {
                jobApplicantService.saveJobApplicant(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            data.add(obj);
        }
    }
}
