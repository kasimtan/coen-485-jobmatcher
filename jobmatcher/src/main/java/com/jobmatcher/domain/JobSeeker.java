package com.jobmatcher.domain;

import com.jobmatcher.reference.States;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class JobSeeker {

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private String email;

    @NotNull
    @Size(min = 4, max = 45)
    private String password;

    @NotNull
    @Size(min = 4, max = 255)
    private String address;

    @NotNull
    @Size(min = 4, max = 45)
    private String city;

    @NotNull
    @Enumerated
    private States states;

    @NotNull
    @Size(min = 5, max = 5)
    private String zip;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	public static JobSeeker fromJsonToJobSeeker(String json) {
        return new JSONDeserializer<JobSeeker>().use(null, JobSeeker.class).deserialize(json);
    }

	public static String toJsonArray(Collection<JobSeeker> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	public static Collection<JobSeeker> fromJsonArrayToJobSeekers(String json) {
        return new JSONDeserializer<List<JobSeeker>>().use(null, ArrayList.class).use("values", JobSeeker.class).deserialize(json);
    }

	@Id
    private BigInteger id;

	public BigInteger getId() {
        return this.id;
    }

	public void setId(BigInteger id) {
        this.id = id;
    }

	public String getEmail() {
        return this.email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public String getPassword() {
        return this.password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public String getAddress() {
        return this.address;
    }

	public void setAddress(String address) {
        this.address = address;
    }

	public String getCity() {
        return this.city;
    }

	public void setCity(String city) {
        this.city = city;
    }

	public States getStates() {
        return this.states;
    }

	public void setStates(States states) {
        this.states = states;
    }

	public String getZip() {
        return this.zip;
    }

	public void setZip(String zip) {
        this.zip = zip;
    }
}
