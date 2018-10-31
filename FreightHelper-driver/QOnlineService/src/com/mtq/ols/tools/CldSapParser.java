/*
 * @Title CldSapParser.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-3-17 ����2:29:23
 * @version 1.0
 */
package com.mtq.ols.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;

import android.text.TextUtils;

import com.cld.gson.Gson;
import com.cld.gson.JsonArray;
import com.cld.gson.JsonObject;
import com.cld.gson.JsonParser;
import com.mtq.ols.sap.CldSapUtil;
import com.mtq.ols.tools.err.CldOlsErrManager.CldOlsErrCode;
import com.mtq.ols.tools.parse.CldKReturn;



/**
 * ������
 * 
 * @author Zhouls
 * @date 2015-3-17 ����2:29:23
 */
public class CldSapParser {
	/**
	 * ��json���л�ΪT��Ӧ�Ķ���
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 * @return T
	 * @author Zhouls
	 * @date 2015-3-18 ����3:53:54
	 */
	public static <T> T fromJson(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
		}
		return t;
	}

	/**
	 * ����
	 * 
	 * @param jsonString
	 * @param cls
	 * @param errRes
	 * @return
	 * @return T
	 * @author Zhouls
	 * @date 2015-4-3 ����12:00:41
	 */
	public static <T> T parseJson(String jsonString, Class<T> cls,
			CldSapReturn errRes) {
		T t = null;
		errRes.jsonReturn = jsonString;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
			if (null != t) {
				if (CldSapParser.hasKey(cls, "errcode")) {
					/**
					 * ���t ��errcode����
					 */
					errRes.errCode = (Integer) CldSapParser
							.getFieldValueByName("errcode", t);
					errRes.errMsg = (String) CldSapParser.getFieldValueByName(
							"errmsg", t);
				} else {
					if (CldSapParser.hasKey(cls.getSuperclass(), "errcode")) {
						/**
						 * ������� ��errcode����
						 */
						errRes.errCode = (Integer) CldSapParser
								.getFieldValueByName("errcode", t);
						errRes.errMsg = (String) CldSapParser
								.getFieldValueByName("errmsg", t);

					} else {
						errRes.errCode = 0;
					}
				}
			} else {
				errRes.errCode = -105;
				errRes.errMsg = "�����쳣";
			}
		} catch (Exception e) {
			errRes.errCode = -105;
			errRes.errMsg = "�����쳣";
		}
		return t;
	}
	
	/**
	 * ��������
	 * 
	 * @param t
	 * @param cls
	 * @param errRes
	 * @return void
	 * @author Zhouls
	 * @date 2015-8-11 ����9:06:39
	 */
	public static <T> void parseObject(T t, Class<T> cls, CldSapReturn errRes) {
		if (null != t) {
			if (CldSapParser.hasKey(cls, "errcode")) {
				/**
				 * ���t ��errcode����
				 */
				errRes.errCode = (Integer) CldSapParser.getFieldValueByName(
						"errcode", t);
				errRes.errMsg = (String) CldSapParser.getFieldValueByName(
						"errmsg", t);
			} else {
				if (CldSapParser.hasKey(cls.getSuperclass(), "errcode")) {
					/**
					 * ������� ��errcode����
					 */
					errRes.errCode = (Integer) CldSapParser
							.getFieldValueByName("errcode", t);
					errRes.errMsg = (String) CldSapParser.getFieldValueByName(
							"errmsg", t);
				} else {
					errRes.errCode = 0;
				}
			}
		} else {
			errRes.errCode = CldOlsErrCode.PARSE_ERR;
			errRes.errMsg = "�����쳣";
		}
	}

	/**
	 * ��ȡJson�б�
	 * 
	 * @param jsonString
	 * @param name
	 * @return
	 * @return JsonArray
	 * @author Zhouls
	 * @date 2015-3-23 ����12:06:25
	 */
	public static JsonArray fromJsonArray(String jsonString, String name) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
		JsonArray jsonArray = jsonObject.getAsJsonArray(name);
		return jsonArray;
	}
	
	/**
	 * ��ȡJson
	 * 
	 */
	public static JsonObject fromJsonObject(String jsonString, String name ,String name2) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
		
		if(jsonObject == null)
			return null;
		
		JsonObject jsonObj = jsonObject.getAsJsonObject(name);
		
		if(jsonObj == null)
			return null;
		
		JsonObject jsonRes = jsonObj.getAsJsonObject(name2);
		
		if(jsonRes == null)
			return null;
		
		return jsonRes;
	}

	/**
	 * ��map�����л�Ϊjson�ַ���
	 * 
	 * @param map
	 *            ������
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-18 ����3:56:16
	 */
	public static <T> String mapToJson(Map<String, T> map) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(map);
		return jsonStr;
	}

	/**
	 * ���������л�Ϊjson�ַ���
	 * 
	 * @param o
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-20 ����3:06:12
	 */
	public static String objectToJson(Object o) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(o);
		return jsonStr;
	}

	/**
	 * ����ֵ��ΪNull���
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-1 ����5:03:47
	 */
	public static void putStringToMap(Map<String, Object> map, String key,
			String value) {
		if (null != value) {
			map.put(key, value);
		}
	}

	/**
	 * ����ֵ��Ϊ-1��int���
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-2 ����2:30:13
	 */
	public static void putIntToMap(Map<String, Object> map, String key,
			int value) {
		if (-1 != value) {
			map.put(key, value);
		}
	}

	/**
	 * ����ֵ��Ϊ0��long���
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-2 ����2:30:16
	 */
	public static void putLongToMap(Map<String, Object> map, String key,
			long value) {
		if (0 != value) {
			map.put(key, value);
		}
	}

	/**
	 * ��ȡЭ���md5Source��
	 * 
	 * @param o
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-18 ����9:14:17
	 */
	public static String formatSource(Object o) {
		if (null != o) {
			/**
			 * ��ȡ�����е����������飨�Ѱ�ascii�������ź�˳��
			 */
			String[] parms = getFiledName(o);
			if (null != parms) {
				String md5Source = "";
				for (int i = 0; i < parms.length; i++) {
					String parm = (String) getFieldValueByName(parms[i], o);
					if (i != 0) {
						if (!TextUtils.isEmpty(parm)) {
							md5Source = md5Source + "&" + parms[i] + "=" + parm;
						}
					} else {
						if (!TextUtils.isEmpty(parm)) {
							md5Source = md5Source + parms[i] + "=" + parm;
						}
					}
				}
				return md5Source;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * ��ȡЭ���md5Source��
	 * 
	 * @param map
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-18 ����9:54:09
	 */
	@SuppressWarnings("rawtypes")
	public static String formatSource(Map<String, Object> map) {
		if (null != map) {
			int size = map.size();
			String[] parms = new String[size];
			Iterator<?> iter = map.entrySet().iterator();
			int i = 0;
			String md5Source = "";
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				parms[i] = (String) entry.getKey();
				i++;
			}
			BubbleSort.sort(parms);
			for (i = 0; i < parms.length; i++) {
				if (i != 0) {
					if (!TextUtils.isEmpty(parms[i])) {
						md5Source = md5Source + "&" + parms[i] + "="
								+ map.get(parms[i]);
					}
				} else {
					if (!TextUtils.isEmpty(parms[i])) {
						md5Source = md5Source + parms[i] + "="
								+ map.get(parms[i]);
					}
				}
			}
			return md5Source;
		} else {
			return "";
		}
	}

	/**
	 * ��ȡURL�������
	 * 
	 * @param map
	 *            �����������
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-6-23 ����9:20:46
	 */
	@SuppressWarnings("rawtypes")
	public static String formatUrlSource(Map<String, Object> map) {
		if (null != map) {
			int size = map.size();
			String[] parms = new String[size];
			Iterator<?> iter = map.entrySet().iterator();
			int i = 0;
			String md5Source = "";
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				parms[i] = (String) entry.getKey();
				i++;
			}
			BubbleSort.sort(parms);
			for (i = 0; i < parms.length; i++) {
				if (i != 0) {
					if (!TextUtils.isEmpty(parms[i])) {
						md5Source = md5Source
								+ "&"
								+ parms[i]
								+ "="
								+ CldSapUtil.getUrlEncodeString(map.get(
										parms[i]).toString());
					}
				} else {
					if (!TextUtils.isEmpty(parms[i])) {
						md5Source = md5Source
								+ parms[i]
								+ "="
								+ CldSapUtil.getUrlEncodeString(map.get(
										parms[i]).toString());
					}
				}
			}
			return md5Source;
		} else {
			return "";
		}
	}

	/**
	 * ������������ȡ����ֵ
	 * 
	 * @param fieldName
	 *            ������
	 * @param o
	 *            ����
	 * @return
	 * @return Object
	 * @author Zhouls
	 * @date 2015-3-18 ����9:15:04
	 */
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��ȡ����������
	 * 
	 * @param o
	 * @return
	 * @return String[]
	 * @author Zhouls
	 * @date 2015-3-18 ����9:15:30
	 */
	public static String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	/**
	 * ���Ƿ���Name ����
	 * 
	 * @param o
	 * @param name
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-3-19 ����10:51:51
	 */
	@SuppressWarnings("rawtypes")
	public static boolean hasKey(Class o, String name) {
		Field[] fields = o.getDeclaredFields();
		if (!TextUtils.isEmpty(name)) {
			for (int i = 0; i < fields.length; i++) {
				if (name.equals(fields[i].getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Md5����
	 * 
	 * @param sourceStr
	 *            ����Դ��
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-18 ����8:57:35
	 */
	public static String MD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	/**
	 * 
	 * ð������
	 * 
	 * @author Zhouls
	 * @date 2015-3-18 ����8:55:13
	 */
	public static class BubbleSort {

		/**
		 * ð������Ascii���С��������ѭ�������С�Σ�ÿ�ν����ķŵ����
		 * 
		 * @param array
		 *            ��������
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-18 ����8:55:24
		 */
		public static void sort(String[] array) {
			if (null != array) {
				for (int i = 1; i < array.length; i++) {
					for (int j = 0; j < array.length - i; j++) {
						if (compare(array[j + 1], array[j])) {
							String temp = array[j];
							array[j] = array[j + 1];
							array[j + 1] = temp;
						}
					}
				}
			}
		}

		/**
		 * 2���ַ����Ƚ�Ascii ���С
		 * 
		 * @param strA
		 * @param strB
		 * @return ��A<B ����true ���򷵻�false
		 * @return boolean
		 * @author Zhouls
		 * @date 2015-3-18 ����8:55:45
		 */
		public static boolean compare(String strA, String strB) {
			char[] a = strA.toCharArray();
			char[] b = strB.toCharArray();
			int cycNum = a.length < b.length ? a.length : b.length;
			for (int i = 0; i < cycNum; i++) {
				if (a[i] == b[i]) {
					continue;
				} else {
					if (a[i] < b[i]) {
						return true;
					} else {
						return false;
					}
				}
			}
			if (a.length != b.length) {
				/**
				 * һ������һ����ǰ׺
				 */
				return false;
			} else {
				/**
				 * 2���ַ�����ȫ���
				 */
				return true;
			}
		}
	}
}
