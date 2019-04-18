package com.su.common;


import com.su.common.utils.DateUtil;
import com.su.common.utils.encrypt.Base64Util;
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
        String str = "AQALipu+I80NogPyILswYd24Dd5ttG2sDuK/aGTiLdEdrrbLUdPXpaQnQhGd3i9RoGvBU/mdSggWr7mD+kMFaMKrPRCpiLCmAwI1xK0NGjZZlCToIgfg6PaCWyfnvyN8w/vS1wxwdeHupcdZPzQUT2GJAdCjE1T2QMymrvcghC8wfPXlHbjD6E654D7zvzh4DdYpGPE/XPLCQzbOsgyw0W17BICsYAxx06jZn4DyuMtyxMtG1ZwyHK6MWFfxhnA6fOA4t1Pm9aBpKBJ3sqao6eOgyiPvjBHwQCqO+ry0xi7XlpAlqGykkJmWPCKklU2lZEcCgdbsNU/WQWWtcpllijnNcY7BZl1gDgoWLfqEkRVxP9wzXeGP86RcRAaILLR+DO8=";
        System.out.println(Base64Util.decode2String(str));


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
