package com.su.elf.admin.entity;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午4:43
 * @version
 */
public class RolePrivilege {
    int roleId;
    int privilegeId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }
}
