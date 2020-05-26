package com.su.elf.auth.client.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author surongyao
 * @date 2019-05-28 16:43
 * @desc
 */
public interface IAuthService {

    /**
     * 通过HttpServletRequest获取token
     *
     * @param request
     * @return String
     */
    String fetchToken(HttpServletRequest request);

    /**
     * 判断url是否在忽略的范围内
     * 只要是配置中的开头，即返回true
     *
     * @param url
     * @return
     */
    boolean ignoreAuth(String url);

    /**
     * 是否有效token
     *
     * @param token
     * @return
     */
    boolean isValidToken(String token);

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param token
     * @param url
     * @return true/false
     */
    boolean hasPermission(String token, String url);

    /**
     * 获取权限列表
     * @param
     * @return
     * @throws Exception
     */
    List<String> getPrivileges(String token);

    /**
     * 是否超管
     * @param
     * @return
     * @throws Exception
     */
    boolean isSuperAdmin(String token);


    /**
     * 是否只读
     * @param
     * @return
     * @throws Exception
     */
    boolean isReadOnly(String token);


    /**
     * 根据token得到用户名
     * @param
     * @return
     * @throws Exception
     */
    String getUserAccount(String token);

}
