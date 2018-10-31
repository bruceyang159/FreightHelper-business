/*
 * @Title KMessage.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.apitest.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.cld.log.CldLog;
import com.mtq.apitest.listener.MyKMlistener;
import com.mtq.apitest.ui.DalInput;
import com.mtq.apitest.ui.InputDialog;
import com.mtq.apitest.ui.MyListAdapter;
import com.mtq.apitest.ui.MyListItem;
import com.mtq.apitest.ui.InputView.AfterListener;
import com.mtq.ols.R;
import com.mtq.ols.api.CldKMessageAPI;
import com.mtq.ols.api.CldKServiceAPI;
import com.mtq.ols.api.CldKMessageAPI.IRecEggMsg;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.bll.CldKAccount;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.dal.CldDalKMessage;
import com.mtq.ols.sap.bean.CldSapKMParm;
import com.mtq.ols.sap.bean.CldSapKMParm.SharePoiParm;


/**
 * The Class KMessage.
 * 
 * @Description ��Ϣϵͳ
 * @author Zhouls
 * @date 2014-11-4 ����2:57:14
 */
public class KMessage extends Activity {

	/** The m list view. */
	private ListView mListView;

	/** The m button list. */
	private List<MyListItem> mButtonList;

	/**
	 * On create.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kmessage);
		initData();
		initControls();
		bindData();
	}

	/**
	 * Inits the data.
	 * 
	 * @return void
	 * @Description ��ʼ������
	 * @author Zhouls
	 * @date 2014-11-4 ����2:17:51
	 */
	public void initData() {
		mListView = (ListView) findViewById(R.id.list_km);
		mButtonList = new ArrayList<MyListItem>();
		CldKMessageAPI.getInstance().setCldKMessageListener(new MyKMlistener());
	}

	/**
	 * Inits the controls.
	 * 
	 * @return void
	 * @Description ��ʼ���ؼ�
	 * @author Zhouls
	 * @date 2014-11-4 ����2:18:00
	 */
	public void initControls() {
		MyListItem item;
		item = new MyListItem("������Ϣ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						CldKServiceAPI.getInstance().recMessage(
								CldKServiceAPI.getInstance().getDuid(),
								CldKServiceAPI.getInstance().getApptype(),
								"6.4", new ArrayList<CldSapKMParm>(),
								CldKServiceAPI.getInstance().getKuid(), 440300,
								100001, 100023,
								CldBllUtil.getInstance().getBussinessid(),
								CldKServiceAPI.getInstance().getSession(),
								CldBllUtil.getInstance().getAppid());
					}
				}).start();
			}
		});
		mButtonList.add(item);
		item = new MyListItem("������Ϣ(target,content)", new OnClickListener() {

			@Override
			public void onClick(View v) {
				new InputDialog(KMessage.this, new AfterListener() {

					@Override
					public void toDo() {
						CldKAccount.getInstance().isRegisterUser(
								DalInput.getInstance().getStrEd1());
						SharePoiParm poi = new SharePoiParm(DalInput
								.getInstance().getStrEd2(), "12,15",
								CldDalKAccount.getInstance().getKuidRegUser());
						CldKMessageAPI.getInstance().sendSharePoiMsg(poi, 1);
					}

					@Override
					public void stopDo() {
						// TODO Auto-generated method stub

					}
				}).show();
			}
		});
		mButtonList.add(item);
		item = new MyListItem("����������Ϣ��ʷ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						CldKMessageAPI.getInstance().recLastestMsgHistory(
								"1,2,3,4,11,15");
					}
				}).start();
			}
		});
		mButtonList.add(item);
		item = new MyListItem("������Ϣ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldKMessageAPI.getInstance().recNewMsgHistory("1,2,3,4,11,15",
						0, 10, 5380, 1415239181);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("������Ϣ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldKMessageAPI.getInstance().recOldMsgHistory("1,2,3,4,11,15",
						0, 10, 5380, 1415239181);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("������Ϣ�Ķ�״̬", new OnClickListener() {

			@Override
			public void onClick(View v) {
				List<String> list = new ArrayList<String>();
				list.add("538111,1415239141,1");
				CldKMessageAPI.getInstance().upMsgReadStatus(list);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("��һ�λ�ȡ�ʵ��б�", new OnClickListener() {
			@Override
			public void onClick(View v) {
				CldKMessageAPI.getInstance().dropAreaEggs(true);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("�ʵ��б�ʱ��", new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						CldKMessageAPI.getInstance().dropAreaEggs(false);
					}
				}, 6000, 30000);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("�Ƿ�ȵ���ʱ��", new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						CldKMessageAPI.getInstance().isInEggsArea(100, 100);
					}
				}, 1000, 1000);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("���ʵ��б�ʱ��", new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						for (int i = 0; i < CldDalKMessage.getInstance()
								.getListEggs().size(); i++) {
							CldLog.d("1", CldDalKMessage.getInstance()
									.getListEggs().size()
									+ "");
							if (i == 5) {
								CldLog.d("2", CldDalKMessage.getInstance()
										.getListEggs().get(i).getMaxx()
										+ ","
										+ CldDalKMessage.getInstance()
												.getListEggs().get(i).getMaxy()
										+ "||" + i);
							}
						}
					}
				}, 1000, 1000);

			}
		});
		mButtonList.add(item);
		item = new MyListItem("���ղʵ���Ϣ", new OnClickListener() {
			@Override
			public void onClick(View v) {
				CldKMessageAPI.getInstance().recEggsMsg(-1, 12, 25,
						new IRecEggMsg() {
							@Override
							public void onRecEggsMsg(int errCode,
									List<CldSapKMParm> list) {
								CldLog.d("ols", list.size() + "");
							}
						});
			}
		});
		mButtonList.add(item);
	}

	/**
	 * Bind data.
	 * 
	 * @return void
	 * @Description ������
	 * @author Zhouls
	 * @date 2014-11-4 ����2:21:16
	 */
	public void bindData() {
		mListView.setAdapter(new MyListAdapter(getApplicationContext(),
				mButtonList));
	}
}
