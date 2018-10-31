package com.mtq.ols.api;

import com.mtq.ols.bll.CldKCombo;
import com.mtq.ols.tools.CldSapReturn;

/**
 * �ײ����ģ�飬�ṩ�ײ���ؽӿ�
 * 
 * @author zhaoqy
 */
public class CldKComboAPI {
	private static CldKComboAPI cldKSimCardAPI;
	private ICldKComboAPIListener listener;

	private CldKComboAPI() {

	}

	public static CldKComboAPI getInstance() {
		if (cldKSimCardAPI == null) {
			cldKSimCardAPI = new CldKComboAPI();
		}
		return cldKSimCardAPI;
	}

	/**
	 * ���ûص��������״�������Ч��
	 * 
	 * @param listener
	 *            �ص�����
	 * @return int (0 ���óɹ���-1 ��������ʧ��)
	 */
	public int setCldKComboListener(ICldKComboAPIListener listener) {
		if (null == this.listener) {
			this.listener = listener;
			return 0;
		} else {
			return -1;
		}
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
	 */
	public void getComboList(final int systemCode, final int deviceCode,
			final int productCode, final int width, final int height,
			final String iccid, final int kuid, final String session,
			final int appid, final int bussinessid) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance().getComboList(
						systemCode, deviceCode, productCode, width, height,
						iccid, kuid, session, appid, bussinessid);

				if (listener != null && errRes != null) {
					listener.onGetComboListResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void getUserComboList(final int systemCode, final int deviceCode,
			final int productCode, final int width, final int height,
			final String iccid, final int kuid, final String session,
			final int appid, final int bussinessid) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance().getUserComboList(
						systemCode, deviceCode, productCode, width, height,
						iccid, kuid, session, appid, bussinessid);
				if (listener != null && errRes != null) {
					listener.onGetUserComboListResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void getUserComboCount(final String iccid, final int kuid,
			final String session, final int appid, final int bussinessid) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance()
						.getUserComboCount(iccid, kuid, session, appid,
								bussinessid);

				if (listener != null && errRes != null) {
					listener.onGetUserComboCountResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void getServiceApp(final String iccid, final int kuid,
			final String session, final int appid, final int bussinessid) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance().getServiceApp(
						iccid, kuid, session, appid, bussinessid);

				if (listener != null && errRes != null) {
					listener.onGetServiceAppResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * �����ײ͹������
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 */
	public void getUpdateComboPayTimes(final int combo_code) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance()
						.getUpdateComboPayTimes(combo_code);

				if (listener != null && errRes != null) {
					listener.onGetUpdateComboPayTimesResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ�ײ͵���������
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 */
	public void getComboAlarmSetting(final int combo_code) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance()
						.getComboAlarmSetting(combo_code);

				if (listener != null && errRes != null) {
					listener.onGetComboAlarmSettingResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void orderCombo(final int deviceCode, final int productCode,
			final int cust_id, final int duid, final String sn,
			final int combo_code, final int month, final int charge,
			final String iccid, final int kuid, final String orderno,
			final int flowrate) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance().orderCombo(
						deviceCode, productCode, cust_id, duid, sn, combo_code,
						month, charge, iccid, kuid, orderno, flowrate);

				if (listener != null && errRes != null) {
					listener.onOrderComboResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ�ɹ�����ײ��б�
	 * 
	 * @param combo_code
	 *            �ײͱ���
	 */
	public void getAllComboList(final int combo_code) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance().getAllComboList(
						combo_code);

				if (listener != null && errRes != null) {
					listener.onGetAllComboListResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void activateIccidCombo(final int combo_code, final int month,
			final int charge, final String iccid, final String orderno,
			final int flowrate) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance()
						.activateIccidCombo(combo_code, month, charge, iccid,
								orderno, flowrate);

				if (listener != null && errRes != null) {
					listener.onActivateIccidComboResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void updateIccidDevice(final String iccid, final int deviceCode,
			final int productCode, final int cust_id, final int duid,
			final String sn, final int combo_code) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance()
						.updateIccidDevice(iccid, deviceCode, productCode,
								cust_id, duid, sn, combo_code);

				if (listener != null && errRes != null) {
					listener.onUpdateIccidDeviceResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void initIccidCombo(final int combo_code, final int month,
			final int charge, final String iccid, final int flowrate) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance().initIccidCombo(
						combo_code, month, charge, iccid, flowrate);

				if (listener != null && errRes != null) {
					listener.onInitIccidComboResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void setIccidComboEnabled(final String iccid, final int combo_code,
			final int orderdate, final int starttime, final int endtime) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance()
						.setIccidComboEnabled(iccid, combo_code, orderdate,
								starttime, endtime);

				if (listener != null && errRes != null) {
					listener.onSetIccidComboEnabledResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
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
	 */
	public void setIccidComboExpired(final String iccid,
			final int combo_code, final int orderdate) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CldSapReturn errRes = CldKCombo.getInstance()
						.setIccidComboExpired(iccid, combo_code, orderdate);

				if (listener != null && errRes != null) {
					listener.onSetIccidComboExpiredResult(errRes.errCode,
							errRes.jsonReturn);
				}
			}
		}).start();
	}

	/**
	 * �ײͻص�����
	 * 
	 * @author zhaoqy
	 * @date 2017-2-10
	 */
	public static interface ICldKComboAPIListener {
		/**
		 * ��ȡ�ɹ�����ײ��б�ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onGetComboListResult(int errCode, String jsonString);

		/**
		 * ��ȡ�û��Ѿ��ɹ�������ײ��б�ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onGetUserComboListResult(int errCode, String jsonString);

		/**
		 * ��ȡ�û��Ѿ��ɹ�������ײ������ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onGetUserComboCountResult(int errCode, String jsonString);

		/**
		 * ��ȡ�û��Ѿ��ɹ�������ײͷ���ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onGetServiceAppResult(int errCode, String jsonString);

		/**
		 * �����ײ͹�������ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onGetUpdateComboPayTimesResult(int errCode,
				String jsonString);

		/**
		 * ��ȡ�ײ͵��������ûص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onGetComboAlarmSettingResult(int errCode, String jsonString);

		/**
		 * �����ײͻص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onOrderComboResult(int errCode, String jsonString);

		/**
		 * ��ȡ�ɹ�����ײ��б�ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onGetAllComboListResult(int errCode, String jsonString);

		/**
		 * �ն��ֶ������ײͻص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onActivateIccidComboResult(int errCode, String jsonString);

		/**
		 * ����ICCID����Ӧ���豸��Ϣ�ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onUpdateIccidDeviceResult(int errCode, String jsonString);

		/**
		 * ICCID���ײ���Ϣʼ���ص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onInitIccidComboResult(int errCode, String jsonString);

		/**
		 * ICCID�ײ����ûص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onSetIccidComboEnabledResult(int errCode, String jsonString);
		
		/**
		 * ICCID�ײ͹��ڻص�
		 * 
		 * @param errCode
		 * @param jsonString
		 */
		public void onSetIccidComboExpiredResult(int errCode, String jsonString);
	}
}
