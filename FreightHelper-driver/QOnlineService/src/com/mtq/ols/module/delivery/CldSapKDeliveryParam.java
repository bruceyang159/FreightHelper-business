/*
 * @Title CldMonitorAuth.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-12-9 ����10:03:00
 * @version 1.0
 */
package com.mtq.ols.module.delivery;

import java.io.Serializable;
import java.util.List;

import com.cld.db.annotation.Column;
import com.cld.db.annotation.Id;
import com.cld.db.annotation.NoAutoIncrement;
import com.mtq.ols.module.delivery.tool.CldShapeCoords;

/**
 * ��Ȩ��Ϣ
 * 
 * @author Zhouls
 * @date 2015-12-9 ����10:03:00
 */
public class CldSapKDeliveryParam {

	/**
	 * 
	 * ��Ȩ�б�
	 * 
	 * @author Zhouls
	 * @date 2015-12-9 ����12:57:11
	 */
	public static class CldMonitorAuth {
		/** ��Ȩ��ʶ */
		public String id;
		/** ��Ȩ�ֻ��� */
		public String mobile;
		/** ��ע */
		public String mark;
		/** ����ʱ�� */
		public long timeOut;
		/** ��Ȩʱ�� */
		public long authTime;
	}

	/**
	 * 
	 * ���ó�ʱʱ��ö��
	 * 
	 * @author Zhouls
	 * @date 2015-12-9 ����11:24:16
	 */
	public static enum CldAuthTimeOut {
		oneday, threeday, sevenday, permanent;
		public static long valueOf(CldAuthTimeOut timeout) {
			switch (timeout) {
			case oneday:
				return 1 * 24 * 60 * 60;
			case threeday:
				return 3 * 24 * 60 * 60;
			case sevenday:
				return 7 * 24 * 60 * 60;
			case permanent:
				// ����20��
				return 20 * 365 * 24 * 60 * 60;
			}
			return 20 * 365 * 24 * 60 * 60;
		}
	}

	/**
	 * ������Ϣ
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����12:15:42
	 */
	public static class CldDeliGroup {
		/** ��ҵID */
		public String corpId;
		/** ��˾���� */
		public String corpName;
		/** ����ID */
		public String groupId;
		/** �������� */
		public String groupName;
		/** ��ϵ�� */
		public String contact;
		/** ��ϵ�绰 */
		public String mobile;
	}

	/**
	 * 
	 * �տ���Ϣ�ϱ�
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����2:58:44
	 */
	public static class CldDeliReceiptParm {
		/** ��ҵID����������������ҵID�� */
		public String corpid;
		/** ��������ID */
		public String taskid;
		/** �ŵ�ID */
		public String storeid;
		/** ���͵��� */
		public String waybill;
		/** �տʽ */
		public String pay_method;
		/** ʵ�ս�� */
		public float real_amount;
		/** �˻�ԭ�� */
		public String return_desc;
		/** �˻���� */
		public float return_amount;
		/** �տע��˵�� */
		public String pay_remark;
		/** ���ӻص� */
		public byte[][] uploadPng;
		/** �ͻ�����**/
		public String cust_orderid ;
		/** �ص�������Ϣ **/
		public String e_receipts_0_ext;

		public CldDeliReceiptParm() {
			pay_method = null;
			real_amount = -1;
			return_desc = null;
			return_amount = -1;
			pay_remark = null;
			uploadPng = null;
			cust_orderid = null;
			e_receipts_0_ext = null;
		}
	}

	/**
	 * 
	 * ��ҵ�ŵ�
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����4:55:25
	 */
	public static class CldDeliStore {
		/** ��ҵID */
		public String corpId;
		/** �ŵ�ID */
		public String storeId;
		/** �ŵ���� */
		public String storeCode;
		/** �ŵ����� */
		public String storeName;
		/** �ŵ��ַ */
		public String storeAddr;
		/** ��ϵ�� */
		public String linkMan;
		/** ��ϵ�˵绰 */
		public String linkPhone;
		/** �ŵ�˵�� */
		public String remark;
		/** λ����Ϣ */
		public CldDeliStorePos pos;
		/** �����Ϣ */
		public CldDeliStoreAuditInfo auditInfo;
	}

