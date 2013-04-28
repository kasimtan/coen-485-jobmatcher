package com.jobmatcher.web;

import com.jobmatcher.domain.Job;
import com.jobmatcher.reference.ExperienceLevel;
import com.jobmatcher.reference.Industry;
import com.jobmatcher.reference.JobType;
import com.jobmatcher.service.AddressService;
import com.jobmatcher.service.HiringManagerService;
import com.jobmatcher.service.JobService;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

@RooWebJson(jsonObject = Job.class)
@Controller
@RequestMapping("/jobs")
@RooWebScaffold(path = "jobs", formBackingObject = Job.class)
public class JobController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") BigInteger id) {
        Job job = jobService.findJob(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (job == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(job.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Job> result = jobService.findAllJobs();
        return new ResponseEntity<String>(Job.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Job job = Job.fromJsonToJob(json);
        jobService.saveJob(job);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Job job: Job.fromJsonArrayToJobs(json)) {
            jobService.saveJob(job);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Job job = Job.fromJsonToJob(json);
        if (jobService.updateJob(job) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Job job: Job.fromJsonArrayToJobs(json)) {
            if (jobService.updateJob(job) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") BigInteger id) {
        Job job = jobService.findJob(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (job == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        jobService.deleteJob(job);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    JobService jobService;

	@Autowired
    AddressService addressService;

	@Autowired
    HiringManagerService hiringManagerService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Job job, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, job);
            return "jobs/create";
        }
        uiModel.asMap().clear();
        jobService.saveJob(job);
        return "redirect:/jobs/" + encodeUrlPathSegment(job.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Job());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (hiringManagerService.countAllHiringManagers() == 0) {
            dependencies.add(new String[] { "hiringmanager", "hiringmanagers" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "jobs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("job", jobService.findJob(id));
        uiModel.addAttribute("itemId", id);
        return "jobs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("jobs", jobService.findJobEntries(firstResult, sizeNo));
            float nrOfPages = (float) jobService.countAllJobs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("jobs", jobService.findAllJobs());
        }
        addDateTimeFormatPatterns(uiModel);
        return "jobs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Job job, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, job);
            return "jobs/update";
        }
        uiModel.asMap().clear();
        jobService.updateJob(job);
        return "redirect:/jobs/" + encodeUrlPathSegment(job.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, jobService.findJob(id));
        return "jobs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Job job = jobService.findJob(id);
        jobService.deleteJob(job);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/jobs";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("job_jobposteddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("job_jobexpirationdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Job job) {
        uiModel.addAttribute("job", job);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("addresseses", addressService.findAllAddresseses());
        uiModel.addAttribute("hiringmanagers", hiringManagerService.findAllHiringManagers());
        uiModel.addAttribute("experiencelevels", Arrays.asList(ExperienceLevel.values()));
        uiModel.addAttribute("industrys", Arrays.asList(Industry.values()));
        uiModel.addAttribute("jobtypes", Arrays.asList(JobType.values()));
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
