package com.su.elf.system.service;



import com.su.elf.common.entity.SearchParam;

import java.util.List;

/**
 * Created by cloud on 16/1/18.
 */
public interface BaseService<T> {

    /**
     * get list.
     *
     * @param params
     * @return List
     * @throws
     */
    List<T> getList(SearchParam params);

    /**
     * get count.
     *
     * @param params
     * @return int
     * @throws
     */
    int getCount(SearchParam params);

    /**
     * get pojo.
     *
     * @param id
     * @return T
     * @throws
     */
    T getPojo(int id);

    /**
     * add pojo.
     *
     * @param pojo
     * @return int
     * @throws
     */
    int insertPojo(T pojo);

    /**
     * update pojo.
     *
     * @param pojo
     * @return int
     * @throws
     */
    int updatePojo(T pojo);

    /**
     * delete pojo.
     *
     * @param id
     * @return int
     * @throws
     */
    int deletePojo(int id);

}
