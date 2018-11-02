package com.mtq.apitest.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.cld.base.CldBase;
import com.cld.log.CldLog;
import com.cld.setting.CldSetting;
import com.mtq.ols.R;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.api.CldOlsBase;
import com.mtq.ols.api.CldKAccountAPI.ICldKAccountListener;
import com.mtq.ols.api.CldOlsBase.CldOlsParam;
import com.mtq.ols.api.CldOlsBase.IInitListener;
import com.mtq.ols.module.delivery.CldBllKDelivery;
import com.mtq.ols.module.delivery.CldDalKDelivery;
import com.mtq.ols.module.delivery.CldKDeliveryAPI;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldAuthInfoListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetElectfenceListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetTaskDetailListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetTaskHistoryListListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliGetTaskListListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliInitListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliSearchStoreListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliTaskStatusListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliTaskStoreStatusListener;
import com.mtq.ols.module.delivery.CldKDeliveryAPI.ICldDeliveryMonitorListener;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.AuthInfoList;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliElectFenceUpload;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliGroup;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliSearchStoreResult;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliUploadStoreParm;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldElectfence;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldMonitorAuth;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldUploadEFParm;
import com.mtq.ols.module.delivery.bean.MtqDeliTask;
import com.mtq.ols.module.delivery.bean.MtqDeliTaskDetail;
import com.mtq.ols.module.delivery.db.OrmLiteApi;
import com.mtq.ols.tools.model.CldOlsInterface.ICldResultListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Delivery extends Activity {

//	private final String userName = "15902067097";
//	private final String passwd = "123456";
	private final String userName = "13713963683";
	private final String passwd = "123456";
//	private final String userName = "13751845830";
//	private final String passwd = "123456";
	// private final String userName = "18814271243";
	// private final String passwd = "123456";
	// private final String userName = "13632509191";
	// private final String passwd = "123456";
	private Button bt = null;
	private Button bt2 = null;
	private Button bt3 = null;
	private Button bt4 = null;
	private Button bt5 = null;
	private Button bt6 = null;
	private Button bt7 = null;
	private Button bt8 = null;
	private Button bt9 = null;
	private Button bt10 = null;
	private Button bt11 = null;
	private Button bt12 = null;
	private Button bt13 = null;
	private Button bt14 = null;
	private Button bt15 = null;
	private Button bt16 = null;
	private Button bt17 = null;
	private Button bt18 = null;
	private Button bt19 = null;
	private Button bt20 = null;
	private Button bt21 = null;
	private Button bt22 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_delivery);
		bt = (Button) findViewById(R.id.button_1);
		bt2 = (Button) findViewById(R.id.button_2);
		bt3 = (Button) findViewById(R.id.button_3);
		bt4 = (Button) findViewById(R.id.button_4);
		bt5 = (Button) findViewById(R.id.button_5);
		bt6 = (Button) findViewById(R.id.button_6);
		bt7 = (Button) findViewById(R.id.button_7);
		bt8 = (Button) findViewById(R.id.button_8);
		bt9 = (Button) findViewById(R.id.button_9);
		bt10 = (Button) findViewById(R.id.button_10);
		bt11 = (Button) findViewById(R.id.button_11);
		bt12 = (Button) findViewById(R.id.button_12);
		bt13 = (Button) findViewById(R.id.button_13);
		bt14 = (Button) findViewById(R.id.button_14);
		bt15 = (Button) findViewById(R.id.button_15);
		bt16 = (Button) findViewById(R.id.button_16);
		bt17 = (Button) findViewById(R.id.button_17);
		bt18 = (Button) findViewById(R.id.button_18);
		bt19 = (Button) findViewById(R.id.button_19);
		bt20 = (Button) findViewById(R.id.button_20);
		bt21 = (Button) findViewById(R.id.button_21);
		bt22 = (Button) findViewById(R.id.button_22);
		
		CldOlsParam initParam = new CldOlsParam();
		initParam.appver = "M3478-L7032-3723J0Q";
		initParam.isTestVersion = true;
		initParam.isDefInit = false;
		initParam.apptype = 31;
		initParam.appid = 25;
		initParam.bussinessid = 7;
		initParam.cid = 1060;
		initParam.mapver = "37200B13J0Q010A1";
		CldOlsBase.getInstance().init(initParam, new IInitListener() {
			@Override
			public void onUpdateConfig() {
				// TODO Auto-generated method stub
				CldLog.e("ols", "ConfigUpdated!");
			}

			@Override
			public void onInitDuid() {
				// TODO Auto-generated method stub
				CldLog.e("ols", "duid:"
						+ CldKAccountAPI.getInstance().getDuid());
			}
		});
		
		List<MtqDeliTaskDetail> mtqDeliTaskDetailList = new ArrayList<MtqDeliTaskDetail>();
		mtqDeliTaskDetailList = OrmLiteApi.getInstance().queryAll(MtqDeliTaskDetail.class);
		Log.i("yyh", "mtqDeliTaskDetailList:" + mtqDeliTaskDetailList.size());
		if (mtqDeliTaskDetailList != null) {
			for(int i = 0; i < mtqDeliTaskDetailList.size(); i ++){
			Log.i("yyh", "load detail from database ,taskId:" + mtqDeliTaskDetailList.get(i).getTaskid()
					+ ", corpName:" + mtqDeliTaskDetailList.get(i).getCorpname() 
					+ ", store :" + mtqDeliTaskDetailList.get(i).getStore().size()
					+ ", order:" + mtqDeliTaskDetailList.get(i).getOrders().size());
			}
		}
		//��¼
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				CldKAccountAPI.getInstance().login(userName, passwd);
			}
		});
		//��Ȩ
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				CldBllKDelivery.getInstance().loginAuth(
						new ICldResultListener() {

							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								Log.e("yyh", "loginAuth result:" + errCode);
							}
						});
			}
		});
		//�ܱ��ŵ�
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().searchNearStores("44424246",
						410817200, 81362500, 10000, 1, 20,
						new ICldDeliSearchStoreListener() {
							@Override
							public void onGetResult(int errCode,
									CldDeliSearchStoreResult result) {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub
								if (errCode != 0)
									return;
								for (int i = 0; i < result.lstOfStores.size(); i++) {
									Log.e("yyh",
											"store name ="
													+ result.lstOfStores.get(i).storeName);
								}
							}
						});
			}
		});
		//�����ŵ�
		bt4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().searchStores("44424246", "����", 1,
						1, 20, new ICldDeliSearchStoreListener() {

							@Override
							public void onGetResult(int errCode,
									CldDeliSearchStoreResult result) {
								// TODO Auto-generated method stub
								if (errCode != 0)
									return;
								for (int i = 0; i < result.lstOfStores.size(); i++) {
									Log.e("yyh",
											"store name ="
													+ result.lstOfStores.get(i).storeName);
								}
							}
						});
			}
		});
		//δ��ע�ŵ�
		bt5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// CldBllKDelivery.getInstance().searchNoPosStores(corpid,
				// regioncity, page, pageSize, listener);
			}
		});
		//����
		bt6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (CldDalKDelivery.getInstance().getLstOfMyGroups().size() > 0) {
					Log.e("yyh", "contact="
							+ CldDalKDelivery.getInstance().getLstOfMyGroups()
									.get(0).contact);
					Log.e("yyh", "corpId="
							+ CldDalKDelivery.getInstance().getLstOfMyGroups()
									.get(0).corpId);
					Log.e("yyh", "corpName="
							+ CldDalKDelivery.getInstance().getLstOfMyGroups()
									.get(0).corpName);
					Log.e("yyh", "groupId="
							+ CldDalKDelivery.getInstance().getLstOfMyGroups()
									.get(0).groupId);
					Log.e("yyh", "groupName="
							+ CldDalKDelivery.getInstance().getLstOfMyGroups()
									.get(0).groupName);
					Log.e("yyh", "mobile="
							+ CldDalKDelivery.getInstance().getLstOfMyGroups()
									.get(0).mobile);
				}

			}
		});
		//��ȡ�˻����б�δ����˻����б�
		bt7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getDeliTaskList(null,
						new ICldDeliGetTaskListListener() {
							@Override
							public void onGetTaskLstResult(int errCode,
									List<MtqDeliTask> lstOfTask) {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub
								if (errCode != 0)
									return;
								for (int i = 0; i < lstOfTask.size(); i++) {
									Log.e("yyh",
											"deliList id = "
													+ lstOfTask.get(i).taskid);
									Log.e("yyh", "deliList store count = "
											+ lstOfTask.get(i).store_count);
									
									Log.e("yyh", "deliList corpname = "
											+ lstOfTask.get(i).corpname);
									
								}
							}
						});
			}
		});
		//��ȡ���˵���ʷ�����е��˻����б�
		bt8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getDeliTaskHistoryList(
						"0|1|2|3|4", null, 1, 200,
						new ICldDeliGetTaskHistoryListListener() {

							@Override
							public void onGetTaskLstResult(int errCode,
									List<MtqDeliTask> lstOfTask, int page,
									int pagecount, int total) {
								// TODO Auto-generated method stub
								if (errCode != 0)
									return;
								for (int i = 0; i < lstOfTask.size(); i++) {
									Log.e("yyh", "deliListHis id = "
											+ lstOfTask.get(i).taskid);
									Log.e("yyh", "deliListHis count= "
											+ lstOfTask.get(i).store_count);
								}
							}
						});

			}
		});
		//��ȡ�˵�����
		bt9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().getDeliTaskDetail("44424246",
						"170396562683", 1, 20,
						new ICldDeliGetTaskDetailListener() {

							@Override
							public void onGetTaskDetailResult(int errCode,
									MtqDeliTaskDetail taskInfo) {
								// TODO Auto-generated method stub
								if (errCode != 0)
									return;
								for (int i = 0; i < taskInfo.getStore().size(); i++) {
									Log.e("yyh", "store name ="
											+ taskInfo.getStore().get(i).storename);
//									Log.e("yyh", "store order  ="
//											+ taskInfo.getStore().get(i).orders);
								}
								OrmLiteApi.getInstance().save(taskInfo);
							}
						});

			}
		});
		//�ܾ����복��
		bt10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldBllKDelivery.getInstance().unJoinGroup("284464429862",
						new ICldResultListener() {
							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								Log.e("yyh", "unjoin code=" + errCode);
							}
						});
			}
		});
		//ͬ����복��
		bt11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldBllKDelivery.getInstance().joinGroup("284464429862",
						new ICldResultListener() {

							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								Log.e("yyh", "join code=" + errCode);
							}
						});
			}
		});
		//��ע�ŵ�
		bt12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CldDeliUploadStoreParm param = new CldDeliUploadStoreParm();
				param.corpid = "48606852";
				param.settype = 2;
				param.storeid = "172483";
				param.storename = "�׶�վ";
				param.address = "������������";
				param.linkman = "�ϰ�213";
				param.phone = "9527";
				param.storekcode = "8uz88822g";
				param.remark = "2016.09.26";
				param.iscenter = 0;

				CldBllKDelivery.getInstance().uploadStore(param,
						new ICldResultListener() {

							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								Log.e("yyh", "uploadstore errcode=" + errCode);
							}
						});
			}
		});
		//�ϱ�����Χ��
		bt13.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CldDeliElectFenceUpload uploadPara = new CldDeliElectFenceUpload();
				uploadPara.am = 6;
				uploadPara.at = 1;
				uploadPara.st = 1;
				uploadPara.rid = "605";

				List<CldDeliElectFenceUpload> lst = new ArrayList<CldDeliElectFenceUpload>();
				lst.add(uploadPara);

				CldUploadEFParm parm = new CldUploadEFParm();
				parm.corpid = "44424246";
				parm.x = 410817200;
				parm.y = 81362500;
				parm.lstOfLauchEF = lst;

				CldBllKDelivery.getInstance().uploadElectfence(parm,
						new ICldResultListener() {

							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								Log.e("yyh", "upload errCode=" + errCode);
							}
						});
			}
		});
		//�������Χ��
		bt14.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldBllKDelivery.getInstance().requestElectfence("44424246",
						new ICldDeliGetElectfenceListener() {
							@Override
							public void onGetResult(int errCode,
									List<CldElectfence> lstOfElectfence) {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub
								for (int i = 0; i < lstOfElectfence.size(); i++) {
									Log.e("yyh",
											"elen name="
													+ lstOfElectfence.get(i).id);
								}
							}
						});
			}
		});
		//��ȡ��Ȩ�û��б�
		bt15.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldBllKDelivery.getInstance().getMonitorAuthList(
						new ICldDeliveryMonitorListener() {
							@Override
							public void onGetResult(int errCode,
									List<CldMonitorAuth> lstOfResult) {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub
								for (int i = 0; i < lstOfResult.size(); i++) {
									Log.e("yyh",
											"auth name="
													+ lstOfResult.get(i).mobile);
								}
							}
						});
			}
		});
		//��ȡ��Ȩ�ŵ��б�
		bt16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		//�޸Ļ��˵�״̬
		bt17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �޸��˻���״̬��0���˻�1�˻���2�����3��ͣ״̬4��ֹ״̬ ��
				CldBllKDelivery.getInstance().updateDeliTaskStatus("44424246",
						"160720423664", 3, "44424246", "160720423664",
						410817200, 81362500, 0, 0,
						new ICldDeliTaskStatusListener() {

							@Override
							public void onUpdateTaskStatus(int errCode,
									String corpid, String taskid, int status) {
								// TODO Auto-generated method stub

							}
						});
			}
		});
		//������Ȩ��ҵ�б�
		bt18.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldBllKDelivery.getInstance().requestAuthStoreList(
						new ICldResultListener() {

							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								List<CldDeliGroup> lstOfAuth = CldDalKDelivery
										.getInstance().getLstOfAuthCorps();
								for (int i = 0; i < lstOfAuth.size(); i++) {
									Log.e("yyh", "name = "
											+ lstOfAuth.get(i).corpName);
								}
							}
						});
			}
		});
		//��ȡȨ����Ϣ
		bt20.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldKDeliveryAPI.getInstance().getAuthInfoList(new ICldAuthInfoListener() {
					
					@Override
					public void onGetResult(int errCode, List<AuthInfoList> lstOfResult) {
						// TODO Auto-generated method stub
						Log.e("yyh","errcode = "+errCode);
					}
				});
			}
		});
		//��ȡ����Χ��
		bt21.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CldKDeliveryAPI.getInstance().requestAllElectfence(new ICldDeliGetElectfenceListener() {
					
					@Override
					public void onGetResult(int errCode, List<CldElectfence> lstOfElectfence) {
						// TODO Auto-generated method stub
						Log.e("yyh","errCode = "+errCode);
					}
				});
			}
		});
		
		bt22.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldBllKDelivery.getInstance().uploadGoodScanRecord("44424246", "160720423664", "1115464", "546464", "454664",
						System.currentTimeMillis(), 0, 0, new ICldResultListener() {
							
							@Override
							public void onGetResult(int errCode) {
								// TODO Auto-generated method stub
								Log.e("yyh","errCode = "+errCode);
							}
						});
				
			}
//				CldBllKDelivery.getInstance().updateDeliTaskStatus("44424246",
//						"160720423664", 3, "44424246", "160720423664",
//						410817200, 81362500, 0, 0,
//						new ICldDeliTaskStatusListener() {
//
//							@Override
//							public void onUpdateTaskStatus(int errCode,
//									String corpid, String taskid, int status) {
//								// TODO Auto-generated method stub
//
//							}
//						});
//			}
		});

	}
}
