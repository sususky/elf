package com.su.elf.common.mapper;

import com.su.elf.common.entity.SearchParam;

import java.util.List;

/**
 * @author surongyao
 * @Type
 * @Desc
 * @date 2018-03-23 11:29
 */
public interface BaseMapper<T> {
    /**
     * get list.
     *
     * @param searchParam
     * @return List
     * @throws
     */
    List<T> getList(SearchParam searchParam);

    /**
     * get count.
     *
     * @param searchParam
     * @return int
     * @throws
     */
    int getCount(SearchParam searchParam);

    /**
     * get pojo.
     *
     * @param id
     * @return T
     * @throws
     */
    T get(int id);

    /**
     * add pojo.
     *
     * @param t
     * @return int
     * @throws
     */
    int insert(T t);

    /**
     * update pojo.
     *
     * @param t
     * @return int
     * @throws
     */
    int update(T t);

    /**
     * delete pojo.
     *
     * @param id
     * @return int
     * @throws
     */
    int delete(int id);

}
