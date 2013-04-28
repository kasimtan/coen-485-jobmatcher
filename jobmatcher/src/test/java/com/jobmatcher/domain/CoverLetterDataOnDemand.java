package com.jobmatcher.domain;

import com.jobmatcher.service.CoverLetterService;
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
@RooDataOnDemand(entity = CoverLetter.class)
public class CoverLetterDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<CoverLetter> data;

	@Autowired
    JobSeekerDataOnDemand jobSeekerDataOnDemand;

	@Autowired
    CoverLetterService coverLetterService;

	public CoverLetter getNewTransientCoverLetter(int index) {
        CoverLetter obj = new CoverLetter();
        setFile(obj, index);
        setJobSeeker(obj, index);
        setName(obj, index);
        return obj;
    }

	public void setFile(CoverLetter obj, int index) {
        byte[] file = String.valueOf(index).getBytes();
        obj.setFile(file);
    }

	public void setJobSeeker(CoverLetter obj, int index) {
        JobSeeker jobSeeker = jobSeekerDataOnDemand.getRandomJobSeeker();
        obj.setJobSeeker(jobSeeker);
    }

	public void setName(CoverLetter obj, int index) {
        String name = "name_" + index;
        if (name.length() > 255) {
            name = name.substring(0, 255);
        }
        obj.setName(name);
    }

	public CoverLetter getSpecificCoverLetter(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        CoverLetter obj = data.get(index);
        BigInteger id = obj.getId();
        return coverLetterService.findCoverLetter(id);
    }

	public CoverLetter getRandomCoverLetter() {
        init();
        CoverLetter obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return coverLetterService.findCoverLetter(id);
    }

	public boolean modifyCoverLetter(CoverLetter obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = coverLetterService.findCoverLetterEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'CoverLetter' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<CoverLetter>();
        for (int i = 0; i < 10; i++) {
            CoverLetter obj = getNewTransientCoverLetter(i);
            try {
                coverLetterService.saveCoverLetter(obj);
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
