/*
 * @Title CldKAccountParse.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-3-21 ����2:41:09
 * @version 1.0
 */
package com.mtq.ols.sap.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;

import com.cld.gson.JsonArray;
import com.cld.gson.JsonObject;
import com.mtq.ols.sap.bean.CldSapKAParm.CldLicenceInfo;
import com.mtq.ols.sap.bean.CldSapKAParm.CldUserInfo;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfo;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.tools.CldSapParser;

/**
 * �˻�ϵͳ������
 * 
 * @author Zhouls
 * @date 2015-3-21 ����2:41:09
 */
public class CldKAccountParse {

	/**
	 * �豸ע��
	 * 
	 * @author Zhouls
	 * @date 2015-3-25 ����4:04:11
	 */
	public static class ProtDeviceInfo extends ProtBase {
		/** �豸���� */
		private String deviceksn;
		/** �豸id */
		private long duid;

		public ProtDeviceInfo() {
			deviceksn = "";
			duid = 0;
		}

		public String getDeviceksn() {
			return deviceksn;
		}

		public void setDeviceksn(String deviceksn) {
			this.deviceksn = deviceksn;
		}

		public long getDuid() {
			return duid;
		}

		public void setDuid(long duid) {
			this.duid = duid;
		}
	}

	/**
	 * 
	 * Kuid ����
	 * 
	 * @author Zhouls
	 * @date 2015-3-25 ����4:21:07
	 */
	public static class ProtUserKuid extends ProtBase {
		/** �û���Kuid */
		private long kuid;

		public ProtUserKuid() {
			kuid = 0;
		}

		public long getKuid() {
			return kuid;
		}

		public void setKuid(long kuid) {
			this.kuid = kuid;
		}
	}

	/**
	 * 
	 * ע�᷵��
	 * 
	 * @author Zhouls
	 * @date 2015-3-25 ����4:22:24
	 */
	public static class ProtUserRegister extends ProtBase {
		/** �û���Kuid */
		private long kuid;
		/** ��¼�� */
		private String loginname;

		public ProtUserRegister() {
			kuid = 0;
			loginname = "";
		}

		public long getKuid() {
			return kuid;
		}

		public void setKuid(long kuid) {
			this.kuid = kuid;
		}

		public String getLoginname() {
			return loginname;
		}

		public void setLoginname(String loginname) {
			this.loginname = loginname;
		}
	}

	/**
	 * 
	 * ��¼����
	 * 
	 * @author Zhouls
	 * @date 2015-3-25 ����4:23:42
	 */
	public static class ProtLogin extends ProtBase {
		/** ��¼����kuid */
		private long kuid;
		/** ��¼����session */
		private String session;
		/** ��¼�� */
		private String loginname;
		/** �û��� */
		private String username;
		/** ���� */
		private String useralias;
		/** ���� */
		private String email;
		/** �Ƿ������ */
		private int emailbind;
		/** �ֻ��� */
		private String mobile;
		/** �Ƿ���ֻ��� */
		private int mobilebind;
		/** �Ա� */
		private int sex;
		/** �Ƿ�VIP(0�� 1��) */
		private int vip;
		/** �û��ȼ� */
		private int userlevel;
		/** ע��ʱ�� */
		private long regtime;
		/** ���һ�ε�¼ʱ�� */
		private long lastlogintime;
		/** 1,���û�ע�ᣬ2���û���¼ */
		private int status;

		public void protParse(CldUserInfo userInfo) {
			userInfo.setLoginName(loginname);
			userInfo.setUserName(username);
			userInfo.setUserAlias(useralias);
			userInfo.setEmail(email);
			userInfo.setEmailBind(emailbind);
			userInfo.setMobile(mobile);
			userInfo.setMobileBind(mobilebind);
			userInfo.setSex(sex);
			userInfo.setVip(vip);
			userInfo.setUserLevel(userlevel);
			userInfo.setRegTime(regtime);
			userInfo.setLastLoginTime(lastlogintime);
			userInfo.setStatus(status);
		}

		public long getKuid() {
			return kuid;
		}

		public void setKuid(long kuid) {
			this.kuid = kuid;
		}

		public String getSession() {
			return session;
		}

		public void setSession(String session) {
			this.session = session;
		}

		public String getLoginname() {
			return loginname;
		}

