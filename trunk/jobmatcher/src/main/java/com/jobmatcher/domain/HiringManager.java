package com.jobmatcher.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
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
import org.springframework.util.DigestUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Persistent
@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson(deepSerialize = true)
public class HiringManager {

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private String email;

    @NotNull
    @Size(min = 4, max = 45)
    private String password;

    @NotNull
    @Size(min = 1, max = 255)
    private String companyName;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Addresses> address = new HashSet<Addresses>();

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
        this.password = encryptPassword(password);
    }

	public String getCompanyName() {
        return this.companyName;
    }

	public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

	public Set<Addresses> getAddress() {
        return this.address;
    }

	public void setAddress(Set<Addresses> address) {
        this.address = address;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	public static HiringManager fromJsonToHiringManager(String json) {
        return new JSONDeserializer<HiringManager>().use(null, HiringManager.class).deserialize(json);
    }

	public static String toJsonArray(Collection<HiringManager> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	public static Collection<HiringManager> fromJsonArrayToHiringManagers(String json) {
        return new JSONDeserializer<List<HiringManager>>().use(null, ArrayList.class).use("values", HiringManager.class).deserialize(json);
    }

    protected String encryptPassword(String password) {
	    if(password.length() == 32) {
	    	//prevent encryption if already encrypted
	    	return password;
	    } else {
	    	password = DigestUtils.md5DigestAsHex(password.getBytes());
	    }
           
            
       
        return password;
    }
}
