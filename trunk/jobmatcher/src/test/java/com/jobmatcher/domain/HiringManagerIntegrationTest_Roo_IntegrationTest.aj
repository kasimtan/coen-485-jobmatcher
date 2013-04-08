// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.domain;

import com.jobmatcher.domain.HiringManagerDataOnDemand;
import com.jobmatcher.domain.HiringManagerIntegrationTest;
import com.jobmatcher.service.HiringManagerService;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

privileged aspect HiringManagerIntegrationTest_Roo_IntegrationTest {
    
    declare @type: HiringManagerIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: HiringManagerIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    @Autowired
    HiringManagerDataOnDemand HiringManagerIntegrationTest.dod;
    
    @Autowired
    HiringManagerService HiringManagerIntegrationTest.hiringManagerService;
    
    @Test
    public void HiringManagerIntegrationTest.testCountAllHiringManagers() {
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to initialize correctly", dod.getRandomHiringManager());
        long count = hiringManagerService.countAllHiringManagers();
        Assert.assertTrue("Counter for 'HiringManager' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void HiringManagerIntegrationTest.testFindHiringManager() {
        HiringManager obj = dod.getRandomHiringManager();
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to provide an identifier", id);
        obj = hiringManagerService.findHiringManager(id);
        Assert.assertNotNull("Find method for 'HiringManager' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'HiringManager' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void HiringManagerIntegrationTest.testFindAllHiringManagers() {
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to initialize correctly", dod.getRandomHiringManager());
        long count = hiringManagerService.countAllHiringManagers();
        Assert.assertTrue("Too expensive to perform a find all test for 'HiringManager', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<HiringManager> result = hiringManagerService.findAllHiringManagers();
        Assert.assertNotNull("Find all method for 'HiringManager' illegally returned null", result);
        Assert.assertTrue("Find all method for 'HiringManager' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void HiringManagerIntegrationTest.testFindHiringManagerEntries() {
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to initialize correctly", dod.getRandomHiringManager());
        long count = hiringManagerService.countAllHiringManagers();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<HiringManager> result = hiringManagerService.findHiringManagerEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'HiringManager' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'HiringManager' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void HiringManagerIntegrationTest.testSaveHiringManager() {
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to initialize correctly", dod.getRandomHiringManager());
        HiringManager obj = dod.getNewTransientHiringManager(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'HiringManager' identifier to be null", obj.getId());
        hiringManagerService.saveHiringManager(obj);
        Assert.assertNotNull("Expected 'HiringManager' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void HiringManagerIntegrationTest.testDeleteHiringManager() {
        HiringManager obj = dod.getRandomHiringManager();
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'HiringManager' failed to provide an identifier", id);
        obj = hiringManagerService.findHiringManager(id);
        hiringManagerService.deleteHiringManager(obj);
        Assert.assertNull("Failed to remove 'HiringManager' with identifier '" + id + "'", hiringManagerService.findHiringManager(id));
    }
    
}
