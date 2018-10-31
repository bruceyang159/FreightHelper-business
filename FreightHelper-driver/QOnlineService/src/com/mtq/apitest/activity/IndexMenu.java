/*
 * @Title IndexMenu.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.apitest.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.cld.log.CldLog;
import com.mtq.apitest.ui.MyListAdapter;
import com.mtq.apitest.ui.MyListItem;
import com.mtq.ols.R;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.api.CldOlsBase;
import com.mtq.ols.api.CldOlsBase.CldOlsParam;
import com.mtq.ols.api.CldOlsBase.IInitListener;

/**
 * The Class IndexMenu.
 * 
 * @Description �˵�
 * @author Zhouls
 * @date 2014-11-4 ����2:49:46
 */
public class IndexMenu extends Activity {

	/** The m menu view. */
	private ListView mMenuView;

	/** The m menu list. */
	private List<MyListItem> mMenuList;

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
		setContentView(R.layout.index_menu);
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
		CldKAccountAPI.getInstance().startAutoLogin(null);
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
		mMenuView = (ListView) findViewById(R.id.list_menu);
		mMenuList = new ArrayList<MyListItem>();
		mMenuList.add(new MyListItem("�ն�����", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), KConfig.class);
				startActivity(intent);
			}
		}));
		mMenuList.add(new MyListItem("�ʺ�ϵͳ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), KAccount.class);
				startActivity(intent);
			}
		}));

		mMenuList.add(new MyListItem("��Ϣϵͳ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), KMessage.class);
				startActivity(intent);
			}
		}));
		mMenuList.add(new MyListItem("һ��ͨ", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), KAkeyCall.class);
				startActivity(intent);
			}
		}));

		mMenuList.add(new MyListItem("KAutoTest", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), KAutoTest.class);
				startActivity(intent);
			}
		}));
		mMenuList.add(new MyListItem("Clean", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), KClean.class);
				startActivity(intent);
			}
		}));
		mMenuList.add(new MyListItem("����", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Delivery.class);
				startActivity(intent);
			}
		}));
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
		mMenuView.setAdapter(new MyListAdapter(getApplicationContext(),
				mMenuList));
	}
}
