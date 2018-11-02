/*
 * @Title CldSapKAParm.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:59
 * @version 1.0
 */
package com.mtq.ols.sap.bean;




/**
 * �ʻ�ϵͳ������
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:46:27
 */
public class CldSapKAParm {
	/**
	 * �û���Ϣ
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:46:59
	 */
	public static class CldUserInfo {
		/** ��¼�� */
		private String loginName;
		/** �û����� */
		private String userName;
		/** �û����� */
		private String userAlias;
		/** ���� */
		private String email;
		/** ������֤(��)״̬(0δ�� 1�� 2����) */
		private int emailBind;
		/** �ֻ� */
		private String mobile;
		/** �ֻ���֤(��)״̬(0δ�� 1�� 2����) */
		private int mobileBind;
		/** �Ա� */
		private int sex;
		/** QQ�� */
		private String qq;
		/** ֤������(1���֤ 2����֤ 3ѧ��֤ 4����) */
		private int cardType;
		/** ֤���� */
		private String cardCode;
		/** �û��ȼ� */
		private int userLevel;
		/** �Ƿ�VIP(0�� 1��) */
		private int vip;
		/** 1,���û�ע�ᣬ2���û���¼ */
		private int status;
		/** ע��ʱ�� */
		private long regTime;
		/** ע��IP */
		private String regIp;
		/** ע��ҵ��ID */
		private int regAppid;
		/** ����ʱ�� */
		private String updateTime;
		/** ���һ�ε�¼ʱ�� */
		private long lastLoginTime;
		/** ���һ�ε�¼ip */
		private String lastLoginIp;
		/** ���һ�ε�¼appid */
		private int lastLoginAppid;
		/** k������ */
		private int kBeans;
		/** ��ǰ���� */
		private String address;
		/** ͷ��·�� */
		private String photoPath;
		/** ��ʻ֤url */
		private String driving_licence;
		/** ������֤��Ϣ */
		private CldLicenceInfo licenceInfo;

		public CldUserInfo() {
			loginName = "";
			userName = "";
			userAlias = "";
			email = "";
			emailBind = -1;
			mobile = "";
			mobileBind = -1;
			sex = 1;
			qq = "";
			cardType = -1;
			cardCode = "";
			userLevel = 0;
			vip = -1;
			status = -1;
			regTime = 0;
			regIp = "";
			regAppid = -1;
			updateTime = "";
			lastLoginTime = 0;
			lastLoginIp = "";
			lastLoginAppid = -1;
			kBeans = 0;
			address = "";
			photoPath = "";
			driving_licence = "";
			licenceInfo = null;
		}
		
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhotoPath() {
			return photoPath;
		}

		public void setPhotoPath(String photoPath) {
			this.photoPath = photoPath;
		}

		public String getDriving_licence() {
			return driving_licence;
		}

		public void setDriving_licence(String driving_licence) {
			this.driving_licence = driving_licence;
		}

		public CldLicenceInfo getLicenceInfo() {
			return licenceInfo;
		}

		public void setLicenceInfo(CldLicenceInfo licenceInfo) {
			this.licenceInfo = licenceInfo;
		}

		/**
		 * Gets the login name.
		 * 
		 * @return the login name
		 */
		public String getLoginName() {
			return loginName;
		}

		/**
		 * Sets the login name.
		 * 
		 * @param loginName
		 *            the new login name
		 */
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		/**
		 * Gets the user name.
		 * 
		 * @return the user name
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * Sets the user name.
		 * 
		 * @param userName
		 *            the new user name
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}

		/**
		 * Gets the user alias.
		 * 
		 * @return the user alias
		 */
		public String getUserAlias() {
			return userAlias;
		}

		/**
		 * Sets the user alias.
		 * 
		 * @param userAlias
		 *            the new user alias
		 */
		public void setUserAlias(String userAlias) {
			this.userAlias = userAlias;
		}

		/**
		 * Gets the email.
		 * 
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * Sets the email.
		 * 
		 * @param email
		 *            the new email
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * Gets the email bind.
		 * 
		 * @return the email bind
		 */
		public int getEmailBind() {
			return emailBind;
		}

		/**
		 * Sets the email bind.
		 * 
		 * @param emailBind
		 *            the new email bind
		 */
		public void setEmailBind(int emailBind) {
			this.emailBind = emailBind;
		}

		/**
		 * Gets the mobile.
		 * 
		 * @return the mobile
		 */
		public String getMobile() {
			return mobile;
		}

		/**
		 * Sets the mobile.
		 * 
		 * @param mobile
		 *            the new mobile
		 */
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		/**
		 * Gets the mobile bind.
		 * 
		 * @return the mobile bind
		 */
		public int getMobileBind() {
			return mobileBind;
		}

		/**
		 * Sets the mobile bind.
		 * 
		 * @param mobileBind
		 *            the new mobile bind
		 */
		public void setMobileBind(int mobileBind) {
			this.mobileBind = mobileBind;
		}

		/**
		 * Gets the sex.
		 * 
		 * @return the sex
		 */
		public int getSex() {
			return sex;
		}

		/**
		 * Sets the sex.
		 * 
		 * @param sex
		 *            the new sex
		 */
		public void setSex(int sex) {
			this.sex = sex;
		}

		/**
		 * Gets the qq.
		 * 
		 * @return the qq
		 */
		public String getQq() {
			return qq;
		}

