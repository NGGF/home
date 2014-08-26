/* 
 * (版權及授權描述)
 *
 * Copyright 2012 (C) Hyweb Technology. All Rights Reserved.
 *
 * Id: A1002 
 * Date: 2012/7/12 下午4:13:36
 ****************************************
 * Date: 2012/7/12 下午4:13:36  User:Terry　
 * Desc: 新加註解
 * --------------------------------------
 */
package tw.tpe.com.nggf.common.utils;

import java.util.UUID;

/**
 * UUID Utilities
 *
 * @author <a href="mailto:terry.lee@hyweb.com.tw">Terry Lee</a>
 * @version Revision: 1 Date: 2012/7/12 下午4:13:36
 * @see
 */
public class UUIDUtils {
	
	/**
	 * 取得一個隨機的 UUID
	 * 
	 * @return UUID 字串，不包含"-"
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
