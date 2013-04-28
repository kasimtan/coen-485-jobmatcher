package com.jobmatcher.web;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.service.AddressService;
import com.jobmatcher.service.HiringManagerService;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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

@RooWebJson(jsonObject = HiringManager.class)
@Controller
@RequestMapping("/hiringmanagers")
@RooWebScaffold(path = "hiringmanagers", formBackingObject = HiringManager.class)
public class HiringManagerController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") BigInteger id) {
        HiringManager hiringManager = hiringManagerService.findHiringManager(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (hiringManager == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(hiringManager.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<HiringManager> result = hiringManagerService.findAllHiringManagers();
        return new ResponseEntity<String>(HiringManager.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        HiringManager hiringManager = HiringManager.fromJsonToHiringManager(json);
        hiringManagerService.saveHiringManager(hiringManager);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (HiringManager hiringManager: HiringManager.fromJsonArrayToHiringManagers(json)) {
            hiringManagerService.saveHiringManager(hiringManager);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HiringManager hiringManager = HiringManager.fromJsonToHiringManager(json);
        if (hiringManagerService.updateHiringManager(hiringManager) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (HiringManager hiringManager: HiringManager.fromJsonArrayToHiringManagers(json)) {
            if (hiringManagerService.updateHiringManager(hiringManager) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") BigInteger id) {
        HiringManager hiringManager = hiringManagerService.findHiringManager(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (hiringManager == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        hiringManagerService.deleteHiringManager(hiringManager);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    HiringManagerService hiringManagerService;

	@Autowired
    AddressService addressService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid HiringManager hiringManager, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, hiringManager);
            return "hiringmanagers/create";
        }
        uiModel.asMap().clear();
        hiringManagerService.saveHiringManager(hiringManager);
        return "redirect:/hiringmanagers/" + encodeUrlPathSegment(hiringManager.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new HiringManager());
        return "hiringmanagers/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("hiringmanager", hiringManagerService.findHiringManager(id));
        uiModel.addAttribute("itemId", id);
        return "hiringmanagers/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("hiringmanagers", hiringManagerService.findHiringManagerEntries(firstResult, sizeNo));
            float nrOfPages = (float) hiringManagerService.countAllHiringManagers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("hiringmanagers", hiringManagerService.findAllHiringManagers());
        }
        return "hiringmanagers/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid HiringManager hiringManager, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, hiringManager);
            return "hiringmanagers/update";
        }
        uiModel.asMap().clear();
        hiringManagerService.updateHiringManager(hiringManager);
        return "redirect:/hiringmanagers/" + encodeUrlPathSegment(hiringManager.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, hiringManagerService.findHiringManager(id));
        return "hiringmanagers/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        HiringManager hiringManager = hiringManagerService.findHiringManager(id);
        hiringManagerService.deleteHiringManager(hiringManager);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/hiringmanagers";
    }

	void populateEditForm(Model uiModel, HiringManager hiringManager) {
        uiModel.addAttribute("hiringManager", hiringManager);
        uiModel.addAttribute("addresseses", addressService.findAllAddresseses());
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
