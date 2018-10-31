package com.mtq.ols.bll;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.api.CldKAppCenterAPI.IAppsUpgradeListener;
import com.mtq.ols.api.CldKAppCenterAPI.IGetRigonListener;
import com.mtq.ols.api.CldKAppCenterAPI.ILogoTipsListener;
import com.mtq.ols.api.CldKAppCenterAPI.IUpgradeListener;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.dal.CldDalKAppCenter;
import com.mtq.ols.sap.CldSapKAppCenter;
import com.mtq.ols.sap.CldSapUtil;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfo;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfoNew;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqLogoTips;
import com.mtq.ols.sap.parse.CldKAccountParse;
import com.mtq.ols.sap.parse.CldKAppCenterParse;
import com.mtq.ols.sap.parse.CldKAppCenterParse.ProtAppInfo;
import com.mtq.ols.sap.parse.CldKAppCenterParse.ProtAppStatus;
import com.mtq.ols.sap.parse.CldKAppCenterParse.ProtLogoTips;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtKeyCode;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;

public class CldKAppCenter {

	private static CldKAppCenter cldKAppCenter;

	private CldKAppCenter() {

	}

	public static CldKAppCenter getInstance() {
		if (cldKAppCenter == null)
			cldKAppCenter = new CldKAppCenter();
		return cldKAppCenter;
	}

