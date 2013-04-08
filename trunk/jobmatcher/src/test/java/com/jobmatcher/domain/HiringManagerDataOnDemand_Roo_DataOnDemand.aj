// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.domain;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.domain.HiringManagerDataOnDemand;
import com.jobmatcher.service.HiringManagerService;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect HiringManagerDataOnDemand_Roo_DataOnDemand {
    
    declare @type: HiringManagerDataOnDemand: @Component;
    
    private Random HiringManagerDataOnDemand.rnd = new SecureRandom();
    
    private List<HiringManager> HiringManagerDataOnDemand.data;
    
    @Autowired
    HiringManagerService HiringManagerDataOnDemand.hiringManagerService;
    
    public HiringManager HiringManagerDataOnDemand.getNewTransientHiringManager(int index) {
        HiringManager obj = new HiringManager();
        setCompanyName(obj, index);
        setEmail(obj, index);
        setPassword(obj, index);
        return obj;
    }
    
    public void HiringManagerDataOnDemand.setCompanyName(HiringManager obj, int index) {
        String companyName = "companyName_" + index;
        if (companyName.length() > 255) {
            companyName = companyName.substring(0, 255);
        }
        obj.setCompanyName(companyName);
    }
    
    public void HiringManagerDataOnDemand.setEmail(HiringManager obj, int index) {
        String email = "foo" + index + "@bar.com";
        obj.setEmail(email);
    }
    
    public void HiringManagerDataOnDemand.setPassword(HiringManager obj, int index) {
        String password = "password_" + index;
        if (password.length() > 45) {
            password = password.substring(0, 45);
        }
        obj.setPassword(password);
    }
    
    public HiringManager HiringManagerDataOnDemand.getSpecificHiringManager(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        HiringManager obj = data.get(index);
        BigInteger id = obj.getId();
        return hiringManagerService.findHiringManager(id);
    }
    
    public HiringManager HiringManagerDataOnDemand.getRandomHiringManager() {
        init();
        HiringManager obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return hiringManagerService.findHiringManager(id);
    }
    
    public boolean HiringManagerDataOnDemand.modifyHiringManager(HiringManager obj) {
        return false;
    }
    
    public void HiringManagerDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = hiringManagerService.findHiringManagerEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'HiringManager' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<HiringManager>();
        for (int i = 0; i < 10; i++) {
            HiringManager obj = getNewTransientHiringManager(i);
            try {
                hiringManagerService.saveHiringManager(obj);
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
