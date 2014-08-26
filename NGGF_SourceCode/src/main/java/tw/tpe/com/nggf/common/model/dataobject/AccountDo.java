package tw.tpe.com.nggf.common.model.dataobject;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import tw.tpe.com.nggf.common.utils.DateUtils;

public class AccountDo {
    private String accId;

    private String accPwd;

    private String name;

    private Date birthday;
    
    private String birthdayStr;

    private String gender;

    private String eMail;

    private String userId;

    private Long roleId;

    private String creator;

    private Date createDate;

    private String creatorIp;

    private String maintainer;

    private Date maintainDate;

    private String maintainIp;

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getAccPwd() {
        return accPwd;
    }

    public void setAccPwd(String accPwd) {
        this.accPwd = accPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() throws ParseException {
    	if(StringUtils.isNotBlank(this.birthdayStr)){
    		birthday = DateUtils.transDate(this.birthdayStr, "yyyy/MM/dd");
    	}
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
	 * @return the birthdayStr
	 */
	public String getBirthdayStr() {
		return birthdayStr;
	}

	/**
	 * @param birthdayStr the birthdayStr to set
	 */
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}