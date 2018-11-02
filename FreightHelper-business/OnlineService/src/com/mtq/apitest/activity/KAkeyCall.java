/*
 * @Title KAkeyCall.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.apitest.activity;

import java.util.ArrayList;
import java.util.List;
import com.cld.log.CldLog;import com.mtq.apitest.listener.MyKClistener;
import com.mtq.apitest.ui.DalInput;
import com.mtq.apitest.ui.InputDialog;
import com.mtq.apitest.ui.MyListAdapter;
import com.mtq.apitest.ui.MyListItem;
import com.mtq.apitest.ui.InputView.AfterListener;
import com.mtq.ols.R;
import com.mtq.ols.api.CldKCallNaviAPI;
import com.mtq.ols.api.CldKConfigAPI;
import com.mtq.ols.bll.CldKConfig.ConfigDomainType;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;


/**
 * The Class KAkeyCall.
 * 
 * @Description һ��ͨ
 * @author Zhouls
 * @date 2014-11-4 ����2:57:56
 */
public class KAkeyCall extends Activity {

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
		setContentView(R.layout.kakeycall);
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
		mListView = (ListView) findViewById(R.id.list_kakey);
		mButtonList = new ArrayList<MyListItem>();
		CldKCallNaviAPI.getInstance().setCldKCallNaviListener(
				new MyKClistener());
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
		item = new MyListItem("��ȡ���ֻ��б�", new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldKCallNaviAPI.getInstance().getBindMobile();
			}
		});
		mButtonList.add(item);
		item = new MyListItem("��ȡ���ֻ���֤��", new OnClickListener() {

			@Override
			public void onClick(View v) {
				new InputDialog(KAkeyCall.this, new AfterListener() {
					@Override
					public void toDo() {
						Toast.makeText(getApplicationContext(),
								DalInput.getInstance().getStrEd1(),
								Toast.LENGTH_SHORT).show();
						CldKCallNaviAPI.getInstance().getIdentifycode(
								DalInput.getInstance().getStrEd1());
					}

					@Override
					public void stopDo() {
						// TODO Auto-generated method stub

					}
				}).show();

			}
		});
		mButtonList.add(item);
		item = new MyListItem("�������ֻ�(idcode,mobile,nmobile)",
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						new InputDialog(KAkeyCall.this, new AfterListener() {
							@Override
							public void toDo() {
								Toast.makeText(getApplicationContext(),
										DalInput.getInstance().getStrEd1(),
										Toast.LENGTH_SHORT).show();
								// if
								// (CldDalKCallNavi.getInstance().getMobiles()
								// .size() > 0) {
								// CldKCallNaviAPI.getInstance().bindToMobile(
								// DalInput.getInstance().getStrEd1(),
								// DalInput.getInstance().getStrEd2(),
								// CldDalKCallNavi.getInstance()
								// .getMobiles().get(0));
								// } else {
								CldKCallNaviAPI.getInstance().bindToMobile(
										DalInput.getInstance().getStrEd1(),
										DalInput.getInstance().getStrEd2(),
										null);
								// }

							}

							@Override
							public void stopDo() {
								// TODO Auto-generated method stub

							}
						}).show();
					}
				});
		mButtonList.add(item);
		item = new MyListItem("ɾ��һ��ͨ����", new OnClickListener() {

			@Override
			public void onClick(View v) {
				new InputDialog(KAkeyCall.this, new AfterListener() {
					@Override
					public void toDo() {
						Toast.makeText(getApplicationContext(),
								DalInput.getInstance().getStrEd1(),
								Toast.LENGTH_SHORT).show();
						CldKCallNaviAPI.getInstance().delBindMobile(
								DalInput.getInstance().getStrEd1());
					}

					@Override
					public void stopDo() {
						// TODO Auto-generated method stub

					}
				}).show();

			}
		});
		mButtonList.add(item);
		item = new MyListItem("ע��һ��ͨ", new OnClickListener() {
			@Override
			public void onClick(View v) {
				CldKCallNaviAPI.getInstance().registerByKuid();

			}
		});
		mButtonList.add(item);
		item = new MyListItem("�ϱ�λ��", new OnClickListener() {
			@Override
			public void onClick(View v) {
				CldKCallNaviAPI.getInstance().upLocation(
						CldKCallNaviAPI.getInstance().getPptMobile(),
						22.537693, 114.018303);

			}
		});
		mButtonList.add(item);
		item = new MyListItem("����绰", new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String phoneUri = "tel:"
								+ CldKConfigAPI.getInstance().getSvrDomain(
										ConfigDomainType.PHONE_HDSC);
						Uri uri = Uri.parse(phoneUri);
						Intent intent = new Intent(Intent.ACTION_CALL, uri);
						startActivity(intent);
					}
				}).start();
			}
		});
		mButtonList.add(item);
		item = new MyListItem("��ȡһ��ͨ��Ϣ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldKCallNaviAPI.getInstance().recPptMsg(
						CldKCallNaviAPI.getInstance().getPptMobile(),
						22.537693, 114.018303, false);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("��ȡһ��ͨ�ֻ����б�", new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < CldKCallNaviAPI.getInstance()
						.getPptMobileList().size(); i++) {
					CldLog.d("ols", CldKCallNaviAPI.getInstance()
							.getPptMobileList().get(i));
				}
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
