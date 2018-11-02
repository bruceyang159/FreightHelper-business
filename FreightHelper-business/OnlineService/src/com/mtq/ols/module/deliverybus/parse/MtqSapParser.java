package com.mtq.ols.module.deliverybus.parse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;

import android.text.TextUtils;

import com.cld.gson.Gson;
import com.cld.log.CldLog;
import com.mtq.ols.module.delivery.tool.CldSapParser;
import com.mtq.ols.tools.err.CldOlsErrManager.CldOlsErrCode;

public class MtqSapParser {
	
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
	public static <T> void parseObject(T t, Class<T> cls, MtqSapReturn errRes) {
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
					 * 
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
			MtqSapReturn errRes) {
		T t = null;
		errRes.jsonReturn = jsonString;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
			if (null != t) {
				if (hasKey(cls, "errcode")) {
					/**
					 * ���t ��errcode����
					 */
					errRes.errCode = (Integer) getFieldValueByName("errcode", t);
					errRes.errMsg = (String) getFieldValueByName("errmsg", t);
				} else {
					if (hasKey(cls.getSuperclass(), "errcode")) {
						/**
						 * ������� ��errcode����
						 */
						errRes.errCode = (Integer) getFieldValueByName(
								"errcode", t);
						errRes.errMsg = (String) getFieldValueByName("errmsg",
								t);

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
		CldLog.e("MtqSapParser", "fieldName: " + fieldName);
		CldLog.e("MtqSapParser", "Object: " + o.toString());
		
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			CldLog.e("MtqSapParser", "firstLetter: " + firstLetter);
			String getter = "get" + firstLetter + fieldName.substring(1);
			CldLog.e("MtqSapParser", "getter: " + getter);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			CldLog.e("MtqSapParser", "value: " + value);
			return value;
		} catch (Exception e) {
			CldLog.e("MtqSapParser", "Exception: " + e.toString());
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
