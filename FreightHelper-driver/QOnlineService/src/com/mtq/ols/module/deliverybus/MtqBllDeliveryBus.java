/**
 * ������ҵ��-ҵ���߼���
 * 
 * @author zhaoqy
 * @date 2017-06-16
 */

package com.mtq.ols.module.deliverybus;

import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;
import com.cld.net.CldResponse.ICldResponse;
import com.cld.net.volley.VolleyError;
import com.mtq.ols.module.delivery.tool.CldKBaseParse.ProtBase;
import com.mtq.ols.module.delivery.tool.CldOlsErrManager.CldOlsErrCode;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqAlarmMsgListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqCarDataDetailListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqCarDataListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqCarSendListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqCarStateCountListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqCarStateDetailListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqCarStateListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqDeviceDTypeListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqDriverDataDetailListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqDriverDataListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqFeedbackListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqHistoryAlarmMsgListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqHistorySysMsgListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqInviteDriverListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqLoginListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqLogoutListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqMsgAlarmReadListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqMsgSysReadListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqOrderCountListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqSysMsgListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqTaskCountListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqTaskNaviListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqTaskStoreListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqTrackHistoryListener;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqUploadAttachPicListener;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtCarData;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtCarDataDetail;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtCarState;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtCarStateCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtCarStateList;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtDeviceDType;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtDriverData;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtDriverDetail;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtInviteDriver;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtLogin;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtMsgAlarm;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtMsgSys;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtOrderCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtTaskCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtTaskNavi;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtTaskStore;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtTrackHistory;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParse.ProtUploadAttachPic;
import com.mtq.ols.module.deliverybus.parse.MtqSapParser;
import com.mtq.ols.module.deliverybus.parse.MtqSapReturn;

public class MtqBllDeliveryBus {

	public String TAG = "bus_delivery";

	private static MtqBllDeliveryBus instance;

	public static MtqBllDeliveryBus getInstance() {
		if (null == instance) {
			instance = new MtqBllDeliveryBus();
		}
		return instance;
	}

