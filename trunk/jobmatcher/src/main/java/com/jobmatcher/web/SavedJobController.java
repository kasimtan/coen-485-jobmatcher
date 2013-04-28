package com.jobmatcher.web;

import com.jobmatcher.domain.SavedJob;
import com.jobmatcher.service.JobSeekerService;
import com.jobmatcher.service.JobService;
import com.jobmatcher.service.SavedJobService;
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

@RooWebJson(jsonObject = SavedJob.class)
@Controller
@RequestMapping("/savedjobs")
@RooWebScaffold(path = "savedjobs", formBackingObject = SavedJob.class)
public class SavedJobController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") BigInteger id) {
        SavedJob savedJob = savedJobService.findSavedJob(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (savedJob == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(savedJob.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<SavedJob> result = savedJobService.findAllSavedJobs();
        return new ResponseEntity<String>(SavedJob.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        SavedJob savedJob = SavedJob.fromJsonToSavedJob(json);
        savedJobService.saveSavedJob(savedJob);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (SavedJob savedJob: SavedJob.fromJsonArrayToSavedJobs(json)) {
            savedJobService.saveSavedJob(savedJob);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        SavedJob savedJob = SavedJob.fromJsonToSavedJob(json);
        if (savedJobService.updateSavedJob(savedJob) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (SavedJob savedJob: SavedJob.fromJsonArrayToSavedJobs(json)) {
            if (savedJobService.updateSavedJob(savedJob) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") BigInteger id) {
        SavedJob savedJob = savedJobService.findSavedJob(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (savedJob == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        savedJobService.deleteSavedJob(savedJob);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    SavedJobService savedJobService;

	@Autowired
    JobService jobService;

	@Autowired
    JobSeekerService jobSeekerService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid SavedJob savedJob, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, savedJob);
            return "savedjobs/create";
        }
        uiModel.asMap().clear();
        savedJobService.saveSavedJob(savedJob);
        return "redirect:/savedjobs/" + encodeUrlPathSegment(savedJob.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new SavedJob());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (jobSeekerService.countAllJobSeekers() == 0) {
            dependencies.add(new String[] { "jobseeker", "jobseekers" });
        }
        if (jobService.countAllJobs() == 0) {
            dependencies.add(new String[] { "job", "jobs" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "savedjobs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("savedjob", savedJobService.findSavedJob(id));
        uiModel.addAttribute("itemId", id);
        return "savedjobs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("savedjobs", savedJobService.findSavedJobEntries(firstResult, sizeNo));
            float nrOfPages = (float) savedJobService.countAllSavedJobs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("savedjobs", savedJobService.findAllSavedJobs());
        }
        return "savedjobs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid SavedJob savedJob, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, savedJob);
            return "savedjobs/update";
        }
        uiModel.asMap().clear();
        savedJobService.updateSavedJob(savedJob);
        return "redirect:/savedjobs/" + encodeUrlPathSegment(savedJob.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, savedJobService.findSavedJob(id));
        return "savedjobs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SavedJob savedJob = savedJobService.findSavedJob(id);
        savedJobService.deleteSavedJob(savedJob);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/savedjobs";
    }

	void populateEditForm(Model uiModel, SavedJob savedJob) {
        uiModel.addAttribute("savedJob", savedJob);
        uiModel.addAttribute("jobs", jobService.findAllJobs());
        uiModel.addAttribute("jobseekers", jobSeekerService.findAllJobSeekers());
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
