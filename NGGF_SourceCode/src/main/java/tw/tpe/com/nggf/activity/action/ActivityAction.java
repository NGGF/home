package tw.tpe.com.nggf.activity.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.model.dataobject.GeneralsDo;
import tw.tpe.com.nggf.common.model.dataobject.UserDo;
import tw.tpe.com.nggf.common.service.GeneralsService;
import tw.tpe.com.nggf.common.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class ActivityAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 270612535737590310L;
	Logger logger = LoggerFactory.getLogger(getClass());
	String activityStartDay;
	String activityEndDay;
	List<GeneralsDo> generalsDoList;
	List<UserDo> userDoList;
	
	@Autowired
	@Qualifier("generalsService")
	GeneralsService generalsService;
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	public String execute(){
		return "activityDateAdd";
	}
	
	public String addActivity(){
		return "activityAdd";
	}
	
	/**
	 * JSON
	 * @return
	 */
	public String querygeneralsList(){
		generalsDoList = generalsService.queryGeneralsListLike(null);
		return "querygeneralsList";
	}
	
	public String queryUserList(){
		userDoList = userService.queryUserListLike(null);
		return "queryUserList";
	}

	
	/**
	 * @return the activityStartDay
	 */
	public String getActivityStartDay() {
		return activityStartDay;
	}

	/**
	 * @param activityStartDay the activityStartDay to set
	 */
	public void setActivityStartDay(String activityStartDay) {
		this.activityStartDay = activityStartDay;
	}

	/**
	 * @return the activityEndDay
	 */
	public String getActivityEndDay() {
		return activityEndDay;
	}

	/**
	 * @param activityEndDay the activityEndDay to set
	 */
	public void setActivityEndDay(String activityEndDay) {
		this.activityEndDay = activityEndDay;
	}

	/**
	 * @return the generalsDoList
	 */
	public List<GeneralsDo> getGeneralsDoList() {
		return generalsDoList;
	}

	/**
	 * @param generalsDoList the generalsDoList to set
	 */
	public void setGeneralsDoList(List<GeneralsDo> generalsDoList) {
		this.generalsDoList = generalsDoList;
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
	
}
