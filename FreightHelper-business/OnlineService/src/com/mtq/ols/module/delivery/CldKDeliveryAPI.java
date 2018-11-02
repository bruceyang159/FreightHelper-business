/*
 * @Title CldKDeliveryTrcApi.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-12-9 ����9:53:08
 * @version 1.0
 */
package com.mtq.ols.module.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.util.Log;

import com.cld.db.utils.CldDbUtils;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.api.CldKServiceAPI;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.AuthInfoList;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldAuthTimeOut;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliCorpLimit;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliCorpLimitMapParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliCorpLimitRouteParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliCorpRoutePlanResult;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliCorpWarning;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliGroup;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliReceiptParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliSearchStoreResult;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliTaskDB;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliUploadStoreParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldElectfence;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldMonitorAuth;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldReUploadEFParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldUploadEFParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqCar;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqCarRoute;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqDeviceCar;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqTask;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqTaskDetail;
import com.mtq.ols.module.delivery.bean.MtqDeliReceiveData;
import com.mtq.ols.module.delivery.bean.MtqDeliTask;
import com.mtq.ols.module.delivery.bean.MtqDeliTaskDetail;
import com.mtq.ols.sap.CldSapUtil;
import com.mtq.ols.tools.err.CldOlsErrManager.CldOlsErrCode;
import com.mtq.ols.tools.model.CldOlsInterface.ICldResultListener;

/**
 * ���˽ӿ�ģ��
 * 
 * @author Zhouls
 * @date 2015-12-9 ����9:53:08
 */
public class CldKDeliveryAPI {
	/**
	 * 
	 * ���˳�ʼ���ص�
	 * 
	 * @author Zhouls
	 * @date 2016-5-7 ����5:10:25
	 */
	public static interface ICldDeliInitListener {
		/**
		 * ��¼��Ȩ���
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2016-5-10 ����5:43:21
		 */
		public void onLoginAuth(int errCode);

		/**
		 * �Ƿ���ʾ��Ӧ�����
		 * 
		 * @param isDisplayTask
		 * @param isDisplayStore
		 * @return void
		 * @author Zhouls
		 * @date 2016-5-9 ����6:00:13
		 */
		public void onDisplayEnterResult(boolean isDisplayTask,
				boolean isDisplayStore);

		/**
		 * ����δ�����˵������ص�
		 * 
		 * @return void
		 * @author Zhouls
		 * @date 2016-5-7 ����5:12:02
		 */
		public void onUpdateUnreadTaskCount();

		/**
		 * ����δ����˻�������
		 * 
		 * @return void
		 * @author Zhouls
		 * @date 2016-5-25 ����5:57:38
		 */
		public void onUpdateUnfinishTaskCount();

	}

	/**
	 * ���˳�ʼ�����ڵ�¼�����˳���¼���ã�
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-10 ����6:36:29
	 */
	public void init(final ICldDeliInitListener listener) {
		authListener = listener;
		CldBllKDelivery.getInstance().init(listener);
	}

