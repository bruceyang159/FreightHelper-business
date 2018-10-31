/*
 * @Title CldSapKBDevelop.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.util.HashMap;
import java.util.Map;

import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;

/**
 * BD����
 * 
 * @author Zhouls
 * @date 2014-11-18 ����3:25:22
 */
public class CldSapKBDevelop {

	/**
	 * ��ȡ�ܱ߾Ƶ꣨800��
	 * 
	 * @param id
	 *            ��ǰ��������ID
	 * @param m
	 *            ����POI���ͣ�101Ԥ���Ƶ꣬����ָ��
	 * @param x
	 *            �������ĵ㾭������
	 * @param y
	 *            �������ĵ�γ������
	 * @param r
	 *            �����뾶����ô���10000���ڣ�Ҫ��Ȼ��Χ̫��Ӱ��Ч�ʡ���λ����
	 * @param s
	 *            ��ʼ��ţ���1��ʼ��Ĭ��ֵΪ1
	 * @param p
	 *            ����������ȡֵ��Χ1-200��Ĭ��ֵΪ200
	 * @param sort
	 *            �������Ĭ�����򡪡�SORT_DEFAULT���������򡪡�SORT_DISTANCE
	 * @param version
	 *            �ӿڰ汾����1��ʼ
	 * @param userid
	 *            �û�ID��������null
	 * @param session
	 *            �ỰID��������null
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-18 ����3:41:24
	 */
	public static CldSapReturn getHotelList(long id, int m, long x, long y,
			int r, int s, int p, int sort, int version, String userid,
			String session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("m", m);
		map.put("c", x + "+" + y);
		map.put("r", r);
		map.put("s", s);
		map.put("p", p);
		map.put("sort", sort);
		map.put("version", version);
		CldSapParser.putStringToMap(map, "userid", userid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParmsNoEncode(map,
				CldSapUtil.getNaviSvrON() + "pois_search.ums", null);
		return errRes;
	}

	/**
	 * ��ȡ�Ƶ귿��Ԥ����Ϣ��801��
	 * 
	 * @param uid
	 *            �Ƶ�uid������ָ��
	 * @param flag
	 *            uid���ͣ�0-KUID��1-VUID��Ĭ�Ͽ�����
	 * @param checkindate
	 *            ��סʱ�� ��ʽyy-mm-dd����201401011
	 * @param checkoutdate
	 *            �뿪ʱ�� ��ʽyy-mm-dd����201401011
	 * @param version
	 *            �ӿڰ汾����1��ʼ
	 * @param userid
	 *            �û�ID��������null
	 * @param session
	 *            �ỰID��������null
	 * @param src
	 *            ��version=2ʱ��src������Ч��
	 *            ��src=0����Ϊ��ʱ��ֻ�·��Ƶ�����Ӧ�ĵ�����������srcid,��srcname���������б�
	 *            ��src>0,��ʾͨ��������ȥ��ȡ�۸�ƻ�
	 *            ������Ҫ��ȡЯ��(src=5)�ļ۸�ƻ�,��ôָ��src����Ϊ5�������Ҫ��ȡ��������̵����ݿ�����src=5,6
	 *            ��ʵ����ȡ���������
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-18 ����3:49:38
	 */
	public static CldSapReturn getRatePlan(String uid, int flag,
			String checkindate, String checkoutdate, int version,
			String userid, String session, String src) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("flag", flag);
		map.put("checkindate", checkindate);
		map.put("checkoutdate", checkoutdate);
		map.put("version", version);
		CldSapParser.putStringToMap(map, "userid", userid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapParser.putStringToMap(map, "src", src);
		CldSapReturn errRes = CldKBaseParse.getGetParmsNoEncode(map,
				CldSapUtil.getNaviSvrBD() + "get_rate_plan.php", null);
		return errRes;
	}

	/**
	 * ��ȡpoi������Ϣ��802��
	 * 
	 * @param uid
	 *            Poi Uid ����ָ��
	 * @param m
	 *            POI���ͣ�101�Ƶ����飬102�������飬����ָ��
	 * @param version
	 *            �ӿڰ汾����1��ʼ
	 * @param userid
	 *            �û�ID��������null
	 * @param session
	 *            �ỰID��������null
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-18 ����3:59:41
	 */
	public static CldSapReturn getPoiDetails(String uid, int m, int version,
			String userid, String session) {
		String strDetailType = "";
		switch (m) {
		case 101:
			strDetailType = "get_hotel_detail.php";
			break;
		case 102:
			strDetailType = "get_cater_detail.php";
			break;
		default:
			strDetailType = "";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("version", version);
		CldSapParser.putStringToMap(map, "userid", userid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParmsNoEncode(map,
				CldSapUtil.getNaviSvrBD() + strDetailType, null);
		return errRes;
	}

	/**
	 * ��ȡĳ������ܱ���Ϣ��803��
	 * 
	 * @param kuid
	 *            ������POI UID
	 * @param flag
	 *            uid���ͣ�0-KUID��1-VUID��Ĭ��KUID
	 * @param t
	 *            ������ 1-�Ź��� 2-�Ż݄�
	 * @param version
	 *            �ӿڰ汾����1��ʼ
	 * @param userid
	 *            �û�ID��������null
	 * @param session
	 *            �ỰID��������null
	 * @return int
	 * @author Zhouls
	 * @date 2015-1-5 ����5:44:53
	 */
	public static CldSapReturn getPoiGroupCoupon(String kuid, int flag, int t,
			int version, String userid, String session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kuid", kuid);
		map.put("flag", flag);
		map.put("t", t);
		map.put("version", version);
		CldSapParser.putStringToMap(map, "userid", userid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParmsNoEncode(map,
				CldSapUtil.getNaviSvrBD() + "get_poi_coupons.php", null);
		return errRes;
	}

	/**
	 * ���ĳ�����Ź�/�Ż�������804��
	 * 
	 * @param f
	 *            ���˴������������"-"�ָ���UTF8 URL���룬�����򴫿��ַ���
	 * @param city
	 *            ����ID������ָ��
	 * @param code
	 *            ���������룬�����򴫿��ַ���
	 * @param t
	 *            ������ 1-�Ź��� 2-�Ż݄�
	 * @param x
	 *            ���ĵ㾭������
	 * @param y
	 *            ���ĵ�γ������
	 * @param r
	 *            �����뾶����λ����
	 * @param sno
	 *            ��ʼ��ţ���1��ʼ��Ĭ��Ϊ1
	 * @param num
	 *            ����Ą�������ÿ�����ȡ1000��
	 * @param sort
	 *            Ĭ�����򡪡�SORT_DEFAULT���������򡪡�SORT_DISTANCE�������ĵ���Ч��
	 * @param version
	 *            �ӿڰ汾����1��ʼ
	 * @param userid
	 *            �û�ID��������null
	 * @param session
	 *            �ỰID��������null
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-18 ����4:23:24
	 */
	public static CldSapReturn getGroupCouponCount(String f, String city,
			String code, int t, long x, long y, int r, int sno, int num,
			int sort, int version, String userid, String session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("f", f);
		map.put("city", city);
		map.put("code", code);
		map.put("t", t);
		map.put("c", x + "," + y);
		map.put("r", r);
		map.put("sno", sno);
		map.put("num", num);
		map.put("sort", sort);
		map.put("version", version);
		CldSapParser.putStringToMap(map, "userid", userid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParmsNoEncode(map,
				CldSapUtil.getNaviSvrBD() + "search_coupons.php", null);
		return errRes;
	}

	/**
	 * ����Ź�/�Ż��б�805��
	 * 
	 * @param f
	 *            ���˴������������"-"�ָ���UTF8 URL���룬�����򴫿��ַ���
	 * @param city
	 *            ����ID����һ���ģ�������ָ��
	 * @param code
	 *            ���������룬�����򴫿��ַ���
	 * @param t
	 *            ������ 1-�Ź��� 2-�Ż݄�
	 * @param x
	 *            ���ĵ㾭������
	 * @param y
	 *            ���ĵ�γ������
	 * @param r
	 *            �����뾶����ô���10000���ڣ�Ҫ��Ȼ��Χ̫��Ӱ��Ч�ʡ���λ����
	 * @param sno
	 *            ��ʼ��ţ���1��ʼ��Ĭ��Ϊ1
	 * @param num
	 *            ����Ą�������ÿ�����ȡ1000��
	 * @param sort
	 *            Ĭ�����򡪡�SORT_DEFAULT���������򡪡�SORT_DISTANCE�������ĵ���Ч��
	 * @param version
	 *            �ӿڰ汾����1��ʼ
	 * @param userid
	 *            �û�ID��������null
	 * @param session
	 *            �ỰID��������null
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-18 ����4:45:34
	 */
	public static CldSapReturn getGroupCouponList(String f, long city,
			String code, int t, long x, long y, int r, int sno, int num,
			int sort, int version, String userid, String session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("f", f);
		map.put("city", city);
		map.put("code", code);
		map.put("t", t);
		map.put("c", x + "," + y);
		map.put("r", r);
		map.put("sno", sno);
		map.put("num", num);
		map.put("sort", sort);
		map.put("version", version);
		CldSapParser.putStringToMap(map, "userid", userid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParmsNoEncode(map,
				CldSapUtil.getNaviSvrBD() + "search_coupons.php", null);
		return errRes;
	}

	/**
	 * ��ȡ�Ź�/�Ż�����(806)
	 * 
	 * @param uid
	 *            ��uid������ָ��
	 * @param t
	 *            1-�Ź���2-�Żݣ�����ָ��
	 * @param version
	 *            �ӿڰ汾����1��ʼ
	 * @param userid
	 *            �û�ID��������null
	 * @param session
	 *            �ỰID��������null
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-18 ����5:37:51
	 */
	public static CldSapReturn getGroupCouponDetails(String uid, int t,
			int version, String userid, String session) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (t == 2) {
			map.put("cuid", uid);
		} else {
			map.put("guid", uid);
		}
		map.put("version", version);
		CldSapParser.putStringToMap(map, "userid", userid);
		CldSapParser.putStringToMap(map, "session", session);
		CldSapReturn errRes = CldKBaseParse.getGetParmsNoEncode(map,
				CldSapUtil.getNaviSvrBD() + "get_coupons_detail.php", null);
		return errRes;
	}
}
