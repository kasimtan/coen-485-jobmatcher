package com.jobmatcher.service;

import com.jobmatcher.domain.CoverLetter;
import com.jobmatcher.repository.CoverLetterRepository;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CoverLetterServiceImpl implements CoverLetterService {

	@Autowired
    CoverLetterRepository coverLetterRepository;

	public long countAllCoverLetters() {
        return coverLetterRepository.count();
    }

	public void deleteCoverLetter(CoverLetter coverLetter) {
        coverLetterRepository.delete(coverLetter);
    }

	public CoverLetter findCoverLetter(BigInteger id) {
        return coverLetterRepository.findOne(id);
    }

	public List<CoverLetter> findAllCoverLetters() {
        return coverLetterRepository.findAll();
    }

	public List<CoverLetter> findCoverLetterEntries(int firstResult, int maxResults) {
        return coverLetterRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCoverLetter(CoverLetter coverLetter) {
        coverLetterRepository.save(coverLetter);
    }

	public CoverLetter updateCoverLetter(CoverLetter coverLetter) {
        return coverLetterRepository.save(coverLetter);
    }
}
