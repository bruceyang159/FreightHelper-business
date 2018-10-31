/**
 * ���˱�-��ҵ��API
 * 
 * @author zhaoqy
 * @date 2017-06-15
 */

package com.mtq.ols.module.deliverybus;

import java.util.List;

import android.text.TextUtils;

import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.api.CldOlsBase.IInitListener;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCar;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarData;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarDataDetail;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarState;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarStateCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqDeviceDType;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqDriver;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqDriverDetail;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqGroup;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqLogin;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqMsgAlarm;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqMsgSys;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqOrderCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqState;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTaskCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTaskNavi;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTaskStore;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTrackHistory;
import com.mtq.ols.module.deliverybus.parse.MtqSapReturn;
import com.mtq.ols.sap.CldSapUtil;

public class MtqDeliveryBusAPI {

	private static MtqDeliveryBusAPI instance;

	private MtqDeliveryBusAPI() {

	}

	public static MtqDeliveryBusAPI getInstance() {
		if (null == instance) {
			instance = new MtqDeliveryBusAPI();
		}
		return instance;
	}

	public static interface IMtqLoginListener {
		/**
		 * �û���¼�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, String errmsg);
	}

	/**
	 * �û���¼
	 * 
	 * @param duid
	 *            ��¼�豸ID
	 * @param apptype
	 *            APPӦ������
	 * @param version
	 *            APP�汾��
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void login(long duid, int apptype, String version, String username,
			String password, IMtqLoginListener listener) {
		String loginPwd;
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
			if (password.length() == 32) {
				loginPwd = password;
			} else {
				loginPwd = CldSapUtil.MD5(password);
			}
			
			MtqBllDeliveryBus.getInstance().login(duid, apptype, version,
					username, loginPwd, listener);
		} else {
			MtqSapReturn errRes = MtqBllDeliveryBus.errDeal();
			if (listener != null) {
				listener.onResult(errRes.errCode, null);
			}
		}
	}

	/**
	 * �Զ���¼
	 * 
	 * @param listener
	 * 
	 * @author zhaoqy
	 * @date 2017-06-16
	 */
	public void autoLogin(IMtqLoginListener listener) {
		long duid = MtqDalDeliveryBus.getInstance().getDuid();
		int apptype = MtqDalDeliveryBus.getInstance().getAppType();
		String version = MtqDalDeliveryBus.getInstance().getVersion();
		String username = MtqDalDeliveryBus.getInstance().getUserName();
		String password = MtqDalDeliveryBus.getInstance().getPassword();
		login(duid, apptype, version, username, password, listener);
	}

	/**
	 * ��ȡ��¼״̬
	 */
	public boolean getLoginStatus() {
		return MtqDalDeliveryBus.getInstance().getLoginStatus();
	}

	/**
	 * ��ȡ�û���
	 */
	public String getUserName() {
		return MtqDalDeliveryBus.getInstance().getUserName();
	}

	/**
	 * ��ȡ����
	 */
	public String getPassword() {
		return MtqDalDeliveryBus.getInstance().getPassword();
	}

	/**
	 * ��ȡ��¼��Ϣ
	 */
	public MtqLogin getMtqLogin() {
		return MtqDalDeliveryBus.getInstance().getMtqLogin();
	}

	/**
	 * ��ȡȨ��
	 */
	public List<String> getActions() {
		return MtqDalDeliveryBus.getInstance().getActions();
	}

	/**
	 * ��ȡ����
	 */
	public List<MtqGroup> getGroups() {
		return MtqDalDeliveryBus.getInstance().getGroups();
	}

	public void uninit() {
		MtqDalDeliveryBus.getInstance().uninit();
	}

