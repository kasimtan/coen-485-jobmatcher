package com.jobmatcher.domain;

import com.jobmatcher.service.JobService;
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

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RooIntegrationTest(entity = Job.class, transactional = false)
public class JobIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    JobDataOnDemand dod;

	@Autowired
    JobService jobService;

	@Test
    public void testCountAllJobs() {
        Assert.assertNotNull("Data on demand for 'Job' failed to initialize correctly", dod.getRandomJob());
        long count = jobService.countAllJobs();
        Assert.assertTrue("Counter for 'Job' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindJob() {
        Job obj = dod.getRandomJob();
        Assert.assertNotNull("Data on demand for 'Job' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Job' failed to provide an identifier", id);
        obj = jobService.findJob(id);
        Assert.assertNotNull("Find method for 'Job' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Job' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllJobs() {
        Assert.assertNotNull("Data on demand for 'Job' failed to initialize correctly", dod.getRandomJob());
        long count = jobService.countAllJobs();
        Assert.assertTrue("Too expensive to perform a find all test for 'Job', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Job> result = jobService.findAllJobs();
        Assert.assertNotNull("Find all method for 'Job' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Job' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindJobEntries() {
        Assert.assertNotNull("Data on demand for 'Job' failed to initialize correctly", dod.getRandomJob());
        long count = jobService.countAllJobs();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Job> result = jobService.findJobEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Job' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Job' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSaveJob() {
        Assert.assertNotNull("Data on demand for 'Job' failed to initialize correctly", dod.getRandomJob());
        Job obj = dod.getNewTransientJob(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Job' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Job' identifier to be null", obj.getId());
        jobService.saveJob(obj);
        Assert.assertNotNull("Expected 'Job' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteJob() {
        Job obj = dod.getRandomJob();
        Assert.assertNotNull("Data on demand for 'Job' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Job' failed to provide an identifier", id);
        obj = jobService.findJob(id);
        jobService.deleteJob(obj);
        Assert.assertNull("Failed to remove 'Job' with identifier '" + id + "'", jobService.findJob(id));
    }
}
