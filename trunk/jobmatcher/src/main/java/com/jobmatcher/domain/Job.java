package com.jobmatcher.domain;

import com.jobmatcher.reference.ExperienceLevel;
import com.jobmatcher.reference.Industry;
import com.jobmatcher.reference.JobType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson(deepSerialize = true)
public class Job {

    @NotNull
    @Size(min = 2, max = 255)
    private String jobTitle;

    @NotNull
    @Enumerated
    private JobType jobType;

    @Enumerated
    private ExperienceLevel experienceLevel;

    @Enumerated
    private Industry industry;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Addresses> address = new HashSet<Addresses>();

    @Size(max = 255)
    private String companyDescription;

    @Size(max = 255)
    private String desiredSkills;

    @NotNull
    @Size(min = 2, max = 255)
    private String jobDescription;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date jobPostedDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date jobExpirationDate;

    @NotNull
    @ManyToOne
    private HiringManager hiringManager;
}
