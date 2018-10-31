package com.mtq.ols.module.deliverybus.parse;

import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;

import com.cld.log.CldLog;
import com.mtq.ols.module.delivery.tool.CldPubFuction.MD5Util;
import com.mtq.ols.sap.CldSapUtil;

public class MtqBaseParse {

	public static final String TAG = "deliverybus";
	
	/**
	 * ����Կ��Ҫ���ܵġ�����Ҫǩ����Postƴ�ӷ�ʽ
	 * 
	 * @param map
	 * @param urlHead
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn getNoSignPostParms(Map<String, Object> map, String urlHead) {
		MtqSapReturn errRes = new MtqSapReturn();
		if (map != null) {
			CldLog.i(TAG, "urlHead: " + urlHead);
			/**
			 * map��Ϊ��
			 */
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
			CldLog.i(TAG, "strPostMap: " + strPostMap);
			errRes.mapPost = dataMap;
		}
		return errRes;
	}

	/**
	 * postStringArray
	 * 
	 * @param map
	 * @param urlHead
	 * @param key
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn getCustPostParms(Map<String, Object> map,
			String urlHead, String key) {
		MtqSapReturn errRes = new MtqSapReturn();
		if (map != null) {
			CldLog.i(TAG, "urlHead: " + urlHead);
			CldLog.i(TAG, "key: " + key);
			/**
			 * map��Ϊ��
			 */
			String md5Source = MtqSapParser.formatSource(map);
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ�գ���Ҫsign ������֤
				 */
				md5Source += key;
				String sign = MD5Util.MD5(md5Source);
				map.put("sign", sign);
				CldLog.i(TAG, "sign: " + sign);
			}
			CldLog.i(TAG, "md5Source: " + md5Source);
			
			errRes.url = urlHead;
			String strPost = MtqSapParser.mapToJson(map);
			CldLog.i(TAG, "strPost: " + strPost);
			errRes.jsonPost = strPost;
			
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
			CldLog.i(TAG, "strPostMap: " + strPostMap);
			errRes.mapPost = dataMap;
		}
		return errRes;
	}

	/**
	 * postStringArray
	 * 
	 * @param map
	 * @param outMd5Source
	 * @param urlHead
	 * @param key
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn getCustPostParms(Map<String, Object> map,
			String outMd5Source, String urlHead, String key) {
		MtqSapReturn errRes = new MtqSapReturn();
		if (map != null) {
			CldLog.i(TAG, "urlHead: " + urlHead);
			CldLog.i(TAG, "key: " + key);
			/**
			 * map��Ϊ��
			 */
			String md5Source = (!TextUtils.isEmpty(outMd5Source)) ? outMd5Source
					: MtqSapParser.formatSource(map);
			if (!TextUtils.isEmpty(key)) {
				/**
				 * ��Կ��Ϊ�գ���Ҫsign ������֤
				 */
				md5Source += key;
				String sign = MD5Util.MD5(md5Source);
				map.put("sign", sign);
			}
			CldLog.i(TAG, "md5Source: " + md5Source);
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
			CldLog.i(TAG, "strPostMap: " + strPostMap);
			errRes.mapPost = dataMap;
		}
		return errRes;
	}
	
	
	
	
	/**
	 * ����Կ��Ҫ���ܵ�Postƴ�ӷ�ʽ
	 * 
	 * @param map
	 * @param urlHead
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn getPostParms(Map<String, Object> map,
			String urlHead) {
		MtqSapReturn errRes = new MtqSapReturn();
		if (map != null) {
			CldLog.i(TAG, "urlHead: " + urlHead);
			/**
			 * map��Ϊ��
			 */
			String md5Source = MtqSapParser.formatSource(map);
			CldLog.i(TAG, "md5Source: " + md5Source);
			String sign = MD5Util.MD5(md5Source);
			CldLog.i(TAG, "sign: " + sign);
			map.put("sign", sign);
			String strPost = MtqSapParser.mapToJson(map);
			CldLog.i(TAG, "strPost: " + strPost);
			errRes.url = urlHead;
			errRes.jsonPost = strPost;
		}
		return errRes;
	}

	/**
	 * ����Կ��Ҫ���ܵ�Postƴ�ӷ�ʽ
	 * 
	 * @param map
	 * @param urlHead
	 * @param key
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn getPostParms(Map<String, Object> map,
			String urlHead, String key) {
		MtqSapReturn errRes = new MtqSapReturn();
		if (map != null) {
			CldLog.i(TAG, "urlHead: " + urlHead);
			CldLog.i(TAG, "key: " + key);
			/**
			 * map��Ϊ��
			 */
			String urlSource = MtqSapParser.formatSource(map);
			String md5Source = urlSource;
			CldLog.i(TAG, "md5Source: " + md5Source);
			if (!TextUtils.isEmpty(key)) {
				md5Source += key;
			}
			String sign = CldSapUtil.MD5(md5Source);
			CldLog.i(TAG, "sign: " + sign);
			map.put("sign", sign);
			String strPost = MtqSapParser.mapToJson(map);
			CldLog.i(TAG, "strPost: " + strPost);
			errRes.url = urlHead;
			errRes.jsonPost = strPost;
		}
		return errRes;
	}

	/**
	 * ����Կ��Ҫ���ܵ�Postƴ�ӷ�ʽ
	 * 
	 * @param map
	 * @param outMd5Source
	 *            �ⲿ��������ǩ���Ĳ���
	 * @param urlHead
	 * @param key
	 * @return MtqSapReturn
	 * 
	 * @author zhaoqy
	 * @date 2017-06-15
	 */
	public static MtqSapReturn getPostParms(Map<String, Object> map,
			String outMd5Source, String urlHead, String key) {
		MtqSapReturn errRes = new MtqSapReturn();
		if (map != null) {
			CldLog.i(TAG, "urlHead: " + urlHead);
			CldLog.i(TAG, "key: " + key);
			/**
			 * map��Ϊ��
			 */
			String urlSource = (!TextUtils.isEmpty(outMd5Source)) ? outMd5Source
					: MtqSapParser.formatSource(map);
			String md5Source = urlSource;
			CldLog.i(TAG, "md5Source: " + md5Source);
			if (!TextUtils.isEmpty(key)) {
				md5Source += key;
			}
			String sign = CldSapUtil.MD5(md5Source);
			CldLog.i(TAG, "sign: " + sign);
			map.put("sign", sign);
			String strPost = MtqSapParser.mapToJson(map);
			CldLog.i(TAG, "strPost: " + strPost);
			errRes.url = urlHead;
			errRes.jsonPost = strPost;
		}
		return errRes;
	}
}
