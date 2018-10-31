/*
 * @Title CldKMessageAPI.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.api;

import java.util.ArrayList;
import java.util.List;
import com.mtq.ols.api.CldOlsInit.ICldOlsInitListener;
import com.mtq.ols.bll.CldKMessage;
import com.mtq.ols.dal.CldDalKMessage;
import com.mtq.ols.sap.bean.CldSapKMParm;
import com.mtq.ols.sap.bean.CldSapKMParm.SharePoiParm;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareRouteParm;
import com.mtq.ols.tools.CldSapReturn;



/**
 * ��Ϣ���ģ�飬�ṩ������Ϣ�ȹ���
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:26:30
 */
public class CldKMessageAPI {

	/** ��Ϣϵͳ�ص���������ʼ��ʱ����һ�� */
	private ICldKMessageListener listener;

	private static CldKMessageAPI cldKMessageAPI;

	private CldKMessageAPI() {

	}

	public static CldKMessageAPI getInstance() {
		if (null == cldKMessageAPI) {
			cldKMessageAPI = new CldKMessageAPI();
		}
		return cldKMessageAPI;
	}

	/**
	 * ��ʼ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:26:47
	 */
	public void init() {

	}

	/**
	 * ����ʼ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:26:57
	 */
	public void uninit() {

	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @param listener
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:27:08
	 */
	public void initKey(final ICldOlsInitListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CldKMessage.getInstance().initKey();
				if (null != listener) {
					listener.onInitReslut();
				}
			}
		}).start();
	}

	/**
	 * ������Ϣϵͳ�ص�
	 * 
	 * @param listener
	 *            ��Ϣϵͳ�ص�����
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:27:18
	 */
	public int setCldKMessageListener(ICldKMessageListener listener) {
		if (null == this.listener) {
			this.listener = listener;
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * �����Ʒ���POI��Ϣ
	 * 
	 * @param poi
	 *            ����POI��Ϣ
	 * @param targetType
	 *            �����������Ǳ��ˣ�0���Լ� ��1�����ˣ�
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:27:30
	 */
	public void sendSharePoiMsg(final SharePoiParm poi, final int targetType) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKMessage.getInstance()
						.sendSharePoiMsg(poi, targetType);
				if (null != listener) {
					listener.onSendPoiResult(errRes.errCode);
				}
			}
		}).start();

	}

	/**
	 * �����Ʒ���·����Ϣ
	 * 
	 * @param route
	 *            �����·����Ϣ
	 * @param targetType
	 *            �����������Ǳ��ˣ�0���Լ� ��1�����ˣ�
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:27:54
	 */
	public void sendShareRouteMsg(final ShareRouteParm route,
			final int targetType) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldSapReturn errRes = CldKMessage.getInstance()
						.sendShareRouteMsg(route, targetType);
				if (null != listener) {
					listener.onSendRouteResult(errRes.errCode);
				}
			}
		}).start();
	}

	/**
	 * ����������Ϣ��ʷ
	 * 
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:28:11
	 */
	public void recLastestMsgHistory(final String messagetype) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<CldSapKMParm> list = new ArrayList<CldSapKMParm>();
				CldSapReturn errRes = CldKMessage.getInstance()
						.recLastestMsgHistory(messagetype, list);
				int maxlength = 0;
				if (errRes.errCode == 0) {
					maxlength = CldDalKMessage.getInstance().getMaxlength();
				}
				if (null != listener) {
					listener.onRecLastestMsgHistoryResult(errRes.errCode,
							maxlength, list, messagetype);
				}
			}
		}).start();
	}

	/**
	 * ������ʷ����Ϣ������ˢ�£�
	 * 
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param offset
	 *            ҳ�룬��0��ʼ
	 * @param length
	 *            ÿ�������¼��
	 * @param lastid
	 *            ���һ������Ϣid
	 * @param lasttime
	 *            ���һ������Ϣʱ��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:28:31
	 */
	public void recNewMsgHistory(final String messagetype, final int offset,
			final int length, final long lastid, final long lasttime) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<CldSapKMParm> list = new ArrayList<CldSapKMParm>();
				CldSapReturn errRes = CldKMessage.getInstance()
						.recNewMsgHistory(messagetype, offset, length, lastid,
								lasttime, list);
				if (null != listener) {
					listener.onRecNewMsgHistoryResult(errRes.errCode, list);
				}
			}
		}).start();
	}

	/**
	 * ������ʷ����Ϣ��ѯ�ӿڣ�����ˢ�£�
	 * 
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param offset
	 *            ҳ�룬��0��ʼ
	 * @param length
	 *            ÿ�������¼��
	 * @param lastid
	 *            ���һ������Ϣid
	 * @param lasttime
	 *            ���һ������Ϣʱ��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:29:21
	 */
	public void recOldMsgHistory(final String messagetype, final int offset,
			final int length, final int lastid, final long lasttime) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<CldSapKMParm> list = new ArrayList<CldSapKMParm>();
				CldSapReturn errRes = CldKMessage.getInstance()
						.recOldMsgHistory(messagetype, offset, length, lastid,
								lasttime, list);
				if (null != listener) {
					listener.onRecOldMsgHistoryResult(errRes.errCode, list);
				}
			}
		}).start();
	}

	/**
	 * ������Ϣ�Ķ�״̬
	 * 
	 * @param list
	 *            Str="msgid,createtime,createtype"
	 *            List��Ϣ����Ϣmsgid,createtime,createtype ��ɵ��ַ����б�
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:29:38
	 */
	public void upMsgReadStatus(final List<String> list) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CldSapReturn errRes = CldKMessage.getInstance()
						.upMsgReadStatus(list);
				if (null != listener) {
					listener.onUpMsgReadStatusResult(errRes.errCode, list);
				}
			}
		}).start();
	}

	/**
	 * �����ʵ��б�
	 * 
	 * @param isInit
	 *            �Ƿ��ǵ�һ����ȡ�ʵ��б���ʱ���и�����false
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:29:52
	 */
	public void dropAreaEggs(final boolean isInit) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CldKMessage.getInstance().getAreaList(isInit);
			}
		}).start();
	}

	/**
	 * �ж��Ƿ�ȵ���
	 * 
	 * @param x
	 *            ��ǰ����������X
	 * @param y
	 *            ��ǰ����������Y
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-3-5 ����3:30:06
	 */
	public boolean isInEggsArea(long x, long y) {
		return CldKMessage.getInstance().isInEggsArea(x, y);
	}

	/**
	 * ���ղʵ���Ϣ�ص�����
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:30:26
	 */
	public interface IRecEggMsg {

		/**
		 * ���ղʵ���Ϣ�ص�
		 * 
		 * @param errCode
		 *            the err code
		 * @param list
		 *            ���յ�����Ϣ�б�
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:30:35
		 */
		public void onRecEggsMsg(int errCode, List<CldSapKMParm> list);
	}

	/**
	 * ���ղʵ���Ϣ
	 * 
	 * @param regionCode
	 *            ����������
	 * @param x
	 *            ��ǰ����x
	 * @param y
	 *            ��ǰ����y
	 * @param eggListener
	 *            �ʵ���Ϣ�ص�
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:30:50
	 */
	public void recEggsMsg(final int regionCode, final long x, final long y,
			final IRecEggMsg eggListener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<CldSapKMParm> list = new ArrayList<CldSapKMParm>();
				CldSapReturn errRes = CldKMessage.getInstance().recShareMsg(
						regionCode, x, y, -1, list);
				if (null != eggListener) {
					eggListener.onRecEggsMsg(errRes.errCode, list);
				}
			}
		}).start();
	}

	/**
	 * ��Ϣϵͳ�ص�����
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:31:24
	 */
	public static interface ICldKMessageListener {

		/**
		 * �����Ʒ���POI��Ϣ�ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:33:10
		 */
		public void onSendPoiResult(int errCode);

		/**
		 * �����Ʒ���Route��Ϣ�ص�
		 * 
		 * @param errCode
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:33:24
		 */
		public void onSendRouteResult(int errCode);

		/**
		 * ����������Ϣ��ʷ�ص�
		 * 
		 * @param errCode
		 * @param maxlength
		 *            the maxlength
		 * @param list
		 *            ��Ϣ�б�
		 * @param Tag
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:33:32
		 */
		public void onRecLastestMsgHistoryResult(int errCode, int maxlength,
				List<CldSapKMParm> list, String Tag);

		/**
		 * ������ʷ����Ϣ�ص�������ˢ�£�
		 * 
		 * @param errCode
		 * @param list
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:34:18
		 */
		public void onRecNewMsgHistoryResult(int errCode,
				List<CldSapKMParm> list);

		/**
		 * ������ʷ����Ϣ�ص�������ˢ�£�
		 * 
		 * @param errCode
		 * @param list
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:34:37
		 */
		public void onRecOldMsgHistoryResult(int errCode,
				List<CldSapKMParm> list);

		/**
		 * ��Ϣ�Ķ�״̬�ش��ص�
		 * 
		 * @param errCode
		 * @param list
		 *            �����ƴ�Ӵ�
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-5 ����3:34:47
		 */
		public void onUpMsgReadStatusResult(int errCode, List<String> list);

	}
}
