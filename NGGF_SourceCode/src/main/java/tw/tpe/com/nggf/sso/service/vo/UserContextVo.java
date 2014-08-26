package tw.tpe.com.nggf.sso.service.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 使用者被授權的物件
 * 
 * @author B00
 * @version 1.0
 * @created 05-六月-2012 15:21:25
 */
public class UserContextVo {

	/**
	 * 使用者ID
	 */
	private String userId;
	/**
	 * 使用者姓名
	 */
	private String userName;
	/**
	 * 使用者單位代碼
	 */
	private String deptId;
	/**
	 * 使用者單位名稱
	 */
	private String deptName;
	/**
	 * 單一登入key
	 */
	private String token;
	/**
	 * 連線主機的 ip address (xxx.xxx.xxx.xxx)
	 */
	private String ip;
    /**
     * 工作站主機所處位置的部門id
     */
    private String workStationDeptId;
    /**
     * 工作站主機所處位置的群組id
     */
    private String workStationGroupId;

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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

    public String getWorkStationDeptId() {
        return workStationDeptId;
    }

    public void setWorkStationDeptId(String workStationDeptId) {
        this.workStationDeptId = workStationDeptId;
    }

    public String getWorkStationGroupId() {
        return workStationGroupId;
    }

    public void setWorkStationGroupId(String workStationGroupId) {
        this.workStationGroupId = workStationGroupId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}