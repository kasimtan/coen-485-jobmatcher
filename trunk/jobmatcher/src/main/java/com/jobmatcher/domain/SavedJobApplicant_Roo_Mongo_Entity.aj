// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.domain;

import com.jobmatcher.domain.SavedJobApplicant;
import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

privileged aspect SavedJobApplicant_Roo_Mongo_Entity {
    
    declare @type: SavedJobApplicant: @Persistent;
    
    @Id
    private BigInteger SavedJobApplicant.id;
    
    public BigInteger SavedJobApplicant.getId() {
        return this.id;
    }
    
    public void SavedJobApplicant.setId(BigInteger id) {
        this.id = id;
    }
    
}
