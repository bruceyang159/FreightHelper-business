package com.mtq.ols.module.deliverybus;

import java.util.HashMap;
import java.util.Map;

import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.module.delivery.tool.CldKDeviceAPI;
import com.mtq.ols.module.deliverybus.parse.MtqBaseParse;
import com.mtq.ols.module.deliverybus.parse.MtqSapReturn;
import com.mtq.ols.tools.CldSapParser;

public class MtqSapDeliveryBus {

	/**
	 * �û���¼
	 * 
	 * @param duid
	 *            ��¼�豸ID
	 * @param apptype
	 *            APPӦ������
	 * @param version
	 *            APP�汾��
	 * @param username
	 *            �û���
	 * @param password
	 *            ���루MD5���ܺ�
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn login(long duid, int apptype, String version,
			String username, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duid", duid);
		map.put("apptype", apptype);
		map.put("version", version);
		map.put("username", username);
		map.put("password", password);

		MtqSapReturn errRes = MtqBaseParse.getNoSignPostParms(map, getSvr()
				+ "v1/user_login.php");
		return errRes;
	}

	/**
	 * �û��˳�
	 * 
	 * @param timestamp
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn logout() {
		Map<String, Object> map = getPubMap();
		map.put("timestamp", CldKDeviceAPI.getSvrTime());

		MtqSapReturn errRes = post(map, "v1/user_logout.php");
		return errRes;
	}

	/**
	 * ����״̬�б�
	 * 
	 * @param group_id
	 *            ����ID��0Ϊȫ����
	 * @param carstatus
	 *            ����״̬ (0Ϊȫ����1���У�2���ɳ���3��ҵ�У�20Ϊά�ޱ���)
	 * @param keywords
	 *            ģ�������ؼ��֣����ƺ��룩
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getCarStateList(int group_id, int carstatus,
			String keywords, int pageindex, int pagesize) {
		Map<String, Object> map = getPubMap();
		map.put("group_id", group_id);
		map.put("carstatus", carstatus);
		CldSapParser.putStringToMap(map, "keywords", keywords);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);

		MtqSapReturn errRes = post(map, "v1/get_carstate_list.php");
		return errRes;
	}

	/**
	 * ����״̬ͳ��
	 * 
	 * @param group_id
	 *            ����ID��0Ϊȫ����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getCarStateCount(int group_id) {
		Map<String, Object> map = getPubMap();
		map.put("group_id", group_id);

		MtqSapReturn errRes = post(map, "v1/get_carstate_count.php");
		return errRes;
	}

	/**
	 * ����ʵʱ״̬����
	 * 
	 * @param carid
	 *            ����ID
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getCarStateDetail(int carid) {
		Map<String, Object> map = getPubMap();
		map.put("carid", carid);

		MtqSapReturn errRes = post(map, "v1/get_carstate_detail.php");
		return errRes;
	}

	/**
	 * ���������˵�����
	 * 
	 * @param carid
	 *            ����ID
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getTaskStore(int carid, int pageindex,
			int pagesize) {
		Map<String, Object> map = getPubMap();
		map.put("carid", carid);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);

		MtqSapReturn errRes = post(map, "v1/get_taskstore_today.php");
		return errRes;
	}

	/**
	 * ���������г�����
	 * 
	 * @param carid
	 *            ����ID
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getTaskNavi(int carid, int pageindex,
			int pagesize) {
		Map<String, Object> map = getPubMap();
		map.put("carid", carid);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);

		MtqSapReturn errRes = post(map, "v1/get_tasknavi_today.php");
		return errRes;
	}

	/**
	 * ��������
	 * 
	 * @param time
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @param smstype
	 *            ��Ϣ���ͣ�1Ϊ�ı��࣬2Ϊ�����ࣩ
	 * @param carid
	 *            �����ȳ���ID
	 * @param driverid
	 *            ������˾��ID
	 * @param x
	 *            ����λ������X���ı�����
	 * @param y
	 *            ����λ������Y���ı�����
	 * @param addr
	 *            ����λ�õ�ַ���ı�����
	 * @param text
	 *            �ı����ݣ��ı�����
	 * @param voicedata
	 *            �������ݣ���������
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn setCarSend(int time, int smstype, int carid,
			int driverid, long x, long y, String addr, String poiname, String text,
			int voiceduration, String voicedata) {
		Map<String, Object> map = getPubMap();
		map.put("time", time);
		map.put("smstype", smstype);
		map.put("carid", carid);
		map.put("driverid", driverid);
		String outMd5Source = "";
		if (smstype == 1) {
			map.put("x", x);
			map.put("y", y);
			CldSapParser.putStringToMap(map, "addr", addr);
			CldSapParser.putStringToMap(map, "poiname", poiname);
			CldSapParser.putStringToMap(map, "text", text);
			outMd5Source = CldSapParser.formatSource(map);
		} else if (smstype == 2) {
			map.put("voiceduration", voiceduration);
			outMd5Source = CldSapParser.formatSource(map);
			CldSapParser.putStringToMap(map, "voicedata", voicedata);
		}

		MtqSapReturn errRes = post(map, outMd5Source, "v1/set_carsend.php");
		return errRes;
	}

	/**
	 * ��ʷ�켣
	 * 
	 * @param carid
	 *            ����ID
	 * @param starttime
	 *            ��ʼʱ�䣨UTCʱ�䣩
	 * @param endtime
	 *            ����ʱ�䣨���ڿ�ʼʱ�䣬С�ڻ���ڵ�ǰʱ�䣬ʱ���Ȳ�����10�죩
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getTrackHistory(int carid, long starttime,
			long endtime) {
		Map<String, Object> map = getPubMap();
		map.put("carid", carid);
		map.put("starttime", starttime);
		map.put("endtime", endtime);

		MtqSapReturn errRes = post(map, "v1/get_track_history.php");
		return errRes;
	}

	/**
	 * ��ȡ���¾�����Ϣ�������飩
	 * 
	 * @param incrindex
	 *            ������ʶ����������ȡ���ݣ�Ϊ�����������£�
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getAlarmMsg(String incrindex, int pagesize) {
		Map<String, Object> map = getPubMap();
		map.put("incrindex", incrindex);
		map.put("pagesize", pagesize);

		MtqSapReturn errRes = post(map, "v1/get_message_alarm.php");
		return errRes;
	}

	/**
	 * ��ȡ��ʷ������Ϣ�������飩
	 * 
	 * @param starttime
	 *            ��ʼʱ�䣨UTCʱ�䣩
	 * @param endtime
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @param order
	 *            �����ֶ�(������ʱ��0-����(Ĭ��)��1-˳��)
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-28
	 */
	public static MtqSapReturn getHistoryAlarmMsg(long starttime, long endtime,
			int pageindex, int pagesize, int order) {
		Map<String, Object> map = getPubMap();
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("order", order);

		MtqSapReturn errRes = post(map, "v1/get_message_alarm_history.php");
		return errRes;
	}

