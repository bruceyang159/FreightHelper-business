/*
 * @Title CldOlsInterface.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2016-1-4 ����2:50:09
 * @version 1.0
 */
package com.mtq.ols.module.delivery.tool;

/**
 * �ӿ���
 * 
 * @author Zhouls
 * @date 2016-1-4 ����2:50:09
 */
public class CldOlsInterface {
	/**
	 * ֻ���ش�����ص�
	 * 
	 * @author Zhouls
	 * @date 2015-3-4 ����4:02:01
	 */
	public static interface ICldResultListener {
		public void onGetResult(int errCode);
	}

}
