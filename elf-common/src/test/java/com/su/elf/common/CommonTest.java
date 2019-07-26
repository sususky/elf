package com.su.elf.common;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Hello world!
 *
 */
public class CommonTest {

    public static void main(String [] args){

    }

    public static int[] twoSum(int[] nums, int target){
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;i<nums.length;j++){
                if(nums[i] + nums[j] == target){
                    return new int[] {i, j};
                }
            }
        }
        throw new IllegalArgumentException("no two sum solution");
    }





}
