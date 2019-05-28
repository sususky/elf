package com.su.elf.system.service.role.impl;

import com.su.elf.common.entity.SearchParam;
import com.su.elf.system.entity.Role;
import com.su.elf.system.service.role.RoleService;
import com.su.elf.system.entity.RolePrivilege;
import com.su.elf.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:35
 * @version
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public int updateRolePrivilege(int roleId, List<Integer> pids) {
        int r = 0;
        roleMapper.deletePrivilege(roleId);
        if(!CollectionUtils.isEmpty(pids)){
            List<RolePrivilege> list = new ArrayList<>();
            RolePrivilege rp;
            for(Integer i:pids){
                rp = new RolePrivilege();
                rp.setRoleId(roleId);
                rp.setPrivilegeId(i);
                list.add(rp);
            }
            r = roleMapper.batchInsertRolePrivilege(list);
        }
        return r;
    }

    @Override
    public List<Role> getList(SearchParam params) {
        return roleMapper.getList(params);
    }

    @Override
    public int getCount(SearchParam params) {
        return roleMapper.getCount(params);
    }

    @Override
    public Role getPojo(int id) {
        return roleMapper.get(id);
    }

    @Override
    public int insertPojo(Role pojo) {
        roleMapper.insert(pojo);
        return pojo.getId();
    }

    @Override
    public int updatePojo(Role pojo) {
        return roleMapper.update(pojo);
    }

    @Override
    public int deletePojo(int id) {
        roleMapper.deletePrivilege(id);
        return roleMapper.delete(id);
    }

}
