package com.su.elf.system.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午1:41
 * @version
 */
@Setter
@Getter
public class Role {

    private int id;
    private String name;
    private String description;
    private String createTime;
    private List<Integer> privileges;

}