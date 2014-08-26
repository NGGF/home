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
import tw.tpe.com.nggf.common.model.dataobject.UserDo;
import tw.tpe.com.nggf.common.service.EncodedcontrolService;
import tw.tpe.com.nggf.common.service.UserService;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

@Service("userService")
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("sqlSessionDao")
	SqlSessionDao sqlSessionDao;
	
	@Autowired
	@Qualifier("encodedcontrolService")
	EncodedcontrolService encodedcontrolService;
	
	/**
	 * 將腳新增
	 * @param userDo
	 * @param userInfo
	 * @return boolean
	 */
	public boolean insertUser(UserDo userDo,UserContextVo userInfo) {
		if(userDo == null){
			logger.debug("userDo is null");
			throw new IllegalArgumentException("userDo is null");
		}
		boolean insertSuccess = false;
		
		//塞入異動欄位
		userDo.setUserId(encodedcontrolService.queryCurrentNo("U"));
		userDo.setCreator(userInfo.getUserId());
		userDo.setCreateDate(new Date());
		userDo.setCreatorIp(userInfo.getIp());
		userDo.setMaintainer(userInfo.getUserId());
		userDo.setMaintainDate(new Date());
		userDo.setMaintainIp(userInfo.getIp());
		
		int insertResult = sqlSessionDao.insert("user.insertSelective",userDo);
		if(insertResult > 0){
			insertSuccess = true;
		}
		return insertSuccess;
	}
	
	/**
	 * 更新將腳資訊
	 * @param userDo
	 * @param userInfo
	 * @return boolean
	 */
	public boolean updateUser(UserDo userDo,UserContextVo userInfo){
		if(userDo == null){
			logger.debug("userDo is null");
			throw new IllegalArgumentException("userDo is null");
		}
		if(StringUtils.isBlank(userDo.getUserId())){
			logger.debug("UserId is null");
			throw new IllegalArgumentException("UserId is null");
		}
		boolean updateSuccess = false;
		
		//塞入異動欄位
		userDo.setMaintainer(userInfo.getUserId());
		userDo.setMaintainDate(new Date());
		userDo.setMaintainIp(userInfo.getIp());
		
		int updateResult = sqlSessionDao.insert("user.updateByPrimaryKeySelective",userDo);
		if(updateResult > 0){
			updateSuccess = true;
		}
		return updateSuccess;
	}
	
	public boolean deleteUser(String UserId){
		//TODO 考慮註記方式，須再將未來相關table刪除！！
		return false;
	}
	
	/**
	 * 將腳關鍵字查詢
	 * @param userName
	 * @return List<UserDo>
	 */
	public List<UserDo> queryUserListLike(String userName){
		if(StringUtils.isBlank(userName)){
			return sqlSessionDao.query(UserDo.class, "user.selectAll");
		}
		userName = "%" + userName + "%";
		return sqlSessionDao.query(UserDo.class, "user.selectByUserNameLike",userName);
	}
	
	/**
	 * 將腳姓名查詢
	 * @param userName
	 * @return List<UserDo>
	 */
	public List<UserDo> queryUserList(String userName){
		if(StringUtils.isBlank(userName)){
			logger.debug("userName is null");
			throw new IllegalArgumentException("userName is null");
		}
		return sqlSessionDao.query(UserDo.class, "user.selectByUserName",userName);
	}
	
	/**
	 * 依主鍵查詢將腳
	 * @param UserId
	 * @return UserDo
	 */
	public UserDo queryUserByPk(String UserId){
		if(StringUtils.isBlank(UserId)){
			logger.debug("UserId is null");
			throw new IllegalArgumentException("UserId is null");
		}
		return sqlSessionDao.find(UserDo.class, "user.selectByPrimaryKey",UserId);
	}
	
	/**
	 * 依姓名查詢關鍵字算戰鬥人員人數
	 * @param userName
	 * @return 戰鬥人員人數
	 */
	public int queryUserType1Count(String userName){
		if(StringUtils.isBlank(userName)){
			return sqlSessionDao.find(Integer.class, "user.selectUserType1Count_all");
		}
		userName = "%" + userName + "%";
		return sqlSessionDao.find(Integer.class, "user.selectUserType1Count",userName);
	}
	
}
