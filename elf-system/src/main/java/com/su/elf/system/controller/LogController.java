package com.su.elf.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.elf.common.entity.ResponseMap;
import com.su.elf.common.entity.SearchParam;
import com.su.elf.logging.entity.Log;
import com.su.elf.logging.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:39
 * @version
 */
@RestController
@RequestMapping("/log")
public class LogController {


    @Autowired
    LogService logService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap getLogList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<Log> list = logService.getList(param);
        int total = logService.getCount(param);
        JSONObject json = new JSONObject();
        json.put("count", total);
        json.put("list", list);
        return ResponseMap.success(json);
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseMap addLog(@RequestBody Log log){
//        int id = logService.insertPojo(log);
//        JSONObject json = new JSONObject();
//        json.put("id", id);
//        return ResponseMap.ok(json);
//    }
//
//    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
//    public ResponseMap deleteLog(@PathVariable int pid){
//        int result = logService.deletePojo(pid);
//        JSONObject json = new JSONObject();
//        json.put("result", result);
//        return ResponseMap.ok(json);
//    }
//
//    @RequestMapping(method = RequestMethod.PUT)
//    public ResponseMap updateRole(@RequestBody Log log){
//        int result = logService.updatePojo(log);
//        JSONObject json = new JSONObject();
//        json.put("result", result);
//        return ResponseMap.ok(json);
//    }
//
//    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
//    public ResponseMap getUser(@PathVariable int pid){
//        Log log = logService.getPojo(pid);
//        JSONObject json = new JSONObject();
//        json.put("log", log);
//        return ResponseMap.ok(json);
//    }

}
