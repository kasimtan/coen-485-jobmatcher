package com.jobmatcher.domain;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson(deepSerialize = true)
public class Resume {

    @NotNull
    @ManyToOne
    private JobSeeker jobSeeker;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @RooUploadedFile(contentType = "application/pdf")
    @Lob
    private byte[] file;
}
