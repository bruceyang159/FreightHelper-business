/*
 * @Title CldKCallNavi.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.bll;

import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;
import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.dal.CldDalKCallNavi;
import com.mtq.ols.sap.CldSapKCallNavi;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtKeyCode;
import com.mtq.ols.sap.parse.CldKCallNaviParse.ProtKCallMobile;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;


/**
 * 
 * һ��ͨ
 * 
 * @author Zhouls
 * @date 2015-4-8 ����4:35:23
 */
public class CldKCallNavi {

	private static CldKCallNavi cldKCallNavi;

	/**
	 * Instantiates a new cld k call navi.
	 */
	private CldKCallNavi() {

	}

	/**
	 * Gets the single instance of CldKCallNavi.
	 * 
	 * @return single instance of CldKCallNavi
	 */
	public static CldKCallNavi getInstance() {
		if (cldKCallNavi == null)
			cldKCallNavi = new CldKCallNavi();
		return cldKCallNavi;
	}

	/**
	 * Uninit.
	 */
	public void uninit() {
		CldDalKCallNavi.getInstance().uninit();
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:20:09
	 */
	public void initKey() {
		String cldKCallKey = CldDalKCallNavi.getInstance().getCldKCallKey();
		// ֻҪû���жϵ�¼
		int slpTimer = 5;// ��������
		while (TextUtils.isEmpty(cldKCallKey)) {
			int sleepSc = 0;// ��������
			if (!CldPhoneNet.isNetConnected()) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				continue;
			}
			CldSapReturn errRes = CldSapKCallNavi.initKeyCode();
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtKeyCode protKeyCode = CldSapParser.parseJson(strRtn,
					ProtKeyCode.class, errRes);
			CldLog.d("ols", errRes.errCode + "_initKCallKey");
			CldLog.d("ols", errRes.errMsg + "_initKCallKey");
			CldErrUtil.handleErr(errRes);
			if (null != protKeyCode && errRes.errCode == 0
					&& !TextUtils.isEmpty(protKeyCode.getCode())) {
				cldKCallKey = protKeyCode.getCode();
				CldDalKCallNavi.getInstance().setCldKCallKey(cldKCallKey);
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
		CldSapKCallNavi.keyCode = cldKCallKey;
	}

	/**
	 * ע��һ��ͨ�ʺ�
	 * 
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:20:27
	 */
	public CldSapReturn registerByKuid() {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCallNavi.registerByKuid(CldBllUtil.getInstance()
					.getApptype(), CldBllUtil.getInstance().getProver(),
					CldDalKAccount.getInstance().getKuid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getBussinessid());
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_pptRegByKuid");
			CldLog.d("ols", errRes.errMsg + "_pptRegByKuid");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		}
		return errRes;
	}

	/**
	 * ��ȡһ��ͨ�ֻ���֤��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:20:36
	 */
	public CldSapReturn getIdentifycode(String mobile) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCallNavi.getIdentifycode(CldBllUtil.getInstance()
					.getApptype(), CldBllUtil.getInstance().getProver(),
					CldDalKAccount.getInstance().getKuid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getBussinessid(), mobile);
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_ppt_getIden");
			CldLog.d("ols", errRes.errMsg + "_ppt_getIden");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		}
		return errRes;
	}

	/**
	 * ��ȡ���ֻ�����
	 * 
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-12 ����3:24:56
	 */
	public CldSapReturn getBindMobile() {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCallNavi.getBindMobile(CldBllUtil.getInstance()
					.getApptype(), CldBllUtil.getInstance().getProver(),
					CldDalKAccount.getInstance().getKuid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getBussinessid());
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtKCallMobile protKCallMobile = CldSapParser.parseJson(strRtn,
					ProtKCallMobile.class, errRes);
			CldLog.d("ols", errRes.errCode + "_ppt_mobiles");
			CldLog.d("ols", errRes.errMsg + "_ppt_mobiles");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protKCallMobile) {
				if (errRes.errCode == 0) {
					CldDalKCallNavi.getInstance().setMobiles(
							protKCallMobile.getData());
				}
			}
		}
		return errRes;
	}

	/**
	 * �������ֻ���
	 * 
	 * @param identifycode
	 *            ��֤��
	 * @param mobile
	 *            �ֻ�����
	 * @param replacemobile
	 *            Ҫ�滻�ľɺ��룬 ��Ϊ�����滻�󶨵ľɺ��룻Ϊ���������°󶨺���
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:20:49
	 */
	public CldSapReturn bindToMobile(String identifycode, String mobile,
			String replacemobile) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCallNavi.bindToMobile(CldBllUtil.getInstance()
					.getApptype(), CldBllUtil.getInstance().getProver(),
					CldDalKAccount.getInstance().getKuid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getBussinessid(), identifycode, mobile,
					replacemobile);
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_pptBindMobile");
			CldLog.d("ols", errRes.errMsg + "_pptBindMobile");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				List<String> mobiles = new ArrayList<String>();
				mobiles.add(mobile);
				CldDalKCallNavi.getInstance().setMobiles(mobiles);
			}
		}
		return errRes;
	}

	/**
	 * ɾ�����ֻ���
	 * 
	 * @param mobile
	 *            �ֻ�����
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:21:07
	 */
	public CldSapReturn delBindMobile(String mobile) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCallNavi.delBindMobile(CldBllUtil.getInstance()
					.getApptype(), CldBllUtil.getInstance().getProver(),
					CldDalKAccount.getInstance().getKuid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getBussinessid(), mobile);
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_pptDelMobile");
			CldLog.d("ols", errRes.errMsg + "_pptDelMobile");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		}
		return errRes;
	}

	/**
	 * �����봦��
	 * 
	 * @param res
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:21:22
	 */
	public void errCodeFix(CldSapReturn res) {
		switch (res.errCode) {
		case 402: {
			/**
			 * ��Կ����
			 */
			CldDalKCallNavi.getInstance().setCldKCallKey("");
			initKey();
		}
			break;
		case 500: {
			/**
			 * sessionʧЧ�Զ���¼һ��ˢ��session
			 */
			CldKAccount.getInstance().sessionRelogin();
		}
			break;
		case 501:
			/**
			 * sessionʧЧ
			 */
			if (res.session.equals(CldDalKAccount.getInstance().getSession())) {
				/**
				 * ���ӿ�ʹ��session�͵�ǰ�ʻ����session��ͬ�ż�����
				 */
				if (!TextUtils.isEmpty(res.session)) {
					CldKAccountAPI.getInstance().sessionInvalid(501, 0);
				}
			}
			break;
		}
	}
}
