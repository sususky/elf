package com.su.common;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class CommonTest {

    @Test
    public void test() {
        float s = 100.0f/21;
        System.out.println(s);
    }

    public int getRangeInt(List<Integer> list) {
        //产生0-(list.size-1)的整数值,也是数组的索引
        int index = (int) (Math.random() * list.size());
        return list.remove(index);
    }

}
