/*
 * @Title CldSapKAccount.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;
import android.util.Base64;

import com.cld.log.CldLog;
import com.cld.ols.tools.CldAescrpy;
import com.mtq.ols.api.CldKServiceAPI;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;

/**
 * �ʻ�ϵͳЭ��ӿ�
 * 
 * @author Zhouls
 * @date 2014-8-15 ����3:02:07
 */
public class CldSapKAccount {

	/** �״�����. */
	public static String keyCode;
	/** �����������. */
	private final static int APIVER = 1;
	/** The APPID. */
	public static int APPID;
	/** The RSCHARSET. */
	private final static int RSCHARSET = 1;
	/** The RSFORMAT. */
	private final static int RSFORMAT = 1;
	/** The UMSAVER. */
	private final static int UMSAVER = 2;
	/** The ENCRYPT. */
	private final static int ENCRYPT = 1;
	/** ������ */
	public static int CID;
	/** ����汾�� */
	public static String PROVER;

	/**
	 * ��ȡ�ʻ�ϵͳ��Կ(201)
	 * 
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-3-13 ����12:20:53
	 */
	public static CldSapReturn initKeyCode() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		String key;
		if (CldBllUtil.getInstance().isTestVersion()) {
			// ���԰汾
			key = "D0E484FCA2BE6038D170DFACC6141DA7";
		} else {
			// ��ʽ�汾
			key = "1F42AF9B4AE3DDB194BBF00A14CC2DC7";
		}
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_get_code.php", key);
		return errRes;
	}

	/**
	 * �豸ע��(202)
	 * 
	 * @param deviceCode
	 *            �豸��ʾ��
	 * @param deviceName
	 *            �豸����
	 * @param osType
	 *            �豸[ϵͳ]����(1Android 2IOS 3WP 4WINCE��5δ֪)
	 * @param apptype
	 *            1 CM 5����
	 * @param osVer
	 *            �豸[ϵͳ]�汾
	 * @param model
	 *            �豸�ͺ�
	 * @param macWifi
	 *            Wifi Mac��ַ
	 * @param macBlue
	 *            Blue Mac��ַ
	 * @param imei
	 *            Imei
	 * @param simNo
	 *            SIM����
	 * @param appVer
	 *            Ӧ�õ�ǰ�汾
	 * @param deviceSn
	 *            �豸������
	 * @param naviVer
	 *            �����汾
	 * @param mapVer
	 *            ��ͼ�汾
	 * @return CldSapReturn (0,402, 301��306)
	 * @author Zhouls
	 * @date 2015-1-5 ����5:41:47
	 */
	public static CldSapReturn deviceRegister(String deviceCode,
			String deviceName, int osType, int apptype, String osVer,
			String model, String macWifi, String macBlue, String imei,
			String simNo, String appVer, String deviceSn, String naviVer,
			String mapVer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("apptype", apptype);
		map.put("devicecode", deviceCode);
		map.put("devicename", deviceName);
		map.put("ostype", osType);
		map.put("rsformat", RSFORMAT);
		map.put("rscharset", RSCHARSET);
		map.put("umsaver", UMSAVER);
		map.put("cid", CID);
		CldSapParser.putStringToMap(map, "appver", appVer);
		CldSapParser.putStringToMap(map, "devicesn", deviceSn);
		CldSapParser.putStringToMap(map, "imei", imei);
		CldSapParser.putStringToMap(map, "model", model);
		CldSapParser.putStringToMap(map, "macwifi", macWifi);
		CldSapParser.putStringToMap(map, "macblue", macBlue);
		CldSapParser.putStringToMap(map, "mapver", mapVer);
		CldSapParser.putStringToMap(map, "naviver", naviVer);
		CldSapParser.putStringToMap(map, "osver", osVer);
		CldSapParser.putStringToMap(map, "simno", simNo);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_reg_device.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �����豸��Ϣ��203��
	 * 
	 * @param duid
	 *            �豸id
	 * @param deviceName
	 *            �豸����
	 * @param osType
	 *            �豸[ϵͳ]����(1Android 2IOS 3WP 4WINCE��5δ֪)
	 * @param apptype
	 *            1 CM 5����
	 * @param osVer
	 *            �豸[ϵͳ]�汾
	 * @param model
	 *            �豸�ͺ�
	 * @param macWifi
	 *            Wifi Mac��ַ
	 * @param macBlue
	 *            Blue Mac��ַ
	 * @param imei
	 *            Imei
	 * @param simNo
	 *            SIM����
	 * @param appVer
	 *            Ӧ�õ�ǰ�汾
	 * @param deviceSn
	 *            �豸������
	 * @param naviVer
	 *            �����汾
	 * @param mapVer
	 *            ��ͼ�汾
	 * @return CldSapReturn (0,402)
	 * @author Zhouls
	 * @date 2014-10-8 ����11:24:42
	 */
	public static CldSapReturn updateDeviceInfo(long duid, String deviceName,
			int osType, int apptype, String osVer, String model,
			String macWifi, String macBlue, String imei, String simNo,
			String appVer, String deviceSn, String naviVer, String mapVer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("apptype", apptype);
		map.put("duid", duid);
		map.put("devicename", deviceName);
		map.put("ostype", osType);
		map.put("rsformat", RSFORMAT);
		map.put("rscharset", RSCHARSET);
		map.put("umsaver", UMSAVER);
		map.put("cid", CID);
		CldSapParser.putStringToMap(map, "appver", appVer);
		CldSapParser.putStringToMap(map, "devicesn", deviceSn);
		CldSapParser.putStringToMap(map, "imei", imei);
		CldSapParser.putStringToMap(map, "model", model);
		CldSapParser.putStringToMap(map, "macwifi", macWifi);
		CldSapParser.putStringToMap(map, "macblue", macBlue);
		CldSapParser.putStringToMap(map, "mapver", mapVer);
		CldSapParser.putStringToMap(map, "naviver", naviVer);
		CldSapParser.putStringToMap(map, "osver", osVer);
		CldSapParser.putStringToMap(map, "simno", simNo);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_update_device_info.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �Ƿ�����ע���û�(204)
	 * 
	 * @param loginName
	 *            �绰���룬�û���������
	 * @param timeStamp
	 *            ʱ���
	 * @return errCode (0,101,401,402,910) int
	 * @author Zhouls
	 * @date 2014-8-18 ����3:56:58
	 */
	public static CldSapReturn isRegisterUser(String loginName, long timeStamp) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("loginname", loginName);
		map.put("timestamp", timeStamp);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_is_reg_user.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ͨ�����ж���ע��(205)
	 * 
	 * @param guid
	 *            ���ű�ʶ��
	 * @param ip
	 *            ע��IP
	 * @return errCode(0,101,201,401,402,503,911) int
	 * @author Zhouls
	 * @date 2014-8-22 ����11:31:53
	 */
	public static CldSapReturn registerBySms(String guid, String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("guid", guid);
		map.put("cid", CID);
		map.put("prover", PROVER);
		CldSapParser.putStringToMap(map, "ip", ip);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_reg_by_send_sms.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��¼(206)
	 * 
	 * @param loginname
	 *            �û���¼������¼�����ֻ��š����䣩
	 * @param loginpwd
	 *            ��¼����
	 * @param bussinessid
	 *            ҵ�����
	 * @param pwdtype
	 *            ��������,1��ͨ��¼���루Ĭ�ϣ�,2��ݵ�¼����,
	 * @param timestamp
	 *            ʱ���
	 * @param ip
	 *            ��¼IP
	 * @param duid
	 *            �豸id
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����4:08:04
	 */
	public static CldSapReturn login(String loginname, String loginpwd,
			int bussinessid, int pwdtype, long timestamp, String ip, long duid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("loginname", loginname);
		map.put("loginpwd", loginpwd);
		map.put("bussinessid", bussinessid);
		map.put("pwdtype", pwdtype);
		map.put("timestamp", timestamp);
		map.put("duid", duid);
		CldSapParser.putStringToMap(map, "ip", ip);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_login_user.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ע��(207)
	 * 
	 * @param kuid
	 *            ��¼���ص�kuid
	 * @param session
	 *            ��¼���ص�session
	 * @param bussinessid
	 *            ��1�� ҵ�����
	 * @return errCode(0,401,402 500��501��504��505) int
	 * @author Zhouls
	 * @date 2014-9-1 ����3:05:00
	 */
	public static CldSapReturn loginOut(long kuid, String session,
			int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("kuid", kuid);
		map.put("session", session);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_logout_user.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡ�û���Ϣ(208)
	 * 
	 * @param kuid
	 *            �û�kuid
	 * @param session
	 *            �û�session
	 * @param bussinessid
	 *            ҵ�����
	 * @return errCode(0,108,401,402 ,500��501��504]) int
	 * @author Zhouls
	 * @date 2014-8-20 ����10:33:36
	 */
	public static CldSapReturn getUserInfo(long kuid, String session,
			int bussinessid, int flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("flag", flag);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_get_user_info.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �����û���Ϣ�ӿ�(209)
	 * 
	 * @param kuid
	 *            �û�KUID(�ش�)
	 * @param session
	 *            ��¼Session���ش���
	 * @param bussinessid
	 *            ҵ�����
	 * @param username
	 *            �û�����
	 * @param useralias
	 *            �û�����
	 * @param email
	 *            ����
	 * @param mobile
	 *            �ֻ�
	 * @param sex
	 *            �Ա�
	 * @param cardtype
	 *            ֤������
	 * @param cardcode
	 *            ֤����
	 * @param qq
	 *            QQ��
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-2 ����3:08:57
	 */
	@SuppressWarnings("deprecation")
	public static CldSapReturn updateUserInfo(long kuid, String session,
			int bussinessid, String username, String useralias, String email,
			String mobile, String sex, String address, int cardtype,
			String cardcode, String qq, byte[] photoData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("encrypt", ENCRYPT);
		map.put("kuid", kuid);
		map.put("session", session);

		if ((photoData != null) && (photoData.length > 0)) {
			String strPhoto = Base64.encodeToString(photoData, 0);
			if (!TextUtils.isEmpty(strPhoto)) {
				CldLog.d("ols",
						photoData.length + ",base64:" + strPhoto.length());
			}
			map.put("photo", strPhoto);
			map.put("suffix", "jpg");
		}

		CldSapParser.putStringToMap(map, "username", username);
		CldSapParser.putStringToMap(map, "useralias", useralias);
		CldSapParser.putStringToMap(map, "email", 
			      CldAescrpy.encrypt(CldSapUtil.decodeKey(keyCode), email));
		CldSapParser.putStringToMap(map, "mobile", mobile);
		CldSapParser.putStringToMap(map, "cardcode", 
			      CldAescrpy.encrypt(CldSapUtil.decodeKey(keyCode), cardcode));
		CldSapParser.putStringToMap(map, "qq", qq);
		CldSapParser.putStringToMap(map, "sex", 
		        CldAescrpy.encrypt(CldSapUtil.decodeKey(keyCode), sex));
		CldSapParser.putStringToMap(map, "address", address);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);
		CldSapParser.putIntToMap(map, "cardtype", cardtype);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_update_user_info.php",
				CldSapUtil.decodeKey(keyCode));
		errRes.jsonPost = URLEncoder.encode(errRes.jsonPost);
		return errRes;
	}

	/**
	 * �ϱ��Ǽ��û���֤
	 * 
	 * @param kuid
	 * @param session
	 * @param bussinessid
	 * @param driving_licence
	 * @param vehicle_licence
	 * @return
	 * @return CldKReturn
	 * @author Zhouls
	 * @date 2016-5-6 ����3:20:21
	 */
	@SuppressWarnings("deprecation")
	public static CldSapReturn uploadStarAuth(long kuid, String session,
			int bussinessid, byte[] driving_licence, byte[] vehicle_licence) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("encrypt", ENCRYPT);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("bussinessid", bussinessid);
		String strPhoto = Base64
				.encodeToString(driving_licence, Base64.DEFAULT);
		map.put("driving_licence", strPhoto);
		strPhoto = Base64.encodeToString(vehicle_licence, Base64.DEFAULT);
		map.put("vehicle_licence", strPhoto);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_up_star_auth.php",
				CldSapUtil.decodeKey(keyCode));
		errRes.jsonPost = URLEncoder.encode(errRes.jsonPost);
		return errRes;
	}

	/**
	 * ��ݵ�¼(210)
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ��֤��
	 * @param fastloginpwd
	 *            �ն����ɿ�ݵ�¼����
	 * @param loginname
	 *            �û���¼��,����Ĭ��M+mobile,ȷ��Ψһ
	 * @param duid
	 *            �豸Ψһ��ʶ
	 * @param bussinessid
	 *            ҵ��id
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����8:47:46
	 */
	public static CldSapReturn fastLogin(String mobile, String verifycode,
			String fastloginpwd, String loginname, long duid, int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("duid", duid);
		map.put("mobile", mobile);
		map.put("verifycode", verifycode);
		map.put("fastloginpwd", fastloginpwd);
		// ��loginname�������ܻᵼ�¿�ݵ�¼ʱ�����û����Ѵ��ڵ����⡣
		// map.put("loginname", loginname);
		map.put("bussinessid", bussinessid);
		map.put("cid", CID);
		map.put("prover", PROVER);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_reg_fastlogin.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡ�ֻ���֤��(211)
	 * 
	 * @param mobile
	 *            �绰����
	 * @param bussinessCode
	 *            ҵ������ 101ע�� 102�� 103�İ� 104��� 105��������106��ݵ�¼
	 * @param timeStamp
	 *            ʱ���
	 * @param kuid
	 *            �û�kuid(102��103��104ʱΪ���裬�����ϲ���)
	 * @param session
	 *            ��¼Session(102��103��104ʱΪ���裬�����ϲ���)
	 * @param bussinessid
	 *            ҵ�����(102��103��104ʱΪ���裬�����ϲ���)
	 * @param oldmobile
	 *            103 �ش�
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-29 ����3:44:11
	 */
	public static CldSapReturn getVerifyCode(String mobile, int bussinessCode,
			long timeStamp, long kuid, String session, int bussinessid,
			String oldmobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("mobile", mobile);
		map.put("timestamp", timeStamp);
		map.put("bussinesscode", bussinessCode);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapParser.putStringToMap(map, "oldmobile", oldmobile);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrKA()
						+ "kaccount_get_mobile_verify_code.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ���ж���ע��(212)
	 * 
	 * @param mobile
	 *            �绰����
	 * @param password
	 *            ����
	 * @param verifyCode
	 *            �ֻ���֤��
	 * @param loginName
	 *            ��¼��
	 * @param ip
	 *            ע��ip
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-2 ����3:33:44
	 */
	public static CldSapReturn register(String mobile, String password,
			String verifyCode, String loginName, String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("mobile", mobile);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("userpwd", password);
		map.put("verifycode", verifyCode);
		map.put("cid", CID);
		map.put("prover", PROVER);
		CldSapParser.putStringToMap(map, "ip", ip);
		CldSapParser.putStringToMap(map, "loginname", loginName);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_reg_by_phone_number.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ����ע��
	 * 
	 * @param mobile
	 *            �绰����
	 * @param password
	 *            ����
	 * @param verifyCode
	 *            �ֻ���֤��
	 * @param loginName
	 *            ��¼��
	 * @param ip
	 *            ע��ip
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-2 ����3:33:44
	 */
	public static CldSapReturn registerEx(String username, String password,
			String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prover", PROVER);
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("userpwd", password);
		map.put("cid", CID);
		map.put("apptype", CldKServiceAPI.getInstance().getApptype());
		CldSapParser.putStringToMap(map, "ip", ip);
		CldSapParser.putStringToMap(map, "loginname", username);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_reg_user.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ͨ���ֻ��� ��֤��������(213)
	 * 
	 * @param mobile
	 *            �绰����
	 * @param newPwd
	 *            ������
	 * @param verifyCode
	 *            ��֤��
	 * @return errCode int
	 * @author Zhouls
	 * @date 2014-8-12 ����6:48:53
	 */
	public static CldSapReturn retrievePwd(String mobile, String newPwd,
			String verifyCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("mobile", mobile);
		map.put("newpwd", newPwd);
		map.put("verifycode", verifyCode);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_reset_pwd_by_mobile.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �޸�����(214)
	 * 
	 * @param oldPwd
	 *            ������
	 * @param newPwd
	 *            ������
	 * @param kuid
	 *            kuid
	 * @param session
	 *            ��¼Session
	 * @param bussinessid
	 *            ҵ�����
	 * @param pwdtype
	 *            ����������,1��ͨ��¼���루Ĭ�ϣ�,2��ݵ�¼����, �����붼����ͨ��¼����
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����11:01:50
	 */
	public static CldSapReturn revisePwd(String oldPwd, String newPwd,
			long kuid, String session, int bussinessid, int pwdtype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("oldpwd", oldPwd);
		map.put("newpwd", newPwd);
		map.put("kuid", kuid);
		map.put("pwdtype", pwdtype);
		map.put("session", session);
		map.put("bussinessid", bussinessid);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_update_pwd.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡ�����ʱ���(215)
	 * 
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-19 ����9:46:48
	 */
	public static CldSapReturn getKAconfig() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_get_config.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ���ֻ���(216)
	 * 
	 * @param kuid
	 *            �û�KUID
	 * @param session
	 *            ��¼Session
	 * @param bussinessid
	 *            ҵ�����
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����11:22:57
	 */
	public static CldSapReturn bindMobile(long kuid, String session,
			int bussinessid, String mobile, String verifycode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("bussinessid", bussinessid);
		map.put("mobile", mobile);
		map.put("verifycode", verifycode);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_bind_mobile.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ����ֻ���(217)
	 * 
	 * @param kuid
	 *            �û�KUID
	 * @param session
	 *            ��¼Session
	 * @param bussinessid
	 *            ҵ�����
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����12:56:43
	 */
	public static CldSapReturn unbindMobile(long kuid, String session,
			int bussinessid, String mobile, String verifycode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("bussinessid", bussinessid);
		map.put("mobile", mobile);
		map.put("verifycode", verifycode);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_unbind_mobile.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * У���ֻ���֤���Ƿ���ȷ��223��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ��֤��
	 * @param bussinessCode
	 *            ҵ������ 101ע�� 102�� 103�İ� 104��� 105��������106��ݵ�¼
	 * @param timeStamp
	 *            ʱ���
	 * @return
	 * @return CldKReturn
	 * @author Zhouls
	 * @date 2015-8-10 ����2:26:40
	 */
	public static CldSapReturn checkMobileVeriCode(String mobile,
			String verifycode, int bussinessCode, long timeStamp) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		//map.put("encrypt", ENCRYPT);
		
		map.put("mobile", mobile);
		map.put("timestamp", timeStamp);
		map.put("bussinesscode", bussinessCode);
		map.put("verifycode", verifycode);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA()
						+ "kaccount_check_mobile_verify_code.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �İ��ֻ���(218)
	 * 
	 * @param kuid
	 *            �û�KUID
	 * @param session
	 *            ��¼Session
	 * @param bussinessid
	 *            ҵ�����
	 * @param oldmobile
	 *            ���ֻ���
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����12:40:20
	 */
	public static CldSapReturn updateMobile(long kuid, String session,
			int bussinessid, String oldmobile, String mobile, String verifycode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("bussinessid", bussinessid);
		map.put("mobile", mobile);
		map.put("oldmobile", oldmobile);
		map.put("verifycode", verifycode);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_update_bind_mobile.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡɨ���¼�Ķ�ά��(219)
	 * 
	 * @param bussinessid
	 *            ҵ�����
	 * @param fastloginpwd
	 *            ��ݵ�¼���룬��ʹ�÷����ɲ�����
	 * @param img
	 *            �Ƿ���Ҫ����imgdata���� 0,����Ҫ��imgdata���ؿ�1��Ҫ
	 * @param size
	 *            ���img����Ϊ1���˲���Ϊ���� ��ά��߳�=size*(25����),size>1
	 * @param timestamp
	 *            ʱ���
	 * @param duid
	 *            �豸��ʶ
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����9:20:50
	 */
	public static CldSapReturn getQRcode(int bussinessid, String fastloginpwd,
			int img, int size, long timestamp, long duid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("bussinessid", bussinessid);
		map.put("timestamp", timestamp);
		map.put("fastloginpwd", fastloginpwd);
		map.put("duid", duid);
		CldSapParser.putIntToMap(map, "img", img);
		CldSapParser.putIntToMap(map, "size", size);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_get_2dcode.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ά���¼(220)
	 * 
	 * @param kuid
	 * @param session
	 * @param bussinessid
	 *            ҵ�����
	 * @param guid
	 *            Ψһ��ʶ��,ɨ���ά��õ�������
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����9:42:47
	 */
	public static CldSapReturn loginByQRcode(long kuid, String session,
			int bussinessid, String guid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("kuid", kuid);
		map.put("guid", guid);
		map.put("session", session);
		map.put("bussinessid", bussinessid);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_login_by_2dcode.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡ��ά���¼״̬(221)
	 * 
	 * @param guid
	 *            Ψһ��ʶ��,��ȡ��ά��ʱ������
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����9:54:36
	 */
	public static CldSapReturn getLoginStatusByQRcode(String guid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("guid", guid);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrKA()
						+ "kaccount_get_login_status_by_2dcode.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��������¼�ӿ�
	 * 
	 * @param openid
	 *            ��������¼���ص�ΨһId
	 * @param type
	 *            ����������,1 qq��¼��2 ΢�ŵ�¼��3����΢����¼
	 * @param userpwd
	 *            �ն�������ɵ���ʱ����
	 * @param bussinessid
	 *            ҵ��id
	 * @param duid
	 *            �豸id
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-6-10 ����9:46:42
	 */
	public static CldSapReturn thirdLogin(String openid, int type,
			String userpwd, int bussinessid, long duid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("appid", APPID);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("openid", openid);
		map.put("type", type);
		map.put("userpwd", userpwd);
		map.put("bussinessid", bussinessid);
		map.put("duid", duid);
		map.put("cid", CID);
		map.put("prover", PROVER);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				CldSapUtil.getNaviSvrKA() + "kaccount_third_reg.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

}
