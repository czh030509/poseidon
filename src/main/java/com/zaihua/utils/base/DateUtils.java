package com.zaihua.utils.base;

/**
 * Created by carl on 29/03/2017.
 */

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

//import java.time.LocalDate;
//import java.time.ZoneId;

/**
 * 实现描述：时间操作工具类
 *
 * @author zixin.zhang
 * @version v1.0.0
 * @see
 * @since 13-8-12上午10:37
 */
public class DateUtils {
    public final static String DATE_CHINESE_PATTERN = "yyyy年MM月dd日";
    public final static String DATE_MONTH_DAY_CHINESE_PATTERN = "MM月dd日";

    /**
     * 标准的中文小时分钟格式
     */
    public final static String HOUR_MINUTE_CHINESE_PATTERN = "HH点mm分";

    /**
     * 标准日期格式
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    public final static String DATE_YYYY_MM = "yyyy-MM";

    public final static String DATE_SHORT_PATTERN = "yyyyMMdd";

    public final static String DATE_SLASH_PATTERN = "yyyy/MM/dd";
    public final static String DATE_SHORT_SLASH_PATTERN = "MM/dd HH:mm";

    /**
     * 标准日期时分秒毫秒格式
     */
    public final static String DATETIME_MILL_SECOND = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 标准时间格式
     */
    public final static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准时间格式，不含秒
     */
    public final static String DATETIME_PATTERN_SHORT = "yyyy-MM-dd HH:mm";

    /**
     * 特殊的格式 针对创建订单，拼凑的最晚支付时间
     */
    public final static String DATETIME_PATTERN_CREAT_ORDER = "yyyy-MM-ddHH:mm";

    public final static String DATETIME_SHORT_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准年小时分钟格式
     */
    public final static String HOUR_MINUTE = "HH:mm";

    /**
     * 标准年小时分钟秒格式
     */
    public final static String HOUR_MINUTE_SECOND = "HH:mm:ss";

    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * Number of milliseconds in a standard day.
     */
    public static final long MILLIS_PER_DAY = 24 * DateUtils.MILLIS_PER_HOUR;

    /**
     * Number of milliseconds in a standard hour.
     */
    public static final long MILLIS_PER_HOUR = 60 * DateUtils.MILLIS_PER_MINUTE;

    /**
     * Number of milliseconds in a standard minute.
     */
    public static final long MILLIS_PER_MINUTE = 60 * DateUtils.MILLIS_PER_SECOND;

    /**
     * Number of milliseconds in a standard second.
     */
    public static final long MILLIS_PER_SECOND = 1000;

    /**
     * Number of day in a year
     */
    public static final int DAYS_PER_YEAR = 365;

    /**
     * 标准年月格式
     */
    public final static String MONTH_PATTERN = "yyyy-MM";

    private final static String[] WEEK_NAMES = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天" };

    /**
     * 在指定日期增加指定月数
     *
     * @param date 指定日期
     * @param months 指定月数
     * @return
     */
    public static Date addMonth(Date date, int months) {
        if (months == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, months);
        return c.getTime();
    }

    /**
     * 在指定日期增加指定天数
     *
     * @param date 指定日期
     * @param days 指定天数
     * @return
     */
    public static Date addDay(Date date, int days) {
        if (days == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTime();
    }

    /**
     * 在指定日期增加指定年数
     * @param date
     * @param years
     * @return
     */
    public static Date addYears(Date date, int years) {
        if (years == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, years);
        return c.getTime();
    }

    /**
     * 在指定日期增加指定天数
     *
     * @param date 指定日期
     * @param days 指定天数
     * @return
     */
    public static Date addDay(String date, int days) {
        return DateUtils.addDay(DateUtils.convertDate(date), days);
    }

    public static Date addMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
    }

