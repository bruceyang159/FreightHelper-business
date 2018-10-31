/*
 * @Title CldOlsInitManager.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-11-30 ����9:42:36
 * @version 1.0
 */
package com.mtq.ols.tools.model;

import java.util.ArrayList;

import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;
import com.mtq.ols.tools.net.CldNetResponeListener;

/**
 * ��ʼ��ģ�������
 * 
 * @author Zhouls
 * @date 2015-11-30 ����9:42:36
 */
public class CldOlsInitManager {
	private static CldOlsInitManager manager;

	public static CldOlsInitManager getInstance() {
		if (null == manager) {
			manager = new CldOlsInitManager();
		}
		return manager;
	}

	private CldOlsInitManager() {

	}

	/**
	 * ע��ģ��
	 * 
	 * @param model
	 *            ģ��ģ��
	 * @return void
	 * @author Wenap
	 * @date 2015-6-2 ����5:44:35
	 */
	public void regist(CldOlsInitMod model) {
		if (model != null && !mModelNameList.contains(model.getModelName())) {
			mModelList.add(model);
			mModelNameList.add(model.getModelName());
		}
	}

	/**
	 * ��ע��ģ��
	 * 
	 * @param model
	 *            ģ��ģ��
	 * @return void
	 * @author Wenap
	 * @date 2015-6-2 ����5:44:35
	 */
	public void unRegist(CldOlsInitMod model) {
		if (model != null) {
			mModelList.remove(model);
			mModelNameList.remove(model.getModelName());
		}
	}

	/**
	 * ��ʼ��
	 * 
	 * @return �����루0���ɹ���������ʧ�ܣ�
	 * @author Wenap
	 * @date 2015-6-2 ����5:06:39
	 */
	public int init() {
		// ��ʼ������ע��ģ��
		for (CldOlsInitMod model : mModelList) {
			Object param = null;
			if (model.mInterface != null) {
				param = model.mInterface.getInitParams();
			}
			long utc = System.currentTimeMillis();
			int ret = model.init(param);
			utc = System.currentTimeMillis() - utc;
			if (0 != ret) {
				CldLog.e("init error! model name: " + model.getModelName()
						+ ", ret: " + ret);
				return ret;
			} else {
				CldLog.d("ols", model.getModelName() + ":" + utc + "ms");
			}
		}
		return 0;
	}

	/**
	 * ��ʼ�����߲���
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2016-1-19 ����5:37:26
	 */
	public void initOnlineData() {
		// ���߲��ַ���ʼǰ��������ģ��ص����� �����־
		CldHttpClient.setReponseListener(new CldNetResponeListener());
		for (CldOlsInitMod model : mModelList) {
			Object param = null;
			if (model.mInterface != null) {
				param = model.mInterface.getInitParams();
			}
			long utc = System.currentTimeMillis();
			model.initOnlineData(param);
			utc = System.currentTimeMillis() - utc;
			CldLog.d("ols_init", model.getModelName() + ":" + utc + "ms");
		}
	}

	private ArrayList<CldOlsInitMod> mModelList = new ArrayList<CldOlsInitMod>();
	private ArrayList<String> mModelNameList = new ArrayList<String>();
}
