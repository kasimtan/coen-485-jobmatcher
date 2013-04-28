package com.jobmatcher.service;

import com.jobmatcher.domain.CoverLetter;
import java.math.BigInteger;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.jobmatcher.domain.CoverLetter.class })
public interface CoverLetterService {

	public abstract long countAllCoverLetters();


	public abstract void deleteCoverLetter(CoverLetter coverLetter);


	public abstract CoverLetter findCoverLetter(BigInteger id);


	public abstract List<CoverLetter> findAllCoverLetters();


	public abstract List<CoverLetter> findCoverLetterEntries(int firstResult, int maxResults);


	public abstract void saveCoverLetter(CoverLetter coverLetter);


	public abstract CoverLetter updateCoverLetter(CoverLetter coverLetter);

}
