package com.su.elf.logging.service;

import com.su.elf.common.entity.SearchParam;
import com.su.elf.logging.entity.Log;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author surongyao
 * @date 2020-06-02 17:37
 * @desc
 */
public interface LogService {

    /**
     * 分页查询
     * @return /
     */
    List<Log> getList(SearchParam param);

    /**
     * 查询全部数量
     * @return /
     */
    int getCount(SearchParam param);

    /**
     * 保存日志数据
     * @param log 日志实体
     */
    @Async
    void save(Log log);

    /**
     * 查询异常详情
     * @param id 日志ID
     * @return Object
     */
    Log get(Long id);

    /**
     * 删除所有错误日志
     */
    int del(Long id);

    /**
     * 导出日志
     * @param logs 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<Log> logs, HttpServletResponse response) throws IOException;

}
