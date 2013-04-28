package com.jobmatcher.domain;

import com.jobmatcher.service.JobSeekerService;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Configurable
@RooIntegrationTest(entity = JobSeeker.class, transactional = false)
public class JobSeekerIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    JobSeekerDataOnDemand dod;

	@Autowired
    JobSeekerService jobSeekerService;

	@Test
    public void testCountAllJobSeekers() {
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to initialize correctly", dod.getRandomJobSeeker());
        long count = jobSeekerService.countAllJobSeekers();
        Assert.assertTrue("Counter for 'JobSeeker' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindJobSeeker() {
        JobSeeker obj = dod.getRandomJobSeeker();
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to provide an identifier", id);
        obj = jobSeekerService.findJobSeeker(id);
        Assert.assertNotNull("Find method for 'JobSeeker' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'JobSeeker' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllJobSeekers() {
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to initialize correctly", dod.getRandomJobSeeker());
        long count = jobSeekerService.countAllJobSeekers();
        Assert.assertTrue("Too expensive to perform a find all test for 'JobSeeker', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<JobSeeker> result = jobSeekerService.findAllJobSeekers();
        Assert.assertNotNull("Find all method for 'JobSeeker' illegally returned null", result);
        Assert.assertTrue("Find all method for 'JobSeeker' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindJobSeekerEntries() {
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to initialize correctly", dod.getRandomJobSeeker());
        long count = jobSeekerService.countAllJobSeekers();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<JobSeeker> result = jobSeekerService.findJobSeekerEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'JobSeeker' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'JobSeeker' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSaveJobSeeker() {
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to initialize correctly", dod.getRandomJobSeeker());
        JobSeeker obj = dod.getNewTransientJobSeeker(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'JobSeeker' identifier to be null", obj.getId());
        jobSeekerService.saveJobSeeker(obj);
        Assert.assertNotNull("Expected 'JobSeeker' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteJobSeeker() {
        JobSeeker obj = dod.getRandomJobSeeker();
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'JobSeeker' failed to provide an identifier", id);
        obj = jobSeekerService.findJobSeeker(id);
        jobSeekerService.deleteJobSeeker(obj);
        Assert.assertNull("Failed to remove 'JobSeeker' with identifier '" + id + "'", jobSeekerService.findJobSeeker(id));
    }
}
