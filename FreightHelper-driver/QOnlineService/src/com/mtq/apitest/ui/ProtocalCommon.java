/*
 * @Title ProtocalCommon.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-3-4 ����8:46:17
 * @version 1.0
 */
package com.mtq.apitest.ui;

/**
 * Э��ͨ�����Դ�ļ�
 */
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import com.cld.utils.CldSerializer;

public class ProtocalCommon {

	private static final int PACK_BUF_SIZE = 100 * 1024; // ��������С

	private static final int ProtDataHeadSize = 8; // ����ͷ��С

	/** ҵ�����Ͷ��� */
	public static final int EProtReportEvt = 103; // �¼��ϱ�(����)
	public static final int EProtDataVer = 500; // ҵ��汾

	// Э������ͷ
	static public class ProtDataHead {
		public long ulDataLen; // ���������ֽ���
		public int unType; // ҵ������[Э��ҵ������]
		public int unCount; // ��¼����
	}

	// ҵ��汾
	static public class ProtDataVer {
		public int unCode; // ҵ�����
		public byte ucVer; // �汾��
		public byte ucReserve; // ����

		public ProtDataVer(int in_nCode, int in_nVer) {
			unCode = in_nCode;
			ucVer = (byte) (in_nVer & 0xff);
		}

		public byte[] getByteData() {
			byte[] ret = null;
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

			try {
				byteStream.write(CldSerializer.ushortToBytes(unCode));
				byteStream.write(ucVer);
				byteStream.write(ucReserve);
				ret = byteStream.toByteArray();
				byteStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ret;
		}
	}

	// λ����Ϣ
	static public class ProtPosition {
		public long lRegionCode; // �������
		public long lX; // ����������X(ǧ��֮һ��)
		public long lY; // ����������Y(ǧ��֮һ��)
		public long lZ; // �߳�(��)

		public int unGpsSpeed; // ��ʱ�ٶ�(km/h)
		public int unAvgSpeed; // ƽ���ٶ�(km/h)
		public int unDirection; // ��ʻ����(GPS�Ƕ�, 0~360��)
		public byte ucErrCode; // ������(ע�����޷�������0~255)
		public byte ucSataliteNum; // ���ǿ���

		public long ulUTCTime; // ��������ķ�����ʱ��
		public long ulCellID; // ��·ͼ��ID,����ʱ��Ϊ0
		public long ulUid; // ��·UID,����ʱ��Ϊ0

		public byte[] getByteData() {
			byte[] ret = null;
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

			try {
				byteStream.write(CldSerializer.uintToBytes(lRegionCode));
				byteStream.write(CldSerializer.uintToBytes(lX));
				byteStream.write(CldSerializer.uintToBytes(lY));
				byteStream.write(CldSerializer.uintToBytes(lZ));
				byteStream.write(CldSerializer.ushortToBytes(unGpsSpeed));
				byteStream.write(CldSerializer.ushortToBytes(unAvgSpeed));
				byteStream.write(CldSerializer.ushortToBytes(unDirection));
				byteStream.write(ucErrCode);
				byteStream.write(ucSataliteNum);
				byteStream.write(CldSerializer.uintToBytes(ulUTCTime));
				byteStream.write(CldSerializer.uintToBytes(ulCellID));
				byteStream.write(CldSerializer.uintToBytes(ulUid));
				ret = byteStream.toByteArray();
				byteStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ret;
		}
	}

	// λ������״̬
	static public class ProtLocStatus {
		public int unCellId; // �ֻ����ڵķ�������ID(0~0xFFFF,0xFFFFΪδ֪)
		public int unLocationAreaCode; // �ֻ����ڵ�λ���������(LAC)(0~0xFFFF,0xFFFFΪδ֪)

		public byte ucGsmSigStrength; // �ֻ��ź�ǿ��(ֵ0~31)
		public byte ucNetSigStrength; // �����ź�ǿ��(ֵ0~100)
		public byte ucNetType; // ��������(�ο�CAL_NetworkType)
		public byte ucReserve; // ����

		public byte[] getByteData() {
			byte[] ret = null;
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

			try {
				byteStream.write(CldSerializer.ushortToBytes(unCellId));
				byteStream.write(CldSerializer
						.ushortToBytes(unLocationAreaCode));
				byteStream.write(ucGsmSigStrength);
				byteStream.write(ucNetSigStrength);
				byteStream.write(ucNetType);
				byteStream.write(ucReserve);
				ret = byteStream.toByteArray();
				byteStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ret;
		}
	}

	// �ϱ��¼���Ϣ
	static public class ProtEventInfo {
		public int unEvtDirection; // �¼�����(1: ����, 2: ����)
		public int unEvtType; // �¼�����
		public int unEvtSource; // �¼���Դ(1: �ٷ�, 2: K��)
		public int unEvtRewardNum; // ���ͽ��

