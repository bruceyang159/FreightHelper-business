/*
 * @Title CldSapKMessage.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cld.gson.JsonArray;
import com.cld.gson.JsonObject;
import com.mtq.ols.dal.CldDalKCallNavi;
import com.mtq.ols.sap.bean.CldSapKMParm;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareAKeyCallParm;
import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.sap.parse.CldKMessageParse.ProtRecMsg.ProtAkeyMsg;
import com.mtq.ols.sap.parse.CldKMessageParse.ProtRecMsg.ProtPoiMsg;
import com.mtq.ols.sap.parse.CldKMessageParse.ProtRecMsg.ProtSysMsg;
import com.mtq.ols.sap.parse.CldKMessageParse.ProtRecMsg.ProtAkeyMsg.ProtContent;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;


/**
 * ��ϢϵͳЭ��ӿ�
 * 
 * @author Zhouls
 * @date 2014-8-13 ����10:19:48
 */
public class CldSapKMessage {

	/** �״�����. */
	public static String keyCode;
	/** Э��汾��. */
	private static final int APIVER = 1;
	/** �������ݱ���(1UTF-8 2GBK��Ŀǰ��֧��1,Ĭ��Ϊ1). */
	private static final int RSCHARSET = 1;
	/** �������ݸ�ʽ(1JSON 2�����ƣ�Ŀǰ��֧��1,Ĭ��Ϊ1) */
	private static final int RSFORMAT = 1;
	/** UMSAЭ��汾(kz:1 kz2:2). */
	private static final int UMSAVER = 2;

