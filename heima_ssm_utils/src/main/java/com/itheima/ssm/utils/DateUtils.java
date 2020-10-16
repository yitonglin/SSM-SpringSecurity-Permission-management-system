package com.itheima.ssm.utils;
import java.text.ParseException;
import	java.text.SimpleDateFormat;

import java.util.Date;

/**
 * 时间字符串转换
 */
public class DateUtils {

    /**
     * 时间格式转字符串
     * @param date
     * @param pattern 转换格式
     * @return
     */
    public static String date2String(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(date);
        return format;
    }


    /**
     * 字符串转时间格式
     * @param str
     * @param patterns
     * @return
     */
    public static Date string2Date(String str,String patterns) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(patterns);
        Date date = sdf.parse(str);
        return date;
    }
}
