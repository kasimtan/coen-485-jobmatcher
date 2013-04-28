package com.jobmatcher.domain;

import com.jobmatcher.service.ResumeService;
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
@RooDataOnDemand(entity = Resume.class)
public class ResumeDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Resume> data;

	@Autowired
    JobSeekerDataOnDemand jobSeekerDataOnDemand;

	@Autowired
    ResumeService resumeService;

	public Resume getNewTransientResume(int index) {
        Resume obj = new Resume();
        setFile(obj, index);
        setJobSeeker(obj, index);
        setName(obj, index);
        return obj;
    }

	public void setFile(Resume obj, int index) {
        byte[] file = String.valueOf(index).getBytes();
        obj.setFile(file);
    }

	public void setJobSeeker(Resume obj, int index) {
        JobSeeker jobSeeker = jobSeekerDataOnDemand.getRandomJobSeeker();
        obj.setJobSeeker(jobSeeker);
    }

	public void setName(Resume obj, int index) {
        String name = "name_" + index;
        if (name.length() > 255) {
            name = name.substring(0, 255);
        }
        obj.setName(name);
    }

	public Resume getSpecificResume(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Resume obj = data.get(index);
        BigInteger id = obj.getId();
        return resumeService.findResume(id);
    }

	public Resume getRandomResume() {
        init();
        Resume obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return resumeService.findResume(id);
    }

	public boolean modifyResume(Resume obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = resumeService.findResumeEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Resume' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Resume>();
        for (int i = 0; i < 10; i++) {
            Resume obj = getNewTransientResume(i);
            try {
                resumeService.saveResume(obj);
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
