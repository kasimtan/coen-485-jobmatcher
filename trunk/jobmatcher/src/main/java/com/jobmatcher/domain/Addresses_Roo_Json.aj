// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.domain;

import com.jobmatcher.domain.Addresses;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Addresses_Roo_Json {
    
    public String Addresses.toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }
    
    public static Addresses Addresses.fromJsonToAddresses(String json) {
        return new JSONDeserializer<Addresses>().use(null, Addresses.class).deserialize(json);
    }
    
    public static String Addresses.toJsonArray(Collection<Addresses> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }
    
    public static Collection<Addresses> Addresses.fromJsonArrayToAddresseses(String json) {
        return new JSONDeserializer<List<Addresses>>().use(null, ArrayList.class).use("values", Addresses.class).deserialize(json);
    }
    
}
