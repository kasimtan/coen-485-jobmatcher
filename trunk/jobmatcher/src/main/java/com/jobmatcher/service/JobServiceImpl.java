package com.jobmatcher.service;

/* Import SOLRJ, JAVA and JUNIT PACKAGES */

import java.io.IOException;
import java.io.StringWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jobmatcher.domain.Job;
import com.jobmatcher.reference.ExperienceLevel;
import com.jobmatcher.reference.JobType;
import com.jobmatcher.repository.JobRepository;
import java.math.BigInteger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	public long countAllJobs() {

		SolrQuery query = new SolrQuery("job.*:*");
		QueryResponse response = search(query);

		return response.getResults().getNumFound();

	}

	public void deleteJob(Job job) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("job." + job.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Job convertQueryToJob(QueryResponse response, int i) {
		// Convert Solr Query to Job class

		Job job = new Job();
		SolrDocument solrDocument = response.getResults().get(i);
		String id = solrDocument.get("id").toString().replaceAll("job.","");
		job.setId(BigInteger.valueOf(Long.parseLong(id)));
	
		Object jobTitleT = solrDocument.get("job.title_t");
		if(jobTitleT != null ) {
			job.setJobTitle(jobTitleT.toString());
		}
		Object category = solrDocument.get("job.category_t");
		if(category != null) {
			
			JobType jobType = Enum.valueOf(JobType.class,category.toString());
			job.setJobType(jobType);
		}
		
		Object jobTitle = response.getResults().get(i).get("job.title_t");
		if(jobTitle != null) {
			job.setJobTitle(jobTitle.toString());
			
		}
		Object jobExperience = response.getResults().get(i).get("job.experience_t");
		if(jobExperience != null) {
			ExperienceLevel experienceLevel = Enum.valueOf(ExperienceLevel.class,jobExperience.toString());
			job.setExperienceLevel(experienceLevel);
		}
	
		

//		 job.setAddress(response.getResults().get(i).getField("job.address_t"));
		
		Object jobDescription = response.getResults().get(i).get("job.description_t");
		if(jobDescription != null) {
			job.setJobDescription(jobDescription.toString());
		}
		Object jobDesicried = response.getResults().get(i).get("job.desiredskills_t");
		if(jobDesicried != null) {
			job.setDesiredSkills(jobDesicried.toString());
		}
		
		
		Object jobExpirationDate = response.getResults().get(i).get("job.expiration_t");
		if(jobExpirationDate != null) {
			job.setJobExpirationDate((Date)jobExpirationDate);
		}
		
//		Object hiringManger = response.getResults().get(i).get("job.hiring_managerid_t");
//		if(hiringManager != null) {
//			job.setHiringManager(hiringManager)
//		}
//		 job.setJobPostedDate(response.getResults().get(i).getField("job.experience_t"));
//		 job.setJobExpirationDate(response.getResults().get(i).getField("job.expiration_t"));
//		 job.setHiringManager(response.getResults().get(i).getField("job.hiring_managerid_t"));

		return job;
	}

	public Job findJob(BigInteger id) {

		SolrQuery query = new SolrQuery();
		query.setQuery("id:job." + id);
		QueryResponse response = search(query);
		return convertQueryToJob(response, 0);

	}

	public List<Job> findAllJobs() {

		SolrQuery query = new SolrQuery("job.title_t:*");

		QueryResponse response = search(query);

		long numFound = response.getResults().getNumFound();
		List<Job> jobs = new ArrayList<Job>();
		for (int i = 0; i < numFound; i++) {
			Job job = convertQueryToJob(response, i);
			jobs.add(job);
		}
		return jobs;

	}

	public List<Job> findJobEntries(int firstResult, int maxResults) {

		// SolrQuery query = new SolrQuery("*:*");
		// QueryResponse response = search( query );
		// Assert.assertEquals(0, response.getStatus());
		// if (firstResult) {
		// return convertQueryToJob(response,0);
		// } else if (maxResults) {
		// BigInteger numJobs = response.getResults().getNumFound();
		// List<Job> jobs = new ArrayList<Job>();
		// for (int i=0; i < numJobs; i++) {
		// Job job = convertQueryToJob(response,i);
		// jobs.add(job);
		// }
		// return jobs;
		// }
		return new ArrayList<Job>();

	}

	public void saveJob(Job job) {
		indexJob(job);
	}

	public Job updateJob(Job job) {
		indexJob(job);
		return job;
	}

	public QueryResponse search(String queryString) {
		return search(new SolrQuery("job.solrsummary_t:"
				+ queryString.toLowerCase()));
	}

	public QueryResponse search(SolrQuery query) {
		try {
			QueryResponse rsp = solrServer().query(query);
			return rsp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public void indexJob(Job jobInfo) {
		List<Job> jobs = new ArrayList<Job>();
		jobs.add(jobInfo);
		indexJobs(jobs);
	}

	public void indexJobs(Collection<Job> jobs) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Job job : jobs) {

            SolrInputDocument jid = new SolrInputDocument();
            String jobId = null;
            if(job.getId() == null) {
            	jobId = Long.valueOf(new Date().getTime()).toString();
            }
            if(jobId != null)
            	jid.addField("id", "job." +  jobId);
            if(job.getJobTitle() != null) {
            	  jid.addField("job.title_t", job.getJobTitle());
            }
          
            if(job.getJobType() != null) {
            	jid.addField("job.category_t", job.getJobType());
            }
            if(job.getExperienceLevel() != null) {
            	jid.addField("job.experience_t", job.getExperienceLevel());
            }
            
            if(job.getIndustry() != null) {
            	 jid.addField("job.industry_t", job.getIndustry());
            }
            
            if(job.getAddress() != null) {
            	jid.addField("job.address_t", job.getAddress());
            }
           
            
            if(job.getCompanyDescription() != null) {
            	jid.addField("job.description_t", job.getCompanyDescription());
            }
            
            if(job.getJobDescription() != null) {
            	jid.addField("job.jobdescription_t", job.getJobDescription());
            }
            
            if(job.getJobPostedDate() != null) {
            	jid.addField("job.jobposteddate_t", job.getJobPostedDate());
            } else {
            	jid.addField("job.jobposteddate_t", new Date());
            }
            
            if(job.getDesiredSkills() != null) {
            	jid.addField("job.desiredskills_t", job.getDesiredSkills());
            }
            
            if(job.getJobExpirationDate() != null) {
            	jid.addField("job.expiration_t", job.getJobExpirationDate());
            }
            
            if(job.getHiringManager() != null) {
            	jid.addField("job.hiring_managerid_t", job.getHiringManager().getId().toString());
            }
            
           StringBuilder str = new StringBuilder();
           
            		
           str.append("Job ID: " + jobId);
           if(job.getJobTitle() != null) {
        	   str.append(" \nJob Title: ").append(job.getJobTitle().toString());
           }
           if(job.getJobType() != null) {
        	   str.append(" \nJob Type: ").append(job.getJobType().toString());
           }
           
           if(job.getExperienceLevel() != null) {
        	   str.append(" \nJob Experience: ").append(job.getExperienceLevel().toString());
           }
           
           if(job.getIndustry() != null) {
        	   str.append(" \nJob Industry: ").append(job.getIndustry());
           }
          
           if(job.getAddress() != null) {
        	   str.append(" \nCompany Address: ").append(job.getAddress());
           }
           
           if(job.getCompanyDescription() != null) {
        	   str.append(" \nCompany Description: ").append(job.getCompanyDescription().toString());
           }
           if(job.getDesiredSkills() != null) {
        	   str.append(" \nDesired Skills: ").append(job.getDesiredSkills());
           }
           
           if(job.getJobExpirationDate() != null) {
        	   str.append(" \n Job Expiration: ").append(job.getJobExpirationDate().toString());
           }
          
           if(job.getHiringManager() != null) {
        	   str.append(" \nHiring Manager ID: ").append(job.getHiringManager().getId().toString());
           }
           
           if(job.getJobDescription() != null) {
        	   str.append(" \nJob description: ").append(job.getJobDescription());
               
           }
           if(job.getJobPostedDate() != null) {
        	   str.append(" \nJob Posted Date: ").append(job.getJobPostedDate().toString());
           }
           
           str.append("\n\n ");
          
                          
            documents.add(jid);
        }
        try {

            // Create a solr server
            SolrServer solrServer = solrServer();

            // Add the solr job document to the solr server
            UpdateResponse upres = solrServer.add(documents);
           
            // Commit the solr document
            upres = solrServer.commit( true, true );
           
            // Optimize the solr documents contents
            upres = solrServer.optimize( true, true );
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void postPersistOrUpdate(Job job) {
		indexJob(job);
	}

	private void preRemove(Job job) {
		deleteJob(job);
	}

	public static final SolrServer solrServer() {
		try {
			SolrServer _solrServer = new HttpSolrServer(
					"http://localhost:8983/solr/");
			if (_solrServer == null)
				throw new IllegalStateException(
						"Solr Server Initialization error!)");
			return _solrServer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public static void main(String[] args) {
		SolrQuery query = new SolrQuery("job.*:*");
		try {
			SolrServer _solrServer = new HttpSolrServer(
					"http://localhost:8983/solr/");
			_solrServer.deleteByQuery("job.title_t:*");
			_solrServer.commit(true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
