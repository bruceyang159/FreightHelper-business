/*
 * @Title CldErrUtil.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-4-10 ����9:42:18
 * @version 1.0
 */
package com.mtq.ols.tools;

import android.text.TextUtils;

import com.cld.log.CldLog;

/**
 * �����봦��
 * 
 * @author Zhouls
 * @date 2015-4-10 ����9:42:18
 */
public class CldErrUtil {

	/**
	 * ͨ�ô�����־����
	 * 
	 * @param errCode
	 *            �ӿڷ��صĴ�����
	 * @param strUrl
	 *            ��������
	 * @param strPost
	 *            post�������ݲ���
	 * @param ret
	 *            �ص����
	 * @return void
	 * @author Zhouls
	 * @date 2015-2-5 ����2:34:48
	 */
	public static void handleErr(CldSapReturn res) {
		switch (res.errCode) {
		case 0:
			/** �ӿڷ�����ȷ */
		case 101:
			/** �û���ע�� */
		case 110:
			/** ���������� */
		case 201:
			/** �ֻ�����ע�� */
		case 301:
			/** �豸��ע�� */
			break;
		default:
			CldLog.e("[ols]" + res.errCode);
			if (!TextUtils.isEmpty(res.url)) {
				CldLog.e("[ols]" + res.url);
			}
			if (!TextUtils.isEmpty(res.jsonPost)) {
				CldLog.e("[ols]" + res.jsonPost);
			}
			if (!TextUtils.isEmpty(res.jsonReturn)) {
				CldLog.e("[ols]" + res.jsonReturn);
			}
		}
	}

	/**
	 * �������Ϸ�����
	 * 
	 * @param res
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-10 ����9:45:01
	 */
	public static CldSapReturn errDeal() {
		CldSapReturn res = new CldSapReturn();
		res.errCode = 401;
		res.errMsg = "�������Ϸ�";
		return res;
	}
}
