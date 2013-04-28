package com.jobmatcher.domain;

import com.jobmatcher.service.JobApplicantService;
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
@RooIntegrationTest(entity = JobApplicant.class, transactional = false)
public class JobApplicantIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    JobApplicantDataOnDemand dod;

	@Autowired
    JobApplicantService jobApplicantService;

	@Test
    public void testCountAllJobApplicants() {
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to initialize correctly", dod.getRandomJobApplicant());
        long count = jobApplicantService.countAllJobApplicants();
        Assert.assertTrue("Counter for 'JobApplicant' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindJobApplicant() {
        JobApplicant obj = dod.getRandomJobApplicant();
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to provide an identifier", id);
        obj = jobApplicantService.findJobApplicant(id);
        Assert.assertNotNull("Find method for 'JobApplicant' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'JobApplicant' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllJobApplicants() {
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to initialize correctly", dod.getRandomJobApplicant());
        long count = jobApplicantService.countAllJobApplicants();
        Assert.assertTrue("Too expensive to perform a find all test for 'JobApplicant', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<JobApplicant> result = jobApplicantService.findAllJobApplicants();
        Assert.assertNotNull("Find all method for 'JobApplicant' illegally returned null", result);
        Assert.assertTrue("Find all method for 'JobApplicant' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindJobApplicantEntries() {
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to initialize correctly", dod.getRandomJobApplicant());
        long count = jobApplicantService.countAllJobApplicants();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<JobApplicant> result = jobApplicantService.findJobApplicantEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'JobApplicant' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'JobApplicant' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSaveJobApplicant() {
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to initialize correctly", dod.getRandomJobApplicant());
        JobApplicant obj = dod.getNewTransientJobApplicant(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'JobApplicant' identifier to be null", obj.getId());
        jobApplicantService.saveJobApplicant(obj);
        Assert.assertNotNull("Expected 'JobApplicant' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteJobApplicant() {
        JobApplicant obj = dod.getRandomJobApplicant();
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'JobApplicant' failed to provide an identifier", id);
        obj = jobApplicantService.findJobApplicant(id);
        jobApplicantService.deleteJobApplicant(obj);
        Assert.assertNull("Failed to remove 'JobApplicant' with identifier '" + id + "'", jobApplicantService.findJobApplicant(id));
    }
}
