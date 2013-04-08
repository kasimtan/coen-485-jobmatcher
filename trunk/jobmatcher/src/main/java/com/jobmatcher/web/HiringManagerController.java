package com.jobmatcher.web;

import com.jobmatcher.domain.HiringManager;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = HiringManager.class)
@Controller
@RequestMapping("/hiringmanagers")
@RooWebScaffold(path = "hiringmanagers", formBackingObject = HiringManager.class)
public class HiringManagerController {
}
