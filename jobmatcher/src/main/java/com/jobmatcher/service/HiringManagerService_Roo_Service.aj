// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.service;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.service.HiringManagerService;
import java.math.BigInteger;
import java.util.List;

privileged aspect HiringManagerService_Roo_Service {
    
    public abstract long HiringManagerService.countAllHiringManagers();    
    public abstract void HiringManagerService.deleteHiringManager(HiringManager hiringManager);    
    public abstract HiringManager HiringManagerService.findHiringManager(BigInteger id);    
    public abstract List<HiringManager> HiringManagerService.findAllHiringManagers();    
    public abstract List<HiringManager> HiringManagerService.findHiringManagerEntries(int firstResult, int maxResults);    
    public abstract void HiringManagerService.saveHiringManager(HiringManager hiringManager);    
    public abstract HiringManager HiringManagerService.updateHiringManager(HiringManager hiringManager);    
}
