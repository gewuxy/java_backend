package cn.medcn.common.utils;

import cn.medcn.common.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/1/16.
 */
public class CalendarUtils {
    // 一个月默认为30天
    public static final int DEFAULT_MONTH = 30;

    public static final int DEFAULT_YEAR = 365;

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
     * 两个date是否是同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
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
     * 获取当前时间所在月份第一天的开始时间
     * @return
     */
    public static Date getMonthFirstDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
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

    public static Date getLastTime(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        //将小时至23
        ca.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        ca.set(Calendar.MINUTE, 59);
        //将秒至59
        ca.set(Calendar.SECOND,59);
        return ca.getTime();
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
     * 获取当前时间所在月份最后一天结束时间
     * @return
     */
    public static Date getMonthLastDay(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
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
     * 获取当前时间所在周的周一时间
     * @return
     */
    public static Date getWeekFirstDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
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
     * 获取本周最后一天结束时间
     * @return
     */
    public static Date getWeekLastDay(Date date){
        Calendar c = Calendar.getInstance();
        //设置周一为一周的开始时间
        c.setTime(date);
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
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirstDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentYear = cal.get(Calendar.YEAR);
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
     * 获取当年的最后一天
     * @return
     */
    public static Date getCurrYearLastDay(Date date) throws ParseException {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int currentYear = cal.get(Calendar.YEAR);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date lastDate = format.parse(currentYear + "-12-31 23:59");
        return lastDate;
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

    public static Date getQuarterFirstDate(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);//获取月份
        int year = cal.get(Calendar.YEAR); //获取年份
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(month <= 3){
            return format.parse(year + "-01-01 00:00");
        } else if (month > 3 && month <= 6) {
            return format.parse(year + "-04-01 00:00");
        }else if(month > 6 && month <=9){
            return format.parse(year + "-07-01 00:00");
        }else{
            return format.parse(year + "-10-01 00:00");
        }
    }

    public static Date getQuarterLastDate(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);//获取月份
        int year = cal.get(Calendar.YEAR); //获取年份
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(month <= 3){
            return format.parse(year + "-03-31 23:59");
        } else if (month > 3 && month <= 6) {
            return format.parse(year + "-06-30 23:59");
        }else if(month > 6 && month <=9){
            return format.parse(year + "-09-30 23:59");
        }else{
            return format.parse(year + "-12-31 23:59");
        }
    }

    /**
     * 指定日期加上月数
     * @param date
     * @param monthCount
     * @return
     */
    public static Date dateAddMonth(Date date,int monthCount){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, monthCount);// 日期加3个月
        Date endDate = rightNow.getTime();
        return endDate;
    }


    //获取某段时间内的所有日期
    public static List<Date> getAllDateList(Date dBegin, Date dEnd) throws ParseException {
        List<Date> list = new ArrayList<>();
        list.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while(dEnd.after(calBegin.getTime())){
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            list.add(calBegin.getTime());
        }

        return list;
    }


    /**
     * 获取指定时间段的所有星期一的日期
     * @param dBegin
     * @param dEnd
     * @return
     * @throws ParseException
     */
    public static List<Date> getWeekFirstDateList(Date dBegin, Date dEnd) throws ParseException {
        List<Date> list = getAllDateList(dBegin,dEnd);
        List<Date> mondayList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Calendar cal = Calendar.getInstance();
            cal.setTime(list.get(i));
            int dayForWeek = 0;
            if(cal.get(Calendar.DAY_OF_WEEK) == 1){
                continue;
            }else{
                dayForWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
                if(dayForWeek == 1){
                    mondayList.add(list.get(i));
                }
            }



        }
        return mondayList;
    }


