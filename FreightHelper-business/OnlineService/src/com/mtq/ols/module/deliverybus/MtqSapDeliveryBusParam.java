package com.mtq.ols.module.deliverybus;

import java.io.Serializable;
import java.util.List;

public class MtqSapDeliveryBusParam {

	/**
	 * ��¼��Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqLogin {
		/** ��¼��ʶ */
		public String sesskey;
		/** ����Ա�û�ID */
		public int admin_id;
		/** ����Ա�û��� */
		public String user_name;
		/** �û����� */
		public String user_alias;
		/** ������֯����ID */
		public int org_id;
		/** ������֯�������� */
		public String org_name;
		/** ������Կ */
		public String token;
	}

	/**
	 * ������Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqGroup {
		/** ����ID */
		public int group_id;
		/** �������� */
		public String group_name;
	}

	/**
	 * ����״̬
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqCarState implements Serializable {
	
		private static final long serialVersionUID = -351130022385804944L;
		/** ����ID */
		public int carid;
		/** ���ƺ��� */
		public String carlicense;
		/** ����״̬(1���У�2���ɳ���3��ҵ�У�20Ϊά�ޱ���) */
		public int carstatus;
		/** �����λ����X */
		public int x;
		/** �����λ����Y */
		public int y;
		/** �����λʱ�� */
		public int gpstime;
		/** ������̣��ף� */
		public int mileage;
	}

	/**
	 * ����״̬ͳ��
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqCarStateCount {
		/** ���߳����� */
		public int onlines;
		/** ��������̣���� */
		public float mileage;
	}

	/**
	 * ����ʵʱ����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqCar {
		/** ���ƺ��� */
		public String carlicense;
		/** ����״̬(1���У�2���ɳ���3��ҵ�У�20Ϊά�ޱ���) */
		public int carstatus;
		/** ������̣��ף� */
		public int mileage;
		/** �����λ����X */
		public int x;
		/** �����λ����Y */
		public int y;
		/** �����λʱ�� */
		public int gpstime;
		/** ��˾��ID */
		public int mdriverid;
		/** ��˾������ */
		public String mdriver;
		/** ��˾���ֻ��� */
		public String mphone;
		/** ��˾��ID */
		public int sdriverid;
		/** ��˾������ */
		public String sdriver;
		/** ��˾���ֻ��� */
		public String sphone;
	}

	/**
	 * ����״̬����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqState {
		/** ��������ʱ�䣨����ʻ������Ϣ���� */
		public int time;
		/** ����ʣ��ȼ�ͣ�%����ЧΪ-512������ʻ������Ϣ���� */
		public int surplusoil;
		/** ��ƿ��ѹ��������ЧΪ-512������ʻ������Ϣ���� */
		public float batteryvoltage;
		/** ������ת�٣�RPM����ЧΪ-512������ʻ������Ϣ���� */
		public int enginespeed;
		/** �����ٶȣ�����/ʱ����ЧΪ-512������ʻ������Ϣ���� */
		public int carspeed;
		/** ��������ȴҺ�¶ȣ����϶ȣ���ЧΪ-512������ʻ������Ϣ���� */
		public int enginecoolcent;
		/** �����ۼ���ʻ����̣��������ʻ������Ϣ���� */
		public int mileage;
		/** ˲ʱ�ͺģ�L/100KM����ЧΪ-512������ʻ��չ��Ϣ���� */
		public float instantfuel;
		/** �¶ȱ������ޣ��������豸�����ò����� */
		public int maxtempalarm;
		/** �¶ȱ������ޣ��������豸�����ò����� */
		public int mintempalarm;
		/** ������1�¶ȣ��ȣ���ЧΪ-512�����¶ȴ�����Ϣ���� */
		public int temperature1;
		/** ������2�¶ȣ��ȣ���ЧΪ-512�����¶ȴ�����Ϣ���� */
		public int temperature2;
		/** ������3�¶ȣ��ȣ���ЧΪ-512�����¶ȴ�����Ϣ���� */
		public int temperature3;
	}

	/**
	 * ���������˵�����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqTaskStore {
		/** �ͻ����� */
		public String cut_orderid;
		/**
		 * ����״̬(0���ύ��1���ύ��20�Ѽƻ���21�ѷ�����30���������ҵ�У���
		 * 31����У���ҵ�У���40�ͻ��У���ҵ�У���50����ɣ�51��ȡ��)
		 */
		public int orderstatus;
		/** �쳣״̬��0���쳣��1���쳣<���ɼ������>��2 ���쳣<�ɼ�����ɣ������������ɾ��>�� */
		public int abnormal_status;
		/** �����˵��� */
		public String send_regionname;
		/** �����˵�ַ */
		public String send_address;
		/** �ջ��˵��� */
		public String receive_regionname;
		/** �ջ��˵�ַ */
		public String receive_address;
		/** Ҫ�����/�ʹ�ʱ�� */
		public int receive_date;
		/** �������������֣� */
		public float goods_weight;
		/** ��������������� */
		public float goods_volume;
		/** ˾������ */
		public String driver_name;
		/** ˾���ֻ��� */
		public String driver_phone;
	}

	/**
	 * ���������г�����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqTaskNavi {
		/** ���ʱ�� */
		public int starttime;
		/** Ϩ��ʱ�� */
		public int endtime;
		/** �����г������ */
		public float mileage;
		/** �����г���ʱ�� */
		public int traveltime;
	}

	/**
	 * ��ʷ�켣
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqTrackHistory {
		/** �豸ID */
		public long duid;
		/** ���ʱ�� */
		public int starttime;
		/** Ϩ��ʱ�� */
		public int endtime;
		/** �����г�����̣���λ��ǧ�� */
		public float mileage;
		/** ���ݵ� */
		public List<MtqPosData> pos_data;
	}

	/**
	 * ���ݵ�
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqPosData {
		/** X���� */
		public int x;
		/** Y���� */
		public int y;
		/** ��ʱ�ٶȣ�km/h�� */
		public int speed;
		/** ���������� */
		public int direction;
		/** �ϱ�UTCʱ�� */
		public int time;
		/** ������:-1����·�����;0���켣��;1����� ;2���յ�;3��ͣ���� */
		public int pos_type;
		/** ͣ��ʱ�������ӣ� */
		public int park_time;
	}

	/**
	 * ������Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqMsgAlarm implements Serializable {

		private static final long serialVersionUID = -644825787180782175L;
		/** ��¼ID */
		public String id;
		/** ����ID */
		public int carid;
		/** ���ƺ��� */
		public String carlicense;
		/** ���ȣ�������ǧ��֮һ�����꣩ */
		public int x;
		/** γ�ȣ�������ǧ��֮һ�����꣩ */
		public int y;
		/** GPS�ٶȣ�����/ʱ�� */
		public int speed;
		/** GPS���򣨶ȣ� */
		public int direction;
		/** ����ID�� */
		public int alarmid;
		/** �����¼���ϸ */
		public String eventjson;
		/** ����ʱ�䣨UTCʱ�䣩 */
		public int locattime;
		/** ˾����������ǰ˾���� */
		public String driver_name;
		/** ˾���ֻ��ţ���ǰ˾���� */
		public String driver_phone;
		/** ��˾������ */
		public String mdriver;
		/** ��˾���ֻ��� */
		public String mphone;
		/** ��˾������ */
		public String sdriver;
		/** ��˾���ֻ��� */
		public String sphone;
		/** �Ķ�״̬��0δ��1�Ѷ��� */
		public int readstatus;
		
		public int alarmType;  
	}

	/**
	 * ϵͳ��Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqMsgSys implements Serializable {
		
		private static final long serialVersionUID = -4067706869512346337L;
		/** ��¼ID */
		public int serialid;
		/** ��Ϣʱ�䣨UTCʱ�䣩 */
		public int time;
		/** ���� */
		public String title;
		/** ���� */
		public String content;
		/** ������ */
		public String publisher;
		/** �Ķ�״̬��0δ��1�Ѷ��� */
		public int readstatus;
	}

	/**
	 * ��������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqCarData {
		/** ����ID */
		public int carid;
		/** ���ƺ��� */
		public String carlicense;
		/** ������ԴID��1-���г��� 2-��ᳵ���� */
		public int sourceid;
		/** ��ǰ˾������ */
		public String driver;
		/** ��ǰ˾���ֻ����� */
		public String phone;
	}

	/**
	 * ������������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqCarDataDetail {
		/** ���ƺ��� */
		public String carlicense;
		/** ������ԴID��1-���г��� 2-��ᳵ���� */
		public int sourceid;
		/** ��˾������ */
		public String mdriver;
		/** ��˾���ֻ��� */
		public String mphone;
		/** ��˾������ */
		public String sdriver;
		/** ��˾���ֻ��� */
		public String sphone;
		/** �������ͣ���ʻ֤�ϵĳ������ͣ� */
		public String carmodel;
		/** ���ͷ��ࣨ1-΢�� 2-���� 3-���� 4-���ͣ� */
		public int cartype;
		/** �豸���ͣ�0Ϊ�ް��豸��2Ϊ����˫ģһ�����3Ϊ������KPND��4ΪTD-BOX��5ΪTD-PND�� */
		public int dtype;
		/** �豸�������� */
		public String dtypename;
		/** �豸�ͺ� */
		public String model;
		/** �ն��豸�� */
		public String idsn;
		/** SIM���� */
		public String idphone;
	}

	/**
	 * ˾������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqDriver {
		/** ˾��ID */
		public int driverid;
		/** ˾������ */
		public String driver_name;
		/** ˾���ֻ����� */
		public String phone;
		/** ����״̬��1δ��������Ϣ��2�Ѷ�������Ϣ��3ͬ����복�ӣ�4�ܾ����복�ӣ�5���˳�����) */
		public int invitestatus;
	}

	/**
	 * ˾����������
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqDriverDetail {
		/** ˾������ */
		public String driver_name;
		/** ˾���ֻ����� */
		public String phone;
		/** ����״̬��1δ��������Ϣ��2�Ѷ�������Ϣ��3ͬ����복�ӣ�4�ܾ����복�ӣ�5���˳�����) */
		public int invitestatus;
		/** ˾�����֤���� */
		public String id_number;
		/** ������ϵ��1 */
		public String emergency_man1;
		/** ������ϵ�˵绰1 */
		public String emergency_phone1;
		/** ������ϵ��2 */
		public String emergency_man2;
		/** ������ϵ�˵绰1 */
		public String emergency_phone2;
	}

	/**
	 * �˵�ͳ�ƿ���
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqOrderCount {
		/** �˵����� */
		public int total;
		/** ������ */
		public int notsend;
		/** ������ */
		public int notbegin;
		/** ������ */
		public int transport;
		/** ��ǩ�� */
		public int finished;
		/** �쳣�� */
		public int abnormal;
	}

	/**
	 * ����ƻ�ͳ�ƿ���
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static class MtqTaskCount {
		/** �˵����� */
		public int total;
		/** �������� */
		public int normal;
		/** ��㷢�� */
		public int late;
		/** �ȴ����� */
		public int wait;
		/** Ԥ������ */
		public int estidelay;
	}

	/**
	 * Ӳ���豸����
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static class MtqDeviceDType {
		/** �豸���ͣ�2Ϊ����˫ģһ�����3Ϊ������KPND��4ΪTD-BOX��5ΪTD-PND�� */
		public int dtype;
		/** �豸���� */
		public String dname;
	}
}
