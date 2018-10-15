package com.su.common;


import com.su.common.utils.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class CommonTest {

    @Test
    public void test() {
        String str = "8617857475170";
        System.out.println(str.substring(2));


        /*
        long now = System.currentTimeMillis();
        System.out.println(now);
        Date today = new Date();
        System.out.println(DateUtil.date2String(today, "yyyy-MM-dd HH:mm:ss"));

        Date yeye = DateUtil.addHour(today, -24);
        System.out.println(DateUtil.date2Long(yeye));
        System.out.println(DateUtil.date2String(yeye, "yyyy-MM-dd HH:mm:ss"));
        */
    }

}
