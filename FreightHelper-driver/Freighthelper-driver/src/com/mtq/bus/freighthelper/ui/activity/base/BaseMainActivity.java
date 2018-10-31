package com.mtq.bus.freighthelper.ui.activity.base;

import me.yokeyword.fragmentation.SupportActivity;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.mtq.bus.freighthelper.manager.ActivityManager;
import com.mtq.bus.freighthelper.utils.SystemBarHintUtil;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRadioGroup;
import com.zhy.autolayout.AutoRelativeLayout;

import de.greenrobot.event.EventBus;

public abstract class BaseMainActivity extends SupportActivity {
	
	private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
	private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
	private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
	private static final String WIDGET_RADIOGROUP = "RadioGroup";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(getLayoutResID());
		SystemBarHintUtil.setSystemBarHint(this);
		initFragment(savedInstanceState);
		initViews();
		setListener();
		initData();
		loadData();
		updateUI();
		
		/**
		 * 网络状态变化广播
		 * add 2017-07-21
		 */
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mBroadcastReceiver, intentFilter);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ActivityManager.getInstance().addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

	protected abstract int getLayoutResID();
	
	protected abstract void initFragment(Bundle savedInstanceState);

	protected abstract void initViews();

	protected abstract void setListener();

	protected abstract void initData();

	protected abstract void loadData();

	protected abstract void updateUI();
	
	protected abstract void onConnectivityChange();
	
	protected BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			BaseMainActivity.this.onReceive(intent);
		}
	};

	@SuppressLint("NewApi")
	protected void onReceive(Intent intent) {
		String action = intent.getAction();
		if (!TextUtils.isEmpty(action)) {
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				onConnectivityChange();
			}
		}
	}
	
	/**
     * 当前窗口只要有 inflate进来都会 回调
     */
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs)
    {
        View view = null;
        if(name.equals(LAYOUT_FRAMELAYOUT))
        {
            view = new AutoFrameLayout(context, attrs);
        }

        if(name.equals(LAYOUT_LINEARLAYOUT))
        {
            view = new AutoLinearLayout(context, attrs);
        }

        if(name.equals(LAYOUT_RELATIVELAYOUT))
        {
            view = new AutoRelativeLayout(context, attrs);
        }
        if(name.equals(WIDGET_RADIOGROUP))
        {
            view = new AutoRadioGroup(context, attrs);
        }

        if(view != null)
            return view;

        return super.onCreateView(name, context, attrs);
    }
}
