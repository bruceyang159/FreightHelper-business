/*
 * @Title CldKAccount.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.bll;

import android.os.SystemClock;
import android.text.TextUtils;

import com.cld.device.CldPhoneManager;
import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;
import com.cld.net.CldResponse.ICldResponse;
import com.cld.net.volley.VolleyError;
import com.cld.setting.CldSetting;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.api.CldKAccountAPI.CldThirdLoginType;
import com.mtq.ols.api.CldOlsBase.IInitListener;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.module.delivery.tool.CldPubFuction.CldOlsThreadPool;
import com.mtq.ols.sap.CldSapKAccount;
import com.mtq.ols.sap.CldSapUtil;
import com.mtq.ols.sap.bean.CldSapKAParm.CldLicenceInfo;
import com.mtq.ols.sap.bean.CldSapKAParm.CldUserInfo;
import com.mtq.ols.sap.parse.CldKAccountParse.ProtDeviceInfo;
import com.mtq.ols.sap.parse.CldKAccountParse.ProtLogin;
import com.mtq.ols.sap.parse.CldKAccountParse.ProtQrCode;
import com.mtq.ols.sap.parse.CldKAccountParse.ProtSvrTime;
import com.mtq.ols.sap.parse.CldKAccountParse.ProtUserInfo;
import com.mtq.ols.sap.parse.CldKAccountParse.ProtUserKuid;
import com.mtq.ols.sap.parse.CldKAccountParse.ProtUserRegister;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtKeyCode;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;
import com.mtq.ols.tools.err.CldOlsErrManager;
import com.mtq.ols.tools.err.CldOlsErrManager.CldOlsErrCode;
import com.mtq.ols.tools.model.CldOlsInterface.ICldResultListener;

/**
 * 
 * �ʻ�ϵͳ
 * 
 * @author Zhouls
 * @date 2015-4-8 ����4:24:35
 */
public class CldKAccount {

	/** ��¼״̬ 0δ��¼ 1���ڵ�¼ 2�ѵ�¼ */
	private int loginStatus;
	/** �Ƿ��жϵ�¼. */
	private boolean interruptLogin;
	/** ��ݵ�¼�����뻺�� */
	private String tempFastLoginPwd;

	private static CldKAccount cldKAccount;

	/**
	 * Instantiates a new cld k account.
	 */
	private CldKAccount() {
		interruptLogin = false;
		tempFastLoginPwd = "";
		setLoginStatus(0);
	}

	/**
	 * Gets the single instance of CldKAccount.
	 * 
	 * @return single instance of CldKAccount
	 */
	public static CldKAccount getInstance() {
		if (cldKAccount == null)
			cldKAccount = new CldKAccount();
		return cldKAccount;
	}

