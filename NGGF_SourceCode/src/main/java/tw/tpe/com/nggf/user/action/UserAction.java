package tw.tpe.com.nggf.user.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.action.DefaultAction;
import tw.tpe.com.nggf.common.model.dataobject.UserDo;
import tw.tpe.com.nggf.common.service.UserService;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

public class UserAction extends DefaultAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 362271760145082368L;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	UserDo userDo;
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String inserUser(){
		List<UserDo> list = userService.queryUserList(userDo.getUserName());
		if(list != null && list.size() > 0){
			addActionMessage(userDo.getUserName() + "已在人員列表裡!!");
		}else{
			UserContextVo userInfo = getUserContextVo();
			boolean insertSuccess = userService.insertUser(userDo, userInfo);
			if(insertSuccess){
				addActionMessage("新增成功");
			}else{
				addActionMessage("新增失敗");
			}
		}
		return SUCCESS;
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
