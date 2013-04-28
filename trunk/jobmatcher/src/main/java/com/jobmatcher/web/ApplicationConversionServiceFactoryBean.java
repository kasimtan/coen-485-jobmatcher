package com.jobmatcher.web;

import com.jobmatcher.domain.Addresses;
import com.jobmatcher.domain.CoverLetter;
import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.domain.Job;
import com.jobmatcher.domain.JobApplicant;
import com.jobmatcher.domain.JobSeeker;
import com.jobmatcher.domain.Resume;
import com.jobmatcher.domain.SavedJob;
import com.jobmatcher.domain.SavedJobApplicant;
import com.jobmatcher.service.AddressService;
import com.jobmatcher.service.CoverLetterService;
import com.jobmatcher.service.HiringManagerService;
import com.jobmatcher.service.JobApplicantService;
import com.jobmatcher.service.JobSeekerService;
import com.jobmatcher.service.JobService;
import com.jobmatcher.service.ResumeService;
import com.jobmatcher.service.SavedJobApplicantService;
import com.jobmatcher.service.SavedJobService;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    AddressService addressService;

	@Autowired
    CoverLetterService coverLetterService;

	@Autowired
    HiringManagerService hiringManagerService;

	@Autowired
    JobService jobService;

	@Autowired
    JobApplicantService jobApplicantService;

	@Autowired
    JobSeekerService jobSeekerService;

	@Autowired
    ResumeService resumeService;

	@Autowired
    SavedJobService savedJobService;

	@Autowired
    SavedJobApplicantService savedJobApplicantService;

	public Converter<Addresses, String> getAddressesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.Addresses, java.lang.String>() {
            public String convert(Addresses addresses) {
                return new StringBuilder().append(addresses.getAddress()).append(' ').append(addresses.getCity()).append(' ').append(addresses.getZip()).toString();
            }
        };
    }

	public Converter<BigInteger, Addresses> getIdToAddressesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.Addresses>() {
            public com.jobmatcher.domain.Addresses convert(java.math.BigInteger id) {
                return addressService.findAddresses(id);
            }
        };
    }

	public Converter<String, Addresses> getStringToAddressesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.Addresses>() {
            public com.jobmatcher.domain.Addresses convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Addresses.class);
            }
        };
    }

	public Converter<CoverLetter, String> getCoverLetterToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.CoverLetter, java.lang.String>() {
            public String convert(CoverLetter coverLetter) {
                return new StringBuilder().append(coverLetter.getName()).toString();
            }
        };
    }

	public Converter<BigInteger, CoverLetter> getIdToCoverLetterConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.CoverLetter>() {
            public com.jobmatcher.domain.CoverLetter convert(java.math.BigInteger id) {
                return coverLetterService.findCoverLetter(id);
            }
        };
    }

	public Converter<String, CoverLetter> getStringToCoverLetterConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.CoverLetter>() {
            public com.jobmatcher.domain.CoverLetter convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), CoverLetter.class);
            }
        };
    }

	public Converter<HiringManager, String> getHiringManagerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.HiringManager, java.lang.String>() {
            public String convert(HiringManager hiringManager) {
                return new StringBuilder().append(hiringManager.getEmail()).append(' ').append(hiringManager.getPassword()).append(' ').append(hiringManager.getCompanyName()).toString();
            }
        };
    }

	public Converter<BigInteger, HiringManager> getIdToHiringManagerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.HiringManager>() {
            public com.jobmatcher.domain.HiringManager convert(java.math.BigInteger id) {
                return hiringManagerService.findHiringManager(id);
            }
        };
    }

	public Converter<String, HiringManager> getStringToHiringManagerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.HiringManager>() {
            public com.jobmatcher.domain.HiringManager convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), HiringManager.class);
            }
        };
    }

	public Converter<Job, String> getJobToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.Job, java.lang.String>() {
            public String convert(Job job) {
                return new StringBuilder().append(job.getJobTitle()).append(' ').append(job.getCompanyDescription()).append(' ').append(job.getDesiredSkills()).append(' ').append(job.getJobDescription()).toString();
            }
        };
    }

	public Converter<BigInteger, Job> getIdToJobConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.Job>() {
            public com.jobmatcher.domain.Job convert(java.math.BigInteger id) {
                return jobService.findJob(id);
            }
        };
    }

	public Converter<String, Job> getStringToJobConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.Job>() {
            public com.jobmatcher.domain.Job convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Job.class);
            }
        };
    }

	public Converter<JobApplicant, String> getJobApplicantToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.JobApplicant, java.lang.String>() {
            public String convert(JobApplicant jobApplicant) {
                return "(no displayable fields)";
            }
        };
    }

	public Converter<BigInteger, JobApplicant> getIdToJobApplicantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.JobApplicant>() {
            public com.jobmatcher.domain.JobApplicant convert(java.math.BigInteger id) {
                return jobApplicantService.findJobApplicant(id);
            }
        };
    }

	public Converter<String, JobApplicant> getStringToJobApplicantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.JobApplicant>() {
            public com.jobmatcher.domain.JobApplicant convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), JobApplicant.class);
            }
        };
    }

	public Converter<JobSeeker, String> getJobSeekerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.JobSeeker, java.lang.String>() {
            public String convert(JobSeeker jobSeeker) {
                return new StringBuilder().append(jobSeeker.getEmail()).append(' ').append(jobSeeker.getPassword()).append(' ').append(jobSeeker.getAddress()).append(' ').append(jobSeeker.getCity()).toString();
            }
        };
    }

	public Converter<BigInteger, JobSeeker> getIdToJobSeekerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.JobSeeker>() {
            public com.jobmatcher.domain.JobSeeker convert(java.math.BigInteger id) {
                return jobSeekerService.findJobSeeker(id);
            }
        };
    }

	public Converter<String, JobSeeker> getStringToJobSeekerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.JobSeeker>() {
            public com.jobmatcher.domain.JobSeeker convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), JobSeeker.class);
            }
        };
    }

	public Converter<Resume, String> getResumeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.Resume, java.lang.String>() {
            public String convert(Resume resume) {
                return new StringBuilder().append(resume.getName()).toString();
            }
        };
    }

	public Converter<BigInteger, Resume> getIdToResumeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.Resume>() {
            public com.jobmatcher.domain.Resume convert(java.math.BigInteger id) {
                return resumeService.findResume(id);
            }
        };
    }

	public Converter<String, Resume> getStringToResumeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.Resume>() {
            public com.jobmatcher.domain.Resume convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Resume.class);
            }
        };
    }

	public Converter<SavedJob, String> getSavedJobToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.SavedJob, java.lang.String>() {
            public String convert(SavedJob savedJob) {
                return "(no displayable fields)";
            }
        };
    }

	public Converter<BigInteger, SavedJob> getIdToSavedJobConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.SavedJob>() {
            public com.jobmatcher.domain.SavedJob convert(java.math.BigInteger id) {
                return savedJobService.findSavedJob(id);
            }
        };
    }

	public Converter<String, SavedJob> getStringToSavedJobConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.SavedJob>() {
            public com.jobmatcher.domain.SavedJob convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), SavedJob.class);
            }
        };
    }

	public Converter<SavedJobApplicant, String> getSavedJobApplicantToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.jobmatcher.domain.SavedJobApplicant, java.lang.String>() {
            public String convert(SavedJobApplicant savedJobApplicant) {
                return "(no displayable fields)";
            }
        };
    }

	public Converter<BigInteger, SavedJobApplicant> getIdToSavedJobApplicantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.jobmatcher.domain.SavedJobApplicant>() {
            public com.jobmatcher.domain.SavedJobApplicant convert(java.math.BigInteger id) {
                return savedJobApplicantService.findSavedJobApplicant(id);
            }
        };
    }

	public Converter<String, SavedJobApplicant> getStringToSavedJobApplicantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.jobmatcher.domain.SavedJobApplicant>() {
            public com.jobmatcher.domain.SavedJobApplicant convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), SavedJobApplicant.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAddressesToStringConverter());
        registry.addConverter(getIdToAddressesConverter());
        registry.addConverter(getStringToAddressesConverter());
        registry.addConverter(getCoverLetterToStringConverter());
        registry.addConverter(getIdToCoverLetterConverter());
        registry.addConverter(getStringToCoverLetterConverter());
        registry.addConverter(getHiringManagerToStringConverter());
        registry.addConverter(getIdToHiringManagerConverter());
        registry.addConverter(getStringToHiringManagerConverter());
        registry.addConverter(getJobToStringConverter());
        registry.addConverter(getIdToJobConverter());
        registry.addConverter(getStringToJobConverter());
        registry.addConverter(getJobApplicantToStringConverter());
        registry.addConverter(getIdToJobApplicantConverter());
        registry.addConverter(getStringToJobApplicantConverter());
        registry.addConverter(getJobSeekerToStringConverter());
        registry.addConverter(getIdToJobSeekerConverter());
        registry.addConverter(getStringToJobSeekerConverter());
        registry.addConverter(getResumeToStringConverter());
        registry.addConverter(getIdToResumeConverter());
        registry.addConverter(getStringToResumeConverter());
        registry.addConverter(getSavedJobToStringConverter());
        registry.addConverter(getIdToSavedJobConverter());
        registry.addConverter(getStringToSavedJobConverter());
        registry.addConverter(getSavedJobApplicantToStringConverter());
        registry.addConverter(getIdToSavedJobApplicantConverter());
        registry.addConverter(getStringToSavedJobApplicantConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