	/**
	 * ���ľ�����Ϣ�Ѷ�״̬
	 * 
	 * @param id
	 *            ��¼ID
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn setMsgAlarmRead(String id) {
		Map<String, Object> map = getPubMap();
		map.put("id", id);

		MtqSapReturn errRes = post(map, "v1/set_message_alarm_read.php");
		return errRes;
	}

	/**
	 * ϵͳ��Ϣ�������飩
	 * 
	 * @param incrindex
	 *            ������ʶ����������ȡ���ݣ�Ϊ�����������£�
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getSysMsg(String incrindex, int pagesize) {
		Map<String, Object> map = getPubMap();
		map.put("incrindex", incrindex);
		map.put("pagesize", pagesize);

		MtqSapReturn errRes = post(map, "v1/get_message_sys.php");
		return errRes;
	}

	/**
	 * ��ȡ��ʷϵͳ��Ϣ�������飩
	 * 
	 * @param starttime
	 *            ��ʼʱ�䣨UTCʱ�䣩
	 * @param endtime
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @param order
	 *            �����ֶ�(������ʱ��0-����(Ĭ��)��1-˳��)
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-28
	 */
	public static MtqSapReturn getHistorySysMsg(long starttime, long endtime,
			int pageindex, int pagesize, int order) {
		Map<String, Object> map = getPubMap();
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("order", order);

		MtqSapReturn errRes = post(map, "v1/get_message_sys_history.php");
		return errRes;
	}

