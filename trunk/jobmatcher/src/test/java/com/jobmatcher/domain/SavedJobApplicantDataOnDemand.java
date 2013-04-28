package com.jobmatcher.domain;

import com.jobmatcher.service.SavedJobApplicantService;
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
@RooDataOnDemand(entity = SavedJobApplicant.class)
public class SavedJobApplicantDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<SavedJobApplicant> data;

	@Autowired
    HiringManagerDataOnDemand hiringManagerDataOnDemand;

	@Autowired
    JobApplicantDataOnDemand jobApplicantDataOnDemand;

	@Autowired
    SavedJobApplicantService savedJobApplicantService;

	public SavedJobApplicant getNewTransientSavedJobApplicant(int index) {
        SavedJobApplicant obj = new SavedJobApplicant();
        setHiringManager(obj, index);
        setJobApplicant(obj, index);
        return obj;
    }

	public void setHiringManager(SavedJobApplicant obj, int index) {
        HiringManager hiringManager = hiringManagerDataOnDemand.getRandomHiringManager();
        obj.setHiringManager(hiringManager);
    }

	public void setJobApplicant(SavedJobApplicant obj, int index) {
        JobApplicant jobApplicant = jobApplicantDataOnDemand.getRandomJobApplicant();
        obj.setJobApplicant(jobApplicant);
    }

	public SavedJobApplicant getSpecificSavedJobApplicant(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        SavedJobApplicant obj = data.get(index);
        BigInteger id = obj.getId();
        return savedJobApplicantService.findSavedJobApplicant(id);
    }

	public SavedJobApplicant getRandomSavedJobApplicant() {
        init();
        SavedJobApplicant obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return savedJobApplicantService.findSavedJobApplicant(id);
    }

	public boolean modifySavedJobApplicant(SavedJobApplicant obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = savedJobApplicantService.findSavedJobApplicantEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'SavedJobApplicant' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<SavedJobApplicant>();
        for (int i = 0; i < 10; i++) {
            SavedJobApplicant obj = getNewTransientSavedJobApplicant(i);
            try {
                savedJobApplicantService.saveSavedJobApplicant(obj);
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
