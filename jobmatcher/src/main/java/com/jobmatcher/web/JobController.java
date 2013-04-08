package com.jobmatcher.web;

import com.jobmatcher.domain.Job;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Job.class)
@Controller
@RequestMapping("/jobs")
@RooWebScaffold(path = "jobs", formBackingObject = Job.class)
public class JobController {
}
