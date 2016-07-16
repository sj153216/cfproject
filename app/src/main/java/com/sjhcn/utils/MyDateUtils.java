package com.sjhcn.utils;

import java.text.SimpleDateFormat;

public class MyDateUtils {

    /**
     * 将时间格式化为年月日
     * 
     * @param time
     *            毫秒值
     * @param separator
     *            分隔符
     * @return
     */
    public static String formatYearMonthDay(long time, String separator) {
        return time > 0 ? new SimpleDateFormat("yyyy" + separator + "MM"
                + separator + "dd").format(time) : "0000" + separator + "00"
                + separator + "00";
    }

    /**
     * 将时间格式化为月日
     * 
     * @param time
     *            毫秒值
     * @param separator
     *            分隔符
     * @return
     */
    public static String formatMonthDay(long time, String separator) {
        return time > 0 ? new SimpleDateFormat("MM" + separator + "dd")
                .format(time) : "00" + separator + "00";
    }

    /**
     * 将时间格式化为时分
     * 
     * @param time
     *            毫秒值
     * @param separator
     *            分隔符
     * @return
     */
    public static String formatHourMin(long time, String separator) {
        return time > 0 ? new SimpleDateFormat("HH" + separator + "mm")
                .format(time) : "00" + separator + "00";
    }

    /**
     * 将时间格式化为分秒
     * 
     * @param time
     *            毫秒值
     * @param separator
     *            分隔符
     * @return
     */
    public static String formatMinSec(long time, String separator) {
        return time > 0 ? new SimpleDateFormat("mm" + separator + "ss")
                .format(time) : "00" + separator + "00";
    }


}
