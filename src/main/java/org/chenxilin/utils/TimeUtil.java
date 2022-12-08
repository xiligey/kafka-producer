package org.chenxilin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 *
 * @author chenxilin
 */
public class TimeUtil {
    /**
     * 时间戳转换成日期格式字符串
     *
     * @param milliseconds 精确到毫秒的时间戳
     */
    public static String timeStamp2str(long milliseconds, String format) {
        return new SimpleDateFormat(format).format(new Date(milliseconds));
    }
}
