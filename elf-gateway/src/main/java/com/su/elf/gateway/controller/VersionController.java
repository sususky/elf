package com.su.elf.gateway.controller;

import com.su.elf.common.entity.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @Desc
 * @date 2020/6/21 14:52
 */
@RestController
public class VersionController {

    @RequestMapping("/version")
    public ResponseMessage welcome() {
        return ResponseMessage.ok().put("version", 1.0);
    }

}
