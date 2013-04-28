package com.jobmatcher.domain;

import com.jobmatcher.reference.States;
import com.jobmatcher.service.JobSeekerService;
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
@RooDataOnDemand(entity = JobSeeker.class)
public class JobSeekerDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<JobSeeker> data;

	@Autowired
    JobSeekerService jobSeekerService;

	public JobSeeker getNewTransientJobSeeker(int index) {
        JobSeeker obj = new JobSeeker();
        setAddress(obj, index);
        setCity(obj, index);
        setEmail(obj, index);
        setPassword(obj, index);
        setStates(obj, index);
        setZip(obj, index);
        return obj;
    }

	public void setAddress(JobSeeker obj, int index) {
        String address = "address_" + index;
        if (address.length() > 255) {
            address = address.substring(0, 255);
        }
        obj.setAddress(address);
    }

	public void setCity(JobSeeker obj, int index) {
        String city = "city_" + index;
        if (city.length() > 45) {
            city = city.substring(0, 45);
        }
        obj.setCity(city);
    }

	public void setEmail(JobSeeker obj, int index) {
        String email = "foo" + index + "@bar.com";
        obj.setEmail(email);
    }

	public void setPassword(JobSeeker obj, int index) {
        String password = "password_" + index;
        if (password.length() > 45) {
            password = password.substring(0, 45);
        }
        obj.setPassword(password);
    }

	public void setStates(JobSeeker obj, int index) {
        States states = States.class.getEnumConstants()[0];
        obj.setStates(states);
    }

	public void setZip(JobSeeker obj, int index) {
        String zip = "zip_" + index;
        if (zip.length() > 5) {
            zip = zip.substring(0, 5);
        }
        obj.setZip(zip);
    }

	public JobSeeker getSpecificJobSeeker(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        JobSeeker obj = data.get(index);
        BigInteger id = obj.getId();
        return jobSeekerService.findJobSeeker(id);
    }

	public JobSeeker getRandomJobSeeker() {
        init();
        JobSeeker obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return jobSeekerService.findJobSeeker(id);
    }

	public boolean modifyJobSeeker(JobSeeker obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = jobSeekerService.findJobSeekerEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'JobSeeker' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<JobSeeker>();
        for (int i = 0; i < 10; i++) {
            JobSeeker obj = getNewTransientJobSeeker(i);
            try {
                jobSeekerService.saveJobSeeker(obj);
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