	/**
	 * ����ϵͳ��Ϣ�Ѷ�״̬
	 * 
	 * @param serialid
	 *            ��¼ID
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn setMsgSysRead(String serialid) {
		Map<String, Object> map = getPubMap();
		map.put("serialid", serialid);

		MtqSapReturn errRes = post(map, "v1/set_message_sys_read.php");
		return errRes;
	}

	/**
	 * ���������б�
	 * 
	 * @param group_id
	 *            ����ID��0Ϊȫ����
	 * @param keywords
	 *            ģ�������ؼ��֣����ƺ��룩
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getCarDataList(int group_id, String keywords,
			int pageindex, int pagesize) {
		Map<String, Object> map = getPubMap();
		map.put("group_id", group_id);
		CldSapParser.putStringToMap(map, "keywords", keywords);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);

		MtqSapReturn errRes = post(map, "v1/get_cardata_list.php");
		return errRes;
	}

	/**
	 * ������������
	 * 
	 * @param carid
	 *            ����ID
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getCarDataDetail(int carid) {
		Map<String, Object> map = getPubMap();
		map.put("carid", carid);

		MtqSapReturn errRes = post(map, "v1/get_cardata_detail.php");
		return errRes;
	}

	/**
	 * ˾�������б�
	 * 
	 * @param invitestatus
	 *            ����״̬��0Ϊȫ����1δ��������Ϣ��2�Ѷ�������Ϣ��3ͬ����복�ӣ�4�ܾ����복�ӣ�5���˳�����)
	 * @param keywords
	 *            ģ�������ؼ��֣�˾��������
	 * @param pageindex
	 *            ҳ�루��1��ʼ��
	 * @param pagesize
	 *            ÿҳ������Ĭ��ÿҳ10�������200����
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getDriverDataList(int invitestatus,
			String keywords, int pageindex, int pagesize) {
		Map<String, Object> map = getPubMap();
		map.put("invitestatus", invitestatus);
		CldSapParser.putStringToMap(map, "keywords", keywords);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);

		MtqSapReturn errRes = post(map, "v1/get_driverdata_list.php");
		return errRes;
	}

	/**
	 * ˾����������
	 * 
	 * @param driverid
	 *            ˾��ID
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getDriverDataDetail(int driverid) {
		Map<String, Object> map = getPubMap();
		map.put("driverid", driverid);

		MtqSapReturn errRes = post(map, "v1/get_driverdata_detail.php");
		return errRes;
	}

	/**
	 * ����˾�����복��
	 * 
	 * @param driverid
	 *            ˾��ID
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn inviteDriver(int driverid) {
		Map<String, Object> map = getPubMap();
		map.put("driverid", driverid);

		MtqSapReturn errRes = post(map, "v1/invite_driver.php");
		return errRes;
	}

	/**
	 * �˵�ͳ�ƿ���
	 * 
	 * @param startdate
	 *            ��ѯ��ʼ����
	 * @param enddate
	 *            ��ѯ�������ڣ����ڻ���ڿ�ʼ���ڣ�С�ڻ���ڵ�ǰ���ڣ����ڿ�Ȳ�����31�죩
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getOrderCount(String startdate, String enddate) {
		Map<String, Object> map = getPubMap();
		map.put("startdate", startdate);
		map.put("enddate", enddate);

		MtqSapReturn errRes = post(map, "v1/get_order_count.php");
		return errRes;
	}

	/**
	 * ����ƻ�ͳ�ƿ���
	 * 
	 * @param startdate
	 *            ��ѯ��ʼ����
	 * @param enddate
	 *            ��ѯ�������ڣ����ڻ���ڿ�ʼ���ڣ�С�ڻ���ڵ�ǰ���ڣ����ڿ�Ȳ�����31�죩
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getTaskCount(String startdate, String enddate) {
		Map<String, Object> map = getPubMap();
		map.put("startdate", startdate);
		map.put("enddate", enddate);

		MtqSapReturn errRes = post(map, "v1/get_task_count.php");
		return errRes;
	}

	/**
	 * �²۷���
	 * 
	 * @param fdtype
	 *            �������ͣ�1Ϊƽ̨�࣬2ΪӲ���ࣩ
	 * @param dtype
	 *            �豸���ͣ�2Ϊ����˫ģһ�����3Ϊ������KPND��4ΪTD-BOX��5ΪTD-PND��
	 * @param title
	 *            ����
	 * @param content
	 *            ��������
	 * @param contact
	 *            ��ϵ��ʽ
	 * @param pics
	 *            ͼƬ�ļ���Ϣ��ý���ʶID�������Сд��;���ֺŷָ���ͼƬ�ļ���ͨ�����ϴ�������Ƭ�ļ����ӿ��ϴ���
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn setFeedback(int fdtype, int dtype, String title,
			String content, String contact, String pics) {
		Map<String, Object> map = getPubMap();
		map.put("fdtype", fdtype);
		map.put("dtype", dtype);
		map.put("title", title);
		map.put("content", content);
		CldSapParser.putStringToMap(map, "contact", contact);
		CldSapParser.putStringToMap(map, "pics", pics);

		MtqSapReturn errRes = post(map, "v1/set_feedback.php");
		return errRes;
	}

	/**
	 * �ϴ�������Ƭ�ļ�
	 * 
	 * @param time
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @param x
	 *            ����ʱ����������X����λʧ��Ϊ0��
	 * @param y
	 *            ����ʱ����������Y����λʧ��Ϊ0��
	 * @param data
	 *            ͼƬ���ݣ�base64��
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn uploadAttachPic(int x, int y, String data) {
		Map<String, Object> map = getPubMap();
		map.put("time", CldKDeviceAPI.getSvrTime());
		map.put("x", x);
		map.put("y", y);
		String outMd5Source = CldSapParser.formatSource(map);
		map.put("data", data);

		MtqSapReturn errRes = post(map, outMd5Source,
				"v1/upload_attach_pic.php");
		return errRes;
	}

	/**
	 * Ӳ���豸����
	 * 
	 * @param timestamp
	 *            ����ʱ�䣨UTCʱ�䣩
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static MtqSapReturn getDeviceDType() {
		Map<String, Object> map = getPubMap();
		map.put("timestamp", CldKDeviceAPI.getSvrTime());

		MtqSapReturn errRes = post(map, "v1/get_device_dtype.php");
		return errRes;
	}

	/**
	 * ��ȡ��������map
	 * 
	 * @return Map<String,Object>
	 * @author zhaoqy
	 * @date 2017-06-09
	 */
	public static Map<String, Object> getPubMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sesskey", MtqDalDeliveryBus.getInstance().getSessKey());
		map.put("admin_id", MtqDalDeliveryBus.getInstance().getAdminID());

