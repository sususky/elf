package com.su.elf.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.admin.service.log.LogService;
import com.su.elf.common.entity.ResponseMessage;
import com.su.elf.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:40
 * @version
 */
@RestController
@RequestMapping("/log")
public class LogController {

    //private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private LogService logService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseMessage getLogList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        JSONObject json = logService.getList(param);
        return ResponseMessage.ok(json.getJSONArray("list")).put("count", json.getInteger("count"));
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMessage deleteLog(@PathVariable int pid){
        JSONObject json = logService.deletePojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMessage getLog(@PathVariable int pid){
        JSONObject json = logService.getPojo(pid);
        return ResponseMessage.ok(json);
    }

}
