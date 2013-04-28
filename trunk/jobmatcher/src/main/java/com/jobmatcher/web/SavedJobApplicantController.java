package com.jobmatcher.web;

import com.jobmatcher.domain.SavedJobApplicant;
import com.jobmatcher.service.HiringManagerService;
import com.jobmatcher.service.JobApplicantService;
import com.jobmatcher.service.SavedJobApplicantService;
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

@RooWebJson(jsonObject = SavedJobApplicant.class)
@Controller
@RequestMapping("/savedjobapplicants")
@RooWebScaffold(path = "savedjobapplicants", formBackingObject = SavedJobApplicant.class)
public class SavedJobApplicantController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") BigInteger id) {
        SavedJobApplicant savedJobApplicant = savedJobApplicantService.findSavedJobApplicant(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (savedJobApplicant == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(savedJobApplicant.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<SavedJobApplicant> result = savedJobApplicantService.findAllSavedJobApplicants();
        return new ResponseEntity<String>(SavedJobApplicant.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        SavedJobApplicant savedJobApplicant = SavedJobApplicant.fromJsonToSavedJobApplicant(json);
        savedJobApplicantService.saveSavedJobApplicant(savedJobApplicant);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (SavedJobApplicant savedJobApplicant: SavedJobApplicant.fromJsonArrayToSavedJobApplicants(json)) {
            savedJobApplicantService.saveSavedJobApplicant(savedJobApplicant);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        SavedJobApplicant savedJobApplicant = SavedJobApplicant.fromJsonToSavedJobApplicant(json);
        if (savedJobApplicantService.updateSavedJobApplicant(savedJobApplicant) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (SavedJobApplicant savedJobApplicant: SavedJobApplicant.fromJsonArrayToSavedJobApplicants(json)) {
            if (savedJobApplicantService.updateSavedJobApplicant(savedJobApplicant) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") BigInteger id) {
        SavedJobApplicant savedJobApplicant = savedJobApplicantService.findSavedJobApplicant(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (savedJobApplicant == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        savedJobApplicantService.deleteSavedJobApplicant(savedJobApplicant);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    SavedJobApplicantService savedJobApplicantService;

	@Autowired
    HiringManagerService hiringManagerService;

	@Autowired
    JobApplicantService jobApplicantService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid SavedJobApplicant savedJobApplicant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, savedJobApplicant);
            return "savedjobapplicants/create";
        }
        uiModel.asMap().clear();
        savedJobApplicantService.saveSavedJobApplicant(savedJobApplicant);
        return "redirect:/savedjobapplicants/" + encodeUrlPathSegment(savedJobApplicant.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new SavedJobApplicant());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (hiringManagerService.countAllHiringManagers() == 0) {
            dependencies.add(new String[] { "hiringmanager", "hiringmanagers" });
        }
        if (jobApplicantService.countAllJobApplicants() == 0) {
            dependencies.add(new String[] { "jobapplicant", "jobapplicants" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "savedjobapplicants/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("savedjobapplicant", savedJobApplicantService.findSavedJobApplicant(id));
        uiModel.addAttribute("itemId", id);
        return "savedjobapplicants/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("savedjobapplicants", savedJobApplicantService.findSavedJobApplicantEntries(firstResult, sizeNo));
            float nrOfPages = (float) savedJobApplicantService.countAllSavedJobApplicants() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("savedjobapplicants", savedJobApplicantService.findAllSavedJobApplicants());
        }
        return "savedjobapplicants/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid SavedJobApplicant savedJobApplicant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, savedJobApplicant);
            return "savedjobapplicants/update";
        }
        uiModel.asMap().clear();
        savedJobApplicantService.updateSavedJobApplicant(savedJobApplicant);
        return "redirect:/savedjobapplicants/" + encodeUrlPathSegment(savedJobApplicant.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, savedJobApplicantService.findSavedJobApplicant(id));
        return "savedjobapplicants/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SavedJobApplicant savedJobApplicant = savedJobApplicantService.findSavedJobApplicant(id);
        savedJobApplicantService.deleteSavedJobApplicant(savedJobApplicant);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/savedjobapplicants";
    }

	void populateEditForm(Model uiModel, SavedJobApplicant savedJobApplicant) {
        uiModel.addAttribute("savedJobApplicant", savedJobApplicant);
        uiModel.addAttribute("hiringmanagers", hiringManagerService.findAllHiringManagers());
        uiModel.addAttribute("jobapplicants", jobApplicantService.findAllJobApplicants());
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
