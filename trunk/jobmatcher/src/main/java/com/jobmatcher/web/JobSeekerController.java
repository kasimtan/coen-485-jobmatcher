package com.jobmatcher.web;

import com.jobmatcher.domain.JobSeeker;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = JobSeeker.class)
@Controller
@RequestMapping("/jobseekers")
@RooWebScaffold(path = "jobseekers", formBackingObject = JobSeeker.class)
public class JobSeekerController {
}
