package com.su.elf.common;


import org.apache.commons.lang3.StringUtils;


/**
 * Hello world!
 *
 */
public class CommonTest {

    public static void main(String [] args){
        System.out.println(StringUtils.isEmpty(" "));
        System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isBlank(" "));
        System.out.println(StringUtils.isBlank(null));
    }



}
