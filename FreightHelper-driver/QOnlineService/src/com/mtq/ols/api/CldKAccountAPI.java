/*
 * @Title CldKAccountAPI.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 17:37:59
 * @version 1.0
 */
package com.mtq.ols.api;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.cld.device.CldPhoneNet;
import com.cld.setting.CldSetting;
import com.mtq.ols.api.CldOlsBase.IInitListener;
import com.mtq.ols.api.CldOlsInit.ICldOlsInitListener;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.bll.CldKAccount;
import com.mtq.ols.bll.CldKAccount.IAutoLoginListener;
import com.mtq.ols.callback.OnAccountResponseCallBack;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.sap.CldSapUtil;
import com.mtq.ols.sap.bean.CldSapKAParm.CldUserInfo;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapReturn;
import com.mtq.ols.tools.model.CldOlsInterface.ICldResultListener;

/**
 * �ʻ����ģ�飬�ṩ�ʻ���ع���.
 * 
 * @author Zhouls
 * @date 2014-8-29 ����11:27:37
 */
public class CldKAccountAPI {

	/** �ʻ�ϵͳ�ص���������ʼ��ʱ����һ��. */
	private ICldKAccountListener listener;
	/** The cld k account api. */
	private static CldKAccountAPI cldKAccountAPI;

	/**
	 * Instantiates a new cld k account api.
	 */
	private CldKAccountAPI() {
	}

	/**
	 * Gets the single instance of CldKAccountAPI.
	 * 
	 * @return single instance of CldKAccountAPI
	 */
	public static CldKAccountAPI getInstance() {
		if (null == cldKAccountAPI) {
			cldKAccountAPI = new CldKAccountAPI();
		}
		return cldKAccountAPI;
	}

	/**
	 * ����������ʼ������.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-8-29 ����12:03:02
	 */
	public void init() {

	}

	/**
	 * ����ʼ��.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-10-24 ����10:00:06
	 */
	public void uninit() {
		CldKAccount.getInstance().uninit();
	}

