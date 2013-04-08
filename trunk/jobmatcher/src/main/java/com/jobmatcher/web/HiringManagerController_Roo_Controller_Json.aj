// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jobmatcher.web;

import com.jobmatcher.domain.HiringManager;
import com.jobmatcher.web.HiringManagerController;
import java.math.BigInteger;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

privileged aspect HiringManagerController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> HiringManagerController.showJson(@PathVariable("id") BigInteger id) {
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
    public ResponseEntity<String> HiringManagerController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<HiringManager> result = hiringManagerService.findAllHiringManagers();
        return new ResponseEntity<String>(HiringManager.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> HiringManagerController.createFromJson(@RequestBody String json) {
        HiringManager hiringManager = HiringManager.fromJsonToHiringManager(json);
        hiringManagerService.saveHiringManager(hiringManager);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> HiringManagerController.createFromJsonArray(@RequestBody String json) {
        for (HiringManager hiringManager: HiringManager.fromJsonArrayToHiringManagers(json)) {
            hiringManagerService.saveHiringManager(hiringManager);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> HiringManagerController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HiringManager hiringManager = HiringManager.fromJsonToHiringManager(json);
        if (hiringManagerService.updateHiringManager(hiringManager) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> HiringManagerController.updateFromJsonArray(@RequestBody String json) {
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
    public ResponseEntity<String> HiringManagerController.deleteFromJson(@PathVariable("id") BigInteger id) {
        HiringManager hiringManager = hiringManagerService.findHiringManager(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (hiringManager == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        hiringManagerService.deleteHiringManager(hiringManager);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}
