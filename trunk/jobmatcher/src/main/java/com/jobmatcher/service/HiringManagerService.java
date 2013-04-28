package com.jobmatcher.service;

import com.jobmatcher.domain.HiringManager;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.HiringManager.class })
public interface HiringManagerService {

	public abstract long countAllHiringManagers();


	public abstract void deleteHiringManager(HiringManager hiringManager);


	public abstract HiringManager findHiringManager(BigInteger id);


	public abstract List<HiringManager> findAllHiringManagers();


	public abstract List<HiringManager> findHiringManagerEntries(int firstResult, int maxResults);


	public abstract void saveHiringManager(HiringManager hiringManager);


	public abstract HiringManager updateHiringManager(HiringManager hiringManager);

}
