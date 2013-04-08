package com.jobmatcher.domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

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
}
