package com.mtq.ols.sap;

import java.util.HashMap;
import java.util.Map;

import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;

public class CldSapKCombo {

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

	/**
	 * ��ȡ�ɹ�����ײ��б�
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
	 * @param iccid
	 *            Iccid����
	 * @param kuid
	 *            �û�kuid
	 * @param session
	 *            ��¼Session
	 * @param appid
	 *            �˺�ϵͳ����
	 * @param bussinessid
	 *            ҵ�����
	 * @return CldSapReturn
	 */
	public static CldSapReturn getComboList(int systemCode, int deviceCode,
			int productCode, int width, int height, String iccid, int kuid,
			String session, int appid, int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("system_code", systemCode);
		map.put("device_code", deviceCode);
		map.put("product_code", productCode);
		map.put("width", width);
		map.put("height", height);
		map.put("launcher_ver", "");
		map.put("iccid", iccid);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_get_combo_list.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ��ȡ�û��Ѿ��ɹ�������ײ��б�
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
	 * @param iccid
	 *            Iccid����
	 * @param kuid
	 *            �û�kuid
	 * @param session
	 *            ��¼Session
	 * @param appid
	 *            �˺�ϵͳ����
	 * @param bussinessid
	 *            ҵ�����
	 * @return CldSapReturn
	 */
	public static CldSapReturn getUserComboList(int systemCode, int deviceCode,
			int productCode, int width, int height, String iccid, int kuid,
			String session, int appid, int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("system_code", systemCode);
		map.put("device_code", deviceCode);
		map.put("product_code", productCode);
		map.put("width", width);
		map.put("height", height);
		map.put("launcher_ver", "");
		map.put("iccid", iccid);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_get_user_combo_list.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ��ȡ�û��Ѿ��ɹ�������ײ�����
	 * 
	 * @param iccid
	 *            Iccid����
	 * @param kuid
	 *            �û�kuid
	 * @param session
	 *            ��¼Session
	 * @param appid
	 *            �˺�ϵͳ����
	 * @param bussinessid
	 *            ҵ�����
	 * @return CldSapReturn
	 */
	public static CldSapReturn getUserComboCount(String iccid, int kuid,
			String session, int appid, int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("iccid", iccid);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_get_user_combo_count.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ��ȡ�û��Ѿ��ɹ�������ײͷ��񣬹�����appӦ���б�
	 * 
	 * @param iccid
	 *            Iccid����
	 * @param kuid
	 *            �û�kuid
	 * @param session
	 *            ��¼Session
	 * @param appid
	 *            �˺�ϵͳ����
	 * @param bussinessid
	 *            ҵ�����
	 * @return CldSapReturn
	 */
	public static CldSapReturn getServiceApp(String iccid, int kuid,
			String session, int appid, int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("iccid", iccid);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_get_service_app.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * �����ײ͹������
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @return CldSapReturn
	 */
	public static CldSapReturn getUpdateComboPayTimes(int combo_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("combo_code", combo_code);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_get_update_combo_pay_times.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ��ȡ�ײ͵���������
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @return CldSapReturn
	 */
	public static CldSapReturn getComboAlarmSetting(int combo_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("combo_code", combo_code);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_get_combo_alarm_setting.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * �����ײ�
	 * 
	 * @param deviceCode
	 *            �豸�ͺű���(��Ӫƽ̨����)
	 * @param productCode
	 *            ��Ʒ�ͺű���(��Ӫƽ̨����)
	 * @param cust_id
	 *            �豸�ͻ�ID
	 * @param duid
	 *            �豸���
	 * @param sn
	 *            �豸Ψһ��
	 * @param combo_code
	 *            �ײͱ���
	 * @param month
	 *            �·���
	 * @param charge
	 *            ���ã���λ��Ԫ
	 * @param iccid
	 *            ICCID����
	 * @param kuid
	 *            �û�kuid
	 * @param orderno
	 *            ������
	 * @param flowrate
	 *            ������������
	 * @return CldSapReturn
	 */
	public static CldSapReturn orderCombo(int deviceCode, int productCode,
			int cust_id, int duid, String sn, int combo_code, int month,
			int charge, String iccid, int kuid, String orderno, int flowrate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("deviceCode", deviceCode);
		map.put("productCode", productCode);
		map.put("cust_id", cust_id);
		map.put("duid", duid);
		map.put("sn", sn);
		map.put("combo_code", combo_code);
		map.put("month", month);
		map.put("charge", charge);
		map.put("iccid", iccid);
		map.put("kuid", kuid);
		map.put("orderno", orderno);
		map.put("flowrate", flowrate);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_order_combo.php", CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ��ȡ�ɹ�����ײ��б�
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @return CldSapReturn
	 */
	public static CldSapReturn getAllComboList(int combo_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("combo_code", combo_code);

		CldSapReturn errRes = CldKBaseParse.getGetParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_get_all_combo_list.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * �ն��ֶ������ײ�
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @param month
	 *            �·���
	 * @param charge
	 *            ���ã���λ��Ԫ
	 * @param iccid
	 *            ICCID����
	 * @param orderno
	 *            ������
	 * @param flowrate
	 *            ������������
	 * @return CldSapReturn
	 */
	public static CldSapReturn activateIccidCombo(int combo_code, int month,
			int charge, String iccid, String orderno, int flowrate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("combo_code", combo_code);
		map.put("month", month);
		map.put("charge", charge);
		map.put("iccid", iccid);
		map.put("orderno", orderno);
		map.put("flowrate", flowrate);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_activate_iccid_combo.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ����ICCID����Ӧ���豸��Ϣ
	 * 
	 * @param iccid
	 *            ICCID����
	 * @param deviceCode
	 *            �豸�ͺű���(��Ӫƽ̨����)
	 * @param productCode
	 *            ��Ʒ�ͺű���(��Ӫƽ̨����)
	 * @param cust_id
	 *            �豸�ͻ�ID
	 * @param duid
	 *            �豸���
	 * @param sn
	 *            �豸Ψһ��
	 * @param combo_code
	 *            �ײͱ���
	 * @return CldSapReturn
	 */
	public static CldSapReturn updateIccidDevice(String iccid, int deviceCode,
			int productCode, int cust_id, int duid, String sn, int combo_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("iccid", iccid);
		map.put("deviceCode", deviceCode);
		map.put("productCode", productCode);
		map.put("cust_id", cust_id);
		map.put("duid", duid);
		map.put("sn", sn);
		map.put("combo_code", combo_code);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_update_iccid_device.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ICCID���ײ���Ϣʼ��
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @param month
	 *            �·���
	 * @param charge
	 *            ���ã���λ��Ԫ
	 * @param iccid
	 *            ICCID����
	 * @param flowrate
	 *            ������������
	 * @return CldSapReturn
	 */
	public static CldSapReturn initIccidCombo(int combo_code, int month,
			int charge, String iccid, int flowrate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("combo_code", combo_code);
		map.put("month", month);
		map.put("charge", charge);
		map.put("iccid", iccid);
		map.put("flowrate", flowrate);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_init_iccid_combo.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	/**
	 * ICCID�ײ�����
	 * 
	 * @param iccid
	 *            ICCID����
	 * @param combo_code
	 *            �ײͱ���
	 * @param orderdate
	 *            �������� ��YYYYMMdd��20160501
	 * @param starttime
	 *            ��ʼʱ��UTC
	 * @param endtime
	 *            ����ʱ��UTC
	 * @return CldSapReturn
	 */
	public static CldSapReturn setIccidComboEnabled(String iccid,
			int combo_code, int orderdate, int starttime, int endtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("iccid", iccid);
		map.put("combo_code", combo_code);
		map.put("orderdate", orderdate);
		map.put("starttime", starttime);
		map.put("endtime", endtime);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_set_iccid_combo_enabled.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}
	
	/**
	 * ICCID�ײ͹���
	 * 
	 * @param iccid
	 *            ICCID����
	 * @param combo_code
	 *            �ײͱ���
	 * @param orderdate
	 *            �������� ��YYYYMMdd��20160501
	 * @return CldSapReturn
	 */
	public static CldSapReturn setIccidComboExpired(String iccid,
			int combo_code, int orderdate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("umsaver", UMSAVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("apiver", APIVER);
		map.put("cid", CID);
		map.put("prover", PROVER);
		map.put("encrypt", ENCRYPT);
		map.put("iccid", iccid);
		map.put("combo_code", combo_code);
		map.put("orderdate", orderdate);

		CldSapReturn errRes = CldKBaseParse.getPostParms(map, getComboHeadUrl()
				+ "kgo/api/kgo_set_iccid_combo_expired.php",
				CldSapKAppCenter.getKgoKey());
		return errRes;
	}

	private static String getComboHeadUrl() {
		return "";
	}
}
