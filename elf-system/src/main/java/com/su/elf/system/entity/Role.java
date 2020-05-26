package com.su.elf.system.entity;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午1:41
 * @version
 */
public class Role {

    private int id;
    private String roleName;
    private String comments;
    private String createTime;
    private List<Integer> privileges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Integer> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Integer> privileges) {
        this.privileges = privileges;
    }
}
