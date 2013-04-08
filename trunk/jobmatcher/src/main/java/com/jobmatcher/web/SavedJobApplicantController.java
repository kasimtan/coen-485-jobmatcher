package com.jobmatcher.web;

import com.jobmatcher.domain.SavedJobApplicant;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = SavedJobApplicant.class)
@Controller
@RequestMapping("/savedjobapplicants")
@RooWebScaffold(path = "savedjobapplicants", formBackingObject = SavedJobApplicant.class)
public class SavedJobApplicantController {
}
