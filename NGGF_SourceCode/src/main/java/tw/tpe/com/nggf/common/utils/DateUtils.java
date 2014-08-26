package tw.tpe.com.nggf.common.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.tpe.com.nggf.common.exception.NggfCommonException;

public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	/**
	 * Date轉換成民國日期-
	 * @param date
	 * @param format　 ex: "/"，"－"；若為null，預設(有帶年月日)
	 * @return String 預設ex:101年1月18日
	 */
	public static String formatRocDate(Date date,String format){
		String rocDate = null;
		if(date != null){
			DateFormat dateFormat = null;
			String formateAd = null;
			if(format == null){
				formateAd = "yyyy年MM月dd日";
				dateFormat = new SimpleDateFormat(formateAd);
				String temp = dateFormat.format(date);
				String tempYear = temp.substring(0,4);
				String tempMdd = temp.substring(4,temp.length());
				int rocYear = Integer.valueOf(tempYear)-1911;
				rocDate = rocYear + tempMdd;
			}else{
				formateAd = "yyyy" + format + "MM" + format + "dd";
				dateFormat = new SimpleDateFormat(formateAd);
				String temp = dateFormat.format(date);
				String[] ss = temp.split(format);
				int rocYear = Integer.valueOf(ss[0])-1911;
				logger.debug("year = {}",rocYear);
				logger.debug("month = {}",ss[1]);
				logger.debug("day = {}",ss[2]);
				StringBuffer sb = new StringBuffer();
				rocDate = sb.append(String.valueOf(rocYear)).append(format).append(ss[1]).append(format).append(ss[2]).toString();
			}
		}
        return rocDate;
	}
	
	/**
	 * 取得系統日期
	 * @return 系統日期文字，yyyyMMdd，ex:20120628
	 */
	public static String getSysDate() {
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	/**
	 * 根據pattern取得格式化日期字串
	 *
	 * @param d the d
	 * @param pattern the pattern
	 * @return the string
	 */
	public static String formatDate(Date d, String pattern) {
        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateTimeInstance();
        df.setTimeZone(TimeZone.getDefault());
        df.applyPattern(pattern);
        return df.format(d);
    }
	
	/**
	 * 輸入字串日期格式，取得Date的日期格式
	 * @param strDate
	 * @param inputPattern "yyyyMMdd"
	 * @return date
	 * @throws ParseException
	 */
	public static Date transDate(String strDate, String inputPattern) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat(inputPattern);
		
		if( StringUtils.isBlank(strDate) || StringUtils.isBlank(inputPattern) ){
			return null;
		}
			Date date = sdf.parse(strDate);
			return date;
	}
	
    /**
     * 輸入字串日期格式，取得Date的日期格式，預設使用yyyyMMdd
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date transDate(String strDate) throws ParseException {
        return transDate(strDate, "yyyyMMdd");
    }
	
	/**
	 *  輸入兩個字串類型的日期，格式為yyyyMMdd，回傳兩日期相減的天數(int)</br>
	 *	回傳正數:　A日期大於B日期幾天</br>
	 *  回傳負數:　A日期小於B日期幾天</br>
	 *  回傳0:　　A日期等於B日期</br>
	 * @param strDateA
	 * @param strDateB
	 * @return 回傳兩日期相減的天數(int)
	 * @throws NggfCommonException
	 */
	public static int calculateDate(String strDateA, String strDateB) throws NggfCommonException{
		SimpleDateFormat inputPattern = new SimpleDateFormat("yyyyMMdd");
		
		//把String 轉成 日期的格式
		Date dateA = null;
		Date dateB = null;
		
		try{
			dateA =inputPattern.parse(strDateA);
			dateB = inputPattern.parse(strDateB);
		}catch(ParseException e){
			logger.debug("轉換日期錯誤");
			throw new NggfCommonException("TODO","日期轉換過程錯誤");
		}
		
		long resultTime = dateA.getTime() - dateB.getTime();
		int result = (int) (resultTime/1000/60/60/24);
		
		logger.debug("calculate result = {}",result);
		
		return result;
	}
	
	
	/**
	 * 調整日期的method，依傳入的年、月、日和要調整的數目。</br>
	 * <範例></br>
	 * 傳入:20120103</br>
	 * 方法:adjustDate(20120103, year, 1)</br>
	 * 回傳:20130103</br>
	 * @param strDateA    傳入一個要調整的不含斜線string日期 yyyyMMdd
	 * @param adjustType  只能輸入三種   year、month、day
	 * @param adjustNo    要調整的數字，輸入正數為加、輸入負數為減
	 * @return String     回傳一個string不含斜線的日期 yyyyMMdd
	 * @throws NggfCommonException
	 */
	public static String adjustDate(String strDateA, String adjustType,int adjustNo) throws NggfCommonException{
		SimpleDateFormat inputPattern = new SimpleDateFormat("yyyyMMdd");
		
		//String轉成日期的格式
		Date DateA = null;
		try{
			DateA = inputPattern.parse(strDateA);
		}catch(ParseException e){
			String errMsg = (new StringBuilder()).append("日期轉換過程錯誤! strDateA=")
				.append(strDateA).append(",adjustType=").append(adjustType).append(",adjustNo=").append(adjustNo).toString();
			logger.debug(errMsg);
			throw new NggfCommonException("TODO",errMsg);
		}
		
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTime(DateA);
		
		if(adjustType.equals("year")){
			calDateA.add(Calendar.YEAR,adjustNo);
			logger.debug("adjust year = {}",adjustNo);
		}else if(adjustType.equals("month")){
			calDateA.add(Calendar.MONTH,adjustNo);
			logger.debug("adjust month = {}",adjustNo);
		}else if(adjustType.equals("day")){
			calDateA.add(Calendar.DAY_OF_MONTH,adjustNo);
			logger.debug("adjust day = {}",adjustNo);
		}else{
			logger.debug("輸入的格式不符，回傳null");
			return null;
		}
		
		//設定輸入的格式為yyyyMMdd
		String result = inputPattern.format(calDateA.getTime());
		logger.debug("result = {}",result);
		return result;
	}
	
	/**
	 * 輸入兩個字串日期yyyyMMdd(String)，轉成日期後比較大小
	 * @param strDateA  格式為yyyyMMdd
	 * @param strDateB  格式為yyyyMMdd
	 * @return
	 * 如果A大於B回傳 1</br>
	 * 如果A等於B回傳 0</br>
	 * 如果A小於B回傳 -1
	 */
	public static int compareDate(String strDateA, String strDateB) throws NggfCommonException{
		
		//傳入的是空字串的或是null
		if (StringUtils.isBlank(strDateA) && StringUtils.isBlank(strDateB)) {
			return 0;
		} else if (StringUtils.isBlank(strDateA) && StringUtils.isNotBlank(strDateB)) {
			return -1;
		} else if (StringUtils.isNotBlank(strDateA) && StringUtils.isBlank(strDateB)) {
			return 1;
		}
		
		//傳入的不是日期格式
		if (!checkDateStrStyle(strDateA) || !checkDateStrStyle(strDateB)) {
			//使用String 的compareTo method
			int i = strDateA.compareTo(strDateB);
			return i > 0 ? 1 : (i < 0 ? -1 : 0);
		}
		
			
		SimpleDateFormat inputPattern = new SimpleDateFormat("yyyyMMdd");
			
		//String轉成日期的格式
		Date DateA = null;
		Date DateB = null;
		try{
			DateA = inputPattern.parse(strDateA);
			DateB = inputPattern.parse(strDateB);
		}catch(ParseException e){
			logger.debug("轉換日期錯誤");
			throw new NggfCommonException("1001","日期轉換過程錯誤");
		}catch(NullPointerException e1){
			logger.debug("輸入日期是空的");
			throw new NggfCommonException("1001","輸入日期格式錯誤");
		}
			
		int result = 0;
			
		try{
			result = DateA.compareTo(DateB);
		} catch(Exception e){
			logger.debug("未傳入正確的日期或日期為null");
			throw new NggfCommonException("1001", "未輸入正確的日期格式，不能比較", e);
		}
			
		return result;
	}

	/**
	 * 西元年轉換民國年度<br>
	 * 若傳入 Null，則以當下日期轉換
	 * @param date 待轉換日期
	 * @return 民國年度字串
	 */
	public static String formatRocYear(Date date) {
		if (null == date)
			date = new Date();

		String result = null;
		String year =  new SimpleDateFormat("yyyy").format(date);
		NumberFormat nf = new DecimalFormat("000");

		try {
			result = nf.format((Long) nf.parse(year) - 1911);
		} catch (ParseException e) {
			// 
			logger.error("西元年轉換民國年度異常", e);
		}

		return result;
	}
	
	/**
	 * 將yyyyMMdd轉為格式yyyy/MM/dd<br>
	 * 若傳入 Null或格式不符,則不轉換
	 * @param yyyyMMdd字串
	 * @return yyyy/MM/dd字串
	 */
	public static String formatStrDate(String strDate) {
		if ( null != strDate && strDate.matches("\\d{8}") ){  //format: 20120101  -> 2012/01/01			
			return String.format(
					"%s/%s/%s", strDate.substring(0, 4), strDate.substring(4, 6), strDate.substring(6));
		} else {
			return strDate;
		}
	}
	
	/**
     * 將yyyy/MM/dd轉為格式yyyyMMdd<br>
     * 若傳入 Null或格式不符,則不轉換
     * @param yyyy/MM/dd字串
     * @return yyyyMMdd字串
     */
    public static String formatStrDateWOSlash(String strDate) {
        if ( null != strDate && strDate.matches("\\d{4}/\\d{2}/\\d{2}") ){  //format: 2012/01/01  -> 20120101         
            return strDate.replaceAll("/", "");
        } else {
            return strDate;
        }
    }
    
    /**
     * 將yyyy/MM/dd轉為格式yyyyMMdd<br>
     * 將yyyy/MM轉為格式yyyyMM
     * 若傳入 Null或格式不符,則不轉換
     * @param yyyy/MM/dd字串
     * @return yyyyMMdd字串
     */
    public static String formatStrDateRangeWOSlash(String strDate) {
        if ( null != strDate && strDate.matches("\\d{4}/\\d{2}/\\d{2}") ){  //format: 2012/01/01  -> 20120101         
            return strDate.replaceAll("/", "");
        } else if(null != strDate && strDate.matches("\\d{4}/\\d{2}")) {
        	return strDate.replaceAll("/", "");
        } else{
            return strDate;
        }
    }
    
	
    /**
     * 以出生日取得現在年齡
     * 
     * @param birthdate String格式之出生日
     * @return the age by birthdate
     * @throws ParseException 
     */
    public static int getAgeByBirthDate(String birthdate) throws ParseException {
        return getAgeByBirthdate(transDate(birthdate));
    }

    /**
     * Gets the age by birthdate.
     * 
     * @param birthdate Date格式之出生日
     * @return the age by birthdate
     */
    public static int getAgeByBirthdate(Date birthdate) {
        return getAgeByBirthdate(birthdate, new Date());
    }

    /**
     * 以出生日取得現在年齡
     * 
     * @param birthdate Date格式之出生日
     * @param curdate 系統日
     * @return the age by birthdate
     */
    public static int getAgeByBirthdate(Date birthdate, Date curdate) {
        Calendar calBirth = new GregorianCalendar();
        Calendar calCur = new GregorianCalendar();
        calBirth.setTime(birthdate);
        calCur.setTime(curdate);
        int age = calCur.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR);
        if (calBirth.get(Calendar.DAY_OF_YEAR) > calCur
                .get(Calendar.DAY_OF_YEAR)) {
            age = age - 1;
        }
        return age;
    }
 
    /**
     * 比較該生日是否滿足之特定年齡
     * 
     * @param birthdate String格式之出生日，格式為yyyyMMdd
     * @param age 欲比較之年齡
     * @return 	true:滿足該年齡</br>
     * 			false:未滿該年齡</br>
     * @throws NggfCommonException 
     */
    public static boolean reachAge(String birthdate, int age) throws NggfCommonException {
    	String sysDate = getSysDate();
    	String cmpDate = DateUtils.adjustDate(birthdate, "year", age);
    	return (calculateDate(sysDate, cmpDate)>=0)?true:false;
    }
	
    /**
     * 比較二出生日之差是否超過之特定年齡
     * 
     * @param birthdate1 String格式之出生日，格式為yyyyMMdd
     * @param birthdate2 String格式之出生日，格式為yyyyMMdd
     * @param age 欲比較之年齡
     * @return 	true:超過該年齡 => birthdate1-birthdate2>age</br>
     * 			false:未超過該年齡 => birthdate1-birthdate2<=age</br>
     * @throws NggfCommonException 
     * @throws ParseException 
     */
    public static boolean exceedAge(String birthdate1, String birthdate2, int age) throws NggfCommonException{
    	String cmpDate = DateUtils.adjustDate(birthdate2, "year", age);
    	return (calculateDate(birthdate1, cmpDate)>0)?true:false;
    }
    
    /**
     * 檢查八個字的字串是否符合日期格式<br/>
     * ex:'20000229':true,'20010229':false,'20000251':false<br/>
     * '2000/02/29':false,'2012022':false
     * @param dateStr
     * @return boolean
     * @throws NggfCommonException 
     * @throws ParseException
     */
    public static boolean checkDateStrStyle(String dateStr) throws NggfCommonException{
    	if(StringUtils.isBlank(dateStr)){
    		return false;
    	}
    	dateStr = dateStr.trim();
    	if(!dateStr.matches("\\d{8}")){
    		return false;
    	}else{
    		Date checkDate = null;
    		try {
    			checkDate = transDate(dateStr);
    		} catch (ParseException e) {
    			e.printStackTrace();
    			logger.error("DateUtils.checkDateStrStyle parse fail");
    			throw new NggfCommonException("E0002","DateUtils.checkDateStrStyle parse fail",e);
    		}
    		String checkDateStr = DateUtils.formatDate(checkDate, "yyyyMMdd");
    		if(checkDateStr.equals(dateStr)){
    			return true;
    		}
    		return false;
    	}
    }
    
    /**
     * 輸入年度及月份，回傳該月份有幾天
     * @param year (西元年)
     * @param month (1~12月)
     * @return date (天數)
     */
    public static Integer queryDateByYearAndMonth(Integer year, Integer month) {
    	
    	Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        
        return maxDate; 
    }
}
