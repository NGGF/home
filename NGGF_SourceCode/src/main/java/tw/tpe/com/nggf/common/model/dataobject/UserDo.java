package tw.tpe.com.nggf.common.model.dataobject;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import tw.tpe.com.nggf.common.utils.DateUtils;

public class UserDo {
    private String userId;

    private String userName;

    private String userDesc;

    private String photoUrl;

    private String profession;

    private String nickname;

    private String personalId;

    private Date birthday;
    
    private String birthdayStr;

    private String gender;

    private String fbEMail;

    private String reserveEmail;

    private String telCountry;

    private String telArea;

    private String telNo;

    private String telExt;

    private String addrCity;

    private String addrArea;

    private String address;

    private String addrZip;

    private String mobilePhone;

    private String underwearSize;

    private String shoesSize;

    private String bootsSize;

    private String creator;

    private Date createDate;

    private String creatorIp;

    private String maintainer;

    private Date maintainDate;

    private String maintainIp;

    private String userType;
    /**
     * 刪除除名註記
     */
    private String delFlag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPersonalId() {
        return personalId;
    }
    
    public void setPersonalId(String personalId) {
    	personalId = personalId.toUpperCase();
        this.personalId = personalId;
    }

    public Date getBirthday() throws ParseException {
    	if(StringUtils.isNotBlank(this.birthdayStr)){
    		birthday = DateUtils.transDate(this.birthdayStr, "yyyy/MM/dd");
    	}
        return birthday;
    }
    
	/**
	 * @param birthday the birthday to set
	 */
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

    public String getFbEMail() {
        return fbEMail;
    }

    public void setFbEMail(String fbEMail) {
        this.fbEMail = fbEMail;
    }

    public String getReserveEmail() {
        return reserveEmail;
    }

    public void setReserveEmail(String reserveEmail) {
        this.reserveEmail = reserveEmail;
    }

    public String getTelCountry() {
        return telCountry;
    }

    public void setTelCountry(String telCountry) {
        this.telCountry = telCountry;
    }

    public String getTelArea() {
        return telArea;
    }

    public void setTelArea(String telArea) {
        this.telArea = telArea;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getTelExt() {
        return telExt;
    }

    public void setTelExt(String telExt) {
        this.telExt = telExt;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrArea() {
        return addrArea;
    }

    public void setAddrArea(String addrArea) {
        this.addrArea = addrArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddrZip() {
        return addrZip;
    }

    public void setAddrZip(String addrZip) {
        this.addrZip = addrZip;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getUnderwearSize() {
        return underwearSize;
    }

    public void setUnderwearSize(String underwearSize) {
        this.underwearSize = underwearSize;
    }

    public String getShoesSize() {
        return shoesSize;
    }

    public void setShoesSize(String shoesSize) {
        this.shoesSize = shoesSize;
    }

    public String getBootsSize() {
        return bootsSize;
    }

    public void setBootsSize(String bootsSize) {
        this.bootsSize = bootsSize;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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