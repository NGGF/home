package tw.tpe.com.nggf.common.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tw.tpe.com.nggf.common.model.SqlSessionDao;
import tw.tpe.com.nggf.common.model.dataobject.AccountDo;
import tw.tpe.com.nggf.common.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("sqlSessionDao")
	SqlSessionDao sqlSessionDao;
	
	/**
	 * 依帳號查詢帳號資訊
	 * @param accId
	 * @return AccountDo
	 */
	public AccountDo queryAccountByPk(String accId){
		if(StringUtils.isBlank(accId)){
			logger.error("accId is empty");
			throw new IllegalArgumentException("property is empty");
		}
		return sqlSessionDao.find(AccountDo.class, "account.selectByPrimaryKey",accId);
	}
	
	/**
	 * 新增新申請帳戶
	 * @param accountDo
	 * @return
	 */
	public boolean insertAccount(AccountDo accountDo,String remoteIP){
		if(accountDo == null){
			logger.debug("accountDo is null");
			throw new IllegalArgumentException("accountDo is null");
		}
		boolean insertSuccess = false;
		//預設一般權限
		accountDo.setRoleId(1L);
		//塞入異動欄位
		accountDo.setCreator("system");
		accountDo.setCreateDate(new Date());
		accountDo.setCreatorIp(remoteIP);
		accountDo.setMaintainer("system");
		accountDo.setMaintainDate(new Date());
		accountDo.setMaintainIp(remoteIP);
		
		int insertResult = sqlSessionDao.insert("account.insert",accountDo);
		if(insertResult > 0){
			insertSuccess = true;
		}
		return insertSuccess;
	}
}
