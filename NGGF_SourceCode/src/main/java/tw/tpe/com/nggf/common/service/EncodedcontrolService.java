package tw.tpe.com.nggf.common.service;

public interface EncodedcontrolService {

	/**
	 * 依號碼類別查詢當前號碼
	 * @param encodeType
	 * @return 該類別最近產生的編號
	 */
	public String queryCurrentNo(String encodeType);
}
