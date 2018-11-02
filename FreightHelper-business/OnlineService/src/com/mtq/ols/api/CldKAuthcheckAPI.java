/*
 * @Title CldKAuthcheckAPI.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.api;

import com.cld.utils.CldPackage;
import com.mtq.ols.bll.CldKAuthcheck;
import com.mtq.ols.tools.CldSapReturn;



/**
 * ��Ȩ���ģ�飬�ṩ��Ȩ����
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:07:24
 */
public class CldKAuthcheckAPI {
	/** ��Ȩ�ص���������ʼ��ʱ����һ�� */
	private ICldKAuthcheckListener listener;

	private static CldKAuthcheckAPI cldKAuthcheckAPI;

	private CldKAuthcheckAPI() {
	}

	public static CldKAuthcheckAPI getInstance() {
		if (cldKAuthcheckAPI == null)
			cldKAuthcheckAPI = new CldKAuthcheckAPI();
		return cldKAuthcheckAPI;
	}

	/**
	 * ���ûص�����
	 * 
	 * @param listener
	 *            ��Ȩ�ص�����
	 * @return (0 ���óɹ���-1��������ʧ��)
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:07:39
	 */
	public int setCldKAuthcheckListener(ICldKAuthcheckListener listener) {
		if (null == this.listener) {
			this.listener = listener;
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * ��Ȩ
	 * 
	 * @param key
	 *            ������Կ���ɿ������ڿ����¿�����ƽ̨�������Կ��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:08:01
	 */
	public void authCheck(final String key) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAuthcheck.getInstance().authCheck(
						key, CldPackage.getSafeCode());
				if (null != listener) {
					listener.onAuthCheckResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��Ȩ�ص�
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:08:15
	 */
	public interface ICldKAuthcheckListener {

		/**
		 * ��Ȩ�ص�
		 * 
		 * @param errCode
		 *            ��0Ϊ�ɹ���1Ϊ�������ݸ�ʽ���Ϸ���2Ϊ��ȫ���������Կ��ƥ�䣬 3Ϊ������Կ�����ڻ���ɾ����4Ϊ�û�δ��ˣ�5
		 *            Ϊ��ȫ����Ȩ�޷��ʣ�6Ϊ���ô������100Ϊϵͳ����
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:08:27
		 */
		public void onAuthCheckResult(int errCode);
	}
}
