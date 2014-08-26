package tw.tpe.com.nggf.common.service;

import java.util.List;

import tw.tpe.com.nggf.common.model.dataobject.GeneralsDo;
import tw.tpe.com.nggf.sso.service.vo.UserContextVo;

public interface GeneralsService {

	/**
	 * 神將新增
	 * @param generalsDo
	 * @param generalsInfo
	 * @return boolean
	 */
	public boolean insertGenerals(GeneralsDo generalsDo,UserContextVo userInfo);
	
	/**
	 * 更新神將資訊
	 * @param generalsDo
	 * @param userInfo
	 * @return boolean
	 */
	public boolean updateGenerals(GeneralsDo generalsDo,UserContextVo userInfo);
	
	public boolean deleteGenerals(String generalsId);
	
	/**
	 * 神將關鍵字查詢
	 * @param generalsName
	 * @return List<GeneralsDo>
	 */
	public List<GeneralsDo> queryGeneralsListLike(String generalsName);
	
	/**
	 * 神將姓名查詢
	 * @param generalsName
	 * @return List<GeneralsDo>
	 */
	public List<GeneralsDo> queryGeneralsList(String generalsName);
	
	/**
	 * 依主鍵查詢神將
	 * @param generalsId
	 * @return GeneralsDo
	 */
	public GeneralsDo queryGeneralsByPk(String generalsId);
}
