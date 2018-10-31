/*
 * @Title CldOlsNetUtils.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-12-25 ����9:26:26
 * @version 1.0
 */
package com.mtq.ols.module.delivery.tool;

import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;
import android.util.Log;


import com.cld.log.CldLog;
import com.mtq.apitest.activity.DebugTool;
import com.mtq.ols.module.delivery.tool.CldPubFuction.MD5Util;
import com.mtq.ols.tools.parse.CldKReturn;

/**
 * ���縨����
 * 
 * @author Zhouls
 * @date 2015-12-25 ����9:26:26
 */
public class CldOlsNetUtils {
	/**
	 * ��ȡGet����ƴ�ӵ�URL
	 * 
	 * @param map
	 * @param urlHead
	 * @param key
	 * @return
	 * @return ProtReturn
	 * @author Zhouls
	 * @date 2015-4-3 ����11:30:06
	 */
	public static CldKReturn getGetParms(Map<String, Object> map,
			String urlHead, String key) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			String md5Source = CldSapParser.formatSource(map);
			String urlSource = CldSapParser.formatUrlSource(map);
			String strUrl = urlHead + "?";
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ��,sign���ܣ����ɵ�url��sign����
				 */
				md5Source += key;
				CldLog.d("ols", md5Source);
				String sign = MD5Util.MD5(md5Source);
				strUrl = strUrl + urlSource + "&sign=" + sign;
			} else {
				strUrl = strUrl + urlSource;
			}
			CldLog.i("ols", strUrl);
			errRes.url = strUrl;
		}
		return errRes;
	}

	public static CldKReturn getGetParms(Map<String, Object> map,
			String outMd5Source, String urlHead, String key,
			boolean isUrlEncoder) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			String md5Source = (!TextUtils.isEmpty(outMd5Source)) ? outMd5Source
					: CldSapParser.formatSource(map);
			String urlSource = CldSapParser.formatUrlSource(map, isUrlEncoder);
			String strUrl = urlHead + "?";
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ��,sign���ܣ����ɵ�url��sign����
				 */
				md5Source += key;
				
				CldLog.d("ols", md5Source);
				String sign = MD5Util.MD5(md5Source);
				strUrl = strUrl + urlSource + "&sign=" + sign;
			} else {
				strUrl = strUrl + urlSource;
			}
			CldLog.i("ols", strUrl);
			errRes.url = strUrl;
		}
		return errRes;
	}

	/**
	 * ����Կ��Ҫ����get
	 * 
	 * @param map
	 * @param urlHead
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-12-9 ����12:03:12
	 */
	public static CldKReturn getGetParms(Map<String, Object> map, String urlHead) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			String md5Source = CldSapParser.formatSource(map);
			String urlSource = CldSapParser.formatUrlSource(map);
			String strUrl = urlHead + "?";
			String sign = MD5Util.MD5(md5Source);
			strUrl = strUrl + urlSource + "&sign=" + sign;
			CldLog.i("ols", strUrl);
			errRes.url = strUrl;
		}
		return errRes;
	}

	/**
	 * ����GET����ͳһURLencode
	 * 
	 * @param map
	 * @param urlHead
	 * @param key
	 * @return
	 * @return CldKReturn
	 * @author Zhouls
	 * @date 2015-7-9 ����5:27:18
	 */
	public static CldKReturn getGetParmsNoEncode(Map<String, Object> map,
			String urlHead, String key) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			String md5Source = CldSapParser.formatSource(map);
			String urlSource = md5Source;
			String strUrl = urlHead + "?";
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ��,sign���ܣ����ɵ�url��sign����
				 */
				md5Source += key;
				String sign = MD5Util.MD5(md5Source);
				strUrl = strUrl + urlSource + "&sign=" + sign;
			} else {
				strUrl = strUrl + urlSource;
			}
			CldLog.i("ols", strUrl);
			errRes.url = strUrl;
		}
		return errRes;
	}

	/**
	 * ��ȡPost����ƴ�ӵ�URL
	 * 
	 * @param map
	 * @param urlHead
	 * @param key
	 * @return
	 * @return ProtReturn
	 * @author Zhouls
	 * @date 2015-4-3 ����11:42:43
	 */
	public static CldKReturn getPostParms(Map<String, Object> map,
			String urlHead, String key) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			/**
			 * map��Ϊ��
			 */
			String md5Source = CldSapParser.formatSource(map);
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ�գ���Ҫsign ������֤
				 */
				md5Source += key;
				String sign = MD5Util.MD5(md5Source);
				map.put("sign", sign);
			}
			String strUrl = urlHead;
			CldLog.i("ols", md5Source);
			CldLog.i("ols", strUrl);
			String strPost = CldSapParser.mapToJson(map);
			CldLog.i("ols", strPost);
			errRes.url = urlHead;
			errRes.jsonPost = strPost;
		}
		return errRes;
	}

	/**
	 * ֧��ģ���Լ���md5source
	 * @param map
	 * @param outMd5Source
	 * @param urlHead
	 * @param key
	 * @return
	 * @return CldKReturn
	 * @author Zhouls
	 * @date 2016-4-8 ����11:15:24
	 */
	public static CldKReturn getPostParms(Map<String, Object> map,
			String outMd5Source, String urlHead, String key) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			/**
			 * map��Ϊ��
			 */
			String md5Source = (!TextUtils.isEmpty(outMd5Source)) ? outMd5Source
					: CldSapParser.formatSource(map);
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ�գ���Ҫsign ������֤
				 */
				md5Source += key;
				String sign = MD5Util.MD5(md5Source);
				map.put("sign", sign);
			}
			String strUrl = urlHead;
			CldLog.i("ols", md5Source);
			CldLog.i("ols", strUrl);
			String strPost = CldSapParser.mapToJson(map);
			CldLog.i("ols", strPost);
			errRes.url = urlHead;
			errRes.jsonPost = strPost;
		}
		return errRes;
	}

	/**
	 * postStringArray
	 * @param map
	 * @param outMd5Source
	 * @param urlHead
	 * @param key
	 * @return
	 * @return CldKReturn
	 * @author Zhouls
	 * @date 2016-5-4 ����2:34:14
	 */
	public static CldKReturn getCustPostParms(Map<String, Object> map,
			String outMd5Source, String urlHead, String key) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			/**
			 * map��Ϊ��
			 */
			String md5Source = (!TextUtils.isEmpty(outMd5Source)) ? outMd5Source
					: CldSapParser.formatSource(map);
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ�գ���Ҫsign ������֤
				 */
				md5Source += key;
				String sign = MD5Util.MD5(md5Source);
				map.put("sign", sign);
			}
			String strUrl = urlHead;
			CldLog.i("olsmd5Source", md5Source);
			CldLog.i("olsstrUrl", strUrl);
			errRes.url = urlHead;
			String strPostMap = "";
			Map<String, String> dataMap = new HashMap<String, String>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (!TextUtils.isEmpty(entry.getKey())
						&& null != entry.getValue()
						&& !TextUtils.isEmpty(entry.getValue().toString())) {
					
					CldLog.d("olscheckmap", entry.getKey() + "="
							+ entry.getValue().toString());
					
					dataMap.put(entry.getKey(), entry.getValue().toString());
					strPostMap = strPostMap + "&" + entry.getKey() + "="
							+ entry.getValue().toString();
				}
			}
			CldLog.d("ols", strPostMap);
			errRes.mapPost = dataMap;
		}
		return errRes;
	}

	/**
	 * ����Կ��Ҫ���ܵ�Post
	 * 
	 * @param map
	 * @param urlHead
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-12-9 ����12:03:49
	 */
	public static CldKReturn getPostParms(Map<String, Object> map,
			String urlHead) {
		CldKReturn errRes = new CldKReturn();
		if (null != map) {
			/**
			 * map��Ϊ��
			 */
			String md5Source = CldSapParser.formatSource(map);
			String sign = MD5Util.MD5(md5Source);
			map.put("sign", sign);
			String strUrl = urlHead;
			CldLog.i("ols", md5Source);
			CldLog.i("ols", strUrl);
			String strPost = CldSapParser.mapToJson(map);
			CldLog.i("ols", strPost);
			errRes.url = urlHead;
			errRes.jsonPost = strPost;
		}
		return errRes;
	}

}
