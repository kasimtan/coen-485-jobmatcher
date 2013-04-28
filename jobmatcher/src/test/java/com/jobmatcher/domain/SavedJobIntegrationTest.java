package com.jobmatcher.domain;

import com.jobmatcher.service.SavedJobService;
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
@RooIntegrationTest(entity = SavedJob.class, transactional = false)
public class SavedJobIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SavedJobDataOnDemand dod;

	@Autowired
    SavedJobService savedJobService;

	@Test
    public void testCountAllSavedJobs() {
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to initialize correctly", dod.getRandomSavedJob());
        long count = savedJobService.countAllSavedJobs();
        Assert.assertTrue("Counter for 'SavedJob' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindSavedJob() {
        SavedJob obj = dod.getRandomSavedJob();
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to provide an identifier", id);
        obj = savedJobService.findSavedJob(id);
        Assert.assertNotNull("Find method for 'SavedJob' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SavedJob' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllSavedJobs() {
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to initialize correctly", dod.getRandomSavedJob());
        long count = savedJobService.countAllSavedJobs();
        Assert.assertTrue("Too expensive to perform a find all test for 'SavedJob', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SavedJob> result = savedJobService.findAllSavedJobs();
        Assert.assertNotNull("Find all method for 'SavedJob' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SavedJob' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindSavedJobEntries() {
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to initialize correctly", dod.getRandomSavedJob());
        long count = savedJobService.countAllSavedJobs();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<SavedJob> result = savedJobService.findSavedJobEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'SavedJob' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SavedJob' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSaveSavedJob() {
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to initialize correctly", dod.getRandomSavedJob());
        SavedJob obj = dod.getNewTransientSavedJob(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SavedJob' identifier to be null", obj.getId());
        savedJobService.saveSavedJob(obj);
        Assert.assertNotNull("Expected 'SavedJob' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteSavedJob() {
        SavedJob obj = dod.getRandomSavedJob();
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SavedJob' failed to provide an identifier", id);
        obj = savedJobService.findSavedJob(id);
        savedJobService.deleteSavedJob(obj);
        Assert.assertNull("Failed to remove 'SavedJob' with identifier '" + id + "'", savedJobService.findSavedJob(id));
    }
}
