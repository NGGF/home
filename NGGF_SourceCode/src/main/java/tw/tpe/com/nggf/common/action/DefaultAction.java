package tw.tpe.com.nggf.common.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import tw.tpe.com.nggf.common.action.vo.JSONResultVo;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

import com.opensymphony.xwork2.ActionSupport;

public class DefaultAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ServletContextAware, SessionAware {
	
	public static final String TO_REDIRECT_ACTION = "toRedirectAction";
	public static final String TO_TILES = "toTiles";
	public static final String TO_HYCASE = "toHyCase";
	public static final String CASE_ID = "hycase.caseid";
	public static final String CASE_DATA = "hycase.casedata";
	public static final String JSON_RESULT = "jsonResult";

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext context;
	private String hyCaseKey;
	private String resultValue;
	private String actionName;
	private JSONResultVo jsonResultVo;
	
	/**
	 * 使用者被授權的物件
	 */
	private UserContextVo userContextVo;
	
	/**
	 * a Map of HTTP session attribute name/value pairs.
	 */
	private Map<String, Object> session;

	
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	public String toHyCase(String caseId, Object... dataObjects) {
		Map<String, Object> inf = new HashMap<String, Object>();
		inf.put(CASE_ID, caseId);
		inf.put(CASE_DATA, dataObjects);
		return this.toHyCase(inf);
	}
	
	public String toHyCase(Map<String, Object> inf) {
		String key = Long.toString(System.currentTimeMillis());
		setHyCaseKey(key);
		getServletRequest().getSession().setAttribute(key, inf);
		return TO_HYCASE;
	}
	
	public String toTiles(String resultValue) {
		setResultValue(resultValue);
		return TO_TILES;
	}
	
	public String toRedirectAction(String actionName) {
		setActionName(actionName);
		return TO_REDIRECT_ACTION;
	}

	public String getHyCaseKey() {
		return this.hyCaseKey;
	}

	public void setHyCaseKey(String hyCaseKey) {
		this.hyCaseKey = hyCaseKey;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return the userContextVo
	 */
	public UserContextVo getUserContextVo() {
		if (userContextVo == null) {
			userContextVo = (UserContextVo) request.getSession().getAttribute("userInfo");
		}
		
		return userContextVo;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
		
	}
	
	public ServletContext getServletContext() {
		return context;
		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	
	public HttpServletResponse getServletResponse() {
		return response;
		
	}

	/**
	 * @return the jsonResultVo
	 */
	public JSONResultVo getJsonResultVo() {
		return jsonResultVo;
	}

	/**
	 * @param jsonResultVo the jsonResultVo to set
	 */
	public void setJsonResultVo(JSONResultVo jsonResultVo) {
		this.jsonResultVo = jsonResultVo;
	}
	
}
