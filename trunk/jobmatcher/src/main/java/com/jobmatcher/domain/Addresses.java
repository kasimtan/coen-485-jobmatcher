package com.jobmatcher.domain;

import com.jobmatcher.reference.States;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
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
public class Addresses {

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

	public static Addresses fromJsonToAddresses(String json) {
        return new JSONDeserializer<Addresses>().use(null, Addresses.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Addresses> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Addresses> fromJsonArrayToAddresseses(String json) {
        return new JSONDeserializer<List<Addresses>>().use(null, ArrayList.class).use("values", Addresses.class).deserialize(json);
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

	@Id
    private BigInteger id;

	public BigInteger getId() {
        return this.id;
    }

	public void setId(BigInteger id) {
        this.id = id;
    }
}
