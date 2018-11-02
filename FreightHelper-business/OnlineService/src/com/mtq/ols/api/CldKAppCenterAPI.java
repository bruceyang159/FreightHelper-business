package com.mtq.ols.api;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.cld.base.CldBase;
import com.cld.setting.CldSetting;
import com.mtq.ols.api.CldOlsInit.ICldOlsInitListener;
import com.mtq.ols.bll.CldKAppCenter;
import com.mtq.ols.dal.CldDalKAppCenter;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfo;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfoNew;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqLogoTips;
import com.mtq.ols.tools.AppInfoUtils;
import com.mtq.ols.tools.CldSapReturn;

/**
 * Ӧ���������ģ�飬�ṩӦ��������ؽӿ�
 * 
 * @author zhaoqy
 */
public class CldKAppCenterAPI {

	private static CldKAppCenterAPI cldKAppCenterAPI;

	private CldKAppCenterAPI() {
	}

	public static CldKAppCenterAPI getInstance() {
		if (cldKAppCenterAPI == null) {
			cldKAppCenterAPI = new CldKAppCenterAPI();
		}
		return cldKAppCenterAPI;
	}

	public void init() {

	}

	public void uninit() {

	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @param listener
	 * @return void
	 */
	public void initKey(final ICldOlsInitListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldKAppCenter.getInstance().initKey();
				if (null != listener) {
					listener.onInitReslut();
				}
			}
		}).start();
	}

	/**
	 * ��ȡӦ�ð汾��Ϣ
	 */
	public MtqAppInfoNew getMtqAppInfo() {
		return CldDalKAppCenter.getInstance().getMtqAppInfo();
	}

	/**
	 * �Ƿ�����°汾
	 */
	public boolean hasNewVersion() {
		return CldDalKAppCenter.getInstance().getNewVersion();
	}

	/**
	 * ����汾��Ϣ
	 */
	public void clearAppVersion() {
		CldDalKAppCenter.getInstance().uninit();
	}

	/**
	 * �����ȡduid�ӿ�
	 */
	public long getDuid() {
		String strDuid = CldSetting.getString("duid");
		if (TextUtils.isEmpty(strDuid)) {
			return 0;
		} else {
			return Long.parseLong(strDuid);
		}
	}

	/**
	 * �����ȡkuid�ӿ�
	 */
	public long getKuid() {
		String strKuid = CldSetting.getString("kuid");
		if (TextUtils.isEmpty(strKuid)) {
			return 0;
		} else {
			return Long.parseLong(strKuid);
		}
	}

	/**
	 * ��ȡ���
	 */
	public int getWidth() {
		String strWidth = CldSetting.getString("width");
		if (TextUtils.isEmpty(strWidth)) {
			int width = AppInfoUtils.getDensityWidth(CldBase.getAppContext());
			CldSetting.put("width", width + "");
			return width;
		} else {
			return Integer.parseInt(strWidth);
		}
	}

	/**
	 * ��ȡ�߶�
	 */
	public int getHeight() {
		String strHeight = CldSetting.getString("height");
		if (TextUtils.isEmpty(strHeight)) {
			int height = AppInfoUtils.getDensityHeight(CldBase.getAppContext());
			CldSetting.put("height", height + "");
			return height;
		} else {
			return Integer.parseInt(strHeight);
		}
	}

	/**
	 * ��ȡdpi
	 */
	public int getDpi() {
		String strDpi = CldSetting.getString("dpi");
		if (TextUtils.isEmpty(strDpi)) {
			int dpi = AppInfoUtils.getDensityDpi(CldBase.getAppContext());
			CldSetting.put("dpi", dpi + "");
			return dpi;
		} else {
			return Integer.parseInt(strDpi);
		}
	}

	/**
	 * ��ȡϵͳ�汾
	 */
	public String getSystemVer() {
		String strSystemVer = CldSetting.getString("systemVer");
		if (TextUtils.isEmpty(strSystemVer)) {
			String systemVer = AppInfoUtils.getSystemVer();
			CldSetting.put("systemVer", systemVer);
			return systemVer;
		} else {
			return strSystemVer;
		}
	}

	/**
	 * ��ȡ����
	 */
	public String getPackName() {
		return AppInfoUtils.getPackName(CldBase.getAppContext());
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @return
	 */
	public int getVerCode() {
		return AppInfoUtils.getVerCode(CldBase.getAppContext());
	}

	/**
	 * ����Ѱ�װ��app�Ƿ�������
	 * 
	 */
	public void getUpgrade(final IUpgradeListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				int width = getWidth();
				int height = getHeight();
				int vercode = getVerCode();
				CldKAppCenter.getInstance().getUpgradeForNew(width, height,
						vercode, listener);
			}
		}).start();
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
	 */
	public void getAppsUpgrade(final int width, final int height,
			final int dpi, final String systemVer, final int page,
			final int size, final String launcherVer, final long duid,
			final long kuid, final int regionId, final int customCode,
			final int planCode, final List<MtqAppInfo> appParms,
			final IAppsUpgradeListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				CldKAppCenter.getInstance().getAppsUpgrade(width, height, dpi,
						systemVer, page, size, launcherVer, duid, kuid,
						regionId, customCode, planCode, appParms, listener);
			}
		}).start();
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
	 */
	public void getRecdApp(final int width, final int height, final int page,
			final int size, final String launcherVer,
			final List<MtqAppInfo> appParms, final IAppCenterListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKAppCenter.getInstance().getRecdApp(
						width, height, page, size, launcherVer, appParms);
				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * ��ȡӦ��״̬��Ϣ(�������¼�app�İ���)
	 * 
	 * @param appParms
	 *            �Ѱ�װapp
	 */
	public void getAppStatus(final List<MtqAppInfo> appParms,
			final IAppCenterListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKAppCenter.getInstance().getAppStatus(
						appParms);
				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * ����Ӧ�����ش���
	 * 
	 * @param appParm
	 *            �Ѱ�װapp
	 */
	public void getUpdateAppDowntimes(final MtqAppInfo appParm,
			final IAppCenterListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKAppCenter.getInstance()
						.getUpdateAppDowntimes(appParm);
				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param listener
	 */
	public void getCarList(final IAppCenterListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKAppCenter.getInstance().getCarList();
				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param longitude
	 * @param latitude
	 * @param listener
	 */
	public void getRegionDistsName(final double longitude,
			final double latitude, final IGetRigonListener listener) {
		if (listener == null)
			return;

		new Thread(new Runnable() {

			@Override
			public void run() {
				CldKAppCenter.getInstance().getRegionDistsName(longitude,
						latitude, listener);
			}
		}).start();
	}

	/**
	 * ��ȡ����id
	 * 
	 * @param longitude
	 * @param latitude
	 * @param listener
	 */
	public void getRegionId(final double longitude, final double latitude,
			final IGetRigonListener listener) {
		if (listener == null)
			return;

		new Thread(new Runnable() {

			@Override
			public void run() {
				CldKAppCenter.getInstance().getRegionDistsName(longitude,
						latitude, listener);
			}
		}).start();
	}

	/**
	 * ��ȡ������tips���ص�ַ
	 * 
	 * @param apptype
	 * @param prover
	 *            launcher�汾��
	 * @param width
	 * @param height
	 * 
	 * @author zhaoqy
	 * @date 2017-5-25
	 */
	public void getSplash(final int apptype, final String prover,
			final int width, final int height, final ILogoTipsListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				CldKAppCenter.getInstance().getSplash(apptype, prover, width,
						height, listener);
			}
		}).start();
	}

	/**
	 * ��ȡ����id�ص�����
	 * 
	 * @author zhaoqy
	 * @date 2017-2-16
	 */
	public static interface IGetRigonListener {
		/**
		 * ����ص�
		 * 
		 * @param regionId
		 * @param provinceName
		 * @param cityName
		 * @param distsName
		 */
		public void onResult(int regionId, String provinceName,
				String cityName, String distsName);
	}

	/**
	 * Ӧ�����Ļص�����
	 * 
	 * @author zhaoqy
	 * @date 2017-2-9
	 */
	public static interface IAppCenterListener {
		/**
		 * ����ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onResult(int errCode, String jsonString);
	}

	/**
	 * Ӧ�����Ļص�����
	 * 
	 * @author zhaoqy
	 * @date 2017-4-19
	 */
	public static interface IUpgradeListener {

		/**
		 * ����ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onResult(int errCode, MtqAppInfoNew result);
	}

	/**
	 * Ӧ�����Ļص�����
	 * 
	 * @author zhaoqy
	 * @date 2017-2-9
	 */
	public static interface IAppsUpgradeListener {

		/**
		 * ����ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onListResult(int errCode, List<MtqAppInfo> lstOfResult);
	}

	/**
	 * ��ȡ������tips���ص�ַ
	 * 
	 * @author zhaoqy
	 * @date 2017-5-25
	 */
	public static interface ILogoTipsListener {

		/**
		 * ����ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onResult(int errCode, MtqLogoTips result);
	}

}
