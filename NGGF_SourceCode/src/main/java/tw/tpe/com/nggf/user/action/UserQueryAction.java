package tw.tpe.com.nggf.user.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.action.DefaultAction;
import tw.tpe.com.nggf.common.model.dataobject.UserDo;
import tw.tpe.com.nggf.common.service.UserService;

public class UserQueryAction extends DefaultAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 362271760145082368L;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	UserDo userDo;
	String queryName;
	List<UserDo> userDoList;
	String queryId;
	/**
	 * 戰鬥人數
	 */
	int userType1Count;
	/**
	 * 護軍人員人數
	 */
	int userType2Count;
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	public String execute(){
		return "userQuery";
	}
	
	public String queryList(){
		logger.debug("userName = {}",queryName);
		userType1Count = userService.queryUserType1Count(queryName);
		userType2Count = 0;
		logger.debug("userType1Count = {}",userType1Count);
		userDoList = userService.queryUserListLike(queryName);
		if(userDoList != null && userDoList.size() > 0){
			//總人數減戰鬥人數=護軍人員人數
			userType2Count = userDoList.size()-userType1Count;
		}else{
			addActionMessage("查無此資料");
			return "userQuery";
		}
		
		return "userQueryList";
	}
	
	public String queryView(){
		userDo = userService.queryUserByPk(queryId);
		if(userDo == null){
			addActionMessage("資料錯誤！！請洽系統管理員");
			return queryList();
		}else{
			return "userQueryView";
		}
	}
	
	/**
	 * @return the queryName
	 */
	public String getQueryName() {
		return queryName;
	}

	/**
	 * @param queryName the queryName to set
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	/**
	 * @return the userDoList
	 */
	public List<UserDo> getUserDoList() {
		return userDoList;
	}

	/**
	 * @param userDoList the userDoList to set
	 */
	public void setUserDoList(List<UserDo> userDoList) {
		this.userDoList = userDoList;
	}

	/**
	 * @return the userDo
	 */
	public UserDo getUserDo() {
		return userDo;
	}
	/**
	 * @param userDo the userDo to set
	 */
	public void setUserDo(UserDo userDo) {
		this.userDo = userDo;
	}

	/**
	 * @return the queryId
	 */
	public String getQueryId() {
		return queryId;
	}

	/**
	 * @param queryId the queryId to set
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**
	 * @return the userType1Count
	 */
	public int getUserType1Count() {
		return userType1Count;
	}

	/**
	 * @param userType1Count the userType1Count to set
	 */
	public void setUserType1Count(int userType1Count) {
		this.userType1Count = userType1Count;
	}

	/**
	 * @return the userType2Count
	 */
	public int getUserType2Count() {
		return userType2Count;
	}

	/**
	 * @param userType2Count the userType2Count to set
	 */
	public void setUserType2Count(int userType2Count) {
		this.userType2Count = userType2Count;
	}

}
