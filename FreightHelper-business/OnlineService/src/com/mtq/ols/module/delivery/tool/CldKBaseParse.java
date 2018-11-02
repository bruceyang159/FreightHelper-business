/*
 * @Title CldKBaseParse.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-3-18 ����3:12:51
 * @version 1.0
 */
package com.mtq.ols.module.delivery.tool;

/**
 * ���������
 * 
 * @author Zhouls
 * @date 2015-3-18 ����3:12:51
 */
public class CldKBaseParse {
	/**
	 * 
	 * Э��㷵��ֵ����
	 * 
	 * @author Zhouls
	 * @date 2015-3-18 ����3:40:24
	 */
	public static class ProtBase {
		public int errcode;
		public String errmsg;
		public int errorcode;
		public String errormsg;

		public ProtBase() {
			errcode = -1;
			errmsg = "";
			errorcode = -1;
			errormsg = "";
		}

		public int getErrcode() {
			return errcode;
		}

		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}

		public int getErrorcode() {
			return errorcode;
		}

		public void setErrorcode(int errorcode) {
			this.errorcode = errorcode;
		}

		public String getErrormsg() {
			return errormsg;
		}

		public void setErrormsg(String errormsg) {
			this.errormsg = errormsg;
		}
	}

	/**
	 * 
	 * Э��㷵��ֵ����
	 * 
	 * @author Zhouls
	 * @date 2015-3-18 ����3:40:24
	 */
	public static class ProtBaseSpec {
		public int errorcode;
		public String errormsg;

		public ProtBaseSpec() {
			errorcode = -1;
			errormsg = "";
		}
	}

	/**
	 * 
	 * ��Կͨ�ý�����
	 * 
	 * @author Zhouls
	 * @date 2015-3-18 ����3:40:49
	 */
	public static class ProtKeyCode extends ProtBase {
		/** ����˷�����Կ */
		public String code;

		public ProtKeyCode() {
			code = "";
		}
	}
}
