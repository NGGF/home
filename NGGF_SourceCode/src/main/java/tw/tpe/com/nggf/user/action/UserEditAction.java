package tw.tpe.com.nggf.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.action.DefaultAction;
import tw.tpe.com.nggf.common.model.dataobject.UserDo;
import tw.tpe.com.nggf.common.service.UserService;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

public class UserEditAction extends DefaultAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7193609911160768507L;
	
	String queryId;
	String queryName;
	UserDo userDo;
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	public String execute(){
		userDo = userService.queryUserByPk(queryId);
		if(userDo == null){
			addActionMessage("資料錯誤！！請洽系統管理員");
			return "userQuery";
		}else{
			return "userEdit";
		}
	}
	
	public String updateUser(){
		UserContextVo userInfo = getUserContextVo();
		boolean insertSuccess = userService.updateUser(userDo, userInfo);
		if(insertSuccess){
			addActionMessage("更新成功");
		}else{
			addActionMessage("更新失敗");
		}
		return "userQuery";
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
	
}
