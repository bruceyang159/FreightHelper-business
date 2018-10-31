package com.mtq.ols.module.deliverybus.tool;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

import com.cld.log.CldLog;

public class MtqSapNetUtil {

	/**
	 * Http Post ����
	 * 
	 * @param strUrl
	 *            the str url
	 * @param strPost
	 *            Post��json������ͳһת�봦�������ֶβ�������ת�룩
	 * @return String
	 * @author Zhouls
	 * @date 2014-9-2 ����11:06:45
	 */
	public static String sapPostMethod(String strUrl, String strPost) {
		try {
			if (!TextUtils.isEmpty(strUrl) && !TextUtils.isEmpty(strPost)) {
				HttpPost request = new HttpPost(strUrl);
				request.addHeader("Accept-Encoding", "gzip");
				StringEntity strEntity = new StringEntity(strPost, HTTP.UTF_8);
				request.setEntity(strEntity);
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(request);
				InputStream inputStream = httpResponse.getEntity().getContent();
				return getJsonFromGZIP(inputStream);
			} else {
				CldLog.e("[ols]", "url is empty!");
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��GZIP�л�ȡJson����
	 * 
	 * @param is
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-4-8 ����2:43:13
	 */
	private static String getJsonFromGZIP(InputStream is) {
		String jsonString = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(is);
			bis.mark(2);
			// ȡǰ�����ֽ�
			byte[] header = new byte[2];
			int result = bis.read(header);
			// reset����������ʼλ��
			bis.reset();
			// �ж��Ƿ���GZIP��ʽ
			int headerData = getShort(header);
			// Gzip �� ��ǰ�����ֽ��� 0x1f8b
			if (result != -1 && headerData == 0x1f8b) {
				is = new GZIPInputStream(bis);
			} else {
				is = bis;
			}
			InputStreamReader reader = new InputStreamReader(is, "utf-8");
			char[] data = new char[100];
			int readSize;
			StringBuffer sb = new StringBuffer();
			while ((readSize = reader.read(data)) > 0) {
				sb.append(data, 0, readSize);
			}
			jsonString = sb.toString();
			bis.close();
			reader.close();
			CldLog.i("ols", jsonString);
			return jsonString;
		} catch (Exception e) {
			/**
			 * �����쳣������jsonSting ΪNull���
			 */
			CldLog.e("[ols]", "net_null");
			return null;
		}
	}

	/**
	 * Gets the short.
	 * 
	 * @param data
	 *            the data
	 * @return the short
	 */
	private static int getShort(byte[] data) {
		return (data[0] << 8) | data[1] & 0xFF;
	}

}