	/**
	 * ��ȡ��Ϣϵͳ��Կ(301)
	 * 
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-22 ����9:12:45
	 */
	public static CldSapReturn initKeyCode() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		String key = "C9T659YZA5UY6357G140TRVCC5411UY9";
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg() + "kmessage_get_code.php", key);
		return errRes;
	}

	/**
	 * ������Ϣ(302)
	 * 
	 * @param kuid
	 *            �û�Id
	 * @param duid
	 *            �û��豸Id
	 * @param messagetype
	 *            ��Ϣ����(11:λ��POI;12:·��;13:·��;14:·��)
	 * @param module
	 *            ��Ϣ����ģ��(1:K��;2:WEB��ͼ;3:һ��ͨ)
	 * @param parm
	 *            �ش�������errCode=0ʱ��д��messageid
	 * @param title
	 *            ��Ϣ���⣨��ΪNull��
	 * @param endtime
	 *            ��Ч����ʱ��(YYYY-MM-dd H:i:s)����ΪNull��
	 * @param bussinessid
	 *            ҵ���� CM(1)
	 * @param session
	 *            ��¼���ص�session
	 * @param appid
	 *            CM(9)
	 * @param apptype
	 *            the apptype
	 * @return errCode(0,401��402��403��404) int
	 * @author Zhouls
	 * @date 2014-8-21 ����3:28:01
	 */
	public static CldSapReturn createCldMsg(long kuid, long duid,
			int messagetype, int module, CldSapKMParm parm, String title,
			String endtime, int bussinessid, String session, int appid,
			int apptype) {
		Map<String, Object> map = new HashMap<String, Object>();
		CldSapReturn errRes = new CldSapReturn();
		switch (messagetype) {
		case 11:
			/**
			 * ����һ����
			 */
			map.put("apiver", APIVER);
			map.put("appid", appid);
			map.put("apptype", apptype);
			map.put("bussinessid", bussinessid);
			map.put("duid", duid);
			map.put("kuid", kuid);
			map.put("messagetype", messagetype);
			map.put("module", module);
			map.put("name", parm.getPoiMsg().getName());
			map.put("poi", parm.getPoiMsg().getPoi());
			map.put("rscharset", RSCHARSET);
			map.put("rsformat", RSFORMAT);
			map.put("session", session);
			map.put("umsaver", UMSAVER);
			CldSapParser.putStringToMap(map, "title", title);
			CldSapParser.putStringToMap(map, "endtime", endtime);
			errRes = CldKBaseParse.getPostParms(map, CldSapUtil.getNaviSvrMsg()
					+ "kmessage_create_terminal_message.php",
					CldSapUtil.decodeKey(keyCode));
			return errRes;
		case 12:
			/**
			 * ����һ��·��
			 */
			map.put("apiver", APIVER);
			map.put("appid", appid);
			map.put("apptype", apptype);
			map.put("bussinessid", bussinessid);
			map.put("rscharset", RSCHARSET);
			map.put("rsformat", RSFORMAT);
			map.put("umsaver", UMSAVER);
			map.put("kuid", kuid);
			map.put("duid", duid);
			map.put("messagetype", messagetype);
			map.put("module", module);
			map.put("name", parm.getRouteMsg().getName());
			map.put("start", parm.getRouteMsg().getStart());
			map.put("end", parm.getRouteMsg().getEnd());
			map.put("routepoint", parm.getRouteMsg().getRoutePoint());
			map.put("avoidpoint", parm.getRouteMsg().getAvoidPoint());
			map.put("conditioncode", parm.getRouteMsg().getConditionCode());
			map.put("aviodcondition", parm.getRouteMsg().getAviodCondition());
			map.put("forbidcondition", parm.getRouteMsg().getForbidCondition());
			map.put("mapver", parm.getRouteMsg().getMapVer());
			map.put("session", session);
			CldSapParser.putStringToMap(map, "endtime", endtime);
			CldSapParser.putStringToMap(map, "title", title);
			errRes = CldKBaseParse.getPostParms(map, CldSapUtil.getNaviSvrMsg()
					+ "kmessage_create_terminal_message.php",
					CldSapUtil.decodeKey(keyCode));
			return errRes;
		default:
			return errRes;
		}
	}

	/**
	 * ������Ϣ(303)
	 * 
	 * @param messageid
	 *            ������Ϣ���ص���Ϣid
	 * @param duid
	 *            �û��豸id
	 * @param uniqueids
	 *            �û�\�豸��ʶ+Apptype�������;�ָ�,�� 1,1;2,1
	 * @param messagetype
	 *            ��Ϣ���� 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 * @param kuid
	 *            the kuid
	 * @param bussinessid
	 *            ҵ���� CM(1)
	 * @param session
	 *            ��¼���ص�session
	 * @param appid
	 *            CM(9)
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-1 ����10:15:43
	 */
	public static CldSapReturn sendCldMsg(long messageid, long duid,
			String uniqueids, int messagetype, long kuid, int bussinessid,
			String session, int appid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("appid", appid);
		map.put("bussinessid", bussinessid);
		map.put("duid", duid);
		map.put("kuid", kuid);
		map.put("messageid", messageid);
		map.put("messagetype", messagetype);
		map.put("session", session);
		map.put("uniqueids", uniqueids);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg()
						+ "kmessage_send_terminal_message.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ������Ϣ��304��
	 * 
	 * @param duid
	 *            �û��豸id
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS���������";"�ָ�����"1;2"
	 * @param prover
	 *            �汾�ţ������";"�ָ�����"6.0;11.0"
	 * @param list
	 *            �ش���Ϣ�б�
	 * @param kuid
	 *            �û�Id��û����0��
	 * @param regionCode
	 *            ����������
	 * @param x
	 *            ��ǰ����x
	 * @param y
	 *            ��ǰ����y
	 * @param bussinessid
	 *            ҵ���� CM(1)
	 * @param session
	 *            ��¼���ص�session
	 * @param appid
	 *            CM(9)
	 * @param istmc
	 *            �Ƿ���·����0��������1���رգ���Ĭ��Ϊ0������·�������ʵ���Ϣ��
	 * @return int
	 * @author Zhouls
	 * @date 2014-12-18 ����12:14:03
	 */
	public static CldSapReturn recShareMsg(long duid, int apptype,
			String prover, long kuid, int regionCode, long x, long y,
			int bussinessid, String session, int appid, int istmc) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("apptype", apptype);
		map.put("duid", duid);
		map.put("prover", prover);
		CldSapParser.putIntToMap(map, "appid", appid);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);
		CldSapParser.putIntToMap(map, "istmc", istmc);
		CldSapParser.putIntToMap(map, "regioncode", regionCode);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapParser.putLongToMap(map, "x", x);
		CldSapParser.putLongToMap(map, "y", y);
		if(CldSapUtil.getNaviSvrMsg() == null || CldSapUtil.getNaviSvrMsg().length() <= 0)
		{
			
		}
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg() + "kmessage_download_message.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �ն˽���������ʷ��Ϣ(305)
	 * 
	 * @param duid
	 *            �豸Id
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param kuid
	 *            �û�Id
	 * @param list
	 *            the list
	 * @param bussinessid
	 *            ҵ���� CM(1)
	 * @param session
	 *            ��¼���ص�session
	 * @param appid
	 *            CM(9)
	 * @param apptype
	 *            the apptype
	 * @return errCode(401��402��403��404) int
	 * @author Zhouls
	 * @date 2014-9-1 ����10:22:15
	 */
	public static CldSapReturn recLastestMsgHitory(long duid,
			String messagetype, long kuid, int bussinessid, String session,
			int appid, int apptype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("bussinessid", bussinessid);
		map.put("duid", duid);
		map.put("messagetype", messagetype);
		map.put("appid", appid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg()
						+ "kmessage_receive_latest_termial_history.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �ն˽�����ʷ����Ϣ��ѯ�ӿڣ����»�����(306)
	 * 
	 * @param duid
	 *            �豸Id
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param length
	 *            ÿ�������¼��
	 * @param lastid
	 *            ���һ������Ϣid
	 * @param lasttime
	 *            ���һ������Ϣʱ��
	 * @param kuid
	 *            �û�Id
	 * @param list
	 *            �ش���Ϣ�б�
	 * @param bussinessid
	 *            ҵ���� CM(1)
	 * @param session
	 *            ��¼���ص�session
	 * @param appid
	 *            CM(9)
	 * @param apptype
	 *            the apptype
	 * @return ������� 401��402��403��404 int
	 * @author Zhouls
	 * @date 2014-9-1 ����10:30:43
	 */
	public static CldSapReturn recNewMsgHitory(long duid, String messagetype,
			int length, long lastid, long lasttime, long kuid, int bussinessid,
			String session, int appid, int apptype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("bussinessid", bussinessid);
		map.put("duid", duid);
		map.put("messagetype", messagetype);
		map.put("appid", appid);
		map.put("lastid", lastid);
		map.put("lasttime", lasttime);
		map.put("length", length);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg()
						+ "kmessage_receive_new_termial_history.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �ն˽�����ʷ����Ϣ��ѯ�ӿڣ����ϻ�����(307)
	 * 
	 * @param duid
	 *            �豸Id
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param length
	 *            ÿ�������¼��
	 * @param lastid
	 *            ���һ������Ϣid
	 * @param lasttime
	 *            ���һ������Ϣʱ��
	 * @param kuid
	 *            �û�Id
	 * @param list
	 *            �ش���Ϣ�б�
	 * @param bussinessid
	 *            ҵ���� CM(1)
	 * @param session
	 *            ��¼���ص�session
	 * @param appid
	 *            CM(9)
	 * @param apptype
	 *            the apptype
	 * @return ������� 401��402��403��404 int
	 * @author Zhouls
	 * @date 2014-9-1 ����10:30:43
	 */
	public static CldSapReturn recOldMsgHitory(long duid, String messagetype,
			int length, long lastid, long lasttime, long kuid, int bussinessid,
			String session, int appid, int apptype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("bussinessid", bussinessid);
		map.put("duid", duid);
		map.put("messagetype", messagetype);
		map.put("appid", appid);
		map.put("lastid", lastid);
		map.put("lasttime", lasttime);
		map.put("length", length);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg()
						+ "kmessage_receive_old_termial_history.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �ն˸�����Ϣ�Ķ�״̬�ӿ�(308)
	 * 
	 * @param duid
	 *            �豸Id
	 * @param kuid
	 *            �û�Id
	 * @param session
	 *            Kuid��Ϊ��ʱ��appid�ش�
	 * @param bussinessid
	 *            Kuid��Ϊ��ʱ��bussinessid�ش�
	 * @param appid
	 *            Kuid��Ϊ��ʱ��appid�ش�
	 * @param createtype
	 *            �������� 1����Ӫ��̨��2���ն�
	 * @param messageids
	 *            ��ϢId(msgid,createtime,createtype;msgid,createtime,createtype;)
	 * @return int
	 * @author Zhouls
	 * @date 2014-10-8 ����3:54:33
	 */
	public static CldSapReturn upMsgReadStatus(long duid, long kuid,
			String session, int bussinessid, int appid, int createtype,
			String messageids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("bussinessid", bussinessid);
		map.put("duid", duid);
		map.put("createtype", createtype);
		map.put("appid", appid);
		map.put("messageids", messageids);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg() + "kmessage_up_read_status.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡ�ʵ��б�309��
	 * 
	 * @param areatype
	 *            �����������ͣ�1������2��Χ 0ȫ��
	 * @param starttime
	 *            ��ʼʱ��
	 * @param endtime
	 *            ����ʱ��
	 * @return int
	 * @author Zhouls
	 * @date 2014-12-17 ����6:37:42
	 */
	public static CldSapReturn getAreaList(int areatype, String starttime,
			String endtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("areatype", areatype);
		CldSapParser.putStringToMap(map, "endtime", endtime);
		CldSapParser.putStringToMap(map, "starttime", starttime);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg() + "kmessage_terminal_area_list.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �ϱ�λ��(407)
	 * 
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS��
	 * @param prover
	 *            �汾��
	 * @param kuid
	 *            �������ʺ�id
	 * @param session
	 *            Session
	 * @param appid
	 *            appid
	 * @param bussinessid
	 *            bussinessid
	 * @param mobile
	 *            �ֻ���,��Ϊ�գ���kuid�󶨵������ֻ��Ŷ��ϱ�λ��
	 * @param longitude
	 *            WGS84 ���ȣ���λ����
	 * @param latitude
	 *            WGS84 γ�ȣ���λ����
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-14 ����9:15:21
	 */
	public static CldSapReturn upLocation(int apptype, String prover,
			long kuid, String session, int appid, int bussinessid,
			String mobile, double longitude, double latitude) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("bussinessid", bussinessid);
		map.put("apptype", apptype);
		map.put("latitude", latitude);
		map.put("appid", appid);
		map.put("longitude", longitude);
		map.put("prover", prover);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapParser.putStringToMap(map, "mobile", mobile);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg() + "kmessage_up_ppt_location.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡһ��ͨ��Ϣ(408)
	 * 
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS��
	 * @param prover
	 *            �汾��
	 * @param kuid
	 *            �������ʺ�id
	 * @param session
	 *            Session
	 * @param appid
	 *            appid
	 * @param bussinessid
	 *            bussinessid
	 * @param mobile
	 *            �ֻ�����
	 * @param longitude
	 *            WGS84 ���ȣ���λ����
	 * @param latitude
	 *            WGS84 γ�ȣ���λ����
	 * @param isloop
	 *            �Ƿ���ѭ��Ĭ��Ϊ��
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-14 ����9:20:42
	 */
	public static CldSapReturn recPptMsg(int apptype, String prover, long kuid,
			String session, int appid, int bussinessid, String mobile,
			double longitude, double latitude, int isloop) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("apptype", apptype);
		map.put("prover", prover);
		map.put("isloop", isloop);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		CldSapParser.putIntToMap(map, "appid", appid);
		CldSapParser.putIntToMap(map, "bussinessid", bussinessid);
		CldSapParser.putLongToMap(map, "kuid", kuid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapParser.putStringToMap(map, "mobile", mobile);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrMsg() + "kmessage_get_ppt_message.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ������Ϣ�б�
	 * 
	 * @param strData
	 *            �ӿڷ��ص���Ϣ�ַ���
	 * @param list
	 *            ��Ϣ�б�
	 * @param kuid
	 *            ��ǰ��¼��kuid
	 * @param apptype
	 *            Ӧ�ó�������
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-23 ����2:34:44
	 */
	public static void parseMessage(String strData, List<CldSapKMParm> list,
			long kuid, int apptype) {
		JsonArray array = CldSapParser.fromJsonArray(strData, "data");
		if (null != array) {
			for (int i = 0; i < array.size(); i++) {
				CldSapKMParm parm = new CldSapKMParm();
				if (null != array.get(i)) {
					if (array.get(i).isJsonObject()) {
						JsonObject json = (JsonObject) array.get(i);
						if (json.has("messagetype")) {
							int msgType = json.get("messagetype").getAsInt();
							switch (msgType) {
							case 1:
							case 2:
							case 3:
							case 4:
								/**
								 * ϵͳ��Ϣ
								 */
								ProtSysMsg protSysMsg = CldSapParser.fromJson(
										json.toString(), ProtSysMsg.class);
								if (null != protSysMsg) {
									protSysMsg.protParse(parm);
									list.add(parm);
								}
								break;
							case 11:
								/**
								 * poi��Ϣ
								 */
								ProtPoiMsg protPoiMsg = CldSapParser.fromJson(
										json.toString(), ProtPoiMsg.class);
								if (null != protPoiMsg) {
									protPoiMsg.protParse(parm);
									list.add(parm);
								}
								break;
							case 15:
								/**
								 * һ��ͨ��Ϣ
								 */
								ProtAkeyMsg protAkeyMsg = CldSapParser
										.fromJson(json.toString(),
												ProtAkeyMsg.class);
								if (null != protAkeyMsg) {
									protAkeyMsg.protParse(parm);
									list.add(parm);
								}
								break;
							default:
								/**
								 * �������͵���Ϣ
								 */
								ProtSysMsg protOtherMsg = CldSapParser
										.fromJson(json.toString(),
												ProtSysMsg.class);
								if (null != protOtherMsg) {
									protOtherMsg.protParse(parm);
									list.add(parm);
								}
								continue;
							}
						}
					}
				}
				if (parm.getCreateuserid() == kuid) {
					/**
					 * ���ʺ��豸����Ϣ
					 */
					if (apptype == parm.getApptype()) {
						parm.setCreateuser("���͵������豸");
					} else {
						switch (parm.getApptype()) {
						case 1:
							parm.setCreateuser("�����ҵ�Android�ֻ�");
							break;
						case 2:
							parm.setCreateuser("�����ҵ�iPhone�ֻ�");
							break;
						case 5:
							parm.setCreateuser("�����ҵ�����");
							break;
						default:
							parm.setCreateuser("�����ҵ������豸");
						}
					}
				} else {
					/**
					 * �ʺż���Ϣ
					 */
					switch (parm.getMsgType()) {
					case 1:
					case 2:
					case 3:
					case 4:
						parm.setCreateuser("����ϵͳ��Ϣ");
						break;
					case 11:
					case 12:
					case 13:
					case 14:
						parm.setCreateuser("����K��" + parm.getCreateuser());
						break;
					case 15:
						parm.setCreateuser("����һ��ͨ");
						break;
					}
				}
			}
		}
	}

	/**
	 * ����һ��ͨ��Ϣ
	 * 
	 * @param strData
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-23 ����2:34:23
	 */
	public static void parsePPtMessage(String strData) {
		List<ShareAKeyCallParm> list = new ArrayList<ShareAKeyCallParm>();
		JsonArray array = CldSapParser.fromJsonArray(strData, "data");
		if (null != array) {
			for (int i = 0; i < array.size(); i++) {
				ShareAKeyCallParm aKeyCallMsg = new ShareAKeyCallParm();
				if (null != array.get(i)) {
					if (array.get(i).isJsonObject()) {
						JsonObject json = (JsonObject) array.get(i);
						ProtContent protAkeyMsg = CldSapParser.fromJson(
								json.toString(), ProtContent.class);
						if (null != protAkeyMsg) {
							protAkeyMsg.protParse(aKeyCallMsg);
							list.add(aKeyCallMsg);
						}
					}
				}
			}
			CldDalKCallNavi.getInstance().setMsgList(list);
		}
	}
}
