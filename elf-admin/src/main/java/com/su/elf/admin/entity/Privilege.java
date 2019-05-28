package com.su.elf.admin.entity;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午1:39
 * @version
 */
public class Privilege {

    private int id;
    private String privilegeName;
    private int seq;
    private int parentId;
    private String parentName;
    private String link;
    private int category;
    private int hasChild;
    private String createTime;
    private List<Privilege> subprivileges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getHasChild() {
        return hasChild;
    }

    public void setHasChild(int hasChild) {
        this.hasChild = hasChild;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Privilege> getSubprivileges() {
        return subprivileges;
    }

    public void setSubprivileges(List<Privilege> subprivileges) {
        this.subprivileges = subprivileges;
    }
}
