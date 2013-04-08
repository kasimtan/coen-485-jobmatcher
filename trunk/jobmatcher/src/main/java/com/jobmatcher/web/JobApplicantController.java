package com.jobmatcher.web;

import com.jobmatcher.domain.JobApplicant;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = JobApplicant.class)
@Controller
@RequestMapping("/jobapplicants")
@RooWebScaffold(path = "jobapplicants", formBackingObject = JobApplicant.class)
public class JobApplicantController {
}
