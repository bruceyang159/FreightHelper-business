/*
 * @Title CldBllKDeliverty.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-12-9 ����11:25:27
 * @version 1.0
 */
package com.mtq.ols.module.delivery;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.util.Log;

import com.cld.device.CldPhoneNet;
import com.cld.gson.Gson;
import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;
import com.cld.net.CldResponse.ICldResponse;
import com.cld.net.volley.VolleyError;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICarListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICarRouteListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldAuthInfoListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetCorpLimitListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetElectfenceListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetTaskDetailListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetTaskHistoryListListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetTaskListListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliInitListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliSearchStoreListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliTaskStatusListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliTaskStoreStatusListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliveryMonitorListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.IDeviceCarListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ITaskDetailListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ITaskListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.IUpdateDeviceCarListener;
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
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliUploadStoreParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldElectfence;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldMonitorAuth;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldReUploadEFParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldUploadEFParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqDeviceCar;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqTask;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.MtqTaskDetail;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtAuthInfoLst;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtCar;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtCarRoute;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtDeviceCar;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetAuthStoreList;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetCorpLimitData;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetElectfence;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetTaskHistoryList;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetTaskInfo;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetTaskList;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetUnFinishTaskCount;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtGetUnReadTaskCount;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtJoinGroup;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtLoginAuth;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtMonitorAuth;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtMonitorAuthLst;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtMstBussData;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtSearchNearStores;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtSearchNpStores;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtSearchStores;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtTask;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtTaskDetail;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtUpdateDeviceCar;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtUpdateTask;
import com.mtq.ols.module.delivery.CldSapKDeliveryParse.ProtUpdateTaskStore;
import com.mtq.ols.module.delivery.bean.MtqDeliTask;
import com.mtq.ols.module.delivery.tool.CldKBaseParse.ProtBase;
import com.mtq.ols.module.delivery.tool.CldKDeviceAPI;
import com.mtq.ols.module.delivery.tool.CldOlsErrManager.CldOlsErrCode;
import com.mtq.ols.module.delivery.tool.CldSapParser;
import com.mtq.ols.tools.err.CldOlsErrManager;
import com.mtq.ols.tools.model.CldOlsInterface.ICldResultListener;
import com.mtq.ols.tools.parse.CldKReturn;

/**
 * ����ҵ���߼���
 * 
 * @author Zhouls
 * @date 2015-12-9 ����11:25:27
 */
public class CldBllKDelivery {
	private String TAG = "ols_delivery";

	/**
	 * ���˳�ʼ�����ڵ�¼�����˳���¼���ã�
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-10 ����6:36:29
	 */
	public void init(final ICldDeliInitListener listener) {
		if (!CldKAccountAPI.getInstance().isLogined()) {
			CldDalKDelivery.getInstance().setLoginAuth(false);
		}
		CldBllKDelivery.getInstance().loginAuth(new ICldResultListener() {
			@Override
			public void onGetResult(int errCode) {
				// TODO Auto-generated method stub
				if (null != listener) {
					listener.onLoginAuth(errCode);
				}
				List<CldDeliGroup> myGroups = CldDalKDelivery.getInstance()
						.getLstOfMyGroups();
				if (errCode == 0 && null != myGroups && myGroups.size() > 0) {
					CldLog.d(TAG, "hy auth success!");
					// ��ȡ����ҵȨ���ŵ��б�
				} else {
					if (errCode != 0) {
						CldLog.d(TAG, "hy auth failed!");
					} else {
						CldLog.d(TAG, "has not join corp!");
					}
				}
			}
		});
	}

	/**
	 * ��¼��Ȩ
	 * 
	 * @param id
	 *            ��ʶid
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:28:03
	 */
	public void loginAuth(final ICldResultListener listener) {
		// ���ú����֮ǰ���ݲ������
		CldDalKDelivery.getInstance().initData();
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			CldLog.e(TAG, " !CldPhoneNet.isNetConnected() ");
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}

