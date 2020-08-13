package com.su.elf.gateway.controller;

import com.su.elf.common.entity.ResponseMap;
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
    public ResponseMap welcome() {
        return ResponseMap.ok().put("version", 1.0);
    }

}
