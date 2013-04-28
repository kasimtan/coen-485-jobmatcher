package com.jobmatcher.domain;

import com.jobmatcher.service.SavedJobService;
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

@Configurable
@Component
@RooDataOnDemand(entity = SavedJob.class)
public class SavedJobDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<SavedJob> data;

	@Autowired
    JobDataOnDemand jobDataOnDemand;

	@Autowired
    JobSeekerDataOnDemand jobSeekerDataOnDemand;

	@Autowired
    SavedJobService savedJobService;

	public SavedJob getNewTransientSavedJob(int index) {
        SavedJob obj = new SavedJob();
        setJob(obj, index);
        setJobSeeker(obj, index);
        return obj;
    }

	public void setJob(SavedJob obj, int index) {
        Job job = jobDataOnDemand.getRandomJob();
        obj.setJob(job);
    }

	public void setJobSeeker(SavedJob obj, int index) {
        JobSeeker jobSeeker = jobSeekerDataOnDemand.getRandomJobSeeker();
        obj.setJobSeeker(jobSeeker);
    }

	public SavedJob getSpecificSavedJob(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        SavedJob obj = data.get(index);
        BigInteger id = obj.getId();
        return savedJobService.findSavedJob(id);
    }

	public SavedJob getRandomSavedJob() {
        init();
        SavedJob obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return savedJobService.findSavedJob(id);
    }

	public boolean modifySavedJob(SavedJob obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = savedJobService.findSavedJobEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'SavedJob' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<SavedJob>();
        for (int i = 0; i < 10; i++) {
            SavedJob obj = getNewTransientSavedJob(i);
            try {
                savedJobService.saveSavedJob(obj);
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
