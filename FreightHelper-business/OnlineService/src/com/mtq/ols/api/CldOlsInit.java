/*
 * @Title CldOlsInit.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-2-12 ����5:32:28
 * @version 1.0
 */
package com.mtq.ols.api;

import com.mtq.ols.api.CldOlsBase.IInitListener;


/**
 * ��ʼ������
 * 
 * @author Zhouls
 * @date 2015-2-12 ����5:32:28
 */
public class CldOlsInit {
	/**
	 * ��ʼ�����߷������
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-1 ����2:09:02
	 */
	public void init(IInitListener listener) {
		updateConfig(listener);
		initKey(listener);
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-2-12 ����6:06:39
	 */
	private void initKey(final IInitListener listener) {
		/**
		 * ��ʼ���ʻ�ϵͳ��Կ
		 */
		CldKAccountAPI.getInstance().initKey(new ICldOlsInitListener() {
			@Override
			public void onInitReslut() {
				// ��ʼ��duid
				initDuid(listener);
				// ��ʼ��ʱ���
				initUtc();
			}
		});
		/**
		 * ��ʼ����Ϣϵͳ��Կ
		 */
		CldKMessageAPI.getInstance().initKey(null);
		/**
		 * ��ʼ��һ��ͨϵͳ��Կ
		 */
		//CldKCallNaviAPI.getInstance().initKey(null);
		
		/**
		 * ��ʼ��Ӧ��������Կ
		 */
		CldKAppCenterAPI.getInstance().initKey(null);
	}

	/**
	 * ��ʼ��duid
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-1 ����10:06:34
	 */
	private void initDuid(IInitListener listener) {
		CldKAccountAPI.getInstance().initDuid(listener);
	}

	/**
	 * ������������
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-1 ����10:50:09
	 */
	private void updateConfig(IInitListener listener) {
		CldKConfigAPI.getInstance().updateCofig(listener);
	}

	/**
	 * ��ʼ��ʱ���
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-1 ����1:01:12
	 */
	private void initUtc() {
		CldKAccountAPI.getInstance().initSvrTime();
	}

	/**
	 * ��ʼ���ӿڻص�
	 * 
	 * @author Zhouls
	 * @date 2015-3-1 ����4:56:38
	 */
	public static interface ICldOlsInitListener {
		public void onInitReslut();
	}
}
