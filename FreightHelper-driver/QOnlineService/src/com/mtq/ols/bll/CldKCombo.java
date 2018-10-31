package com.mtq.ols.bll;

import com.cld.device.CldPhoneNet;
import com.mtq.ols.sap.CldSapKCombo;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;

public class CldKCombo {

	private static CldKCombo cldKCombo;

	private CldKCombo() {

	}

	public static CldKCombo getInstance() {
		if (cldKCombo == null) {
			cldKCombo = new CldKCombo();
		}
		return cldKCombo;
	}

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
	public CldSapReturn getComboList(int systemCode, int deviceCode,
			int productCode, int width, int height, String iccid, int kuid,
			String session, int appid, int bussinessid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.getComboList(systemCode, deviceCode,
					productCode, width, height, iccid, kuid, session, appid,
					bussinessid);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn getUserComboList(int systemCode, int deviceCode,
			int productCode, int width, int height, String iccid, int kuid,
			String session, int appid, int bussinessid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.getUserComboList(systemCode, deviceCode,
					productCode, width, height, iccid, kuid, session, appid,
					bussinessid);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn getUserComboCount(String iccid, int kuid,
			String session, int appid, int bussinessid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.getUserComboCount(iccid, kuid, session,
					appid, bussinessid);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn getServiceApp(String iccid, int kuid,
			String session, int appid, int bussinessid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.getServiceApp(iccid, kuid, session, appid,
					bussinessid);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}

	/**
	 * �����ײ͹������
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @return CldSapReturn
	 */
	public CldSapReturn getUpdateComboPayTimes(int combo_code) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.getUpdateComboPayTimes(combo_code);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}

	/**
	 * ��ȡ�ײ͵���������
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @return CldSapReturn
	 */
	public CldSapReturn getComboAlarmSetting(int combo_code) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.getComboAlarmSetting(combo_code);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn orderCombo(int deviceCode, int productCode,
			int cust_id, int duid, String sn, int combo_code, int month,
			int charge, String iccid, int kuid, String orderno, int flowrate) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.orderCombo(deviceCode, productCode, cust_id,
					duid, sn, combo_code, month, charge, iccid, kuid, orderno,
					flowrate);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}

	/**
	 * ��ȡ�ɹ�����ײ��б�
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 * @return CldSapReturn
	 */
	public CldSapReturn getAllComboList(int combo_code) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.getAllComboList(combo_code);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn activateIccidCombo(int combo_code, int month,
			int charge, String iccid, String orderno, int flowrate) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.activateIccidCombo(combo_code, month, charge,
					iccid, orderno, flowrate);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn updateIccidDevice(String iccid, int deviceCode,
			int productCode, int cust_id, int duid, String sn, int combo_code) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.updateIccidDevice(iccid, deviceCode,
					productCode, cust_id, duid, sn, combo_code);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn initIccidCombo(int combo_code, int month,
			int charge, String iccid, int flowrate) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.initIccidCombo(combo_code, month, charge,
					iccid, flowrate);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn setIccidComboEnabled(String iccid,
			int combo_code, int orderdate, int starttime, int endtime) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.setIccidComboEnabled(iccid, combo_code,
					orderdate, starttime, endtime);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
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
	public CldSapReturn setIccidComboExpired(String iccid,
			int combo_code, int orderdate) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKCombo.setIccidComboExpired(iccid, combo_code,
					orderdate);

			if (errRes != null) {
				String strRtn = CldSapNetUtil.sapPostMethod(errRes.url,
						errRes.jsonPost);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldErrUtil.handleErr(errRes);
			}
		} else {
			errRes.errCode = -2;
			errRes.errMsg = "�����쳣";
		}
		return errRes;
	}
}
