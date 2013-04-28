package com.jobmatcher.service;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.repository.HiringManagerRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class HiringManagerServiceImpl implements HiringManagerService {

	@Autowired
    HiringManagerRepository hiringManagerRepository;

	public long countAllHiringManagers() {
        return hiringManagerRepository.count();
    }

	public void deleteHiringManager(HiringManager hiringManager) {
        hiringManagerRepository.delete(hiringManager);
    }

	public HiringManager findHiringManager(BigInteger id) {
        return hiringManagerRepository.findOne(id);
    }

	public List<HiringManager> findAllHiringManagers() {
        return hiringManagerRepository.findAll();
    }

	public List<HiringManager> findHiringManagerEntries(int firstResult, int maxResults) {
        return hiringManagerRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveHiringManager(HiringManager hiringManager) {
        hiringManagerRepository.save(hiringManager);
    }

	public HiringManager updateHiringManager(HiringManager hiringManager) {
        return hiringManagerRepository.save(hiringManager);
    }
}
