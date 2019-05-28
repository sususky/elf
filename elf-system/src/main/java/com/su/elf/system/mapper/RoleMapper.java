package com.su.elf.system.mapper;

import com.su.elf.common.mapper.BaseMapper;
import com.su.elf.system.entity.Role;
import com.su.elf.system.entity.RolePrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    int deletePrivilege(int roleId);

    int batchInsertRolePrivilege(List<RolePrivilege> list);

}
