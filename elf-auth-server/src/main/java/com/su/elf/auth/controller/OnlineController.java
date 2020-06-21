package com.su.elf.auth.controller;

import com.su.elf.auth.service.OnlineUserService;
import com.su.elf.common.entity.ResponseMessage;
import com.su.elf.logging.annotation.LogRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author surongyao
 * @date 2020-06-05 13:17
 * @desc
 */
@RestController
@RequestMapping("/online")
@Api(tags = "系统：在线用户管理")
public class OnlineController {

    @Autowired
    private OnlineUserService onlineUserService;

    @ApiOperation("查询在线用户")
    @GetMapping
    public ResponseMessage query(String filter, Pageable pageable){
        return ResponseMessage.ok(onlineUserService.getAll(filter, pageable));
    }

    @LogRecord("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, String filter) throws IOException {
        onlineUserService.download(onlineUserService.getAll(filter), response);
    }

    @ApiOperation("踢出用户")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Set<String> keys) throws Exception {
        for (String key : keys) {
            // 解密Key
            onlineUserService.kickOut(key);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

