package tw.tpe.com.nggf.godGenerals.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sun.tools.javac.comp.Todo;

import tw.tpe.com.nggf.common.action.DefaultAction;
import tw.tpe.com.nggf.common.model.dataobject.GeneralsDo;
import tw.tpe.com.nggf.common.service.GeneralsService;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

public class GodGeneralsEditAction extends DefaultAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7193609911160768507L;
	
	String generalsId;
	String queryGeneralsName;
	GeneralsDo generalsDo;
	
	@Autowired
	@Qualifier("generalsService")
	GeneralsService generalsService;
	
	public String execute(){
		generalsDo = generalsService.queryGeneralsByPk(generalsId);
		if(generalsDo == null){
			addActionMessage("資料錯誤！！請洽系統管理員");
			return "godGeneralsQuery";
		}else{
			return "godGeneralsEdit";
		}
	}
	
	public String updateUser(){
		UserContextVo userInfo = getUserContextVo();
		boolean insertSuccess = generalsService.updateGenerals(generalsDo, userInfo);
		if(insertSuccess){
			addActionMessage("更新成功");
		}else{
			addActionMessage("更新失敗");
		}
		return "godGeneralsQuery";
	}

	/**
	 * @return the generalsId
	 */
	public String getGeneralsId() {
		return generalsId;
	}

	/**
	 * @param generalsId the generalsId to set
	 */
	public void setGeneralsId(String generalsId) {
		this.generalsId = generalsId;
	}

	/**
	 * @return the queryGeneralsName
	 */
	public String getQueryGeneralsName() {
		return queryGeneralsName;
	}

	/**
	 * @param queryGeneralsName the queryGeneralsName to set
	 */
	public void setQueryGeneralsName(String queryGeneralsName) {
		this.queryGeneralsName = queryGeneralsName;
	}

	/**
	 * @return the generalsDo
	 */
	public GeneralsDo getGeneralsDo() {
		return generalsDo;
	}

	/**
	 * @param generalsDo the generalsDo to set
	 */
	public void setGeneralsDo(GeneralsDo generalsDo) {
		this.generalsDo = generalsDo;
	}
	
}
