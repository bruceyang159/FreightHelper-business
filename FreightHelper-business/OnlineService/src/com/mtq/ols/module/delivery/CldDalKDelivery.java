/*
 * @Title CldDalKDelivery.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-12-9 ����10:42:32
 * @version 1.0
 */
package com.mtq.ols.module.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.cld.db.sqlite.Selector;
import com.cld.db.utils.CldDbUtils;
import com.cld.db.utils.DbException;
import com.cld.log.CldLog;
import com.mtq.ols.api.CldKServiceAPI;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.AuthInfoList;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliGroup;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliTaskDB;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldMonitorAuth;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetUnFinishTaskCount.ProtUnFinishData;
import com.mtq.ols.module.delivery.bean.MtqDeliTask;

/**
 * �������ݲ�
 * 
 * @author Zhouls
 * @date 2015-12-9 ����10:42:32
 */
public class CldDalKDelivery {

	private String TAG = "ols_delivery";
	private List<CldMonitorAuth> lstOfMonitorAuth;// �û���Ȩ�б�
	private String key;// ��Ȩ��Կ ����
	private String expiry_time;// �ʺż�Ȩ��ֹʱ��
	private int state;// ״̬��0Ϊδ�������1Ϊ����������2Ϊ�����ѹ��ڣ�
	private int lockcorpid; // ��������ҵID
	private List<CldDeliGroup> lstOfMyGroups;// �ҵĳ�����Ϣ
	private List<CldDeliGroup> lstOfAuthCorps;// ��Ȩ�ŵ���ҵ�б�
	private List<AuthInfoList> lstOfAuthInfo;// ��Ȩ�ŵ���ҵ�б�

	private int unReadTaskCount;// δ���˻�������

	private boolean isLoginAuth = false;

	private static CldDalKDelivery instance;

	public static CldDalKDelivery getInstance() {
		if (null == instance) {
			instance = new CldDalKDelivery();
		}
		return instance;
	}

	private CldDalKDelivery() {
		// �޳��� ���ߵ�¼��Ȩδȡ�����ʹ��Ĭ��ֵ
		lstOfMonitorAuth = new ArrayList<CldMonitorAuth>();
		lstOfMyGroups = new ArrayList<CldDeliGroup>();
		lstOfAuthCorps = new ArrayList<CldDeliGroup>();
		lstOfAuthInfo = new ArrayList<CldSapKDeliveryParam.AuthInfoList>();
		key = "";
		state = -1;
		expiry_time = "";
		lockcorpid = 0;
		unReadTaskCount = 0;
	}

	/**
	 * ��¼��Ȩ���������
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����2:15:19
	 */
	public void uninitData() {
		lstOfMonitorAuth = new ArrayList<CldMonitorAuth>();
		lstOfMyGroups = new ArrayList<CldDeliGroup>();
		lstOfAuthCorps = new ArrayList<CldDeliGroup>();
		lstOfAuthInfo = new ArrayList<CldSapKDeliveryParam.AuthInfoList>();
		mapOfUnFinishTaskCount = new HashMap<String, Object>();
		key = "";
		state = -1;
		expiry_time = "";
		lockcorpid = 0;
		unReadTaskCount = 0;
	}

	/**
	 * ��ʼ������
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-18 ����6:52:12
	 */
	public void initData() {
		lstOfMonitorAuth = new ArrayList<CldMonitorAuth>();
		lstOfMyGroups = new ArrayList<CldDeliGroup>();
		lstOfAuthCorps = new ArrayList<CldDeliGroup>();
		lstOfAuthInfo = new ArrayList<CldSapKDeliveryParam.AuthInfoList>();
		mapOfUnFinishTaskCount = new HashMap<String, Object>();
		key = "";
		state = -1;
		expiry_time = "";
		lockcorpid = 0;
		unReadTaskCount = 0;
	}

