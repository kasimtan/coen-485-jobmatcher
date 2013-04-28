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
public class SavedJob {

    @NotNull
    @ManyToOne
    private JobSeeker jobSeeker;

    @NotNull
    @ManyToOne
    private Job job;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	public static SavedJob fromJsonToSavedJob(String json) {
        return new JSONDeserializer<SavedJob>().use(null, SavedJob.class).deserialize(json);
    }

	public static String toJsonArray(Collection<SavedJob> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	public static Collection<SavedJob> fromJsonArrayToSavedJobs(String json) {
        return new JSONDeserializer<List<SavedJob>>().use(null, ArrayList.class).use("values", SavedJob.class).deserialize(json);
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

	@Id
    private BigInteger id;

	public BigInteger getId() {
        return this.id;
    }

	public void setId(BigInteger id) {
        this.id = id;
    }
}
