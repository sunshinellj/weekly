package cn.caitc.weekly.util;

import cn.caitc.weekly.constant.CommonConstant;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */

public class CommonUtil {

    public static void main(String[] args) throws Exception {
        int year = 2015;
        int month = 4;
        int week = 1;
        System.out.println(weekToDayFormDot(year, month, week));
        //获取一个时间段内的日期
        /*List<String> dateList= getDatePeriod(new Date(), 10);
        for(String date:dateList){
            System.out.println(date);
        }  */
        //getNextWeekDay(new Date());
    }


    /**
     * 判断日期值是否有效
     *
     * @param str String
     * @return boolean
     */

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 判断是否是数字
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 根据年、月、期求所处的时间段
     *
     * @param year  int
     * @param month int
     * @param week  int
     * @param df    String[]
     * @return SimpleDateFormat
     */
    public static String[] weekToDayConver(int year, int month, int week,
                                           SimpleDateFormat df) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        //日历中的本月第一周的周一不在本月时，则不是系统所认为的本月第一周
        cal.set(Calendar.WEEK_OF_MONTH, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (cal.get(Calendar.MONTH) != month - 1) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.WEEK_OF_MONTH, week + 1);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.WEEK_OF_MONTH, week);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }

        String startDate = df.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = df.format(cal.getTime());
        String[] dates = {startDate, endDate};
        return dates;
    }

    /**
     * @param year  int
     * @param month int
     * @param week  int
     * @return 返回值如 2015.8.1-2015.8.7
     */
    public static String weekToDayFormDot(int year, int month, int week) {
        String dates = weekToDayForm2(year, month, week);
        return dates.replaceAll("/", ".");
    }

    /**
     * @param year  年
     * @param month 月
     * @param week  日
     * @return 返回值(如 2015/08/01-2015/08/07)
     */
    public static String weekToDayForm2(int year, int month, int week) {
        return commonFormat(year, month, week, CommonConstant.DATE_FORMAT_YYYYMMDD, "-");
    }

    /**
     * 根据 年、月、期，确定该期的起止日期
     *
     * @param year  年
     * @param month 月
     * @param week  日
     * @return 返回值(如 20150801~20150807)
     */
    public static String weekToDay(int year, int month, int week) {
        return commonFormat(year, month, week, CommonConstant.DATE_FORMAT_YYYYMMDD, "~");
    }

    /**
     * 根据 年、月、期，确定该期的起止日期
     *3168752242
     * @param year  年
     * @param month 月
     * @param week  日
     * @param style 年月日格式
     * @param sign  符号
     * @return 返回值(如 20150801~20150807)
     */
    private static String commonFormat(int year, int month, int week, String style, String sign) {
        SimpleDateFormat df = new SimpleDateFormat(style);
        String[] dates = weekToDayConver(year, month, week, df);
        return dates[0] + sign + dates[1];
    }

    /**
     * 求出某月有多少个周一
     *
     * @param syear  String
     * @param smonth String
     * @return int
     */
    public static int mondayCount(String syear, String smonth) {
        // 月份
        int year = Integer.parseInt(syear);
        int month = Integer.parseInt(smonth);
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1); // 使用 set() 更改日历字段,set(f, value) 将日历字段 f 更改为
        // value。注意：多次调用 set() 不会触发多次不必要的计算。
        int mountDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);// 说根据你传入的参数代表的意思（年、月、周等）查询当前（年、月、周）拥有的最大值。如果是年就查询这一年中的天数，如果月份就查询当前月中的天数，如果是周就查询当前周的天数
        int num = mountDay / 7;
        int yu = mountDay % 7;
        for (int i = 1; i <= yu; i++) {
            c.set(Calendar.DAY_OF_MONTH, i);
            if (2 == c.get(Calendar.DAY_OF_WEEK)) {
                num++;
                break;
            }
        }
        System.out.println(month + "月总共有" + num + "个星期一");
        return num;
    }

    /**
     * @param year  int
     * @param month int
     * @return int
     */
    public static int mondayCount(int year, int month) {
        String syear = String.valueOf(year);
        String smonth = String.valueOf(month);
        return mondayCount(syear, smonth);
    }

    public static String weekToDay(Date date) {

        return "";
    }

    /**
     * 根据日期获取该日期所处的 年、月、期
     *
     * @param date Date
     * @return String[]
     */
    public static String[] getYearMonthWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int week = cal.get(Calendar.WEEK_OF_MONTH);

        // 判断这个月的第一天是否是周一
        cal.set(Calendar.DAY_OF_MONTH, 1);
        if (2 != cal.get(Calendar.DAY_OF_WEEK)) {
            if (week == 1) {
                cal.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH) + 1;
                week = cal.get(Calendar.WEEK_OF_MONTH);
                System.out.println(cal.getTime());
                // 切换到上个月的第一天
                cal.set(Calendar.DAY_OF_MONTH, 1);
                if (2 != cal.get(Calendar.DAY_OF_WEEK)) {
                    week = week - 1;
                }
            } else {
                week = week - 1;
            }
        }

        String[] dateZone = {String.valueOf(year), String.valueOf(month),
                String.valueOf(week)};

        System.out.println(year);
        System.out.println(month);
        System.out.println(week);
        return dateZone;
    }

    /**
     * 判断指定日期是周几
     *
     * @param date Date
     * @return int
     */
    public static int getDayOfTheWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    /**
     * 获取一个时间段内的日期(暂时还没用)
     *
     * @param date       Date
     * @param beforeDays List<String>
     * @return List<String>
     */
    public static List<String> getDatePeriod(Date date, int beforeDays) {
        List<String> datePeriodList = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        for (int i = beforeDays - 1; i >= 0; i--) {
            cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - i);
            datePeriodList.add(dateFormat.format(cal.getTime()));
        }
        return datePeriodList;
    }

    /**
     * 求指定日期下个周的日期
     *
     * @param date Date
     * @return Date
     */
    public static Date getNextWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        return cal.getTime();
    }
}
