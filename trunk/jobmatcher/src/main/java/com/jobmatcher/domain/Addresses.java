package com.jobmatcher.domain;

import com.jobmatcher.reference.States;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

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
}