	public static interface IMtqLogoutListener {
		/**
		 * �û��˳��ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode);
	}

	/**
	 * �û��˳�
	 * 
	 * @param timestamp
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @param listener
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void logout(IMtqLogoutListener listener) {
		MtqBllDeliveryBus.getInstance().logout(listener);
	}

	public static interface IMtqCarStateListener {

		/**
		 * ����״̬�б�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqCarState> data, int total);
	}

	/**
	 * ����״̬�б�
	 * 
	 * @param group_id
	 *            ����ID��0Ϊȫ����
	 * @param carstatus
	 *            ����״̬ (0Ϊȫ����1���У�2���ɳ���3��ҵ�У�20Ϊά�ޱ���)
	 * @param keywords
	 *            ģ�������ؼ��֣����ƺ��룩
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getCarStateList(int group_id, int carstatus, String keywords,
			int pageindex, int pagesize, IMtqCarStateListener listener) {
		MtqBllDeliveryBus.getInstance().getCarStateList(group_id, carstatus,
				keywords, pageindex, pagesize, listener);
	}

	public static interface IMtqCarStateCountListener {

		/**
		 * ����״̬ͳ�ƻص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, MtqCarStateCount data);
	}

	/**
	 * ����״̬ͳ��
	 * 
	 * @param group_id
	 *            ����ID��0Ϊȫ����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getCarStateCount(int group_id,
			IMtqCarStateCountListener listener) {
		MtqBllDeliveryBus.getInstance().getCarStateCount(group_id, listener);

	}

	public static interface IMtqCarStateDetailListener {
		/**
		 * ����ʵʱ״̬���ݻص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, MtqCar mtqCar, MtqState mtqState);
	}

	/**
	 * ����ʵʱ״̬����
	 * 
	 * @param carid
	 *            ����ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getCarStateDetail(int carid, IMtqCarStateDetailListener listener) {
		MtqBllDeliveryBus.getInstance().getCarStateDetail(carid, listener);
	}

	public static interface IMtqTaskStoreListener {

		/**
		 * ���������˵����ݻص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqTaskStore> data, int total);
	}

	/**
	 * ���������˵�����
	 * 
	 * @param carid
	 *            ����ID
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getTaskStore(int carid, int pageindex, int pagesize,
			IMtqTaskStoreListener listener) {
		MtqBllDeliveryBus.getInstance().getTaskStore(carid, pageindex,
				pagesize, listener);
	}

	public static interface IMtqTaskNaviListener {

		/**
		 * ���������г����ݻص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqTaskNavi> data, int total);
	}

	/**
	 * ���������г�����
	 * 
	 * @param carid
	 *            ����ID
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getTaskNavi(int carid, int pageindex, int pagesize,
			IMtqTaskNaviListener listener) {
		MtqBllDeliveryBus.getInstance().getTaskNavi(carid, pageindex, pagesize,
				listener);
	}

	public static interface IMtqCarSendListener {

		/**
		 * �������Ȼص�
		 * 
		 * @param errCode
		 * @param systime
		 */
		public void onResult(int errCode);
	}

	/**
	 * ��������
	 * 
	 * @param time
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @param smstype
	 *            ��Ϣ���ͣ�1Ϊ�ı��࣬2Ϊ�����ࣩ
	 * @param carid
	 *            �����ȳ���ID
	 * @param driverid
	 *            ������˾��ID
	 * @param x
	 *            ����λ������X���ı�����
	 * @param y
	 *            ����λ������Y���ı�����
	 * @param addr
	 *            ����λ�õ�ַ���ı�����
	 * @param text
	 *            �ı����ݣ��ı�����
	 * @param voicedata
	 *            �������ݣ���������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void setCarSend(int time, int smstype, int carid, int driverid,
			long x, long y, String addr, String poiname, String text, 
			int voiceduration, String voicedata, 
			IMtqCarSendListener listener) {
		MtqBllDeliveryBus.getInstance().setCarSend(time, smstype, carid,
				driverid, x, y, addr, poiname, text, voiceduration, 
				voicedata, listener);
	}

	public static interface IMtqTrackHistoryListener {

		/**
		 * ��ʷ�켣�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqTrackHistory> data,
				int alarmnum);
	}

	/**
	 * ��ʷ�켣
	 * 
	 * @param carid
	 *            ����ID
	 * @param starttime
	 *            ��ʼʱ�䣨UTCʱ�䣩
	 * @param endtime
	 *            ����ʱ�䣨���ڿ�ʼʱ�䣬С�ڻ���ڵ�ǰʱ�䣬ʱ���Ȳ�����10�죩
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getTrackHistory(int carid, long starttime, long endtime,
			IMtqTrackHistoryListener listener) {
		MtqBllDeliveryBus.getInstance().getTrackHistory(carid, starttime,
				endtime, listener);
	}

	public static interface IMtqAlarmMsgListener {

		/**
		 * ������Ϣ�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqMsgAlarm> data,
				String incrindex);
	}

	/**
	 * ������Ϣ�������飩
	 * 
	 * @param incrindex
	 *            ������ʶ
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getAlarmMsg(String incrindex, int pagesize,
			IMtqAlarmMsgListener listener) {
		MtqBllDeliveryBus.getInstance().getAlarmMsg(incrindex, pagesize,
				listener);
	}

	public static interface IMtqHistoryAlarmMsgListener {

		/**
		 * ��ʷ������Ϣ�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqMsgAlarm> data,
				String incrindex);
	}

	/**
	 * ��ȡ��ʷ������Ϣ�������飩
	 * 
	 * @param incrindex
	 *            ������ʶ
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getHistoryAlarmMsg(long starttime, long endtime, int pageindex,
			int pagesize, int order, IMtqHistoryAlarmMsgListener listener) {
		MtqBllDeliveryBus.getInstance().getHistoryAlarmMsg(starttime, endtime,
				pageindex, pagesize, order, listener);
	}

	public static interface IMtqMsgAlarmReadListener {

		/**
		 * ���ľ�����Ϣ�Ѷ�״̬�ص�
		 * 
		 * @param errCode
		 * @param systime
		 */
		public void onResult(int errCode);
	}

