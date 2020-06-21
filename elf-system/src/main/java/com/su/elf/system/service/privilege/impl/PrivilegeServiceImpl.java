package com.su.elf.system.service.privilege.impl;

import com.su.elf.common.entity.SearchParam;
import com.su.elf.system.entity.Privilege;
import com.su.elf.system.mapper.PrivilegeMapper;
import com.su.elf.system.service.privilege.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:32
 * @version
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeMapper privilegeMapper;

    @Override
    public List<Privilege> getPrivilegeByRoleId(int roleId) {
        return privilegeMapper.getPrivilegeByRoleId(roleId);
    }

    @Override
    public List<Privilege> getPrivilegeByParentId(int parentId) {
        return privilegeMapper.getPrivilegeByParentId(parentId);
    }


    @Override
    public List<Privilege> getList(SearchParam params) {
        Map<Integer, String> map = new HashMap<>();
        List<Privilege> pList = getPrivilegeByParentId(0);
        if(!CollectionUtils.isEmpty(pList)){
            for(Privilege p:pList){
                map.put(p.getId(), p.getName());
//                List<Privilege> subList = getPrivilegeByParentId(p.getId());
//                p.setSubprivileges(subList);
            }
        }
        //return list;
        List<Privilege> list = privilegeMapper.getList(params);
        if(!CollectionUtils.isEmpty(list)) {
            for (Privilege p : list) {
                if(p.getParentId()>0){
                    p.setParentName(map.get(p.getParentId()));
                }
            }
        }
        return list;
    }

    @Override
    public int getCount(SearchParam params) {
        return privilegeMapper.getCount(params);
    }

    @Override
    public Privilege getPojo(int id) {
        return privilegeMapper.get(id);
    }

    @Override
    public int insertPojo(Privilege pojo) {
        privilegeMapper.insert(pojo);
        return pojo.getId();
    }

    @Override
    public int updatePojo(Privilege pojo) {
        return privilegeMapper.update(pojo);
    }

    @Override
    public int deletePojo(int id) {
        return privilegeMapper.delete(id);
    }

}
