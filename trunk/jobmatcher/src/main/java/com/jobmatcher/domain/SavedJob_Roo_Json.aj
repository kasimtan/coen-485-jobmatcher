// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.domain;

import com.jobmatcher.domain.SavedJob;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect SavedJob_Roo_Json {
    
    public String SavedJob.toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }
    
    public static SavedJob SavedJob.fromJsonToSavedJob(String json) {
        return new JSONDeserializer<SavedJob>().use(null, SavedJob.class).deserialize(json);
    }
    
    public static String SavedJob.toJsonArray(Collection<SavedJob> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }
    
    public static Collection<SavedJob> SavedJob.fromJsonArrayToSavedJobs(String json) {
        return new JSONDeserializer<List<SavedJob>>().use(null, ArrayList.class).use("values", SavedJob.class).deserialize(json);
    }
    
}
