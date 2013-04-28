package com.jobmatcher.web;

import com.jobmatcher.domain.JobSeeker;
import com.jobmatcher.reference.States;
import com.jobmatcher.service.JobSeekerService;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
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

@RooWebJson(jsonObject = JobSeeker.class)
@Controller
@RequestMapping("/jobseekers")
@RooWebScaffold(path = "jobseekers", formBackingObject = JobSeeker.class)
public class JobSeekerController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") BigInteger id) {
        JobSeeker jobSeeker = jobSeekerService.findJobSeeker(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (jobSeeker == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(jobSeeker.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<JobSeeker> result = jobSeekerService.findAllJobSeekers();
        return new ResponseEntity<String>(JobSeeker.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        JobSeeker jobSeeker = JobSeeker.fromJsonToJobSeeker(json);
        jobSeekerService.saveJobSeeker(jobSeeker);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (JobSeeker jobSeeker: JobSeeker.fromJsonArrayToJobSeekers(json)) {
            jobSeekerService.saveJobSeeker(jobSeeker);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        JobSeeker jobSeeker = JobSeeker.fromJsonToJobSeeker(json);
        if (jobSeekerService.updateJobSeeker(jobSeeker) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (JobSeeker jobSeeker: JobSeeker.fromJsonArrayToJobSeekers(json)) {
            if (jobSeekerService.updateJobSeeker(jobSeeker) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") BigInteger id) {
        JobSeeker jobSeeker = jobSeekerService.findJobSeeker(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (jobSeeker == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        jobSeekerService.deleteJobSeeker(jobSeeker);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    JobSeekerService jobSeekerService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid JobSeeker jobSeeker, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, jobSeeker);
            return "jobseekers/create";
        }
        uiModel.asMap().clear();
        jobSeekerService.saveJobSeeker(jobSeeker);
        return "redirect:/jobseekers/" + encodeUrlPathSegment(jobSeeker.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new JobSeeker());
        return "jobseekers/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("jobseeker", jobSeekerService.findJobSeeker(id));
        uiModel.addAttribute("itemId", id);
        return "jobseekers/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("jobseekers", jobSeekerService.findJobSeekerEntries(firstResult, sizeNo));
            float nrOfPages = (float) jobSeekerService.countAllJobSeekers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("jobseekers", jobSeekerService.findAllJobSeekers());
        }
        return "jobseekers/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid JobSeeker jobSeeker, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, jobSeeker);
            return "jobseekers/update";
        }
        uiModel.asMap().clear();
        jobSeekerService.updateJobSeeker(jobSeeker);
        return "redirect:/jobseekers/" + encodeUrlPathSegment(jobSeeker.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, jobSeekerService.findJobSeeker(id));
        return "jobseekers/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        JobSeeker jobSeeker = jobSeekerService.findJobSeeker(id);
        jobSeekerService.deleteJobSeeker(jobSeeker);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/jobseekers";
    }

	void populateEditForm(Model uiModel, JobSeeker jobSeeker) {
        uiModel.addAttribute("jobSeeker", jobSeeker);
        uiModel.addAttribute("stateses", Arrays.asList(States.values()));
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
