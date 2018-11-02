/*
 * @Title CldSapUtil.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:59
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.text.TextUtils;
import com.cld.log.CldLog;
import com.mtq.ols.api.CldKConfigAPI;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.bll.CldKConfig.ConfigDomainType;


/**
 * Э��㹫��������
 * 
 * @author Zhouls
 * @date 2014-9-23 ����11:02:38
 */
public class CldSapUtil {

	/**
	 * �����������ȡ�ַ����������������
	 * 
	 * @param is
	 *            the is
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-29 ����9:24:52
	 */
	public static String getStringFromFileIn(InputStream is) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(is, "GBK"));
			String data = "";
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
		} catch (IOException e1) {
		}
		String result = sb.toString();
		return result;
	}

	/**
	 * �ļ�תbyte[]
	 * 
	 * @param file
	 * @return
	 * @return byte[]
	 * @author Zhouls
	 * @date 2015-3-4 ����5:55:03
	 */
	public static byte[] fileToByte(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * ��ȡ��������ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-24 ����12:00:16
	 */
	public static String getNaviSvrKConfig() {
		String naviSvrKC;
		if (CldBllUtil.getInstance().isTestVersion()) {
			// ���԰汾
			naviSvrKC = "http://sttest.careland.com.cn/";
		} else {
			// ��ʽ�汾
			naviSvrKC = "http://st.careland.com.cn/";
		}
		return naviSvrKC;
	}

	/**
	 * ��ȡBD����ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-11-17 ����4:43:08
	 */
	public static String getNaviSvrBD() {
		String naviSvrBd = getBdTestDomain();
		if (TextUtils.isEmpty(naviSvrBd)) {
			naviSvrBd = getSvrAddr(ConfigDomainType.NAVI_SVR_BD);
		}
		return naviSvrBd;
	}

	/**
	 * ��ȡ���ߵ�������ͷ
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-4-8 ����4:34:48
	 */
	public static String getNaviSvrON() {
		String naviSvrOn = getOnTestDomain();
		if (TextUtils.isEmpty(naviSvrOn)) {
			naviSvrOn = getSvrAddr(ConfigDomainType.NAVI_SVR_ONLINENAVI);
		}
		return naviSvrOn;
	}

	/**
	 * ��ȡ�ʻ�ϵͳ����ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-15 ����5:44:36
	 */
	public static String getNaviSvrKA() {
		return getSvrAddr(ConfigDomainType.NAVI_SVR_KACCOUNT);
	}

	/**
	 * ��ȡ��ɢ�ӿ�����ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-25 ����8:31:49
	 */
	public static String getNaviSvrPub() {
		return getSvrAddr(ConfigDomainType.NAVI_SVR_PUB);
	}

	/**
	 * ��ȡWebsite����ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-25 ����8:31:49
	 */
	public static String getNaviSvrWS() {
		return getSvrAddr(ConfigDomainType.NAVI_SVR_WEBSITE);
	}

	/**
	 * ��ȡ��Ϣϵͳ����ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-15 ����5:49:17
	 */
	public static String getNaviSvrMsg() {
		String naviSvrMsg = getMsgTestDomain();
		if (TextUtils.isEmpty(naviSvrMsg)) {
			naviSvrMsg = getSvrAddr(ConfigDomainType.NAVI_SVR_MSG);
		}
		return naviSvrMsg;
	}

	/**
	 * ��ȡһ��ͨ����ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-29 ����4:54:06
	 */
	public static String getNaviSvrPPT() {
		String naviSvrPpt = getKAKeyCallTestDomain();
		if (TextUtils.isEmpty(naviSvrPpt)) {
			naviSvrPpt = getSvrAddr(ConfigDomainType.NAVI_SVR_PPTCALL);
		}
		return naviSvrPpt;
	}

	/**
	 * ��ȡλ�÷�������ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-15 ����5:49:17
	 */
	public static String getNaviSvrPos() {
		String naviSvrPos = getPosTestDomain();
		if (TextUtils.isEmpty(naviSvrPos)) {
			naviSvrPos = getSvrAddr(ConfigDomainType.NAVI_SVR_POS);
		}
		return naviSvrPos;
	}

	/**
	 * ��ȡ�û���Ȩ����ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-15 ����5:44:36
	 */
	public static String getNaviSvrAC() {
		return getSvrAddr(ConfigDomainType.NAVI_SVR_AC);
	}

	/**
	 * ��ȡ·������ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-12-26 ����5:07:44
	 */
	public static String getNaviSvrRTI() {
		return getSvrAddr(ConfigDomainType.NAVI_SVR_RTI);
	}

	/**
	 * ��ȡK������ͷ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-12-26 ����5:07:48
	 */
	public static String getNaviSvrKC() {
		return getSvrAddr(ConfigDomainType.NAVI_SVR_KC);
	}
	
	/**
	 * ��ȡ��������ͷ
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-7-8 ����8:47:40
	 */
	public static String getNaviSvrWeather() {
		String addr = getSvrAddr(ConfigDomainType.NAVI_SVR_KWEATHER);
		return addr;
	}
	
	/** ��ȡ��������ͷ
	 * @return
	 * @return String
	 * @author buxc
	 * @date 2016��3��4�� ����4:04:11
	 */ 
	public static String getNaviSvrSearch() {
		String addr = getSvrAddr(ConfigDomainType.NAVI_SVR_SEARCH);
		return addr;
	}
	
	/** ��ȡ��������ͷ
	 * @return
	 * @return String
	 * @author buxc
	 * @date 2016��3��4�� ����4:04:11
	 */ 
	public static String getNaviSvrHY() {
		String addr = getSvrAddr(ConfigDomainType.NAVI_SVR_HY);
		return addr;
	}

	
	/** ��ȡ����������������Ŀ¼
	 * @return String
	 * @author weian
	 * @date 2016��07��14�� ����11:31:11
	 */ 
	public static String getNaviHyDownload() {
		String addr = getSvrAddr(ConfigDomainType.NAVI_SVR_HY_DOWNLOAD);
		return addr;
	}
	
	
	/**
	 * ��ȡ����ͷ
	 * 
	 * @param svrType
	 *            ��������
	 * @return String ��������ͷ��Ϣ
	 * @author huagx
	 * @date 2014-8-19 ����5:56:12
	 */
	public static String getSvrAddr(int svrType) {
		return CldKConfigAPI.getInstance().getSvrDomain(svrType);
	}
	
	/**
	 * @annotation :��ȡ�����ϱ�����ͷ
	 * @author : yuyh
	 * @date :2016-8-27����3:30:13
	 * @parama :
	 * @return :
	 **/
	public static String getNaviSvrPo()
	{
		String addr = getSvrAddr(ConfigDomainType.NAVI_SVR_PO);
		return addr;
	}

	/**
	 * ��ȡ����IP��ַ
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-13 ����10:51:15
	 */
	public static String getLocalIpAddress() {
		String ip = "192.168.1.1";
		try {
			Socket socket = new Socket("www.careland.com.cn", 80);
			ip = socket.getLocalAddress().toString().substring(1);
		} catch (Exception e) {
		}
		return ip;
	}

	/**
	 * ��ȡ����ʱ���
	 * 
	 * @return long
	 * @author Zhouls
	 * @date 2014-9-19 ����10:11:51
	 */
	public static long getLocalTime() {
		long localTime = System.currentTimeMillis() / 1000;
		return localTime;
	}

	/**
	 * ���Ͷ���
	 * 
	 * @param address
	 *            ��ַ
	 * @param content
	 *            ����
	 * @param context
	 *            the context
	 * @return int
	 * @author Zhouls
	 * @date 2014-8-22 ����4:22:03
	 */
	public static int sendSMS(String address, String content, Context context) {
		int errCode = -1;
		/**
		 * �����صķ���״̬
		 */
		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
				sentIntent, 0);
		// register the Broadcast Receivers
		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					CldLog.d("ols", "SENT_SMS_ACTION sendOK_sms");
					// ���ŷ��ͳɹ�
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					break;
				}
			}
		}, new IntentFilter(SENT_SMS_ACTION));
		/**
		 * �����صĽ���״̬
		 */
		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
		// create the deilverIntent parameter
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
				deliverIntent, 0);
		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				// ���ųɹ�
				CldLog.d("ols", "DELIVERED_SMS_ACTION sendOK_sms");
			}
		}, new IntentFilter(DELIVERED_SMS_ACTION));

		SmsManager smsManager = SmsManager.getDefault();
		List<String> divideContents = smsManager.divideMessage(content);
		for (String text : divideContents) {
			smsManager.sendTextMessage(address, null, text, sentPI, deliverPI);
		}
		return errCode;
	}

	/**
	 * ��ȡ�豸guid
	 * 
	 * @param timeStamp
	 *            the time stamp
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-22 ����1:59:07
	 */
	public static String getGuid(long timeStamp) {
		String guid;
		String imsi = CldBllUtil.getInstance().getImsi();
		String imei = CldBllUtil.getInstance().getImei();
		if (null == imsi) {
			imsi = "SIMCARDID";
		}
		if (null == imei) {
			imei = "IMEI";
		}
		guid = imsi + imei + timeStamp;
		guid = MD5(guid);
		return guid;
	}

	/**
	 * ��������ַ���
	 * 
	 * @param pwd_len
	 *            ������볤��
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-1-19 ����4:32:03
	 */
	public static String genRandomNum(int pwd_len) {
		// 35����Ϊ�����Ǵ�0��ʼ�ģ�26����ĸ+10�� ����
		final int maxNum = 36;
		int i; // ���ɵ������
		int count = 0; // ���ɵ�����ĳ���
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// �����������ȡ����ֵ����ֹ ���ɸ�����
			i = Math.abs(r.nextInt(maxNum)); // ���ɵ������Ϊ36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * �Ǳ����ֶ�(String)
	 * 
	 * @param strName
	 *            the str name
	 * @param str
	 *            the str
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-19 ����8:58:41
	 */
	public static String isStrParmRequest(String strName, String str) {
		if (null == str || str.length() <= 0) {
			return "";
		} else {
			return strName + str;
		}
	}

	/**
	 * �Ǳ����ֶΣ�int��
	 * 
	 * @param strName
	 *            the str name
	 * @param val
	 *            the val
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-19 ����9:00:35
	 */
	public static String isIntParmRequest(String strName, int val) {
		if (val == -1) {
			return "";
		} else {
			return strName + val;
		}
	}

	/**
	 * �Ǳ����ֶΣ�long��
	 * 
	 * @param strName
	 *            the str name
	 * @param val
	 *            the val
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-19 ����9:00:35
	 */
	public static String isLongParmRequest(String strName, long val) {
		if (val == 0) {
			return "";
		} else {
			return strName + val;
		}
	}

	/**
	 * MD5����
	 * 
	 * @param sourceStr
	 *            the source str
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-13 ����2:39:21
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
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return result;
	}

	/**
	 * ������Կ
	 * 
	 * @param keyCode
	 *            the key code
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-14 ����12:01:57
	 */
	public static String decodeKey(String keyCode) {
		if (TextUtils.isEmpty(keyCode)) {
			return "";
		} else {
			String key = keyCode.substring(keyCode.length() - 6);
			String str = keyCode.substring(0, keyCode.length() - 6);
			CldEDecrpy cldEDecrpy = new CldEDecrpy(str, key);
			keyCode = cldEDecrpy.decrypt();
			return keyCode;
		}
	}

	/**
	 * ������Կ(test����)
	 * 
	 * @param keyCode
	 *            the key code
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-14 ����12:01:57
	 */
	public static String ecodeKey(String keyCode) {
		return keyCode;
	}

	/**
	 * ��ʽ��Json�ַ���
	 * 
	 * @param content
	 *            the content
	 * @return String
	 * @author Zhouls
	 * @date 2014-12-17 ����9:27:28
	 */
	public static String formatJsonString(String content) {
		if (null != content) {
			content = content.substring(content.indexOf("{"),
					content.lastIndexOf("}") + 1);
		}
		return content;
	}

	/**
	 * ��Կ���� ����
	 * 
	 * @author Zhouls
	 * @date 2014-8-13 ����4:05:06
	 */
	public static class CldEDecrpy {
		/** The str. */
		private String str;
		/** The key box. */
		private int[] keyBox;

		/**
		 * Instantiates a new cld e decrpy.
		 * 
		 * @param str
		 *            the str
		 * @param key
		 *            the key
		 */
		public CldEDecrpy(String str, String key) {
			this.str = str;
			keyBox = new int[3];
			keyBox[0] = Integer.parseInt(key.substring(0, 2));
			keyBox[1] = Integer.parseInt(key.substring(2, 4));
			keyBox[2] = Integer.parseInt(key.substring(4, 6));
		}

		/**
		 * ����
		 * 
		 * @return char
		 * @author Zhouls
		 * @date 2014-8-13 ����5:13:26
		 */
		public String encrypt() {
			String enStr = "";
			int tempKey;
			for (int i = 0; i < str.length(); i++) {
				tempKey = keyBox[i % 3] % 24;
				enStr = enStr
						+ getChEncrpyt(getIndexEncrpyt(str.charAt(i)) + tempKey);
			}
			return enStr;
		}

		/**
		 * ����
		 * 
		 * @return char
		 * @author Zhouls
		 * @date 2014-8-13 ����5:00:18
		 */
		public String decrypt() {
			String deStr = "";
			int tempKey;
			for (int i = 0; i < str.length(); i++) {
				tempKey = keyBox[i % 3] % 24;
				deStr += getChDecrpyt(getIndexDecrpyt(str.charAt(i)) - tempKey);
			}
			return deStr;
		}

		/**
		 * ��ȡ�����ַ�
		 * 
		 * @param i
		 *            the i
		 * @return String
		 * @author Zhouls
		 * @date 2014-8-13 ����4:22:18
		 */
		private char getChEncrpyt(int i) {
			if (i >= 0 && i <= 25) {
				return chr(ord('a') + i);
			} else if (i >= 26 && i <= 35) {
				return chr(ord('0') + i - 26);
			} else if (i >= 36 && i <= 61) {
				return chr(ord('A') + i - 36);
			}
			return '0';
		}

		/**
		 * ��ȡ�����ַ�
		 * 
		 * @param i
		 *            the i
		 * @return String
		 * @author Zhouls
		 * @date 2014-8-13 ����4:21:09
		 */
		private char getChDecrpyt(int i) {
			if (i == 0) {
				return '-';
			} else if (i == 1) {
				return ';';
			} else if (i >= 2 && i <= 11) {
				return chr(i + ord('0') - 2);
			} else if (i >= 12 && i <= 37) {
				return chr(i + ord('A') - 12);
			}
			return '0';
		}

		/**
		 * ��ȡ��������
		 * 
		 * @param c
		 *            the c
		 * @return int
		 * @author Zhouls
		 * @date 2014-8-13 ����4:20:45
		 */
		private int getIndexDecrpyt(char c) {
			if (ord(c) >= ord('0') && ord(c) <= ord('9')) {
				return ord(c) + 26 - ord('0');
			} else if (ord(c) >= ord('A') && ord(c) <= ord('Z')) {
				return ord(c) + 36 - ord('A');
			} else {
				return ord(c) - ord('a');
			}
		}

		/**
		 * ��ȡ��������
		 * 
		 * @param c
		 *            the c
		 * @return int
		 * @author Zhouls
		 * @date 2014-8-13 ����4:22:13
		 */
		private int getIndexEncrpyt(char c) {
			if (c == '-') {
				return 0;
			}
			if (c == ';') {
				return 1;
			} else if (c >= '0' && c <= '9') {
				return c - '0' + 2;
			} else if (c >= 'A' && c <= 'Z') {
				return c - 'A' + 12;
			}
			return 0;
		}

		/**
		 * intתchar
		 * 
		 * @param i
		 *            the i
		 * @return char
		 * @author Zhouls
		 * @date 2014-8-13 ����4:31:41
		 */
		private char chr(int i) {
			char c = (char) i;
			return c;
		}

		/**
		 * charתint
		 * 
		 * @param c
		 *            the c
		 * @return int
		 * @author Zhouls
		 * @date 2014-8-13 ����4:29:52
		 */
		private int ord(char c) {
			int a = c;
			return a;
		}
	}

	/**
	 * Gzip ѹ������
	 * 
	 * @author Zhouls
	 * @date 2015-3-9 ����5:20:10
	 */
	public static class GZipUtils {
		public static final int BUFFER = 1024;
		public static final String EXT = ".gz";

		/**
		 * ѹ������
		 * 
		 * @param data
		 * @return
		 * @throws Exception
		 * @return byte[]
		 * @author Zhouls
		 * @date 2015-3-9 ����5:20:25
		 */
		public byte[] compress(byte[] data) throws Exception {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// ѹ��
			compress(bais, baos);
			byte[] output = baos.toByteArray();
			baos.flush();
			baos.close();
			bais.close();
			return output;
		}

		/**
		 * �ļ�ѹ��
		 * 
		 * @param file
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:20:42
		 */
		public void compress(File file) throws Exception {
			compress(file, true);
		}

		/**
		 * �ļ�ѹ��
		 * 
		 * @param file
		 * @param delete
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:20:59
		 */
		public void compress(File file, boolean delete) throws Exception {
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);
			compress(fis, fos);
			fis.close();
			fos.flush();
			fos.close();
			if (delete) {
				file.delete();
			}
		}

		/**
		 * ����ѹ��
		 * 
		 * @param is
		 * @param os
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:21:09
		 */
		public void compress(InputStream is, OutputStream os) throws Exception {
			GZIPOutputStream gos = new GZIPOutputStream(os);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = is.read(data, 0, BUFFER)) != -1) {
				gos.write(data, 0, count);
			}
			gos.finish();
			gos.flush();
			gos.close();
		}

		/**
		 * �ļ�ѹ��
		 * 
		 * @param path
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:21:18
		 */
		public void compress(String path) throws Exception {
			compress(path, true);
		}

		/**
		 * �ļ�ѹ��
		 * 
		 * @param path
		 * @param delete
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:21:28
		 */
		public void compress(String path, boolean delete) throws Exception {
			File file = new File(path);
			compress(file, delete);
		}

		/**
		 * ���ݽ�ѹ��
		 * 
		 * @param data
		 * @return
		 * @throws Exception
		 * @return byte[]
		 * @author Zhouls
		 * @date 2015-3-9 ����5:21:37
		 */
		public byte[] decompress(byte[] data) throws Exception {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// ��ѹ��
			decompress(bais, baos);
			data = baos.toByteArray();
			baos.flush();
			baos.close();
			bais.close();
			return data;
		}

		/**
		 * �ļ���ѹ��
		 * 
		 * @param file
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:21:45
		 */
		public void decompress(File file) throws Exception {
			decompress(file, true);
		}

		/**
		 * �ļ���ѹ��
		 * 
		 * @param file
		 * @param delete
		 *            �Ƿ�ɾ��ԭʼ�ļ�
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:21:52
		 */
		public void decompress(File file, boolean delete) throws Exception {
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(file.getPath().replace(
					EXT, ""));
			decompress(fis, fos);
			fis.close();
			fos.flush();
			fos.close();
			if (delete) {
				file.delete();
			}
		}

		/**
		 * ���ݽ�ѹ��
		 * 
		 * @param is
		 * @param os
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:22:05
		 */
		public void decompress(InputStream is, OutputStream os)
				throws Exception {
			GZIPInputStream gis = new GZIPInputStream(is);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = gis.read(data, 0, BUFFER)) != -1) {
				os.write(data, 0, count);
			}
			gis.close();
		}

		/**
		 * �ļ���ѹ��
		 * 
		 * @param path
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:22:12
		 */
		public void decompress(String path) throws Exception {
			decompress(path, true);
		}

		/**
		 * �ļ���ѹ��
		 * 
		 * @param path
		 * @param delete
		 * @throws Exception
		 * @return void
		 * @author Zhouls
		 * @date 2015-3-9 ����5:22:20
		 */
		public void decompress(String path, boolean delete) throws Exception {
			File file = new File(path);
			decompress(file, delete);
		}
	}

	/**
	 * url������ֵ��
	 * 
	 * @author Zhouls
	 * @date 2015-1-27 ����2:28:05
	 */
	public static class URLAnalysis {
		private Map<String, String> paramMap = new HashMap<String, String>();

		public void analysis(String url) {
			paramMap.clear();
			if (!"".equals(url)) {// ���URL���ǿ��ַ���
				url = url.substring(url.indexOf('?') + 1);
				String paramaters[] = url.split("&");
				for (String param : paramaters) {
					String values[] = param.split("=");
					paramMap.put(values[0], values[1]);
				}
			}
		}

		public String getParam(String name) {
			if (!TextUtils.isEmpty(paramMap.get(name))) {
				return paramMap.get(name);
			} else {
				return "";
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param urlEncodeStr
	 *            the url encode str
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-26 ����9:26:35
	 */

	public static String getUrlDecodeString(String urlEncodeStr) {
		String decodeStr = "";
		try {
			if (!TextUtils.isEmpty(urlEncodeStr)) {
				decodeStr = URLDecoder.decode(urlEncodeStr, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodeStr;
	}

	/**
	 * ת��
	 * 
	 * @param urlDecodeStr
	 *            the url decode str
	 * @return String
	 * @author Zhouls
	 * @date 2014-8-28 ����10:26:54
	 */
	public static String getUrlEncodeString(String urlDecodeStr) {
		String encodeStr = "";
		try {
			if (!TextUtils.isEmpty(urlDecodeStr)) {
				encodeStr = URLEncoder.encode(urlDecodeStr, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}

	/**
	 * ת��utf-8
	 * 
	 * @param toUtfStr
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-10 ����5:02:53
	 */
	public static String getUtfStr(String toUtfStr) {
		byte[] a;
		try {
			a = toUtfStr.getBytes("unicode");
			return new String(a, "Utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * �����ʼ����ʼ�������ļ�
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-9-22 ����5:55:32
	 */
	public static void initMsgTestDomain() {
		m_msgTestDomain = null;
		m_AKeyCallTestDomain = null;
		m_OnTestDomain = null;
		m_PosTestDomain = null;
		m_BdTestDomain = null;
	}

	/** ��Ϣ����ʹ������. */
	private static String m_msgTestDomain = null;

	/**
	 * ��ȡ��Ϣ�Ŀ�������
	 * 
	 * @return String
	 * @author Wenap
	 * @date 2014-9-17 ����7:51:22
	 */
	protected static String getMsgTestDomain() {
		if (null != m_msgTestDomain) {
			return m_msgTestDomain;
		}
		if (CldBllUtil.getInstance().isTestVersion()) {
			try {
				String strPath = CldBllUtil.getInstance().getAppPath()
						+ "/msgtestaddr.cfg";
				File file = new File(strPath);
				if (file.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(
							file));
					m_msgTestDomain = reader.readLine();
					reader.close();
				}
			} catch (Exception e) {
			}
		}
		return m_msgTestDomain;
	}

	/** һ��ͨ����ʹ������ */
	private static String m_AKeyCallTestDomain = null;

	/**
	 * ��ȡһ��ͨ�Ŀ�������
	 * 
	 * @return String
	 * @author Wenap
	 * @date 2014-9-17 ����7:51:22
	 */
	protected static String getKAKeyCallTestDomain() {
		if (null != m_AKeyCallTestDomain) {
			return m_AKeyCallTestDomain;
		}
		if (CldBllUtil.getInstance().isTestVersion()) {
			try {
				File file = new File(CldBllUtil.getInstance().getAppPath()
						+ "/akeycalltestaddr.cfg");
				if (file.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(
							file));
					m_AKeyCallTestDomain = reader.readLine();
					reader.close();
				}
			} catch (Exception e) {
			}
		}
		return m_AKeyCallTestDomain;
	}

	/** pos����ʹ������. */
	private static String m_PosTestDomain = null;

	/**
	 * ��ȡpos�Ŀ�������
	 * 
	 * @return String
	 * @author Wenap
	 * @date 2014-9-17 ����7:51:22
	 */
	protected static String getPosTestDomain() {
		if (null != m_PosTestDomain) {
			return m_PosTestDomain;
		}
		if (CldBllUtil.getInstance().isTestVersion()) {
			try {
				File file = new File(CldBllUtil.getInstance().getAppPath()
						+ "/postestaddr.cfg");
				if (file.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(
							file));
					m_PosTestDomain = reader.readLine();
					reader.close();
				}
			} catch (Exception e) {
			}
		}
		return m_PosTestDomain;
	}

	/** ON����ʹ������ */
	private static String m_OnTestDomain = null;

	/**
	 * ��ȡ���ߵ����Ŀ�������
	 * 
	 * @return String
	 * @author Wenap
	 * @date 2014-9-17 ����7:51:22
	 */
	protected static String getOnTestDomain() {
		if (null != m_OnTestDomain) {
			return m_OnTestDomain;
		}
		if (CldBllUtil.getInstance().isTestVersion()) {
			try {
				File file = new File(CldBllUtil.getInstance().getAppPath()
						+ "/ontestaddr.cfg");
				if (file.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(
							file));
					m_OnTestDomain = reader.readLine();
					reader.close();
				}
			} catch (Exception e) {
			}
		}
		return m_OnTestDomain;
	}

	/** BD����ʹ������ */
	private static String m_BdTestDomain = null;

	/**
	 * ��ȡ���ߵ����Ŀ�������
	 * 
	 * @return String
	 * @author Wenap
	 * @date 2014-9-17 ����7:51:22
	 */
	protected static String getBdTestDomain() {
		if (null != m_BdTestDomain) {
			return m_BdTestDomain;
		}
		if (CldBllUtil.getInstance().isTestVersion()) {
			try {
				File file = new File(CldBllUtil.getInstance().getAppPath()
						+ "/bdtestaddr.cfg");
				if (file.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(
							file));
					m_BdTestDomain = reader.readLine();
					reader.close();
				}
			} catch (Exception e) {
			}
		}
		return m_BdTestDomain;
	}
}
