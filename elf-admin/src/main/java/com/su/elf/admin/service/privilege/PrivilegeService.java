package com.su.elf.admin.service.privilege;


import com.su.elf.admin.entity.Privilege;
import com.su.elf.admin.service.BaseService;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:30
 * @version
 */
public interface PrivilegeService extends BaseService {

    List<String> getPrivilegeByRoleId(int roleId);

    List<Privilege> getPrivileges();

}
