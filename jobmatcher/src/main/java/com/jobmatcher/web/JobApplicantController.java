package com.jobmatcher.web;

import com.jobmatcher.domain.JobApplicant;
import com.jobmatcher.service.CoverLetterService;
import com.jobmatcher.service.JobApplicantService;
import com.jobmatcher.service.JobSeekerService;
import com.jobmatcher.service.JobService;
import com.jobmatcher.service.ResumeService;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RooWebJson(jsonObject = JobApplicant.class)
@Controller
@RequestMapping("/jobapplicants")
@RooWebScaffold(path = "jobapplicants", formBackingObject = JobApplicant.class)
public class JobApplicantController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") BigInteger id) {
        JobApplicant jobApplicant = jobApplicantService.findJobApplicant(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (jobApplicant == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(jobApplicant.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<JobApplicant> result = jobApplicantService.findAllJobApplicants();
        return new ResponseEntity<String>(JobApplicant.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        JobApplicant jobApplicant = JobApplicant.fromJsonToJobApplicant(json);
        jobApplicantService.saveJobApplicant(jobApplicant);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (JobApplicant jobApplicant: JobApplicant.fromJsonArrayToJobApplicants(json)) {
            jobApplicantService.saveJobApplicant(jobApplicant);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        JobApplicant jobApplicant = JobApplicant.fromJsonToJobApplicant(json);
        if (jobApplicantService.updateJobApplicant(jobApplicant) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (JobApplicant jobApplicant: JobApplicant.fromJsonArrayToJobApplicants(json)) {
            if (jobApplicantService.updateJobApplicant(jobApplicant) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") BigInteger id) {
        JobApplicant jobApplicant = jobApplicantService.findJobApplicant(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (jobApplicant == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        jobApplicantService.deleteJobApplicant(jobApplicant);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    JobApplicantService jobApplicantService;

	@Autowired
    CoverLetterService coverLetterService;

	@Autowired
    JobService jobService;

	@Autowired
    JobSeekerService jobSeekerService;

	@Autowired
    ResumeService resumeService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid JobApplicant jobApplicant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, jobApplicant);
            return "jobapplicants/create";
        }
        uiModel.asMap().clear();
        jobApplicantService.saveJobApplicant(jobApplicant);
        return "redirect:/jobapplicants/" + encodeUrlPathSegment(jobApplicant.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new JobApplicant());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (jobSeekerService.countAllJobSeekers() == 0) {
            dependencies.add(new String[] { "jobseeker", "jobseekers" });
        }
        if (jobService.countAllJobs() == 0) {
            dependencies.add(new String[] { "job", "jobs" });
        }
        if (resumeService.countAllResumes() == 0) {
            dependencies.add(new String[] { "resume", "resumes" });
        }
        if (coverLetterService.countAllCoverLetters() == 0) {
            dependencies.add(new String[] { "coverletter", "coverletters" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "jobapplicants/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("jobapplicant", jobApplicantService.findJobApplicant(id));
        uiModel.addAttribute("itemId", id);
        return "jobapplicants/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("jobapplicants", jobApplicantService.findJobApplicantEntries(firstResult, sizeNo));
            float nrOfPages = (float) jobApplicantService.countAllJobApplicants() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("jobapplicants", jobApplicantService.findAllJobApplicants());
        }
        return "jobapplicants/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid JobApplicant jobApplicant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, jobApplicant);
            return "jobapplicants/update";
        }
        uiModel.asMap().clear();
        jobApplicantService.updateJobApplicant(jobApplicant);
        return "redirect:/jobapplicants/" + encodeUrlPathSegment(jobApplicant.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, jobApplicantService.findJobApplicant(id));
        return "jobapplicants/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        JobApplicant jobApplicant = jobApplicantService.findJobApplicant(id);
        jobApplicantService.deleteJobApplicant(jobApplicant);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/jobapplicants";
    }

	void populateEditForm(Model uiModel, JobApplicant jobApplicant) {
        uiModel.addAttribute("jobApplicant", jobApplicant);
        uiModel.addAttribute("coverletters", coverLetterService.findAllCoverLetters());
        uiModel.addAttribute("jobs", jobService.findAllJobs());
        uiModel.addAttribute("jobseekers", jobSeekerService.findAllJobSeekers());
        uiModel.addAttribute("resumes", resumeService.findAllResumes());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
