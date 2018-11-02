package com.mtq.ols.bll;

import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;
import com.mtq.ols.sap.CldSapKSimCard;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;

public class CldKSimCard {

	private static CldKSimCard cldKSimCard;

	private CldKSimCard() {

	}

	public static CldKSimCard getInstance() {
		if (cldKSimCard == null) {
			cldKSimCard = new CldKSimCard();
		}
		return cldKSimCard;
	}

	/**
	 * ��ȡ��ֵ��״̬��Ϣ
	 * 
	 * @param iccid
	 *            ICCID��
	 * @param imsi
	 *            IMSI��
	 * @param sim
	 *            sim����
	 * @param sn
	 *            ���к�-������
	 * @param ver
	 *            �汾�ŵ�һ�Σ��豸�����K3618��
	 * @return CldSapReturn
	 */
	public CldSapReturn getSimCardStatus(String iccid, String imsi, String sim,
			String sn, String ver) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKSimCard.getSimCardStatus(iccid, imsi, sim, sn, ver);
			if (errRes != null) {
				CldLog.i("ols", "url: " + errRes.url + ", jsonPost: "
						+ errRes.jsonPost);
				String strRtn = CldHttpClient.post(errRes.url, errRes.jsonPost);
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
	 * ��ѯsim������״̬(���ڼ���ֵ���Ƿ�Ƿ�)
	 * 
	 * @param iccid
	 *            ICCID��
	 * @param imsi
	 *            IMSI��
	 * @param sim
	 *            sim����
	 * @param sn
	 *            ���к�-������
	 * @param ver
	 *            �汾�ŵ�һ�Σ��豸�����K3618��
	 * @param duid
	 *            �豸Ψһid
	 * @param kuid
	 *            �û�Ψһid
	 * @return CldSapReturn
	 */
	public CldSapReturn checkSimCard(String iccid, String imsi, String sim,
			String sn, String ver, long duid, long kuid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKSimCard.checkSimCard(iccid, imsi, sim, sn, ver,
					duid, kuid);
			if (errRes != null) {
				CldLog.e("checkSimCard", "url:" + errRes.url);
				String strRtn = CldHttpClient.post(errRes.url, errRes.jsonPost);
				CldLog.e("checkSimCard", "strRtn:" + strRtn);
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
	 * ��ֵ���״εǼ�--���񼤻�
	 * 
	 * @param iccid
	 *            ICCID��
	 * @param imsi
	 *            IMSI��
	 * @param sim
	 *            sim����
	 * @param sn
	 *            ���к�-������
	 * @param ver
	 *            �汾�ŵ�һ�Σ��豸�����K3618��
	 * @param duid
	 *            �豸Ψһid
	 * @param kuid
	 *            �û�Ψһid
	 * @param dcode
	 *            �豸�ͺű��� (��Ӫƽ̨����)
	 * @param pcode
	 *            ��Ʒ�ͺű��� (��Ӫƽ̨����)
	 * @param custid
	 *            �豸�ͻ�ID
	 * @return CldSapReturn
	 */
	public CldSapReturn registerSimCard(String iccid, String imsi, String sim,
			String sn, String ver, long duid, long kuid, long dcode,
			long pcode, long custid) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKSimCard.registerSimCard(iccid, imsi, sim, sn, ver,
					duid, kuid, dcode, pcode, custid);
			if (errRes != null) {
				String strRtn = CldHttpClient.post(errRes.url, errRes.jsonPost);
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
	 * ��ѯ����֧��״̬
	 * 
	 * @param iccid
	 *            ICCID��
	 * @param imsi
	 *            IMSI��
	 * @param sim
	 *            sim����
	 * @param sn
	 *            ���к�-������
	 * @param ver
	 *            �汾�ŵ�һ�Σ��豸�����K3618��
	 * @param duid
	 *            �豸Ψһid
	 * @param kuid
	 *            �û�Ψһid
	 * @param getordertime
	 *            ������������ʱ�����Ψһ��ʶ
	 * @return CldSapReturn
	 */
	public CldSapReturn checkPayStatus(String iccid, String imsi, String sim,
			String sn, String ver, long duid, long kuid, long getordertime) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKSimCard.checkPayStatus(iccid, imsi, sim, sn, ver,
					duid, kuid, getordertime);
			if (errRes != null) {
				String strRtn = CldHttpClient.post(errRes.url, errRes.jsonPost);
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
	 * ��������-����ն�״̬
	 * 
	 * @param iccid
	 *            ICCID��
	 * @param sim
	 *            sim����
	 * @param sn
	 *            ���к�-������
	 * @param ver
	 *            �汾�ŵ�һ�Σ��豸�����K3618��
	 * @param update
	 *            �ն��ϴ�ʱ���
	 * @return CldSapReturn
	 */
	public CldSapReturn checkHeartbeat(String iccid, String sim, String sn,
			String ver, long update) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKSimCard.checkHeartbeat(iccid, sim, sn, ver, update);
			if (errRes != null) {
				String strRtn = CldHttpClient.post(errRes.url, errRes.jsonPost);
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
