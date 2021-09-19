package com.datealive.utils;

import com.alibaba.druid.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期常用格式化方法帮助类
 * @author azhou
 */
public class DateUtil {


    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM = "yyyy-MM";
    public static final String yyyy_MM_2 = "yyyy年MM月";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMM = "yyyyMM";
    public static final String yyMM = "yyMM";
    public static final String yyyy = "yyyy";
    public static final String yy = "yy";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HH_mm = "HH:mm";
    public static final String MM = "MM";

    /**
     * <p> @Title: getCurrentStringStr</p>
     * <p> @Description: 根据传入格式化模式，进行当前时间格式化返回</p>
     * <p> @Param: 例：yyyy-MM-dd HH:mm:ss</p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String getCurrentStringStr(String formatStr) {
        return formatDate(new Date(), formatStr);
    }

    /**
     * <p> @Title: defaultFormatDate</p>
     * <p> @Description: 返回当前时间的默认格式化字符串</p>
     * <p> @Param: yyyy-MM-dd HH:mm:ss</p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String defaultFormatDate() {
        return formatDate(new Date(), yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * <p> @Title: formatDate</p>
     * <p> @Description: 根据传入的Date和格式化模式，进行格式化返回</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * <p> @Title: parseDate</p>
     * <p> @Description: 根据传入的时间或者日期字符串，和传入的格式化模式，返回Date对象</p>
     * <p> @Param: </p>
     * <p> @Return: Date</p>
     * <p> @Author Administrator</p>
     */
    public static Date parseDate(String dateStr, String formatStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.parse(dateStr);
    }

    /**
     * <p> @Title: getDaysOfMonth</p>
     * <p> @Description: 获取某个月的天数</p>
     * <p> @Param: </p>
     * <p> @Return: int</p>
     * <p> @Author Administrator</p>
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * <p> @Title: addDay</p>
     * <p> @Description: 给指定日期加入天数</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String addDay(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }

    /**
     * <p> @Title: getMonthSpace</p>
     * <p> @Description: 获取指定两个日期相隔月份</p>
     * <p> @Param: </p>
     * <p> @Return: int</p>
     * <p> @Author Administrator</p>
     */
    public static int getMonthSpace(String date1, String date2) throws ParseException {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        result = (year * 12 + c2.get(Calendar.MONTH)) - c1.get(Calendar.MONTH);
        return result == 0 ? 0 : Math.abs(result);

    }

