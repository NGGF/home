package tw.tpe.com.nggf.godGenerals.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.tpe.com.nggf.common.action.DefaultAction;
import tw.tpe.com.nggf.common.model.dataobject.GeneralsDo;
import tw.tpe.com.nggf.common.service.GeneralsService;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

public class GodGeneralsAction extends DefaultAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8114759280317762769L;

	GeneralsDo generalsDo;
	
	@Autowired
	@Qualifier("generalsService")
	GeneralsService generalsService;
	
	@Override
	public String execute(){
		return SUCCESS;
	}
	
	public String inserGodGenerals(){
		List<GeneralsDo> list = generalsService.queryGeneralsList(generalsDo.getGeneralsName());
		if(list != null && list.size() > 0){
			addActionMessage(generalsDo.getGeneralsName() + "已在神將列表裡!!");
		}else{
			UserContextVo userInfo = getUserContextVo();
			boolean insertSuccess = generalsService.insertGenerals(generalsDo, userInfo);
			if(insertSuccess){
				addActionMessage("新增成功");
			}else{
				addActionMessage("新增失敗");
			}
		}
		return SUCCESS;
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
