package com.su.elf.admin.service.user;


import com.su.elf.admin.service.BaseService;
import com.su.elf.auth.client.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:29
 * @version
 */
public interface UserService extends BaseService {

    User getByName(String userName);

    void addLoginLog(HttpServletRequest request, String username, String message);

}
