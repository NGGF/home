package tw.tpe.com.nggf.common.service;

import java.util.List;
import tw.tpe.com.nggf.common.model.dataobject.UserDo;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

public interface UserService {
	
	/**
	 * 將腳新增
	 * @param userDo
	 * @param userInfo
	 * @return boolean
	 */
	public boolean insertUser(UserDo userDo,UserContextVo userInfo);
	
	/**
	 * 更新將腳資訊
	 * @param userDo
	 * @param userInfo
	 * @return boolean
	 */
	public boolean updateUser(UserDo userDo,UserContextVo userInfo);
	
	/**
	 * 將腳關鍵字查詢
	 * @param userName
	 * @return List<UserDo>
	 */
	public List<UserDo> queryUserListLike(String userName);
	
	/**
	 * 將腳姓名查詢
	 * @param userName
	 * @return List<UserDo>
	 */
	public List<UserDo> queryUserList(String userName);
	
	/**
	 * 依主鍵查詢將腳
	 * @param UserId
	 * @return UserDo
	 */
	public UserDo queryUserByPk(String UserId);
	
	/**
	 * 依姓名查詢關鍵字算戰鬥人員人數
	 * @param userName
	 * @return 戰鬥人員人數
	 */
	public int queryUserType1Count(String userName);
}
