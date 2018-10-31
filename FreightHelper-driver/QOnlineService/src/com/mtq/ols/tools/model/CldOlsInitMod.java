/*
 * @Title CldOlsModel.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-11-30 ����9:37:20
 * @version 1.0
 */
package com.mtq.ols.tools.model;

/**
 * ģ���ʼ��ģ��
 * 
 * @author Zhouls
 * @date 2015-11-30 ����9:37:20
 */
public abstract class CldOlsInitMod {

	/**
	 * ��ʼ��
	 * 
	 * @param param
	 *            ��ʼ����������������null
	 * @return �����루0���ɹ�����������ģ�鶨�壩
	 * @return int
	 * @author Wenap
	 * @date 2015-6-1 ����10:52:02
	 */
	public abstract int init(Object param);

	/**
	 * ��ʼ�����߲��ֺ�ʱ����
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2016-1-19 ����5:36:32
	 */
	public abstract void initOnlineData(Object param);

	/**
	 * ����ģ����Ҫʵ�ֵĽӿ�
	 * 
	 * @author Wenap
	 * @date 2015-6-4 ����11:38:15
	 */
	public abstract interface ICldOlsModelInterface {

		/**
		 * ��ȡģ���ʼ������������ģ����Ҫʵ��
		 * 
		 * @return ��ʼ������
		 * @return Object
		 * @author Wenap
		 * @date 2015-6-4 ����11:36:33
		 */
		public abstract Object getInitParams();

	}

	/**
	 * ��ȡģ������
	 * 
	 * @return ģ������
	 * @return String
	 * @author Wenap
	 * @date 2015-6-10 ����11:32:11
	 */
	public String getModelName() {
		return this.getClass().getName();
	}

	/**
	 * �Ƿ��Ѿ���ʼ����ģ���ʼ��������ʼ��ʱ��Ҫ�ж�
	 */
	protected boolean bInit = false;

	/**
	 * ģ��ӿ�
	 */
	public ICldOlsModelInterface mInterface = null;
}