    public static Date addHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    public static Date addSecond(Date date, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, second);
        return c.getTime();
    }

    /**
     * 当前日期之后
     *
     * @param date
     * @return
     */
    public static boolean afterToday(Object date) {
        Date currentDate = new Date();
        return DateUtils.compareDate(date, currentDate) == 1;
    }

    /**
     * 当前时间之后
     *
     * @param date
     * @return
     */
    public static boolean afterTodayDate(Date date) {
        Date currentDate = new Date();
        return currentDate.compareTo(date) == -1;
    }

    /**
     * 当前日期之前
     *
     * @param date
     * @return
     */
    public static boolean beforeToday(Object date) {
        Date currentDate = new Date();
        return DateUtils.compareDate(date, currentDate) == -1;
    }

    /**
     * 当前时间之前
     *
     * @param date
     * @return
     */
    public static boolean beforeTodayDate(Date date) {
        Date currentDate = new Date();
        return currentDate.compareTo(date) == 1;
    }

    /**
     * 比较两个日期date1大于date2 返回1 等于返回0 小于返回-1
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Object date1, Object date2) {
        if (date1 == null || date2 == null) {
            String msg = "illegal arguments,date1 and date2 must be not null.";
            throw new IllegalArgumentException(msg);
        }
        Date d1 = (Date) (date1 instanceof String ? DateUtils.convertDate((String) date1) : date1);
        Date d2 = (Date) (date2 instanceof String ? DateUtils.convertDate((String) date2) : date2);
        return DateUtils.round(d1, Calendar.DATE).compareTo(DateUtils.round(d2, Calendar.DATE));
    }

    public static Date convertDate(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            String msg = "the date or pattern is empty.";
            throw new IllegalArgumentException(msg);
        }
        String dateForPattern = DateUtils.formatDate(date, pattern);
        return DateUtils.convertDate(dateForPattern, pattern);
    }

    /**
     * 将long型整数转化为时间。
     *
     * @param date 时间对应的long值
     * @return 时间对象
     */
    public static Date convertDate(Long date) {
        return new Date(date);
    }

    /**
     * 将日期或者时间戳转化为日期对象
     *
     * @param date yyyy-MM-dd or yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static Date convertDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        if (StringUtils.isNumeric(date)) {
            long timestamp = Long.parseLong(date);
            if (timestamp > 0 && timestamp < Long.MAX_VALUE) {
                return new Date(timestamp);
            } else {
                return null;
            }
        }
        if (date.indexOf(":") > 0) {
            return DateUtils.convertDate(date, DateUtils.DATETIME_PATTERN);
        } else if (date.indexOf(".") > 0) {
            return DateUtils.convertDate(date, DateUtils.DATETIME_MILL_SECOND);
        } else {
            return DateUtils.convertDate(date, DateUtils.DATE_PATTERN);
        }
    }

    /**
     * 将日期或者时间字符串转化为日期对象
     *
     * @param date 日期字符串
     * @param pattern 格式字符串</br> yyyy-MM-DD, yyyy/MM/DD, yyyyMMdd</br> yyyy-MM-dd-HH:mm:ss, yyyy-MM-dd HH:mm:ss
     *            格式字符串可选字符："GyMdkHmsSEDFwWahKzZ"
     * @return Date
     */
    public static Date convertDate(String date, String pattern) {
        if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(date) || "null".equals(date)) {
            logger.error("the date or pattern is empty, params date:{},pattern:{}.", date, pattern);
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern.trim());
            return df.parse(date.trim());
        } catch (Exception e) {
            DateUtils.logger.error("Method===DateUtils.convertDate error!", e);
        }
        return null;
    }

    /**
     * 将时间字符串转化为时间对象Time
     *
     * @param time 时间字符串
     * @param pattern 格式字符串 yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static Time convertTime(String time, String pattern) {
        if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(time) || "null".equals(time)) {
            return null;
        }
        String timeVal = autoSplitOrPadingTimeChar(time, "00:00:00");
        Date d = DateUtils.convertDate(timeVal, pattern);
        if(null == d){
            return null;
        }
        return new Time(d.getTime());
    }

    /**
     * 获得日期相差天数
     *
     * @param date1 日期
     * @param date2 日期
     * @return
     */
    public static int diffDate(Date date1, Date date2) {
        return (int) ((date1.getTime() - date2.getTime()) / DateUtils.MILLIS_PER_DAY);
    }

    /**
     * 获取两个日期相差的分钟数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int diffMinute(Date date1, Date date2) {
        return (int) ((date1.getTime() - date2.getTime()) / DateUtils.MILLIS_PER_MINUTE);
    }

    /**
     * 获取两个日期相差的分钟数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int diffSecond(Date date1, Date date2) {
        return (int) ((date1.getTime() - date2.getTime()) / DateUtils.MILLIS_PER_SECOND);
    }

    /**
     * 格式为时间字符串
     *
     * @param date 日期
     * @return yyyy-MM-dd Date
     */
    public static String formatDate(Date date) {
        try {
            return DateUtils.formatDate(date, DateUtils.DATE_PATTERN);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 按指定格式字符串格式时间
     *
     * @param date 日期或者时间
     * @param pattern 格式化字符串 yyyy-MM-dd， yyyy-MM-dd HH:mm:ss, yyyy年MM月dd日 etc.</br>
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date != null && StringUtils.isNotBlank(pattern)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(pattern.trim());
                return format.format(date);
            } catch (Exception e) {
                logger.error("formatDate error. pattern:{}, reason:{}.", pattern, e.getMessage());
            }
        }
        return "";
    }

    /**
     * 格式为时间戳字符串
     *
     * @param date 时间
     * @return yyyy-MM-dd HH:mm:ss Date
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DateUtils.DATETIME_PATTERN);
    }

    /**
     * 将制定时间格式为字符串'yyyyMMddHHmmss'.
     *
     * @return
     */
    public static String formatDateToYMDHMS(Date date) {
        return formatDate(date, DateUtils.DATETIME_SHORT_PATTERN);
    }

    public static String formatMonth(Date date) {
        return formatDate(date, DateUtils.MONTH_PATTERN);
    }

    /**
     * 将当前时间格式为字符串'yyyyMMddHHmmss'.
     *
     * @return
     */
    public static String formatNowToYMDHMS() {
        return DateUtils.formatDateToYMDHMS(new Date());
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static Date getDateFromShortString(String str) {
        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDF.parse(str);
        } catch (ParseException e) {
            DateUtils.logger.warn("parse date error", e);
        }
        return null;
    }

    /**
     * 获得本周第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfThisWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获得小时
     *
     * @param date
     * @return
     */
    public static int getHourOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得分钟数
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }


    /**
     * 获得年数
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获得月数
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        return localDate.getMonthValue();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }
    /**
     * 获取后续第n天日期
     *
     * @param date
     * @param n 第n天
     * @return
     */
    public static Date getNextNDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获得星期数
     *
     * @param date 日期
     * @return
     */
    public static int getWeekNumber(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int number = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (number == 0) {
            number = 7;
        }
        return number;
    }

    /**
     * 获得星期名称
     *
     * @param date
     * @return
     */
    public static String getWeekNumberString(Date date) {
        int dayNum = DateUtils.getWeekNumber(date);
        return DateUtils.WEEK_NAMES[dayNum - 1];
    }

    /**
     * 是否同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Object date1, Object date2) {
        return DateUtils.compareDate(date1, date2) == 0;
    }

    /**
     * 检查时间或者字符串是否合法
     *
     * @param date 时间
     * @param pattern 格式串
     * @return
     */
    public static boolean isValidDate(String date, String pattern) {
        try {
            Date dateConvert = DateUtils.convertDate(date, pattern);
            return dateConvert != null;
        } catch (Exception e) {
            DateUtils.logger.error("Method===DateUtils.isValidDate error!", e);
            return false;
        }
    }

    /**
     * 获得当前时间戳
     *
     * @return Timestamp
     */
    public static Timestamp now() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 获得当前时间字符串,格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String nowDateTime() {
        return DateUtils.formatDate(new Date(), DateUtils.DATETIME_PATTERN);
    }

    /**
     * 按指定roundType格式化日期。
     *
     * @param date 日期
     * @param roundType
     * @return Date
     */
    public static Date round(Date date, int roundType) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        switch (roundType) {
            case Calendar.MONTH:
                c.set(Calendar.DAY_OF_MONTH, 1);
            case Calendar.DATE:
                c.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR:
                c.set(Calendar.MINUTE, 0);
            case Calendar.MINUTE:
                c.set(Calendar.SECOND, 0);
            case Calendar.SECOND:
                c.set(Calendar.MILLISECOND, 0);
                return c.getTime();
            default:
                throw new IllegalArgumentException("invalid round roundType.");
        }
    }

    /**
     * 获得当前日期对象
     *
     * @return
     */
    public static Date today() {
        return DateUtils.convertDate(DateUtils.formatDate(new Date()), DateUtils.DATE_PATTERN);
    }

    /**
     * 获得当前日期字符串,格式为：yyyy-MM-dd
     *
     * @return
     */
    public static String todayDate() {
        return DateUtils.formatDate(new Date());
    }

    /**
     *
     * 将日期或者时间字符串转化为Timestamp对象
     *
     * @param date 日期字符串
     * @param pattern 格式字符串</br> yyyy-MM-DD, yyyy/MM/DD, yyyyMMdd</br> yyyy-MM-dd-HH:mm:ss, yyyy-MM-dd HH:mm:ss
     * @return Timestamp
     * @author reeboo
     */
    public static Timestamp toTimestamp(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern.trim());
        try {
            return new Timestamp(format.parse(date).getTime());
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date getFirstDateOfCurrentMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getFirstDateFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 判断当前时间是否在某个区间内
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean todayInClose(Date startDate,Date endDate){
        Date currentDate =convertDate(new Date(),DATE_PATTERN);
        return currentDate.getTime() >= startDate.getTime() && currentDate.getTime() <= endDate.getTime();
    }

    /**
     * 判断两个时间区间是否有交集
     * @param closeOneStartDate
     * @param closeOneEndDate
     * @param closeTwoStartDate
     * @param closeTwoEndDate
     * @return
     */
    public static boolean timeIntersection(Date closeOneStartDate,Date closeOneEndDate,Date closeTwoStartDate,
                                           Date closeTwoEndDate){
        long start = Math.max(closeOneStartDate.getTime(), closeTwoStartDate.getTime());
        long end = Math.min(closeOneEndDate.getTime(), closeTwoEndDate.getTime());
        return start <= end;
    }

    /**
     * 自动截取或补齐时间串为"HH:mm:ss"
     * <p>
     * 处理case有:<br>
     * 0:00
     * 24:00:00
     * 2015-08-31 17:12:12
     * 2015-08-31 17:12:12.101
     * mm
     * </p>
     *
     * @param timeVal
     * @return
     */
    private static String autoSplitOrPadingTimeChar(String timeVal, String defVal) {
        if(StringUtils.isBlank(timeVal)) {
            return defVal;
        }
        if("24:00:00".equals(timeVal)) {
            return "23:59:59";
        }
        Splitter splitter = Splitter.on(CharMatcher.anyOf(" :.")).trimResults().omitEmptyStrings();
        Iterable<String> iterable = splitter.split(timeVal);
        Iterator<String> it = iterable.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            String entity = (String) it.next();
            if(entity.length() > 2) { // filter yyyy-MM-dd
                continue;
            }
            sb.append(StringUtils.leftPad(entity, 2, '0'));
            sb.append(":");
        }
        String rs = StringUtils.rightPad(sb.toString(), 8, "00:"); // HH:mm:ss
        return rs.substring(0, 8);
    }


    /**
     * 对日期列表进行排序
     * 注意若对unmodifiable list进行排序可能会抛出UnsupportedOperationException异常
     * 如Guava中的Lists.transform方法构造的list
     * @param dateList
     */
    public static void dateSort (List<Date> dateList) {
        Collections.sort(dateList, new Comparator<Date>() {
            public int compare(Date m1, Date m2) {
                return m1.compareTo(m2);
            }
        });
    }
    /**
     * 时间范围
     * @author liande
     * @time 2016年1月3日 下午10:11:43
     */
    public static class DateRange {
        private Date startDate;
        private Date endDate;

        public static DateRange withTime(Date startDate, Date endDate) {
            DateRange dateRange = new DateRange();
            dateRange.startDate = startDate;
            dateRange.endDate = endDate;
            return dateRange;
        }

        public long getRange() {
            return endDate.getTime() - startDate.getTime();
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((endDate == null) ? 0 : endDate.hashCode());
            result = prime * result
                    + ((startDate == null) ? 0 : startDate.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DateRange other = (DateRange) obj;
            if (endDate == null) {
                if (other.endDate != null)
                    return false;
            } else if (!endDate.equals(other.endDate))
                return false;
            if (startDate == null) {
                if (other.startDate != null)
                    return false;
            } else if (!startDate.equals(other.startDate))
                return false;
            return true;
        }

    }

}

