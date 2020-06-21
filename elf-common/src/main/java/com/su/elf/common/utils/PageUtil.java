package com.su.elf.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author surongyao
 * @date 2020-06-05 12:51
 * @desc
 */
public class PageUtil {

    /**
     * List 分页
     */
    public static List toPage(int page, int size , List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;
        if(fromIndex > list.size()){
            return new ArrayList();
        } else if(toIndex >= list.size()) {
            return list.subList(fromIndex,list.size());
        } else {
            return list.subList(fromIndex,toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     */
    public static JSONObject toPage(Page page) {
        JSONObject json = new JSONObject();
        json.put("list", page.getContent());
        json.put("total", page.getTotalElements());
        return json;
    }

    /**
     * 自定义分页
     */
    public static JSONObject toPage(Object object, Object totalElements) {
        JSONObject json = new JSONObject();
        json.put("list", object);
        json.put("total", totalElements);
        return json;
    }

}

