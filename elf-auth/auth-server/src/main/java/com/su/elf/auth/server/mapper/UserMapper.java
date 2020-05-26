package com.su.elf.auth.server.mapper;

import com.su.elf.auth.client.entity.User;
import com.su.elf.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 *
	 * @Param:
	 * @return:
	 */
	@Select("SELECT id, role_id, account, password, nickname, readonly, is_super FROM user where account = #{account}")
	// 返回 Map 结果集
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "roleId", column = "role_id"),
			@Result(property = "isSuper", column = "is_super"),
			@Result(property = "username", column = "account"),
	})
	User getByName(@Param("account") String account);

}
