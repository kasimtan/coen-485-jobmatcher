package com.jobmatcher.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

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
}
