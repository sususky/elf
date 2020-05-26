package com.su.elf.common.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午2:59
 * @version
 */
public class SearchParam {

    private int id;
    private int roleId;
    private String name;  // 用户名
    private String phone;  // 用户名

    private int limit = 10;
    private int offset;
    private int page = 1;

    private String startTime;
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("page=").append(this.page).append("&limit=").append(this.limit);
        if(this.id>0){
            sb.append("&id=").append(this.id);
        }
        if(this.roleId>0){
            sb.append("&roleId=").append(this.roleId);
        }
        if(StringUtils.isNotEmpty(this.name)){
            sb.append("&name=").append(this.name);
        }
        if(StringUtils.isNotEmpty(this.phone)){
            sb.append("&phone=").append(this.phone);
        }
        if(StringUtils.isNotEmpty(this.startTime)){
            sb.append("&startTime=").append(this.startTime);
        }
        if(StringUtils.isNotEmpty(this.endTime)){
            sb.append("&endTime=").append(this.endTime);
        }
        return sb.toString();
    }
}
