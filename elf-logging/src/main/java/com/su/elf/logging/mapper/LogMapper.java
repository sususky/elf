package com.su.elf.logging.mapper;

import com.su.elf.logging.entity.Log;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author surongyao
 * @date 2020-06-02 17:51
 * @desc
 */
@Mapper
public interface LogMapper {

    /**
     *
     * @Param:
     * @return:
     */
    @Select({"<script>",
            "select count(id) from sys_log where 1=1 ",
            "<if test='startTime!= null'>",
            "and create_time &gt;= #{startTime}",
            "</if>",
            "<if test='endTime!= null'>",
            "and create_time &lt;= #{endTime}",
            "</if>",
            "</script>"})
    int getLogCount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     *
     * @Param:
     * @return:
     */
    @Select({"<script>",
            "select id, log_type, method, client_ip, username, browser, os, request_params, opt ",
            "exception_detail, time, create_time from sys_log where 1=1",
            "<if test='startTime!= null'>",
            "and create_time &gt;= #{startTime}",
            "</if>",
            "<if test='endTime!= null'>",
            "and create_time &lt;= #{endTime}",
            "</if>",
            "order by create_time desc ",
            "<if test='pageSize!= null and pageSize>0'>",
            "limit #{pageSize} offset #{pageOffset}",
            "</if>",
            "</script>"})
    // 返回 Map 结果集
    @Results({
            @Result(property = "logType", column = "log_type"),
            @Result(property = "clientIp", column = "client_ip"),
            @Result(property = "requestParams", column = "request_params"),
            @Result(property = "exceptionDetail", column = "exception_detail"),
            @Result(property = "createTime", column = "create_time")
    })
    List<Log> getLogList(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("pageOffset")int pageOffset,
                         @Param("pageSize")int pageSize);

    /**
     *
     * @Param:
     * @return:
     */
    @Insert("insert into sys_log(log_type, method, client_ip, username, browser, os, request_params, opt, " +
            "exception_detail, time, create_time) values(#{logType}, #{method}, #{clientIp}, #{username}, #{browser}, " +
            "#{os}, #{requestParams}, #{opt}, #{exceptionDetail}, #{time}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Log log);


    /**
     *
     * @Param:
     * @return:
     */
    @Update("update sys_log set log_type=#{logType}, request_params=#{requestParams}, " +
            "exception_detail=#{exceptionDetail} where id=#{id}")
    int update(Log log);


    /**
     *
     * @Param:
     * @return:
     */
    @Delete("delete from sys_log where id = #{id}")
    int delete(Long id);

}
