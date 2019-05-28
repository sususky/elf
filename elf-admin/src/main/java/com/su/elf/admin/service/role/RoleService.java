package com.su.elf.admin.service.role;



import com.alibaba.fastjson.JSONObject;
import com.su.elf.admin.service.BaseService;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:30
 * @version
 */
public interface RoleService extends BaseService {

//    int deletePrivilege(int roleId);

    JSONObject updateRolePrivilege(int roleId, List<Integer> list);

}