		public void setLoginname(String loginname) {
			this.loginname = loginname;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUseralias() {
			return useralias;
		}

		public void setUseralias(String useralias) {
			this.useralias = useralias;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getEmailbind() {
			return emailbind;
		}

		public void setEmailbind(int emailbind) {
			this.emailbind = emailbind;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public int getMobilebind() {
			return mobilebind;
		}

		public void setMobilebind(int mobilebind) {
			this.mobilebind = mobilebind;
		}

		public int getSex() {
			return sex;
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		/** @return the vip */
		public int getVip() {
			return vip;
		}

		/**
		 * @param vip
		 *            the vip to set
		 */
		public void setVip(int vip) {
			this.vip = vip;
		}

		/** @return the userlevel */
		public int getUserlevel() {
			return userlevel;
		}

		/**
		 * @param userlevel
		 *            the userlevel to set
		 */
		public void setUserlevel(int userlevel) {
			this.userlevel = userlevel;
		}

		/** @return the regtime */
		public long getRegtime() {
			return regtime;
		}

		/**
		 * @param regtime
		 *            the regtime to set
		 */
		public void setRegtime(long regtime) {
			this.regtime = regtime;
		}

		/** @return the lastlogintime */
		public long getLastlogintime() {
			return lastlogintime;
		}

		/**
		 * @param lastlogintime
		 *            the lastlogintime to set
		 */
		public void setLastlogintime(long lastlogintime) {
			this.lastlogintime = lastlogintime;
		}

		/** @return the status */
		public int getStatus() {
			return status;
		}

		/**
		 * @param status
		 *            the status to set
		 */
		public void setStatus(int status) {
			this.status = status;
		}
	}

	/**
	 * 
	 * ��ȡ�û���Ϣ
	 * 
	 * @author Zhouls
	 * @date 2015-3-25 ����4:27:25
	 */
	public static class ProtUserInfo extends ProtBase {
		private ProtData data;

		public ProtLicenceInfo licence_info;

		@SuppressLint("SimpleDateFormat")
		public void protParse(CldUserInfo userInfo) {
			if (null != data) {
				userInfo.setLoginName(data.getLoginname());
				userInfo.setUserName(data.getUsername());
				userInfo.setUserAlias(data.getUseralias());
				userInfo.setEmail(data.getEmail());
				userInfo.setEmailBind(data.getEmailbind());
				userInfo.setMobile(data.getMobile());
				userInfo.setMobileBind(data.getMobilebind());
				userInfo.setSex(data.getSex());
				userInfo.setQq(data.getQq());
				userInfo.setCardType(data.getCardtype());
				userInfo.setCardCode(data.getCardcode());
				userInfo.setUserLevel(data.getUserlevel());
				userInfo.setVip(data.getVip());
				userInfo.setStatus(data.getStatus());
				try {
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date;
					date = format.parse(data.getRegtime());
					userInfo.setRegTime(date.getTime() / 1000);
					date = format.parse(data.getLastlogintime());
					userInfo.setLastLoginTime(date.getTime() / 1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				userInfo.setRegIp(data.getRegip());
				userInfo.setRegAppid(data.getRegappid());
				userInfo.setUpdateTime(data.getUpdatetime());
				userInfo.setLastLoginIp(data.getLastloginip());
				userInfo.setLastLoginAppid(data.getLastloginappid());
				userInfo.setPhotoPath(data.photo);
				userInfo.setAddress(data.address);
				userInfo.setDriving_licence(data.driving_licence);
				CldLicenceInfo iInfo = new CldLicenceInfo();
				if (null != licence_info) {
					iInfo.licenceName = licence_info.dl_name;
					iInfo.licenceNum = licence_info.dl_num;
					iInfo.vehicleNum = licence_info.vehicle_plate_num;
					iInfo.vehicleType = licence_info.vehicle_type;
					iInfo.status = licence_info.status;
					iInfo.reason = licence_info.reason;
				}
				userInfo.setLicenceInfo(iInfo);
			}
		}

		public static class ProtData {
			private String loginname;
			private String username;
			private String useralias;
			private String email;
			private int emailbind;
			private String mobile;
			private int mobilebind;
			private int sex;
			private String qq;
			private int cardtype;
			private String cardcode;
			private int userlevel;
			private int vip;
			private int status;
			private String regip;
			private String regtime;
			private int regappid;
			private String updatetime;
			private String lastlogintime;
			private String lastloginip;
			private int lastloginappid;
			public String photo;
			public String address;
			public String driving_licence;

			public String getPhoto() {
				return photo;
			}

			public void setPhoto(String photo) {
				this.photo = photo;
			}

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getDriving_licence() {
				return driving_licence;
			}

			public void setDriving_licence(String driving_licence) {
				this.driving_licence = driving_licence;
			}

			public String getLoginname() {
				return loginname;
			}

			public void setLoginname(String loginname) {
				this.loginname = loginname;
			}

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getUseralias() {
				return useralias;
			}

			public void setUseralias(String useralias) {
				this.useralias = useralias;
			}

			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}

			public int getEmailbind() {
				return emailbind;
			}

			public void setEmailbind(int emailbind) {
				this.emailbind = emailbind;
			}

			public String getMobile() {
				return mobile;
			}

			public void setMobile(String mobile) {
				this.mobile = mobile;
			}

			public int getMobilebind() {
				return mobilebind;
			}

			public void setMobilebind(int mobilebind) {
				this.mobilebind = mobilebind;
			}

			public int getSex() {
				return sex;
			}

			public void setSex(int sex) {
				this.sex = sex;
			}

			public String getQq() {
				return qq;
			}

			public void setQq(String qq) {
				this.qq = qq;
			}

			public int getCardtype() {
				return cardtype;
			}

			public void setCardtype(int cardtype) {
				this.cardtype = cardtype;
			}

			public String getCardcode() {
				return cardcode;
			}

			public void setCardcode(String cardcode) {
				this.cardcode = cardcode;
			}

			public int getUserlevel() {
				return userlevel;
			}

			public void setUserlevel(int userlevel) {
				this.userlevel = userlevel;
			}

			public int getVip() {
				return vip;
			}

			public void setVip(int vip) {
				this.vip = vip;
			}

			public int getStatus() {
				return status;
			}

			public void setStatus(int status) {
				this.status = status;
			}

			public String getRegtime() {
				return regtime;
			}

			public void setRegtime(String regtime) {
				this.regtime = regtime;
			}

			public String getUpdatetime() {
				return updatetime;
			}

			public void setUpdatetime(String updatetime) {
				this.updatetime = updatetime;
			}

			public String getLastlogintime() {
				return lastlogintime;
			}

			public void setLastlogintime(String lastlogintime) {
				this.lastlogintime = lastlogintime;
			}

			public String getLastloginip() {
				return lastloginip;
			}

			public void setLastloginip(String lastloginip) {
				this.lastloginip = lastloginip;
			}

			public int getLastloginappid() {
				return lastloginappid;
			}

			public void setLastloginappid(int lastloginappid) {
				this.lastloginappid = lastloginappid;
			}

			public String getRegip() {
				return regip;
			}

			public void setRegip(String regip) {
				this.regip = regip;
			}

			public int getRegappid() {
				return regappid;
			}

			public void setRegappid(int regappid) {
				this.regappid = regappid;
			}
		}

		public ProtData getData() {
			return data;
		}

		public void setData(ProtData data) {
			this.data = data;
		}
	}

	/**
	 * 
	 * ʱ�������
	 * 
	 * @author Zhouls
	 * @date 2015-4-7 ����2:40:02
	 */
	public static class ProtSvrTime extends ProtBase {
		private ProtData data;

		public static class ProtData {
			/** �����ʱ��� */
			private long server_time;

			public long getServer_time() {
				return server_time;
			}

			public void setServer_time(long server_time) {
				this.server_time = server_time;
			}
		}

		public ProtData getData() {
			return data;
		}

		public void setData(ProtData data) {
			this.data = data;
		}
	}

	public static class ProtLicenceInfo {
		public String dl_name;
		public String dl_num;
		public String vehicle_plate_num;
		public String vehicle_type;
		public int status;
		public String reason;
	}

	/**
	 * 
	 * ��ά��ɨ�����
	 * 
	 * @author Zhouls
	 * @date 2015-4-7 ����2:40:22
	 */
	public static class ProtQrCode extends ProtBase {
		private String guid;
		private String create_time;
		private String imgdata;

		public String getGuid() {
			return guid;
		}

		public void setGuid(String guid) {
			this.guid = guid;
		}

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public String getImgdata() {
			return imgdata;
		}

		public void setImgdata(String imgdata) {
			this.imgdata = imgdata;
		}
	}
}
