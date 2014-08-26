package tw.tpe.com.nggf.common.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.tpe.com.nggf.common.exception.NggfCommonException;

public class MoneyToChinese { 
	private static Logger logger = LoggerFactory.getLogger(MoneyToChinese.class);
	
	/**  
     * 依輸入的數字轉成國字金額 ，最多13位</br>
     * 可支援小收到第2位，會出先角分</br>
     * 可支援’－’的負數  </br>
     * @param money  
     * @return String  
	 * @throws NggfCommonException 
     * @throws NggfCommonException  
     */  
    public static String toChineseCharacter(double money) throws NggfCommonException{  
        double temp = 0;  
        long l = Math.abs((long) money);
        if(l == 0L){
        	return "零元整";
        }
        BigDecimal bil = new BigDecimal(l);  
        if (bil.toString().length() > 14) { 
        	logger.error("數字太大，計算精準度不夠!");
            throw new NggfCommonException("數字太大，計算精準度不夠!");  
        }  
        NumberFormat nf = NumberFormat.getInstance();  
        nf.setMaximumFractionDigits(2);  
        int i = 0;  
        String result = "", sign = "", tempStr = "", temp1 = "";  
        String[] arr = null;  
        sign = money < 0 ? "負" : "";  
        temp = Math.abs(money);  
        if (l == temp) {  
            result = doForEach(new BigDecimal(temp).multiply(new BigDecimal(100)).toString(),  
                    sign);  
        } else {  
            nf.setMaximumFractionDigits(2);  
            temp1 = nf.format(temp);  
            arr = temp1.split(",");  
            while (i < arr.length) {  
                tempStr += arr[i];  
                i++;  
            }  
            BigDecimal b = new BigDecimal(tempStr);  
            b = b.multiply(new BigDecimal(100));  
            tempStr=b.toString();  
            if(tempStr.indexOf(".")==tempStr.length()-3){  
                result = doForEach(tempStr.substring(0,  
                    tempStr.length() - 3), sign);  
            }else{  
                result = doForEach(tempStr.substring(0,  
                        tempStr.length() - 3)+"0", sign);  
            }  
        }  
        return result;  
    }  
  
    /**  
     * 為數字配上相對位置的單位  
     * @param result  
     * @param sign 正數為"",負數為"負" 
     * @return  
     */  
    private static String doForEach(String result, String sign) {  
        String flag = "", b_string = "";  
        String[] arr = { "分", "角", "元", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "億",  
                "拾", "佰", "仟", "萬", "拾" };  
        String[] arr1 = { "壹", "貳", "叁", "肆", "伍", "陸", "柒", "捌", "玖" };  
        boolean zero = true;  
        int len = 0, i = 0, z_count = 0;  
        if (result == null) {  
            len = 0;  
        } else {  
            len = result.length();  
        }  
        while (i < len) {  
            flag = result.substring(i, i + 1);  
            i++;  
            if (flag.equals("0")) {  
                if (len - i == 10 || len - i == 6 || len - i == 2 || len == i) {  
                    if (zero) {  
                        b_string = b_string.substring(0,  
                                (b_string.length()) - 1);  
                        zero = false;  
                    }
                    //ivan edit
                    boolean hasWun = false;
                    if(len > 6){
                    	String temp = result.substring(0,len-6);
                    	if(temp.length() > 4){
                    		temp = temp.substring(temp.length()-4,temp.length());
                    	}
                    	String checkTmp = null;
                    	int count =1;
                    	for(int x=0;x<temp.length();x++){
                    		checkTmp = temp.substring(x,count);
                    		if(!"0".equals(checkTmp)){
                    			hasWun = true;
                    		}
                    		count++;
                    	}
                    }
                    
                    if (len - i == 10) {  
                        b_string = b_string + "億";  
                    }
                    if (hasWun == true && len - i == 6) {  
                        b_string = b_string + "萬";  
                    }  
                    if (len - i == 2) {  
                        b_string = b_string + "元";  
                    }  
                    if (len == i) {  
                        b_string = b_string + "整";  
                    }  
                    z_count = 0;  
                } else {  
                    if (z_count == 0) {  
                        b_string = b_string + "零";  
                        zero = true;  
                    }  
                    z_count = z_count + 1;  
                }  
            } else {  
                b_string = b_string + arr1[Integer.parseInt(flag) - 1]  
                        + arr[len - i];  
                z_count = 0;  
                zero = false;  
            }  
        }  
        b_string = sign + b_string;  
        return b_string;  
    }  
}  