	/**
	 * ��������Ȩ�б�
	 * 
	 * @param bean
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:10:18
	 */
	public void addToMonitorLst(CldMonitorAuth bean) {
		if (null != lstOfMonitorAuth && null != bean) {
			if (null == getMonitor(bean.id)) {
				lstOfMonitorAuth.add(0, bean);
			} else {
				CldLog.e(TAG, "add a exsit bean!");
			}
		}
	}

	/**
	 * ��ȡ�б�����»�ȡ
	 * 
	 * @param lstOfBean
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����7:27:22
	 */
	public void reloadMonitorLst(List<CldMonitorAuth> lstOfBean) {
		if (null != lstOfBean && null != lstOfMonitorAuth) {
			lstOfMonitorAuth.clear();
			lstOfMonitorAuth.addAll(lstOfBean);
		}
	}

	/**
	 * ��ȡ�ڴ�����Ȩ��Ϣ�б�
	 * 
	 * @return
	 * @return List<CldMonitorAuth>
	 * @author Zhouls
	 * @date 2015-12-9 ����7:29:46
	 */
	public List<CldMonitorAuth> getMonitorAuthLst() {
		List<CldMonitorAuth> lstOfBean = new ArrayList<CldMonitorAuth>();
		lstOfBean.addAll(lstOfMonitorAuth);
		return lstOfBean;
	}

	/**
	 * �޸���Ȩ��Ϣ
	 * 
	 * @param id
	 * @param mark
	 * @param timeOut
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:12:30
	 */
	public void reviseMonitor(String id, String mark, long timeOut) {
		CldMonitorAuth temp = getMonitor(id);
		if (null != temp) {
			// �����id���޸�
			if (!TextUtils.isEmpty(mark)) {
				temp.mark = (mark);
			}
			if (timeOut > 0) {
				temp.timeOut = (timeOut);
			}
		}
	}

	/**
	 * ɾ����Ȩ��Ϣ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:14:27
	 */
	public void delMonitor(String id) {
		if (null == lstOfMonitorAuth || lstOfMonitorAuth.size() <= 0
				|| TextUtils.isEmpty(id)) {
			return;
		}
		for (int i = 0; i < lstOfMonitorAuth.size(); i++) {
			if (null == lstOfMonitorAuth.get(i)) {
				continue;
			}
			if (id.equals(lstOfMonitorAuth.get(i).id)) {
				lstOfMonitorAuth.remove(i);
			}
		}
	}

	/**
	 * ���б��л�ȡָ��id����Ȩ��Ϣ�����򷵻أ����򷵻�null��
	 * 
	 * @param id
	 * @return
	 * @return CldMonitorAuth
	 * @author Zhouls
	 * @date 2015-12-9 ����11:05:41
	 */
	private CldMonitorAuth getMonitor(String id) {
		if (null == lstOfMonitorAuth || lstOfMonitorAuth.size() <= 0
				|| TextUtils.isEmpty(id)) {
			return null;
		}
		for (int i = 0; i < lstOfMonitorAuth.size(); i++) {
			if (null == lstOfMonitorAuth.get(i)) {
				continue;
			}
			if (id.equals(lstOfMonitorAuth.get(i).id)) {
				return lstOfMonitorAuth.get(i);
			}
		}
		return null;
	}

	public boolean isLoginAuth() {
		return isLoginAuth;
	}

	public synchronized void setLoginAuth(boolean isLoginAuth) {
		this.isLoginAuth = isLoginAuth;
	}

	/** @return the cldDeliveryKey */
	public String getCldDeliveryKey() {
		return key;
	}

	/**
	 * @param cldDeliveryKey
	 *            the cldDeliveryKey to set
	 */
	public void setCldDeliveryKey(String cldDeliveryKey) {
		this.key = cldDeliveryKey;
	}

	/** @return the state */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/** @return the lockcorpid */
	public int getLockcorpid() {
		return lockcorpid;
	}

