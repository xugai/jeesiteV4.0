package com.jeesite.modules.sys.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rabbit on 2018/5/13.
 */
public class datetimeUtil {
        //joda-time

    private static final String BASIC_FORMAT = "yyyy-MM-dd";

    //用于把日期（年月日）的字符串形式转换成Date类型
    public static Date strToDate(String dateStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(BASIC_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateStr);
        return dateTime.toDate();
    }

    //把Date类型转换成字符串类型，用于标识请假表中的请假编号
    public static String datetimeToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
}
