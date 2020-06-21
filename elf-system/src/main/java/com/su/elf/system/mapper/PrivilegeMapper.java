package com.su.elf.system.mapper;

import com.su.elf.common.mapper.BaseMapper;
import com.su.elf.system.entity.Privilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrivilegeMapper extends BaseMapper<Privilege> {

    List<Privilege> getPrivilegeByRoleId(int roleId);

    List<Privilege> getPrivilegeByParentId(int parentId);

}
