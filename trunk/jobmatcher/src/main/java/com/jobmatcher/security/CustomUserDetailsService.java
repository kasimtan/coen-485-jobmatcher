package com.jobmatcher.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.domain.JobSeeker;

public class CustomUserDetailsService implements UserDetailsService {

    private MongoTemplate mongoTemplate;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        String email = "";
        String password = "";
        UserDetail userDetail = null;
        JobSeeker user = getJobSeekerDetail(username);
        if(user == null) {
            HiringManager managerUser = getHiringManagerDetail(username);
            if(managerUser != null) {
                email = managerUser.getEmail();
                password = managerUser.getPassword();
                userDetail = new UserDetail(email, password, true, true, true, true, getAuthorities());
                userDetail.setHiringManager(managerUser);
            }
        }
        else {
            email = user.getEmail();
            password = user.getPassword();
            userDetail = new UserDetail(email, password, true, true, true, true, getAuthorities());
            userDetail.setJobSeeker(user);
        }
        
        
        
        return userDetail;
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authList;
    }

    public JobSeeker getJobSeekerDetail(String username) {
        MongoOperations mongoOperation = (MongoOperations)mongoTemplate;
        JobSeeker user = mongoOperation.findOne(
                new Query(Criteria.where("email").is(username)),
                JobSeeker.class);
        return user;
    }

    public HiringManager getHiringManagerDetail(String username) {
        MongoOperations mongoOperation = (MongoOperations)mongoTemplate;
        HiringManager user = mongoOperation.findOne(
                new Query(Criteria.where("email").is(username)),
                HiringManager.class);
        return user;
    }

}