package cn.medcn.common.utils;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lixuan on 2017/1/16.
 */
public class CalendarUtils {
    // 一个月默认为30天
    public static final int DEFAULT_MONTH = 30;

    /**
     * 计算month个月之后的时间
     * @param month
     * @return
     */
    public static Date calendarMonth(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 后一天的开始时间
     *
     * @return
     */
    public static Date nextDateStartTime(){
        Date date = calendarDay(Constants.NUMBER_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 计算day天之后的时间
     * @param day
     * @return
     */
    public static Date calendarDay(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 计算date时间day天之后的时间
     * @param day
     * @return
     */
    public static Date calendarDay(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 计算year之后的时间
     * @param year
     * @return
     */
    public static Date calendarYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }


    /**
     * 获取本月第一天的开始时间
     * @return
     */
    public static Date getMonthFirstDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH,1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND,0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取本月最后一天结束时间
     * @return
     */
    public static Date getMonthLastDay(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        ca.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        ca.set(Calendar.MINUTE, 59);
        //将秒至59
        ca.set(Calendar.SECOND,59);
        return ca.getTime();
    }

    /**
     * 获取本周第一天开始时间
     * @return
     */
    public static Date getWeekFirstDay(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND,0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 根据偏移量计算offset之前的周一
     * @param offset
     * @return
     */
    public static Date getWeekStartByOffset(int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 0-offset);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND,0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据偏移量计算offset之前的周日
     * @param offset
     * @return
     */
    public static Date getWeekEndByOffset(int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 0-offset);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }


    public static String[] getWeekStartToEndByOffset(int offset){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 0-offset);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String[] array = new String[7];
        for (int index = 2; index <= 7; index++){
            calendar.set(Calendar.DAY_OF_WEEK, index);
            array[index-2] = format.format(calendar.getTime());
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        array[6] = format.format(calendar.getTime());
        return array;
    }

    /**
     * 获取一周的时间戳
     * @param offset
     * @return
     */
    public static long[] getWeekTimeStartToEndByOffset(int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 0-offset);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        long[] array = new long[7];
        for (int index = 2; index <= 7; index++){
            calendar.set(Calendar.DAY_OF_WEEK, index);
            array[index-2] = calendar.getTime().getTime();
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        array[6] = calendar.getTime().getTime();
        return array;
    }

    /**
     * 获取本周最后一天结束时间
     * @return
     */
    public static Date getWeekLastDay(){
        Calendar c = Calendar.getInstance();
        //设置周一为一周的开始时间
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        c.set(Calendar.MINUTE,59);
        //将秒至59
        c.set(Calendar.SECOND,59);
        return c.getTime();
    }

    /**
     * 根据日期 获取星期名
     * @param day
     * @return
     */
    public static String getWeekNameByDay(Integer day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        String retWeekName = null;
        switch (weekDay){
            case 1:
                retWeekName = "周日";
                break;
            case 2:
                retWeekName = "周一";
                break;
            case 3:
                retWeekName = "周二";
                break;
            case 4:
                retWeekName = "周三";
                break;
            case 5:
                retWeekName = "周四";
                break;
            case 6:
                retWeekName = "周五";
                break;
            case 7:
                retWeekName = "周六";
                break;
            default:
                break;
        }
        return retWeekName;
    }


    /**
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirstDay(){
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirstDay(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirstDay(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static Date getCurrYearLastDay(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLastDay(currentYear);
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLastDay(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 获取当年的当前日期
     * @return
     */
    public static Date getYearCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 0);
        Date currYearDay = calendar.getTime();
        return currYearDay;
    }


    /**
     *  获取时间差（时分秒）
     * @param startTime
     * @param endTime
     */
    public static String getTimesDiff(Date startTime,Date endTime){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        return (day*24+hour) + ":" + min + ":" + sec ;
    }

    /**
     * 获取时间差 或者秒数 转换为分′秒″ 格式
     * @param startTime
     * @param endTime
     * @param secondTime
     * @return
     */
    public static String formatTimesDiff(Date startTime, Date endTime, long secondTime){
        long diff = 0l;
        if (startTime != null && endTime != null) {

            long stTime = startTime.getTime();
            long edTime = endTime.getTime();

            if (stTime < edTime) {
                diff = edTime - stTime;
            } else {
                diff = stTime - edTime;
            }

            diff = diff / 1000;
        }

        if (secondTime > 0) {
            diff = secondTime;
        }
        long min = diff / 60;
        long sec = diff % 60;
        return unitFormat((int) min) + "′" + unitFormat((int) sec) + "″";
    }

    /**
     * 将秒数 转换为 时分秒格式
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }


    public static String formatTime(long time){
        if(time < 3600){
            return time/60+"分钟";
        }else if(time < 24*3600){
            return time/3600+"小时"+time%3600/60+"分钟";
        } else {
            return time/(24*3600)+"天"+time%(24*3600)/3600+"小时"+time%3600/60+"分钟";
        }
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    /**
     * 将long型转换为string型
     * @param millSec  毫秒数
     * @param formatType 日期类型
     * @return
     */
    public static String transferLongToDate(Long millSec, String formatType) {
         SimpleDateFormat sdf = new SimpleDateFormat(formatType);
         Date date = new Date(millSec);
         return sdf.format(date);
    }

    /**
     * 计算两个日期之间相差的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int daysBetween(Date startDate, Date endDate) throws ParseException {
        int days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        startDate = sdf.parse(sdf.format(startDate));
        endDate = sdf.parse(sdf.format(endDate));

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long sTime = cal.getTimeInMillis();

        cal.setTime(endDate);
        long eTime = cal.getTimeInMillis();

        if (eTime > sTime) {
            long betweenDays = (eTime - sTime) / (1000*3600*24);
            days = Integer.parseInt(String.valueOf(betweenDays));
        }
        return days;
    }

    public static void main(String[] args) {
       /*Date date = calendarMonth(2);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));*/
        //sec = (diff / Constants.NUMBER_THOUSAND - TimeUnit.DAYS.toSeconds(day) - TimeUnit.MINUTES.toSeconds(min));
     /*   String[] array = getWeekStartToEndByOffset(1);
        for (String date:array){
            System.out.println(date);
        }

        Long s = 996l;
        System.out.println(secToTime(s.intValue()));*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String st = "2017-10-13 09:49:54";

        try {

            Date startTime = sdf.parse(st);
            Date endTime = new Date();
            System.out.println("ss: "+formatTimesDiff(startTime, endTime,0));
            System.out.println(sdf.format(calendarDay(1)));
            Calendar calendar = Calendar.getInstance();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