	private MtqBllDeliveryBus() {

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
	 * @date 2017-06-15
	 */
	public void login(final long duid, final int apptype, final String version,
			final String username, final String password,
			final IMtqLoginListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.login(duid, apptype,
				version, username, password);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtLogin.class,
				false, new ICldResponse<ProtLogin>() {

					@Override
					public void onErrorResponse(VolleyError error) {
						CldLog.d(TAG, "onErrorResponse: " + error.getMessage());
					}

					@Override
					public void onGetReqKey(String arg0) {
						CldLog.d(TAG, "onGetReqKey: " + arg0);
					}

					@Override
					public void onResponse(ProtLogin response) {
						MtqSapParser.parseObject(response, ProtLogin.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_login");
						CldLog.d(TAG, errRes.errMsg + "_login");
						if (response != null && errRes.errCode == 0) {
							MtqDalDeliveryBus.getInstance()
									.setLoginStatus(true);
							MtqDalDeliveryBus.getInstance().setDuid(duid);
							MtqDalDeliveryBus.getInstance().setAppType(apptype);
							MtqDalDeliveryBus.getInstance().setVersion(version);
							MtqDalDeliveryBus.getInstance().setUserName(
									username);
							MtqDalDeliveryBus.getInstance().setPassword(
									password);

							if (response.login != null) {
								MtqDalDeliveryBus.getInstance().setMtqLogin(
										response.login);
							}

							if (response.groups != null
									&& response.groups.size() > 0) {
								MtqDalDeliveryBus.getInstance().setGroups(
										response.groups);
							}

							if (response.actions != null
									&& response.actions.size() > 0) {
								MtqDalDeliveryBus.getInstance().setActions(
										response.actions);
							}

							if (listener != null) {
								listener.onResult(errRes.errCode, errRes.errMsg);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, errRes.errMsg);
							}
						}
					}
				});
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
	public void logout(final IMtqLogoutListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.logout();
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtBase response) {
						MtqSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_logout");
						CldLog.d(TAG, errRes.errMsg + "_logout");
						if (response != null && errRes.errCode == 0) {
							MtqDalDeliveryBus.getInstance().uninit();
							MtqDalDeliveryBus.getInstance().setDuid(0);
							MtqDalDeliveryBus.getInstance().setAppType(0);
							MtqDalDeliveryBus.getInstance().setVersion("");

							MtqDalDeliveryBus.getInstance().setMtqLogin(null);
							MtqDalDeliveryBus.getInstance().setGroups(null);
							MtqDalDeliveryBus.getInstance().setActions(null);
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getCarStateList(int group_id, int carstatus, String keywords,
			int pageindex, int pagesize, final IMtqCarStateListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, 0);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getCarStateList(
				group_id, carstatus, keywords, pageindex, pagesize);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost,
				ProtCarStateList.class, false,
				new ICldResponse<ProtCarStateList>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtCarStateList response) {
						MtqSapParser.parseObject(response,
								ProtCarStateList.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getCarStateList");
						CldLog.d(TAG, errRes.errMsg + "_getCarStateList");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.total);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, 0);
							}
						}
					}
				});
	}

	/**
	 * ����״̬ͳ��
	 * 
	 * @param group_id
	 *            ����ID��0Ϊȫ����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getCarStateCount(int group_id,
			final IMtqCarStateCountListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus
				.getCarStateCount(group_id);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost,
				ProtCarStateCount.class, false,
				new ICldResponse<ProtCarStateCount>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtCarStateCount response) {
						MtqSapParser.parseObject(response,
								ProtCarStateCount.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getCarStateCount");
						CldLog.d(TAG, errRes.errMsg + "_getCarStateCount");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode, response.data);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null);
							}
						}
					}
				});
	}

	/**
	 * ����ʵʱ״̬����
	 * 
	 * @param carid
	 *            ����ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getCarStateDetail(int carid,
			final IMtqCarStateDetailListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getCarStateDetail(carid);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtCarState.class,
				false, new ICldResponse<ProtCarState>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtCarState response) {
						MtqSapParser.parseObject(response, ProtCarState.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getCarStateDetail");
						CldLog.d(TAG, errRes.errMsg + "_getCarStateDetail");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode, response.car,
										response.state);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, null);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getTaskStore(int carid, int pageindex, int pagesize,
			final IMtqTaskStoreListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, 0);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getTaskStore(carid,
				pageindex, pagesize);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtTaskStore.class,
				false, new ICldResponse<ProtTaskStore>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtTaskStore response) {
						MtqSapParser.parseObject(response, ProtTaskStore.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getTaskStore");
						CldLog.d(TAG, errRes.errMsg + "_getTaskStore");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.total);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, 0);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getTaskNavi(int carid, int pageindex, int pagesize,
			final IMtqTaskNaviListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, 0);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getTaskNavi(carid,
				pageindex, pagesize);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtTaskNavi.class,
				false, new ICldResponse<ProtTaskNavi>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtTaskNavi response) {
						MtqSapParser.parseObject(response, ProtTaskNavi.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getTaskNavi");
						CldLog.d(TAG, errRes.errMsg + "_getTaskNavi");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.total);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, 0);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void setCarSend(int time, int smstype, int carid, int driverid,
			long x, long y, String addr, String poiname, String text, 
			int voiceduration, String voicedata, 
			final IMtqCarSendListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.setCarSend(time,
				smstype, carid, driverid, x, y, addr, poiname, text, voiceduration,
				voicedata);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtBase response) {
						MtqSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_setCarSend");
						CldLog.d(TAG, errRes.errMsg + "_setCarSend");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getTrackHistory(int carid, long starttime, long endtime,
			final IMtqTrackHistoryListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, 0);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getTrackHistory(carid,
				starttime, endtime);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost,
				ProtTrackHistory.class, false,
				new ICldResponse<ProtTrackHistory>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtTrackHistory response) {
						MtqSapParser.parseObject(response,
								ProtTrackHistory.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getTrackHistory");
						CldLog.d(TAG, errRes.errMsg + "_getTrackHistory");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.alarmnum);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, 0);
							}
						}
					}
				});
	}

	/**
	 * ������Ϣ�������飩
	 * 
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getAlarmMsg(String incrindex, int pagesize,
			final IMtqAlarmMsgListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, "");
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getAlarmMsg(incrindex,
				pagesize);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtMsgAlarm.class,
				false, new ICldResponse<ProtMsgAlarm>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtMsgAlarm response) {
						MtqSapParser.parseObject(response, ProtMsgAlarm.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getAlarmMsg");
						CldLog.d(TAG, errRes.errMsg + "_getAlarmMsg");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.incrindex);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, "");
							}
						}
					}
				});
	}

	/**
	 * ��ȡ��ʷ������Ϣ�������飩
	 * 
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getHistoryAlarmMsg(long starttime, long endtime, int pageindex,
			int pagesize, int order, final IMtqHistoryAlarmMsgListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, "");
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getHistoryAlarmMsg(
				starttime, endtime, pageindex, pagesize, order);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtMsgAlarm.class,
				false, new ICldResponse<ProtMsgAlarm>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtMsgAlarm response) {
						MtqSapParser.parseObject(response, ProtMsgAlarm.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getHistoryAlarmMsg");
						CldLog.d(TAG, errRes.errMsg + "_getHistoryAlarmMsg");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.incrindex);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, "");
							}
						}
					}
				});
	}

	/**
	 * ���ľ�����Ϣ�Ѷ�״̬
	 * 
	 * @param serialid
	 *            ��¼ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void setMsgAlarmRead(String id,
			final IMtqMsgAlarmReadListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.setMsgAlarmRead(id);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtBase response) {
						MtqSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_setMsgAlarmRead");
						CldLog.d(TAG, errRes.errMsg + "_setMsgAlarmRead");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						}
					}
				});
	}

	/**
	 * ϵͳ��Ϣ�������飩
	 * 
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getSysMsg(String incrindex, int pagesize,
			final IMtqSysMsgListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, "");
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getSysMsg(incrindex,
				pagesize);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtMsgSys.class,
				false, new ICldResponse<ProtMsgSys>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtMsgSys response) {
						MtqSapParser.parseObject(response, ProtMsgSys.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getSysMsg");
						CldLog.d(TAG, errRes.errMsg + "_getSysMsg");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.incrindex);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, "");
							}
						}
					}
				});
	}

	/**
	 * ��ȡ��ʷϵͳ��Ϣ�������飩
	 * 
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getHistorySysMsg(long starttime, long endtime, int pageindex,
			int pagesize, int order, final IMtqHistorySysMsgListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, "");
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getHistorySysMsg(
				starttime, endtime, pageindex, pagesize, order);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtMsgSys.class,
				false, new ICldResponse<ProtMsgSys>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtMsgSys response) {
						MtqSapParser.parseObject(response, ProtMsgSys.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getHistorySysMsg");
						CldLog.d(TAG, errRes.errMsg + "_getHistorySysMsg");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.incrindex);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, "");
							}
						}
					}
				});
	}

	/**
	 * ����ϵͳ��Ϣ�Ѷ�״̬
	 * 
	 * @param serialid
	 *            ��¼ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void setMsgSysRead(String serialid,
			final IMtqMsgSysReadListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.setMsgSysRead(serialid);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtBase response) {
						MtqSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_setMsgSysRead");
						CldLog.d(TAG, errRes.errMsg + "_setMsgSysRead");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getCarDataList(int group_id, String keywords, int pageindex,
			int pagesize, final IMtqCarDataListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, 0);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getCarDataList(group_id,
				keywords, pageindex, pagesize);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtCarData.class,
				false, new ICldResponse<ProtCarData>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtCarData response) {
						MtqSapParser.parseObject(response, ProtCarData.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getCarDataList");
						CldLog.d(TAG, errRes.errMsg + "_getCarDataList");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.total);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, 0);
							}
						}
					}
				});
	}

	/**
	 * ������������
	 * 
	 * @param carid
	 *            ����ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getCarDataDetail(int carid,
			final IMtqCarDataDetailListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getCarDataDetail(carid);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost,
				ProtCarDataDetail.class, false,
				new ICldResponse<ProtCarDataDetail>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtCarDataDetail response) {
						MtqSapParser.parseObject(response,
								ProtCarDataDetail.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getCarDataDetail");
						CldLog.d(TAG, errRes.errMsg + "_getCarDataDetail");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode, response.car,
										response.groups);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, null);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getDriverDataList(int invitestatus, String keywords,
			int pageindex, int pagesize, final IMtqDriverDataListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null, 0);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getDriverDataList(
				invitestatus, keywords, pageindex, pagesize);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtDriverData.class,
				false, new ICldResponse<ProtDriverData>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtDriverData response) {
						MtqSapParser.parseObject(response,
								ProtDriverData.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getDriverDataList");
						CldLog.d(TAG, errRes.errMsg + "_getDriverDataList");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.data, response.total);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null, 0);
							}
						}
					}
				});
	}

	/**
	 * ˾����������
	 * 
	 * @param driverid
	 *            ˾��ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void getDriverDataDetail(int driverid,
			final IMtqDriverDataDetailListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus
				.getDriverDataDetail(driverid);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost,
				ProtDriverDetail.class, false,
				new ICldResponse<ProtDriverDetail>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtDriverDetail response) {
						MtqSapParser.parseObject(response,
								ProtDriverDetail.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getDriverDataDetail");
						CldLog.d(TAG, errRes.errMsg + "_getDriverDataDetail");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode, response.data);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null);
							}
						}
					}
				});
	}

	/**
	 * ����˾�����복��
	 * 
	 * @param driverid
	 *            ˾��ID
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public void inviteDriver(int driverid,
			final IMtqInviteDriverListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, -1);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.inviteDriver(driverid);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost,
				ProtInviteDriver.class, false,
				new ICldResponse<ProtInviteDriver>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtInviteDriver response) {
						MtqSapParser.parseObject(response,
								ProtInviteDriver.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_inviteDriver");
						CldLog.d(TAG, errRes.errMsg + "_inviteDriver");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.invitestatus);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, -1);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getOrderCount(String startdate, String enddate,
			final IMtqOrderCountListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getOrderCount(startdate,
				enddate);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtOrderCount.class,
				false, new ICldResponse<ProtOrderCount>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtOrderCount response) {
						MtqSapParser.parseObject(response,
								ProtOrderCount.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getOrderCount");
						CldLog.d(TAG, errRes.errMsg + "_getOrderCount");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode, response.data);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void getTaskCount(String startdate, String enddate,
			final IMtqTaskCountListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getTaskCount(startdate,
				enddate);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtTaskCount.class,
				false, new ICldResponse<ProtTaskCount>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtTaskCount response) {
						MtqSapParser.parseObject(response, ProtTaskCount.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_getTaskCount");
						CldLog.d(TAG, errRes.errMsg + "_getTaskCount");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode, response.data);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void setFeedback(int fdtype, int dtype, String title,
			String content, String contact, String pics,
			final IMtqFeedbackListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.setFeedback(fdtype,
				dtype, title, content, contact, pics);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtBase.class, false,
				new ICldResponse<ProtBase>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtBase response) {
						MtqSapParser.parseObject(response, ProtBase.class,
								errRes);
						CldLog.d(TAG, errRes.errCode + "_setFeedback");
						CldLog.d(TAG, errRes.errMsg + "_setFeedback");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode);
							}
						}
					}
				});
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
	 * @date 2017-06-15
	 */
	public void uploadAttachPic(int x, int y, String data,
			final IMtqUploadAttachPicListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.uploadAttachPic(x, y,
				data);
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost,
				ProtUploadAttachPic.class, false,
				new ICldResponse<ProtUploadAttachPic>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtUploadAttachPic response) {
						MtqSapParser.parseObject(response,
								ProtUploadAttachPic.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_uploadAttachPic");
						CldLog.d(TAG, errRes.errMsg + "_uploadAttachPic");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode,
										response.mediaid);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null);
							}
						}
					}
				});
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
	public void getDeviceDType(final IMtqDeviceDTypeListener listener) {
		final MtqSapReturn errRes = new MtqSapReturn();
		if (!CldPhoneNet.isNetConnected()) {
			if (listener != null) {
				errRes.errCode = CldOlsErrCode.NET_NO_CONNECTED;
				listener.onResult(errRes.errCode, null);
			}
			return;
		}

		final MtqSapReturn request = MtqSapDeliveryBus.getDeviceDType();
		CldLog.e(TAG, "url: " + request.url);
		CldLog.e(TAG, "mapPost: " + request.mapPost);
		CldHttpClient.post(request.url, request.mapPost, ProtDeviceDType.class,
				false, new ICldResponse<ProtDeviceDType>() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}

					@Override
					public void onGetReqKey(String arg0) {

					}

					@Override
					public void onResponse(ProtDeviceDType response) {
						MtqSapParser.parseObject(response,
								ProtDeviceDType.class, errRes);
						CldLog.d(TAG, errRes.errCode + "_getDeviceDType");
						CldLog.d(TAG, errRes.errMsg + "_getDeviceDType");
						if (response != null && errRes.errCode == 0) {
							if (listener != null) {
								listener.onResult(errRes.errCode, response.data);
							}
						} else {
							if (listener != null) {
								listener.onResult(errRes.errCode, null);
							}
						}
					}
				});
	}
	
	/**
	 * �������Ϸ�����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn errDeal() {
		MtqSapReturn res = new MtqSapReturn();
		res.errCode = 401;
		res.errMsg = "�������Ϸ�";
		return res;
	}
}
