package tw.tpe.com.nggf.registry.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import tw.tpe.com.nggf.common.model.dataobject.AccountDo;
import tw.tpe.com.nggf.common.service.AccountService;

public class RegistryAddAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8272298835893718597L;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	AccountDo accountDo;
	
	@Autowired
	@Qualifier("accountService")
	AccountService accountService;
	
	public String execute()throws Exception{
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String remoteIP = request.getRemoteAddr();
		logger.debug("remoteIP = {}",remoteIP);
		logger.debug("accountDo = {}",accountDo.toString());
		boolean insertSuccess = accountService.insertAccount(accountDo, remoteIP);
		if(insertSuccess){
			addActionMessage("註冊成功");
		}else{
			addActionMessage("註冊失敗");
		}
		return "login";
	}

	public AccountDo getAccountDo() {
		return accountDo;
	}
	public void setAccountDo(AccountDo accountDo) {
		this.accountDo = accountDo;
	}
}
