package com.su.elf.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 处理日期时间的工具类。
 * Created by cloud on 16/3/1.
 */
public class DateUtil {

    public static final String DATETIME_FARMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 返回当前时间戳
     * @param
     * @return
     * @throws
     */
    public static String getTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
        return df.format(new Date());
        //Timestamp ts = Timestamp.valueOf(time);
    }

    /**
     * 返回当前时间
     * @param
     * @return
     * @throws
     */
    public static String getDatetme() {
        SimpleDateFormat df = new SimpleDateFormat(DATETIME_FARMAT);
        return df.format(new Date());
    }

    /**
     * 返回当前天
     * @param
     * @return
     * @throws
     */
    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(new Date());
    }

    /**
     * Date转String
     * @param date
     * @param formatType
     * @return
     */
    public static String date2String(Date date, String formatType) {
        return new SimpleDateFormat(formatType).format(date);
    }


    /**
     * String转Date
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date string2Date(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * Long转Date
     * @param currentTime
     * @return
     * @throws ParseException
     */
    public static Date long2Date(long currentTime) throws ParseException {
        Date date = new Date(currentTime);
        return date;
    }

    /**
     * Date转Long
     * @param date
     * @return
     */
    public static long date2Long(Date date)
    {
        return date.getTime();
    }

    /**
     * 判断是否闰年
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if ((year % 100 != 0 && year % 4 == 0) || (year % 400 == 0)) {
            return true;
        }
        return false;
    }


    //时区相关

    /**
     * 判断用户的设备时区是否为东八区（中国）
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08")) {
            defaultVaule = true;
        } else {
            defaultVaule = false;
        }
        return defaultVaule;
    }


    /**
     * 根据不同时区，转换时间
     */
    public static Date transformTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime()) - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }


    /**
     * 得到一个月有几天
     * @param date
     * @return
     * @throws
     */
    public static int getDateOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH)+1;
        int [] monthOf30 = {4,6,9,11};
        int [] monthOf31 = {1,3,5,7,8,10,12};
        for(int m:monthOf30){
            if(month == m){
                return 30;
            }
        }
        for(int m:monthOf31){
            if(month == m){
                return 31;
            }
        }
        int year = calendar.get(Calendar.YEAR);
        if(isLeapYear(year)){
            return 29;
        }else{
            return 28;
        }
    }


    /**
     * 取得指定天数后的时间
     *
     * @param date      基准时间
     * @param dayAmount 指定天数，允许为负数
     * @return 指定天数后的时间
     */
    public static Date addDay(Date date, int dayAmount) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayAmount);
        return calendar.getTime();
    }

    /**
     * 取得指定小时数后的时间
     *
     * @param date       基准时间
     * @param hourAmount 指定小时数，允许为负数
     * @return 指定小时数后的时间
     */
    public static Date addHour(Date date, int hourAmount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hourAmount);
        return calendar.getTime();
    }

    /**
     * 取得指定分钟数后的时间
     *
     * @param date         基准时间
     * @param minuteAmount 指定分钟数，允许为负数
     * @return 指定分钟数后的时间
     */
    public static Date addMinute(Date date, int minuteAmount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minuteAmount);
        return calendar.getTime();
    }

    /**
     * 比较两个日期大小
     * @param @param a，开始时间
     * @param @param b，结束时间
     * @param @param day，指定天数
     * @return boolean    返回类型
     * @throws
     */
    public static int compareDate(Date d1, Date d2){
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int result=c1.compareTo(c2);
        return result;
    }

    /**
     * 判断时间段的差值是否在指定天数内
     * @param @param a，开始时间
     * @param @param b，结束时间
     * @param @param day，指定天数
     * @return boolean    返回类型
     * @throws
     */
    public static boolean tolerateDate(Date a, Date b, int day){
        long la = a.getTime();
        long lb = b.getTime();
        long diff = lb -la;
        long tolerate = (day-1) * 24 * 3600 * 1000;
        return (tolerate >= diff);
    }

}
