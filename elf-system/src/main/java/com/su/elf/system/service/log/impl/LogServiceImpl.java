package com.su.elf.system.service.log.impl;

import com.su.elf.common.entity.SearchParam;
import com.su.elf.system.entity.Log;
import com.su.elf.system.mapper.LogMapper;
import com.su.elf.system.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:37
 * @version
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;


    @Override
    public List<Log> getList(SearchParam params) {
        return logMapper.getList(params);
    }

    @Override
    public int getCount(SearchParam params) {
        return logMapper.getCount(params);
    }

    @Override
    public Log getPojo(int id) {
        return logMapper.get(id);
    }

    @Override
    public int insertPojo(Log pojo) {
        logMapper.insert(pojo);
        return pojo.getId();
    }

    @Override
    public int updatePojo(Log pojo) {
        return logMapper.update(pojo);
    }

    @Override
    public int deletePojo(int id) {
        return logMapper.delete(id);
    }

}