    /**
     * <p> @Title: getDateSpace</p>
     * <p> @Description: 取指定两个日期相隔天数</p>
     * <p> @Param:startTime:开始时间,endTime:结束时间, isContainCritical:是否包临界点（1:包含开始时间,其他则不包含）</p>
     * <p> @Return: int</p>
     * <p> @Author Administrator</p>
     */
    public static int getDateSpace(String startTime, String endTime, String isContainCritical) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(sdf.parse(startTime));
        caled.setTime(sdf.parse(endTime));
        // 设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        // 得到两个日期相差的天数
        int days = (int) (((long) (caled.getTime().getTime() / 1000) - (long) (calst.getTime().getTime() / 1000)) / 3600 / 24);
        if ("1".equals(isContainCritical)) {// 包含开始时间
            days = days + 1;
        }
        return days;
    }
    
    /**
     * <p> @Title: subMonth</p>
     * <p> @Description: 给某个日期加月份</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String subMonth(String date, int monthNum) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, monthNum);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }



    /**
     * <p> @Title: getYear</p>
     * <p> @Description: 获取年</p>
     * <p> @Param: </p>
     * <p> @Return: int</p>
     * <p> @Author Administrator</p>
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * <p> @Title: getYear</p>
     * <p> @Description: 获取月</p>
     * <p> @Param: </p>
     * <p> @Return: int</p>
     * <p> @Author Administrator</p>
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * <p> @Title: getYear</p>
     * <p> @Description: 获取日</p>
     * <p> @Param: </p>
     * <p> @Return: int</p>
     * <p> @Author Administrator</p>
     */
    public static int getDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    /**
     * <p> @Title: getLastMonth</p>
     * <p> @Description: 获取当前年月的上一个月</p>
     * <p> @Param: </p>
     * <p> @Return: String 例： 2019-07 </p>
     * <p> @Author Administrator</p>
     */
    public static String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        return format.format(date);
    }

    /**
     * <p> @Title: getLastMonth</p>
     * <p> @Description: 获取当前年月</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String getLastMonth(Date time) {
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)); // 设置为上一个月
        time = calendar.getTime();
        return format.format(time);
    }

    /**
     * @Author: Administrator
     * @Description: 获取当前年月的上两个月
     * @Param:
     * @return: java.lang.String
     **/
    public static String getLastTwoMonth() {
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上两个月
        date = calendar.getTime();
        return format.format(date);
    }

    /**
     * @Author: Administrator
     * @Description: 获取当前年月的前三个月
     * @Param:
     * @return: java.lang.String
     **/
    public static String getLastThreeMonth() {
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2); // 设置为上三个月
        date = calendar.getTime();
        return format.format(date);
    }

    /**
     * @Author: Administrator
     * @Description: 获取当前年月日的时间戳
     * @Param:
     * @return: java.lang.String
     **/
    public static String getFormatDate() {
        return formatDate(new Date(), yyyyMMdd);
    }

    /**
     * @Author: Administrator
     * @Description: 获取当前时间的时间戳
     * @Param:
     * @return: java.lang.String
     **/
    public static String getCurrentTime() {
        return formatDate(new Date(), yyyyMMddHHmmss);
    }
    
    /**
     * <p> @Title: getFormat</p>
     * <p> @Description: 获取当前年月的时间戳</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String getFormat() {
        return formatDate(new Date(), yyyyMM);
    }

    /**
     * <p> @Title: getFebruaryDay</p>
     * <p> @Description: 获取二月的天数</p>
     * <p> @param dateStr:传入的日期
     * <p> @return
     * <p> @throws ParseException</p>
     * <p> @Return: int</p>
     * <p> @Author Administrator</p>
     */
    public static int getFebruaryDay(String dateStr) throws ParseException {
        Date date = DateUtil.parseDate(dateStr, DateUtil.yyyy_MM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        String februaryStr = calendar.get(Calendar.YEAR) + "-02";
        return DateUtil.getDaysOfMonth(DateUtil.parseDate(februaryStr, DateUtil.yyyy_MM));
    }

    /**
     * <p> @Title: toChinese</p>
     * <p> @Description: 将数字转换为中文数字</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    private static String toChinese(Integer str) {
        String[] s2 = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        if (str == -1) {
            return s2[11];
        }
        return s2[str];
    }

    /**
     * @Author: Administrator
     * @Description: 根据传入的时间进行比较
     * @Param:
     * @return: boolean
     **/
    public static boolean after(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return c2.after(c1);
    }


    /**
     * <p> @Title:  获取某年某月的天数</p>
     * <p> @Description:  </p>
     * <p> @param    * @param year年  month月</p>
     * <p> @return  {@link null}</p>
     * <p> @Author Administrator </p>
     * <p> @Time 2019/12/31 14:10</p>
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * <p> @Title:  numberToChinese</p>
     * <p> @Description:  数字转汉字  2位正整数 返回汉字</p>
     * <p> @param    * @param numb 正整数</p>
     * <p> @return  {@link null}</p>
     * <p> @Author Administrator </p>
     */
    public static String numberToChinese(Integer numb) throws Exception {
        String[] s1 = {"站位", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        if (numb <= 0) {
            throw new Exception("数字不是正整数");
        }
        if (numb < 100) {
            if (numb <= 10) { //一位数
                return s1[numb];
            }
            if (numb > 10) {//2位数
                String s2 = "";
                if (numb % 100 / 10 == 1) {
                    s2 = s1[10];
                } else {
                    s2 = s1[numb % 100 / 10] + s1[10];
                }
                String s3 = "";
                if (numb % 10 == 0) {
                    s3 = "";
                } else {
                    s3 = s1[numb % 10];
                }
                return s2 + s3;
            }
        }
        throw new Exception("维护方法，现支持二位数字");
    }
    /**
     * 
     * <p> @Title: getMonthChinese</p>
     * <p> @Description: 获取前几个月的年月</p>
     * <p> @Param: frontMonth  当前月以前为负数 </p>
     * <p> @Return: List<String></p>
     * <p> @Author Administrator</p>
     * eg： 要返回前半年的年月: time = new Date(); frontMonth = -4
     */
    public static List<String> getMonthChinese(String time,int frontMonth) throws ParseException {
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
    	if (StringUtils.isEmpty(time)) {
    		c.setTime(new Date());
		}else {
			Date date =sdf.parse(time);
	    	c.setTime(date);
		}
        c.add(Calendar.MONTH, frontMonth);
        String before_six = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH);
        ArrayList<String> result = new ArrayList<String>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(sdf.parse(before_six));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        if(StringUtils.isEmpty(time)) {
        	max.setTime(new Date());
        }else {
        	max.setTime(sdf.parse(time));
		}
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        while (curr.before(max)) {
            curr.add(Calendar.MONTH, 1);
            if(curr.get(Calendar.MONTH) == 0) {
            	result.add(curr.get(Calendar.YEAR)-1+"-12");
            }else {
            	result.add(curr.get(Calendar.YEAR)+"-"+(curr.get(Calendar.MONTH)>9 ? curr.get(Calendar.MONTH) : "0"+curr.get(Calendar.MONTH)));
			}
        }
        return result;
    }
    
    /**
     * 
     * <p> @Title: getYesterday</p>
     * <p> @Description: 获取昨日时间</p>
     * <p> @Param: </p>
     * <p> @Return: Map<String,String></p>
     * <p> @Author Administrator</p>
     */
    public static Map<String,String> getYesterday(){
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Map<String,String> condition=new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        condition.put("endDate",df.format(calendar.getTime())+" 23:59:59");
        calendar.set(Calendar.HOUR_OF_DAY,0);
        condition.put("startDate",df.format(calendar.getTime())+" 00:00:00");
        return condition;
    }
    
    /**
     * 
     * <p> @Title: getDaySevenRange</p>
     * <p> @Description: 获得近一周的开始时间和结束时间（一周按7天计算）</p>
     * <p> @Param: </p>
     * <p> @Return: Map<String,String></p>
     * <p> @Author Administrator</p>
     */
    public static Map<String,String> getDaySevenRange(){
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Map<String,String> condition=new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime())+" 23:59:59");
        calendar.set(Calendar.HOUR_OF_DAY,-144);
        condition.put("startDate",df.format(calendar.getTime())+" 00:00:00");
        return condition;
    }

    /**
     * 获得近一月的开始时间和结束时间（一个月按30天计算）
     * @return
     */
    public static Map<String,String> getDayTRange(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Map<String,String> condition=new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime())+" 23:59:59");
        calendar.set(Calendar.HOUR_OF_DAY,-696);
        condition.put("startDate",df.format(calendar.getTime())+" 00:00:00");
        return condition;
    }

    /**
     * 获得近一年的开始时间和结束时间（一年按365天计算）
     * @return
     */
    public static Map<String,String> getYearTRange(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Map<String,String> condition=new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime())+" 23:59:59");
        calendar.set(Calendar.HOUR_OF_DAY,-8736);
        condition.put("startDate",df.format(calendar.getTime())+" 00:00:00");
        return condition;
    }
    
    public static String addMonth(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.MONTH, num);// num为增加的月份数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }
    /**
     * 
     * <p> @Title: addHours</p>
     * <p> @Description: 增加几个分钟</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String addMinute(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
        Date currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.MINUTE, num);// num为增加的小时数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }
    
    public static String addMinute1(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.MINUTE, num);// num为增加的小时数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }
    
    /**
     * 
     * <p> @Title: addHours</p>
     * <p> @Description: 增加小时</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String addHours(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.HOUR, num);// num为增加的小时数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }



    /**
      * <p> @Title:  getWeekOfDate</p>
      * <p> @Description:  获取当天是星期几</p>
      * <p> @param    * @param null</p>
      * <p> @return  {@link null}</p>
      * <p> @Author Administrator </p>
      */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    /**
      * <p> @Title: getBetweenDays </p>
      * <p> @Description:获取两个日期之间的所有日期(字符串格式, 按天计算)  </p>
      * <p> @param    * @param null</p>
      * <p> @return  {@link null}</p>
      * <p> @Author Administrator </p>
      */
    public static List<Date> getBetweenDays(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.DAY_OF_MONTH, 1);
        result.add(start);

        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
    
    /**
     * 
     * <p> @Title: getDayBetween</p>
     * <p> @Description: 获取两个日期之间的所有日期，上面方法不太适合我的使用场景</p>
     * <p> @Param: </p>
     * <p> @Return: List<String></p>
     * <p> @Author Administrator</p>
     */
    public static List<String> getDayBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(parseDate(minDate, yyyy_MM_dd));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH),min.get(Calendar.DATE));

        max.setTime(parseDate(maxDate, yyyy_MM_dd));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH),max.get(Calendar.DATE));
      
        Calendar curr = min;
        while (curr.getTimeInMillis() <= max.getTimeInMillis()) {
         result.add(formatDate(curr.getTime(), yyyy_MM_dd));
         curr.add(Calendar.DATE, 1);
        }

        return result;
      }
    
    /**
     * 
     * <p> @Title: getMonthBetween</p>
     * <p> @Description: 获取两个日期之间的所有月份</p>
     * <p> @Param: </p>
     * <p> @Return: List<String></p>
     * <p> @Author Administrator</p>
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(parseDate(minDate, yyyy_MM));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(parseDate(maxDate, yyyy_MM));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
      
        Calendar curr = min;
        while (curr.before(max)) {
         result.add(formatDate(curr.getTime(), yyyy_MM));
         curr.add(Calendar.MONTH, 1);
        }

        return result;
      }
    
    /**
     * 
     * <p> @Title: getYearBetween</p>
     * <p> @Description: 获取两个日期之间的所有年份</p>
     * <p> @Param: </p>
     * <p> @Return: List<String></p>
     * <p> @Author Administrator</p>
     */
    public static List<String> getYearBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(parseDate(minDate, yyyy));
	    max.setTime(parseDate(maxDate, yyyy));
      
        Calendar curr = min;
        while (curr.getTimeInMillis() <= max.getTimeInMillis()) {
            result.add(formatDate(curr.getTime(), yyyy));
            curr.add(Calendar.YEAR, 1);
           }

        return result;
      }


    

    
    /**
     * 
     * <p> @Title: beforeMonthLastDay</p>
     * <p> @Description: 获取上月最后一天</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String beforeMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd);
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * <p> @Title: beforeMonthFirstDay</p>
     * <p> @Description: 获取上月第一天</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String beforeMonthFirstDay() {
    	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd);
		return format.format(calendar.getTime());
    }
    
    /**
     * <p> @Title: getSixOfMonthTRange</p>
     * <p> @Description: 获得近六月的开始时间和结束时间（一年按183天计算）</p>
     * <p> @return Map<String,String></p>
     * <p> @Author Administrator</p>
     */
    public static Map<String,String> getSixOfMonthTRange(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Map<String,String> condition=new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime())+" 23:59:59");
        calendar.set(Calendar.HOUR_OF_DAY,-4368);
        condition.put("startDate",df.format(calendar.getTime())+" 00:00:00");
        return condition;
    }
    
    /**
     * 
     * <p> @Title: addYear</p>
     * <p> @Description: 添加年</p>
     * <p> @Param: </p>
     * <p> @Return: String</p>
     * <p> @Author Administrator</p>
     */
    public static String addYear(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.YEAR, num);// num为增加的月份数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }

}
