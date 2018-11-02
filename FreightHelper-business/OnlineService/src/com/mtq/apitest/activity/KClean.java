/*
 * @Title KClean.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.apitest.activity;

import com.mtq.apitest.ui.DataCleanManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


/**
 * The Class KClean.
 * 
 * @Description ����˽����
 * @author Zhouls
 * @date 2014-12-9 ����11:38:32
 */
public class KClean extends Activity {

	/**
	 * On create.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Button cls = new Button(getApplicationContext());
		cls.setText("���˽�д洢");
		cls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataCleanManager.cleanSharedPreference(getApplicationContext());
				Toast.makeText(getApplicationContext(), "����ɹ�",
						Toast.LENGTH_SHORT).show();
			}
		});
		setContentView(cls);
	}
}
