package com.mtq.ols.api;

import com.mtq.ols.bll.CldKSimCard;
import com.mtq.ols.tools.CldSapReturn;

/**
 * sim�����ģ�飬�ṩsim����ؽӿ�
 * 
 * @author zhaoqy
 */
public class CldKSimCardAPI {

	private static CldKSimCardAPI cldKSimCardAPI;

	public static CldKSimCardAPI getInstance() {
		if (cldKSimCardAPI == null) {
			cldKSimCardAPI = new CldKSimCardAPI();
		}
		return cldKSimCardAPI;
	}

	private CldKSimCardAPI() {
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
	 */
	public void getSimCardStatus(final String iccid, final String imsi,
			final String sim, final String sn, final String ver,
			final ISimCardAPIListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKSimCard.getInstance()
						.getSimCardStatus(iccid, imsi, sim, sn, ver);

				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void checkSimCard(final String iccid, final String imsi,
			final String sim, final String sn, final String ver,
			final long duid, final long kuid, final ISimCardAPIListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKSimCard.getInstance().checkSimCard(
						iccid, imsi, sim, sn, ver, duid, kuid);

				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void registerSimCard(final String iccid, final String imsi,
			final String sim, final String sn, final String ver,
			final long duid, final long kuid, final long dcode,
			final long pcode, final long custid,
			final ISimCardAPIListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKSimCard.getInstance()
						.registerSimCard(iccid, imsi, sim, sn, ver, duid, kuid,
								dcode, pcode, custid);

				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void checkPayStatus(final String iccid, final String imsi,
			final String sim, final String sn, final String ver,
			final long duid, final long kuid, final long getordertime,
			final ISimCardAPIListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKSimCard.getInstance().checkPayStatus(
						iccid, imsi, sim, sn, ver, duid, kuid, getordertime);

				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void checkHeartbeat(final String iccid, final String sim,
			final String sn, final String ver, final long update,
			final ISimCardAPIListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKSimCard.getInstance().checkHeartbeat(
						iccid, sim, sn, ver, update);

				if (listener != null && errRes != null) {
					listener.onResult(errRes.errCode, errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * sim���ص�����
	 * 
	 * @author zhaoqy
	 * @date 2017-2-10
	 */
	public static interface ISimCardAPIListener {
		/**
		 * �ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onResult(int errCode, String jsonString);
	}
}
