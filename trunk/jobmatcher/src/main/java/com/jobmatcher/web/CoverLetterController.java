package com.jobmatcher.web;

import com.jobmatcher.domain.CoverLetter;
import com.jobmatcher.service.CoverLetterService;
import com.jobmatcher.service.JobSeekerService;
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

@RooWebJson(jsonObject = CoverLetter.class)
@Controller
@RequestMapping("/coverletters")
@RooWebScaffold(path = "coverletters", formBackingObject = CoverLetter.class)
public class CoverLetterController {

	@Autowired
    CoverLetterService coverLetterService;

	@Autowired
    JobSeekerService jobSeekerService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CoverLetter coverLetter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, coverLetter);
            return "coverletters/create";
        }
        uiModel.asMap().clear();
        coverLetterService.saveCoverLetter(coverLetter);
        return "redirect:/coverletters/" + encodeUrlPathSegment(coverLetter.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CoverLetter());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (jobSeekerService.countAllJobSeekers() == 0) {
            dependencies.add(new String[] { "jobseeker", "jobseekers" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "coverletters/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("coverletter", coverLetterService.findCoverLetter(id));
        uiModel.addAttribute("itemId", id);
        return "coverletters/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("coverletters", coverLetterService.findCoverLetterEntries(firstResult, sizeNo));
            float nrOfPages = (float) coverLetterService.countAllCoverLetters() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("coverletters", coverLetterService.findAllCoverLetters());
        }
        return "coverletters/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CoverLetter coverLetter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, coverLetter);
            return "coverletters/update";
        }
        uiModel.asMap().clear();
        coverLetterService.updateCoverLetter(coverLetter);
        return "redirect:/coverletters/" + encodeUrlPathSegment(coverLetter.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, coverLetterService.findCoverLetter(id));
        return "coverletters/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CoverLetter coverLetter = coverLetterService.findCoverLetter(id);
        coverLetterService.deleteCoverLetter(coverLetter);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/coverletters";
    }

	void populateEditForm(Model uiModel, CoverLetter coverLetter) {
        uiModel.addAttribute("coverLetter", coverLetter);
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") BigInteger id) {
        CoverLetter coverLetter = coverLetterService.findCoverLetter(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (coverLetter == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(coverLetter.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<CoverLetter> result = coverLetterService.findAllCoverLetters();
        return new ResponseEntity<String>(CoverLetter.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        CoverLetter coverLetter = CoverLetter.fromJsonToCoverLetter(json);
        coverLetterService.saveCoverLetter(coverLetter);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (CoverLetter coverLetter: CoverLetter.fromJsonArrayToCoverLetters(json)) {
            coverLetterService.saveCoverLetter(coverLetter);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        CoverLetter coverLetter = CoverLetter.fromJsonToCoverLetter(json);
        if (coverLetterService.updateCoverLetter(coverLetter) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (CoverLetter coverLetter: CoverLetter.fromJsonArrayToCoverLetters(json)) {
            if (coverLetterService.updateCoverLetter(coverLetter) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") BigInteger id) {
        CoverLetter coverLetter = coverLetterService.findCoverLetter(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (coverLetter == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        coverLetterService.deleteCoverLetter(coverLetter);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