		map.put("duid", MtqDalDeliveryBus.getInstance().getDuid());
		map.put("apptype", MtqDalDeliveryBus.getInstance().getAppType());
		map.put("version", MtqDalDeliveryBus.getInstance().getVersion());
		return map;
	}

	/**
	 * postƴ�ӷ�ʽ
	 * 
	 * @param outMd5Source
	 *            �ⲿ��������ǩ���Ĳ���
	 * @param map
	 * @param url
	 * @return MtqSapReturn
	 */
	private static MtqSapReturn post(Map<String, Object> map,
			String outMd5Source, String url) {
		return MtqBaseParse.getCustPostParms(map, outMd5Source, getSvr() + url,
				MtqDalDeliveryBus.getInstance().getToken());
	}

	/**
	 * postƴ�ӷ�ʽ
	 * 
	 * @param map
	 * @param url
	 * @return MtqSapReturn
	 */
	private static MtqSapReturn post(Map<String, Object> map, String url) {
		return MtqBaseParse.getCustPostParms(map, getSvr() + url,
				MtqDalDeliveryBus.getInstance().getToken());
	}

	/**
	 * ��ȡ���˱���ҵ��ӿڵ�ַ
	 * 
	 * @param ���Ե�ַ
	 *            ����������http://192.168.200.213/ktms/hybapp/
	 * @param ���Ե�ַ
	 *            ����������http://test.matiquan.cn:50301/ktms/hybapp/
	 * @param ��ʽ��ַ
	 *            ��https://hy.careland.com.cn/hybapp/
	 * @return
	 */
	public static String getSvr() {
		String addr = "";
		if (CldBllUtil.getInstance().isTestVersion()) {
			addr = "http://test.matiquan.cn:50301/ktms/hybapp/";
			//addr = "http://192.168.200.213/ktms/hybapp/";
		} else {
			addr = "https://hy.careland.com.cn/hybapp/";
			       
		}
		return addr;
	}
}
