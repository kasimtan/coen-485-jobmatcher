// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.domain;

import com.jobmatcher.domain.JobSeeker;
import com.jobmatcher.reference.States;

privileged aspect JobSeeker_Roo_JavaBean {
    
    public String JobSeeker.getEmail() {
        return this.email;
    }
    
    public void JobSeeker.setEmail(String email) {
        this.email = email;
    }
    
    public String JobSeeker.getPassword() {
        return this.password;
    }
    
    public void JobSeeker.setPassword(String password) {
        this.password = password;
    }
    
    public String JobSeeker.getAddress() {
        return this.address;
    }
    
    public void JobSeeker.setAddress(String address) {
        this.address = address;
    }
    
    public String JobSeeker.getCity() {
        return this.city;
    }
    
    public void JobSeeker.setCity(String city) {
        this.city = city;
    }
    
    public States JobSeeker.getStates() {
        return this.states;
    }
    
    public void JobSeeker.setStates(States states) {
        this.states = states;
    }
    
    public String JobSeeker.getZip() {
        return this.zip;
    }
    
    public void JobSeeker.setZip(String zip) {
        this.zip = zip;
    }
    
}
