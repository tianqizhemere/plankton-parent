package top.tianqi.plankton.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期操作工具类
 *
 * @Author Wukh
 * @Date 2020/06/28
 */
public class DateUtils {

    /**
     * 例如:2020-06-28
     */
    public static final String DATE = "yyyy-MM-dd";

    /**
     * 例如:2020-06-28 10:00:00
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 例如:10:00:00
     */
    public static final String TIME = "HHmmss";

    /**
     * 例如:10:00
     */
    public static final String TIME_WITHOUT_SECOND = "HH:mm";

    /**
     * 例如:2020-06-28 10:00
     */
    public static final String DATE_TIME_WITHOUT_SECONDS = "yyyy-MM-dd HH:mm";


    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Date getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//0点
        cal.set(Calendar.MINUTE, 0);//0分
        cal.set(Calendar.SECOND, 0);//0秒
        cal.set(Calendar.MILLISECOND, 0);//0毫秒
        return cal.getTime();
    }

    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Date getDayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);//23点
        cal.set(Calendar.MINUTE, 59);//59分
        cal.set(Calendar.SECOND, 59);//59秒
        return cal.getTime();
    }


    /**
     * 获取明天开始时间
     *
     * @return
     */
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayBegin());//当天开始时间
        cal.add(Calendar.DAY_OF_MONTH, 1);//当天月份天数加1
        return cal.getTime();
    }

    /**
     * 获取明天结束时间
     *
     * @return
     */
    public static Date getEndDayOfTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayEnd());//当天结束时间
        cal.add(Calendar.DAY_OF_MONTH, 1);//当天月份天数加1
        return cal.getTime();
    }


    /**
     * 获取某个日期的开始时间
     *
     * @param d
     * @return
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }


    /**
     * 获取某个日期的结束时间
     *
     * @param d
     * @return
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取指定日期所在月的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在月的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取本周的开始时间
     *
     * @return
     */
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayOfWeek);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取本周的结束时间
     *
     * @return
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取今年是哪一年
     *
     * @return
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }


    /**
     * 获取本月是哪一月
     *
     * @return
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }


    /**
     * 获取本月的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }


    /**
     * 获取本月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * 获取上月的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }


    /**
     * 获取上月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * 获取本年的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取本年的结束时间
     *
     * @return
     */
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }


    /**
     * 两个日期相减得到的天数
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 相差天数
     */
    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
        long diff = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        return new Long(diff).intValue();
    }


    /**
     * 两个日期相减得到的毫秒数
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 相差毫秒数
     */
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    /**
     * 日期转换 Date -> String
     *
     * @param date   日期对象
     * @param format 指定转化格式, DateUtils.DATE
     * @return
     */
    public static String dateFormat(Date date, String format) {
        if (date != null && format != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        }
        return null;
    }


    /**
     * 日期转换 Date -> String
     *
     * @param date Date类型信息
     * @return 字符串时间
     */
    public static String dateFormat(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME);
            return formatter.format(date);
        }
        return null;
    }

    /**
     * 将"2015-08-31 21:08:06"型字符串转化为Date
     *
     * @param str 字符串时间
     * @return Date类型信息
     * @throws Exception 抛出异常
     */
    public static Date stringToDate(String str) throws ParseException {
        if (str == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME);
        return formatter.parse(str);
    }

    /**
     * 时间字符串根据格式化字符串和解析点位置生成对应的时间。
     *
     * @param dateString
     *            时间字符串
     *
     * @param pattern
     *            格式化字符串
     * @return 所对应的时间
     */
    public static Date stringToDate(String dateString, String pattern) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }

        if(StringUtils.isEmpty(pattern)){
            pattern = DATE_TIME;
        }


        try {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            return formater.parse(dateString);
        }catch(IllegalArgumentException iae){
            SimpleDateFormat formater = new SimpleDateFormat(DATE_TIME);
            try {
                return formater.parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        } catch (ParseException pe) {
            return null;
        }

    }

    /**
     * 返回指定时间点增加(减少)一段时间后的时间. 返回新对象,源日期对象(<code>date</code>)不变.
     *
     * @param date
     *          源时间,不能为<code>null</code>.
     * @param calendarField
     *          增加(减少)的日历项.
     * @param amount
     *          数量,正数为加,负数为减.
     * @return 日历项增加(减少)指定数目后的日期对象.
     *
     * @throws IllegalArgumentException
     *           如果源日期对象(<code>date</code>)为<code>null</code>.
     */
    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("日期对象不允许为null!");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 增加日期天数
     * 正数往后移动
     * 负数往前移动
     *
     * @param date 时间
     * @param day  天数
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 返回指定时间点 <code>amount</code> 年后(前)的时间. 返回新对象,源日期对象(<code>date</code>)不变.
     *
     * @param date
     *          源时间,不能为<code>null</code>.
     * @param amount
     *          年数,正数为加,负数为减.
     * @return 增加(减少)指定年数后的日期对象.
     *
     * @throws IllegalArgumentException
     *           如果源日期对象(<code>date</code>)为<code>null</code>.
     */
    public static java.util.Date addYears(java.util.Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 返回指定时间点 <code>amount</code> 月后(前)的时间. 返回新对象,源日期对象(<code>date</code>)不变.
     *
     * @param date
     *          源时间,不能为<code>null</code>.
     * @param amount
     *          月数,正数为加,负数为减.
     * @return 增加(减少)指定月数后的日期对象.
     *
     * @throws IllegalArgumentException
     *           如果源日期对象(<code>date</code>)为<code>null</code>.
     */
    public static java.util.Date addMonths(java.util.Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 将时间转换成秒
     *
     * @param date
     * @return
     */
    public static Long getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis() / 1000;
    }
}
