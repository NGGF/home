package tw.tpe.com.nggf.sso.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.tpe.com.nggf.common.action.DefaultAction;
import com.opensymphony.xwork2.ActionContext;

public class LogoutAction extends DefaultAction{

	/**
	 * serialId
	 */
	private static final long serialVersionUID = 5533580402825472221L;
	Logger logger = LoggerFactory.getLogger(LogoutAction.class);
	
	public String execute(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String remoteIP = request.getRemoteAddr();
		logger.debug("remoteIP = {}",remoteIP);
		Map<String, Object> session = ctx.getSession();
		session.clear();
		return "login";
	}
}
