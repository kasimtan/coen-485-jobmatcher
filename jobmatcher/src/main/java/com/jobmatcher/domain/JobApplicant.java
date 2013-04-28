package com.jobmatcher.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Persistent
@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson(deepSerialize = true)
public class JobApplicant {

    @NotNull
    @ManyToOne
    private JobSeeker jobSeeker;

    @NotNull
    @ManyToOne
    private Job job;

    @NotNull
    @ManyToOne
    private Resume resume;

    @NotNull
    @ManyToOne
    private CoverLetter coverLetter;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	public static JobApplicant fromJsonToJobApplicant(String json) {
        return new JSONDeserializer<JobApplicant>().use(null, JobApplicant.class).deserialize(json);
    }

	public static String toJsonArray(Collection<JobApplicant> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	public static Collection<JobApplicant> fromJsonArrayToJobApplicants(String json) {
        return new JSONDeserializer<List<JobApplicant>>().use(null, ArrayList.class).use("values", JobApplicant.class).deserialize(json);
    }

	@Id
    private BigInteger id;

	public BigInteger getId() {
        return this.id;
    }

	public void setId(BigInteger id) {
        this.id = id;
    }

	public JobSeeker getJobSeeker() {
        return this.jobSeeker;
    }

	public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

	public Job getJob() {
        return this.job;
    }

	public void setJob(Job job) {
        this.job = job;
    }

	public Resume getResume() {
        return this.resume;
    }

	public void setResume(Resume resume) {
        this.resume = resume;
    }

	public CoverLetter getCoverLetter() {
        return this.coverLetter;
    }

	public void setCoverLetter(CoverLetter coverLetter) {
        this.coverLetter = coverLetter;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
