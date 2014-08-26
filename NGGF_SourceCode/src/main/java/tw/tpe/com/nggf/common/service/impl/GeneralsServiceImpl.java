package tw.tpe.com.nggf.common.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tw.tpe.com.nggf.common.model.SqlSessionDao;
import tw.tpe.com.nggf.common.model.dataobject.GeneralsDo;
import tw.tpe.com.nggf.common.service.EncodedcontrolService;
import tw.tpe.com.nggf.common.service.GeneralsService;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

@Service("generalsService")
public class GeneralsServiceImpl implements GeneralsService {
Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("sqlSessionDao")
	SqlSessionDao sqlSessionDao;
	
	@Autowired
	@Qualifier("encodedcontrolService")
	EncodedcontrolService encodedcontrolService;
	
	/**
	 * 神將新增
	 * @param generalsDo
	 * @param generalsInfo
	 * @return boolean
	 */
	public boolean insertGenerals(GeneralsDo generalsDo,UserContextVo userInfo) {
		if(generalsDo == null){
			logger.debug("GeneralsDo is null");
			throw new IllegalArgumentException("GeneralsDo is null");
		}
		boolean insertSuccess = false;
		
		//塞入異動欄位
		generalsDo.setGeneralsId(encodedcontrolService.queryCurrentNo("G"));
		generalsDo.setCreator(userInfo.getUserId());
		generalsDo.setCreateDate(new Date());
		generalsDo.setCreatorIp(userInfo.getIp());
		generalsDo.setMaintainer(userInfo.getUserId());
		generalsDo.setMaintainDate(new Date());
		generalsDo.setMaintainIp(userInfo.getIp());
		
		int insertResult = sqlSessionDao.insert("generals.insertSelective",generalsDo);
		if(insertResult > 0){
			insertSuccess = true;
		}
		return insertSuccess;
	}
	
	/**
	 * 更新神將資訊
	 * @param generalsDo
	 * @param userInfo
	 * @return boolean
	 */
	public boolean updateGenerals(GeneralsDo generalsDo,UserContextVo userInfo){
		if(generalsDo == null){
			logger.debug("generalsDo is null");
			throw new IllegalArgumentException("generalsDo is null");
		}
		if(StringUtils.isBlank(generalsDo.getGeneralsId())){
			logger.debug("GeneralsId is null");
			throw new IllegalArgumentException("GeneralsId is null");
		}
		boolean updateSuccess = false;
		
		//塞入異動欄位
		generalsDo.setMaintainer(userInfo.getUserId());
		generalsDo.setMaintainDate(new Date());
		generalsDo.setMaintainIp(userInfo.getIp());
		
		int updateResult = sqlSessionDao.insert("generals.updateByPrimaryKeySelective",generalsDo);
		if(updateResult > 0){
			updateSuccess = true;
		}
		return updateSuccess;
	}
	
	public boolean deleteGenerals(String generalsId){
		//TODO 考慮註記方式，須再將未來相關table刪除！！
		return false;
	}
	
	/**
	 * 神將關鍵字查詢
	 * @param generalsName
	 * @return List<GeneralsDo>
	 */
	public List<GeneralsDo> queryGeneralsListLike(String generalsName){
		if(StringUtils.isBlank(generalsName)){
			return sqlSessionDao.query(GeneralsDo.class, "generals.selectAll");
		}
		generalsName = "%" + generalsName + "%";
		return sqlSessionDao.query(GeneralsDo.class, "generals.selectByGeneralsNameLike",generalsName);
	}
	
	/**
	 * 神將姓名查詢
	 * @param generalsName
	 * @return List<GeneralsDo>
	 */
	public List<GeneralsDo> queryGeneralsList(String generalsName){
		if(StringUtils.isBlank(generalsName)){
			logger.debug("GeneralsId is null");
			throw new IllegalArgumentException("GeneralsId is null");
		}
		return sqlSessionDao.query(GeneralsDo.class, "generals.selectByGeneralsName",generalsName);
	}
	
	/**
	 * 依主鍵查詢神將
	 * @param generalsId
	 * @return GeneralsDo
	 */
	public GeneralsDo queryGeneralsByPk(String generalsId){
		if(StringUtils.isBlank(generalsId)){
			logger.debug("generalsId is null");
			throw new IllegalArgumentException("generalsId is null");
		}
		return sqlSessionDao.find(GeneralsDo.class, "generals.selectByPrimaryKey",generalsId);
	}
}
