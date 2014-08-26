package tw.tpe.com.nggf.common.service;

import tw.tpe.com.nggf.common.model.dataobject.AccountDo;

public interface AccountService {

	/**
	 * 依帳號查詢帳號資訊
	 * @param accId
	 * @return AccountDo
	 */
	public AccountDo queryAccountByPk(String accId);
	
	/**
	 * 新增新申請帳戶
	 * @param accountDo
	 * @return
	 */
	public boolean insertAccount(AccountDo accountDo,String remoteIP);
	
}
