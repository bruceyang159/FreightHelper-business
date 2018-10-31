/*
 * @Title CldSapUtil.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:59
 * @version 1.0
 */
package com.mtq.ols.module.delivery.tool;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Base64;

import com.cld.log.CldLog;
import com.cld.utils.CldSerializer;

/**
 * Э��㹫��������
 * 
 * @author Zhouls
 * @date 2014-9-23 ����11:02:38
 */
public class CldPubFuction {

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
	 * ���ļ����ȡ����������
	 * 
	 * @param path
	 *            ·��
	 * @return
	 * @return byte[]
	 * @author Zhouls
	 * @date 2015-7-1 ����10:29:09
	 */
	public static byte[] fileToByte(String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				long fileSize = file.length();
				if (fileSize > Integer.MAX_VALUE) {
					return null;
				}
				FileInputStream fi = new FileInputStream(file);
				byte[] buffer = new byte[(int) fileSize];
				int offset = 0;
				int numRead = 0;
				while (offset < buffer.length
						&& (numRead = fi.read(buffer, offset, buffer.length
								- offset)) >= 0) {
					offset += numRead;
				}
				// ȷ���������ݾ�����ȡ
				if (offset != buffer.length) {
					fi.close();
					return null;
				}
				fi.close();
				return buffer;
			} else {
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ȡָ���ֽ���
	 * 
	 * @param path
	 *            �ļ�Ŀ¼
	 * @param byteCount
	 *            �ļ�ͷ���ݵĴ�С
	 * @return
	 * @return byte[]
	 * @author Zhouls
	 * @date 2015-7-1 ����7:51:53
	 */
	public static byte[] fileToByte(String path, int byteCount) {
		try {
			File file = new File(path);
			if (file.exists()) {
				long fileSize = file.length();
				if (fileSize > Integer.MAX_VALUE) {
					return null;
				}
				if (byteCount > fileSize) {
					return null;
				}
				FileInputStream fi = new FileInputStream(file);
				byte[] buffer = new byte[byteCount];
				int offset = 0;
				int numRead = 0;
				while (offset < buffer.length
						&& (numRead = fi.read(buffer, offset, buffer.length
								- offset)) >= 0) {
					offset += numRead;
				}
				// ȷ���������ݾ�����ȡ
				if (offset != buffer.length) {
					fi.close();
					return null;
				}
				fi.close();
				return buffer;
			} else {
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �ļ�ͨ�������ļ�
	 * 
	 * @param s
	 * @param t
	 * @return void
	 * @author Zhouls
	 * @date 2015-8-11 ����8:50:46
	 */
	public static boolean fileChannelCopy(String oldPath, String newPath) {
		File sourceFile = new File(oldPath);
		File targetFile = new File(newPath);
		if (sourceFile.exists()) {
			// Դ�ļ�����
			FileInputStream fi = null;
			FileOutputStream fo = null;
			FileChannel in = null;
			FileChannel out = null;
			try {
				long sUtc = System.currentTimeMillis();
				fi = new FileInputStream(sourceFile);
				fo = new FileOutputStream(targetFile);
				in = fi.getChannel();// �õ���Ӧ���ļ�ͨ��
				out = fo.getChannel();// �õ���Ӧ���ļ�ͨ��
				in.transferTo(0, in.size(), out);// ��������ͨ�������Ҵ�inͨ����ȡ��Ȼ��д��outͨ��
				long eUtc = System.currentTimeMillis();
				long disUtc = eUtc - sUtc;
				CldLog.d("file", disUtc + "ms");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					fi.close();
					in.close();
					fo.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			CldLog.e("file", "copy file:" + oldPath + " not exist!");
			return false;
		}
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
					CldLog.d("ols", "sendOK_sms");
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
	 * ���ļ�ת��base64 ���뵽�ļ�
	 * 
	 * @param path
	 * @param toPath
	 * @throws Exception
	 * @return void
	 * @author Zhouls
	 * @date 2015-9-1 ����3:20:11
	 */
	public static void encodeBase64File(String path, String toPath) {
		try {
			File file = new File(path);
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			String result = Base64.encodeToString(buffer, Base64.DEFAULT);
			CldLog.d("ols", "base:" + result);
			FileWriter fw = new FileWriter(new File(toPath));
			fw.write(result);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			if (TextUtils.isEmpty(keyCode)) {
				CldLog.e("ols", "decodekey Failed!");
			}
			return keyCode;
		}
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
	 * 
	 * Aes���ܽ����㷨
	 * 
	 * @author Zhouls
	 * @date 2015-11-6 ����4:54:33
	 */
	public static class CldAescrpy {
		private static final String AESTYPE = "AES/ECB/PKCS7Padding";

		public static String encrypt(String keycode, String plainText) {
			byte[] encrypt = null;
			if (TextUtils.isEmpty(plainText) || TextUtils.isEmpty(keycode)) {
				return null;
			}
			try {
				Key key = generateKey(keycode);
				Cipher cipher = Cipher.getInstance(AESTYPE);
				cipher.init(Cipher.ENCRYPT_MODE, key);
				encrypt = cipher.doFinal(plainText.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
			String res = new String(Base64.encode(encrypt, Base64.DEFAULT));
			return res.replaceAll("\n", "");
		}

		public static String decrypt(String keycode, String encryptData) {
			byte[] decrypt = null;
			if (TextUtils.isEmpty(keycode) || TextUtils.isEmpty(encryptData)) {
				return null;
			}
			try {
				Key key = generateKey(keycode);
				Cipher cipher = Cipher.getInstance(AESTYPE);
				cipher.init(Cipher.DECRYPT_MODE, key);
				decrypt = cipher.doFinal(Base64.decode(encryptData,
						Base64.DEFAULT));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new String(decrypt).trim();
		}

		private static Key generateKey(String keycode) throws Exception {
			try {
				SecretKeySpec keySpec = new SecretKeySpec(keycode.getBytes(),
						"AES");
				return keySpec;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		public static void test(String key, String str, String toDecode) {
			String keyStr = key;
			String plainText = str;
			String encText = encrypt(keyStr, plainText);
			String decString = decrypt(keyStr, encText);
			CldLog.d("ols_aes", "encode src:" + encText);
			CldLog.d("ols_aes", "encode res:" + decString);
			String res = decrypt(keyStr, toDecode);
			CldLog.d("ols_aes", "decode src:" + toDecode);
			CldLog.d("ols_aes", "decode res:" + res);
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

		public URLAnalysis() {

		}

		public URLAnalysis(String url) {
			analysis(url);
		}

		public void analysis(String url) {
			paramMap.clear();
			if (!TextUtils.isEmpty(url)) {// ���URL���ǿ��ַ���
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
	 * 
	 * �̳߳ع���
	 * 
	 * @author Zhouls
	 * @date 2015-12-22 ����5:26:00
	 */
	public static class CldOlsThreadPool {
		private static ExecutorService threadPool = Executors
				.newCachedThreadPool();;

		/**
		 * �ύ�������̳߳�
		 * 
		 * @param command
		 * @return void
		 * @author Zhouls
		 * @date 2015-5-13 ����2:16:19
		 */
		public static void submit(Runnable command) {
			if (null != threadPool) {
				threadPool.execute(command);
			}
		}
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
				if (a.length < b.length) {
					return true;
				} else {
					return false;
				}

			} else {
				/**
				 * 2���ַ�����ȫ���
				 */
				return true;
			}
		}
	}

	/**
	 * 
	 * �����ƽӿ�����ת�������޴���ӿ�
	 * 
	 * @author Zhouls
	 * @date 2015-5-26 ����3:15:31
	 */
	public static class CldDataUtils {

		/**
		 * ��������תlong
		 * 
		 * @param array
		 * @return
		 * @return long
		 * @author Zhouls
		 * @date 2015-5-26 ����3:15:01
		 */
		public static long bytes2Long(byte[] array) {
			return CldSerializer.bytesToUint(array);
		}

		/**
		 * ��������תint
		 * 
		 * @param array
		 * @return
		 * @return long
		 * @author Zhouls
		 * @date 2015-5-26 ����3:15:16
		 */
		public static int bytes2Int(byte[] array) {
			return CldSerializer.bytesToUshort(array);
		}

		/**
		 * �ֽ�ǿתint
		 * 
		 * @param mByte
		 * @return
		 * @return int
		 * @author Zhouls
		 * @date 2015-5-26 ����3:25:40
		 */
		public static int byte2Int(byte mByte) {
			return mByte;
		}

		/**
		 * ����˶��������� תunicode�ַ��� Ҫ��BOMͷ
		 * 
		 * @param array
		 *            ����˶��������� ����BOM(����֪�������ֶ�)
		 * @return
		 * @return String
		 * @author Zhouls
		 * @date 2015-6-2 ����12:03:24
		 */
		public static String bytes2UnicodeString(byte[] array) {
			if (null == array || array.length == 0) {
				return null;
			}
			byte[] res;
			int sizeOfRes;
			if (array[array.length - 2] == 0 && array[array.length - 1] == 0) {
				sizeOfRes = array.length;
			} else {
				sizeOfRes = array.length + 2;
			}
			res = new byte[sizeOfRes];
			res[0] = -1;
			res[1] = -2;
			for (int i = 2; i < sizeOfRes; i++) {
				res[i] = array[i - 2];
			}
			try {
				String strRes = new String(res, "unicode");
				return strRes;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * ת��unicode
		 * 
		 * @param array
		 * @return
		 * @return String
		 * @author Zhouls
		 * @date 2015-6-29 ����5:17:04
		 */
		public static String bytes2GBK(byte[] array, int key) {
			if (null == array) {
				return "";
			}
			decodeSwData(array, key);
			try {
				byte[] dest = new byte[array.length];
				int j = 0;
				for (int i = 0; i < dest.length; i++) {
					if (array[i] != 0) {
						dest[j++] = array[i];
					}
				}
				dest[j] = 0;
				String strRes = new String(dest, "GBK");
				return strRes.trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}

		/**
		 * �����Ƽ������ݽ���
		 * 
		 * @param arr
		 * @param key
		 * @return
		 * @return int
		 * @author Zhouls
		 * @date 2015-7-1 ����12:20:43
		 */
		public static int bytes2Int(byte[] array, int key) {
			if (null == array) {
				return 0;
			}
			decodeSwData(array, key);
			return (int) bytes2Long(array);
		}

		/**
		 * ������ȫ����
		 * 
		 * @param array
		 *            ����
		 * @param key
		 *            ����Key
		 * @return void
		 * @author Zhouls
		 * @date 2015-7-1 ����12:44:57
		 */
		private static void decodeSwData(byte[] array, int key) {
			if (null == array || 0 == key) {
				return;
			}
			for (int i = 0; i < array.length; i++) {
				array[i] = (byte) (array[i] ^ key);
			}
		}
	}

	/**
	 * 
	 * ZLibѹ������
	 * 
	 * @author Zhouls
	 * @date 2015-5-25 ����10:52:53
	 */
	public static class ZLibUtils {

		/**
		 * ѹ��
		 * 
		 * @param data
		 *            ��ѹ������
		 * @return
		 * @return byte[] ѹ���������
		 * @author Zhouls
		 * @date 2015-5-25 ����10:53:44
		 */
		public static byte[] compress(byte[] data) {
			byte[] output = new byte[0];
			Deflater compresser = new Deflater();
			compresser.reset();
			compresser.setInput(data);
			compresser.finish();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
			try {
				byte[] buf = new byte[1024];
				while (!compresser.finished()) {
					int i = compresser.deflate(buf);
					bos.write(buf, 0, i);
				}
				output = bos.toByteArray();
			} catch (Exception e) {
				output = data;
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			compresser.end();
			return output;
		}

		/**
		 * ѹ��
		 * 
		 * @param data
		 *            ��ѹ������
		 * @param os
		 *            �����
		 * @return void
		 * @author Zhouls
		 * @date 2015-5-25 ����10:54:02
		 */
		public static void compress(byte[] data, OutputStream os) {
			DeflaterOutputStream dos = new DeflaterOutputStream(os);
			try {
				dos.write(data, 0, data.length);
				dos.finish();
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * ��ѹ��
		 * 
		 * @param data
		 *            ��ѹ��������
		 * @return
		 * @return byte[] ��ѹ���������
		 * @author Zhouls
		 * @date 2015-5-25 ����10:54:18
		 */
		public static byte[] decompress(byte[] data) {
			byte[] output = new byte[0];
			Inflater decompresser = new Inflater();
			decompresser.reset();
			decompresser.setInput(data);
			ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
			try {
				byte[] buf = new byte[1024];
				while (!decompresser.finished()) {
					int i = decompresser.inflate(buf);
					o.write(buf, 0, i);
				}
				output = o.toByteArray();
			} catch (Exception e) {
				output = data;
				e.printStackTrace();
			} finally {
				try {
					o.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			decompresser.end();
			return output;
		}

		/**
		 * ��ѹ��
		 * 
		 * @param is
		 *            ������
		 * @return
		 * @return byte[] ��ѹ���������
		 * @author Zhouls
		 * @date 2015-5-25 ����10:54:34
		 */
		public static byte[] decompress(InputStream is) {
			InflaterInputStream iis = new InflaterInputStream(is);
			ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
			try {
				int i = 1024;
				byte[] buf = new byte[i];
				while ((i = iis.read(buf, 0, i)) > 0) {
					o.write(buf, 0, i);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return o.toByteArray();
		}
	}

	/**
	 * FileWrite
	 * 
	 * @author Zhouls
	 * @date 2015-10-14 ����10:00:26
	 */
	public static class CldFileWrite {
		private String path = "";

		public CldFileWrite(String path) {
			this.path = path;
		}

		/**
		 * �Ƿ���Ҫ����
		 * 
		 * @param str
		 * @param isChangeLine
		 * @throws IOException
		 * @return void
		 * @author Zhouls
		 * @date 2015-10-14 ����10:03:01
		 */
		public void write(String str) throws IOException {
			// TODO Auto-generated method stub
			if (TextUtils.isEmpty(path)) {
				CldLog.e("ols", "CldFileWrite is null!");
				return;
			}
//			CldLog.logToFile(path, str, false);
		}
	}

	/**
	 * 
	 * �ļ�������
	 * 
	 * @author Zhouls
	 * @date 2015-8-19 ����5:08:50
	 */
	public static class FileManger {
		private static String TAG = "OlsFile";

		/**
		 * ɾ��Ŀ¼�����ļ�
		 * 
		 * @param file
		 *            Ŀ¼
		 * @return void
		 * @author Zhouls
		 * @date 2015-8-19 ����5:08:32
		 */
		public static void deleteSubFile(File file) {
			if (file.isFile()) {
				file.delete();
				CldLog.d(TAG, "del:" + file.getPath());
				return;
			}
			if (file.isDirectory()) {
				File[] childFiles = file.listFiles();
				if (childFiles == null || childFiles.length == 0) {
					// ����ǿ�Ŀ¼ֱ�ӷ��أ�����
					return;
				}
				for (int i = 0; i < childFiles.length; i++) {
					deleteSubFile(childFiles[i]);
				}
				CldLog.d(TAG, "del subFile:" + file.getPath());
			}
		}

		/**
		 * ɾ��ĳ���ļ� ��Ŀ¼
		 * 
		 * @param path
		 * @return void
		 * @author Zhouls
		 * @date 2016-2-1 ����5:40:56
		 */
		public static void delete(String path) {
			File file = new File(path);
			if (file.exists()) {
				delete(file);
			}
		}

		/**
		 * ɾ���ļ�����Ŀ¼��
		 * 
		 * @param file
		 * @return void
		 * @author Zhouls
		 * @date 2015-8-19 ����5:08:32
		 */
		public static void delete(File file) {
			if (file.isFile()) {
				file.delete();
				CldLog.d(TAG, "del:" + file.getPath());
				return;
			}
			if (file.isDirectory()) {
				File[] childFiles = file.listFiles();
				if (childFiles == null || childFiles.length == 0) {
					file.delete();
					return;
				}
				for (int i = 0; i < childFiles.length; i++) {
					delete(childFiles[i]);
				}
				CldLog.d(TAG, "del:" + file.getPath());
				file.delete();
			}
		}

		/**
		 * ɾ���ļ�����Ŀ¼��
		 * 
		 * @param file
		 *            �ļ�Ŀ¼
		 * @param filePath
		 *            �������ļ���
		 * @return void
		 * @author Zhouls
		 * @date 2015-8-25 ����7:21:21
		 */
		public static void delete(File file, String filePath) {
			if (file.isFile()) {
				if (file.getPath().equals(filePath)) {
					CldLog.d(TAG, "del turn:" + filePath);
					return;
				}
				file.delete();
				CldLog.d(TAG, "del:" + file.getPath());
				return;
			}
			if (file.isDirectory()) {
				File[] childFiles = file.listFiles();
				if (childFiles == null || childFiles.length == 0) {
					file.delete();
					return;
				}
				for (int i = 0; i < childFiles.length; i++) {
					delete(childFiles[i], filePath);
				}
				childFiles = file.listFiles();
				if (null == childFiles || childFiles.length == 0) {
					file.delete();
					CldLog.d(TAG, "del:" + file.getPath());
				} else {
					CldLog.d(TAG, "del:still file size" + childFiles.length);
					for (int j = 0; j < childFiles.length; j++) {
						CldLog.d(TAG,
								"del:still file" + childFiles[j].getPath());
					}
				}
			}
		}

		/**
		 * ���Ƶ����ļ�
		 * 
		 * @param oldPath
		 *            String ԭ�ļ�·�� �磺c:/fqf.txt
		 * @param newPath
		 *            String ���ƺ�·�� �磺f:/fqf.txt
		 * @return void
		 * @author Zhouls
		 * @date 2015-8-25 ����7:38:26
		 */
		public static void copyFile(String oldPath, String newPath) {
			try {
				int bytesum = 0;
				int byteread = 0;
				File oldfile = new File(oldPath);
				if (oldfile.exists()) { // �ļ�����ʱ
					InputStream inStream = new FileInputStream(oldPath); // ����ԭ�ļ�
					FileOutputStream fs = new FileOutputStream(newPath);
					byte[] buffer = new byte[1444];
					while ((byteread = inStream.read(buffer)) != -1) {
						bytesum += byteread; // �ֽ��� �ļ���С
						fs.write(buffer, 0, byteread);
					}
					inStream.close();
					fs.close();
					CldLog.d(TAG, "copy:" + oldPath);
					CldLog.d(TAG, "copy:size=" + bytesum);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		}

		/**
		 * ���������ļ�������
		 * 
		 * @param oldPath
		 *            String ԭ�ļ�·�� �磺c:/fqf
		 * @param newPath
		 *            String ���ƺ�·�� �磺f:/fqf/ff
		 * @return void
		 * @author Zhouls
		 * @date 2015-8-25 ����7:35:19
		 */
		public static void copyFolder(String oldPath, String newPath) {
			try {
				(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
				File a = new File(oldPath);
				String[] file = a.list();
				File temp = null;
				for (int i = 0; i < file.length; i++) {
					if (oldPath.endsWith(File.separator)) {
						temp = new File(oldPath + file[i]);
					} else {
						temp = new File(oldPath + File.separator + file[i]);
					}

					if (temp.isFile()) {
						FileInputStream input = new FileInputStream(temp);
						FileOutputStream output = new FileOutputStream(newPath
								+ "/" + (temp.getName()).toString());
						byte[] b = new byte[1024 * 5];
						int len;
						while ((len = input.read(b)) != -1) {
							output.write(b, 0, len);
						}
						output.flush();
						output.close();
						input.close();
						CldLog.d(TAG, "copy:" + temp.getName());
					}
					if (temp.isDirectory()) {// ��������ļ���
						copyFolder(oldPath + "/" + file[i], newPath + "/"
								+ file[i]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		/**
		 * ���������ļ�������(����ĳ���ļ�)
		 * 
		 * @param oldPath
		 *            String ԭ�ļ�·�� �磺c:/fqf
		 * @param newPath
		 *            String ���ƺ�·�� �磺f:/fqf/ff
		 * @param filePath
		 *            �������ļ�
		 * @return void
		 * @author Zhouls
		 * @date 2015-8-25 ����7:35:19
		 */
		public static void copyFolder(String oldPath, String newPath,
				String filePath) {
			try {
				(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
				File a = new File(oldPath);
				String[] file = a.list();
				File temp = null;
				for (int i = 0; i < file.length; i++) {
					if (oldPath.endsWith(File.separator)) {
						temp = new File(oldPath + file[i]);
					} else {
						temp = new File(oldPath + File.separator + file[i]);
					}
					if (temp.isFile()) {
						if (temp.getPath().equals(filePath)) {
							CldLog.d(TAG, "copy turn:" + filePath);
							continue;
						}
						FileInputStream input = new FileInputStream(temp);
						FileOutputStream output = new FileOutputStream(newPath
								+ "/" + (temp.getName()).toString());
						byte[] b = new byte[1024 * 5];
						int len;
						while ((len = input.read(b)) != -1) {
							output.write(b, 0, len);
						}
						output.flush();
						output.close();
						input.close();
						CldLog.d(TAG, "copy:" + temp.getName());
					}
					if (temp.isDirectory()) {// ��������ļ���
						copyFolder(oldPath + "/" + file[i], newPath + "/"
								+ file[i], filePath);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		/**
		 * ��ȡ�ļ�������
		 * 
		 * @param file
		 *            Ŀ���ļ�
		 * @return
		 * @return int
		 * @author Zhouls
		 * @date 2015-12-15 ����9:03:56
		 */
		public static int getTotalLines(File file) {
			int totalLines = 0;
			try {
				FileReader in = new FileReader(file);
				LineNumberReader reader = new LineNumberReader(in);
				String strLine = reader.readLine();
				while (strLine != null) {
					totalLines++;
					strLine = reader.readLine();
				}
				reader.close();
				in.close();
			} catch (Exception e) {

			}
			return totalLines;
		}
	}

	/**
	 * 
	 * MD5У�鹤��
	 * 
	 * @author Zhouls
	 * @date 2015-8-11 ����9:13:40
	 */
	public static class MD5Util {
		/**
		 * Ĭ�ϵ������ַ�����ϣ��������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�,apacheУ�����ص��ļ�����ȷ���õľ���Ĭ�ϵ�������
		 */
		protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		protected static MessageDigest messagedigest = null;
		static {
			try {
				messagedigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		/**
		 * ��ȡ�ļ���MD5У����
		 * 
		 * @param file
		 *            У���ļ�
		 * @return
		 * @return String
		 * @author Zhouls
		 * @date 2015-8-11 ����9:20:02
		 */
		public static String getFileMD5String(File file) {
			try {
				InputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int numRead = 0;
				while ((numRead = fis.read(buffer)) > 0) {
					messagedigest.update(buffer, 0, numRead);
				}
				fis.close();
				return bufferToHex(messagedigest.digest());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		private static String bufferToHex(byte bytes[]) {
			return bufferToHex(bytes, 0, bytes.length);
		}

		private static String bufferToHex(byte bytes[], int m, int n) {
			StringBuffer stringbuffer = new StringBuffer(2 * n);
			int k = m + n;
			for (int l = m; l < k; l++) {
				appendHexPair(bytes[l], stringbuffer);
			}
			return stringbuffer.toString();
		}

		private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
			char c0 = hexDigits[(bt & 0xf0) >> 4];// ȡ�ֽ��и� 4 λ������ת��
			// Ϊ�߼����ƣ�������λһ������,�˴�δ�������ַ����кβ�ͬ
			char c1 = hexDigits[bt & 0xf];// ȡ�ֽ��е� 4 λ������ת��
			stringbuffer.append(c0);
			stringbuffer.append(c1);
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
				e.printStackTrace();
			}
			return result;
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

	public static boolean crcCheck(File file, long checkCode) {
		if (null == file || !file.exists()) {
			return false;
		}
		return true;
		// CldAlg.getCrcValue(File);
	}
}