	/**
	 * ��ʼ����Կ
	 */
	public void initKey() {
		if (TextUtils.isEmpty(CldSapKAppCenter.getKgoKey())) {
			CldSapReturn errRes = CldSapKAppCenter.initKeyCode();
			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldLog.e("ols", "initKey strRtn:" + strRtn);
				ProtKeyCode protKeyCode = CldSapParser.parseJson(strRtn,
						ProtKeyCode.class, errRes);
				CldLog.d("ols", errRes.errCode + "");
				CldLog.d("ols", errRes.errMsg);
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
				if (null != protKeyCode && errRes.errCode == 0
						&& !TextUtils.isEmpty(protKeyCode.getCode())) {
					String key = CldSapUtil.decodeKey(protKeyCode.getCode());
					CldLog.i("ols", "key= " + key);
					CldSapKAppCenter.setKgoKey(key);
				}
			}
		}
	}
	
	/**
	 * Ӧ��������
	 * 
	 * @param width
	 *            �ֱ��ʿ�
	 * @param height
	 *            �ֱ��ʸ�
	 * @param vercode
	 *            �汾��
	 * 
	 * @return CldSapReturn
	 */
	public void getUpgradeForNew(int width, int height, int vercode,
			IUpgradeListener listener) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAppCenter.getUpgradeForNew(width, height, vercode);
			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url); 
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
				CldLog.d("ols", errRes.errCode + "_getAppUpgrade");
				CldLog.d("ols", errRes.errMsg + "_getAppUpgrade");
				errCodeFix(errRes);
				if (errRes.errCode == 0) {
					MtqAppInfoNew result = CldKAppCenterParse.parseAppInfoForNew(strRtn);
					if (result != null) {
						/**
						 * 1. ��̨���صİ汾�Ŵ��ڴ���İ汾��ʱ���������°汾
						 * 2. expiredtime ��ֹʱ����ڵ�ǰʱ�䣬�������汾��Ч
						 */
						long curtime = System.currentTimeMillis() / 1000;
						if (result.version > vercode && result.expiredtime >= curtime) {
							if (listener != null) {
								listener.onResult(errRes.errCode, result);
								CldDalKAppCenter.getInstance().setMtqAppInfo(result);
								CldDalKAppCenter.getInstance().setNewVersion(true);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null);
							}
						}
					} else {
						if (listener != null) {
							listener.onResult(errRes.errCode, null);
						}
					}
				} else {
					if (listener != null) {
						listener.onResult(errRes.errCode, null);
					}
				}
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
			if (listener != null) {
				listener.onResult(errRes.errCode, null);
			}
		}
	}

	/**
	 * ����Ѱ�װ��app�Ƿ�������
	 * 
	 * @param width
	 *            �ֱ��ʿ�
	 * @param height
	 *            �ֱ��ʸ�
	 * @param dpi
	 *            dpi
	 * @param systemVer
	 *            android ϵͳ�汾
	 * @param page
	 *            ҳ��
	 * @param size
	 *            ÿҳ��¼��
	 * @param launcherVer
	 *            launcher�汾��
	 * @param duid
	 *            duid
	 * @param kuid
	 *            kuid
	 * @param regionId
	 *            ����id
	 * @param customCode
	 *            �ͻ����
	 * @param planCode
	 *            �����̱��
	 * @param appParms
	 *            �Ѱ�װapp
	 * 
	 * @return CldSapReturn
	 */
	public void getAppsUpgrade(int width, int height, int dpi,
			String systemVer, int page, int size, String launcherVer,
			long duid, long kuid, int regionId, int customCode, int planCode,
			List<MtqAppInfo> appParms, IAppsUpgradeListener listener) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAppCenter.getAppsUpgrade(width, height, dpi,
					systemVer, page, size, launcherVer, duid, kuid, regionId,
					customCode, planCode, appParms);
			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
				CldLog.d("ols", errRes.errCode + "_getAppUpgrade");
				CldLog.d("ols", errRes.errMsg + "_getAppUpgrade");
				errCodeFix(errRes);
				if (errRes.errCode == 0) {
					List<MtqAppInfo> lstOfResult = new ArrayList<MtqAppInfo>();
					CldKAppCenterParse.parseAppInfos(strRtn, lstOfResult);
					if (listener != null) {
						listener.onListResult(errRes.errCode, lstOfResult);
					}
				}
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
	}

	/**
	 * ��ȡ��Ӫƽ̨�Ƽ���Ӧ���б�(���ų��ն����Ѱ�װ��Ӧ��)
	 * 
	 * @param systemCode
	 *            ����ϵͳ����(��Ӫƽ̨����)
	 * @param deviceCode
	 *            �豸�ͺű���(��Ӫƽ̨����)
	 * @param productCode
	 *            ��Ʒ�ͺű���(��Ӫƽ̨����)
	 * @param width
	 *            �ֱ��ʿ�
	 * @param height
	 *            �ֱ��ʸ�
	 * @param page
	 *            ҳ��
	 * @param size
	 *            ÿҳ��¼��
	 * @param launcherVer
	 *            launcher�汾��
	 * @param appParms
	 *            �Ѱ�װapp
	 * 
	 * @return CldSapReturn
	 */
	public CldSapReturn getRecdApp(int width, int height, int page, int size,
			String launcherVer, List<MtqAppInfo> appParms) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAppCenter.getRecdApp(width, height, page, size,
					launcherVer, appParms);
			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtAppInfo.class, errRes);
				CldErrUtil.handleErr(errRes);
				CldLog.d("ols", errRes.errCode + "_getRecdApp");
				CldLog.d("ols", errRes.errMsg + "_getRecdApp");
				errCodeFix(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}

	/**
	 * ��ȡӦ��״̬��Ϣ(�������¼�app�İ���)
	 * 
	 * @param appParms
	 *            �Ѱ�װapp
	 * 
	 * @return CldSapReturn
	 */
	public CldSapReturn getAppStatus(List<MtqAppInfo> appParms) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAppCenter.getAppStatus(appParms);
			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtAppStatus.class, errRes);
				CldErrUtil.handleErr(errRes);
				CldLog.d("ols", errRes.errCode + "_getAppStatus");
				CldLog.d("ols", errRes.errMsg + "_getAppStatus");
				errCodeFix(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}

	/**
	 * ����Ӧ�����ش���
	 * 
	 * @param appParm
	 *            �Ѱ�װapp
	 * 
	 * @return CldSapReturn
	 */
	public CldSapReturn getUpdateAppDowntimes(MtqAppInfo appParm) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAppCenter.getUpdateAppDowntimes(appParm);
			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
				CldLog.d("ols", errRes.errCode + "_getUpdateAppDowntimes");
				CldLog.d("ols", errRes.errMsg + "_getUpdateAppDowntimes");
				errCodeFix(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @return CldSapReturn
	 */
	public CldSapReturn getCarList() {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAppCenter.getCarList();
			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
				CldLog.d("ols", errRes.errCode + "_getCarList");
				CldLog.d("ols", errRes.errMsg + "_getCarList");
				errCodeFix(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}

	/**
	 * ��ȡ������tips���ص�ַ
	 * 
	 * @param apptype
	 * @param prover
	 *            launcher�汾��
	 * @param width
	 * @param height
	 * @return CldSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-5-25
	 */
	public CldSapReturn getSplash(int apptype, String prover, int width,
			int height, ILogoTipsListener listener) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAppCenter.getSplash(apptype, prover, width, height);
			CldLog.i("ols", "getSplash = " + errRes.url);
			if (errRes != null) {
				String strRtn = CldHttpClient.get(errRes.url);
				CldLog.i("ols", "strRtn = " + strRtn);
				ProtLogoTips protLogoTips = CldSapParser.parseJson(strRtn,
						ProtLogoTips.class, errRes);
				CldErrUtil.handleErr(errRes);
				CldLog.d("ols", errRes.errCode + "_getSplash");
				CldLog.d("ols", errRes.errMsg + "_getSplash");
				errCodeFix(errRes);
				if (errRes.errCode == 0 && protLogoTips != null) {
					MtqLogoTips mtqLogoTips = new MtqLogoTips();
					protLogoTips.protParse(mtqLogoTips);
					if (listener != null) {
						listener.onResult(errRes.errCode, mtqLogoTips);
					}
				} else {
					if (listener != null) {
						listener.onResult(errRes.errCode, null);
					}
				}
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
			if (listener != null) {
				listener.onResult(errRes.errCode, null);
			}
		}
		return errRes;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param longitude
	 * @param latitude
	 * @param listener
	 */
	public void getRegionDistsName(double longitude, double latitude,
			IGetRigonListener listener) {
		long newLong = (int) (longitude * 3600000);
		long newLat = (int) (latitude * 3600000);
		String params = "&p=" + newLong + "+" + newLat;
		String url = "http://navitest1.careland.com.cn/cgi/pub_getpositioninfo_j.ums?d=1&ct=1"
				+ params;
		CldLog.d("ols", "url location:" + url);
		String json = CldSapNetUtil.sapGetMethod(url);
		CldLog.d("ols", "json location:" + json);
		int regionCityId = CldSapNetUtil.getCityIdJson(json);
		String provinceName = CldSapNetUtil.getNameByJson(json, 1);
		String cityName = CldSapNetUtil.getNameByJson(json, 2);
		String distsName = CldSapNetUtil.getNameByJson(json, 3);

		if (listener != null)
			listener.onResult(regionCityId, provinceName, cityName, distsName);
	}

	/**
	 * ��ȡ����id
	 * 
	 * @param longitude
	 * @param latitude
	 * @param listener
	 */
	public void getRegionId(double longitude, double latitude,
			final IGetRigonListener listener) {
		long newLong = (int) (longitude * 3600000);
		long newLat = (int) (latitude * 3600000);
		String params = "&p=" + newLong + "+" + newLat;
		String url = "http://navitest1.careland.com.cn/cgi/pub_getpositioninfo_j.ums?d=1&ct=1"
				+ params;
		CldLog.d("ols", "url location:" + url);
		String json = CldSapNetUtil.sapGetMethod(url);
		CldLog.d("ols", "json location:" + json);
		int regionCityId = CldSapNetUtil.getCityIdJson(json);

		if (listener != null)
			listener.onResult(regionCityId, "", "", "");
	}

	/**
	 * �����봦��
	 * 
	 * @param res
	 * @return void
	 * @author zhaoqy
	 * @date 2017-4-24
	 */
	public void errCodeFix(CldSapReturn res) {
		switch (res.errCode) {
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
}
