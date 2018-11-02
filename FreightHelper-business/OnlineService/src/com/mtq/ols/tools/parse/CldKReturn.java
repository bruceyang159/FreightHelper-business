/*
 * @Title CldKReturn.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-11-16 ����11:29:41
 * @version 1.0
 */
package com.mtq.ols.tools.parse;

import java.util.Map;

/**
 * Э�����߼������������
 * 
 * @author Zhouls
 * @date 2015-11-16 ����11:29:41
 */
public class CldKReturn {
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
	/** Post��׼map*/
	public Map<String, String> mapPost;

	public CldKReturn() {
		errCode = -1;
		errMsg = "";
		jsonReturn = "";
		url = "";
		jsonPost = "";
	}
}
