// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.domain;

import com.jobmatcher.domain.ResumeDataOnDemand;
import com.jobmatcher.domain.ResumeIntegrationTest;
import com.jobmatcher.service.ResumeService;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

privileged aspect ResumeIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ResumeIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ResumeIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    @Autowired
    ResumeDataOnDemand ResumeIntegrationTest.dod;
    
    @Autowired
    ResumeService ResumeIntegrationTest.resumeService;
    
    @Test
    public void ResumeIntegrationTest.testCountAllResumes() {
        Assert.assertNotNull("Data on demand for 'Resume' failed to initialize correctly", dod.getRandomResume());
        long count = resumeService.countAllResumes();
        Assert.assertTrue("Counter for 'Resume' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ResumeIntegrationTest.testFindResume() {
        Resume obj = dod.getRandomResume();
        Assert.assertNotNull("Data on demand for 'Resume' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Resume' failed to provide an identifier", id);
        obj = resumeService.findResume(id);
        Assert.assertNotNull("Find method for 'Resume' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Resume' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ResumeIntegrationTest.testFindAllResumes() {
        Assert.assertNotNull("Data on demand for 'Resume' failed to initialize correctly", dod.getRandomResume());
        long count = resumeService.countAllResumes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Resume', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Resume> result = resumeService.findAllResumes();
        Assert.assertNotNull("Find all method for 'Resume' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Resume' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ResumeIntegrationTest.testFindResumeEntries() {
        Assert.assertNotNull("Data on demand for 'Resume' failed to initialize correctly", dod.getRandomResume());
        long count = resumeService.countAllResumes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Resume> result = resumeService.findResumeEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Resume' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Resume' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ResumeIntegrationTest.testSaveResume() {
        Assert.assertNotNull("Data on demand for 'Resume' failed to initialize correctly", dod.getRandomResume());
        Resume obj = dod.getNewTransientResume(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Resume' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Resume' identifier to be null", obj.getId());
        resumeService.saveResume(obj);
        Assert.assertNotNull("Expected 'Resume' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ResumeIntegrationTest.testDeleteResume() {
        Resume obj = dod.getRandomResume();
        Assert.assertNotNull("Data on demand for 'Resume' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Resume' failed to provide an identifier", id);
        obj = resumeService.findResume(id);
        resumeService.deleteResume(obj);
        Assert.assertNull("Failed to remove 'Resume' with identifier '" + id + "'", resumeService.findResume(id));
    }
    
}
