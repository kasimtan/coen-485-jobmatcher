package com.jobmatcher.domain;

import com.jobmatcher.service.SavedJobApplicantService;
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
@RooIntegrationTest(entity = SavedJobApplicant.class, transactional = false)
public class SavedJobApplicantIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SavedJobApplicantDataOnDemand dod;

	@Autowired
    SavedJobApplicantService savedJobApplicantService;

	@Test
    public void testCountAllSavedJobApplicants() {
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to initialize correctly", dod.getRandomSavedJobApplicant());
        long count = savedJobApplicantService.countAllSavedJobApplicants();
        Assert.assertTrue("Counter for 'SavedJobApplicant' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindSavedJobApplicant() {
        SavedJobApplicant obj = dod.getRandomSavedJobApplicant();
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to provide an identifier", id);
        obj = savedJobApplicantService.findSavedJobApplicant(id);
        Assert.assertNotNull("Find method for 'SavedJobApplicant' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SavedJobApplicant' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllSavedJobApplicants() {
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to initialize correctly", dod.getRandomSavedJobApplicant());
        long count = savedJobApplicantService.countAllSavedJobApplicants();
        Assert.assertTrue("Too expensive to perform a find all test for 'SavedJobApplicant', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SavedJobApplicant> result = savedJobApplicantService.findAllSavedJobApplicants();
        Assert.assertNotNull("Find all method for 'SavedJobApplicant' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SavedJobApplicant' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindSavedJobApplicantEntries() {
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to initialize correctly", dod.getRandomSavedJobApplicant());
        long count = savedJobApplicantService.countAllSavedJobApplicants();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<SavedJobApplicant> result = savedJobApplicantService.findSavedJobApplicantEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'SavedJobApplicant' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SavedJobApplicant' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSaveSavedJobApplicant() {
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to initialize correctly", dod.getRandomSavedJobApplicant());
        SavedJobApplicant obj = dod.getNewTransientSavedJobApplicant(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SavedJobApplicant' identifier to be null", obj.getId());
        savedJobApplicantService.saveSavedJobApplicant(obj);
        Assert.assertNotNull("Expected 'SavedJobApplicant' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteSavedJobApplicant() {
        SavedJobApplicant obj = dod.getRandomSavedJobApplicant();
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SavedJobApplicant' failed to provide an identifier", id);
        obj = savedJobApplicantService.findSavedJobApplicant(id);
        savedJobApplicantService.deleteSavedJobApplicant(obj);
        Assert.assertNull("Failed to remove 'SavedJobApplicant' with identifier '" + id + "'", savedJobApplicantService.findSavedJobApplicant(id));
    }
}
