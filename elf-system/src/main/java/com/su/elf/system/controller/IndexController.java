package com.su.elf.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class IndexController {

    @Value("${app.version}")
    String version;

    @RequestMapping(method = RequestMethod.GET)
    public String version(){
        return "welcome to system, version is " + version;
    }


}