		if (!CldKAccountAPI.getInstance().isLogined()) {
			CldLog.e(TAG, " !CldKAccountAPI.getInstance().isLogined() ");
			// δ��¼���ص�¼������
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.ACCOUT_NOT_LOGIN;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		
		
		CldLog.e(TAG, " CldSapKDelivery.loginAuth() ");
		final CldKReturn request = CldSapKDelivery.loginAuth();
		CldHttpClient.get(request.url, ProtLoginAuth.class,
				new ICldResponse<ProtLoginAuth>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtLoginAuth response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtLoginAuth.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_loginAuth");
						CldLog.d(TAG, errRes.errMsg + "_loginAuth");
						handleErr(request, errRes);
						if (errRes.errCode == 0 && null != response
								&& null != response.data) {
							if (!TextUtils.isEmpty(response.syskey)) {
								CldDalKDelivery.getInstance()
										.setCldDeliveryKey(response.syskey);
								CldDalKDelivery.getInstance().setExpiry_time(
										response.data.expiry_time);
								CldDalKDelivery.getInstance().setLockcorpid(
										response.data.lockcorpid);
								CldDalKDelivery.getInstance().setState(
										response.data.state);
								if (null != response.data.groups
										&& response.data.groups.size() > 0) {
									List<CldDeliGroup> lstOfMyGroups = new ArrayList<CldDeliGroup>();
									response.data.protParse(lstOfMyGroups);
									CldDalKDelivery.getInstance()
											.setLstOfMyGroups(lstOfMyGroups);
								}
								CldDalKDelivery.getInstance()
										.setLoginAuth(true);
								CldKDeliveryAPI.getInstance().getAuthInfoList(
										new ICldAuthInfoListener() {

											@Override
											public void onGetResult(
													int errCode,
													List<AuthInfoList> lstOfResult) {
												// TODO Auto-generated method
												CldDalKDelivery.getInstance()
														.setLstOfAuthInfo(
																lstOfResult);
											}
										});
							} else {
								CldLog.e(TAG, "login auth key is empty!");
							}
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
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
	public void addMonitorAuth(final String mobile, final String remark,
			CldAuthTimeOut timeOut, final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		// ����ʱ��
		final long oTime = CldKDeviceAPI.getSvrTime()
				+ CldAuthTimeOut.valueOf(timeOut);
		final CldKReturn request = CldSapKDelivery.addMonitorAuth(mobile,
				remark, oTime);
		CldHttpClient.get(request.url, ProtMonitorAuth.class,
				new ICldResponse<ProtMonitorAuth>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtMonitorAuth response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtMonitorAuth.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_addMonitorAuth");
						CldLog.d(TAG, errRes.errMsg + "_addMonitorAuth");
						handleErr(request, errRes);
						if (errRes.errCode == 0 && null != response) {
							CldMonitorAuth bean = new CldMonitorAuth();
							bean.timeOut = (oTime);
							bean.mobile = (mobile);
							bean.mark = (remark);
							response.protParase(bean);
							CldDalKDelivery.getInstance().addToMonitorLst(bean);
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
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
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.delMonitorAuth(id);
		CldHttpClient.get(request.url, ProtBase.class,
				new ICldResponse<ProtBase>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_delMonitorAuth");
						CldLog.d(TAG, errRes.errMsg + "_delMonitorAuth");
						handleErr(request, errRes);
						if (errRes.errCode == 0 && null != response) {
							CldDalKDelivery.getInstance().delMonitor(id);
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
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
			CldAuthTimeOut timeOut, final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		// ����ʱ��
		final long oTime = CldKDeviceAPI.getSvrTime()
				+ CldAuthTimeOut.valueOf(timeOut);
		final CldKReturn request = CldSapKDelivery.reviseMonitorAuthTimeOut(id,
				oTime);
		CldHttpClient.get(request.url, ProtBase.class,
				new ICldResponse<ProtBase>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_revMonitorAuth");
						CldLog.d(TAG, errRes.errMsg + "_revMonitorAuth");
						handleErr(request, errRes);
						if (errRes.errCode == 0 && null != response) {
							CldDalKDelivery.getInstance().reviseMonitor(id,
									null, oTime);
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
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
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.reviseMonitorAuthMark(id,
				mark);
		CldHttpClient.get(request.url, ProtBase.class,
				new ICldResponse<ProtBase>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_revMonitorAuth");
						CldLog.d(TAG, errRes.errMsg + "_revMonitorAuth");
						handleErr(request, errRes);
						if (errRes.errCode == 0 && null != response) {
							CldDalKDelivery.getInstance().reviseMonitor(id,
									mark, -1);
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ��ȡ��Ȩ��Ϣ�б�
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-9 ����11:29:48
	 */
	public void getMonitorAuthList(final ICldDeliveryMonitorListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.getMonitorAuthList();
		CldHttpClient.get(request.url, ProtMonitorAuthLst.class,
				new ICldResponse<ProtMonitorAuthLst>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtMonitorAuthLst response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtMonitorAuthLst.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getMonitorAuthList");
						CldLog.d(TAG, errRes.errMsg + "_getMonitorAuthList");
						handleErr(request, errRes);
						if (errRes.errCode == 0 && null != response) {
							List<CldMonitorAuth> lstOfBean = new ArrayList<CldMonitorAuth>();
							response.protParase(lstOfBean);
							CldDalKDelivery.getInstance().reloadMonitorLst(
									lstOfBean);
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode,
									CldDalKDelivery.getInstance()
											.getMonitorAuthLst());
						}
					}
				});
	}

	/**
	 * �ϴ��տ���Ϣ
	 * 
	 * @param param
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����4:21:48
	 */
	public void uploadReceipt(CldDeliReceiptParm param,
			final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (null == param) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.uploadReceipt(param.corpid,
				param.taskid, param.storeid, param.pay_method,
				param.real_amount, param.return_desc, param.return_amount,
				param.pay_remark, param.waybill, param.uploadPng,
				param.e_receipts_0_ext);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_uploadReceipt");
						CldLog.d(TAG, errRes.errMsg + "_uploadReceipt");
						handleErr(request, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
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
	 *            �����뾶
	 * @param page
	 *            ҳ��
	 * @param pageSize
	 *            ҳ����
	 * @param listener
	 *            �ص�
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-22 ����4:46:54
	 */
	public void searchNearStores(String corpid, int centerX, int centerY,
			int range, int page, int pageSize,
			final ICldDeliSearchStoreListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid) || centerX < 0 || centerY < 0
				|| range < 0) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID, null);
			}
			return;
		}
		if (page < 0) {
			page = 1;
		}
		if (pageSize < 0) {
			pageSize = 20;
		}
		final CldKReturn request = CldSapKDelivery.searchNearStores(corpid,
				centerX, centerY, range, page, pageSize);
		CldHttpClient.get(request.url, ProtSearchNearStores.class,
				new ICldResponse<ProtSearchNearStores>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtSearchNearStores response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtSearchNearStores.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_searchNearStores");
						CldLog.d(TAG, errRes.errMsg + "_searchNearStores");
						handleErr(request, errRes);
						CldDeliSearchStoreResult result = null;
						if (null != response) {
							result = response.protParse();
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode, result);
						}
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
	 * @date 2016-4-22 ����5:49:14
	 */
	public void searchStores(String corpid, String keyword, int type, int page,
			int pageSize, final ICldDeliSearchStoreListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid) || type < 0) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID, null);
			}
			return;
		}
		if (page < 0) {
			page = 1;
		}
		if (pageSize < 0) {
			pageSize = 20;
		}
		final CldKReturn request = CldSapKDelivery.searchStores(corpid,
				keyword, type, page, pageSize);
		CldHttpClient.get(request.url, ProtSearchStores.class,
				new ICldResponse<ProtSearchStores>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtSearchStores response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtSearchStores.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_searchStores");
						CldLog.d(TAG, errRes.errMsg + "_searchStores");
						handleErr(request, errRes);
						CldDeliSearchStoreResult result = null;
						if (null != response) {
							result = response.protParse();
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode, result);
						}
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
	 * @date 2016-4-22 ����5:52:00
	 */
	public void searchNoPosStores(String corpid, String regioncity, int page,
			int pageSize, final ICldDeliSearchStoreListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid)) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID, null);
			}
			return;
		}
		if (page < 0) {
			page = 1;
		}
		if (pageSize < 0) {
			pageSize = 20;
		}
		final CldKReturn request = CldSapKDelivery.searchNoPosStores(corpid,
				regioncity, page, pageSize);
		CldHttpClient.get(request.url, ProtSearchNpStores.class,
				new ICldResponse<ProtSearchNpStores>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtSearchNpStores response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtSearchNpStores.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_searchNoPosStores");
						CldLog.d(TAG, errRes.errMsg + "_searchNoPosStores");
						handleErr(request, errRes);
						CldDeliSearchStoreResult result = null;
						if (null != response) {
							result = response.protParse();
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode, result);
						}
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
	 * @date 2016-4-22 ����6:08:31
	 */
	public void uploadStore(CldDeliUploadStoreParm param,
			final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (null == param) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.uploadStore(param.corpid,
				param.settype, param.storeid, param.storename, param.address,
				param.linkman, param.phone, param.storekcode, param.remark,
				param.uploadPng, param.iscenter);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_uploadStore");
						CldLog.d(TAG, errRes.errMsg + "_uploadStore");
						handleErr(request, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ���복��
	 * 
	 * @param inviteCode
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-27 ����3:10:15
	 */
	public void joinGroup(String inviteCode, final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (TextUtils.isEmpty(inviteCode)) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.joinGroup(inviteCode);
		CldHttpClient.get(request.url, ProtJoinGroup.class,
				new ICldResponse<ProtJoinGroup>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtJoinGroup response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtJoinGroup.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_joinGroup");
						CldLog.d(TAG, errRes.errMsg + "_joinGroup");
						handleErr(request, errRes);
						if (null != response && errRes.errCode == 0) {
							List<CldDeliGroup> lstOfMyGroups = new ArrayList<CldDeliGroup>();
							response.protParse(lstOfMyGroups);
							CldDalKDelivery.getInstance().setLstOfMyGroups(
									lstOfMyGroups);
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * �ܾ����복��
	 * 
	 * @param inviteCode
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-27 ����3:15:57
	 */
	public void unJoinGroup(String inviteCode, final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (TextUtils.isEmpty(inviteCode)) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.unJoinGroup(inviteCode);
		CldHttpClient.get(request.url, ProtJoinGroup.class,
				new ICldResponse<ProtJoinGroup>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtJoinGroup response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtJoinGroup.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_unJoinGroup");
						CldLog.d(TAG, errRes.errMsg + "_unJoinGroup");
						handleErr(request, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ��ȡδ��ɻ��˵��б�
	 * 
	 * @param corpid
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-27 ����5:22:34
	 */
	public void getDeliTaskList(String corpid,
			final ICldDeliGetTaskListListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetTaskLstResult(errRes.errCode, null);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.getDeliTaskList(corpid);

		CldHttpClient.get(request.url, ProtGetTaskList.class,
				new ICldResponse<ProtGetTaskList>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetTaskLstResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetTaskList response) {
						// TODO Auto-generated method stub

						Log.e("mymymy", new Gson().toJson(response.tasks)
								.toString());

						CldSapParser.parseObject(response,
								ProtGetTaskList.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getDeliTaskList");
						CldLog.d(TAG, errRes.errMsg + "_getDeliTaskList");
						handleErr(request, errRes);
						if (null != response && response.errcode == 0) {
							if (null != response.tasks
									&& response.tasks.size() > 0) {
								// �����Ķ�״̬���Ա�
								// CldDalKDelivery.getInstance()
								// .syncTaskReadStatus(response.tasks);
								if (null != listener) {
									listener.onGetTaskLstResult(errRes.errCode,
											response.tasks);
								}
							} else {
								if (null != listener) {
									listener.onGetTaskLstResult(errRes.errCode,
											new ArrayList<MtqDeliTask>());
								}
							}
						} else {
							if (null != listener) {
								listener.onGetTaskLstResult(errRes.errCode,
										null);
							}
						}
					}
				});
	}

	/**
	 * ��ȡ�˻�����ʷ
	 * 
	 * @param status
	 * @param corpid
	 * @param page
	 * @param pagesize
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-27 ����5:31:36
	 */
	public void getDeliTaskHistoryList(String status, String corpid, int page,
			int pagesize, final ICldDeliGetTaskHistoryListListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetTaskLstResult(errRes.errCode, null, -1, -1, -1);
			}
			return;
		}
		if (TextUtils.isEmpty(status)) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetTaskLstResult(CldOlsErrCode.PARAM_INVALID, null,
						-1, -1, -1);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.getDeliTaskHistoryList(
				status, corpid, page, pagesize);
		CldHttpClient.get(request.url, ProtGetTaskHistoryList.class,
				new ICldResponse<ProtGetTaskHistoryList>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetTaskLstResult(errRes.errCode, null,
									-1, -1, -1);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetTaskHistoryList response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetTaskHistoryList.class, errRes);
						CldLog.d(TAG, errRes.errCode
								+ "_getDeliTaskHistoryList");
						CldLog.d(TAG, errRes.errMsg + "_getDeliTaskHistoryList");
						handleErr(request, errRes);
						if (null != response && response.errcode == 0) {
							if (null != response.tasks
									&& response.tasks.size() > 0) {
								if (null != listener) {
									listener.onGetTaskLstResult(errRes.errCode,
											response.tasks, response.page,
											response.pagecount, response.total);
								}
							} else {
								if (null != listener) {
									listener.onGetTaskLstResult(errRes.errCode,
											new ArrayList<MtqDeliTask>(),
											response.page, response.pagecount,
											response.total);
								}
							}
						} else {
							if (null != listener) {
								listener.onGetTaskLstResult(errRes.errCode,
										null, -1, -1, -1);
							}
						}
					}
				});
	}

	/**
	 * ��ȡ�˻�������
	 * 
	 * @param corpid
	 * @param taskid
	 * @param page
	 *            �˻���ҳ��
	 * @param pagesize
	 *            �˻���ҳ����
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-27 ����5:56:55
	 */
	public void getDeliTaskDetail(String corpid, String taskid, int page,
			int pagesize, final ICldDeliGetTaskDetailListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetTaskDetailResult(errRes.errCode, null);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid) || TextUtils.isEmpty(taskid)) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetTaskDetailResult(CldOlsErrCode.PARAM_INVALID,
						null);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.getDeliTaskDetail(corpid,
				taskid, page, pagesize);
		CldHttpClient.get(request.url, ProtGetTaskInfo.class,
				new ICldResponse<ProtGetTaskInfo>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetTaskDetailResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetTaskInfo response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetTaskInfo.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_TaskDetail");
						CldLog.d(TAG, errRes.errMsg + "_TaskDetail");
						handleErr(request, errRes);
						if (null != response && response.errcode == 0) {
							if (null != response.taskinfo) {
								if (null != listener) {
									listener.onGetTaskDetailResult(
											errRes.errCode, response.taskinfo);
								}
							} else {
								if (null != listener) {
									listener.onGetTaskDetailResult(
											CldOlsErrCode.PARSE_ERR, null);
								}
							}
						} else {
							if (null != listener) {
								listener.onGetTaskDetailResult(errRes.errCode,
										null);
							}
						}
					}
				});
	}

	/**
	 * ���»��˵�״̬
	 * 
	 * @param corpid
	 * @param taskid
	 * @param status
	 * @param ecorpid
	 *            ��Ҫ��ͣ���˻���������ҵID
	 * @param etaskid
	 *            ��Ҫ��ͣ���˻���ID
	 * @param x
	 * @param y
	 * @param cell
	 * @param uid
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-14 ����4:39:35
	 */
	public void updateDeliTaskStatus(String corpid, String taskid, int status,
			String ecorpid, String etaskid, long x, long y, int cell, int uid,
			final ICldDeliTaskStatusListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onUpdateTaskStatus(errRes.errCode, null, null, -1);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid) || TextUtils.isEmpty(taskid)) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onUpdateTaskStatus(CldOlsErrCode.PARAM_INVALID, null,
						null, -1);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.updateDeliTaskStatus(corpid,
				taskid, status, ecorpid, etaskid, x, y, cell, uid);
		CldHttpClient.get(request.url, ProtUpdateTask.class,
				new ICldResponse<ProtUpdateTask>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onUpdateTaskStatus(errRes.errCode, null,
									null, -1);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtUpdateTask response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtUpdateTask.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_updateDeliTaskStatus");
						CldLog.d(TAG, errRes.errMsg + "_updateDeliTaskStatus");
						handleErr(request, errRes);
						if (null != response && response.errcode == 0) {
							if (null != listener) {
								listener.onUpdateTaskStatus(errRes.errCode,
										response.corpid, response.taskid,
										response.status);
							}
						} else {
							if (null != listener) {
								listener.onUpdateTaskStatus(errRes.errCode,
										null, null, -1);
							}
						}
					}
				});
	}

	/**
	 * �����˻���״̬
	 * 
	 * @param corpid
	 * @param taskid
	 * @param storeid
	 * @param status
	 * @param x
	 * @param y
	 * @param cell
	 * @param uid
	 * @param waybill
	 * @param ewaybill
	 *            ��Ҫ��ͣ���˻���
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-10 ����11:14:41
	 */
	public void updateDeliTaskStoreStatus(String corpid, String taskid,
			String storeid, int status, long x, long y, int cell, int uid,
			String waybill, String ewaybill, int taskStatus,
			final ICldDeliTaskStoreStatusListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onUpdateTaskStoreStatus(errRes.errCode, null, null,
						null, -1, null, null, null);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid) || TextUtils.isEmpty(taskid)) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onUpdateTaskStoreStatus(CldOlsErrCode.PARAM_INVALID,
						null, null, null, -1, null, null, null);
			}
			return;
		}

		final CldKReturn request = CldSapKDelivery.updateDeliTaskStoreStatus(
				corpid, taskid, storeid, status, x, y, cell, uid, waybill,
				ewaybill, taskStatus);
		CldHttpClient.get(request.url, ProtUpdateTaskStore.class,
				new ICldResponse<ProtUpdateTaskStore>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onUpdateTaskStoreStatus(errRes.errCode,
									null, null, null, -1, null, null, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtUpdateTaskStore response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtUpdateTaskStore.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_updateDeliStoreStatus");
						CldLog.d(TAG, errRes.errMsg + "_updateDeliStroeStatus");
						handleErr(request, errRes);
						if (null != response && response.errcode == 0) {
							if (null != listener) {
								listener.onUpdateTaskStoreStatus(
										errRes.errCode, response.corpid,
										response.taskid, response.storeid,
										response.status, response.waybill,
										response.ewaybill, response.data);
							}
						} else {
							if (null != listener) {
								listener.onUpdateTaskStoreStatus(
										errRes.errCode, null, null, null, -1,
										null, null, null);
							}
						}
					}
				});
	}

	/**
	 * ��ȡͼ����ҵ��������
	 * 
	 * @param parm
	 *            ͼ�����в���
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-14 ����4:33:59
	 */
	public void getMapCorpLimitData(CldDeliCorpLimitMapParm parm,
			final ICldDeliGetCorpLimitListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				listener.onGetCorpLimitResult(CldOlsErrCode.NET_NO_CONNECTED,
						null, -1, null, -1);
			}
			return;
		}
		if (null == parm) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetCorpLimitResult(CldOlsErrCode.PARAM_INVALID,
						null, -1, null, -1);
			}
			return;
		}
		// corpid��0 ȡ������ҵ��������
		final CldKReturn request = CldSapKDelivery.getCorpLimitData("0",
				parm.req, "", parm.minX, parm.minY, parm.maxX, parm.maxY,
				parm.tht, parm.twh, parm.twt, parm.tad, parm.tvt);
		CldHttpClient.get(request.url, ProtGetCorpLimitData.class,
				new ICldResponse<ProtGetCorpLimitData>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetCorpLimitResult(errRes.errCode, null,
									-1, null, -1);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetCorpLimitData response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetCorpLimitData.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getCorpLimitData");
						CldLog.d(TAG, errRes.errMsg + "_getCorpLimitData");
						handleErr(request, errRes);
						if (null != response && errRes.errCode == 0) {
							List<CldDeliCorpLimit> lstOfLimit = new ArrayList<CldDeliCorpLimit>();
							List<CldDeliCorpWarning> lstOfWarning = new ArrayList<CldDeliCorpWarning>();
							response.protParse(lstOfLimit, lstOfWarning);
							if (null != listener) {
								listener.onGetCorpLimitResult(errRes.errCode,
										lstOfLimit, response.truckcount,
										lstOfWarning, response.warningcount);
							}
						} else {
							if (null != listener) {
								listener.onGetCorpLimitResult(errRes.errCode,
										null, -1, null, -1);
							}
						}
					}
				});
	}

	/**
	 * ��ȡ��;��ҵ��������
	 * 
	 * @param corpid
	 * @param datatype
	 * @param uid
	 * @param req
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-4-28 ����2:59:59
	 */
	public void getRouteCorpLimitData(CldDeliCorpLimitRouteParm parm,
			final ICldDeliGetCorpLimitListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				listener.onGetCorpLimitResult(CldOlsErrCode.NET_NO_CONNECTED,
						null, -1, null, -1);
			}
			return;
		}
		if (null == parm) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetCorpLimitResult(CldOlsErrCode.PARAM_INVALID,
						null, -1, null, -1);
			}
			return;
		}
		// corpid��0 ȡ������ҵ��������
		final CldKReturn request = CldSapKDelivery.getCorpLimitData(
				parm.curTaskCorpId, parm.req, parm.uid, -1, -1, -1, -1,
				parm.tht, parm.twh, parm.twt, parm.tad, parm.tvt);
		CldHttpClient.get(request.url, ProtGetCorpLimitData.class,
				new ICldResponse<ProtGetCorpLimitData>() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						/** ͨ���쳣���� */
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetCorpLimitResult(errRes.errCode, null,
									-1, null, -1);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetCorpLimitData response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetCorpLimitData.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getCorpLimitData");
						CldLog.d(TAG, errRes.errMsg + "_getCorpLimitData");
						handleErr(request, errRes);
						if (null != response && errRes.errCode == 0) {
							List<CldDeliCorpLimit> lstOfLimit = new ArrayList<CldDeliCorpLimit>();
							List<CldDeliCorpWarning> lstOfWarning = new ArrayList<CldDeliCorpWarning>();
							response.protParse(lstOfLimit, lstOfWarning);
							if (null != listener) {
								listener.onGetCorpLimitResult(errRes.errCode,
										lstOfLimit, response.truckcount,
										lstOfWarning, response.warningcount);
							}
						} else {
							if (null != listener) {
								listener.onGetCorpLimitResult(errRes.errCode,
										null, -1, null, -1);
							}
						}
					}
				});
	}

	/**
	 * ��ȡ��ҵ�ƶ���·(ͬ�����ⲿ���̴߳���)
	 * 
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
	 * @date 2016-5-11 ����10:07:57
	 */
	public CldDeliCorpRoutePlanResult requestCorpRoutePlan(int isroute,
			String corpid, String taskid, int routeid, int naviid, int islimit,
			String routePlanStr) {
		CldDeliCorpRoutePlanResult result = new CldDeliCorpRoutePlanResult();
		if (!CldPhoneNet.isNetConnected()) {
			result.errCode = CldOlsErrCode.NET_NO_CONNECTED;
			return result;
		}
		if (TextUtils.isEmpty(routePlanStr)) {
			// �������Ϸ��ص�
			result.errCode = CldOlsErrCode.PARAM_INVALID;
			return result;
		}
		final CldKReturn request = CldSapKDelivery.getCorpRoutePlan(isroute,
				corpid, taskid, routeid, naviid, islimit, routePlanStr);
		byte[] data = CldHttpClient.getBytes(request.url);
		if (null != data && data.length > 0) {
			CldLog.e("ols", "return data not null!");
			ProtMstBussData bussData = CldSapKDeliveryParse
					.covertCorpRouteData(data);
			if (null != bussData && null != bussData.routeInfo
					&& null != bussData.rnsData) {
				result.errCode = 0;
				result.routeInfo = bussData.routeInfo.routeinfo;
				result.rpData = bussData.rnsData;
				result.isRoute = bussData.routeInfo.existroute;
				result.corpId = bussData.routeInfo.corpid;
				return result;
			} else {
				result.errCode = CldOlsErrCode.DATA_RETURN_ERR;
				return result;
			}
		} else {
			CldLog.e("ols", "return data null!");
			result.errCode = CldOlsErrCode.DATA_RETURN_ERR;
			return result;
		}
	}

	/**
	 * �ͻ������У��ڿ�ʼ�ͻ�ʱ���ϴ�������������Ϣ(�ı��˻���״̬ʵ��)��ͬʱ���ն�������λ�÷����仯ʱ��������ڽ��붫ݸʱ�ٴ��ϴ�һ���ն˵�λ����Ϣ��
	 * 
	 * @param corpid
	 * @param taskid
	 * @param storeid
	 * @param regioncode
	 * @param regionname
	 * @param x
	 * @param y
	 * @param cell
	 * @param uid
	 * @param waybill
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-5 ����6:03:36
	 */
	public void uploadPostion(String corpid, String taskid, String storeid,
			int regioncode, String regionname, long x, long y, int cell,
			int uid, String waybill, final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid) || TextUtils.isEmpty(taskid)
				|| TextUtils.isEmpty(storeid) || TextUtils.isEmpty(regionname)
				|| TextUtils.isEmpty(waybill) || x < 0 || y < 0 || cell < 0
				|| uid < 0) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.uploadPostion(corpid,
				taskid, storeid, regioncode, regionname, x, y, cell, uid,
				waybill);
		CldHttpClient.get(request.url, ProtBase.class,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_uploadPostion");
						CldLog.d(TAG, errRes.errMsg + "_uploadPostion");
						handleErr(request, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * �ϱ�·��״̬
	 * 
	 * @param corpid
	 * @param routeid
	 * @param naviid
	 * @param status
	 * @param x
	 * @param y
	 * @param cell
	 * @param uid
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-6 ����11:20:35
	 */
	public void uploadRoutePlanStatus(String corpid, int routeid, int naviid,
			int status, long x, long y, int cell, int uid,
			final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (TextUtils.isEmpty(corpid) || x < 0 || y < 0 || cell < 0 || uid < 0) {
			// �������Ϸ��ص�
			if (null != listener) {
				listener.onGetResult(CldOlsErrCode.PARAM_INVALID);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.uploadRoutePlanStatus(
				corpid, routeid, naviid, status, x, y, cell, uid);
		CldHttpClient.get(request.url, ProtBase.class,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_uploadRoutePlanStatus");
						CldLog.d(TAG, errRes.errMsg + "_uploadRoutePlanStatus");
						handleErr(request, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ����δ���˻�������
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-7 ����4:41:20
	 */
	public void requestUnreadTaskCount(final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.requestUnreadTaskCount();
		CldHttpClient.get(request.url, ProtGetUnReadTaskCount.class,
				new ICldResponse<ProtGetUnReadTaskCount>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetUnReadTaskCount response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetUnReadTaskCount.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getUnreadTaskCount");
						CldLog.d(TAG, errRes.errMsg + "_getUnreadTaskCount");
						handleErr(request, errRes);
						if (null != response) {
							if (response.count >= 0) {
								CldDalKDelivery.getInstance()
										.setUnReadTaskCount(response.count);
							}
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ����δ���������
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-23 ����12:19:55
	 */
	public void requestUnfinishTaskCount(final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.requestUnfinishTaskCount();
		CldHttpClient.get(request.url, ProtGetUnFinishTaskCount.class,
				new ICldResponse<ProtGetUnFinishTaskCount>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetUnFinishTaskCount response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetUnFinishTaskCount.class, errRes);
						CldLog.d(TAG, errRes.errCode
								+ "_requestUnfinishTaskCount");
						CldLog.d(TAG, errRes.errMsg
								+ "_requestUnfinishTaskCount");
						handleErr(request, errRes);
						if (null != response && response.errcode == 0
								&& null != response.counts) {
							if (response.counts.size() >= 0) {
								CldDalKDelivery.getInstance()
										.saveUnFinishCountMap(response.counts);
							}
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ������Ȩ��ҵ�б�
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-7 ����4:41:36
	 */
	public void requestAuthStoreList(final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.requestAuthStoreList();
		CldHttpClient.get(request.url, ProtGetAuthStoreList.class,
				new ICldResponse<ProtGetAuthStoreList>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetAuthStoreList response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetAuthStoreList.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getAuthStoreList");
						CldLog.d(TAG, errRes.errMsg + "_getAuthStoreList");
						handleErr(request, errRes);
						if (null != response) {
							List<CldDeliGroup> lstOfTemp = new ArrayList<CldDeliGroup>();
							response.protParse(lstOfTemp);
							CldDalKDelivery.getInstance().setLstOfAuthCorps(
									lstOfTemp);
						}
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ��ȡδ����˻�������
	 * 
	 * @param status
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2016-5-23 ����6:01:15
	 */
	public int getUnFinishTaskCount(int status) {
		return CldDalKDelivery.getInstance().getUnFinishCountByStatus(status);
	}

	/**
	 * �������Χ��
	 * 
	 * @param corpid
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-24 ����12:12:36
	 */
	public void requestElectfence(String corpid,
			final ICldDeliGetElectfenceListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.requestElectfence(corpid);
		CldHttpClient.get(request.url, ProtGetElectfence.class,
				new ICldResponse<ProtGetElectfence>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetElectfence response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetElectfence.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_requestElectfence");
						CldLog.d(TAG, errRes.errMsg + "_requestElectfence");
						handleErr(request, errRes);
						if (null != response && errRes.errCode == 0) {
							List<CldElectfence> lstOfEF = new ArrayList<CldElectfence>();
							response.protParse(lstOfEF);
							if (null != listener) {
								listener.onGetResult(errRes.errCode, lstOfEF);
							}
						} else {
							if (null != listener) {
								listener.onGetResult(CldOlsErrCode.PARSE_ERR,
										null);
							}
						}
					}
				});
	}

	/**
	 * @annotation :��ȡ������ҵ�ĵ���Χ��
	 * @author : yuyh
	 * @date :2016-11-28����2:44:50
	 * @parama :
	 * @return :
	 **/
	public void requestAllElectfence(
			final ICldDeliGetElectfenceListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.requestAllElectfence();
		CldHttpClient.get(request.url, ProtGetElectfence.class,
				new ICldResponse<ProtGetElectfence>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtGetElectfence response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtGetElectfence.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_requestElectfence");
						CldLog.d(TAG, errRes.errMsg + "_requestElectfence");
						handleErr(request, errRes);
						if (null != response && errRes.errCode == 0) {
							List<CldElectfence> lstOfEF = new ArrayList<CldElectfence>();
							response.protParse(lstOfEF);
							if (null != listener) {
								listener.onGetResult(errRes.errCode, lstOfEF);
							}
						} else {
							if (null != listener) {
								listener.onGetResult(CldOlsErrCode.PARSE_ERR,
										null);
							}
						}
					}
				});

	}

	/**
	 * �ϱ�����Χ��
	 * 
	 * @param corpid
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-24 ����2:24:22
	 */
	public void uploadElectfence(CldUploadEFParm parm,
			final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (null == parm || null == parm.lstOfLauchEF
				|| parm.lstOfLauchEF.size() <= 0) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.PARAM_INVALID;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.uploadElectfenceStatus(
				parm.corpid, parm.x, parm.y, parm.lstOfLauchEF);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_uploadElectfence");
						CldLog.d(TAG, errRes.errMsg + "_uploadElectfence");
						handleErr(request, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * ��������Χ��
	 * 
	 * @param parm
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-30 ����9:28:11
	 */
	public void reUploadElectfence(CldReUploadEFParm parm,
			final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		if (null == parm || null == parm.lstOfLauchEF
				|| parm.lstOfLauchEF.size() <= 0) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.PARAM_INVALID;
				listener.onGetResult(errRes.errCode);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery
				.reUploadElectfenceStatus(parm.lstOfLauchEF);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_uploadElectfence");
						CldLog.d(TAG, errRes.errMsg + "_uploadElectfence");
						handleErr(request, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
	}

	/**
	 * @annotation :��ȡ�ŵ�Ȩ���б�
	 * @author : yuyh
	 * @date :2016-9-22����10:27:27
	 * @parama :
	 * @return :
	 **/
	public void getAuthInfoList(final ICldAuthInfoListener listener) {

		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (null != listener) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}
		final CldKReturn request = CldSapKDelivery.getAuthInfoList();
		CldHttpClient.get(request.url, ProtAuthInfoLst.class,
				new ICldResponse<ProtAuthInfoLst>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(error, errRes);
						if (null != listener) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtAuthInfoLst response) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(response,
								ProtAuthInfoLst.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getAuthInfoList");
						CldLog.d(TAG, errRes.errMsg + "_getAuthInfoList");
						handleErr(request, errRes);
						if (null != response && errRes.errCode == 0) {
							List<AuthInfoList> lstOfEF = new ArrayList<AuthInfoList>();
							response.protParase(lstOfEF);
							if (null != listener) {
								listener.onGetResult(errRes.errCode, lstOfEF);
							}
						} else {
							if (null != listener) {
								listener.onGetResult(CldOlsErrCode.PARSE_ERR,
										null);
							}
						}
					}
				});

	}

	/**
	 * �ϴ�������Ƭ���ߵ��ӻص�ͼƬ
	 * 
	 * @author ligangfan
	 * @date 2017-3-27
	 * 
	 * @param corpId
	 *            ��ҵId
	 * @param taskId
	 *            ��������Id
	 * @param wayBill
	 *            ���͵���
	 * @param cust_order_id
	 *            �ͻ�����
	 * @param upTime
	 *            �ϴ�ʱ�䡾UTCʱ�䡿
	 * @param pic_type
	 *            ��Ƭ���ͣ�1��������Ƭ��Ĭ�ϡ���2�����ӻص���Ƭ��
	 * @param pic_time
	 *            ����ʱ�䡾UTCʱ�䡿
	 * @param pic_x
	 *            ����ʱ����������x
	 * @param pic_y
	 *            ����ʱ����������y
	 * @param base64_pic
	 *            ����ͼƬ��base64�ַ���
	 * @param listener
	 *            �ص�����
	 */
	public void uploadDeliPicture(String corpId, String taskId, String wayBill,
			String cust_order_id, long upTime, int pic_type, long pic_time,
			int pic_x, int pic_y, String base64_pic,
			final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
		}

		final CldKReturn request = CldSapKDelivery.uploadDeliPicture(corpId,
				taskId, wayBill, cust_order_id, pic_type, pic_time, pic_x,
				pic_y, base64_pic);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("Uploadpic",
								"request_ErrorResponse" + arg0.getMessage());
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub
						Log.e("Uploadpic", "request_GetReqKey" + arg0);
					}

					@Override
					public void onResponse(ProtBase arg0) {
						// TODO Auto-generated method stub

						CldSapParser.parseObject(arg0, ProtBase.class, errRes);

						Log.e("Uploadpic", "request_Responser" + arg0.errcode);
						handleErr(request, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode);
						}
					}

				});

	}

	/**
	 * �ϴ�����ɨ���¼
	 * 
	 * @author ligangfan
	 * @date 2017-3-27
	 * 
	 * @param corpId
	 *            ��ҵId
	 * @param taskId
	 *            ��������id
	 * @param wayBill
	 *            ���͵���
	 * @param cust_order_id
	 *            �ͻ�����
	 * @param bar_code
	 *            ����������
	 * @param upTime
	 *            ɨ��ʱ�䡾UTCʱ�䡿
	 * @param scan_x
	 *            ɨ��λ��x
	 * @param scan_y
	 *            ɨ��λ��y
	 * @param listener
	 *            �ص�����
	 */
	public void uploadGoodScanRecord(String corpId, String taskId,
			String wayBill, String cust_order_id, String bar_code, long upTime,
			int scan_x, int scan_y, final ICldResultListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode);
			}
		}
		final CldKReturn request = CldSapKDelivery.uploadGoodScanRecord(corpId,
				taskId, wayBill, cust_order_id, bar_code, upTime, scan_x,
				scan_y);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(ProtBase arg0) {
						// TODO Auto-generated method stub
						CldSapParser.parseObject(arg0, ProtBase.class, errRes);
						handleErr(request, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode);
						}
					}
				});
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
	public void getCarInfo(String taskId, String corpId,
			final ICarListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
		}

		final CldKReturn request = CldSapKDelivery.getCarInfo(taskId, corpId);
		CldLog.d(TAG, "request.url: " + request.url);
		CldHttpClient.get(request.url, ProtCar.class,
				new ICldResponse<ProtCar>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtCar response) {
						CldSapParser.parseObject(response, ProtCar.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getCarInfo");
						CldLog.d(TAG, errRes.errMsg + "_getCarInfo");
						handleErr(request, errRes);
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onGetResult(errRes.errCode,
										response.car);
							}
						} else {
							if (listener != null) {
								listener.onGetResult(errRes.errCode, null);
							}
						}
					}
				});
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
	public void getTasks(String starttime, String endtime,
			final ITaskListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
			return;
		}

		final CldKReturn request = CldSapKDelivery.getTasks(starttime, endtime);
		CldLog.e("zhaoqy", "request.url: " + request.url);
		CldHttpClient.get(request.url, ProtTask.class, false,
				new ICldResponse<ProtTask>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtTask response) {

						CldSapParser.parseObject(response, ProtTask.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getTasks");
						CldLog.d(TAG, errRes.errMsg + "_getTasks");
						handleErr(request, errRes);
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onGetResult(errRes.errCode,
										response.tasks);
							}
						} else {
							if (listener != null) {
								listener.onGetResult(errRes.errCode, null);
							}
						}
					}
				});
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
	public void getCarRoutes(String date, List<MtqTask> tasks,
			final ICarRouteListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
		}
		final CldKReturn request = CldSapKDelivery.getCarRoutes(date, tasks);
		CldLog.e("zhaoqy", "request.url: " + request.url);
		CldHttpClient.get(request.url, ProtCarRoute.class, false,
				new ICldResponse<ProtCarRoute>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtCarRoute response) {
						CldSapParser.parseObject(response, ProtCarRoute.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getCarRoutes");
						CldLog.d(TAG, errRes.errMsg + "_getCarRoutes");
						handleErr(request, errRes);
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onGetResult(errRes.errCode,
										response.cars);
							}
						} else {
							if (listener != null) {
								listener.onGetResult(CldOlsErrCode.PARSE_ERR,
										null);
							}
						}
					}
				});
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
	public void getTaskDetail(String carduid, String serialid,
			final ITaskDetailListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
		}
		final CldKReturn request = CldSapKDelivery.getTaskDetail(carduid,
				serialid);
		CldLog.e("zhaoqy", "request.url: " + request.url);
		CldHttpClient.get(request.url, ProtTaskDetail.class, false,
				new ICldResponse<ProtTaskDetail>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtTaskDetail response) {
						CldSapParser.parseObject(response,
								ProtTaskDetail.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getTravelDetail");
						CldLog.d(TAG, errRes.errMsg + "_getTravelDetail");
						handleErr(request, errRes);
						if (response != null && errRes.errCode == 0) {
							MtqTaskDetail result = new MtqTaskDetail();
							result.navi = response.navi;
							result.tracks = response.tracks;
							if (listener != null) {
								listener.onGetResult(errRes.errCode, result);
							}
						} else {
							if (listener != null) {
								listener.onGetResult(errRes.errCode, null);
							}
						}
					}
				});
	}

	/**
	 * ��ȡ�豸�ĳ�����Ϣ
	 * 
	 * @param timestamp
	 * @param listener
	 * @author zhaoqy
	 * @data 2017-5-25
	 */
	public void getDeviceCarinfo(String timestamp,
			final IDeviceCarListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, null);
			}
		}
		final CldKReturn request = CldSapKDelivery.getDeviceCarinfo(timestamp);
		CldLog.e("zhaoqy", "request.url: " + request.url);
		CldHttpClient.get(request.url, ProtDeviceCar.class, false,
				new ICldResponse<ProtDeviceCar>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode, null);
						}
					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtDeviceCar response) {
						CldSapParser.parseObject(response, ProtDeviceCar.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getDeviceCarinfo");
						CldLog.d(TAG, errRes.errMsg + "_getDeviceCarinfo");
						handleErr(request, errRes);
						if (response != null && errRes.errCode == 0) {
							MtqDeviceCar result = new MtqDeviceCar();
							result = response.data;
							if (listener != null) {
								listener.onGetResult(errRes.errCode, result);
							}
						} else {
							if (listener != null) {
								listener.onGetResult(errRes.errCode, null);
							}
						}
					}
				});
	}

	/**
	 * �����豸�ĳ�����Ϣ
	 * 
	 * @param carlicense
	 *            ���ƺ�
	 * @param brand
	 *            Ʒ��
	 * @param carmodel
	 *            ��������
	 * @param cartype
	 *            �����ͺţ�1-΢�� 2-���� 3-���� 4-���ͣ�
	 * @param carlong
	 *            �����ף�
	 * @param carwidth
	 *            ���ף�
	 * @param carheight
	 *            �ߣ��ף�
	 * @param caraxle
	 *            �������ᣩ
	 * @param carweight
	 *            ���أ��֣�
	 * @param carvin
	 *            ���ܺţ���6λ��
	 * @param carengine
	 *            �������ţ���6λ��
	 * @param listener
	 * @author zhaoqy
	 * @data 2017-5-25
	 */
	public void updateDeviceCarinfo(String carlicense, String brand,
			String carmodel, int cartype, float carlong, float carwidth,
			float carheight, int caraxle, float carweight, String carvin,
			String carengine, final IUpdateDeviceCarListener listener) {
		final CldKReturn errRes = new CldKReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onGetResult(errRes.errCode, "");
			}
		}
		final CldKReturn request = CldSapKDelivery.updateDeviceCarinfo(
				carlicense, brand, carmodel, cartype, carlong, carwidth,
				carheight, caraxle, carweight, carvin, carengine);
		CldLog.e("zhaoqy", "request.url: " + request.url);
		CldHttpClient.get(request.url, ProtUpdateDeviceCar.class, false,
				new ICldResponse<ProtUpdateDeviceCar>() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						CldOlsErrManager.handlerException(arg0, errRes);
						if (listener != null) {
							listener.onGetResult(errRes.errCode, "");
						}
					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtUpdateDeviceCar response) {
						CldSapParser.parseObject(response,
								ProtUpdateDeviceCar.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_updateDeviceCarinfo");
						CldLog.d(TAG, errRes.errMsg + "_updateDeviceCarinfo");
						handleErr(request, errRes);
						if (response != null && errRes.errCode == 0) {
							String systime = "";
							systime = response.systime;
							if (listener != null) {
								listener.onGetResult(errRes.errCode, systime);
							}
						} else {
							if (listener != null) {
								listener.onGetResult(errRes.errCode, "");
							}
						}
					}
				});
	}

	/**
	 * ������ͨ�ô���
	 * 
	 * @param request
	 * @param errRes
	 * @return void
	 * @author Zhouls
	 * @date 2016-5-20 ����2:21:20
	 */
	private void handleErr(CldKReturn request, CldKReturn errRes) {
		CldOlsErrManager.handleErr(request, errRes);
		if (null != request) {
			if (null != errRes) {
				errRes.errCode = fixErrCode(errRes.errCode);
			} else {
				request.errCode = fixErrCode(request.errCode);
			}
		}
	}

	/**
	 * ���˴���������
	 * 
	 * @param errCode
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2016-5-20 ����3:31:35
	 */
	private int fixErrCode(int errCode) {
		int res = errCode;
		switch (errCode) {
		case 1001:
			// ������ȱ��
		case 1002:
			// �������ݸ�ʽ���Ϸ�
		case 1003:
			// ��ҵID������
		case 1005:
			// Signǩ��ʧ��
		case 1006:
			// ��¼ҵ����벻֧��
		case 1061:
			// �����û�������
		case 1072:
			// �鳵��Ȩ������
		case 1099:
			// �����û���Ȩ�޲���
		case 1102:
			// �˻���������
			// ����������ͨ��Ϊ��������
			res = CldOlsErrCode.PARAM_INVALID;
			break;
		case 1007:
			// �ʺ�δ��¼
			res = CldOlsErrCode.ACCOUT_NOT_LOGIN;
			if (CldKAccountAPI.getInstance().isLogined()) {
				CldLog.e("ols", "session invalid!Deli!");
				CldKAccountAPI.getInstance().sessionInvalid(501, 0);
			}
			break;
		}
		return res;
	}

	private static CldBllKDelivery instance;

	public static CldBllKDelivery getInstance() {
		if (null == instance) {
			instance = new CldBllKDelivery();
		}
		return instance;
	}

	private CldBllKDelivery() {

	}
}
