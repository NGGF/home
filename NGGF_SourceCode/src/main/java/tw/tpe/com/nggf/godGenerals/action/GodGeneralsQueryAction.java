package tw.tpe.com.nggf.godGenerals.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.action.DefaultAction;
import tw.tpe.com.nggf.common.model.dataobject.GeneralsDo;
import tw.tpe.com.nggf.common.service.GeneralsService;

public class GodGeneralsQueryAction extends DefaultAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5426560757364552220L;
	
	String queryGeneralsName;
	List<GeneralsDo> generalsDoList;
	String generalsId;
	GeneralsDo generalsDo;

	@Autowired
	@Qualifier("generalsService")
	GeneralsService generalsService;
	
	@Override
	public String execute(){
		return "godGeneralsQuery";
	}
	
	public String queryList(){
		generalsDoList = generalsService.queryGeneralsListLike(queryGeneralsName);
		if(generalsDoList != null && generalsDoList.size() > 0){
			return "godGeneralsQueryList";
		}else{
			addActionMessage("查無此資料");
			return "godGeneralsQuery";
		}
	}
	
	public String queryView(){
		generalsDo = generalsService.queryGeneralsByPk(generalsId);
		if(generalsDo == null){
			addActionMessage("資料錯誤！！請洽系統管理員");
			return queryList();
		}else{
			return "godGeneralsQueryView";
		}
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
