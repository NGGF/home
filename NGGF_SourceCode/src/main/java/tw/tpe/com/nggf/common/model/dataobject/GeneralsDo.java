package tw.tpe.com.nggf.common.model.dataobject;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import tw.tpe.com.nggf.common.utils.DateUtils;

public class GeneralsDo {
    private String generalsId;

    private String generalsName;

    private Long makeCost;

    private Date makeDate;
    
    private String makeDateStr;

    private String generalsDesc;

    private String photoUrl;

    private String creator;

    private Date createDate;

    private String creatorIp;

    private String maintainer;

    private Date maintainDate;

    private String maintainIp;
    
    /**
     * 刪除除名註記
     */
    private String delFlag;

    public String getGeneralsId() {
        return generalsId;
    }

    public void setGeneralsId(String generalsId) {
        this.generalsId = generalsId;
    }

    public String getGeneralsName() {
        return generalsName;
    }

    public void setGeneralsName(String generalsName) {
        this.generalsName = generalsName;
    }

    public Long getMakeCost() {
        return makeCost;
    }

    public void setMakeCost(Long makeCost) {
        this.makeCost = makeCost;
    }

    public Date getMakeDate() throws ParseException {
    	if(StringUtils.isNotBlank(this.makeDateStr)){
    		makeDate = DateUtils.transDate(this.makeDateStr, "yyyy/MM/dd");
    	}
        return makeDate;
    }
    
    public void setMakeDate(Date makeDate) {
        this.makeDate = makeDate;
    }

    /**
	 * @return the makeDateStr
	 */
	public String getMakeDateStr() {
		return makeDateStr;
	}

	/**
	 * @param makeDateStr the makeDateStr to set
	 */
	public void setMakeDateStr(String makeDateStr) {
		this.makeDateStr = makeDateStr;
	}

	public String getGeneralsDesc() {
        return generalsDesc;
    }

    public void setGeneralsDesc(String generalsDesc) {
        this.generalsDesc = generalsDesc;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatorIp() {
        return creatorIp;
    }

    public void setCreatorIp(String creatorIp) {
        this.creatorIp = creatorIp;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }

    public String getMaintainIp() {
        return maintainIp;
    }

    public void setMaintainIp(String maintainIp) {
        this.maintainIp = maintainIp;
    }

    /**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}