	/**
	 * @param lockcorpid
	 *            the lockcorpid to set
	 */
	public void setLockcorpid(int lockcorpid) {
		this.lockcorpid = lockcorpid;
	}

	/**
	 * ɾ����ҵ���յ��˳�������Ϣ�󣬸�����ҵ�б�
	 * 
	 * @param cropid
	 */
	public void deleteCorp(String cropid) {
		if (lstOfMyGroups != null && !lstOfMyGroups.isEmpty()) {
			List<CldDeliGroup> temp = new ArrayList<CldDeliGroup>();
			temp.addAll(lstOfMyGroups);

			for (int i = 0; i < temp.size(); i++) {
				if (cropid.equals(temp.get(i).corpId)) {
					lstOfMyGroups.remove(i);
				}
			}
		}
	}

	public CldDeliGroup getCldDeliGroup(String cropid) {
		if (lstOfMyGroups != null && !lstOfMyGroups.isEmpty()) {
			for (int i = 0; i < lstOfMyGroups.size(); i++) {
				if (cropid.equals(lstOfMyGroups.get(i).corpId)) {
					return lstOfMyGroups.get(i);
				}
			}
		}
		return null;
	}

	/** @return the lstOfMyGroups */
	public List<CldDeliGroup> getLstOfMyGroups() {
		return lstOfMyGroups;
	}

	/**
	 * @param lstOfMyGroups
	 *            the lstOfMyGroups to set
	 */
	public void setLstOfMyGroups(List<CldDeliGroup> lstOfMyGroups) {
		this.lstOfMyGroups = lstOfMyGroups;
	}

	public List<AuthInfoList> getLstOfAuthInfo() {
		return lstOfAuthInfo;
	}

	public void setLstOfAuthInfo(List<AuthInfoList> lstOfAuthInfo) {
		this.lstOfAuthInfo = lstOfAuthInfo;
	}

	/** @return the expiry_time */
	public String getExpiry_time() {
		return expiry_time;
	}

	/**
	 * @param expiry_time
	 *            the expiry_time to set
	 */
	public void setExpiry_time(String expiry_time) {
		this.expiry_time = expiry_time;
	}

	/** @return the lstOfAuthCorps */
	public List<CldDeliGroup> getLstOfAuthCorps() {
		return lstOfAuthCorps;
	}

	/**
	 * @param lstOfAuthCorps
	 *            the lstOfAuthCorps to set
	 */
	public void setLstOfAuthCorps(List<CldDeliGroup> lstOfAuthCorps) {
		this.lstOfAuthCorps = lstOfAuthCorps;
	}

	/** @return the unReadTaskCount */
	public int getUnReadTaskCount() {
		return unReadTaskCount;
	}

	/**
	 * @param unReadTaskCount
	 *            the unReadTaskCount to set
	 */
	public void setUnReadTaskCount(int unReadTaskCount) {
		this.unReadTaskCount = unReadTaskCount;
	}

