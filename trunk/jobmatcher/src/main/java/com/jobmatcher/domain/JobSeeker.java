package com.jobmatcher.domain;

import com.jobmatcher.reference.States;
import javax.persistence.Column;
import javax.persistence.Enumerated;
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
}
