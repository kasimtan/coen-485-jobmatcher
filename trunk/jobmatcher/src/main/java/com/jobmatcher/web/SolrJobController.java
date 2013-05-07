package com.jobmatcher.web;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jobmatcher.domain.Job;
@Controller
@RequestMapping("/solrjobs")
public class SolrJobController {
	
	
	
	

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
		
		System.out.println("LIST JSON");
		
		return null;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json; charset=utf-8");
//        List<Job> result = jobService.findAllJobs();
      //  return new ResponseEntity<String>(Job.toJsonArray(result), headers, HttpStatus.OK);
    }

//	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
//    public ResponseEntity<String> createFromJson(@RequestBody String json) {
////        Job job = Job.fromJsonToJob(json);
////        jobService.saveJob(job);
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Content-Type", "application/json");
////        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
//    }
//
//	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
//    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
////        for (Job job: Job.fromJsonArrayToJobs(json)) {
////            jobService.saveJob(job);
////        }
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Content-Type", "application/json");
////        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
//    }

}
