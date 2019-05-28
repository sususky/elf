package com.su.common;


import org.junit.Test;


/**
 * Hello world!
 *
 */
public class CommonTest {

    @Test
    public void test() {
        System.out.println(toShortAddr("吉林省吉林市舒兰市电鑫源小区17号楼"));
    }

    public String toShortAddr(String districtAddr) {
        String newAddr = districtAddr;
        if (districtAddr != null && !districtAddr.isEmpty()) {
            int index0 = districtAddr.indexOf("省");
            int index1 = districtAddr.indexOf("市");
            if(index0 + index1 >= 0){
                // 有省字
                newAddr = districtAddr.substring(index1 + 1);
            }
        }
        return newAddr;
    }

}