	/**
	 * ���ľ�����Ϣ�Ѷ�״̬
	 * 
	 * @param serialid
	 *            ��¼ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void setMsgAlarmRead(String id, IMtqMsgAlarmReadListener listener) {
		MtqBllDeliveryBus.getInstance().setMsgAlarmRead(id, listener);
	}

	public static interface IMtqSysMsgListener {

		/**
		 * ϵͳ��Ϣ�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqMsgSys> data, String incrindex);
	}

	/**
	 * ϵͳ��Ϣ�������飩
	 * 
	 * @param incrindex
	 *            ������ʶ
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getSysMsg(String incrindex, int pagesize,
			IMtqSysMsgListener listener) {
		MtqBllDeliveryBus.getInstance()
				.getSysMsg(incrindex, pagesize, listener);
	}

	public static interface IMtqHistorySysMsgListener {

		/**
		 * ϵͳ��Ϣ�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqMsgSys> data, String incrindex);
	}

	/**
	 * ��ȡ��ʷϵͳ��Ϣ�������飩
	 * 
	 * @param incrindex
	 *            ������ʶ
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getHistorySysMsg(long starttime, long endtime, int pageindex,
			int pagesize, int order, IMtqHistorySysMsgListener listener) {
		MtqBllDeliveryBus.getInstance().getHistorySysMsg(starttime, endtime,
				pageindex, pagesize, order, listener);
	}

	public static interface IMtqMsgSysReadListener {

		/**
		 * ����ϵͳ��Ϣ�Ѷ�״̬�ص�
		 * 
		 * @param errCode
		 * @param systime
		 */
		public void onResult(int errCode);
	}

	/**
	 * ����ϵͳ��Ϣ�Ѷ�״̬
	 * 
	 * @param serialid
	 *            ��¼ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void setMsgSysRead(String serialid, IMtqMsgSysReadListener listener) {
		MtqBllDeliveryBus.getInstance().setMsgSysRead(serialid, listener);
	}

	public static interface IMtqCarDataListener {

		/**
		 * ���������б�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqCarData> data, int total);
	}

	/**
	 * ���������б�
	 * 
	 * @param group_id
	 *            ����ID��0Ϊȫ����
	 * @param keywords
	 *            ģ�������ؼ��֣����ƺ��룩
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getCarDataList(int group_id, String keywords, int pageindex,
			int pagesize, IMtqCarDataListener listener) {
		MtqBllDeliveryBus.getInstance().getCarDataList(group_id, keywords,
				pageindex, pagesize, listener);
	}

	public static interface IMtqCarDataDetailListener {

		/**
		 * ������������ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, MtqCarDataDetail car,
				List<MtqGroup> groups);
	}

	/**
	 * ������������
	 * 
	 * @param carid
	 *            ����ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getCarDataDetail(int carid, IMtqCarDataDetailListener listener) {
		MtqBllDeliveryBus.getInstance().getCarDataDetail(carid, listener);
	}

	public static interface IMtqDriverDataListener {

		/**
		 * ˾�������б�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqDriver> data, int total);
	}

	/**
	 * ˾�������б�
	 * 
	 * @param invitestatus
	 *            ����״̬��0Ϊȫ����1δ��������Ϣ��2�Ѷ�������Ϣ��3ͬ����복�ӣ�4�ܾ����복�ӣ�5���˳�����)
	 * @param keywords
	 *            ģ�������ؼ��֣�˾��������
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getDriverDataList(int invitestatus, String keywords,
			int pageindex, int pagesize, IMtqDriverDataListener listener) {
		MtqBllDeliveryBus.getInstance().getDriverDataList(invitestatus,
				keywords, pageindex, pagesize, listener);
	}

	public static interface IMtqDriverDataDetailListener {

		/**
		 * ˾����������ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, MtqDriverDetail data);
	}

	/**
	 * ˾����������
	 * 
	 * @param driverid
	 *            ˾��ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getDriverDataDetail(int driverid,
			IMtqDriverDataDetailListener listener) {
		MtqBllDeliveryBus.getInstance().getDriverDataDetail(driverid, listener);
	}

	public static interface IMtqInviteDriverListener {

		/**
		 * ����˾�����복�ӻص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, int invitestatus);
	}

	/**
	 * ����˾�����복��
	 * 
	 * @param driverid
	 *            ˾��ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void inviteDriver(int driverid, IMtqInviteDriverListener listener) {
		MtqBllDeliveryBus.getInstance().inviteDriver(driverid, listener);
	}

	public static interface IMtqOrderCountListener {

		/**
		 * �˵�ͳ�ƿ���ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, MtqOrderCount data);
	}

	/**
	 * �˵�ͳ�ƿ���
	 * 
	 * @param startdate
	 *            ��ѯ��ʼ����
	 * @param enddate
	 *            ��ѯ�������ڣ����ڻ���ڿ�ʼ���ڣ�С�ڻ���ڵ�ǰ���ڣ����ڿ�Ȳ�����31�죩
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getOrderCount(String startdate, String enddate,
			IMtqOrderCountListener listener) {
		MtqBllDeliveryBus.getInstance().getOrderCount(startdate, enddate,
				listener);
	}

	public static interface IMtqTaskCountListener {

		/**
		 * ����ƻ�ͳ�ƿ���ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, MtqTaskCount data);
	}

	/**
	 * ����ƻ�ͳ�ƿ���
	 * 
	 * @param startdate
	 *            ��ѯ��ʼ����
	 * @param enddate
	 *            ��ѯ�������ڣ����ڻ���ڿ�ʼ���ڣ�С�ڻ���ڵ�ǰ���ڣ����ڿ�Ȳ�����31�죩
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void getTaskCount(String startdate, String enddate,
			IMtqTaskCountListener listener) {
		MtqBllDeliveryBus.getInstance().getTaskCount(startdate, enddate,
				listener);
	}

	public static interface IMtqFeedbackListener {

		/**
		 * �²۷����ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode);
	}

	/**
	 * �²۷���
	 * 
	 * @param fdtype
	 *            �������ͣ�1Ϊƽ̨�࣬2ΪӲ���ࣩ
	 * @param dtype
	 *            �豸���ͣ�2Ϊ����˫ģһ�����3Ϊ������KPND��4ΪTD-BOX��5ΪTD-PND��
	 * @param title
	 *            ����
	 * @param content
	 *            ��������
	 * @param contact
	 *            ��ϵ��ʽ
	 * @param pics
	 *            ͼƬ�ļ���Ϣ��ý���ʶID�������Сд��;���ֺŷָ���ͼƬ�ļ���ͨ�����ϴ�������Ƭ�ļ����ӿ��ϴ���
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void setFeedback(int fdtype, int dtype, String title,
			String content, String contact, String pics,
			IMtqFeedbackListener listener) {
		MtqBllDeliveryBus.getInstance().setFeedback(fdtype, dtype, title,
				content, contact, pics, listener);
	}

	public static interface IMtqUploadAttachPicListener {

		/**
		 * �ϴ�������Ƭ�ļ��ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, String mediaid);
	}

	/**
	 * �ϴ�������Ƭ�ļ�
	 * 
	 * @param time
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @param x
	 *            ����ʱ����������X����λʧ��Ϊ0��
	 * @param y
	 *            ����ʱ����������Y����λʧ��Ϊ0��
	 * @param data
	 *            ͼƬ���ݣ�base64��
	 * 
	 * @author zhaoqy
	 * @date 2017-06-10
	 */
	public void uploadAttachPic(int x, int y, String data,
			IMtqUploadAttachPicListener listener) {
		MtqBllDeliveryBus.getInstance().uploadAttachPic(x, y, data, listener);
	}

	public static interface IMtqDeviceDTypeListener {

		/**
		 * Ӳ���豸���ͻص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onResult(int errCode, List<MtqDeviceDType> data);
	}

	/**
	 * Ӳ���豸����
	 * 
	 * @param timestamp
	 *            ����ʱ�䣨UTCʱ�䣩
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getDeviceDType(IMtqDeviceDTypeListener listener) {
		MtqBllDeliveryBus.getInstance().getDeviceDType(listener);
	}
}