    /**
     * 获取时间段内所有月份的第一天
     * @param dBegin
     * @param dEnd
     * @return
     * @throws ParseException
     */
    public static List<Date> getMonthFirstDateList(Date dBegin, Date dEnd) throws ParseException {
        List<Date> list = getAllDateList(dBegin,dEnd);
        List<Date> monthList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Calendar cal = Calendar.getInstance();
            cal.setTime(list.get(i));
            if(cal.get(Calendar.DAY_OF_MONTH ) == 1 ){
                monthList.add(list.get(i));
            }
        }
        return monthList;
    }

    /**
     * 获取时间段内所有季度的第一天
     * @param dBegin
     * @param dEnd
     * @return
     * @throws ParseException
     */
    public static List<Date> getQuarterFirstDateList(Date dBegin, Date dEnd) throws ParseException {
        List<Date> list = getAllDateList(dBegin,dEnd);
        List<Date> quarterList = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for(int i=0;i<list.size();i++){
            Calendar cal = Calendar.getInstance();
            cal.setTime(list.get(i));
            Date date = null;
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            if(month <= 3){
                date = format.parse(year + "-01-01");
            }else if(month > 3 && month <= 6){
                date = format.parse(year + "-04-01");
            }else if(month > 6 && month <= 9){
                date = format.parse(year + "-07-01");
            }else{
                date = format.parse(year + "-10-01");
            }

            if(!quarterList.contains(date)){
                quarterList.add(date);
            }

        }
        return quarterList;
    }


    /**
     * 获取时间段内所有月份的第一天
     * @param dBegin
     * @param dEnd
     * @return
     * @throws ParseException
     */
    public static List<Date> getYearFirstDateList(Date dBegin, Date dEnd) throws ParseException {
       Calendar cal = Calendar.getInstance();
       cal.setTime(dBegin);
       int start = cal.get(Calendar.YEAR);
       cal.setTime(dEnd);
       int end = cal.get(Calendar.YEAR);
       if(end >= start){
           List<Date> list = new ArrayList<>();
           DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           Date date =  null;
           if(end == start){
               date = format.parse(start + "-01-01");
               list.add(date);
               return list;
           }
           int interval = end - start;
           for(int i = 0 ; i < interval ; i++){
                date = format.parse(start + "-01-01");
               list.add(date);
               start ++;
           }
           return list;
       }

        return null;
    }

    /**
     * 少一秒算法，一天时间为00:00:00 - 23:59:59
     *
     * @param date
     * @param day
     * @return
     */
    public static Date calendarTime(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        Date newDate = new Date();
        newDate.setTime(date.getTime() - 1000);
        calendar.setTime(newDate);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 获取当前日期
     * @return 返回字符串 yyyyMMdd 格式
     */
    public static String getCurrentDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }



    public static void main(String[] args) throws ParseException {
       /*Date date = calendarMonth(2);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));*/
        //sec = (diff / Constants.NUMBER_THOUSAND - TimeUnit.DAYS.toSeconds(day) - TimeUnit.MINUTES.toSeconds(min));
     /*   String[] array = getWeekStartToEndByOffset(1);
        for (String date:array){
            System.out.println(date);
        }

        Long s = 996l;
        System.out.println(secToTime(s.intValue()));*/
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate1 = dateFormat1.parse("2018-01-29 00:00:00");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate2 = dateFormat2.parse("2018-01-28 23:59:58");
        System.out.println(isSameDate(myDate1,myDate2));
        System.out.println(getWeekFirstDay());
        List list = getAllDateList(myDate1,myDate2);
        System.out.println(daysBetween(myDate1,myDate2));
        System.out.println(dateFormat1.format(calendarDay(myDate1,2)));
        System.out.println(dateFormat1.format(calendarTime(myDate2,2)));



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String st = "2017-10-13 09:49:54";

        try {

            Date startTime = sdf.parse(st);
            Date endTime = new Date();
            System.out.println("ss: "+formatTimesDiff(startTime, endTime,0));
            System.out.println(sdf.format(calendarDay(1)));
            Calendar calendar = Calendar.getInstance();

            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dateString = formatter.format(currentTime);
            System.out.println(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




}
