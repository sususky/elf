package com.su.elf.system.mapper;

import com.su.elf.common.mapper.BaseMapper;
import com.su.elf.system.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

	User getByName(String account);

}
