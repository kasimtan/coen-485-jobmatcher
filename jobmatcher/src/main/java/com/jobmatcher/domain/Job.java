package com.jobmatcher.domain;

import com.jobmatcher.reference.ExperienceLevel;
import com.jobmatcher.reference.Industry;
import com.jobmatcher.reference.JobType;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Persistent
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
    private String companyName;
    
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

	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	public static Job fromJsonToJob(String json) {
        return new JSONDeserializer<Job>().use(null, Job.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Job> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Job> fromJsonArrayToJobs(String json) {
        return new JSONDeserializer<List<Job>>().use(null, ArrayList.class).use("values", Job.class).deserialize(json);
    }

	public String getJobTitle() {
        return this.jobTitle;
    }

	public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

	public JobType getJobType() {
        return this.jobType;
    }

	public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

	public ExperienceLevel getExperienceLevel() {
        return this.experienceLevel;
    }

	public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

	public Industry getIndustry() {
        return this.industry;
    }

	public void setIndustry(Industry industry) {
        this.industry = industry;
    }

	public Set<Addresses> getAddress() {
        return this.address;
    }

	public void setAddress(Set<Addresses> address) {
        this.address = address;
    }

	public String getCompanyDescription() {
        return this.companyDescription;
    }

	public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

	public String getDesiredSkills() {
        return this.desiredSkills;
    }

	public void setDesiredSkills(String desiredSkills) {
        this.desiredSkills = desiredSkills;
    }

	public String getJobDescription() {
        return this.jobDescription;
    }

	public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

	public Date getJobPostedDate() {
        return this.jobPostedDate;
    }

	public void setJobPostedDate(Date jobPostedDate) {
        this.jobPostedDate = jobPostedDate;
    }

	public Date getJobExpirationDate() {
        return this.jobExpirationDate;
    }

	public void setJobExpirationDate(Date jobExpirationDate) {
        this.jobExpirationDate = jobExpirationDate;
    }

	public HiringManager getHiringManager() {
        return this.hiringManager;
    }

	public void setHiringManager(HiringManager hiringManager) {
        this.hiringManager = hiringManager;
    }
	
	

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Id
    private BigInteger id;

	public BigInteger getId() {
        return this.id;
    }

	public void setId(BigInteger id) {
        this.id = id;
    }
}