	/**
	 * 
	 * ��ҵ�ŵ�λ����Ϣ
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����4:55:39
	 */
	public static class CldDeliStorePos {
		/** �ŵ�λ��x */
		public int x;
		/** �ŵ�λ��y */
		public int y;
		/** ������� */
		public int regionCode;
		/** �������� */
		public String regionName;
		/** λ��k�� */
		public String kCode;
	}

	/**
	 * 
	 * ��ҵ�ŵ������Ϣ
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����4:55:53
	 */
	public static class CldDeliStoreAuditInfo {
		/** ���״̬ */
		public int auditStatus;
		/** ���״̬˵�� */
		public String auditStatusText;
	}

	/**
	 * 
	 * �����ŵ���
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����5:06:15
	 */
	public static class CldDeliSearchStoreResult {
		/** ��ǰҳ */
		public int page;
		/** ��ҳ�� */
		public int pagecount;
		/** �ܼ�¼�� */
		public int record;
		/** �ŵ��б� */
		public List<CldDeliStore> lstOfStores;
	}

	/**
	 * 
	 * ��ע�ŵ�
	 * 
	 * @author Zhouls
	 * @date 2016-4-22 ����6:06:49
	 */
	public static class CldDeliUploadStoreParm {
		/** ��ҵID���ŵ���������ҵID�� */
		public String corpid;
		/** �ϱ����� 1-���� 2-���� */
		public int settype;
		/** �ŵ�ID���ϱ�����Ϊ����ʱΪ0�� */
		public String storeid;
		/** �ŵ����� */
		public String storename;
		/** �ŵ��ַ */
		public String address;
		/** ��ϵ�� */
		public String linkman;
		/** ��ϵ�绰 */
		public String phone;
		/** �ŵ�λ��K�� */
		public String storekcode;
		/** ���䱸ע */
		public String remark;
		/** ��Ƭ */
		public byte[] uploadPng;
		/**���ͣ�3-�ͻ���ַ��1-�������ģ�0-�ŵ꣩**/
		public int iscenter;
	}

	/**
	 * 
	 * �˻���
	 * 
	 * @author Zhouls
	 * @date 2016-4-27 ����4:32:21
	 */
//	public static class CldDeliTask {
//		/** ��������ID */
//		public String taskid;
//		/** �ͻ����� */
//		public long taskdate;
//		/** ����ʱ�� */
//		public long sendtime;
//		/** �˻�״̬��0���˻�1�˻���2�����3��ͣ״̬4��ֹ״̬ �� */
//		public int status;
//		/** ������ҵID */
//		public String corpid;
//		/** ��ҵ���� */
//		public String corpname;
//		/** �˻������� */
//		public int store_count;
//		/** ��������� */
//		public int finish_count;
//		/** �Ѷ�δ��״̬0δ�� 1 �Ѷ� */
//		public int readstatus;
//		/** ���һ�θ���ʱ�� */
//		public long dttime;
//	}
	

