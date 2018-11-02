package com.mtq.ols.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cld.log.CldLog;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.module.delivery.tool.CldSapParser;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfo;
import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapReturn;

public class CldSapKAppCenter {

	/** �����������. */
	private final static int APIVER = 1;
	/** The RSCHARSET. */
	private final static int RSCHARSET = 1;
	/** The RSFORMAT. */
	private final static int RSFORMAT = 1;
	/** The UMSAVER. */
	private final static int UMSAVER = 2;
	/** The ENCRYPT. */
	private final static int ENCRYPT = 0;
	/** ������ */
	public static int CID;
	/** ����汾�� */
	public static String PROVER;

	/** ����ϵͳ���� ��Ӫƽ̨���� */
	public static final int system_code = 1;
	/** �豸�ͺű��� ��Ӫƽ̨���� */
	public static final int device_code = 1;
	/** ��Ʒ�ͺű��� ��Ӫƽ̨���� */
	public static final int product_code = 2;

	public static String kgo_key = "";

	public static void setKgoKey(String key) {
		kgo_key = key;
		CldLog.i("ols", "kgo_key= " + kgo_key);
	}

	public static String getKgoKey() {
		return kgo_key;
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @return CldSapReturn
	 */
	public static CldSapReturn initKeyCode() {
		String key = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		//map.put("cid", CID);
		//map.put("prover", PROVER);
		// map.put("encrypt", ENCRYPT);

		if (CldBllUtil.getInstance().isTestVersion()) {
			//key = "373275EB226022907CCA40BD2AE481D8";
			key = "BBA9B3328AD5D4DBEF5C756D3DBF335D";
			// key = "07CCA260229D340BD28AE48175EB2732";
		} else {
			//key = "373275EB226022907CCA40BD2AE481D8";
			key = "BBA9B3328AD5D4DBEF5C756D3DBF335D";
		}

		/*CldSapReturn errRes = CldKBaseParse
				.getGetParms(map, getOperationPlatformHeadUrl()
						+ "kgo/api/kgo_get_code.php", key);*/
		CldSapReturn errRes = CldKBaseParse
				.getGetParms(map, getOperationPlatformHeadUrlForNew()
						+ "cm_pub/php/pub_get_code.php", key); 
		return errRes;
	}

	/**
	 * Ӧ��������
	 * 
	 * @param width
	 *            �ֱ��ʿ�
	 * @param height
	 *            �ֱ��ʸ�
	 * @param dpi
	 *            dpi
	 * @param systemVer
	 *            android ϵͳ�汾
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
	public static CldSapReturn getUpgrade(int width, int height, int dpi,
			String systemVer, String launcherVer, long duid, long kuid,
			int regionId, int customCode, int planCode, String packname,
			int vercode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("system_code", system_code);
		map.put("device_code", device_code);
		map.put("product_code", product_code);
		map.put("width", width);
		map.put("height", height);
		map.put("dpi", dpi);
		map.put("system_ver", systemVer);
		map.put("page", 0);
		map.put("size", 0);
		map.put("launcher_ver", launcherVer);
		map.put("duid", duid);
		map.put("kuid", kuid);
		map.put("area_code", regionId);
		map.put("custom_code", customCode);
		map.put("plan_code", planCode);

		// appInfoList���������sign
		String mymd5 = CldSapParser.formatSource(map);
		List<Map<String, Object>> applist = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapApp = null;
		mapApp = new HashMap<String, Object>();
		mapApp.put("packname", packname);
		mapApp.put("vercode", vercode);
		applist.add(mapApp);
		map.put("install_app", applist);

		// CldLog.i("ols", "key= " + kgo_key);
		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				getOperationPlatformHeadUrl()
						+ "kgo/api/kgo_get_app_upgrade.php", kgo_key, mymd5);

		return errRes;
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
	public static CldSapReturn getAppsUpgrade(int width, int height, int dpi,
			String systemVer, int page, int size, String launcherVer,
			long duid, long kuid, int regionId, int customCode, int planCode,
			List<MtqAppInfo> appinfos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("system_code", system_code);
		map.put("device_code", device_code);
		map.put("product_code", product_code);
		map.put("width", width);
		map.put("height", height);
		map.put("dpi", dpi);
		map.put("system_ver", systemVer);
		map.put("page", page);
		map.put("size", size);
		map.put("launcher_ver", launcherVer);
		map.put("duid", duid);
		map.put("kuid", kuid);
		map.put("area_code", regionId);
		map.put("custom_code", customCode);
		map.put("plan_code", planCode);

		// appInfoList���������sign
		String mymd5 = CldSapParser.formatSource(map);
		List<Map<String, Object>> applist = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapApp = null;
		for (MtqAppInfo item : appinfos) {
			mapApp = new HashMap<String, Object>();
			mapApp.put("packname", item.pack_name);
			mapApp.put("vercode", "" + item.ver_code);
			applist.add(mapApp);
		}
		map.put("install_app", applist);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				getOperationPlatformHeadUrl()
						+ "kgo/api/kgo_get_app_upgrade.php", kgo_key, mymd5);
		return errRes;
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
	public static CldSapReturn getUpgradeForNew(int width, int height, int vercode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		
		map.put("apptype", getApptype());
		map.put("vercode", vercode);
		map.put("wrate", width);
		map.put("hrate", height);

		String mymd5 = CldSapParser.formatSource(map);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				getOperationPlatformHeadUrlForNew()
						+ "cm_pub/php/upgrade_check_version.php", mymd5,
				kgo_key);

		return errRes;
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
	public static CldSapReturn getRecdApp(int width, int height, int page,
			int size, String launcherVer, List<MtqAppInfo> appinfos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("system_code", system_code);
		map.put("device_code", device_code);
		map.put("product_code", product_code);
		map.put("width", width);
		map.put("height", height);
		map.put("page", page);
		map.put("size", size);
		map.put("launcher_ver", launcherVer);

		// appInfoList���������sign
		String mymd5 = CldSapParser.formatSource(map);
		List<Map<String, Object>> applist = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapApp = null;
		for (MtqAppInfo item : appinfos) {
			mapApp = new HashMap<String, Object>();
			mapApp.put("packname", item.pack_name);
			applist.add(mapApp);
		}
		map.put("install_app", applist);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				getOperationPlatformHeadUrl()
						+ "kgo/api/kgo_get_recommend_app.php", kgo_key, mymd5);
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
	public static CldSapReturn getAppStatus(List<MtqAppInfo> appinfos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);

		// appInfoList���������sign
		String mymd5 = CldSapParser.formatSource(map);
		List<Map<String, Object>> applist = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapApp = null;
		for (MtqAppInfo item : appinfos) {
			mapApp = new HashMap<String, Object>();
			mapApp.put("packname", item.pack_name);
			applist.add(mapApp);
		}
		map.put("install_app", applist);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map,
				getOperationPlatformHeadUrl()
						+ "kgo/api/kgo_get_app_status.php", kgo_key, mymd5);
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
	public static CldSapReturn getUpdateAppDowntimes(MtqAppInfo appinfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("packname", appinfo.pack_name);
		map.put("vercode", appinfo.ver_code);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				getOperationPlatformHeadUrl()
						+ "kgo/api/kgo_get_update_app_down_times.php", kgo_key);
		return errRes;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @return CldSapReturn
	 * @author zhaoqy
	 * @date 2017-2-10
	 */
	public static CldSapReturn getCarList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("useid", 2);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				getOperationPlatformHeadUrl() + "kgo/api/kgo_get_car_list.php",
				kgo_key);
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
	public static CldSapReturn getSplash(int apptype, String prover, int width,
			int height) {
		String key = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apptype", apptype);
		map.put("prover", prover);
		map.put("length", width);
		map.put("width", height);

		if (CldBllUtil.getInstance().isTestVersion()) {
			key = "F4A41A19D58AE8F7B7306FD30CB3F3FA";
		} else {
			key = "BB2CC2F71F01DF39A156E4F4FE56FEE5";
		}

		CldSapReturn errRes = CldKBaseParse
				.getGetParms(map, getOperationPlatformHeadUrl()
						+ "kgo/api/get_logo_tips_url.php", key);
		return errRes;
	}

	private static String getOperationPlatformHeadUrl() {
		if (CldBllUtil.getInstance().isTestVersion()) {
			return "http://tmctest.careland.com.cn/";
		} else {
			return "http://stat.careland.com.cn/";
		}
	}

	private static String getOperationPlatformHeadUrlForNew() {
		if (CldBllUtil.getInstance().isTestVersion()) {
			return "http://test.careland.com.cn/kz/";
		} else {
			return "http://pj.careland.com.cn/";
		}
	}

	public static int getApptype() {
		return CldBllUtil.getInstance().getApptype();
	}
}
