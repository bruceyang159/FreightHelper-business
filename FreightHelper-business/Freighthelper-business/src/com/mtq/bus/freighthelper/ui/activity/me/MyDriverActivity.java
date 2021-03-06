/**
 * 
 * Copyright © 2017 mtq. All rights reserved.
 *
 * @Title: MyDriverActivity.java
 * @Prject: MTQBusFreighthelper
 * @Package: com.mtq.bus.freighthelper.ui.activity.me
 * @Description: 我的司机
 * @author: zhaoqy
 * @date: 2017年6月16日 下午9:40:08
 * @version: V1.0
 */

package com.mtq.bus.freighthelper.ui.activity.me;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.mtq.bus.freighthelper.R;
import com.mtq.bus.freighthelper.api.deliverybus.DeliveryBusAPI;
import com.mtq.bus.freighthelper.bean.DropDown;
import com.mtq.bus.freighthelper.bean.eventbus.BaseEvent;
import com.mtq.bus.freighthelper.db.DriverTable;
import com.mtq.bus.freighthelper.manager.HandleErrManager;
import com.mtq.bus.freighthelper.ui.activity.base.BaseActivity;
import com.mtq.bus.freighthelper.ui.adapter.DropDownAdapter;
import com.mtq.bus.freighthelper.ui.adapter.ItemClickListener;
import com.mtq.bus.freighthelper.ui.adapter.MyDriverAdapter;
import com.mtq.bus.freighthelper.ui.customview.ClearEditText;
import com.mtq.bus.freighthelper.ui.customview.ListViewForRefresh;
import com.mtq.bus.freighthelper.ui.customview.ShadowDrawable;
import com.mtq.bus.freighthelper.ui.customview.ListViewForRefresh.OnRefreshListener;
import com.mtq.bus.freighthelper.ui.dialog.ProgressDialog;
import com.mtq.bus.freighthelper.utils.CallUtils;
import com.mtq.bus.freighthelper.utils.ConstantUtils;
import com.mtq.bus.freighthelper.utils.DensityUtils;
import com.mtq.bus.freighthelper.utils.DriverUtils;
import com.mtq.bus.freighthelper.utils.DropDownUtils;
import com.mtq.bus.freighthelper.utils.PermissionUtils;
import com.mtq.ols.module.deliverybus.MtqDeliveryBusAPI.IMtqDriverDataListener;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqDriver;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MyDriverActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnRefreshListener, TextWatcher {

	public static final String TAG = "MyDriverActivity";
	public static final int PAGESIZE = 200;
	public static final int REQUEST_MYDRIVER = 1;

	public static final int UPDATE_LOADING = 0;
	public static final int UPDATE_NET_ABNORMAL = 1;
	public static final int UPDATE_SUCCESS = 2;
	public static final int UPDATE_FAILED = 3;
	public static final int UPDATE_EMPTY = 4;

	private View mTitleLayout;
	private ImageView mBack;
	private TextView mTitle;
	private TextView mRight;
	private ImageView mDropImg;

	private TextView mEmpty;
	private LinearLayout mFailed;
	private LinearLayout mNetAbnormal;
	private LinearLayout mLoading;
	private LinearLayout mLayout;
	private LinearLayout mSearchLayout;
	private ClearEditText mClearEdit;
	private ListViewForRefresh mListView;
	private MyDriverAdapter mAdapter;
	private List<MtqDriver> mMyDrivers;
	private List<MtqDriver> mTotalDrivers;
	private String mCurMobile = "";
	private int mCurPosition = -1;
	private int mCurPage = 0;
	private int mTotal = 0;

	private PopupWindow mPop;
	private DropDownAdapter mPullAdapter;
	private List<DropDown> mDropList;
	private int mShadowWidth = 0;
	private int xoff = 0;
	private int mCurStatus = 0;
	private boolean mHasPull = false;

	@Override
	protected int getLayoutResID() {
		return R.layout.activity_mydriver;
	}

	@Override
	protected void initViews() {
		mTitleLayout = findViewById(R.id.mydriver_title);
		mBack = (ImageView) findViewById(R.id.title_left_img);
		mTitle = (TextView) findViewById(R.id.title_text);
		mRight = (TextView) findViewById(R.id.title_right_text);
		mDropImg = (ImageView) findViewById(R.id.title_right_img);

		mEmpty = (TextView) findViewById(R.id.mydriver_empty);
		mFailed = (LinearLayout) findViewById(R.id.mydriver_failed);
		mNetAbnormal = (LinearLayout) findViewById(R.id.mydriver_net_abnormal);
		mLoading = (LinearLayout) findViewById(R.id.mydriver_laoding);
		mLayout = (LinearLayout) findViewById(R.id.mydriver_layout);
		mSearchLayout = (LinearLayout) findViewById(R.id.mydriver_search_layout);
		mClearEdit = (ClearEditText) findViewById(R.id.mydriver_clear_edit);
		mListView = (ListViewForRefresh) findViewById(R.id.mydriver_listview);
	}

	@Override
	protected void setListener() {
		mBack.setOnClickListener(this);
		mRight.setOnClickListener(this);
		mDropImg.setOnClickListener(this);
		findViewById(R.id.net_abnormal_refresh).setOnClickListener(this);
		findViewById(R.id.failed_refresh).setOnClickListener(this);
		mClearEdit.addTextChangedListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnRefreshListener(this);
	}

	@Override
	protected void initData() {
		mTitle.setText(R.string.me_driver);
		mBack.setVisibility(View.VISIBLE);
		mRight.setVisibility(View.VISIBLE);
		mRight.setText("全部");

		mMyDrivers = new ArrayList<MtqDriver>();
		mTotalDrivers = new ArrayList<MtqDriver>();
		mAdapter = new MyDriverAdapter(this, mMyDrivers, mListener);
		mListView.setAdapter(mAdapter);
		mListView.setEmptyView(mEmpty);

		mDropList = DropDownUtils.getMyDriverDrop();
		mPullAdapter = new DropDownAdapter(this, mDropList);

		mShadowWidth = DensityUtils.getDedaultSize(this);
		xoff = (int) (DensityUtils.getWidth(this) * 0.60);
	}

	@Override
	protected void loadData() {
		if (CldPhoneNet.isNetConnected()) {
			mCurPage = 1;
			getDriverDataList(mCurPage);
		} else {
			update(UPDATE_NET_ABNORMAL);
		}
	}

	@Override
	protected void updateUI() {
		
	}

	@Override
	protected void onConnectivityChange() {
		if (!CldPhoneNet.isNetConnected()) {
			if (mLoading != null && mLoading.isShown()) {
				mNetAbnormal.setVisibility(View.VISIBLE);
				mLoading.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.title_left_img: {
			finish();
			break;
		}
		case R.id.title_right_text:
		case R.id.title_right_img: {
			if (!mHasPull) {
				mHasPull = true;
				mDropImg.setImageResource(R.drawable.icon_pull_up);
				pull();
			}
			break;
		}
		case R.id.net_abnormal_refresh:
		case R.id.failed_refresh: {
			if (!CldPhoneNet.isNetConnected()) {
				Toast.makeText(this, R.string.common_network_abnormal,
						Toast.LENGTH_SHORT).show();
			} else {
				update(UPDATE_LOADING);
				loadData();
			}
			break;
		}
		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private void pull() {
		if (mPop == null) {
			ListView listView = new ListView(this);
			listView.setCacheColorHint(0x00000000);
			listView.setBackgroundColor(getResources().getColor(R.color.white));
			Drawable drawable = getResources().getDrawable(
					R.drawable.divider_bg);
			listView.setDivider(drawable);
			listView.setDividerHeight(1);
			listView.setAdapter(mPullAdapter);
			ShadowDrawable.Builder.on(listView)
					.bgColor(getResources().getColor(R.color.white))
					.shadowColor(Color.parseColor("#000000"))
					.shadowRange(mShadowWidth).offsetBottom(mShadowWidth)
					.offsetTop(mShadowWidth).offsetLeft(mShadowWidth).create();
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mPop.dismiss();
					mHasPull = false;
					mDropImg.setImageResource(R.drawable.icon_pull_down);
					if (!CldPhoneNet.isNetConnected()) {
						Toast.makeText(MyDriverActivity.this,
								R.string.common_network_abnormal,
								Toast.LENGTH_SHORT).show();
					} else {
						filterByStatus(mDropList.get(position).id);
						mRight.setText(mDropList.get(position).name);
					}
				}
			});
			int width = (int) (DensityUtils.getWidth(this) * 0.40);
			mPop = new PopupWindow(listView, width + mShadowWidth,
					LayoutParams.WRAP_CONTENT, true);
			mPop.setBackgroundDrawable(new ColorDrawable(0x00000000));
			mPop.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					mPop.dismiss();
					mHasPull = false;
					mDropImg.setImageResource(R.drawable.icon_pull_down);
				}
			});
		}
		mPop.showAsDropDown(mTitleLayout, xoff - mShadowWidth, -mShadowWidth);
	}

	@Override
	public void onRefresh() {
		if (!CldPhoneNet.isNetConnected()) {
			mListView.onRefreshFinish();
			/**
			 * 如果mTotalDrivers为空，则直接显示"网络连接失败"
			 * modify 2017-08-03
			 */
			if (mTotalDrivers.isEmpty()) {
				update(UPDATE_NET_ABNORMAL);
			} else {
				Toast.makeText(this, R.string.common_network_abnormal,
						Toast.LENGTH_SHORT).show();
			}
		} else {
			/**
			 * 如果搜索框里面有关键字，则清除掉关键字
			 */
			if(mClearEdit.getText().toString().length() > 0) {
				mClearEdit.setText("");
			}
			
			/**
			 * 如果mTotalDrivers为空, 则重新获取车辆列表
			 * modify 2017-08-03
			 */
			if (mTotalDrivers.isEmpty()) {
				update(UPDATE_LOADING);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						getDriverDataList(mCurPage);
					}
				}, 500);
				return;
			}
			
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					int size = mTotalDrivers.size();
					if (size < mTotal) {
						mCurPage++;
						getDriverDataList(mCurPage);
					} else {
						/**
						 * 当前个数等于返回个数
						 */
						if (mCurPage == 1) {
							onRefreshMore(mCurPage);
						} else if (mCurPage > 1) {
							mCurPage++;
							onRefreshMore(mCurPage);
						}
					}
				}
			}, 500);
		}
	}

	@Override
	public void onLoadMoring() {
		if (!CldPhoneNet.isNetConnected()) {
			mListView.onRefreshFinish();
			Toast.makeText(this, R.string.common_network_abnormal,
					Toast.LENGTH_SHORT).show();
		} else {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mListView.onRefreshFinish();
					Toast.makeText(MyDriverActivity.this, "没有更多了",
							Toast.LENGTH_SHORT).show();
				}
			}, 500);
		}
	}

	private ItemClickListener mListener = new ItemClickListener() {

		@Override
		public void itemOnClick(int position, View v) {
			mCurMobile = mMyDrivers.get(position).phone;
			if (!TextUtils.isEmpty(mCurMobile)) {
				CallUtils.makeCall(MyDriverActivity.this, mCurMobile);
			}
		}
	};

	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		switch (requestCode) {
		case PermissionUtils.CALL_PHONE: {
			if (grantResults.length > 0
					&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (!TextUtils.isEmpty(mCurMobile)) {
					CallUtils.call(this, mCurMobile);
				}
			} else {
				/**
				 * 无权限
				 */
				Toast.makeText(this, "请打开拨打电话权限", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		default:
			super.onRequestPermissionsResult(requestCode, permissions,
					grantResults);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (!DeliveryBusAPI.getInstance().hasDriverReadPermission()) {
			Toast.makeText(this, "当前账号无查看司机权限", Toast.LENGTH_SHORT).show();
			return;
		}
		
		int headerCount = mListView.getHeaderViewsCount();
		mCurPosition = position - headerCount;
		int driverid = mMyDrivers.get(mCurPosition).driverid;
		Intent intent = new Intent(this, MyDriverDetailActivity.class);
		intent.putExtra(ConstantUtils.DRIVER_ID, driverid);
		startActivityForResult(intent, REQUEST_MYDRIVER);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_MYDRIVER) {
				Bundle bundle = data.getExtras();
				int invitestatus = bundle.getInt("invitestatus");
				CldLog.i(TAG, "invitestatus: " + invitestatus);
				mAdapter.updateView(mListView, mCurPosition, invitestatus);
				mMyDrivers.get(mCurPosition).invitestatus = invitestatus;
			}
		}
	}

	private void getDriverDataList(int pageindex) {
		DeliveryBusAPI.getInstance().getDriverDataList(mCurStatus, null,
				pageindex, PAGESIZE, new IMtqDriverDataListener() {

					@Override
					public void onResult(int errCode, List<MtqDriver> data,
							int total) {
						if (ProgressDialog.isShowProgress()) {
							ProgressDialog.cancelProgress();
						}
						
						/**
						 * 帐号被挤出
						 */
						if (errCode == 1008) {
							HandleErrManager.getInstance().handleErrCode(
									errCode);
							return;
						}

						if (errCode == 0) {
							mTotal = total;
							if (data != null && !data.isEmpty()) {
								if (mCurPage > 1) {
									Toast.makeText(MyDriverActivity.this, "刷新成功",
											Toast.LENGTH_SHORT).show();
									mListView.onRefreshFinish();
								} else {
									update(UPDATE_SUCCESS);
									mTotalDrivers.clear();
									mMyDrivers.clear();
								}
								mListView.onRefreshFinish();
								mTotalDrivers.addAll(0, data);
								mMyDrivers.addAll(0, data);
								mAdapter.notifyDataSetChanged();
								
								/*mTotalDrivers.addAll(0, data);
								*//********************根据邀请状态筛选**************//*
								List<MtqDriver> list = new ArrayList<MtqDriver>();
								list.clear();
								if (mCurStatus == 0) {
									list = data;
								} else {
									for (MtqDriver driver : data) {
										if (mCurStatus == driver.invitestatus) {
											list.add(driver);
										}
									}
								}
								*//*******************根据邀请状态筛选**************//*
								
								mMyDrivers.addAll(0, list);
								mAdapter.notifyDataSetChanged();
								if (mCurPage > 1) {
									Toast.makeText(MyDriverActivity.this,
											"刷新成功", Toast.LENGTH_SHORT).show();
									mListView.onRefreshFinish();
								} else {
									mListView.onRefreshFinish();
									update(UPDATE_SUCCESS);
								}*/
								/**
								 * 插入到数据库
								 */
								DriverTable.getInstance().insert(data);
							} else {
								if (mCurPage > 1) {
									Toast.makeText(MyDriverActivity.this,
											"刷新成功", Toast.LENGTH_SHORT).show();
									mCurPage--;
									mListView.onRefreshFinish();
								} else {
									mListView.onRefreshFinish();
									mTotalDrivers.clear();
									mMyDrivers.clear();
									mAdapter.notifyDataSetChanged();
									update(UPDATE_EMPTY);
								}
							}
						} else {
							if (mCurPage > 1) {
								Toast.makeText(MyDriverActivity.this, "刷新失败",
										Toast.LENGTH_SHORT).show();
								mCurPage--;
								mListView.onRefreshFinish();
							} else {
								mListView.onRefreshFinish();
								mTotalDrivers.clear();
								mMyDrivers.clear();
								mAdapter.notifyDataSetChanged();
								update(UPDATE_FAILED);
							}
						}
					}
				});
	}

	protected void onRefreshMore(int pageindex) {
		DeliveryBusAPI.getInstance().getDriverDataList(mCurStatus, null, pageindex,
				PAGESIZE, new IMtqDriverDataListener() {

					@Override
					public void onResult(int errCode, List<MtqDriver> data,
							int total) {
						if (ProgressDialog.isShowProgress()) {
							ProgressDialog.cancelProgress();
						}
						
						/**
						 * 帐号被挤出
						 */
						if (errCode == 1008) {
							HandleErrManager.getInstance().handleErrCode(
									errCode);
							return;
						}

						if (errCode == 0) {
							mTotal = total;
							Toast.makeText(MyDriverActivity.this, "刷新成功",
									Toast.LENGTH_SHORT).show();
							mListView.onRefreshFinish();
							if (data != null && !data.isEmpty()) {
								if (mCurPage == 1) {
									mTotalDrivers.clear();
									mMyDrivers.clear();
								}
								mTotalDrivers.addAll(0, data);
								
								/********************根据邀请状态筛选**************//*
								List<MtqDriver> list = new ArrayList<MtqDriver>();
								list.clear();
								if (mCurStatus == 0) {
									list = data;
								} else {
									for (MtqDriver driver : data) {
										if (mCurStatus == driver.invitestatus) {
											list.add(driver);
										}
									}
								}
								*//*********************根据邀请状态筛选************/
								
								mMyDrivers.addAll(0, data);
								mAdapter.notifyDataSetChanged();
								/**
								 * 插入到数据库
								 */
								DriverTable.getInstance().insert(data);
							} else {
								if (mCurPage > 1) {
									mCurPage--;
								}
							}
						} else {
							Toast.makeText(MyDriverActivity.this, "刷新失败",
									Toast.LENGTH_SHORT).show();
							mListView.onRefreshFinish();
							if (mCurPage > 1) {
								mCurPage--;
							}
						}
					}
				});
	}

	protected void update(int type) {
		switch (type) {
		case UPDATE_LOADING: {
			mEmpty.setVisibility(View.GONE);
			mFailed.setVisibility(View.GONE);
			mNetAbnormal.setVisibility(View.GONE);
			mLoading.setVisibility(View.VISIBLE);
			mLayout.setVisibility(View.GONE);
			break;
		}
		case UPDATE_NET_ABNORMAL: {
			mEmpty.setVisibility(View.GONE);
			mFailed.setVisibility(View.GONE);
			mNetAbnormal.setVisibility(View.VISIBLE);
			mLoading.setVisibility(View.GONE);
			mLayout.setVisibility(View.GONE);
			break;
		}
		case UPDATE_SUCCESS: {
			mEmpty.setVisibility(View.GONE);
			mFailed.setVisibility(View.GONE);
			mNetAbnormal.setVisibility(View.GONE);
			mLoading.setVisibility(View.GONE);
			mLayout.setVisibility(View.VISIBLE);
			mSearchLayout.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.VISIBLE);
			break;
		}
		case UPDATE_FAILED: {
			mEmpty.setVisibility(View.GONE);
			mFailed.setVisibility(View.VISIBLE);
			mNetAbnormal.setVisibility(View.GONE);
			mLoading.setVisibility(View.GONE);
			mLayout.setVisibility(View.GONE);
			break;
		}
		case UPDATE_EMPTY: {
			mListView.setEmptyView(mEmpty);
			mEmpty.setVisibility(View.VISIBLE);
			mFailed.setVisibility(View.GONE);
			mNetAbnormal.setVisibility(View.GONE);
			mLoading.setVisibility(View.GONE);
			mLayout.setVisibility(View.VISIBLE);
			mSearchLayout.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			break;
		}
		default:
			break;
		}
	}

	@Subscribe(threadMode = ThreadMode.MainThread)
	public void onMessageEvent(BaseEvent event) {
		switch (event.msgId) {
		default:
			break;
		}
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		search(s.toString());
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@SuppressLint("DefaultLocale")
	private void search(String string) {
		List<MtqDriver> list = new ArrayList<MtqDriver>();
		list.clear();
		if (TextUtils.isEmpty(string)) {
			if (mCurStatus == 0) {
				list = mTotalDrivers;
			} else {
				for (MtqDriver driver : mTotalDrivers) {
					if (driver.invitestatus == mCurStatus) {
						list.add(driver);
					}
				}
			}
		} else {
			for (MtqDriver driver : mTotalDrivers) {
				String driver_name = driver.driver_name;
				/**
				 * 忽略大小写
				 */
				if (driver_name.toUpperCase().indexOf(
						string.toString().toUpperCase()) != -1) {
					if (mCurStatus == 0) {
						list.add(driver);
					} else {
						if (driver.invitestatus == mCurStatus) {
							list.add(driver);
						}
					}
				}
			}
		}
		mListView.setEmptyView(null);
		mMyDrivers.clear();
		mMyDrivers.addAll(list);
		mAdapter.notifyDataSetChanged();
	}

	private void filterByStatus(int invitestatus) {
		if (invitestatus != mCurStatus) {
			mCurStatus = invitestatus;
			mCurPage = 1;
			
			String str = getResources().getString(R.string.tip_refreshing);
			ProgressDialog.showProgress(this, str, null);
			
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					getDriverDataList(mCurPage);
				}
			}, 500);
			
			/*mCurStatus = invitestatus;
			String status = DriverUtils.getInviteStatus(invitestatus);
			mRight.setText(status);

			List<MtqDriver> list = new ArrayList<MtqDriver>();
			list.clear();
			if (invitestatus == 0) {
				list = mTotalDrivers;
			} else {
				for (MtqDriver driver : mTotalDrivers) {
					if (invitestatus == driver.invitestatus) {
						list.add(driver);
					}
				}
			}
			mMyDrivers.clear();
			mMyDrivers.addAll(list);
			
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if (ProgressDialog.isShowProgress()) {
						ProgressDialog.cancelProgress();
					}
					mAdapter.notifyDataSetChanged();
				}
			}, 500);*/
		}
	}
}
