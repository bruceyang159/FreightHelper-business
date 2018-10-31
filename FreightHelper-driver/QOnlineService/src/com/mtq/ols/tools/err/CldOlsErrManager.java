/*
 * @Title CldErrUtil.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-4-10 ����9:42:18
 * @version 1.0
 */
package com.mtq.ols.tools.err;

import java.util.concurrent.TimeoutException;

import android.text.TextUtils;

import com.cld.log.CldLog;
import com.cld.net.volley.AuthFailureError;
import com.cld.net.volley.VolleyError;
import com.mtq.ols.tools.CldSapReturn;
import com.mtq.ols.tools.parse.CldKReturn;

/**
 * �����봦��
 * 
 * @author Zhouls
 * @date 2015-4-10 ����9:42:18
 */
public class CldOlsErrManager {
	private static String TAG = "ols_err:";

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
	private static void handleErr(CldKReturn res) {
		switch (res.errCode) {
		case 0:
			/** �ӿڷ�����ȷ */
		case 1:
			/** �����ɹ� */
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
			CldLog.e(TAG + res.errCode);
			if (!TextUtils.isEmpty(res.url)) {
				CldLog.e(TAG + res.url);
			}
			if (!TextUtils.isEmpty(res.jsonPost)) {
				CldLog.e(TAG + res.jsonPost);
			}
			if (!TextUtils.isEmpty(res.jsonReturn)) {
				CldLog.e(TAG + res.jsonReturn);
			}
		}
	}
	
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
	private static void handleErr(CldSapReturn res) {
		switch (res.errCode) {
		case 0:
			/** �ӿڷ�����ȷ */
		case 1:
			/** �����ɹ� */
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
			CldLog.e(TAG + res.errCode);
			if (!TextUtils.isEmpty(res.url)) {
				CldLog.e(TAG + res.url);
			}
			if (!TextUtils.isEmpty(res.jsonPost)) {
				CldLog.e(TAG + res.jsonPost);
			}
			if (!TextUtils.isEmpty(res.jsonReturn)) {
				CldLog.e(TAG + res.jsonReturn);
			}
		}
	}
	
	

	/**
	 * �첽����ǰ �����������������
	 * 
	 * @param request
	 * @param errRes
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-30 ����11:25:23
	 */
	public static void handleErr(CldKReturn request, CldKReturn errRes) {
		if (null != request) {
			if (null != errRes) {
				errRes.url = request.url;
				errRes.jsonPost = request.jsonPost;
				errRes.bytePost = request.bytePost;
				CldLog.i("ols", errRes.jsonReturn);
				handleErr(errRes);
			} else {
				handleErr(request);
			}
		}
	}
	

	/**
	 * �첽����ǰ �����������������
	 * 
	 * @param request
	 * @param errRes
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-30 ����11:25:23
	 */
	public static void handleErr(CldSapReturn request, CldSapReturn errRes) {
		if (null != request) {
			if (null != errRes) {
				errRes.url = request.url;
				errRes.jsonPost = request.jsonPost;
				errRes.bytePost = request.bytePost;
				CldLog.i("ols", errRes.jsonReturn);
				handleErr(errRes);
			} else {
				handleErr(request);
			}
		}
	}

	/**
	 * �����ܷ����쳣�����
	 * 
	 * @param error
	 *            �����ܷ��ش���
	 * @param errRes
	 *            �ӿ�ͨ�ûص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-9-11 ����10:52:31
	 */
	public static void handlerException(VolleyError error, CldKReturn errRes) {
		if (null != error) {
			if (null != error) {
				if (error.getCause() instanceof TimeoutException) {
					errRes.errCode = CldOlsErrCode.NET_TIMEOUT;
					errRes.errMsg = "���糬ʱ";
				} else if (error.getCause() instanceof AuthFailureError) {
					errRes.errCode = CldOlsErrCode.HTTP_REJECT;
					errRes.errMsg = "svr reject!";
				} else {
					errRes.errCode = CldOlsErrCode.NET_OTHER_ERR;
					errRes.errMsg = "�����쳣";
				}
			}
			error.printStackTrace();
			CldLog.d(TAG, error.getMessage());
		} else {
			CldLog.e(TAG, "error is null!");
		}
	}
	
	/**
	 * �����ܷ����쳣�����
	 * 
	 * @param error
	 *            �����ܷ��ش���
	 * @param errRes
	 *            �ӿ�ͨ�ûص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-9-11 ����10:52:31
	 */
	public static void handlerException(VolleyError error, CldSapReturn errRes) {
		if (null != error) {
			if (null != error) {
				if (error.getCause() instanceof TimeoutException) {
					errRes.errCode = CldOlsErrCode.NET_TIMEOUT;
					errRes.errMsg = "���糬ʱ";
				} else if (error.getCause() instanceof AuthFailureError) {
					errRes.errCode = CldOlsErrCode.HTTP_REJECT;
					errRes.errMsg = "svr reject!";
				} else {
					errRes.errCode = CldOlsErrCode.NET_OTHER_ERR;
					errRes.errMsg = "�����쳣";
				}
			}
			error.printStackTrace();
			CldLog.d(TAG, error.getMessage());
		} else {
			CldLog.e(TAG, "error is null!");
		}
	}

	/**
	 * ���߷�����������
	 * 
	 * @author Zhouls
	 * @date 2015-9-11 ����10:58:08
	 */
	public static class CldOlsErrCode {
		// ���� ����������� ��10001-10099��
		/** ����������� */
		public static final int NET_NO_CONNECTED = 10001;
		/** ���糬ʱ������ */
		public static final int NET_TIMEOUT = NET_NO_CONNECTED + 1;
		/** ���������쳣 */
		public static final int NET_OTHER_ERR = NET_TIMEOUT + 1;
		/** �����쳣 */
		public static final int PARSE_ERR = NET_OTHER_ERR + 1;
		/** ���ݷ����쳣 */
		public static final int DATA_RETURN_ERR = PARSE_ERR + 1;
		/** http 403 */
		public static final int HTTP_REJECT = DATA_RETURN_ERR + 1;
		// ҵ���߼�������루10100-10199��
		/** �������Ϸ� */
		public static final int PARAM_INBALID = 10100;
		// ҵ���߼�����10200-10500��
		/** δ��¼ */
		public static final int ACCOUT_NOT_LOGIN = 20001;
	}
}
