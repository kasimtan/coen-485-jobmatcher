package com.jobmatcher.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Job.class, transactional = false)
public class JobIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
