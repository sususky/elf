package com.su.logging.service.impl;

import com.su.elf.common.entity.SearchParam;
import com.su.logging.entity.Log;
import com.su.logging.mapper.LogMapper;
import com.su.logging.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author surongyao
 * @date 2020-06-02 17:48
 * @desc
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public List<Log> getList(SearchParam param) {
        return logMapper.getLogList(param.getStartTime(), param.getEndTime(), param.getLimit(), param.getOffset());
    }

    @Override
    public int getCount(SearchParam param) {
        return logMapper.getLogCount(param.getStartTime(), param.getEndTime());
    }

    @Override
    public void save(Log l) {
        logMapper.insert(l);
        log.info("新增一条日志, id:[{}]", l.getId());
    }

    @Override
    public Log get(Long id) {
        return null;
    }

    @Override
    public int del(Long id) {
        return logMapper.delete(id);
    }


    @Override
    public void download(List<Log> logs, HttpServletResponse response) throws IOException {

    }



}
