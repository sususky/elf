package com.su.elf.system.service.privilege;

import com.su.elf.system.entity.Privilege;
import com.su.elf.system.service.BaseService;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:30
 * @version
 */
public interface PrivilegeService extends BaseService<Privilege> {

    List<Privilege> getPrivilegeByRoleId(int roleId);

    List<Privilege> getPrivilegeByParentId(int parentId);

}
