package tw.tpe.com.nggf.registry.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.model.dataobject.AccountDo;
import tw.tpe.com.nggf.common.service.AccountService;

import com.opensymphony.xwork2.ActionSupport;

public class RegistryAction  extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6659447069868807722L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private boolean isDuplicate;
	private String accId;
	
	@Autowired
	@Qualifier("accountService")
	AccountService accountService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String isDuplicate(){
		logger.debug("accId = {}",accId);
		AccountDo accountDo = accountService.queryAccountByPk(accId);
		if(accountDo != null && StringUtils.isNotBlank(accountDo.getAccId()) && accountDo.getAccId().equals(accId)){
			isDuplicate = true;
		}
		return "isDuplicate";
	}
	
	public boolean getIsDuplicate(){
		return this.isDuplicate;
	}
	public void setIsDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	/**
	 * @return the accId
	 */
	public String getAccId() {
		return accId;
	}
	/**
	 * @param accId the accId to set
	 */
	public void setAccId(String accId) {
		this.accId = accId;
	}
	
}