	/**
	 * @annotation :�˻�����ϸ
	 * @author : yuyh
	 * @date :2016-9-20����9:04:19
	 * @parama :
	 * @return :
	 **/
	public static class CldDeliTaskOrders implements Serializable
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 3527820282165481608L;
		/** �ͻ�����**/
		public String cust_orderid;
		/** Ҫ���ʹ�ʱ��(��)**/
		public String reqtime;
		/** Ҫ���ʹ�ʱ��(ֹ)**/
		public String reqtime_e;
		/** ������**/
		public String send_name;
		/** �����˵绰**/
		public String send_phone;
		/** �����˵�ַ **/
		public String send_addr;
		/** ������K�� **/
		public String send_kcode;
		/** �ջ���**/
		public String receive_name;
		/** �ջ��˵绰**/
		public String receive_phone;
		/** �ջ��˵�ַ**/
		public String receive_addr;
		/** �ջ���K��**/
		public String receive_kcode;
		/** ������֪**/
		public String note;
		/** ���ϴ���Ƭ�� **/
		public String photo_nums;
		/** ���ϴ��ص��� **/
		public String receipt_nums;
		/** ����**/
		public List<Goods> goods;
	}
	
	/**
	 * @annotation :����
	 * @author : yuyh
	 * @date :2016-9-20����9:14:46
	 * @parama :
	 * @return :
	 **/
	public static class Goods implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6272346263251331927L;
		/**��������**/
		public String name;
		/**��������**/
		public String amount;
		/**��λ**/
		public String unit;
		/**������ **/
		public String pack;
		/**����**/
		public String weight;
		/**���**/
		public String volume;
	}
	
	/**
	 * @annotation :��̨�ĳ�����Ϣ
	 * @author : yuyh
	 * @date :2016-10-28����5:23:14
	 * @parama :
	 * @return :
	 **/
	public static class UMS_Carinfo implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -217515439900500190L;
		/** ���߼��� */
		public String tht;
		/** ������ */
		public String twh;
		/** ���ؼ���*/
		public String twt;
		/** ���ͼ��� */ 
		public String tvt;
		/** ��������*/
		public String tlnt;
	}

	/**
	 * 
	 * �˻���
	 * 
	 * @author Zhouls
	 * @date 2016-4-27 ����4:59:12
	 */
	public static class CldDeliTaskStore implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8158680453468331686L;
		/** ���͵��� */
		public String waybill;
		/** �ŵ�ID */
		public String storeid;
		/** λ��X(����������) */
		public long storex;
		/** λ��Y(����������) */
		public long storey;
		/** �˻������� */
		public String storename;
		/** �˻����ַ */
		public String storeaddr;
		/** �ջ��� */
		public String linkman;
		/** �ջ��˵绰 */
		public String linkphone;
		/** �ͻ�˳�� */
		public int storesort;
		/** ���ʱ�䣨ֻ������ɲ���ֵ */
		public long finishtime;
		/** �ͻ�˵�� */
		public String storemark;
		/** �ͻ�״̬��0-�ȴ��ͻ���1-���������У�2-��������ͣ�3-��ͣ�ͻ� �� */
		public int storestatus;
		/** �������ͣ�1�ͻ�/3���/4�س̣� */
		public int optype;
		/** ͣ��λ��X(����������) */
		public long stopx;
		/** ͣ��λ��Y(����������) */
		public long stopy;
		/** �Ƿ���Ҫ�տ0-����Ҫ��1-��Ҫ�� */
		public int pay_mode;
		/** �տʽ����ѡ����տʽ��û����Ϊ�գ� */
		public String pay_method;
		/** ʵ�ս����յ��Ľ�û����Ϊ0�� */
		public float real_amount;
		/** Ӧ�ս�û����Ϊ0�� */
		public float total_amount;
		/** �˻�ԭ����ѡ����˻�ԭ��û����Ϊ�գ� */
		public String return_desc;
		/** ���һ�θ���ʱ�� */
		public long dttime;
		/** �ͻ�����**/
		public String cust_orderid;
		/** �Ƿ���Ҫ�ص� **/
		public String is_receipt;
		/** �˵��������� **/
		public String assist_url;

		/** �ͻ���ά�� **/
		/** �����ͻ�״̬��0-�ȴ��ͻ���1-���������У�2-��������ͣ�3-��ͣ�ͻ� �� */
		public int local_storestatus;
		/** �������˻���ID **/
		public String taskID;
		/** �տ��˵�� **/
		public String payRemark;
		/** ��ҵID**/
		public String corpId;
		/** �·�ʱ�� **/
		public String sendTime;
		/** ���ɻص�ʱ�� **/
		public String receiptTime;
		/**orders��Ϣ**/
		public CldDeliTaskOrders orders;
	}

	/**
	 * 
	 * �˻�������
	 * 
	 * @author Zhouls
	 * @date 2016-4-28 ����4:26:37
	 */
