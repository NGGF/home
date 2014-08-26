package tw.tpe.com.nggf.common.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tw.tpe.com.nggf.common.model.SqlSessionDao;
import tw.tpe.com.nggf.common.model.dataobject.EncodedcontrolDo;
import tw.tpe.com.nggf.common.service.EncodedcontrolService;

@Service("encodedcontrolService")
public class EncodedcontrolServiceImpl implements EncodedcontrolService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("sqlSessionDao")
	SqlSessionDao sqlSessionDao;

	/**
	 * 依號碼類別查詢本年度產生的最新編號
	 * @param encodeType
	 * @return 該類別最新產生的編號
	 */
	public String queryCurrentNo(String encodeType) {
		if(StringUtils.isBlank(encodeType)){
			logger.error("property is empty");
			throw new IllegalArgumentException("property is empty");
		}
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		String recordYear = String.valueOf(year);
		EncodedcontrolDo encodedcontrolDo = queryEncodedcontrolByTypeAndYear(encodeType, recordYear);
		String result = null;
		if(encodedcontrolDo != null){
			int tempNo = encodedcontrolDo.getCurrentNo() + 1;
			result = encodedcontrolDo.getEncodeType() + encodedcontrolDo.getRecordYear() + getPlusZeroNo(tempNo);
			encodedcontrolDo.setCurrentNo(tempNo);
			boolean updateSuccess = updateEncodedcontrol(encodedcontrolDo);
			logger.info("updateEncodedcontrol = {}",updateSuccess);
		}else{
			//如果本年度都還沒有,則新增一筆從0001開始
			EncodedcontrolDo insertDo = new EncodedcontrolDo();
			insertDo.setEncodeType(encodeType);
			insertDo.setRecordYear(recordYear);
			insertDo.setCurrentNo(1);
			boolean insertSuccess = insertEncodedcontrol(insertDo);
			logger.info("insertEncodedcontrol = {}",insertSuccess);
			result = encodeType + recordYear + getPlusZeroNo(1);
		}
		return result;
	}
	
	/**
	 * 依類別及年份查詢號碼控制檔
	 * @param encodeType
	 * @param recordYear
	 * @return EncodedcontrolDo
	 */
	private EncodedcontrolDo queryEncodedcontrolByTypeAndYear(String encodeType,String recordYear){
		logger.info("encodeType = {}",encodeType);
		logger.info("recordYear = {}",recordYear);
		if(StringUtils.isBlank(encodeType) || StringUtils.isBlank(recordYear)){
			logger.error("property is empty");
			throw new IllegalArgumentException("property is empty");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("encodeType", encodeType);
		map.put("recordYear", recordYear);
		return sqlSessionDao.find(EncodedcontrolDo.class, "encodedcontrol.selectByTypeAndYear",map);
	}
	
	/**
	 * 新增號碼控制檔
	 * @param encodedcontrolDo
	 * @return boolean
	 */
	private boolean insertEncodedcontrol(EncodedcontrolDo encodedcontrolDo){
		if(encodedcontrolDo == null){
			logger.error("property is empty");
			throw new IllegalArgumentException("property is empty");
		}
		boolean insertSuccess = false;
		int result = sqlSessionDao.insert("encodedcontrol.insert", encodedcontrolDo);
		if(result > 0){
			insertSuccess = true;
		}
		return insertSuccess;
	}
	
	/**
	 * 更新號碼控制檔
	 * @param encodedcontrolDo
	 * @return boolean
	 */
	private boolean updateEncodedcontrol(EncodedcontrolDo encodedcontrolDo){
		if(encodedcontrolDo == null){
			logger.error("property is empty");
			throw new IllegalArgumentException("property is empty");
		}
		boolean updateSuccess = false;
		int result = sqlSessionDao.update("encodedcontrol.updateByPrimaryKey", encodedcontrolDo);
		if(result > 0){
			updateSuccess = true;
		}
		return updateSuccess;
	}
	
	private String getPlusZeroNo(Integer number){
		if(number == null){
			logger.error("getPlusZeroNo property is empty");
			throw new IllegalArgumentException("getPlusZeroNo property is empty");
		}
		String tempNoStr = String.valueOf(number);
		int checkSize = tempNoStr.length();
		int addZeroSize = 4 - checkSize;
		if(addZeroSize <= 0){
			return tempNoStr;
		}else{
			for(int x = 0;x<addZeroSize;x++){
				tempNoStr = "0" + tempNoStr;
			}
		}
		return tempNoStr;
	}

}