		/**
		 * Sets the qq.
		 * 
		 * @param qq
		 *            the new qq
		 */
		public void setQq(String qq) {
			this.qq = qq;
		}

		/**
		 * Gets the card type.
		 * 
		 * @return the card type
		 */
		public int getCardType() {
			return cardType;
		}

		/**
		 * Sets the card type.
		 * 
		 * @param cardType
		 *            the new card type
		 */
		public void setCardType(int cardType) {
			this.cardType = cardType;
		}

		/**
		 * Gets the card code.
		 * 
		 * @return the card code
		 */
		public String getCardCode() {
			return cardCode;
		}

		/**
		 * Sets the card code.
		 * 
		 * @param cardCode
		 *            the new card code
		 */
		public void setCardCode(String cardCode) {
			this.cardCode = cardCode;
		}

		/**
		 * Gets the user level.
		 * 
		 * @return the user level
		 */
		public int getUserLevel() {
			return userLevel;
		}

		/**
		 * Sets the user level.
		 * 
		 * @param userLevel
		 *            the new user level
		 */
		public void setUserLevel(int userLevel) {
			this.userLevel = userLevel;
		}

		/**
		 * Gets the vip.
		 * 
		 * @return the vip
		 */
		public int getVip() {
			return vip;
		}

		/**
		 * Sets the vip.
		 * 
		 * @param vip
		 *            the new vip
		 */
		public void setVip(int vip) {
			this.vip = vip;
		}

		/**
		 * Gets the status.
		 * 
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}

		/**
		 * Sets the status.
		 * 
		 * @param status
		 *            the new status
		 */
		public void setStatus(int status) {
			this.status = status;
		}

		/**
		 * Gets the reg time.
		 * 
		 * @return the reg time
		 */
		public long getRegTime() {
			return regTime;
		}

		/**
		 * Sets the reg time.
		 * 
		 * @param regTime
		 *            the new reg time
		 */
		public void setRegTime(long regTime) {
			this.regTime = regTime;
		}

		/**
		 * Gets the reg ip.
		 * 
		 * @return the reg ip
		 */
		public String getRegIp() {
			return regIp;
		}

		/**
		 * Sets the reg ip.
		 * 
		 * @param regIp
		 *            the new reg ip
		 */
		public void setRegIp(String regIp) {
			this.regIp = regIp;
		}

		/**
		 * Gets the reg appid.
		 * 
		 * @return the reg appid
		 */
		public int getRegAppid() {
			return regAppid;
		}

		/**
		 * Sets the reg appid.
		 * 
		 * @param regAppid
		 *            the new reg appid
		 */
		public void setRegAppid(int regAppid) {
			this.regAppid = regAppid;
		}

		/**
		 * Gets the update time.
		 * 
		 * @return the update time
		 */
		public String getUpdateTime() {
			return updateTime;
		}

		/**
		 * Sets the update time.
		 * 
		 * @param updateTime
		 *            the new update time
		 */
		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

		/**
		 * Gets the last login time.
		 * 
		 * @return the last login time
		 */
		public long getLastLoginTime() {
			return lastLoginTime;
		}

		/**
		 * Sets the last login time.
		 * 
		 * @param lastLoginTime
		 *            the new last login time
		 */
		public void setLastLoginTime(long lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}

		/**
		 * Gets the last login ip.
		 * 
		 * @return the last login ip
		 */
		public String getLastLoginIp() {
			return lastLoginIp;
		}

		/**
		 * Sets the last login ip.
		 * 
		 * @param lastLoginIp
		 *            the new last login ip
		 */
		public void setLastLoginIp(String lastLoginIp) {
			this.lastLoginIp = lastLoginIp;
		}

		/**
		 * Gets the last login appid.
		 * 
		 * @return the last login appid
		 */
		public int getLastLoginAppid() {
			return lastLoginAppid;
		}

		/**
		 * Sets the last login appid.
		 * 
		 * @param lastLoginAppid
		 *            the new last login appid
		 */
		public void setLastLoginAppid(int lastLoginAppid) {
			this.lastLoginAppid = lastLoginAppid;
		}

		/**
		 * Gets the k beans.
		 * 
		 * @return the k beans
		 */
		public int getkBeans() {
			return kBeans;
		}

		/**
		 * Sets the k beans.
		 * 
		 * @param kBeans
		 *            the new k beans
		 */
		public void setkBeans(int kBeans) {
			this.kBeans = kBeans;
		}
	}
	
	/**
	 * 
	 * ��ʻ֤��Ϣ
	 * @author Zhouls
	 * @date 2016-5-6 ����3:10:52
	 */
	public static class CldLicenceInfo {
		/** ��ʻ֤����*/
		public String licenceName;
		/** ��ʻ֤����*/
		public String licenceNum;
		/** ��ʻ֤���ƺ�*/
		public String vehicleNum;
		/** ��ʻ֤��������*/
		public String vehicleType;
		/** ˾����Ϣ���״̬ 0 δ�ύ  1 ����� 2����֤ 3��֤ʧ��*/
		public int status;
		/** ˾����Ϣ���ʧ��ԭ��*/
		public String reason;

		public CldLicenceInfo() {
			licenceName = "";
			licenceNum = "";
			vehicleNum = "";
			vehicleType = "";
			status = -1;
			reason = "";
		}
	}
}
