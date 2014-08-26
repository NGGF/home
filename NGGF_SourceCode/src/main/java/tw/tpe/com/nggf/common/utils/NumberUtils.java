/*
 * Copyright 2012 (C) Hyweb Technology. All Rights 
 * Reserved.
 *
 * Id: A0105
 * Date: 2012/11/20 上午11:29:06
 ****************************************
 * Date: 2012/11/20 上午11:29:06 User: Carl
 * Desc: Class Creat
 * --------------------------------------
 */
package tw.tpe.com.nggf.common.utils;

/**
 * 功能描述
 *
 * @author <a href=”mailto:carl.lu@mail.hyweb.com.tw”>Carl Lu</a>
 * @version Revision: 1  Date: 2012/11/20 上午11:29:06
 * @see
 */
public class NumberUtils {

	public static Long sum(Long summand, Long addend) {
		if (null == summand && null == addend)
			return null;
		return (null == summand ? 0 : summand) + (null == addend ? 0 : addend);
	}

	public static Integer sum(Integer summand, Integer addend) {
		if (null == summand && null == addend)
			return null;
		return (null == summand ? 0 : summand) + (null == addend ? 0 : addend);
	}

	public static Integer sum(String summand, String addend) {
		if (null == summand && null == addend)
			return null;
		int s, a;

		if (null != summand && false == summand.matches("\\d*"))
			return null;
		else
			s = Integer.parseInt(null != summand ? summand : "0");

		if (null != addend && false == addend.matches("\\d*"))
			return null;
		else
			a = Integer.parseInt(null != addend ? addend : "0");

		return s + a;
	}

	public static Long subtraction(Long summand, Long addend) {
		if (null == summand && null == addend)
			return null;
		return (null == summand ? 0 : summand) - (null == addend ? 0 : addend);
	}

	public static Integer subtraction(Integer summand, Integer addend) {
		if (null == summand && null == addend)
			return null;
		return (null == summand ? 0 : summand) - (null == addend ? 0 : addend);
	}

	public static Integer subtraction(String summand, String addend) {
		if (null == summand && null == addend)
			return null;
		int s, a;

		if (null != summand && false == summand.matches("\\d*"))
			return null;
		else
			s = Integer.parseInt(null != summand ? summand : "0");

		if (null != addend && false == addend.matches("\\d*"))
			return null;
		else
			a = Integer.parseInt(null != addend ? addend : "0");

		return s - a;
	}
}
