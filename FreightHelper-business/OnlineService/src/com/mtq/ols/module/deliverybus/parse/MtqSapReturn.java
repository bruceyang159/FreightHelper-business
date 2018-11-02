/**
 * Э��㷵�ؽ����
 * 
 * @author zhaoqy
 * @date 2017-06-15
 */

package com.mtq.ols.module.deliverybus.parse;

import java.util.Map;

public class MtqSapReturn {

	/** ������ */
	public int errCode;
	/** ������Ϣ */
	public String errMsg;
	/** ����Json */
	public String jsonReturn;
	/** ����URL */
	public String url;
	/** PostJson�� */
	public String jsonPost;
	/** Post���������� */
	public byte[] bytePost;
	/** Post��׼map */
	public Map<String, String> mapPost;

	public MtqSapReturn() {
		errCode = -1;
		errMsg = "";
		jsonReturn = "";
		url = "";
		jsonPost = "";
	}
}