		public byte[] szEvtDes = null; // �¼�����
		public String strPhotoFile = null; // ��Ƭ�ļ���(ȫ·��)
		public String strVoiceFile = null; // ��Ƭ�ļ���(ȫ·��)

		private long ulSizeContent = 0; // �¼���������
		private long ulSizePhoto = 0; // ͼƬ���ݳ���
		private long ulSizeVoice = 0; // �������ݳ���

		public byte[] getByteData() {
			byte[] ret = null;
			int readLen = 0;
			byte[] readBuf = new byte[1024];
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

			try {
				byteStream.write(CldSerializer.ushortToBytes(unEvtDirection));
				byteStream.write(CldSerializer.ushortToBytes(unEvtType));
				byteStream.write(CldSerializer.ushortToBytes(unEvtSource));
				byteStream.write(CldSerializer.ushortToBytes(unEvtRewardNum));

				if (null != szEvtDes)
					ulSizeContent = szEvtDes.length;
				if (null != strPhotoFile)
					ulSizePhoto = (new FileInputStream(strPhotoFile))
							.available();
				if (null != strVoiceFile)
					ulSizeVoice = (new FileInputStream(strVoiceFile))
							.available();
				byteStream.write(CldSerializer.uintToBytes(ulSizeContent));
				byteStream.write(CldSerializer.uintToBytes(ulSizePhoto));
				byteStream.write(CldSerializer.uintToBytes(ulSizeVoice));

				// �¼�����
				if (null != szEvtDes && ulSizeContent > 0)
					byteStream.write(szEvtDes);

				// ��Ƭ
				if (null != strPhotoFile) {
					FileInputStream fin = new FileInputStream(strPhotoFile);
					while (-1 != (readLen = fin.read(readBuf))) {
						byteStream.write(readBuf, 0, readLen);
					}
					fin.close();
				}

				// ����
				if (null != strVoiceFile) {
					FileInputStream fin = new FileInputStream(strVoiceFile);
					while (-1 != (readLen = fin.read(readBuf))) {
						byteStream.write(readBuf, 0, readLen);
					}
					fin.close();
				}

				ret = byteStream.toByteArray();
				byteStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ret;
		}
	}

	/** ��Ա */
	private ByteArrayOutputStream byteData = null;
	private byte[] packBuffer = null;

	/**
	 * ����
	 */
	public ProtocalCommon() {
		packBuffer = new byte[PACK_BUF_SIZE];
		byteData = new ByteArrayOutputStream();
	}

	/** JNI��������. */
	/*
	 * ======================================================== ����: ������� ����:
	 * in_data [����] => ��Ҫ��������� in_dataLen[����] => ��Ҫ��������ݳ���(�ֽ���) out_data [���
	 * 
	 * ] => ���������� in_bufLen [����] => ������峤��(�ֽ���) in_isZip [����] => �Ƿ���Ҫѹ�� ����:
	 * 
	 * (>0) => ���������ݳ��� other => ʧ��(0: ��������; -1: ������岻��; -2: ѹ��ʧ��; -3: ���ɼ�����ʧ��
	 * 
	 * ) ========================================================
	 */
	private native int packPostData(byte[] in_data, int in_dataLen,
			byte[] out_data, int in_bufLen, int in_isZip);

	/*
	 * ======================================================== ����: ��������ͷ ����:
	 * in_nType [����] => ����ҵ������ in_nCount [����] => ���� in_lDataLen[����] => ���ݳ���
	 * ========================================================
	 */
	public void appendDataHeader(int in_nType, int in_nCount, long in_lDataLen) {
		try {
			byteData.write(CldSerializer.uintToBytes(in_lDataLen
					+ ProtDataHeadSize));
			byteData.write(CldSerializer.ushortToBytes(in_nType));
			byteData.write(CldSerializer.ushortToBytes(in_nCount));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ======================================================== ����: �������� ����:
	 * in_data[����] => ����========================================================
	 */
	public void appendData(byte[] in_data) {
		try {
			byteData.write(in_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ======================================================== ����: ��ȡPost�������
	 * ����: in_isZip[����] => �Ƿ�ѹ��(0: ��ѹ��; 1: ѹ��) ����: Post�������
	 * ========================================================
	 */
	public byte[] getPostPackData(int in_isZip) {
		int ret = 0;
		byte[] srcData = byteData.toByteArray();

		ret = packPostData(srcData, srcData.length, packBuffer, PACK_BUF_SIZE,
				in_isZip);
		if (ret > 0) {
			byteData.reset();
			byteData.write(packBuffer, 0, ret);
		}

		return byteData.toByteArray();
	}
}
