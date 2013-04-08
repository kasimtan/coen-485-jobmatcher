package com.jobmatcher.web;

import com.jobmatcher.domain.CoverLetter;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = CoverLetter.class)
@Controller
@RequestMapping("/coverletters")
@RooWebScaffold(path = "coverletters", formBackingObject = CoverLetter.class)
public class CoverLetterController {
}