	/**
	 * ����ʼ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-26 ����8:48:07
	 */
	public void uninit() {
		interruptLogin = false;
		tempFastLoginPwd = "";
		setLoginStatus(0);
		CldDalKAccount.getInstance().uninit();
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-26 ����8:48:25
	 */
	public void initKey() {
		String cldKAKey = CldDalKAccount.getInstance().getCldKAKey();
		int slpTimer = 5;// ��������
		while (TextUtils.isEmpty(cldKAKey)) {
			/**
			 * û����Կ
			 */
			int sleepSc = 0;// ��������
			if (!CldPhoneNet.isNetConnected()) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				continue;
			}
			CldSapReturn errRes = CldSapKAccount.initKeyCode();
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtKeyCode protKeyCode = CldSapParser.parseJson(strRtn,
					ProtKeyCode.class, errRes);
			CldLog.d("ols", errRes.errCode + "_initKAKey");
			CldLog.d("ols", errRes.errMsg + "_initKAKey");
			CldErrUtil.handleErr(errRes);
			if (null != protKeyCode && errRes.errCode == 0
					&& !TextUtils.isEmpty(protKeyCode.getCode())) {
				cldKAKey = protKeyCode.getCode();
				CldDalKAccount.getInstance().setCldKAKey(cldKAKey);
			} else {
				try {
					while (sleepSc < slpTimer) {
						Thread.sleep(1000);
						sleepSc++;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (slpTimer >= 30) {
				/**
				 * ����30S�Ժ�̶�30S����
				 */
				slpTimer = 30;
			} else {
				/**
				 * ����30Sÿ�μ�5S
				 */
				slpTimer += 5;
			}
		}
		CldSapKAccount.keyCode = cldKAKey;
	}

	/**
	 * ��ʼ��duid
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-1 ����10:07:27
	 */
	public void initDuid(IInitListener listener) {
		long duid = CldDalKAccount.getInstance().getDuid();
		int slpTimer = 5;// ��������
		while (0 == duid) {
			int sleepSc = 0;// ��������
			if (!CldPhoneNet.isNetConnected()) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				continue;
			}
			int errCode = deviceRegister().errCode;
			if (errCode == 0 || errCode == 301) {
				duid = CldDalKAccount.getInstance().getDuid();
			} else {
				try {
					while (sleepSc < slpTimer) {
						Thread.sleep(1000);
						sleepSc++;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (slpTimer >= 30) {
				/**
				 * ����30S�Ժ�̶�30S����
				 */
				slpTimer = 30;
			} else {
				/**
				 * ����30Sÿ�μ�5S
				 */
				slpTimer = +5;
			}
		}
		if (null != listener) {
			listener.onInitDuid();
		}
		/**
		 * ��ȡ��duid���Ƚ�MD5duid���Ƿ���Ҫ�����豸��Ϣ
		 */
		String sourceStr = duid
				+ CldSapUtil.isStrParmRequest("", CldBllUtil.getInstance()
						.getWifiMac())
				+ CldSapUtil.isStrParmRequest("", CldBllUtil.getInstance()
						.getBlueMac())
				+ CldSapUtil.isStrParmRequest("", CldBllUtil.getInstance()
						.getImei())
				+ CldSapUtil.isStrParmRequest("", CldBllUtil.getInstance()
						.getImsi())
				+ CldSapUtil.isStrParmRequest("", CldBllUtil.getInstance()
						.getAppver());
		String MD5duid = CldSapUtil.MD5(sourceStr);
		if (!MD5duid.equals(CldSetting.getString("MD5duid"))) {
			int errCode = deviceRegister().errCode;
			if (errCode == 301) {
				updateDeviceInfo();
			}
		}
	}

	/**
	 * �豸ע��
	 * 
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:26:29
	 */
	public CldSapReturn deviceRegister() {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.deviceRegister(CldBllUtil.getInstance()
					.getDeviceCode(), CldBllUtil.getInstance().getDeviceName(),
					CldBllUtil.getInstance().getOsType(), CldBllUtil
							.getInstance().getApptype(), CldBllUtil
							.getInstance().getSDKVersion(), CldBllUtil
							.getInstance().getModel(), CldBllUtil.getInstance()
							.getWifiMac(), CldBllUtil.getInstance()
							.getBlueMac(), CldBllUtil.getInstance().getImei(),
					CldBllUtil.getInstance().getSN(), CldBllUtil.getInstance()
							.getProver(), null, CldBllUtil.getInstance()
							.getAppver(), CldBllUtil.getInstance().getMapver());
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			ProtDeviceInfo protDeviceInfo = CldSapParser.parseJson(strRtn,
					ProtDeviceInfo.class, errRes);
			CldLog.d("ols", "deviceRegister json = " + strRtn);
			CldLog.d("ols", "DeviceCode:"
					+ CldBllUtil.getInstance().getDeviceCode());
			CldLog.d("ols", errRes.errCode + "_deviceReg");
			CldLog.d("ols", errRes.errMsg + "_deviceReg");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protDeviceInfo) {
				if (errRes.errCode == 0 || errRes.errCode == 301) {
					CldDalKAccount.getInstance().setDuid(
							protDeviceInfo.getDuid());
					String sourceStr = CldDalKAccount.getInstance().getDuid()
							+ CldSapUtil.isStrParmRequest("", CldBllUtil
									.getInstance().getWifiMac())
							+ CldSapUtil.isStrParmRequest("", CldBllUtil
									.getInstance().getBlueMac())
							+ CldSapUtil.isStrParmRequest("", CldBllUtil
									.getInstance().getImei())
							+ CldSapUtil.isStrParmRequest("", CldBllUtil
									.getInstance().getImsi());
					String MD5duid = CldSapUtil.MD5(sourceStr);
					CldSetting.put("MD5duid", MD5duid);
				}
			}
		}
		return errRes;
	}

	/**
	 * �����豸��Ϣ
	 * 
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:26:40
	 */
	public CldSapReturn updateDeviceInfo() {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.updateDeviceInfo(CldDalKAccount
					.getInstance().getDuid(), CldBllUtil.getInstance()
					.getDeviceName(), CldBllUtil.getInstance().getOsType(),
					CldBllUtil.getInstance().getApptype(), CldBllUtil
							.getInstance().getSDKVersion(), CldBllUtil
							.getInstance().getModel(), CldBllUtil.getInstance()
							.getWifiMac(), CldBllUtil.getInstance()
							.getBlueMac(), CldBllUtil.getInstance().getImei(),
					CldBllUtil.getInstance().getSN(), CldBllUtil.getInstance()
							.getProver(), null, CldBllUtil.getInstance()
							.getAppver(), CldBllUtil.getInstance().getMapver());
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_deviceUpdte");
			CldLog.d("ols", errRes.errMsg + "_deviceUpdte");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * �ж��û��Ƿ���ע��
	 * 
	 * @param loginName
	 *            ��¼�����绰����,����,�û�����
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:26:51
	 */
	public CldSapReturn isRegisterUser(String loginName) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.isRegisterUser(loginName, getSvrTime());
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtUserKuid protUserKuid = CldSapParser.parseJson(strRtn,
					ProtUserKuid.class, errRes);
			CldLog.d("ols", errRes.errCode + "_isRegUser");
			CldLog.d("ols", errRes.errMsg + "_isRegUser");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protUserKuid) {
				CldDalKAccount.getInstance().setKuidRegUser(
						protUserKuid.getKuid());
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * У���ֻ���֤��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ��֤��
	 * @param bussinessCode
	 *            ҵ��ID
	 * @return void
	 * @author Zhouls
	 * @return
	 * @date 2015-10-9 ����10:06:16
	 */
	public CldSapReturn checkMobileVeriCode(String mobile, String verifycode,
			int bussinessCode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			long timeStamp = CldKAccount.getInstance().getSvrTime();
			errRes = CldSapKAccount.checkMobileVeriCode(mobile, verifycode,
					bussinessCode, timeStamp);
			CldLog.d("ols",  "errRes.url: " + errRes.url);
			CldLog.d("ols",  "errRes.jsonPost: " + errRes.jsonPost);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_checkCode");
			CldLog.d("ols", errRes.errMsg + "_checkCode");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		} else {
			errRes.errCode = -2;
		}
		return errRes;

	}

	/**
	 * ͨ�����ж���ע��
	 * 
	 * @param password
	 *            �û����õ�����
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:27:07
	 */
	public CldSapReturn registerBySms(String password) {
		CldSapReturn errRes = new CldSapReturn();
		String guid = CldSapUtil.getGuid(getSvrTime());
		int svrType = CldBllUtil.getInstance().getUimtype();
		String address = CldSapUtil.getSvrAddr(svrType);
		if (CldPhoneManager.isSimReady()) {
			// SIM������
			if (CldPhoneNet.isNetConnected()) {
				// ��������
				String smsContent;// ��������
				smsContent = 3 + "|" + guid + "|" + CldSapUtil.MD5(password);
				CldSapUtil.sendSMS(address, smsContent, CldBllUtil
						.getInstance().getContext());
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				for (int i = 0; i < 6; i++) {
					String ip = CldSapUtil.getLocalIpAddress();
					errRes = CldSapKAccount.registerBySms(guid, ip);
					String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
							errRes.jsonPost);
					ProtUserRegister protUserRegister = CldSapParser.parseJson(
							strRtn, ProtUserRegister.class, errRes);
					CldLog.d("ols", errRes.errCode + "_regBySms");
					CldLog.d("ols", errRes.errMsg + "_regBySms");
					CldErrUtil.handleErr(errRes);
					errCodeFix(errRes);
					if (null != protUserRegister) {
						CldDalKAccount.getInstance().setKuidRegSms(
								protUserRegister.getKuid());
						CldDalKAccount.getInstance().setLoginNameRegSms(
								protUserRegister.getLoginname());
					}
					if (errRes.errCode == 0) {
						return errRes;
					}
					if (errRes.errCode == 201) {
						// �ֻ��ѱ���֤
						return errRes;
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
			} else {
				errRes.errCode = -3;
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ��¼
	 * 
	 * @param loginName
	 *            ��¼��
	 * @param loginPwd
	 *            ����
	 * @param pwdtype
	 *            ��������,1��ͨ��¼���루Ĭ�ϣ�,2��ݵ�¼����,
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:27:24
	 */
	public synchronized CldSapReturn login(String loginName, String loginPwd,
			int pwdtype) {
		CldSapReturn errRes = new CldSapReturn();
		long duid = CldDalKAccount.getInstance().getDuid();
		if (duid != 0) {
			// �ɹ���ȡ��duid
			if (CldPhoneNet.isNetConnected()) {
				setLoginStatus(1);
				// ��������
				long timestamp = getSvrTime();
				/**
				 * ƴ������URL
				 */
				errRes = CldSapKAccount.login(loginName, loginPwd, CldBllUtil
						.getInstance().getBussinessid(), pwdtype, timestamp,
						null, duid);
				/**
				 * ��������
				 */
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				/**
				 * �������
				 */
				ProtLogin protLogin = CldSapParser.parseJson(strRtn,
						ProtLogin.class, errRes);
				CldLog.d("ols", errRes.errCode + "_login");
				CldLog.d("ols", errRes.errMsg + "_login");
				CldErrUtil.handleErr(errRes);
				kaKeyFix(errRes.errCode);
				if (null != protLogin && errRes.errCode == 0) {
					// �����ж��Ƿ��¼�ɹ��ı�ʶ
					setLoginStatus(2);
					CldDalKAccount.getInstance().setLoginName(loginName);
					CldDalKAccount.getInstance().setLoginPwd(loginPwd);
					CldDalKAccount.getInstance().setPwdtype(pwdtype);
					CldDalKAccount.getInstance().setKuid(protLogin.getKuid());
					CldDalKAccount.getInstance().setSession(
							protLogin.getSession());
					protLogin.protParse(CldDalKAccount.getInstance()
							.getCldUserInfo());
					CldKAccountAPI.getInstance().getUserInfo();
				} else {
					// ����ԭ���¼ʧ��(102,103,104�ʺ�ԭ���¼������������˹���)
					if (errRes.errCode == 910) {
						/**
						 * ʱ�䱻����
						 */
						initSvrTime();
					} else {
						setLoginStatus(0);
						CldDalKAccount.getInstance().uninit();
						CldDalKAccount.getInstance().setLoginPwd("");
					}
				}
			} else {
				errRes.errCode = -2;
				errRes.errMsg = "�����쳣";
			}
		} else {
			errRes.errCode = -1;
			errRes.errMsg = "duid��ʼ��ʧ��";

		}
		return errRes;
	}

	/**
	 * ע����¼
	 * 
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:27:45
	 */
	public CldSapReturn loginOut() {
		CldSapReturn errRes = new CldSapReturn();
		CldDalKAccount.getInstance().uninit();
		CldDalKAccount.getInstance().setLoginPwd("");
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.loginOut(CldDalKAccount.getInstance()
					.getKuid(), CldDalKAccount.getInstance().getSession(),
					CldBllUtil.getInstance().getBussinessid());
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_loginOut");
			CldLog.d("ols", errRes.errMsg + "_loginOut");
			CldErrUtil.handleErr(errRes);
			setLoginStatus(0);
			kaKeyFix(errRes.errCode);
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * 
	 * ��¼״̬����
	 * 
	 * @author Zhouls
	 * @date 2015-4-8 ����4:27:55
	 */
	public interface IAutoLoginListener {

		/**
		 * ��¼״̬�����ص�
		 * 
		 * @param loginState
		 *            ��¼״̬��0δ��¼,1���ڵ�¼,2��¼�ɹ���3��¼ʧ��,4�ⲿ�жϣ�
		 * @param errCode
		 *            ��¼���ص�errCode
		 * @return void
		 * @author Zhouls
		 * @date 2014-9-1 ����5:46:38
		 */
		public void onLoginStateChange(int loginState, CldSapReturn err);
	}

	/**
	 * ѭ����¼
	 * 
	 * @param listener
	 *            �Զ���¼�ص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-2-12 ����10:34:21
	 */
	public void cycleLogin(IAutoLoginListener listener) {
		int loginState = 0;
		CldSapReturn errRes = new CldSapReturn();
		/**
		 * �������쳣�˳�����ʱδ�������session��kuid
		 */
		CldKAccountAPI.getInstance().cleanKuid();
		CldKAccountAPI.getInstance().cleanSession();
		/**
		 * δ��¼(0,-2)
		 */
		if (null != listener) {
			listener.onLoginStateChange(loginState, errRes);
		}
		// ȡ�ñ�����û���������ols_ka_pwdtype
		int pwdtype = CldSetting.getInt("ols_ka_pwdtype");
		if (pwdtype == 0) {
			/**
			 * ����֮ǰû�д��ֶΰ汾�����Զ���¼
			 */
			pwdtype = 1;
		}
		String username = CldDalKAccount.getInstance().getLoginName();
		String password = CldDalKAccount.getInstance().getLoginPwd();
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
			// ֻҪû���жϵ�¼
			int slpTimer = 5;// ��������
			interruptLogin = false;
			while (!interruptLogin) {
				int sleepSc = 0;// ��������
				loginState = 1;
				errRes.errCode = -3;
				/**
				 * ���ڵ�¼(1,-3) -3��δ��ʼ��¼
				 */
				setLoginStatus(1);
				if (null != listener) {
					listener.onLoginStateChange(loginState, errRes);
				}
				// ��¼
				errRes = login(username, password, pwdtype);
				CldLog.d("ols", "autologin errorCode = " + errRes.errCode);
				if (errRes.errCode == 104) {
					// �������
					loginState = 3;
					/**
					 * ��¼ʧ��(3,104)
					 */
					if (null != listener) {
						listener.onLoginStateChange(loginState, errRes);
					}
					// �жϵ�¼
					interruptLogin = true;
				} else {
					if (errRes.errCode == 0) {
						loginState = 2;
						/**
						 * ��¼�ɹ�(2,0)
						 */
						if (null != listener) {
							listener.onLoginStateChange(loginState, errRes);
						}
						// �жϵ�¼
						interruptLogin = true;
						if (null == listener) {
							/**
							 * session ʧЧ�ص�½ ���˴��� �����ʱ�����ӿڴ������µ�¼
							 */
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else if (errRes.errCode == 102 || errRes.errCode == 104) {
						switch (errRes.errCode) {
						case 102:
							interruptLogin = true;
							CldSetting.put("userName", "");
							loginState = 0;
							listener.onLoginStateChange(loginState, errRes);
							break;
						case 104:
							interruptLogin = true;
							CldSetting.put("password", "");
							break;
						}
					} else {

						loginState = 1;
						/**
						 * ���ڵ�¼(1,-1||-2)(δ��ȡ��duid||�����쳣)
						 */
						if (null != listener) {
							listener.onLoginStateChange(loginState, errRes);
						}
						try {
							while (sleepSc < slpTimer) {
								Thread.sleep(1000);
								sleepSc++;
								// �жϵ�¼
								if (interruptLogin) {
									loginState = 4;
									/**
									 * �ⲿ�ж�(4,errCode)
									 */
									if (null != listener) {
										listener.onLoginStateChange(loginState,
												errRes);
									}
								}
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (slpTimer >= 30) {
					/**
					 * ����30S�Ժ�̶�30S����
					 */
					slpTimer = 30;
				} else {
					/**
					 * ����30Sÿ�μ�5S
					 */
					slpTimer = +5;
				}
			}
		} else {
			/**
			 * δ��¼(0,-1)(�û���������Ϊ��)
			 */
			errRes.errCode = -1;
			if (null != listener) {
				listener.onLoginStateChange(loginState, errRes);
			}
		}
	}

	/**
	 * �Զ���¼
	 * 
	 * @param listener
	 *            ��¼״̬����
	 * @return void
	 * @author Zhouls
	 * @date 2014-9-2 ����8:54:24
	 */
	public void startAutoLogin(IAutoLoginListener listener) {
		cycleLogin(listener);
	}

	/**
	 * sessionʧЧ���µ�¼
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-2-5 ����4:01:12
	 */
	public void sessionRelogin() {
		CldOlsThreadPool.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				cycleLogin(null);
			}
		});
	}

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:28:21
	 */
	public CldSapReturn getUserInfo() {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			if (getLoginStatus() == 2) {
				errRes = CldSapKAccount.getUserInfo(CldDalKAccount
						.getInstance().getKuid(), CldDalKAccount.getInstance()
						.getSession(), CldBllUtil.getInstance()
						.getBussinessid(), 1);
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				ProtUserInfo protUserInfo = CldSapParser.parseJson(strRtn,
						ProtUserInfo.class, errRes);
				CldLog.d("ols", errRes.errCode + "_getUserInfo");
				CldLog.d("ols", errRes.errMsg + "_getUserInfo");
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
				if (null != protUserInfo) {
					CldUserInfo userInfor = CldDalKAccount.getInstance()
							.getCldUserInfo();
					if (errRes.errCode == 0) {
						protUserInfo.protParse(userInfor);
					}
				}
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ͬ��������ʱ��
	 * 
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:28:32
	 */
	public void initSvrTime() {
		CldSapReturn errRes = new CldSapReturn();
		boolean isInited = false;
		int slpTimer = 5;// ��������
		while (!isInited) {
			int sleepSc = 0;// ��������
			if (!CldPhoneNet.isNetConnected()) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				continue;
			}
			errRes = CldSapKAccount.getKAconfig();
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtSvrTime protSvrTime = CldSapParser.parseJson(strRtn,
					ProtSvrTime.class, errRes);
			CldLog.d("ols", errRes.errCode + "_initSvrtime");
			CldLog.d("ols", errRes.errMsg + "_initSvrtime");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protSvrTime && null != protSvrTime.getData()
					&& errRes.errCode == 0) {
				// long timeDif = CldSapUtil.getLocalTime()
				// - protSvrTime.getData().getServer_time();
				long timeDif = SystemClock.elapsedRealtime() / 1000
						- protSvrTime.getData().getServer_time();
				CldDalKAccount.getInstance().setTimeDif(timeDif);
				isInited = true;
			} else {
				try {
					while (sleepSc < slpTimer) {
						Thread.sleep(1000);
						sleepSc++;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (slpTimer >= 30) {
				/**
				 * ����30S�Ժ�̶�30S����
				 */
				slpTimer = 30;
			} else {
				/**
				 * ����30Sÿ�μ�5S
				 */
				slpTimer += 5;
			}
		}
	}

	/**
	 * ��ȡ�ֻ���֤��
	 * 
	 * @param mobile
	 *            �绰����
	 * @param bussinessCode
	 *            ҵ������ 101ע�� 102�� 103�İ� 104��� 105�������� 106��ݵ�¼
	 * @param oldmoble
	 *            103�ش�
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:28:48
	 */
	public CldSapReturn getVerifyCode(String mobile, int bussinessCode,
			String oldmoble) {
		CldSapReturn errRes = new CldSapReturn();
		long timeStamp = CldKAccount.getInstance().getSvrTime();
		if (CldPhoneNet.isNetConnected()) {
			switch (bussinessCode) {
			case 101:
			case 105:
			case 106:
				errRes = CldSapKAccount.getVerifyCode(mobile, bussinessCode,
						timeStamp, 0, null, -1, null);
				break;
			case 103:
				errRes = CldSapKAccount.getVerifyCode(mobile, bussinessCode,
						timeStamp, CldDalKAccount.getInstance().getKuid(),
						CldDalKAccount.getInstance().getSession(), CldBllUtil
								.getInstance().getBussinessid(), oldmoble);
				break;
			case 102:
			case 104:
				errRes = CldSapKAccount.getVerifyCode(mobile, bussinessCode,
						timeStamp, CldDalKAccount.getInstance().getKuid(),
						CldDalKAccount.getInstance().getSession(), CldBllUtil
								.getInstance().getBussinessid(), null);
				break;
			}
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_getVericode");
			CldLog.d("ols", errRes.errMsg + "_getVericode");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ע�ᣨ�����˺�)
	 * 
	 * @param mobile
	 *            �绰����
	 * @param password
	 *            ����
	 * @param verifyCode
	 *            �ֻ���֤��
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:29:55
	 */
	public CldSapReturn registerEx(String username, String password) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			String ip = CldSapUtil.getLocalIpAddress();
			errRes = CldSapKAccount.registerEx(username, password, ip);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			ProtUserRegister protUserRegister = CldSapParser.parseJson(strRtn,
					ProtUserRegister.class, errRes);
			CldLog.d("ols", errRes.errCode + "_regbycode");
			CldLog.d("ols", errRes.errMsg + "_regbycode");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protUserRegister) {
				CldDalKAccount.getInstance().setLoginNameReg(
						protUserRegister.getLoginname());
				CldDalKAccount.getInstance().setKuidReg(
						protUserRegister.getKuid());
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ע�ᣨ���ֻ���֤��)
	 * 
	 * @param mobile
	 *            �绰����
	 * @param password
	 *            ����
	 * @param verifyCode
	 *            �ֻ���֤��
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:29:55
	 */
	public CldSapReturn register(String mobile, String password,
			String verifyCode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			String ip = CldSapUtil.getLocalIpAddress();
			errRes = CldSapKAccount.register(mobile, password, verifyCode,
					null, ip);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			ProtUserRegister protUserRegister = CldSapParser.parseJson(strRtn,
					ProtUserRegister.class, errRes);
			CldLog.d("ols", errRes.errCode + "_regbycode");
			CldLog.d("ols", errRes.errMsg + "_regbycode");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protUserRegister) {
				CldDalKAccount.getInstance().setLoginNameReg(
						protUserRegister.getLoginname());
				CldDalKAccount.getInstance().setKuidReg(
						protUserRegister.getKuid());
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ͨ���ֻ��� ��֤��������
	 * 
	 * @param mobile
	 *            �绰����
	 * @param newPwd
	 *            ������
	 * @param verifyCode
	 *            �ֻ���֤��
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:30:07
	 */
	public CldSapReturn retrievePwd(String mobile, String newPwd,
			String verifyCode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.retrievePwd(mobile, newPwd, verifyCode);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_resetPwdByCode");
			CldLog.d("ols", errRes.errMsg + "_resetPwdByCode");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldDalKAccount.getInstance().setLoginName(mobile);
				CldDalKAccount.getInstance()
						.setLoginPwd(CldSapUtil.MD5(newPwd));
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * �޸����루��ͨ�����޸ģ�
	 * 
	 * @param oldPwd
	 *            ������
	 * @param newPwd
	 *            ������
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:30:23
	 */
	public CldSapReturn revisePwd(String oldPwd, String newPwd) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount
					.revisePwd(oldPwd, newPwd, CldDalKAccount.getInstance()
							.getKuid(), CldDalKAccount.getInstance()
							.getSession(), CldBllUtil.getInstance()
							.getBussinessid(), 1);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_revisePwd");
			CldLog.d("ols", errRes.errMsg + "_revisePwd");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldDalKAccount.getInstance()
						.setLoginPwd(CldSapUtil.MD5(newPwd));
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * �޸����루��ݵ�¼�����޸ģ�
	 * 
	 * @param newPwd
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:30:39
	 */
	public CldSapReturn revisePwdByFastPwd(String newPwd) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			String oldPwd = CldDalKAccount.getInstance().getLoginPwd();
			errRes = CldSapKAccount
					.revisePwd(oldPwd, newPwd, CldDalKAccount.getInstance()
							.getKuid(), CldDalKAccount.getInstance()
							.getSession(), CldBllUtil.getInstance()
							.getBussinessid(), 2);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_revisePwd");
			CldLog.d("ols", errRes.errMsg + "_revisePwd");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldDalKAccount.getInstance()
						.setLoginPwd(CldSapUtil.MD5(newPwd));
				CldDalKAccount.getInstance().setPwdtype(1);
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * �����û���Ϣ
	 * 
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
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:31:28
	 */
	public CldSapReturn updateUserInfo(String username, String useralias,
			String email, String mobile, String sex, String address,
			byte[] photoData) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.updateUserInfo(CldDalKAccount.getInstance()
					.getKuid(), CldDalKAccount.getInstance().getSession(),
					CldBllUtil.getInstance().getBussinessid(), username,
					useralias, email, mobile, sex, address, -1, null, null,
					photoData);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_updateUserInfo");
			CldLog.d("ols", errRes.errMsg + "_updateUserInfo");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ���ֻ���
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:31:44
	 */
	public CldSapReturn bindMobile(String mobile, String verifycode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.bindMobile(CldDalKAccount.getInstance()
					.getKuid(), CldDalKAccount.getInstance().getSession(),
					CldBllUtil.getInstance().getBussinessid(), mobile,
					verifycode);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_bindMobile");
			CldLog.d("ols", errRes.errMsg + "_bindMobile");
			if (errRes.errCode == 0) {
				CldDalKAccount.getInstance().getCldUserInfo().setMobileBind(1);
				CldDalKAccount.getInstance().getCldUserInfo().setMobile(mobile);
			}
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ����ֻ���
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:31:56
	 */
	public CldSapReturn unbindMobile(String mobile, String verifycode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.unbindMobile(CldDalKAccount.getInstance()
					.getKuid(), CldDalKAccount.getInstance().getSession(),
					CldBllUtil.getInstance().getBussinessid(), mobile,
					verifycode);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_unbindMobile");
			CldLog.d("ols", errRes.errMsg + "_unbindMobile");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldDalKAccount.getInstance().getCldUserInfo().setMobile(mobile);
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * �İ��ֻ���
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param oldmobile
	 *            ���ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:32:16
	 */
	public CldSapReturn updateMobile(String mobile, String oldmobile,
			String verifycode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.updateMobile(CldDalKAccount.getInstance()
					.getKuid(), CldDalKAccount.getInstance().getSession(),
					CldBllUtil.getInstance().getBussinessid(), oldmobile,
					mobile, verifycode);
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_updateMobile");
			CldLog.d("ols", errRes.errMsg + "_updateMobile");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldDalKAccount.getInstance().getCldUserInfo().setMobile(mobile);
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * �޸�����󱣴�������
	 * 
	 * @param newPwd
	 *            the new login pwd
	 * @return void
	 * @author Zhouls
	 * @date 2014-9-4 ����6:28:07
	 */
	public void setLoginPwd(String newPwd) {
		String password = CldSapUtil.MD5(newPwd);
		CldSetting.put("password", password);
	}

	/**
	 * ��ȡ������û���
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-11-25 ����7:33:28
	 */
	public String getLoginName() {
		return CldSetting.getString("userName");
	}

	/**
	 * ��ȡ��������루MD5ֵ��
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-9-5 ����8:59:44
	 */
	public String getLoginPwd() {
		return CldSetting.getString("password");
	}

	/**
	 * ��˽����������ȡduid
	 * 
	 * @return (��:0;��Ϊ��:duid) int
	 * @author Zhouls
	 * @date 2014-8-29 ����4:13:53
	 */
	public long getServiceDuid() {
		return preferToLong("duid");
	}

	/**
	 * ��˽����������ȡkuid
	 * 
	 * @return (��:0;��Ϊ��:kuid) int
	 * @author Zhouls
	 * @date 2014-8-29 ����9:47:51
	 */
	public long getServiceKuid() {
		return preferToLong("kuid");
	}

	/**
	 * �жϵ�¼
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-8-26 ����11:09:28
	 */
	public void interruptLogin() {
		interruptLogin = true;
	}

	/**
	 * ȡKEYת��Long
	 * 
	 * @param key
	 *            the key
	 * @return long
	 * @author Zhouls
	 * @date 2014-9-4 ����4:48:55
	 */
	private long preferToLong(String key) {
		if (CldSetting.getString(key).length() == 0) {
			return 0;
		} else {
			return Long.parseLong(CldSetting.getString(key));
		}
	}

	/**
	 * ��ȡ������ʱ��
	 * 
	 * @return ��ǰ������ʱ�� long
	 * @author Zhouls
	 * @date 2014-9-19 ����10:28:30
	 */
	public long getSvrTime() {
		// return CldSapUtil.getLocalTime()
		// - CldDalKAccount.getInstance().getTimeDif();
		return SystemClock.elapsedRealtime() / 1000
				- CldDalKAccount.getInstance().getTimeDif();
	}

	/**
	 * ����������ɵĿ�ݵ�¼����
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-1-19 ����4:34:06
	 */
	public String randomFastLoginPwd() {
		return CldSapUtil.MD5(CldSapUtil.genRandomNum(8));
	}

	/**
	 * ��ݵ�¼
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ��֤��
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����8:51:57
	 */
	public CldSapReturn fastLogin(String mobile, String verifycode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			String loginname = "M" + mobile;
			String fastloginpwd = randomFastLoginPwd();
			setLoginStatus(1);
			errRes = CldSapKAccount.fastLogin(mobile, verifycode, fastloginpwd,
					loginname, CldDalKAccount.getInstance().getDuid(),
					CldBllUtil.getInstance().getBussinessid());
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			ProtLogin protLogin = CldSapParser.parseJson(strRtn,
					ProtLogin.class, errRes);
			CldLog.d("ols", errRes.errCode + "_fastLogin");
			CldLog.d("ols", errRes.errMsg + "_fastLogin");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protLogin) {
				if (errRes.errCode == 0) {
					// �����ж��Ƿ��¼�ɹ��ı�ʶ
					setLoginStatus(2);
					CldDalKAccount.getInstance().setLoginName(
							protLogin.getLoginname());
					CldDalKAccount.getInstance().setLoginPwd(fastloginpwd);
					CldDalKAccount.getInstance().setPwdtype(2);
					CldDalKAccount.getInstance().setKuid(protLogin.getKuid());
					CldDalKAccount.getInstance().setSession(
							protLogin.getSession());
					protLogin.protParse(CldDalKAccount.getInstance()
							.getCldUserInfo());
					CldKAccountAPI.getInstance().getUserInfo();
				} else {
					if (errRes.errCode == 910) {
						/**
						 * ʱ�䱻����
						 */
						initSvrTime();
					} else {
						setLoginStatus(0);
						CldDalKAccount.getInstance().uninit();
						CldDalKAccount.getInstance().setLoginPwd("");
					}
				}
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ��ȡɨ���¼�Ķ�ά��
	 * 
	 * @param QRsideLength
	 *            ��ά��߳�(����)
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����9:27:22
	 */
	public CldSapReturn getQRcode(int QRsideLength) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			String fastloginpwd = randomFastLoginPwd();
			tempFastLoginPwd = fastloginpwd;
			/**
			 * �Ƿ��ȡ��ά��ͼ������
			 */
			int img = 0;
			int size = -1;
			if (QRsideLength > 0) {
				img = 1;
				size = QRsideLength / 25;
				if (size < 1) {
					/**
					 * �������С��1 size��1
					 */
					size = 1;
				}
			}
			errRes = CldSapKAccount.getQRcode(CldBllUtil.getInstance()
					.getBussinessid(), fastloginpwd, img, size, getSvrTime(),
					CldDalKAccount.getInstance().getDuid());
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtQrCode protQrCode = CldSapParser.parseJson(strRtn,
					ProtQrCode.class, errRes);
			CldLog.d("ols", errRes.errCode + "_getQRcode");
			CldLog.d("ols", errRes.errMsg + "_getQRcode");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protQrCode) {
				if (errRes.errCode == 0) {
					CldDalKAccount.getInstance().setGuid(protQrCode.getGuid());
					CldDalKAccount.getInstance().setCreateTime(
							protQrCode.getCreate_time());
					CldDalKAccount.getInstance().setImgdata(
							protQrCode.getImgdata());
				}
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ��ά���¼
	 * 
	 * @param guid
	 *            Ψһ��ʶ��,ɨ���ά��õ�������
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����9:45:47
	 */
	public CldSapReturn loginByQRcode(String guid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			if (getLoginStatus() == 2) {
				errRes = CldSapKAccount.loginByQRcode(CldDalKAccount
						.getInstance().getKuid(), CldDalKAccount.getInstance()
						.getSession(), CldBllUtil.getInstance()
						.getBussinessid(), guid);
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldLog.d("ols", errRes.errCode + "_loginByQR");
				CldLog.d("ols", errRes.errMsg + "_loginByQR");
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
			} else {
				errRes.errCode = -1;
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ��ȡ��ά���¼״̬
	 * 
	 * @param guid
	 *            Ψһ��ʶ��,��ȡ��ά��ʱ������
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-19 ����9:57:17
	 */
	public CldSapReturn getLoginStatusByQRcode(String guid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAccount.getLoginStatusByQRcode(guid);
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtLogin protLogin = CldSapParser.parseJson(strRtn,
					ProtLogin.class, errRes);
			CldLog.d("ols", errRes.errCode + "_getLoginStatus");
			CldLog.d("ols", errRes.errMsg + "_getLoginStatus");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protLogin) {
				if (!TextUtils.isEmpty(tempFastLoginPwd)) {
					if (errRes.errCode == 0) {
						// �����ж��Ƿ��¼�ɹ��ı�ʶ
						setLoginStatus(2);
						CldDalKAccount.getInstance().setLoginName(
								protLogin.getLoginname());
						CldDalKAccount.getInstance().setLoginPwd(
								tempFastLoginPwd);
						CldDalKAccount.getInstance().setPwdtype(2);
						CldDalKAccount.getInstance().setKuid(
								protLogin.getKuid());
						CldDalKAccount.getInstance().setSession(
								protLogin.getSession());
						protLogin.protParse(CldDalKAccount.getInstance()
								.getCldUserInfo());
					}
				}
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * ��������¼
	 * 
	 * @param openid
	 *            ��������¼���ص�ΨһId
	 * @param type
	 *            CldThirdLoginType
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-6-10 ����9:58:43
	 */
	public CldSapReturn thirdLogin(String openid,
			CldThirdLoginType cldThirdLoginType) {
		/**
		 * �������쳣�˳�����ʱδ�������session��kuid
		 */
		CldKAccountAPI.getInstance().cleanKuid();
		CldKAccountAPI.getInstance().cleanSession();
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			final String fastloginpwd = randomFastLoginPwd();
			errRes = CldSapKAccount.thirdLogin(openid, CldThirdLoginType
					.valueOf(cldThirdLoginType), fastloginpwd, CldBllUtil
					.getInstance().getBussinessid(), CldDalKAccount
					.getInstance().getDuid());
			String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
					errRes.jsonPost);
			ProtLogin protLogin = CldSapParser.parseJson(strRtn,
					ProtLogin.class, errRes);
			if (null != protLogin) {
				if (errRes.errCode == 0) {
					setLoginStatus(2);
					CldDalKAccount.getInstance().setLoginName(
							protLogin.getLoginname());
					CldDalKAccount.getInstance().setLoginPwd(fastloginpwd);
					CldDalKAccount.getInstance().setPwdtype(2);
					CldDalKAccount.getInstance().setKuid(protLogin.getKuid());
					CldDalKAccount.getInstance().setSession(
							protLogin.getSession());
					protLogin.protParse(CldDalKAccount.getInstance()
							.getCldUserInfo());
					CldLog.d("ols", errRes.errCode + "_thirdLogin");
					CldLog.d("ols", errRes.errMsg + "_thirdLogin");
				}
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
			}
		} else {
			errRes.errCode = -2;
		}
		return errRes;
	}

	/**
	 * �ϴ��Ǽ���֤��Ϣ
	 * 
	 * @param driveLicenceData
	 *            ��ʻ֤��Ϣ
	 * @param vehicleLicenceData
	 *            ��ʻ֤��Ϣ
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-6 ����3:28:26
	 */
	public void uploadStarAuth(byte[] driveLicenceData,
			byte[] vehicleLicenceData, final ICldResultListener listener) {
		final CldSapReturn errRes = new CldSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (null == driveLicenceData || driveLicenceData.length <= 0
				|| null == vehicleLicenceData || vehicleLicenceData.length <= 0) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.PARAM_INBALID;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldSapReturn request = CldSapKAccount
				.uploadStarAuth(CldDalKAccount.getInstance().getKuid(),
						CldDalKAccount.getInstance().getSession(), CldBllUtil
								.getInstance().getBussinessid(),
						driveLicenceData, vehicleLicenceData);
		CldHttpClient.post(request.url, request.jsonPost, ProtBase.class,
				new ICldResponse<ProtBase>() {

					@Override
					public void onResponse(ProtBase response) {
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d("ols", errRes.errCode + "_uploadStarAuth");
						CldLog.d("ols", errRes.errMsg + "_uploadStarAuth");
						CldOlsErrManager.handleErr(request, errRes);
						errCodeFix(errRes);
						if (errRes.errCode == 0) {
							CldUserInfo userinfo = CldDalKAccount.getInstance()
									.getCldUserInfo();
							if (null != userinfo) {
								CldLicenceInfo licenceInfo = userinfo
										.getLicenceInfo();
								if (null != licenceInfo) {
									// �ύ�ɹ�Ϊ�����
									licenceInfo.status = 1;
								}
								userinfo.setLicenceInfo(licenceInfo);
							}
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onErrorResponse(VolleyError error) {
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * �����봦��
	 * 
	 * @param res
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:33:09
	 */
	public void errCodeFix(CldSapReturn res) {
		switch (res.errCode) {
		case 402: {
			/**
			 * ��Կ����
			 */
			CldDalKAccount.getInstance().setCldKAKey("");
			initKey();
		}
			break;
		case 500: {
			/**
			 * sessionʧЧ�Զ���¼һ��ˢ��session
			 */
			sessionRelogin();
		}
			break;
		case 501: {
			/**
			 * �������ߴ���
			 */
			if (res.session.equals(CldDalKAccount.getInstance().getSession())) {
				/**
				 * ���ӿ�ʹ��session�͵�ǰ�ʻ����session��ͬ�ż�����
				 */
				if (!TextUtils.isEmpty(res.session)) {
					CldKAccountAPI.getInstance().sessionInvalid(501, 0);
				}
			}
		}
			break;
		}
	}

	/**
	 * ��Կ��֤����
	 * 
	 * @param errCode
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-23 ����11:09:26
	 */
	public void kaKeyFix(int errCode) {
		switch (errCode) {
		case 402: {
			/**
			 * ��Կ����
			 */
			CldDalKAccount.getInstance().setCldKAKey("");
			initKey();
		}
			break;
		}
	}

	// synchronized ? �������ʿ�ס��yyh
	public int getLoginStatus() {
		return loginStatus;
	}

	public synchronized void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}
}
