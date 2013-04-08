package com.jobmatcher.web;

import com.jobmatcher.domain.Addresses;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Addresses.class)
@Controller
@RequestMapping("/addresseses")
@RooWebScaffold(path = "addresseses", formBackingObject = Addresses.class)
public class AddressesController {
}
