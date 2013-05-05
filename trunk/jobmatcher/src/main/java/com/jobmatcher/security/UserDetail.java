package com.jobmatcher.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.domain.JobSeeker;

public class UserDetail extends org.springframework.security.core.userdetails.User {

	private HiringManager hiringManager;
	private JobSeeker jobSeeker;
	
	
	public UserDetail(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	
	}

	public HiringManager getHiringManager() {
		return hiringManager;
	}

	public void setHiringManager(HiringManager hiringManager) {
		this.hiringManager = hiringManager;
	}






	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}






	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}






	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
