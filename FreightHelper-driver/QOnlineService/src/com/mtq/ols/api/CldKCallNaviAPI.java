/*
 * @Title CldKCallNaviAPI.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.api;

import java.util.List;
import com.mtq.ols.api.CldOlsInit.ICldOlsInitListener;
import com.mtq.ols.bll.CldKCallNavi;
import com.mtq.ols.bll.CldKMessage;
import com.mtq.ols.dal.CldDalKCallNavi;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareAKeyCallParm;
import com.mtq.ols.tools.CldSapReturn;



/**
 * һ��ͨ���ģ�飬�ṩһ��ͨ��ع���
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:18:44
 */
public class CldKCallNaviAPI {
	/** һ��ͨ�ص���������ʼ��ʱ����һ�� */
	private ICldKCallNaviListener listener;

	private static CldKCallNaviAPI cldKCallNaviAPI;

	private CldKCallNaviAPI() {

	}

	public static CldKCallNaviAPI getInstance() {
		if (null == cldKCallNaviAPI) {
			cldKCallNaviAPI = new CldKCallNaviAPI();
		}
		return cldKCallNaviAPI;
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2015-2-12 ����6:05:49
	 */
	public void initKey(final ICldOlsInitListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CldKCallNavi.getInstance().initKey();
				if (null != listener) {
					listener.onInitReslut();
				}
			}
		}).start();
	}

	/**
	 * һ��ͨ��ʼ��(��½�����)
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:19:06
	 */
	public void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldKCallNavi.getInstance().getBindMobile();
			}
		}).start();
	}

	/**
	 * ����ʼ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:19:19
	 */
	public void uninit() {

	}

	/**
	 * ����һ��ͨ�ص�����
	 * 
	 * @param listener
	 *            һ��ͨ�ص�
	 * @return (0 ���óɹ���-1��������ʧ��)
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:19:30
	 */
	public int setCldKCallNaviListener(ICldKCallNaviListener listener) {
		if (null == this.listener) {
			this.listener = listener;
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * ��ȡ���ֻ�����
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:19:45
	 */
	public void getBindMobile() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKCallNavi.getInstance()
						.getBindMobile();
				if (null != listener) {
					listener.onGetBindMobileResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡһ��ͨ�ֻ���֤��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:19:54
	 */
	public void getIdentifycode(final String mobile) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKCallNavi.getInstance()
						.getIdentifycode(mobile);
				if (null != listener) {
					listener.onGetIdentifycodeResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * �������ֻ��Žӿ�
	 * 
	 * @param identifycode
	 *            ��֤��
	 * @param mobile
	 *            �ֻ�����
	 * @param replacemobile
	 *            ��Ҫ�滻���ľɺ��� ����Ϊ�����滻��Ӧ���룻Ϊ��������mobile
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:20:07
	 */
	public void bindToMobile(final String identifycode, final String mobile,
			final String replacemobile) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKCallNavi.getInstance().bindToMobile(
						identifycode, mobile, replacemobile);
				if (null != listener) {
					listener.onBindToMobileResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * �ϱ�λ��
	 * 
	 * @param mobile
	 *            �ֻ���,��Ϊ�գ���kuid�󶨵������ֻ��Ŷ��ϱ�λ��
	 * @param longitude
	 *            WGS84 ���ȣ���λ����
	 * @param latitude
	 *            WGS84 γ�ȣ���λ����
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:20:23
	 */
	public void upLocation(final String mobile, final double longitude,
			final double latitude) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKMessage.getInstance().upLocation(
						mobile, longitude, latitude);
				if (null != listener) {
					listener.onUpLocationResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡһ��ͨ��Ϣ
	 * 
	 * @param mobile
	 *            �ֻ��ţ���������ֻ��ţ���ֻ���ѯָ�����ֻ���,������ѭ�����ֻ���
	 * @param longitude
	 *            WGS84 ���ȣ���λ����
	 * @param latitude
	 *            WGS84 γ�ȣ���λ����
	 * @param isLoop
	 *            �Ƿ���ѯ����ֻ���
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:20:39
	 */
	public void recPptMsg(final String mobile, final double longitude,
			final double latitude, final boolean isLoop) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKMessage.getInstance().recPptMsg(
						mobile, longitude, latitude, isLoop);
				if (null != listener) {
					listener.onRecPptMsgResult(errRes.errCode, CldDalKCallNavi
							.getInstance().getMsgList());
				}
			}
		}).start();
	}

	/**
	 * ע��һ��ͨ�ʺ�
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:20:53
	 */
	public void registerByKuid() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKCallNavi.getInstance()
						.registerByKuid();
				if (null != listener) {
					listener.onRegisterResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ɾ�����ֻ���
	 * 
	 * @param mobile
	 *            �ֻ�����
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:21:03
	 */
	public void delBindMobile(final String mobile) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKCallNavi.getInstance().delBindMobile(
						mobile);
				if (null != listener) {
					listener.onDelMobileResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ��ȡ�󶨵��ֻ���
	 * 
	 * @return
	 * @return String ֮ǰ���ù���ȡ���ֻ������򷵻�֮ǰ�ĺ��룬���򷵻�null �ٵ��û�ȡ���ֻ��Žӿ�
	 * @author Zhouls
	 * @date 2015-3-5 ����3:21:17
	 */
	public String getPptMobile() {
		if (CldDalKCallNavi.getInstance().getMobiles().size() > 0) {
			return CldDalKCallNavi.getInstance().getMobiles().get(0);
		} else {
			return null;
		}
	}

	/**
	 * ��ȡ�󶨵��ֻ����б�
	 * 
	 * @return
	 * @return List<String>
	 * @author Zhouls
	 * @date 2015-3-5 ����3:21:37
	 */
	public List<String> getPptMobileList() {
		return CldDalKCallNavi.getInstance().getMobiles();
	}

	/**
	 * ���°󶨵��ֻ���
	 * 
	 * @param mobiles
	 *            ���µ��ֻ���
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:21:49
	 */
	public void updateBindMobile(List<String> mobiles) {
		CldDalKCallNavi.getInstance().setMobiles(mobiles);
	}

	/**
	 * һ��ͨ�ص�����
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:22:04
	 */
	public static interface ICldKCallNaviListener {

		/**
		 * ע��һ��ͨ�ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:22:16
		 */
		public void onRegisterResult(int errCode);

		/**
		 * ��ȡ���ֻ�����ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:22:29
		 */
		public void onGetBindMobileResult(int errCode);

		/**
		 * ��ȡһ��ͨ�ֻ���֤��ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:22:38
		 */
		public void onGetIdentifycodeResult(int errCode);

		/**
		 * �������ֻ��Żص�
		 * 
		 * @param errCode
		 *            0�ɹ� 405��֤�����
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:22:47
		 */
		public void onBindToMobileResult(int errCode);

		/**
		 * ɾ���ֻ��Żص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:23:10
		 */
		public void onDelMobileResult(int errCode);

		/**
		 * �ϱ�λ�ûص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:23:19
		 */
		public void onUpLocationResult(int errCode);

		/**
		 * ����һ��ͨ��Ϣ�ص�
		 * 
		 * @param errCode
		 * @param list
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:23:29
		 */
		public void onRecPptMsgResult(int errCode, List<ShareAKeyCallParm> list);
	}
}