	/**
	 * ���ûص��������״�������Ч��
	 * 
	 * @param listener
	 *            �ص�����
	 * @return int (0 ���óɹ���-1��������ʧ��)
	 * @author Zhouls
	 * @date 2014-8-29 ����12:09:36
	 */
	public int setCldKAccountListener(ICldKAccountListener listener) {
		if (null == this.listener) {
			this.listener = listener;
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2015-2-12 ����6:06:00
	 */
	public void initKey(final ICldOlsInitListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CldKAccount.getInstance().initKey();
				if (null != listener) {
					listener.onInitReslut();
				}
			}
		}).start();
	}

	/**
	 * ��ʼ��������ʱ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-1 ����1:13:11
	 */
	public void initSvrTime() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CldKAccount.getInstance().initSvrTime();
			}
		}).start();
	}

	/**
	 * ��ʼ��duid
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-1 ����1:16:50
	 */
	public void initDuid(final IInitListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CldKAccount.getInstance().initDuid(listener);
			}
		}).start();
	}

	/**
	 * �豸ע�ᣨ��ȡduid��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-11-25 ����3:19:48
	 */
	public void deviceRegister() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldKAccount.getInstance().deviceRegister();
			}
		}).start();
	}

	/**
	 * �ж��Ƿ�����ע���û�����ȡָ���û���kuid��
	 * 
	 * @param loginName
	 *            ��¼�����绰����,����,�û�����
	 * @return void
	 * @author Zhouls
	 * @date 2014-8-29 ����12:03:27
	 */
	public void isRegisterUser(final String loginName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				CldSapReturn errRes = CldKAccount.getInstance().isRegisterUser(
						loginName);
				long kuid = 0;
				if (errRes.errCode == 101) {
					kuid = CldDalKAccount.getInstance().getKuidRegUser();
				}
				if (null != listener) {
					listener.onIsRegUser(errRes.errCode, kuid, loginName);
				}
				Looper.loop();
			}
		}).start();
	}

	/**
	 * ���ж���ע��.
	 * 
	 * @param password
	 *            �û����õ�����
	 * @return void
	 * @author Zhouls
	 * @date 2014-8-29 ����12:04:17
	 */
	public void registerBySms(final String password) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().registerBySms(
						password);
				long kuid = 0;
				String loginName = "";
				if (errRes.errCode == 0) {
					kuid = CldDalKAccount.getInstance().getKuidRegSms();
					loginName = CldDalKAccount.getInstance()
							.getLoginNameRegSms();
				}
				if (null != listener) {
					listener.onRegBySms(errRes.errCode, kuid, loginName);
				}
			}
		}).start();
	}

	/**
	 * 
	 * ��������¼����ö��
	 * 
	 * @author Zhouls
	 * @date 2015-6-23 ����2:59:31
	 */
	public enum CldThirdLoginType {
		QQLOGIN, WEIXLOGIN, SINALOGIN;
		/**
		 * ��ȡ��Ӧ��ֵ
		 * 
		 * @param CldThirdLoginType
		 * @return
		 * @return int
		 * @author Zhouls
		 * @date 2015-6-23 ����2:58:13
		 */
		public static int valueOf(CldThirdLoginType cldThirdLoginType) {
			switch (cldThirdLoginType) {
			case QQLOGIN:
				// QQ��¼
				return 1;
			case WEIXLOGIN:
				// ΢�ŵ�¼
				return 2;
			case SINALOGIN:
				// ���˵�¼
				return 3;
			default:
				return 0;
			}
		}
	}

	/**
	 * �ֶ���¼.
	 * 
	 * @param loginName
	 *            ��¼�����绰����,����,�û�����
	 * @param password
	 *            ����
	 * @return void
	 * @author Zhouls
	 * @date 2014-10-23 ����10:35:23
	 * 
	 */
	public void login(final String loginName, final String password) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String loginPwd;
				CldSapReturn errRes;
				if (!TextUtils.isEmpty(loginName)
						&& !TextUtils.isEmpty(password)) {
					if (password.length() == 32) {
						loginPwd = password;
					} else {
						loginPwd = CldSapUtil.MD5(password);
					}
					errRes = CldKAccount.getInstance().login(loginName,
							loginPwd, 1);
				} else {
					errRes = CldErrUtil.errDeal();
				}
				if (null != listener) {
					listener.onLoginResult(errRes.errCode, false);

				}
			}
		}).start();
	}

	/**
	 * �ֶ���¼
	 * 
	 * @author ligangfan
	 * @param loginName
	 * @param password
	 * @param callBack
	 */
	public void login(final String loginName, final String password,
			final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String loginPwd;
				CldSapReturn errRes;
				if (!TextUtils.isEmpty(loginName)
						&& !TextUtils.isEmpty(password)) {
					if (password.length() == 32) {
						loginPwd = password;
					} else {
						loginPwd = CldSapUtil.MD5(password);
					}
					errRes = CldKAccount.getInstance().login(loginName,
							loginPwd, 1);
				} else {
					errRes = CldErrUtil.errDeal();
				}
				if (callBack != null) {
					callBack.onResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ�û���Ϣ.
	 * 
	 * @return
	 * @return CldUserInfo
	 * @author Zhouls
	 * @date 2015-3-5 ����2:35:45
	 */
	public CldUserInfo getUserInfoDetail() {
		return CldDalKAccount.getInstance().getCldUserInfo();
	}

	/**
	 * ��ȡ�û���Ϣ(����).
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-2-12 ����11:17:47
	 */
	public void getUserInfo() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().getUserInfo();
				if (null != listener) {
					listener.onGetUserInfoResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ע��.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-9-2 ����10:11:12
	 * 
	 */
	public void loginOut() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().loginOut();
				if (null != listener) {
					listener.onLoginOutResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ע����¼
	 * 
	 * @author ligangfan
	 * @date 2017-3-21
	 * @param callBack
	 */
	public void loginOut(final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().loginOut();
				if (callBack != null) {
					callBack.onResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * �Զ���¼.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:36:27
	 * 
	 */
	public void startAutoLogin() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldKAccount.getInstance().startAutoLogin(
						new IAutoLoginListener() {
							@Override
							public void onLoginStateChange(int loginState,
									CldSapReturn errRes) {
								if (null != listener) {
									listener.onAutoLoginResult(loginState,
											errRes.errCode);
								}
							}
						});
			}
		}).start();
	}

	/**
	 * �Զ���¼
	 * 
	 * @author ligangfan
	 * @date 2017-3-21
	 * @param callBack
	 */
	public void startAutoLogin(final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldKAccount.getInstance().startAutoLogin(
						new IAutoLoginListener() {
							@Override
							public void onLoginStateChange(int loginState,
									CldSapReturn errRes) {
								if (callBack != null) {
									callBack.onResult(errRes.errCode);
								}
							}
						});
			}
		}).start();
	}

	/**
	 * ��ȡ�ֻ���֤��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param bussinessCode
	 *            ҵ������ 101ע�� 102�� 103�İ� 104��� 105�������� 106��ݵ�¼
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:54:05
	 */
	public void getVerifyCode(final String mobile, final int bussinessCode,
			final String oldmoble) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().getVerifyCode(
						mobile, bussinessCode, oldmoble);
				if (null != listener) {
					listener.onGetVerifyCode(errRes.errCode, bussinessCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ��¼����֤��
	 * 
	 * @author ligangfan
	 * @date 2017-3-21
	 * @param mobile
	 * @param callBack
	 */
	public void getVerifyCodeToLogin(final String mobile,
			final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				CldSapReturn errRes = CldKAccount.getInstance().getVerifyCode(
						mobile, 106, null);
				if (callBack != null) {
					callBack.onResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ�İ��ֻ�����֤��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param oldmobile
	 *            ���ֻ���
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-10 ����5:40:06
	 * 
	 */
	public void getVerCodeToReviseMobile(final String mobile,
			final String oldmoble) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().getVerifyCode(
						mobile, 103, oldmoble);
				if (null != listener) {
					listener.onGetVerifyCode(errRes.errCode, 103);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ�İ��ֻ�����֤��
	 * 
	 * @author ligangfan
	 * @date 2017-3-21
	 * @param mobile
	 * @param oldmoble
	 * @param callBack
	 */
	public void getVerCodeToReviseMobile(final String mobile,
			final String oldmoble, final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().getVerifyCode(
						mobile, 103, oldmoble);
				if (callBack != null) {
					callBack.onResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ���ж���ע��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param password
	 *            ����
	 * @param verifyCode
	 *            �ֻ���֤�루bussinessCode=101��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:54:40
	 */
	public void register(final String mobile, final String password,
			final String verifyCode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().register(
						mobile, password, verifyCode);
				String loginName = "";
				long kuid = 0;
				if (errRes.errCode == 0) {
					loginName = CldDalKAccount.getInstance().getLoginNameReg();
					kuid = CldDalKAccount.getInstance().getKuidReg();
				}
				if (null != listener) {
					listener.onRegister(errRes.errCode, kuid, loginName);
				}
			}
		}).start();
	}

	/**
	 * ͨ���ֻ���֤��������
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param newPwd
	 *            ������
	 * @param verifyCode
	 *            �ֻ���֤�루bussinessCode=105��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:55:18
	 */
	public void retrievePwd(final String mobile, final String newPwd,
			final String verifyCode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().retrievePwd(
						mobile, newPwd, verifyCode);
				if (null != listener) {
					listener.onRetrievePwd(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * У���ֻ���֤���Ƿ���ȷ
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ��֤��
	 * @param bussinessCode
	 *            ҵ��ID
	 * @return void
	 * @author Zhouls
	 * @date 2015-10-9 ����10:10:08
	 */
	public void checkMobileVeriCode(final String mobile,
			final String verifycode, final int bussinesscode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance()
						.checkMobileVeriCode(mobile, verifycode, bussinesscode);
				if (null != listener) {
					listener.onCheckMobileVeriCode(errRes.errCode,
							bussinesscode);
				}
			}
		}).start();
	}

	/**
	 * �����˺�ע��
	 * 
	 * @param username
	 *            �����˺�
	 * @param password
	 *            ����
	 * @param autoLoginAfterRegistered
	 *            �Ƿ�ע��ɹ����Զ���½
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:54:40
	 */
	public void registerEx(final String username, final String password,
			final boolean autoLoginAfterRegistered) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().registerEx(
						username, password);
				String loginName = "";
				long kuid = 0;
				if (errRes.errCode == 0) {
					loginName = CldDalKAccount.getInstance().getLoginNameReg();
					kuid = CldDalKAccount.getInstance().getKuidReg();
					if (autoLoginAfterRegistered) {
						CldKAccount.getInstance().startAutoLogin(
								new IAutoLoginListener() {
									@Override
									public void onLoginStateChange(
											int loginState, CldSapReturn errRes) {
										if (null != listener) {
											listener.onAutoLoginResult(
													loginState, errRes.errCode);
										}
									}
								});
					}
				}
				if (null != listener) {
					listener.onRegister(errRes.errCode, kuid, loginName);
				}
			}
		}).start();
	}

	/**
	 * �޸�����
	 * 
	 * @param oldPwd
	 *            ������
	 * @param newPwd
	 *            ������
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:55:43
	 * 
	 */
	public void revisePwd(final String oldPwd, final String newPwd) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().revisePwd(
						oldPwd, newPwd);
				if (null != listener) {
					listener.onRevisePwd(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * �޸�����
	 * 
	 * @author ligangfan
	 * @date 2017-3-21
	 * @param oldPwd
	 * @param newPwd
	 * @param callBack
	 */
	public void revisePwd(final String oldPwd, final String newPwd,
			final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().revisePwd(
						oldPwd, newPwd);
				if (callBack != null) {
					callBack.onResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * �����û���Ϣ(�ɲ��ָ���)
	 * 
	 * @param username
	 *            �û���
	 * @param useralias
	 *            ����
	 * @param email
	 *            ����(�� ��null)
	 * @param mobile
	 *            �ֻ�(�� ��null)
	 * @param sex
	 *            �Ա�"0","1" ���Ĵ�null��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:56:40
	 */
	public void updateUserInfo(final String username, final String useralias,
			final String email, final String mobile, final String sex,
			final String address, final byte[] photoData) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().updateUserInfo(
						username, useralias, email, mobile, sex, address,
						photoData);
				
				if (null != listener) {
					listener.onUpdateUserInfo(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ���ֻ���
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-19 ����12:17:31
	 * 
	 * @note ����һ���ص�������������ڷ��ؽ��
	 * @author ligangfan
	 * @date 2017-3-21
	 */
	public void bindMobile(final String mobile, final String verifycode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().bindMobile(
						mobile, verifycode);
				if (null != listener) {
					listener.onBindMobile(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ���ֻ���
	 * 
	 * @author ligangfan
	 * @date 2017-3-21
	 * @param mobile
	 * @param verifycode
	 * @param callBack
	 */
	public void bindMobile(final String mobile, final String verifycode,
			final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().bindMobile(
						mobile, verifycode);
				if (callBack != null) {
					callBack.onResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ����ֻ���
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            ������֤��
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-19 ����1:00:52
	 */
	public void unbindMobile(final String mobile, final String verifycode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().unbindMobile(
						mobile, verifycode);
				if (null != listener) {
					listener.onUnbindMobile(errRes.errCode);
				}
			}
		}).start();
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
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-19 ����12:46:49
	 */
	public void updateMobile(final String mobile, final String oldmobile,
			final String verifycode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().updateMobile(
						mobile, oldmobile, verifycode);
				if (null != listener) {
					listener.onUpdateMobile(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ݵ�¼
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param verifycode
	 *            106��ȡ����֤��
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-22 ����9:56:54
	 * 
	 */
	public void fastLogin(final String mobile, final String verifycode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().fastLogin(
						mobile, verifycode);
				if (null != listener) {
					listener.onLoginResult(errRes.errCode, true);
				}
			}
		}).start();
	}

	/**
	 * ��ݵ�¼
	 * 
	 * @author ligangfan
	 * @date 2017-3-21
	 * @param mobile
	 * @param verifycode
	 * @param callBack
	 */
	public void fastLogin(final String mobile, final String verifycode,
			final OnAccountResponseCallBack callBack) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().fastLogin(
						mobile, verifycode);
				if (callBack != null) {
					callBack.onResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ���������¼������ͨ�ʻ���¼���ĵ�¼����
	 * 
	 * @return �������û��������û���¼�� 1,���û�ע�ᣬ2���û���¼
	 * @return int
	 * @author Zhouls
	 * @date 2015-7-3 ����9:19:49
	 */
	public int getSpecialLoginStatus() {
		return CldDalKAccount.getInstance().getCldUserInfo().getStatus();
	}

	/**
	 * ��ݵ�¼���޸�����
	 * 
	 * @param newPwd
	 *            ������
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-20 ����11:23:29
	 */
	public void revisePwdByFastPwd(final String newPwd) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance()
						.revisePwdByFastPwd(newPwd);
				if (null != listener) {
					listener.onRevisePwdByFastLogin(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ��ά���ֵ
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-1-21 ����5:11:34
	 */
	public String getQRcodeValue() {
		int ns = 1;
		if (!CldPhoneNet.isNetConnected()) {
			ns = 0;
		}
		String guid = "cldqr://f=l&p=" + CldDalKAccount.getInstance().getGuid()
				+ "&ns=" + ns + "&apptype="
				+ CldBllUtil.getInstance().getApptype();
		return guid;
	}

	/**
	 * ��ȡɨ���¼�Ķ�ά��
	 * 
	 * @param QRsideLength
	 *            ��ά��߳�(����)(�����Ҫ��������ɵĶ�ά��ͼƬ�����������ݣ��򴫴�ֵ������0����ȡguid �ն�����)
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-19 ����9:34:35
	 */
	public void getQRcode(final int QRsideLength) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().getQRcode(
						QRsideLength);
				if (null != listener) {
					listener.onGetQRcodeResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ά���¼����
	 * 
	 * @author Zhouls
	 * @date 2015-1-26 ����11:38:53
	 */
	public interface IQRLoginResultListener {
		/**
		 * ��ά���¼�ص�
		 * 
		 * @param errCode
		 *            ��0 �ɹ���
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-26 ����11:38:45
		 */
		public void onLoginByQRcodeResult(int errCode);
	}

	/**
	 * ��ά���¼
	 * 
	 * @param guid
	 *            Ψһ��ʶ��,ɨ���ά��õ�������
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-19 ����9:47:47
	 */
	public void loginByQRcode(final String guid,
			final IQRLoginResultListener qrlistener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().loginByQRcode(
						guid);
				if (null != qrlistener) {
					qrlistener.onLoginByQRcodeResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ��ά���¼״̬��errCode=0,��¼�ɹ���
	 * 
	 * @param guid
	 *            Ψһ��ʶ��,��ȡ��ά��ʱ������
	 * @return void
	 * @author Zhouls
	 * @date 2015-1-19 ����9:58:31
	 */
	public void getLoginStatusByQRcode(final String guid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance()
						.getLoginStatusByQRcode(guid);
				while (errRes.errCode != 0) {
					try {
						Thread.sleep(15000);
						errRes = CldKAccount.getInstance()
								.getLoginStatusByQRcode(guid);
						if (null != listener) {
							listener.onGetQRLoginStatusResult(errRes.errCode);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * ��������¼
	 * 
	 * @param openid
	 *            �ӵ�������ȡ��Ψһopenid
	 * @param cldThirdLoginType
	 *            ��������¼����
	 * @return void
	 * @author Zhouls
	 * @date 2015-10-21 ����5:12:15
	 */
	public void thirdLogin(final String openid,
			final CldThirdLoginType cldThirdLoginType) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKAccount.getInstance().thirdLogin(
						openid, cldThirdLoginType);
				if (null != listener) {
					listener.onThirdLoginResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * sessionʧЧ
	 * 
	 * @param errCode
	 *            �ӿڷ��صĴ�����
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����2:58:06
	 */
	public void sessionInvalid(int errCode, int bussiness) {
		if (errCode == 500) {
			/**
			 * 500�Զ���¼
			 */
			CldKAccount.getInstance().sessionRelogin();
		} else if (errCode == 501) {
			uninit();
			if (null != listener) {
				listener.onInValidSession(bussiness);
			}
		}
	}

	/**
	 * �ʻ�ϵͳ�ص�����.
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����2:58:28
	 */
	public static interface ICldKAccountListener {

		/**
		 * �Ƿ���ע��ص�.
		 * 
		 * @param errCode
		 *            (0,401��411��412��413��414��415��416��417��402 ��910��101)
		 * @param kuid
		 *            errCode=101���ش��û�kuid��errCode=0,�û�δע�ᣩ
		 * @param loginName
		 *            �ش��жϵĵ�¼��
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����2:59:33
		 */
		public void onIsRegUser(int errCode, long kuid, String loginName);

		/**
		 * �����û���Ϣ�ص�.
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:00:02
		 */
		public void onUpdateUserInfo(int errCode);

		/**
		 * ��ȡ�ֻ���֤��ص�.
		 * 
		 * @param errCode
		 *            0�ɹ� ; 201�ֻ����ѱ���֤;202�ֻ�δ��֤;500��¼��ʧЧ;501�����µ�¼;
		 *            903XX���ڲ����ظ�������֤��;906���ʹ����Ѵ��������; 910����ʱ���ѹ���;
		 * @param bussinessid
		 *            the bussinessid
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:00:16
		 */
		public void onGetVerifyCode(int errCode, int bussinessid);

		/**
		 * ���ж���ע��ص�.
		 * 
		 * @param errCode
		 *            101�û����Ѵ���; 201�ֻ����ѱ���֤;503ע��ʧ��;
		 *            907��֤���ѹ���;908��֤�벻��ȷ;909��֤����Ч��;
		 * @param kuid
		 *            errCode=0,�ش�ע���û�Kuid
		 * @param loginName
		 *            errCode=0,�ش���¼��
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:00:54
		 */
		public void onRegister(int errCode, long kuid, String loginName);

		/**
		 * ���ж���ע��ص�.
		 * 
		 * @param errCode
		 *            (402 201��503 ,911,912 ,913 ,914),
		 *            (-1:���ն����ӳ�,-2:Sim���쳣,-3�������쳣)
		 * @param kuid
		 *            errCode=0,�ش�ע���û�Kuid
		 * @param loginName
		 *            errCode=0,�ش���¼��
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:01:15
		 */
		public void onRegBySms(int errCode, long kuid, String loginName);

		/**
		 * ͨ���ֻ���֤��������ص�.
		 * 
		 * @param errCode
		 *            907��֤���ѹ���;908��֤�벻��ȷ;909��֤����Ч��202�ֻ���δ��֤105�޸�����ʧ��
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:01:43
		 */
		public void onRetrievePwd(int errCode);

		/**
		 * �޸�����ص�.
		 * 
		 * @param errCode
		 *            500��¼ʧЧ��501�����µ�¼��104�������105�޸�����ʧ��
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:02:00
		 */
		public void onRevisePwd(int errCode);

		/**
		 * У���ֻ���֤���Ƿ���ȷ
		 * 
		 * @param errCode
		 * @param bussinessid
		 * @return void
		 * @author Zhouls
		 * @date 2015-8-10 ����2:32:05
		 */
		public void onCheckMobileVeriCode(int errCode, int bussinessid);

		/**
		 * ��¼�ص�.
		 * 
		 * @param errCode
		 *            ��¼�ӿڴ�����
		 * @param isFastLogin
		 *            �Ƿ��ǿ�ݵ�¼���ά���¼
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-21 ����3:47:53
		 */
		public void onLoginResult(int errCode, boolean isFastLogin);

		/**
		 * ��ȡ�û���Ϣ�ص�
		 * 
		 * @param errCode
		 *            ��ȡ�û���Ϣ�ӿڴ�����
		 * @return void
		 * @author Zhouls
		 * @date 2015-2-12 ����9:57:17
		 */
		public void onGetUserInfoResult(int errCode);

		/**
		 * ע���ص�.
		 * 
		 * @param errCode
		 *            (errCode=0 ע���ɹ� ������������룬Kuid )
		 * @return void
		 * @author Zhouls
		 * @date 2014-9-2 ����10:12:55
		 */
		public void onLoginOutResult(int errCode);

		/**
		 * �Զ���¼�ص�.
		 * 
		 * @param loginState
		 *            �Զ���¼״̬
		 * @param errCode
		 *            �Զ���¼������
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:02:38
		 */
		public void onAutoLoginResult(int loginState, int errCode);

		/**
		 * session ʧЧ�ص�
		 * 
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:02:57
		 */
		public void onInValidSession(int bussiness);

		/**
		 * ���ֻ��Żص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-19 ����12:18:48
		 */
		public void onBindMobile(int errCode);

		/**
		 * ����ֻ��Żص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-19 ����1:02:27
		 */
		public void onUnbindMobile(int errCode);

		/**
		 * �İ��ֻ��Żص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-19 ����12:46:20
		 */
		public void onUpdateMobile(int errCode);

		/**
		 * ��ݵ�¼�޸�����ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-20 ����11:25:03
		 */
		public void onRevisePwdByFastLogin(int errCode);

		/**
		 * ��ȡ��¼��ά��ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-19 ����9:34:02
		 */
		public void onGetQRcodeResult(int errCode);

		/**
		 * ��ȡ��ά���¼״̬�ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-1-21 ����5:01:39
		 */
		public void onGetQRLoginStatusResult(int errCode);

		/**
		 * ��������¼�ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-6-10 ����9:55:44
		 */
		public void onThirdLoginResult(int errCode);
	}

	/**
	 * ��ȡ��ǰ��¼�ʻ���Kuid.
	 * 
	 * @return
	 * @return long
	 * @author Zhouls
	 * @date 2015-3-5 ����3:03:40
	 */
	public long getKuidLogin() {
		return CldDalKAccount.getInstance().getKuid();
	}

	/**
	 * ��ȡ��ǰ��¼�ʻ���session
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:03:52
	 */
	public String getSession() {
		return CldDalKAccount.getInstance().getSession();
	}

	/**
	 * ��ȡ��ǰ�豸��duid.
	 * 
	 * @return
	 * @return long
	 * @author Zhouls
	 * @date 2015-3-5 ����3:04:13
	 */
	public long getDuid() {
		return CldDalKAccount.getInstance().getDuid();
	}

	/**
	 * ��ȡ��������루MD5ֵ��.
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:04:23
	 */
	public String getLoginPwd() {
		return CldKAccount.getInstance().getLoginPwd();
	}

	/**
	 * ��ȡ��ǰ��¼�ʺŵĵ�¼��.
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:04:32
	 */
	public String getLoginName() {
		return CldKAccount.getInstance().getLoginName();
	}

	/**
	 * ��ȡ��ǰ��¼�ʺŵ��û���
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:04:43
	 */
	public String getUserName() {
		return CldDalKAccount.getInstance().getCldUserInfo().getUserName();
	}

	/**
	 * ��ȡ��¼У��ʹ�õ�bussiness
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:04:53
	 */
	public int getBusinessid() {
		return CldBllUtil.getInstance().getBussinessid();
	}

	/**
	 * ��ȡ�����ʱ��
	 * 
	 * @return
	 * @return long
	 * @author Zhouls
	 * @date 2015-3-5 ����3:05:04
	 */
	public long getSvrTime() {
		return CldKAccount.getInstance().getSvrTime();
	}

	/**
	 * ��ȡ����ע��ɹ��󷵻صĵ�¼��.
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:05:14
	 */
	public String getLoginNameRegSms() {
		return CldDalKAccount.getInstance().getLoginNameRegSms();
	}

	/**
	 * ��ȡע��ɹ��󷵻صĵ�¼��.
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:05:24
	 */
	public String getLoginNameReg() {
		return CldDalKAccount.getInstance().getLoginNameReg();
	}

	/**
	 * �жϵ�ǰ�Ƿ��¼����ȡ�û���Ϣ�󶨣��ɹ�.
	 * 
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-3-5 ����3:05:33
	 */
	public boolean isLogined() {
		return CldKAccount.getInstance().getLoginStatus() == 2 ? true : false;
	}

	/**
	 * �޸�����󱣴�������
	 * 
	 * @param newPwd
	 *            ������
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:05:44
	 */
	public void setNewPwd(String newPwd) {
		CldKAccount.getInstance().setLoginPwd(newPwd);
	}

	/**
	 * ������ر����kuid.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:05:57
	 */
	public void cleanKuid() {
		CldSetting.put("kuid", "");
	}

	/**
	 * ������ر����session.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:06:07
	 */
	public void cleanSession() {
		CldSetting.put("session", "");
	}

	/**
	 * ������ر��������.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:06:18
	 */
	public void cleanPassword() {
		CldSetting.put("password", "");
	}

	/**
	 * ������ر���ĵ�¼��.
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:06:29
	 */
	public void cleanLoginName() {
		CldSetting.put("userName", "");
	}

	/**
	 * ��ȡ�󶨵��ֻ���
	 * 
	 * @return �󶨵��ֻ��Ż���""��δ��¼������δ���ֻ��ţ�
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:06:38
	 */
	public String getBindMobile() {
		if (isLogined()) {
			CldUserInfo cldUserInfo = CldDalKAccount.getInstance()
					.getCldUserInfo();
			if (cldUserInfo.getMobileBind() == 1) {
				return cldUserInfo.getMobile();
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * 
	 * ��¼״̬
	 * 
	 * @author Zhouls
	 * @date 2015-4-2 ����4:00:27
	 */
	public static class CldLoginStatus {
		/** δ��¼ */
		public static final int LOGIN_NONE = 0;
		/** ���ڵ�¼ */
		public static final int LOGIN_DOING = LOGIN_NONE + 1;
		/** �ѵ�¼ */
		public static final int LOGIN_DONE = LOGIN_DOING + 1;
	}

	/**
	 * ��ȡ��¼״̬��CldLoginStatus��
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-4-2 ����3:57:20
	 */
	public int getLoginStatus() {
		return CldKAccount.getInstance().getLoginStatus();
	}

	/**
	 * 
	 * ��ȡ�û���Ϣ�ص�
	 * 
	 * @author Zhouls
	 * @date 2016-5-16 ����5:10:49
	 */
	public static interface ICldGetUserInfoListener {
		/**
		 * ����ע��
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2016-5-16 ����4:51:57
		 */
		public void onGetUserInfoResult(int errCode);
	}

	/**
	 * �ύ�Ǽ���֤��Ϣ
	 * 
	 * @param driveLicenceData
	 *            ��ʻ֤��Ϣ
	 * @param vehicleLicenceData
	 *            ��ʻ֤��Ϣ
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-6 ����3:30:37
	 */
	public void uploadStarAuth(byte[] driveLicenceData,
			byte[] vehicleLicenceData, ICldResultListener listener) {
		CldKAccount.getInstance().uploadStarAuth(driveLicenceData,
				vehicleLicenceData, listener);
	}

	/**
	 * 
	 * ��֤��ҵ��ö��
	 * 
	 * @author Zhouls
	 * @date 2015-5-6 ����9:52:08
	 */
	public enum CldBussinessCode {
		REGISTER, BIND_MOBILE, BIND_EMAIL, MODIFY_MOBILE, MODIFY_EMAIL, UNBIND_MOBILE, UNBIND_EMAIL, RESET_PWD, FAST_LOGIN;
		/**
		 * ��ȡ��Ӧ��ҵ��ID
		 * 
		 * @param bussinessCode
		 * @return
		 * @return int
		 * @author Zhouls
		 * @date 2015-5-6 ����9:52:56
		 */
		public static int valueOf(CldBussinessCode bussinessCode) {
			switch (bussinessCode) {
			case REGISTER:
				/**
				 * ���ж���ע��
				 */
				return 101;
			case BIND_MOBILE:
			case BIND_EMAIL:
				/**
				 * ���ֻ�������
				 */
				return 102;
			case MODIFY_MOBILE:
			case MODIFY_EMAIL:
				/**
				 * �޸��ֻ�
				 */
				return 103;
			case UNBIND_MOBILE:
			case UNBIND_EMAIL:
				/**
				 * ����ֻ�
				 */
				return 104;
			case RESET_PWD:
				/**
				 * ��������
				 */
				return 105;
			case FAST_LOGIN:
				/**
				 * ��ݵ�¼
				 */
				return 106;
			default:
				return 0;
			}
		}

		/**
		 * ͨ��bussinessCode��ȡ��Ӧ��ö��
		 * 
		 * @param bussinessCode
		 * @return
		 * @return CldBussinessCode
		 * @author Zhouls
		 * @date 2015-5-21 ����8:55:16
		 */
		public static CldBussinessCode value(int bussinessCode) {
			switch (bussinessCode) {
			case 101:
				/**
				 * ���ж���ע��
				 */
				return REGISTER;
			case 102:
				/**
				 * ���ֻ�
				 */
				return BIND_MOBILE;
			case 103:
				/**
				 * �޸��ֻ�
				 */
				return MODIFY_MOBILE;
			case 104:
				/**
				 * ����ֻ�
				 */
				return UNBIND_MOBILE;
			case 105:
				/**
				 * ��������
				 */
				return RESET_PWD;
			case 106:
				/**
				 * ��ݵ�¼
				 */
				return FAST_LOGIN;
			default:
				return null;
			}
		}
	}
}
