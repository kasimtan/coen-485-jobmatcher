package com.jobmatcher.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.domain.JobSeeker;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String JOBSEEKER_URL = "/jobSeeker.html";
    private static final String HIRINGMANGER_URL = "/hiringManager.html";
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

//        // changeLastLoginTime(username)
//        userService.changeLastLoginTime(authentication.getName());

    	UserDetail userDetail = (UserDetail) authentication.getPrincipal();
    	HiringManager currentHiringManager = userDetail.getHiringManager();
    	JobSeeker currentJobSeeker = userDetail.getJobSeeker();
    	if(currentHiringManager != null) {
    		setDefaultTargetUrl(HIRINGMANGER_URL);
    	} else if(currentJobSeeker != null) {
    		setDefaultTargetUrl(JOBSEEKER_URL);
    	}
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
}