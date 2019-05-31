package com.su.elf.auth.server.mapper;

import com.su.elf.auth.client.entity.Authorities;
import com.su.elf.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AuthoritieMapper extends BaseMapper<Authorities> {

    /**
     *
     * @Param:
     * @return:
     */
    @Select("select p.link, p.privilege_name from privilege p join role_privilege_ref rel on rel.privilege_id = p.id " +
            "where rel.role_id = #{roleId};")
    // 返回 Map 结果集
    @Results({
            @Result(property = "authority", column = "link"),
            @Result(property = "authorityName", column = "privilege_name"),
    })
    List<Authorities> getPrivilegeByRoleId(@Param("roleId")int roleId);

}
