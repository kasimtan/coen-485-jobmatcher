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
public class SavedJobApplicant {

    @NotNull
    @ManyToOne
    private HiringManager hiringManager;

    @NotNull
    @ManyToOne
    private JobApplicant jobApplicant;

	@Id
    private BigInteger id;

	public BigInteger getId() {
        return this.id;
    }

	public void setId(BigInteger id) {
        this.id = id;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	public static SavedJobApplicant fromJsonToSavedJobApplicant(String json) {
        return new JSONDeserializer<SavedJobApplicant>().use(null, SavedJobApplicant.class).deserialize(json);
    }

	public static String toJsonArray(Collection<SavedJobApplicant> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	public static Collection<SavedJobApplicant> fromJsonArrayToSavedJobApplicants(String json) {
        return new JSONDeserializer<List<SavedJobApplicant>>().use(null, ArrayList.class).use("values", SavedJobApplicant.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public HiringManager getHiringManager() {
        return this.hiringManager;
    }

	public void setHiringManager(HiringManager hiringManager) {
        this.hiringManager = hiringManager;
    }

	public JobApplicant getJobApplicant() {
        return this.jobApplicant;
    }

	public void setJobApplicant(JobApplicant jobApplicant) {
        this.jobApplicant = jobApplicant;
    }
}
