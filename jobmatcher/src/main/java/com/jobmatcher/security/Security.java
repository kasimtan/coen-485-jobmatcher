package com.jobmatcher.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.domain.JobSeeker;

public class Security {

	public static  HiringManager getCurrentHiringManager() {
		UserDetail userDetail = getCurrentUserDetail() ;
		if(userDetail == null) {
			return null;
		}
		
		return userDetail.getHiringManager();
		
		
	}
	
	
	
	public static JobSeeker getCurrentJobSeeker() {
		UserDetail userDetail = getCurrentUserDetail();
		if(userDetail == null) {
			return null;
		}
		return userDetail.getJobSeeker();
	}
	
	
	
	
	
	
	public static UserDetail getCurrentUserDetail() {
		
		 if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			 return null;
		 }
		
		 return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	}
	
}