	/**
	 * ȡ�˻���ʱͬ���Ķ�״̬
	 * 
	 * @param lstOfDeliTask
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-18 ����6:16:35
	 */
	public void syncTaskReadStatus(List<MtqDeliTask> lstOfDeliTask) {
		if (null == lstOfDeliTask || lstOfDeliTask.size() <= 0) {
			return;
		}
		long kuid = CldKServiceAPI.getInstance().getKuid();
		if (kuid <= 0) {
			return;
		}
		List<CldDeliTaskDB> newStatus = new ArrayList<CldDeliTaskDB>();
		// �����ȶ� ͬ����������
		for (int i = 0; i < lstOfDeliTask.size(); i++) {
			CldDeliTaskDB newDB = new CldDeliTaskDB();
			MtqDeliTask temp = lstOfDeliTask.get(i);
			if (null == temp) {
				continue;
			}
			long id = 0;
			try {
				long corp = Long.parseLong(temp.corpid);
				long task = Long.parseLong(temp.taskid);
				id = (corp << 32) + task;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (id == 0) {
				continue;
			}
			List<CldDeliTaskDB> lstOfLocal = getLocTaskReadCach(kuid);
			if (null != lstOfLocal && lstOfLocal.size() > 0) {
				// ÿ�������� ��Ҫ�жϱ����Ƿ����Ѷ�״̬������Ѷ����������е�״̬�ĵ�
				for (int j = 0; j < lstOfLocal.size(); j++) {
					long locId = lstOfLocal.get(j).id;
					int locStatus = lstOfLocal.get(j).status;
					// �߼�����
					if (id == locId && temp.readstatus == 0 && locStatus == 1) {
						// ���ֱ����Ѷ� ���ø��˻����Ѷ�
						temp.readstatus = 1;
					}
				}
			}
			newDB.id = id;
			newDB.kuid = kuid;
			newDB.status = temp.readstatus;
			newStatus.add(newDB);
		}
		CldDbUtils.deleteAll(CldDeliTaskDB.class);
		CldDbUtils.saveAll(newStatus);
	}

	/**
	 * �����˻���ʧ�ܵ�ʱ���Ķ�״̬���뱾��
	 * 
	 * @param corpid
	 * @param taskid
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-18 ����6:22:32
	 */
	public void saveTaskSatus(String corpid, String taskid) {
		// try {
		// long corp = Long.parseLong(corpid);
		// long task = Long.parseLong(taskid);
		// CldDeliTaskDB db = new CldDeliTaskDB();
		// db.id = (corp << 32) + task;
		// db.kuid = CldKServiceAPI.getInstance().getKuid();
		// db.status = 1;
		// CldDbUtils.save(db);
		// } catch (NumberFormatException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * ��ȡ����δ���˻�������
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2016-5-18 ����6:28:43
	 */
	public int getLocUnreadTaskCount(long kuid) {
		int count = 0;
		try {
			count = (int) CldDbUtils.getDbInstance().count(
					Selector.from(CldDeliTaskDB.class).where("kuid", "=", kuid)
							.and("status", "=", 0));
			if (count > 50) {
				CldLog.e("ols", "too much task hasn't sync!");
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * ��ȡָ��Kuid�������񵥵�״̬
	 * 
	 * @param kuid
	 * @return
	 * @return List<CldDeliTaskReadStatusDB>
	 * @author Zhouls
	 * @date 2016-5-7 ����5:36:29
	 */
	public List<CldDeliTaskDB> getLocTaskReadCach(long kuid) {
		List<CldDeliTaskDB> lst = null;
		try {
			lst = CldDbUtils.getDbInstance()
					.findAll(
							Selector.from(CldDeliTaskDB.class).where("kuid",
									"=", kuid));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	/** δ����˻������� */
	private Map<String, Object> mapOfUnFinishTaskCount;

	/**
	 * �洢δ����˻�������
	 * 
	 * @param lstOfData
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-23 ����5:58:45
	 */
	public void saveUnFinishCountMap(List<ProtUnFinishData> lstOfData) {
		if (null != mapOfUnFinishTaskCount && null != lstOfData
				&& lstOfData.size() > 0) {
			for (int i = 0; i < lstOfData.size(); i++) {
				ProtUnFinishData temp = lstOfData.get(i);
				if (null == temp) {
					continue;
				}
				mapOfUnFinishTaskCount.put(temp.status + "", temp.count);
			}
		}
	}

	/**
	 * ��ȡδ����˻�������
	 * 
	 * @param status
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2016-5-23 ����5:58:13
	 */
	public int getUnFinishCountByStatus(int status) {
		if (null == mapOfUnFinishTaskCount) {
			CldLog.e("ols", "mapOfUnFinishTaskCount is null!");
			return 0;
		}
		if (mapOfUnFinishTaskCount.containsKey(status + "")) {
			try {
				int count = (Integer) mapOfUnFinishTaskCount.get(status + "");
				return count;
			} catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return 0;
	}
}
