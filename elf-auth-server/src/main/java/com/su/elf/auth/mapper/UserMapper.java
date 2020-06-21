package com.su.elf.auth.mapper;

import com.su.elf.auth.client.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {

	/**
	 *
	 * @Param:
	 * @return:
	 */
	@Select("SELECT id, username, password, nickname, avatar_url, gender FROM sys_user where username = #{username}")
	// 返回 Map 结果集
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "avatar", column = "avatar_url"),
	})
	AuthUser getByName(@Param("username") String username);

	/**
	 *
	 * @Param:
	 * @return:
	 */
	@Select("SELECT role_id FROM sys_user_role_rel where user_id = #{id}")
	List<Integer> getRoles(@Param("id") int id);

}
