package com.mtq.ols.module.deliverybus;

import java.util.List;

import com.mtq.ols.module.delivery.tool.CldKBaseParse.ProtBase;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCar;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarData;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarDataDetail;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarState;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqCarStateCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqDeviceDType;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqDriver;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqDriverDetail;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqGroup;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqLogin;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqMsgAlarm;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqMsgSys;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqOrderCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqState;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTaskCount;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTaskNavi;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTaskStore;
import com.mtq.ols.module.deliverybus.MtqSapDeliveryBusParam.MtqTrackHistory;

public class MtqSapDeliveryBusParse {
	
	
	/*public static class ProtBase extends ProtBase{
		*//** ϵͳʱ�䣨UTCʱ�䣩 *//*
		public int systime;
	}*/

	/**
	 * ��¼��Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtLogin extends ProtBase {
		public List<String> actions;
		public MtqLogin login;
		public List<MtqGroup> groups;
	}

	/**
	 * ����״̬
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtCarStateList extends ProtBase {
		public int pageindex;
		public int total;
		public List<MtqCarState> data;
	}

	/**
	 * ����״̬ͳ��
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtCarStateCount extends ProtBase {
		public MtqCarStateCount data;
	}

	/**
	 * ����ʵʱ״̬����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtCarState extends ProtBase {
		public MtqCar car;
		public MtqState state;
	}

	/**
	 * ���������˵�����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtTaskStore extends ProtBase {
		public int pageindex;
		public int total;
		public List<MtqTaskStore> data;
	}

	/**
	 * ���������˵�����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtTaskNavi extends ProtBase {
		public int pageindex;
		public int total;
		public List<MtqTaskNavi> data;
	}

	/**
	 * ��ʷ�켣
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtTrackHistory extends ProtBase {
		public int alarmnum;
		public List<MtqTrackHistory> data;
	}

	/**
	 * ������Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtMsgAlarm extends ProtBase {
		public int pageindex;
		public String incrindex;
		public int total;
		public List<MtqMsgAlarm> data;
	}

	/**
	 * ϵͳ��Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtMsgSys extends ProtBase {
		public int pageindex;
		public String incrindex;
		public int total;
		public List<MtqMsgSys> data;
	}

	/**
	 * ��������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtCarData extends ProtBase {
		public int pageindex;
		public int total;
		public List<MtqCarData> data;
	}

	/**
	 * ������������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtCarDataDetail extends ProtBase {
		public int pageindex;
		public int total;
		public MtqCarDataDetail car;
		public List<MtqGroup> groups;
	}

	/**
	 * ˾������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtDriverData extends ProtBase {
		public int pageindex;
		public int total;
		public List<MtqDriver> data;
	}

	/**
	 * ˾����������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtDriverDetail extends ProtBase {
		public MtqDriverDetail data;
	}
	
	/**
	 * ����˾�����복��
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtInviteDriver extends ProtBase {
		public int invitestatus;
	}

	/**
	 * �˵�ͳ�ƿ���
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtOrderCount extends ProtBase {
		public MtqOrderCount data;
	}

	/**
	 * ����ƻ�ͳ�ƿ���
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtTaskCount extends ProtBase {
		public MtqTaskCount data;
	}
	
	/**
	 * �ϴ�������Ƭ�ļ�
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class ProtUploadAttachPic extends ProtBase {
		public String mediaid;
	}

	/**
	 * Ӳ���豸����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static class ProtDeviceDType extends ProtBase {
		public List<MtqDeviceDType> data;
	}
}
