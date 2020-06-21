package com.su.elf.system.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午1:39
 * @version
 */
@Setter
@Getter
public class Privilege {

    private int id;
    private int parentId;
    private String name;
    private int seq;
    private String parentName;
    private String path;
    private int category;
    private int hasChild;
    private String createBy;
    private String updateBy;
    private String createTime;
    private String updateTime;
    private List<Privilege> subprivileges;

}