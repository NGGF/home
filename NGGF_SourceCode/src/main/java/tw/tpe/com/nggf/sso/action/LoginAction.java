package tw.tpe.com.nggf.sso.action;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.action.DefaultAction;
import tw.tpe.com.nggf.common.model.dataobject.AccountDo;
import tw.tpe.com.nggf.common.service.AccountService;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends DefaultAction{

	/**
	 * serialId
	 */
	private static final long serialVersionUID = 5533580402825472221L;
	Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Autowired
	@Qualifier("accountService")
	AccountService accountService;
	
	private String accId;
	private String accPwd;
	
	public String execute(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS z");
		String remoteIP = request.getRemoteAddr();
		Map<String, Object> session = ctx.getSession();
		UserContextVo userInfo;
		logger.info("User: " + accId + " application Log in the " + dtf.format(System.currentTimeMillis()) + " from " + remoteIP);
		session.clear();
		
		logger.debug(remoteIP);
		logger.debug("accId = {}",accId);
		logger.debug("accPwd = {}",accPwd);
		if(StringUtils.isBlank(accId)){
			addActionMessage("請輸入帳號");
			return "login";
		}
		AccountDo accountDo = accountService.queryAccountByPk(accId);
		if(accountDo != null && accId.equals(accountDo.getAccId()) && accPwd.equals(accountDo.getAccPwd())){
			userInfo = new UserContextVo();
			userInfo.setIp(remoteIP);
			logger.debug("accountDo = {}",accountDo.toString());
			userInfo.setUserName(accountDo.getName());
			userInfo.setUserId(accountDo.getAccId());
			String token = UUID.randomUUID().toString();
			userInfo.setToken(token);
			
			getSession().put("userInfo", userInfo);
			getSession().put("userName", accountDo.getName());
			getSession().put("serviceTime", System.currentTimeMillis());
			getSession().put("t", token);
			return SUCCESS;
		}else{
			addActionMessage("帳號密碼輸入錯誤!!");
			return "login";
		}
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getAccPwd() {
		return accPwd;
	}

	public void setAccPwd(String accPwd) {
		this.accPwd = accPwd;
	}
}
