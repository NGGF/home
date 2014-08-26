package tw.tpe.com.nggf.sso.interceptor;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Action 攔截
 */
public class AuthorityInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = System.currentTimeMillis();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private static Set<String> unregulated;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {		
		String result = null ;
		Object actionObject = invocation.getAction();

		logger.info("before authority " + actionObject);

		ActionContext ctx = invocation.getInvocationContext();
		Map<String, Object> session = ctx.getSession();
		Object userOb = session.get("userInfo");
		UserContextVo userInfo = null;
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String userToken = request.getParameter("t");
		logger.debug("userToken = {}",userToken);
		// 取得 session 內之 user info
		if (null != userOb && userOb instanceof UserContextVo)
			userInfo = (UserContextVo) userOb;
		
		if (null == unregulated){
			this.creatAuthIdMap();
		}
		logger.debug("actionObject.getClass().getName() = {}",actionObject.getClass().getName());
		// Action
		if (false == unregulated.contains(actionObject.getClass().getName())) {
			try {
				// 判斷無使用者登入資訊，返回登入頁面
				if (null == userInfo && StringUtils.isBlank(userToken)) {
					this.printErrMsg(actionObject, "使用者未登入，請重新登入！");
					return "login";
				}
			} catch (Exception e) {
				this.printErrMsg(actionObject, "使用者不合法，請重新登入！");
				return "login";
			}
		}

		// 呼叫後續 Action
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			logger.error("Action: " + actionObject + ", Exception!", e);
			result = Action.ERROR;
		}

		session.put("dayStr", sdf.format(System.currentTimeMillis()));

		logger.info("after authority " + actionObject);

		return result;   
	}

	private void printErrMsg(Object action, String message) {
		logger.error(message);
		if (action instanceof ActionSupport)
			((ActionSupport) action).addFieldError("login", message);
	}
	
	private void creatAuthIdMap() throws Exception {

		unregulated = new HashSet<String>();
		unregulated.add("com.opensymphony.xwork2.ActionSupport");
		unregulated.add("tw.tpe.com.nggf.sso.action.LoginAction");
		unregulated.add("tw.tpe.com.nggf.sso.action.LogoutAction");
		unregulated.add("tw.tpe.com.nggf.registry.action.RegistryAction");
		unregulated.add("tw.tpe.com.nggf.registry.action.RegistryAddAction");
	}
}
