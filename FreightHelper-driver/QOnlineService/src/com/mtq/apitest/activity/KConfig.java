/*
 * @Title KConfig.java
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
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.cld.log.CldLog;
import com.mtq.apitest.listener.MyKAClistener;
import com.mtq.apitest.ui.MyListAdapter;
import com.mtq.apitest.ui.MyListItem;
import com.mtq.ols.R;
import com.mtq.ols.api.CldKAuthcheckAPI;
import com.mtq.ols.api.CldKCallNaviAPI;
import com.mtq.ols.api.CldKConfigAPI;
import com.mtq.ols.api.CldKUtilAPI;
import com.mtq.ols.api.CldOlsBase;
import com.mtq.ols.api.CldOlsBase.IInitListener;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.bll.CldKConfig.ConfigDomainType;
import com.mtq.ols.sap.CldSapUtil;


/**
 * The Class KConfig.
 * 
 * @Description ����ϵͳ
 * @author Zhouls
 * @date 2014-11-4 ����2:58:05
 */
public class KConfig extends Activity {

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
		setContentView(R.layout.kconfig);
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
		mListView = (ListView) findViewById(R.id.list_kc);
		mButtonList = new ArrayList<MyListItem>();
		CldKAuthcheckAPI.getInstance().setCldKAuthcheckListener(
				new MyKAClistener());
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
		item = new MyListItem("��������", new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldKConfigAPI.getInstance().updateCofig(new IInitListener() {

					@Override
					public void onUpdateConfig() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onInitDuid() {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		mButtonList.add(item);
		item = new MyListItem("����ͷ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < ConfigDomainType.NAVI_SVR_PO + 1; i++) {
					CldLog.d("ols", i + "|" +CldKConfigAPI.getInstance().getSvrDomain(i));
				}
			}
		});
		mButtonList.add(item);
		item = new MyListItem("�������־�ļ�", new OnClickListener() {
			@Override
			public void onClick(View v) {
				CldLog.setLogFileName(CldBllUtil.getInstance().getAppPath()
						+ "/log/CldOlsLog.txt");
				CldLog.setLogEMode(true);
				Toast.makeText(getApplicationContext(), "��־������ļ����ܿ���",
						Toast.LENGTH_SHORT).show();
			}
		});
		mButtonList.add(item);
		item = new MyListItem("����ģʽ", new OnClickListener() {
			@Override
			public void onClick(View v) {
				String appPath = Environment.getExternalStorageDirectory()
						+ "/CldOlsApi";
				CldOlsBase.getInstance().setDebugMode(appPath);
				Toast.makeText(getApplicationContext(), "����ģʽ����",
						Toast.LENGTH_SHORT).show();
			}
		});
		mButtonList.add(item);

		item = new MyListItem("����ģʽ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldKUtilAPI.getInstance().setCarMode();
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						CldKCallNaviAPI.getInstance().upLocation(null,
								22.537693, 114.018303);
					}
				}, 15000, 300000);
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						CldKCallNaviAPI.getInstance().recPptMsg(null,
								22.537693, 114.018303, true);
					}
				}, 30000, 30000);
			}
		});
		mButtonList.add(item);
		item = new MyListItem("��Ȩ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				CldKAuthcheckAPI.getInstance().authCheck(
						"29c2fb265a5g2gg745b18a71");
			}
		});
		mButtonList.add(item);
		item = new MyListItem("KC", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(
						getApplicationContext(),
						CldKConfigAPI.getInstance().getSvrDomain(
								ConfigDomainType.NAVI_SVR_KC),
						Toast.LENGTH_SHORT).show();
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
