/*
 * @Title CldSapReturn.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-3-13 ����12:09:01
 * @version 1.0
 */
package com.mtq.ols.tools;

import java.util.Map;

import com.mtq.ols.dal.CldDalKAccount;

/**
 * Э��㷵�ؽ����
 * 
 * @author Zhouls
 * @date 2015-3-13 ����12:09:01
 */
public class CldSapReturn {
	/** ������ */
	public int errCode;
	/** ������Ϣ */
	public String errMsg;
	/** �ӿ�ʹ��session */
	public String session;
	/** ����Json */
	public String jsonReturn;
	/** ����URL */
	public String url;
	/** PostJson�� */
	public String jsonPost;
	/** Post���������� */
	public byte[] bytePost;
	/** Post��׼map*/
	public Map<String, String> mapPost;

	public CldSapReturn() {
		errCode = -1;
		errMsg = "";
		session = CldDalKAccount.getInstance().getSession();
		jsonReturn = "";
		url = "";
		jsonPost = "";
	}
}
