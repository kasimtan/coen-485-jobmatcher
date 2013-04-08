package com.jobmatcher.web;

import com.jobmatcher.domain.Resume;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Resume.class)
@Controller
@RequestMapping("/resumes")
@RooWebScaffold(path = "resumes", formBackingObject = Resume.class)
public class ResumeController {
}
