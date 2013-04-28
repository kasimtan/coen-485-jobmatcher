package com.jobmatcher.domain;

import com.jobmatcher.service.CoverLetterService;
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
@RooIntegrationTest(entity = CoverLetter.class, transactional = false)
public class CoverLetterIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    CoverLetterDataOnDemand dod;

	@Autowired
    CoverLetterService coverLetterService;

	@Test
    public void testCountAllCoverLetters() {
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to initialize correctly", dod.getRandomCoverLetter());
        long count = coverLetterService.countAllCoverLetters();
        Assert.assertTrue("Counter for 'CoverLetter' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCoverLetter() {
        CoverLetter obj = dod.getRandomCoverLetter();
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to provide an identifier", id);
        obj = coverLetterService.findCoverLetter(id);
        Assert.assertNotNull("Find method for 'CoverLetter' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CoverLetter' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCoverLetters() {
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to initialize correctly", dod.getRandomCoverLetter());
        long count = coverLetterService.countAllCoverLetters();
        Assert.assertTrue("Too expensive to perform a find all test for 'CoverLetter', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CoverLetter> result = coverLetterService.findAllCoverLetters();
        Assert.assertNotNull("Find all method for 'CoverLetter' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CoverLetter' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCoverLetterEntries() {
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to initialize correctly", dod.getRandomCoverLetter());
        long count = coverLetterService.countAllCoverLetters();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<CoverLetter> result = coverLetterService.findCoverLetterEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'CoverLetter' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CoverLetter' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSaveCoverLetter() {
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to initialize correctly", dod.getRandomCoverLetter());
        CoverLetter obj = dod.getNewTransientCoverLetter(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CoverLetter' identifier to be null", obj.getId());
        coverLetterService.saveCoverLetter(obj);
        Assert.assertNotNull("Expected 'CoverLetter' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteCoverLetter() {
        CoverLetter obj = dod.getRandomCoverLetter();
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CoverLetter' failed to provide an identifier", id);
        obj = coverLetterService.findCoverLetter(id);
        coverLetterService.deleteCoverLetter(obj);
        Assert.assertNull("Failed to remove 'CoverLetter' with identifier '" + id + "'", coverLetterService.findCoverLetter(id));
    }
}
