package com.jobmatcher.web;

import com.jobmatcher.domain.SavedJob;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = SavedJob.class)
@Controller
@RequestMapping("/savedjobs")
@RooWebScaffold(path = "savedjobs", formBackingObject = SavedJob.class)
public class SavedJobController {
}