//	public static class CldDeliTaskDetail implements Serializable{
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = -2706103986984298866L;
//		/** ��������ID */
//		public String taskid;
//		/** �ͻ����� */
//		public String taskdate;
//		/** �ͻ�״̬ */
//		public int status;
//		/** �˻������һ�θ���ʱ�� **/
//		public String ddtime;
//		/** �ͻ���Ա */
//		public String sender;
//		/** �г̾��루�ף� */
//		public int distance;
//		/** ��ҵID */
//		public String corpid;
//		/** ������ҵ���� */
//		public String corpname;
//		/** �Ƿ񷵳̣�1Ϊ�ǣ�0Ϊ�񣬡����̡�������������͵�󷵻ص��������ģ� */
//		public int isback;
//		/** ��ǰҳ�� */
//		public int page;
//		/** ��ǰҳ���� */
//		public int pagecount;
//		/** ������˻������� */
//		public int finishcount;
//		/** �˻������� */
//		public int total;
//		/** ���ƺ� */
//		public String carlicense; 
//		/** �˻����б� */
//		public List<CldDeliTaskStore> store;
//		/** �˻�����ϸ*/
//		public List<CldDeliTaskOrders> orders;
//		/** ������Ϣ*/
//		public UMS_Carinfo ums_carinfo;
//
//		/** �ͻ���ά�� **/
//		/** �����ڱ��ص��ͻ�״̬ */
//		public int local_status;
//
//		public long downUtcTime;
//
//		/** �������� **/
//		public CldDeliveryTaskCentre taskCentre;
//
//	}

	/**
	 * ��ȡ��ҵ��������
	 * 
	 * @author Zhouls
	 * @date 2016-5-13 ����10:29:58
	 */
	public static class CldDeliCorpLimitParm {
		/** ��ȡ��ʽ��0-����(Ĭ��)��1-ֻ������������,2-ֻ���ؾ�ʾ���ݡ� */
		public int req;
		/** �������������߼��� */
		public int tht;
		/** ���������������� */
		public int twh;
		/** �������������ؼ��� */
		public int twt;
		/** ���������������ؼ��� */
		public int tad;
		/** ��������������Գ��͵�ö��ֵ */
		public int tvt;

		public CldDeliCorpLimitParm() {
			req = 0;
			tht = -1;
			twh = -1;
			twt = -1;
			tad = -1;
			tvt = -1;
		}
	}

	/**
	 * 
	 * ·���ϵ���ҵ���в���
	 * 
	 * @author Zhouls
	 * @date 2016-5-14 ����4:31:18
	 */
	public static class CldDeliCorpLimitRouteParm extends CldDeliCorpLimitParm {
		/** �����˻�����ҵID */
		public String curTaskCorpId;
		/** ָ��uid·���ϵ��������ݡ����uid�á�|���ָ���(version>=3����) */
		public String uid;
	}

	/**
	 * 
	 * ͼ����ҵ��������
	 * 
	 * @author Zhouls
	 * @date 2016-5-14 ����4:31:33
	 */
	public static class CldDeliCorpLimitMapParm extends CldDeliCorpLimitParm {
		/** ˢͼ��ͼ������ */
		public int minX;
		public int minY;
		public int maxX;
		public int maxY;

		public CldDeliCorpLimitMapParm() {
			minX = -1;
			minY = -1;
			maxX = -1;
			maxY = -1;
		}
	}

	/**
	 * 
	 * ��������
	 * 
	 * @author Zhouls
	 * @date 2016-4-28 ����4:48:27
	 */
	public static class CldDeliCorpLimit extends CldDeliCorpWarning {
		/** ���ͣ�1Ϊ���У�2Ϊ���У� */
		public int limitType;
		/** ���г��ͣ�0Ϊ������1Ϊ���У�2Ϊ���ͼ����ϣ�3Ϊ�����ͣ�4Ϊ���ͣ� */
		public int prohibitType;
		/** ���أ��֣�0Ϊ���ޣ� */
		public float limitWeight;
		/** �޳����ף�0Ϊ���ޣ� */
		public float limitLong;
		/** �޿��ף�0Ϊ���ޣ� */
		public float limitWidth;
		/** �޸ߣ��ף�0Ϊ���ޣ� */
		public float limitHeight;
		/**��ҵ����**/
		public String corpName ;

		public CldDeliCorpLimit() {
			limitWeight = -1;
			limitLong = -1;
			limitWidth = -1;
			limitHeight = -1;
		}
	}

	/**
	 * 
	 * ��ʾ����
	 * 
	 * @author Zhouls
	 * @date 2016-4-28 ����4:48:45
	 */
	public static class CldDeliCorpWarning {
		/** X���� */
		public int x;
		/** Y���� */
		public int y;
		/** ��·cell */
		public int cellid;
		/** ��·UID */
		public int uid;
		/** ��·���� */
		public String roadName;
		/** ������������ */
		public String voiceContent;
		/** ������ҵID */
		public String corpid;
		/**��ҵ����**/
		public String corpName;
	}

	/**
	 * 
	 * ��ҵ��·����
	 * 
	 * @author Zhouls
	 * @date 2016-4-29 ����4:20:36
	 */
	public static class CldCorpRouteInfo {
		/** �Ƽ���·ID */
		public int routeid;
		/** ��·���� */
		public String title;
		/** ʹ���Ƽ���·����ID */
		public int naviid;
		/** ������ҵID */
		public String corpid;
		/** �����ٶ�(����/Сʱ)��0������ */
		public int limitspeed;
		/** ƫ����Χ(��)��0������ */
		public int yawrange;
		/** ��·��ע˵�� */
		public String remark;
	}

	/**
	 * 
	 * �����û��˻����Ķ�״̬����
	 * 
	 * @author Zhouls
	 * @date 2016-5-7 ����5:28:15
	 */
	public static class CldDeliTaskDB {
		@Id
		@NoAutoIncrement
		@Column(column = "id")
		public long id;// ��ҵId+�˻���IdΨһ����
		@Column(column = "kuid")
		public long kuid;// �˻��������û�id
		@Column(column = "status")
		public int status;// �˻����Ƿ��Ѷ� 0δ�� 1�Ѷ�
	}

	/**
	 * 
	 * ��ȡ��ҵ��·���
	 * 
	 * @author Zhouls
	 * @date 2016-5-11 ����10:03:22
	 */
	public static class CldDeliCorpRoutePlanResult {
		/** �ӿڷ��ش��� */
		public int errCode;
		/** �ӿڷ��ش������� */
		public String errMsg;
		/** ��ҵID */
		public String corpId;
		/** �Ƿ�����ҵ��·��1Ϊ�У�0Ϊ�ޣ� */
		public int isRoute;
		/** ��ҵ��·�����Ϣ */
		public CldCorpRouteInfo routeInfo;
		/** ·�߹滮��� */
		public byte[] rpData;

		public CldDeliCorpRoutePlanResult() {
			errCode = -1;
			errMsg = "";
			routeInfo = null;
			rpData = null;
			isRoute = -1;
			corpId = "";
		}
	}

	/**
	 * 
	 * ����Χ��
	 * 
	 * @author Zhouls
	 * @date 2016-5-24 ����11:43:01
	 */
	public static class CldElectfence {
		/** id */
		public String id;
		/** ��ʼʱ�� */
		public long stime;
		/** ����ʱ�� */
		public long etime;
		/** ������� */
		public int limitSpeed;
		/** �������ͣ�1Ϊ����,2Ϊ����,3Ϊ���ڵ���,4Ϊ���⵽��,5Ϊ������ */
		public int alarmType;
		/** ��״������ */
		public int count;
		/** ��״�㼯�� */
		public List<CldShapeCoords> lstOfShapeCoords;
	}

	/**
	 * 
	 * �ϱ�����Χ������
	 * 
	 * @author Zhouls
	 * @date 2016-5-24 ����2:23:45
	 */
	public static class CldUploadEFParm {
		/** Χ������������ҵid */
		public String corpid;
		/** ����x */
		public int x;
		/** ����y */
		public int y;
		/** ������Χ����Ϣ */
		public List<CldDeliElectFenceUpload> lstOfLauchEF;
	}

	/**
	 * 
	 * ��������Χ������
	 * 
	 * @author Zhouls
	 * @date 2016-5-30 ����9:21:19
	 */
	public static class CldReUploadEFParm {
		/** ������Χ����Ϣ */
		public List<CldDeliElectFenceReUpload> lstOfLauchEF;
	}

	/**
	 * 
	 * �ϱ�Χ������
	 * 
	 * @author Zhouls
	 * @date 2016-5-30 ����9:09:37
	 */
	public static class CldDeliElectFenceUpload {
		/** ��������ID */
		public String rid;
		/** �������͡�1Ϊ����2Ϊ����3Ϊ���ڵ���4Ϊ���⵽��5Ϊ����6���١� */
		public int am;
		/** �ϱ����͡�1-��ʼ״̬��2�仯״̬,3������ */
		public int at;
		/** ��ǰ״̬��1���ڣ�2���⡿ */
		public int st;
	}

	/**
	 * 
	 * ��������Χ������
	 * 
	 * @author Zhouls
	 * @date 2016-5-30 ����9:15:47
	 */
	public static class CldDeliElectFenceReUpload extends
			CldDeliElectFenceUpload {
		/** ����ҵID����������������ҵID�� */
		public String corpid;
		/** ������λ������X */
		public int x;
		/** ������λ������Y */
		public int y;
		/** ������ʱ�䡾UTCʱ�䡿 */
		public long time;
	}
	
	public static class AuthInfoList
	{
		/**��ҵID */
		public String corpid;
		/**��ҵ����*/
		public String corpname;
		/**�Ƿ�������Ȩ��*/
		public int isadd;
		/**�Ƿ����޸�Ȩ��*/
		public int ismod;
		/**�Ƿ��ж�ȡȨ��*/
		public int isread;
	}

	/**
	 * ��������
	 * 
	 * @author buxc
	 * @date 2015��8��19�� ����3:41:14
	 */
	public class CldDeliveryTaskCentre implements Serializable {

		private static final long serialVersionUID = 1L;
		private String storeId;
		private long storeX;
		private long storeY;
		private String storeName;
		private String storeAddr;
		private long stopX;
		private long stopY;

		public CldDeliveryTaskCentre(String sID, long sX, long sY,
				String sName, String sAddr, long stopX, long stopY) {
			storeId = sID;
			this.stopX = sX;
			this.stopY = sY;
			storeName = sName;
			storeAddr = sAddr;
			this.stopX = stopX;
			this.stopY = stopY;
		}

		public String getStoreId() {
			return storeId;
		}

		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}

		public long getStoreX() {
			return storeX;
		}

		public void setStoreX(long storeX) {
			this.storeX = storeX;
		}

		public long getStoreY() {
			return storeY;
		}

		public void setStoreY(long storeY) {
			this.storeY = storeY;
		}

		public String getStoreName() {
			return storeName;
		}

		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}

		public String getStoreAddr() {
			return storeAddr;
		}

		public void setStoreAddr(String storeAddr) {
			this.storeAddr = storeAddr;
		}

		public long getStopX() {
			return stopX;
		}

		public void setStopX(long stopX) {
			this.stopX = stopX;
		}

		public long getStopY() {
			return stopY;
		}

		public void setStopY(long stopY) {
			this.stopY = stopY;
		}
	}
	
	/**
	 * ˾����ʻ������Ϣ
	 * @author zhaoqy
	 * @date 2017-4-18
	 */
	public static class MtqCar {
		/** ���ƺ���  */
		public String carlicense;
		/** �����豸ID */
		public String carduid;
		/** ��������  */
		public String carmodel;
		/** ����Ʒ��  */
		public String brand;
		/** �����ͺ� */
		public String vehicletype;
		/** �豸��������  */
		public String devicename;
		/** �豸���к�  */
		public String mcuid;
		/** �豸SIM����ʱ�� */
		public String sim_endtime;
		
		public MtqNavi navi;
	}
	
	/**
	 * �г�����
	 * @author zhaoqy
	 * @date 2017-4-18
	 */
	public static class MtqTask {
		/** ��������ID */
		public String taskid;
		/** ������˾ID */
		public String corpid;
		/** ��ʼ�ͻ�ʱ�� */
		public String starttime;
		/** �ͻ����ʱ�� */
		public String finishtime;
	}
	
	/**
	 * �����г�
	 * @author zhaoqy
	 * @date 2017-4-18
	 */
	public static class MtqCarRoute {
		/** ���ƺ���  */
		public String carlicense;
		/** �����豸ID */
		public String carduid;
		/** ��������ID */
		public String taskid;
		/** ������˾ID */
		public String corpid;
		/** �г̸���  */
		public String navicount;
		
		public List<MtqNavi> navis;
	}
	
	public static class MtqNavi {
		/** �г̼�¼ID  */
		public String serialid;
		/** ���ʱ�� */
		public String starttime;
		/** Ϩ��ʱ��  */
		public String endtime;
		/** �����  */
		public String mileage;
		/** ��ʱ��  */
		public String traveltime;
		
		public List<MtqOrder> orders;
	}
	
	@SuppressWarnings("serial")
	public static class MtqOrder implements Serializable {
		/** �ͻ�����  */
		public String cut_orderid;
		/** ������ */
		public String send_name;
		/** �����˵绰  */
		public String send_phone;
		/** �����˵�ַ  */
		public String send_addr;
		/** �ջ��� */
		public String receive_name;
		/** �ջ��˵绰  */
		public String receive_phone;
		/** �ջ��˵�ַ  */
		public String receive_addr;
	}
	
	/**
	 * �г�����
	 * @author zhaoqy
	 * @date 2017-4-18
	 */
	public static class MtqTaskDetail {
		public Navi navi;
		
		public List<MtqTrack> tracks;
	}

	public static class Navi {
		/** �����豸ID */
		public String carduid;
		/** ���ʱ��  */
		public String starttime;
		/** Ϩ��ʱ��  */
		public String endtime;
		/** �ۼ�������������  */
		public String fuelcon;
		/** �ۼƵ��ٺ�����������  */
		public String idlefuelcon;
		/** ����̣���λ��ǧ��  */
		public String mileage;
		/** �г���߳��٣�����/ʱ�� */
		public String topspeed;
		/** �ۼ���ʻʱ�䣨�룩 */
		public String traveltime;
		/** �ۼ��ȳ�ʱ�䣨�룩 */
		public String warmedtime;
		/** �ۼƵ���ʱ�䣨�룩 */
		public String idletime;
		/** �г������ʶȵ÷֣��֣� */
		public String comfortscore;
	}
	
	public static class MtqTrack {
		/** �����豸ID  */
		public String carduid;
		/** �켣��ʼʱ�䣨UTCʱ�� */
		public String start_time;
		/** �켣����ʱ�䣨UTCʱ�䣩 */
		public String end_time;
		/** ����̣���λ��ǧ��  */
		public String mileage;
		
		public List<MtqPosData> pos_data;
	}
	
	public static class MtqPosData {
		/** X����  */
		public String x;
		/** Y���� */
		public String y;
		/** ��ʱ�ٶȣ�KM/H�� */
		public String speed;
		/** ����������  */
		public String direction;
		/** �ϱ�UTCʱ��� */
		public String time;
		/** ������  */
		public String pos_type;
		/** ͣ��ʱ��  */
		public String park_time;
	}
	
	/**
	 * ��ȡ�豸�ĳ�����Ϣ
	 * 
	 * @author zhaoqy
	 * @date 2017-5-31
	 */
	public static class MtqDeviceCar {
		
		/** ����������1Ϊ���˳�����2Ϊ��ҵ������*/
		public int owner;
		/** �����豸ID��Ϊ0ʱ���ް��豸�����˳���Ϊ��ǰ��¼�豸ID��*/
		public String carduid;
		/** �豸���ͣ�1ΪJTT808��2ΪOBD007��3ΪTPND��0Ϊ������*/
		public int devicetype;
		/** �豸�������ƣ���ƽ̨�˵��豸�������豸����һ�£�*/
		public String devicename;
		/** ���ƺ�  */
		public String carlicense;
		/** Ʒ��  */
		public String brand;
		/** ��������  */
		public String carmodel;
		/** �����ͺţ�0-δ֪; 1-΢��; 2-����; 3-����; 4-���ͣ�  */
		public int cartype;
		/** �����ף�  */
		public String carlong;
		/** ���ף�  */
		public String carwidth;
		/** �ߣ��ף�  */
		public String carheight;
		/** �������ᣩ  */
		public int caraxle;
		/** ���أ��֣�  */
		public String carweight;
		/** ���ܺţ���6λ��  */
		public String carvin;
		/** �������ţ���6λ��  */
		public String carengine;
	}
}