	/**
	 * ��ʼ����Ȩ��Ϣ�б�,�ӷ���˻�ȡ��Ȩ�б��첽��
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:29:48
	 */
	public void initMonitorAuthList(final ICldDeliveryMonitorListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getMonitorAuthList(listener);
			}
		});
	}

	/**
	 * ������Ȩ��Ϣ
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param mark
	 *            ��ע
	 * @param timeOut
	 *            ��Ч��
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:27:18
	 */
	public void addMonitorAuth(final String mobile, final String mark,
			final CldAuthTimeOut timeOut, final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().addMonitorAuth(mobile, mark,
						timeOut, listener);
			}
		});
	}

	/**
	 * ɾ����Ȩ��Ϣ
	 * 
	 * @param id
	 *            ��ʶid
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:28:03
	 */
	public void delMonitorAuth(final String id,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().delMonitorAuth(id, listener);
			}
		});
	}

	/**
	 * ��ȡ·���ϵ���������
	 * 
	 * @param parm
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-14 ����4:44:38
	 */
	public void getRouteCorpLimitData(final CldDeliCorpLimitRouteParm parm,
			final ICldDeliGetCorpLimitListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetCorpLimitResult(errCode, null, -1, null, -1);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getRouteCorpLimitData(parm,
						listener);
			}
		});
	}

	/**
	 * ��ȡͼ����������
	 * 
	 * @param parm
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-14 ����4:44:25
	 */
	public void getMapCorpLimitData(final CldDeliCorpLimitMapParm parm,
			final ICldDeliGetCorpLimitListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetCorpLimitResult(errCode, null, -1, null, -1);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getMapCorpLimitData(parm,
						listener);
			}
		});
	}

	/**
	 * ��ȡ�˻�������
	 * 
	 * @param corpid
	 *            ��ҵID
	 * @param taskid
	 *            �˻���ID
	 * @param page
	 *            �˻���ҳ��
	 * @param pagesize
	 *            �˻���ҳ����
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-28 ����3:24:53
	 */
	public void getDeliTaskDetail(final String corpid, final String taskid,
			final int page, final int pagesize,
			final ICldDeliGetTaskDetailListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetTaskDetailResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getDeliTaskDetail(corpid, taskid,
						page, pagesize, new ICldDeliGetTaskDetailListener() {

							@Override
							public void onGetTaskDetailResult(int errCode,
									MtqDeliTaskDetail taskInfo) {
								// TODO Auto-generated method stub
								CldDalKDelivery.getInstance().saveTaskSatus(
										corpid, taskid);
								if (null != listener) {
									listener.onGetTaskDetailResult(errCode,
											taskInfo);
								}
							}
						});
			}
		});
	}

	/**
	 * ��ȡ��ǰ��¼�û��˻����б�
	 * 
	 * @param status
	 *            �˻�״̬��0���˻�1�˻���2�����3��ͣ״̬4��ֹ״̬ �����á�|���ָ�֧�ֻ�ȡ���״̬��ȡ��
	 * @param corpid
	 *            ָ����ҵID����ָ���������м�����ҵ�����ݣ�
	 * @param page
	 *            ҳ��(Ĭ��1��
	 * @param pagesize
	 *            ÿҳ����(Ĭ��10)
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-28 ����3:27:47
	 */
	public void getDeliTaskHistoryList(final String status,
			final String corpid, final int page, final int pagesize,
			final ICldDeliGetTaskHistoryListListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetTaskLstResult(errCode, null, -1, -1, -1);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getDeliTaskHistoryList(status,
						corpid, page, pagesize, listener);
			}
		});
	}

	/**
	 * ��ȡ�û����˻����б�
	 * 
	 * @param corpid
	 *            ָ����ҵID����ָ����null,�������м�����ҵ�����ݣ�
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-28 ����3:28:45
	 */
	public void getDeliTaskList(final String corpid,
			final ICldDeliGetTaskListListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetTaskLstResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getDeliTaskList(corpid, listener);
			}
		});
	}

	/**
	 * ��ȡδ���˻�������
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2016-5-7 ����4:58:16
	 */
	public int getUnreadTaskCount() {
		if (!CldKAccountAPI.getInstance().isLogined()
				|| !CldDalKDelivery.getInstance().isLoginAuth()) {
			return 0;
		}
		int webCount = CldDalKDelivery.getInstance().getUnReadTaskCount();
		long kuid = CldKServiceAPI.getInstance().getKuid();
		List<CldDeliTaskDB> lstOfLoc = CldDalKDelivery.getInstance()
				.getLocTaskReadCach(kuid);
		if (null == lstOfLoc || lstOfLoc.size() <= 0) {
			return webCount;
		}
		return CldDalKDelivery.getInstance().getLocUnreadTaskCount(kuid);
	}

	/**
	 * ��ȡ�����˻�������
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2016-5-31 ����2:51:47
	 */
	public int getLocTaskCount() {
		if (!CldKAccountAPI.getInstance().isLogined()
				|| !CldDalKDelivery.getInstance().isLoginAuth()) {
			return 0;
		}
		long kuid = CldKServiceAPI.getInstance().getKuid();
		List<CldDeliTaskDB> lstOfLoc = CldDalKDelivery.getInstance()
				.getLocTaskReadCach(kuid);
		return lstOfLoc.size();
	}

	/**
	 * ��ȡ��Ȩ�ŵ���ҵ�б�
	 * 
	 * @return
	 * @return List<CldDeliGroup>
	 * @author Zhouls
	 * @date 2016-5-7 ����4:59:25
	 */
	public List<CldDeliGroup> getAuthStoreCorpList() {
		if (!CldKAccountAPI.getInstance().isLogined()
				|| !CldDalKDelivery.getInstance().isLoginAuth()) {
			return new ArrayList<CldDeliGroup>();
		}
		return CldDalKDelivery.getInstance().getLstOfAuthCorps();
	}

	/**
	 * ��ȡ��Ȩ�б��ӻ���ģ���ȡ��Ȩ�б�
	 * 
	 * @return
	 * @return List<CldMonitorAuth>
	 * @author Zhouls
	 * @date 2015-12-10 ����6:59:13
	 */
	public List<CldMonitorAuth> getMonitorAuthList() {
		return CldDalKDelivery.getInstance().getMonitorAuthLst();
	}

	private ICldDeliInitListener authListener;

	/**
	 * ͬ����복��
	 * 
	 * @param inviteCode
	 *            �����루��12λ������ɣ�
	 * @param listener
	 * @return void (1301������ �¼������ҵΪ��������Ѽ����˶�����ҵ������lockid�ж����������)
	 * @author Zhouls
	 * @date 2016-4-28 ����3:20:25
	 */
	public void joinGroup(final String inviteCode,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().joinGroup(inviteCode, listener);
			}
		});
	}

	/**
	 * 
	 * �޸ı�ע
	 * 
	 * @param id
	 *            ��ʶid
	 * @param mark
	 *            ��ע
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:28:43
	 */
	public void reviseMonitorAuthMark(final String id, final String mark,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().reviseMonitorAuthMark(id, mark,
						listener);
			}
		});
	}

	/**
	 * �޸���Ȩ��Ч��
	 * 
	 * @param id
	 *            ��ʶid
	 * @param timeOut
	 *            ��Ч��
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:28:25
	 */
	public void reviseMonitorAuthTimeOut(final String id,
			final CldAuthTimeOut timeOut, final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().reviseMonitorAuthTimeOut(id,
						timeOut, listener);
			}
		});
	}

	/**
	 * ��ȡ�ܱ��ŵ�
	 * 
	 * @param corpid
	 *            ��ҵID
	 * @param centerX
	 *            ��ǰλ��X
	 * @param centerY
	 *            ��ǰλ��Y
	 * @param range
	 *            ������Χ�뾶��λ(�ף����20KM)
	 * @param page
	 *            ��ǰҳ(��1��ʼ��Ĭ��Ϊ1)
	 * @param pageSize
	 *            ÿҳ��ʾ����(Ĭ��20�����100)
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����6:13:28
	 */
	public void searchNearStores(final String corpid, final int centerX,
			final int centerY, final int range, final int page,
			final int pageSize, final ICldDeliSearchStoreListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().searchNearStores(corpid, centerX,
						centerY, range, page, pageSize, listener);
			}
		});
	}

	/**
	 * ����δ��ע�ŵ�
	 * 
	 * @param corpid
	 *            ָ����ҵID
	 * @param region
	 *            city ��������
	 * @param page
	 *            ��ǰҳ(��1��ʼ��Ĭ��Ϊ1)
	 * @param pageSize
	 *            ÿҳ��ʾ����(Ĭ��20�����100)
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����6:14:29
	 */
	public void searchNoPosStores(final String corpid, final String regioncity,
			final int page, final int pageSize,
			final ICldDeliSearchStoreListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().searchNoPosStores(corpid,
						regioncity, page, pageSize, listener);
			}
		});
	}

	/**
	 * �����ŵ�
	 * 
	 * @param corpid
	 *            ָ����ҵID
	 * @param keyword
	 *            ������
	 * @param type��������
	 *            (1��λ���ŵ�����,0��λ�ô����)
	 * @param page
	 *            ��ǰҳ(��1��ʼ��Ĭ��Ϊ1)
	 * @param pageSize
	 *            ÿҳ��ʾ����(Ĭ��20�����100)
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����6:12:58
	 */
	public void searchStores(final String corpid, final String keyword,
			final int type, final int page, final int pageSize,
			final ICldDeliSearchStoreListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().searchStores(corpid, keyword,
						type, page, pageSize, listener);
			}
		});
	}

	/**
	 * �ܾ����복��
	 * 
	 * @param inviteCode
	 *            �����루��12λ������ɣ�
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-28 ����3:21:22
	 */
	public void unJoinGroup(final String inviteCode,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().unJoinGroup(inviteCode, listener);
			}
		});
	}

	/**
	 * �����˻���״̬
	 * 
	 * @param corpid
	 *            ��������������ҵID
	 * @param taskid
	 *            ��������ID
	 * @param status
	 *            �޸��˻���״̬��0���˻�1�˻���2�����3��ͣ״̬4��ֹ״̬ ��
	 * @param ecorpid
	 *            ��Ҫ��ͣ���˻���������ҵID
	 * @param etaskid
	 *            ��Ҫ��ͣ���˻���ID
	 * @param x
	 *            �ϱ�λ��X
	 * @param y
	 *            �ϱ�λ��Y
	 * @param cell
	 *            ��·ͼ��ID
	 * @param uid
	 *            ��·UID
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-14 ����4:41:55
	 */
	public void updateDeliTaskStatus(final String corpid, final String taskid,
			final int status, final String ecorpid, final String etaskid,
			final long x, final long y, final int cell, final int uid,
			final ICldDeliTaskStatusListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onUpdateTaskStatus(errCode, null, null, -1);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().updateDeliTaskStatus(corpid,
						taskid, status, ecorpid, etaskid, x, y, cell, uid,
						new ICldDeliTaskStatusListener() {

							@Override
							public void onUpdateTaskStatus(int errCode,
									String corpid, String taskid, int status) {
								// TODO Auto-generated method stub
								CldDalKDelivery.getInstance().saveTaskSatus(
										corpid, taskid);
								if (null != listener) {
									listener.onUpdateTaskStatus(errCode,
											ecorpid, etaskid, status);
								}
							}
						});
			}
		});
	}

	/**
	 * �����˻���״̬
	 * 
	 * @param corpid
	 *            ��ҵID
	 * @param taskid
	 *            ��������ID
	 * @param storeid
	 *            �ŵ�ID
	 * @param status
	 *            �޸��˻���״̬��0δ��ʼ1������2�����3��ͣ״̬4��ֹ״̬ ��
	 * @param x
	 *            �ϱ�λ��X
	 * @param y
	 *            �ϱ�λ��Y
	 * @param cell
	 *            ��·ͼ��ID
	 * @param uid
	 *            ��·UID
	 * @param waybill
	 *            �˻����
	 * @param ewaybill
	 *            ��Ҫ��ͣ���˻���
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-10 ����11:14:57
	 */
	public void updateDeliTaskStoreStatus(final String corpid,
			final String taskid, final String storeid, final int status,
			final long x, final long y, final int cell, final int uid,
			final String waybill, final String ewaybill,
			final ICldDeliTaskStoreStatusListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onUpdateTaskStoreStatus(errCode, null, null, null,
							-1, null, null, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().updateDeliTaskStoreStatus(corpid,
						taskid, storeid, status, x, y, cell, uid, waybill,
						ewaybill, -1, listener);
			}
		});
	}

	/**
	 * �����˻���״̬
	 * 
	 * @param corpid
	 *            ��ҵID
	 * @param taskid
	 *            ��������ID
	 * @param storeid
	 *            �ŵ�ID
	 * @param status
	 *            �޸��˻���״̬��0δ��ʼ1������2�����3��ͣ״̬4��ֹ״̬ ��
	 * @param x
	 *            �ϱ�λ��X
	 * @param y
	 *            �ϱ�λ��Y
	 * @param cell
	 *            ��·ͼ��ID
	 * @param uid
	 *            ��·UID
	 * @param waybill
	 *            �˻����
	 * @param ewaybill
	 *            ��Ҫ��ͣ���˻���
	 * @param taskStatus
	 *            �˻���������ĵ�״̬
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-10 ����11:14:57
	 */
	public void updateDeliTaskStoreStatus(final String corpid,
			final String taskid, final String storeid, final int status,
			final long x, final long y, final int cell, final int uid,
			final String waybill, final String ewaybill, final int taskStatus,
			final ICldDeliTaskStoreStatusListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onUpdateTaskStoreStatus(errCode, null, null, null,
							-1, null, null, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().updateDeliTaskStoreStatus(corpid,
						taskid, storeid, status, x, y, cell, uid, waybill,
						ewaybill, taskStatus, listener);
			}
		});
	}

	/**
	 * �ϴ�������Ƭ���ߵ��ӻص�ͼƬ
	 * 
	 * */
	public void uploadDeliPicture(final String corpId, final String taskId,
			final String wayBill, final String cust_order_id,
			final long upTime, final int pic_type, final long pic_time,
			final int pic_x, final int pic_y, final String base64_pic,
			final ICldResultListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub

				Log.e("uploadpic_kdeli", "faildeal");

				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {

				Log.e("uploadpic_kdeli", "todeal");

				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().uploadDeliPicture(corpId, taskId,
						wayBill, cust_order_id, upTime, pic_type, pic_time,
						pic_x, pic_y, base64_pic, listener);
			}
		});

	}

	/**
	 * �ϴ�����ɨ���¼
	 * */

	public void uploadGoodScanRecord(final String corpId, final String taskId,
			final String wayBill, final String cust_order_id,
			final String bar_code, final long upTime, final int scan_x,
			final int scan_y, final ICldResultListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().uploadGoodScanRecord(corpId,
						taskId, wayBill, cust_order_id, bar_code, upTime,
						scan_x, scan_y, listener);
			}
		});

	}

	/**
	 * �ϱ��տ���Ϣ
	 * 
	 * @param param
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����6:10:26
	 */
	public void uploadReceipt(final CldDeliReceiptParm param,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().uploadReceipt(param, listener);
			}
		});
	}

	/**
	 * ��ע�ŵ�
	 * 
	 * @param param
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����6:15:14
	 */
	public void uploadStore(final CldDeliUploadStoreParm param,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().uploadStore(param, listener);
			}
		});
	}

	/**
	 * ��ȡ�ҵĳ����б�
	 * 
	 * @return
	 * @return List<CldDeliGroup>
	 * @author Zhouls
	 * @date 2016-4-29 ����9:27:42
	 */
	public List<CldDeliGroup> getMyGroups() {
		if (!CldKAccountAPI.getInstance().isLogined()) {
			return new ArrayList<CldDeliGroup>();
		} else {
			return CldDalKDelivery.getInstance().getLstOfMyGroups();
		}
	}

	public void deleteCorp(String cropid) {
		CldDalKDelivery.getInstance().deleteCorp(cropid);
	}

	public CldDeliGroup getCldDeliGroup(String cropid) {
		return CldDalKDelivery.getInstance().getCldDeliGroup(cropid);
	}

	/**
	 * ��ȡ��������ҵID
	 */
	public int getLockcorpid() {
		if (!CldKAccountAPI.getInstance().isLogined()) {
			return -1;
		} else {
			return CldDalKDelivery.getInstance().getLockcorpid();
		}
	}

	/**
	 * ��ȡ��ҵ�ƶ�·��(ͬ���ӿ����ⲿ�����߳�)
	 * 
	 * @param isroute
	 *            �Ƿ�ʹ����ҵ��·����滮[0-��ʹ��(Ĭ��ֵ)��1-ʹ��]
	 * @param corpid
	 *            ��ҵID����ʹ����ҵ��·ʱ��0��������������������ҵID��
	 * @param taskid
	 *            ��������ID��������0��
	 * @param routeid
	 *            �Ƽ���·ID��ʹ�����Ƽ���·������ʱ���룬����0��
	 * @param naviid
	 *            ʹ���Ƽ���·����ID��ʹ�����Ƽ���·����ͬһ�ε������棬����ʱ���룬����0��
	 * @param islimit
	 *            ��ҵ�����������Ƿ����滮[0-����(Ĭ��ֵ)��1-ʹ��](���������Ƽ���·��ʱ������)
	 * @param routePlanStr
	 *            6���ӿ�ƴ�Ӳ���
	 * @return
	 * @return CldDeliCorpRoutePlanResult
	 * @author Zhouls
	 * @date 2016-5-11 ����10:09:50
	 */
	public CldDeliCorpRoutePlanResult requestCorpRoutePlan(int isroute,
			String corpid, String taskid, int routeid, int naviid, int islimit,
			String routePlanStr) {
		if (!CldDalKDelivery.getInstance().isLoginAuth())
			return null;
		return CldBllKDelivery.getInstance().requestCorpRoutePlan(isroute,
				corpid, taskid, routeid, naviid, islimit, routePlanStr);
	}

	/**
	 * ����ˢ��δ�����˵�����
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-13 ����11:55:49
	 */
	public void requestTaskUnreadCount(ICldResultListener listener) {
		CldBllKDelivery.getInstance().requestUnreadTaskCount(listener);
	}

	/**
	 * ���복�Ӻ�ˢ����ҵ�ŵ�Ȩ���б�
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-6-1 ����11:41:42
	 */
	public void requestAuthStoreList(ICldResultListener listener) {
		CldBllKDelivery.getInstance().requestAuthStoreList(listener);
	}

	public static interface ICarListener {

		/**
		 * ��ȡ˾����ʻ������Ϣ�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onGetResult(int errCode, MtqCar result);
	}

	/**
	 * ��ȡ˾����ʻ������Ϣ
	 * 
	 * @param taskId
	 *            ��������ID
	 * @param corpId
	 *            ������˾ID
	 * @param listener
	 * @author zhaoqy
	 */
	public void getCarInfo(final String taskId, final String corpId,
			final ICarListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				if (listener != null) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				CldBllKDelivery.getInstance().getCarInfo(taskId, corpId,
						listener);
			}
		});
	}

	public static interface ITaskListener {

		/**
		 * �г������б�ص�
		 * 
		 * @param errCode
		 * @param listOfResult
		 */
		public void onGetResult(int errCode, List<MtqTask> listOfResult);
	}

	/**
	 * 
	 * ��ȡ�г������б�
	 * 
	 * @param starttime
	 *            ������ʼʱ�䣨ʱ�����
	 * @param endtime
	 *            ��������ʱ��
	 * @param listener
	 * @author zhaoqy
	 */
	public void getTasks(final String starttime, final String endtime,
			final ITaskListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				if (listener != null) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				CldBllKDelivery.getInstance().getTasks(starttime, endtime,
						listener);
			}
		});
	}

	public static interface ICarRouteListener {

		/**
		 * �����г��б�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onGetResult(int errCode, List<MtqCarRoute> listOfResult);
	}

	/**
	 * ��ȡ�����г��б�
	 * 
	 * @param date
	 *            �������ڣ���ʽ��YYYY-MM-DD��
	 * @param taskId
	 *            ��������ID
	 * @param corpId
	 *            ������˾ID
	 * @param listener
	 * @author zhaoqy
	 */
	public void getCarRoutes(final String date, final List<MtqTask> tasks,
			final ICarRouteListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				if (listener != null) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				CldBllKDelivery.getInstance().getCarRoutes(date, tasks,
						listener);
			}
		});
	}

	public static interface ITaskDetailListener {

		/**
		 * �����г��б�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onGetResult(int errCode, MtqTaskDetail result);
	}

	/**
	 * ��ȡ�г�����
	 * 
	 * @param carduid
	 *            �����豸ID
	 * @param serialid
	 *            �г̼�¼ID
	 * @param listener
	 * @author zhaoqy
	 */
	public void getTaskDetail(final String carduid, final String serialid,
			final ITaskDetailListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				if (listener != null) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				CldBllKDelivery.getInstance().getTaskDetail(carduid, serialid,
						listener);
			}
		});
	}

	public static interface IDeviceCarListener {
		/**
		 * ��ȡ�豸�ĳ�����Ϣ�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onGetResult(int errCode, MtqDeviceCar result);
	}

	/**
	 * ��ȡ�豸�ĳ�����Ϣ
	 * 
	 * @param timestamp
	 * @param listener
	 * @author zhaoqy
	 * @data 2017-5-25
	 */
	public void getDeviceCarinfo(final String timestamp,
			final IDeviceCarListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				if (listener != null) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				CldBllKDelivery.getInstance().getDeviceCarinfo(timestamp,
						listener);
			}
		});
	}

	public static interface IUpdateDeviceCarListener {
		/**
		 * �����г��б�ص�
		 * 
		 * @param errCode
		 * @param result
		 */
		public void onGetResult(int errCode, String systime);
	}

	/**
	 * �����豸�ĳ�����Ϣ
	 * 
	 * @param carlicense ���ƺ�
	 * @param brand Ʒ��
	 * @param carmodel ��������
	 * @param cartype �����ͺţ�1-΢�� 2-���� 3-���� 4-���ͣ�
	 * @param carlong �����ף�
	 * @param carwidth ���ף�
	 * @param carheight �ߣ��ף�
	 * @param caraxle �������ᣩ
	 * @param carweight ���أ��֣�
	 * @param carvin ���ܺţ���6λ��
	 * @param carengine �������ţ���6λ��
	 * @param listener
	 * @author zhaoqy
	 * @data 2017-5-25
	 */
	public void updateDeviceCarinfo(final String carlicense,
			final String brand, final String carmodel, final int cartype,
			final float carlong, final float carwidth, final float carheight,
			final int caraxle, final float carweight, final String carvin,
			final String carengine, final IUpdateDeviceCarListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void failedDeal(int errCode) {
				if (listener != null) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				CldBllKDelivery.getInstance().updateDeviceCarinfo(carlicense,
						brand, carmodel, cartype, carlong, carwidth, carheight,
						caraxle, carweight, carvin, carengine, listener);
			}
		});
	}
	
	/**
	 * �Ƿ��¼��Ȩ
	 * @return
	 * @author zhaoqy
	 * @data 2017-5-26
	 */
	public boolean isLoginAuth() {
		return CldDalKDelivery.getInstance().isLoginAuth();
	}
	
	/**
	 * ��ȡ����ǩ��key
	 * @return
	 * @author zhaoqy
	 * @data 2017-5-25
	 */
	public String getCldDeliveryKey() {
		return CldDalKDelivery.getInstance().getCldDeliveryKey();
	}
	
	/**
	 * ��ȡ��������ͷ
	 * @return
	 * @author zhaoqy
	 * @data 2017-5-25
	 */
	public String getCldDeliveryUrlHead() {
		return CldSapUtil.getNaviSvrHY();
	}
	
	/**
	 * ��ȡ��������map
	 * 
	 * @return Map<String,Object>
	 * @author zhaoqy
	 * @data 2017-5-25
	 */
	public Map<String, Object> getCldDeliveryPubMap() {
		return CldSapKDelivery.getPubMap();
	}
	

	/**
	 * 
	 * ��ҵ���лص�
	 * 
	 * @author Zhouls
	 * @date 2016-4-28 ����3:00:11
	 */
	public static interface ICldDeliGetCorpLimitListener {
		/**
		 * ��ȡ��ҵ���лص�
		 * 
		 * @param errCode
		 * @param lstOfLimit
		 *            �����б�
		 * @param limitCount
		 *            ��������
		 * @param lstOfWarning
		 *            ��ʾ�б�
		 * @param warningCount
		 *            ��ʾ����
		 * @return void
		 * @author Zhouls
		 * @date 2016-4-28 ����3:00:31
		 */
		public void onGetCorpLimitResult(int errCode,
				List<CldDeliCorpLimit> lstOfLimit, int limitCount,
				List<CldDeliCorpWarning> lstOfWarning, int warningCount);
	}

	/**
	 * 
	 * ��ȡ�˻�������ص�
	 * 
	 * @author Zhouls
	 * @date 2016-4-27 ����6:49:31
	 */
	public static interface ICldDeliGetTaskDetailListener {
		public void onGetTaskDetailResult(int errCode,
				MtqDeliTaskDetail taskInfo);
	}

	/**
	 * 
	 * ��ȡ�˻����б�ص�
	 * 
	 * @author Zhouls
	 * @date 2016-4-27 ����5:18:28
	 */
	public static interface ICldDeliGetTaskListListener {
		public void onGetTaskLstResult(int errCode, List<MtqDeliTask> lstOfTask);
	}

	/**
	 * 
	 * ��ȡ���˵���ʷ�б�ص�
	 * 
	 * @author Zhouls
	 * @date 2016-4-29 ����9:48:21
	 */
	public static interface ICldDeliGetTaskHistoryListListener {
		public void onGetTaskLstResult(int errCode,
				List<MtqDeliTask> lstOfTask, int page, int pagecount, int total);
	}

	/**
	 * 
	 * �����ŵ�ص�
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����5:10:15
	 */
	public interface ICldDeliSearchStoreListener {
		/**
		 * �����ŵ�ص�
		 * 
		 * @param errCode
		 * @param result
		 * @return void
		 * @author Zhouls
		 * @date 2016-4-22 ����5:09:36
		 */
		public void onGetResult(int errCode, CldDeliSearchStoreResult result);
	}

	/**
	 * 
	 * ���˵�״̬�ص�
	 * 
	 * @author Zhouls
	 * @date 2016-4-27 ����6:49:46
	 */
	public static interface ICldDeliTaskStatusListener {
		public void onUpdateTaskStatus(int errCode, String corpid,
				String taskid, int status);
	}

	/**
	 * 
	 * �˻���״̬�ص�
	 * 
	 * @author Zhouls
	 * @date 2016-4-27 ����6:50:20
	 */
	public static interface ICldDeliTaskStoreStatusListener {
		public void onUpdateTaskStoreStatus(int errCode, String corpid,
				String taskid, String storeid, int status, String waybill,
				String ewaybill, MtqDeliReceiveData data);
	}

	/**
	 * 
	 * ��ȡ��Ȩ��Ϣ�ص�
	 * 
	 * @author Zhouls
	 * @date 2015-12-9 ����10:29:53
	 */
	public static interface ICldDeliveryMonitorListener {
		/**
		 * ��ȡ��Ȩ�б�ص�
		 * 
		 * @param errCode
		 *            �����루errCode=0�ɹ�,errCode=CldOlsErrCode.
		 *            ACCOUT_NOT_LOGINδ��¼���¼̬��ʧЧ�����û�ȥ��¼��
		 * @param lstOfResult
		 *            �״λ�ȡ�ɹ��ص�
		 * @return void
		 * @author Zhouls
		 * @date 2015-12-11 ����11:05:21
		 */
		public void onGetResult(int errCode, List<CldMonitorAuth> lstOfResult);
	}

	/**
	 * @annotation :��ȡ��ҵȨ����Ϣ
	 * @author : yuyh
	 * @date :2016-9-22����10:36:22
	 * @parama :
	 * @return :
	 **/
	public static interface ICldAuthInfoListener {
		/**
		 * ��ȡ��Ȩ�б�ص�
		 * 
		 * @param errCode
		 *            �����루errCode=0�ɹ�,errCode=CldOlsErrCode.
		 *            ACCOUT_NOT_LOGINδ��¼���¼̬��ʧЧ�����û�ȥ��¼��
		 * @param lstOfResult
		 *            �״λ�ȡ�ɹ��ص�
		 * @return void
		 * @author Zhouls
		 * @date 2015-12-11 ����11:05:21
		 */
		public void onGetResult(int errCode, List<AuthInfoList> lstOfResult);
	}

	/**
	 * ȷ����Ȩ�ɹ�
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-10 ����6:17:00
	 */
	public static void dealAfterAuth(final ICldDealAuthListener listener) {
		if (!CldKAccountAPI.getInstance().isLogined()) {
			CldDalKDelivery.getInstance().setLoginAuth(false);
			if (null != listener) {
				listener.failedDeal(CldOlsErrCode.ACCOUT_NOT_LOGIN);
			}
		} else {
			if (CldDalKDelivery.getInstance().isLoginAuth()) {
				if (null != listener) {
					listener.toDeal();
				}
			} else {
				// CldBllKDelivery.getInstance().init(authListener);
				CldBllKDelivery.getInstance().loginAuth(
						new ICldResultListener() {

							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								if (errCode == 0) {
									if (null != listener) {
										listener.toDeal();
									}
									List<CldDeliGroup> myGroups = CldDalKDelivery
											.getInstance().getLstOfMyGroups();
									if (null != myGroups && myGroups.size() > 0) {
									}
								} else {
									if (null != listener) {
										listener.failedDeal(errCode);
									}
								}
							}
						});
			}
		}
	}

	/**
	 * �ͻ������У��ڿ�ʼ�ͻ�ʱ���ϴ�������������Ϣ(�ı��˻���״̬ʵ��)��ͬʱ���ն�������λ�÷����仯ʱ��������ڽ��붫ݸʱ�ٴ��ϴ�һ���ն˵�λ����Ϣ��
	 * 
	 * @param corpId
	 *            �˻���������ҵID
	 * @param taskId
	 *            �˻���ID
	 * @param storeId
	 *            �˻�����ǰ�˻���ID
	 * @param regionCode
	 *            ��ǰλ������ID
	 * @param regionName
	 *            ��ǰλ����������
	 * @param x
	 *            ��ǰλ��x
	 * @param y
	 *            ��ǰλ��y
	 * @param cellId
	 *            ��·ͼ��ID
	 * @param uid
	 *            ��·UID
	 * @param waybill
	 *            ���͵���
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-5 ����6:04:33
	 */
	public void uploadPostion(String corpId, String taskId, String storeId,
			int regionCode, String regionName, long x, long y, int cellId,
			int uid, String waybill, ICldResultListener listener) {
		CldBllKDelivery.getInstance().uploadPostion(corpId, taskId, storeId,
				regionCode, regionName, x, y, cellId, uid, waybill, listener);
	}

	/**
	 * �ϱ���·״̬
	 * 
	 * @param corpid
	 *            ��ҵID
	 * @param routeid
	 *            �Ƽ���·ID
	 * @param naviid
	 *            ʹ���Ƽ���·����ID
	 * @param status
	 *            0�����Ƽ���·���� 1ʹ���Ƽ���·���� 6��������(����Ŀ�ĵ�)7��������(û��Ŀ�ĵ�) ����ֻ�� (0 1 6 7)
	 * @param x
	 *            �ϱ�λ��X
	 * @param y
	 *            �ϱ�λ��y
	 * @param cell
	 *            ��·ͼ��ID
	 * @param uid
	 *            ��·UID
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-6 ����11:21:41
	 */
	public void uploadRoutePlanStatus(final String corpid, final int routeid,
			final int naviid, final int status, final long x, final long y,
			final int cell, final int uid, final ICldResultListener listener) {

		dealAfterAuth(new ICldDealAuthListener() {
			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().uploadRoutePlanStatus(corpid,
						routeid, naviid, status, x, y, cell, uid, listener);
			}
		});
	}

	/**
	 * �ӵ�ǰ����ĳ����б���ȥId��Ӧ����ҵ����
	 * 
	 * @param corpId
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2016-5-18 ����5:26:04
	 */
	public String getCorpNameById(String corpId) {
		if (TextUtils.isEmpty(corpId)) {
			return "";
		}
		List<CldDeliGroup> myGroups = CldDalKDelivery.getInstance()
				.getLstOfMyGroups();
		if (null != myGroups && myGroups.size() > 0) {
			for (int i = 0; i < myGroups.size(); i++) {
				if (null != myGroups.get(i)) {
					if (corpId.equals(myGroups.get(i).corpId)) {
						return myGroups.get(i).corpName;
					}
				}
			}
		}
		return "";
	}

	/**
	 * �߳���ҵ�����
	 * 
	 * @param corpId
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-20 ����4:31:46
	 */
	public void afterKickOutCorp(String corpId) {
		if (TextUtils.isEmpty(corpId)) {
			return;
		}
		// �Ƴ��������ҵ
		List<CldDeliGroup> lstOfMGroups = CldDalKDelivery.getInstance()
				.getLstOfMyGroups();
		if (null != lstOfMGroups && lstOfMGroups.size() > 0) {
			for (int i = 0; i < lstOfMGroups.size(); i++) {
				if (null == lstOfMGroups.get(i)) {
					continue;
				}
				if (corpId.equals(lstOfMGroups.get(i).corpId)) {
					// �ҵ����Ƴ�����ҵ �Ƴ�
					lstOfMGroups.remove(i);
				}
			}
		}
		// �Ƴ���Ȩ�б������ҵ
		List<CldDeliGroup> lstOfAuthGroups = CldDalKDelivery.getInstance()
				.getLstOfAuthCorps();
		if (null != lstOfAuthGroups && lstOfAuthGroups.size() > 0) {
			for (int i = 0; i < lstOfAuthGroups.size(); i++) {
				if (null == lstOfAuthGroups.get(i)) {
					continue;
				}
				if (corpId.equals(lstOfAuthGroups.get(i).corpId)) {
					// �ҵ����Ƴ�����ҵ �Ƴ�
					lstOfAuthGroups.remove(i);
				}
			}
		}
	}

	/**
	 * �յ����˻�����Ϣ�󱣴�
	 * 
	 * @param lstOfNewTask
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-26 ����5:39:06
	 */
	public void saveNewTaskStatus(List<MtqDeliTask> lstOfNewTask) {
		if (null != lstOfNewTask && lstOfNewTask.size() > 0) {
			List<CldDeliTaskDB> lstOfDb = new ArrayList<CldDeliTaskDB>();
			for (int i = 0; i < lstOfNewTask.size(); i++) {
				MtqDeliTask temptask = lstOfNewTask.get(i);
				if (null == temptask) {
					continue;
				}
				long id = 0;
				try {
					long corp = Long.parseLong(temptask.corpid);
					long task = Long.parseLong(temptask.taskid);
					id = (corp << 32) + task;
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				if (id == 0) {
					continue;
				}
				CldDeliTaskDB temp = new CldDeliTaskDB();
				temp.id = id;
				temp.kuid = CldKServiceAPI.getInstance().getKuid();
				temp.status = temptask.readstatus;
				lstOfDb.add(temp);
			}
			if (lstOfDb.size() > 0) {
				CldDbUtils.saveAll(lstOfDb);
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
	 * @date 2016-5-23 ����6:02:40
	 */
	public int getUnFinishTaskCount(int status) {
		return CldBllKDelivery.getInstance().getUnFinishTaskCount(status);
	}

	/**
	 * 
	 * ��ȡ��ҵ����Χ���ص�
	 * 
	 * @author Zhouls
	 * @date 2016-5-24 ����12:02:44
	 */
	public static interface ICldDeliGetElectfenceListener {
		/**
		 * ��ȡ��ҵ����Χ���ص�
		 * 
		 * @param errCode
		 * @param lstOfElectfence
		 * @return void
		 * @author Zhouls
		 * @date 2016-5-24 ����12:03:04
		 */
		public void onGetResult(int errCode, List<CldElectfence> lstOfElectfence);
	}

	/**
	 * �������Χ��
	 * 
	 * @param corpid
	 *            ��ǰ�����˻��˻�������ҵid
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-24 ����2:31:47
	 */
	public void requestElectfence(final String corpid,
			final ICldDeliGetElectfenceListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {
			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().requestElectfence(corpid,
						listener);
			}
		});
	}

	/**
	 * @annotation : ��ȡ������ҵ��Χ��
	 * @author : yuyh
	 * @date :2016-11-28����2:39:57
	 * @parama :
	 * @return :
	 **/
	public void requestAllElectfence(
			final ICldDeliGetElectfenceListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().requestAllElectfence(listener);
			}

			@Override
			public void failedDeal(int errcode) {
				// TODO Auto-generated method stub
				listener.onGetResult(errcode, null);
			}
		});
	}

	/**
	 * ��������Χ������
	 * 
	 * @param parm
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-24 ����2:34:46
	 */
	public void uploadElectfence(final CldUploadEFParm parm,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {
			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().uploadElectfence(parm, listener);
			}
		});
	}

	/**
	 * ��������Χ������
	 * 
	 * @param parm
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-24 ����2:34:46
	 */
	public void reUploadElectfence(final CldReUploadEFParm parm,
			final ICldResultListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {
			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance()
						.reUploadElectfence(parm, listener);

			}
		});
	}

	/**
	 * @annotation : ��ȡ�ŵ�Ȩ���б�
	 * @author : yuyh
	 * @date :2016-9-22����10:24:51
	 * @parama :
	 * @return :
	 **/
	public void getAuthInfoList(final ICldAuthInfoListener listener) {
		dealAfterAuth(new ICldDealAuthListener() {
			@Override
			public void failedDeal(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onGetResult(errCode, null);
				}
			}

			@Override
			public void toDeal() {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getAuthInfoList(listener);
			}
		});
	}

	/**
	 * �˳���¼������û�����
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-20 ����4:37:08
	 */
	public void onLoginOut() {
		CldDalKDelivery.getInstance().uninitData();
	}

	public static CldKDeliveryAPI getInstance() {
		if (null == instance) {
			instance = new CldKDeliveryAPI();
		}
		return instance;
	}

	private CldKDeliveryAPI() {

	}

	private static CldKDeliveryAPI instance;

	/**
	 * 
	 * ��Ȩ����
	 * 
	 * @author Zhouls
	 * @date 2015-12-10 ����6:14:08
	 */
	public interface ICldDealAuthListener {
		/**
		 * ��Ȩʧ�ܴ���
		 * 
		 * @return void
		 * @author Zhouls
		 * @date 2015-12-10 ����6:16:17
		 */
		public void failedDeal(int errcode);

		/**
		 * �Ѽ�Ȩ���߼�Ȩ�ɹ�����
		 * 
		 * @return void
		 * @author Zhouls
		 * @date 2015-12-10 ����6:16:01
		 */
		public void toDeal();
	}
}
