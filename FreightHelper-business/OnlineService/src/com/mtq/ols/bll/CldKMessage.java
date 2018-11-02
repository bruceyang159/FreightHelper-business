/*
 * @Title CldKMessage.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.bll;

import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;
import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.cld.setting.CldSetting;
import com.mtq.ols.api.CldKAccountAPI;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.dal.CldDalKCallNavi;
import com.mtq.ols.dal.CldDalKMessage;
import com.mtq.ols.sap.CldSapKMessage;
import com.mtq.ols.sap.bean.CldSapKMParm;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareAKeyCallParm;
import com.mtq.ols.sap.bean.CldSapKMParm.SharePoiParm;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareRouteParm;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareAKeyCallParm.CldAreaEgg;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtKeyCode;
import com.mtq.ols.sap.parse.CldKMessageParse.ProtCreMsg;
import com.mtq.ols.sap.parse.CldKMessageParse.ProtEggs;
import com.mtq.ols.sap.parse.CldKMessageParse.ProtMaxLength;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;



/**
 * 
 * ��Ϣϵͳ
 * 
 * @author Zhouls
 * @date 2015-4-8 ����4:10:14
 */
public class CldKMessage {

	/** �����ʵ��б���2*60*60s(ʱ���) */
	private final static long DROP_EGGS_UTC = 2 * 60 * 60;

	private static CldKMessage cldKMessage;

	/**
	 * Instantiates a new cld k message.
	 */
	private CldKMessage() {
	}

	/**
	 * Gets the single instance of CldKMessage.
	 * 
	 * @return single instance of CldKMessage
	 */
	public static CldKMessage getInstance() {
		if (cldKMessage == null)
			cldKMessage = new CldKMessage();
		return cldKMessage;
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:10:51
	 */
	public void initKey() {
		String cldKMKey = CldDalKMessage.getInstance().getCldKMKey();
		// ֻҪû���жϵ�¼
		int slpTimer = 5;// ��������
		while (TextUtils.isEmpty(cldKMKey)) {
			int sleepSc = 0;// ��������
			if (!CldPhoneNet.isNetConnected()) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				continue;
			}
			CldSapReturn errRes = CldSapKMessage.initKeyCode();
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtKeyCode protKeyCode = CldSapParser.parseJson(strRtn,
					ProtKeyCode.class, errRes);
			CldLog.d("ols", errRes.errCode + "_initKMKey");
			CldLog.d("ols", errRes.errMsg + "_initKMKey");
			CldErrUtil.handleErr(errRes);
			if (null != protKeyCode && errRes.errCode == 0
					&& !TextUtils.isEmpty(protKeyCode.getCode())) {
				cldKMKey = protKeyCode.getCode();
				CldDalKMessage.getInstance().setCldKMKey(cldKMKey);
			} else {
				try {
					while (sleepSc < slpTimer) {
						Thread.sleep(1000);
						sleepSc++;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (slpTimer >= 30) {
				/**
				 * ����30S�Ժ�̶�30S����
				 */
				slpTimer = 30;
			} else {
				/**
				 * ����30Sÿ�μ�5S
				 */
				slpTimer += 5;
			}
		}
		CldSapKMessage.keyCode = cldKMKey;
	}

	/**
	 * ������Ϣ
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
	 *            �ش���������
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
	 * @param uniqueids
	 *            "kuid,0"
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:11:20
	 */
	private CldSapReturn sendShareMsg(long kuid, long duid, int messagetype,
			int module, CldSapKMParm parm, String title, String endtime,
			int bussinessid, String session, int appid, int apptype,
			String uniqueids) {
		CldSapReturn errRes = CldSapKMessage.createCldMsg(kuid, duid,
				messagetype, module, parm, title, endtime, bussinessid,
				session, appid, apptype);
		String strRtn = CldSapNetUtil
				.sapPostMethod(errRes.url, errRes.jsonPost);
		ProtCreMsg protCreMsg = CldSapParser.parseJson(strRtn,
				ProtCreMsg.class, errRes);
		CldLog.d("ols", errRes.errCode + "_cremsg");
		CldLog.d("ols", errRes.errMsg + "_cremsg");
		CldErrUtil.handleErr(errRes);
		errCodeFix(errRes);
		if (null != protCreMsg) {
			if (errRes.errCode == 0) {
				parm.setMessageId(protCreMsg.getMessageid());
				errRes = CldSapKMessage.sendCldMsg(parm.getMessageId(), duid,
						uniqueids, messagetype, kuid, bussinessid, session,
						appid);
				strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldLog.d("ols", errRes.errCode + "_sendmsg");
				CldLog.d("ols", errRes.errMsg + "_sendmsg");
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
			}
		}
		return errRes;
	}

	/**
	 * ����һ��POI��Ϣ
	 * 
	 * @param poi
	 *            ����ĵ���Ϣ
	 * @param targetType
	 *            �����������Ǳ��ˣ�0���Լ� ��1�����ˣ�
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:11:38
	 */
	public CldSapReturn sendSharePoiMsg(SharePoiParm poi, int targetType) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			// ��������
			if (targetType == 0) {
				// ���͸�����
				CldSapKMParm parm = new CldSapKMParm();
				poi.setPoi(poi.getPoi() + "," + poi.getName());
				parm.setPoiMsg(poi);
				String uniqueids = CldDalKAccount.getInstance().getKuid() + ","
						+ "0";
				errRes = sendShareMsg(CldDalKAccount.getInstance().getKuid(),
						CldDalKAccount.getInstance().getDuid(), 11, CldBllUtil
								.getInstance().getModule(), parm, null, null,
						CldBllUtil.getInstance().getBussinessid(),
						CldDalKAccount.getInstance().getSession(), CldBllUtil
								.getInstance().getAppid(), CldBllUtil
								.getInstance().getApptype(), uniqueids);
			}
			if (targetType == 1) {
				// ���͸�����
				CldSapKMParm parm = new CldSapKMParm();
				poi.setPoi(poi.getPoi() + "," + poi.getName());
				parm.setPoiMsg(poi);
				// �����û�������kuidƴ�ɷ���Ŀ���ֶ�
				String uniqueids = poi.getTarget() + "," + "0";
				errRes = sendShareMsg(CldDalKAccount.getInstance().getKuid(),
						CldDalKAccount.getInstance().getDuid(), 11, CldBllUtil
								.getInstance().getModule(), parm, null, null,
						CldBllUtil.getInstance().getBussinessid(),
						CldDalKAccount.getInstance().getSession(), CldBllUtil
								.getInstance().getAppid(), CldBllUtil
								.getInstance().getApptype(), uniqueids);
			}
		}
		return errRes;
	}

	/**
	 * ����һ����·��Ϣ
	 * 
	 * @param route
	 *            �����·����Ϣ
	 * @param targetType
	 *            �����������Ǳ��ˣ�0���Լ� ��1�����ˣ�
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:12:01
	 */
	public CldSapReturn sendShareRouteMsg(ShareRouteParm route, int targetType) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			// ��������
			if (targetType == 0) {
				// ���͸�����
				CldSapKMParm parm = new CldSapKMParm();
				parm.setRouteMsg(route);
				String uniqueids = CldDalKAccount.getInstance().getKuid() + ","
						+ "0";
				errRes = sendShareMsg(CldDalKAccount.getInstance().getKuid(),
						CldDalKAccount.getInstance().getDuid(), 12, CldBllUtil
								.getInstance().getModule(), parm, null, null,
						CldBllUtil.getInstance().getBussinessid(),
						CldDalKAccount.getInstance().getSession(), CldBllUtil
								.getInstance().getAppid(), CldBllUtil
								.getInstance().getApptype(), uniqueids);
			}
			if (targetType == 1) {
				// ���͸�����
				CldSapKMParm parm = new CldSapKMParm();
				parm.setRouteMsg(route);
				// �����û�������kuidƴ�ɷ���Ŀ���ֶ�
				String uniqueids = route.getTarget() + "," + "0";
				errRes = sendShareMsg(CldDalKAccount.getInstance().getKuid(),
						CldDalKAccount.getInstance().getDuid(), 12, CldBllUtil
								.getInstance().getModule(), parm, null, null,
						CldBllUtil.getInstance().getBussinessid(),
						CldDalKAccount.getInstance().getSession(), CldBllUtil
								.getInstance().getAppid(), CldBllUtil
								.getInstance().getApptype(), uniqueids);
			}
		}
		return errRes;
	}

	/**
	 * ������Ϣ
	 * 
	 * @param regionCode
	 *            ������
	 * @param x
	 *            ��ǰ����X
	 * @param y
	 *            ��ǰ����Y
	 * @param istmc
	 *            �Ƿ���·����0��������1���رգ���Ĭ��Ϊ0
	 * @param list
	 *            �ش���Ϣ�б�
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2014-12-18 ����12:48:25
	 */
	public CldSapReturn recShareMsg(int regionCode, long x, long y, int istmc,
			List<CldSapKMParm> list) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKMessage.recShareMsg(CldDalKAccount.getInstance()
					.getDuid(), CldBllUtil.getInstance().getApptype(),
					CldBllUtil.getInstance().getProver(), CldDalKAccount
							.getInstance().getKuid(), regionCode, x, y,
					CldBllUtil.getInstance().getBussinessid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), istmc);
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_downloadMsg");
			CldLog.d("ols", errRes.errMsg + "_downloadMsg");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldSapKMessage.parseMessage(strRtn, list, CldDalKAccount
						.getInstance().getKuid(), CldBllUtil.getInstance()
						.getApptype());
			}
		}
		return errRes;
	}

	/**
	 * �ն˽���������ʷ��Ϣ(204)
	 * 
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param list
	 *            �ش���Ϣ�б�
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2014-9-1 ����11:42:47
	 */
	public CldSapReturn recLastestMsgHistory(String messagetype,
			List<CldSapKMParm> list) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKMessage.recLastestMsgHitory(CldDalKAccount
					.getInstance().getDuid(), messagetype, CldDalKAccount
					.getInstance().getKuid(), CldBllUtil.getInstance()
					.getBussinessid(), CldDalKAccount.getInstance()
					.getSession(), CldBllUtil.getInstance().getAppid(),
					CldBllUtil.getInstance().getApptype());
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtMaxLength protMaxLength = CldSapParser.parseJson(strRtn,
					ProtMaxLength.class, errRes);
			CldLog.d("ols", errRes.errCode + "_reclast");
			CldLog.d("ols", errRes.errMsg + "_reclast");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (null != protMaxLength) {
				if (errRes.errCode == 0) {
					CldSapKMessage.parseMessage(strRtn, list, CldDalKAccount
							.getInstance().getKuid(), CldBllUtil.getInstance()
							.getApptype());
					CldDalKMessage.getInstance().setMaxlength(
							protMaxLength.getMaxlength());
				}
			}
		}
		return errRes;
	}

	/**
	 * �ն˽�����ʷ����Ϣ��ѯ�ӿڣ����»�����
	 * 
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param offset
	 *            ҳ�룬��0��ʼ
	 * @param length
	 *            ÿ�������¼��
	 * @param lastid
	 *            ���һ������Ϣid
	 * @param lasttime
	 *            ���һ������Ϣʱ��
	 * @param list
	 *            �ش���Ϣ�б�
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:12:44
	 */
	public CldSapReturn recNewMsgHistory(String messagetype, int offset,
			int length, long lastid, long lasttime, List<CldSapKMParm> list) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			// ��������
			errRes = CldSapKMessage.recNewMsgHitory(CldDalKAccount
					.getInstance().getDuid(), messagetype, length, lastid,
					lasttime, CldDalKAccount.getInstance().getKuid(),
					CldBllUtil.getInstance().getBussinessid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getApptype());
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_recnew");
			CldLog.d("ols", errRes.errMsg + "_recnew");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldSapKMessage.parseMessage(strRtn, list, CldDalKAccount
						.getInstance().getKuid(), CldBllUtil.getInstance()
						.getApptype());
			}
		}
		return errRes;
	}

	/**
	 * �ն˽�����ʷ����Ϣ��ѯ�ӿڣ����ϻ�����
	 * 
	 * @param messagetype
	 *            1:���Ϣ; 2:������Ϣ; 3:������Ϣ; 11:POI; 12:·��; 13:·��; 14:·��; 15:һ��ͨ
	 *            ��Ϣ���ͣ�֧�ֶ������","�ָ�
	 * @param offset
	 *            ҳ�룬��0��ʼ
	 * @param length
	 *            ÿ�������¼��
	 * @param lastid
	 *            ���һ������Ϣid
	 * @param lasttime
	 *            ���һ������Ϣʱ��
	 * @param list
	 *            �ش���Ϣ�б�
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:13:03
	 */
	public CldSapReturn recOldMsgHistory(String messagetype, int offset,
			int length, int lastid, long lasttime, List<CldSapKMParm> list) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			// ��������
			errRes = CldSapKMessage.recOldMsgHitory(CldDalKAccount
					.getInstance().getDuid(), messagetype, length, lastid,
					lasttime, CldDalKAccount.getInstance().getKuid(),
					CldBllUtil.getInstance().getBussinessid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getApptype());
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_recold");
			CldLog.d("ols", errRes.errMsg + "_recold");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
			if (errRes.errCode == 0) {
				CldSapKMessage.parseMessage(strRtn, list, CldDalKAccount
						.getInstance().getKuid(), CldBllUtil.getInstance()
						.getApptype());
			}
		}
		return errRes;
	}

	/**
	 * �ն��Ķ���Ϣ״̬���½ӿ�(207)
	 * 
	 * @param list
	 *            Str="msgid,createtime,createtype"
	 *            List��Ϣ����Ϣmsgid,createtime,createtype ��ɵ��ַ����б�
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:13:21
	 */
	public CldSapReturn upMsgReadStatus(List<String> list) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			if (list.size() > 0) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < list.size(); i++) {
					stringBuilder.append(list.get(i));
					stringBuilder.append(";");
				}
				String messageids = stringBuilder.toString();
				errRes = CldSapKMessage.upMsgReadStatus(CldDalKAccount
						.getInstance().getDuid(), CldDalKAccount.getInstance()
						.getKuid(), CldDalKAccount.getInstance().getSession(),
						CldBllUtil.getInstance().getBussinessid(), CldBllUtil
								.getInstance().getAppid(), 2, messageids);
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				CldLog.d("ols", errRes.errCode + "_updateMsgRead");
				CldLog.d("ols", errRes.errMsg + "_updateMsgRead");
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
			} else {
				errRes.errCode = -2;
			}
		}
		return errRes;
	}

	/**
	 * �ϱ�λ��
	 * 
	 * @param mobile
	 *            �ֻ���,��Ϊ�գ���kuid�󶨵������ֻ��Ŷ��ϱ�λ��
	 * @param longitude
	 *            WGS84 ���ȣ���λ����
	 * @param latitude
	 *            WGS84 γ�ȣ���λ����
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:13:39
	 */
	public CldSapReturn upLocation(String mobile, double longitude,
			double latitude) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKMessage.upLocation(CldBllUtil.getInstance()
					.getApptype(), CldBllUtil.getInstance().getProver(),
					CldDalKAccount.getInstance().getKuid(), CldDalKAccount
							.getInstance().getSession(), CldBllUtil
							.getInstance().getAppid(), CldBllUtil.getInstance()
							.getBussinessid(), mobile, longitude, latitude);
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldLog.d("ols", errRes.errCode + "_ppt_uploc");
			CldLog.d("ols", errRes.errMsg + "_ppt_uploc");
			CldErrUtil.handleErr(errRes);
			errCodeFix(errRes);
		}
		return errRes;
	}

	/**
	 * ��ȡһ��ͨ��Ϣ
	 * 
	 * @param mobile
	 *            �ֻ��ţ���������ֻ��ţ���ֻ���ѯָ�����ֻ���,������ѭ�����ֻ���
	 * @param longitude
	 *            WGS84 ���ȣ���λ����
	 * @param latitude
	 *            WGS84 γ�ȣ���λ����
	 * @param isLoop
	 *            �Ƿ���ѯ����ֻ���
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-14 ����9:26:09
	 */
	public CldSapReturn recPptMsg(String mobile, double longitude,
			double latitude, boolean isLoop) {
		CldSapReturn errRes = new CldSapReturn();
		// ������ɵ���Ϣ
		List<ShareAKeyCallParm> list = CldDalKCallNavi.getInstance()
				.getMsgList();
		if (null != list) {
			list.clear();
		} else {
			return errRes;
		}
		for (int i = 0; i < 10; i++) { // ִ��10�Σ����5�룬ÿ��ִ��ǰ�ж�����
			if (CldPhoneNet.isNetConnected()) {
				errRes = CldSapKMessage.recPptMsg(CldBllUtil.getInstance()
						.getApptype(), CldBllUtil.getInstance().getProver(),
						CldDalKAccount.getInstance().getKuid(), CldDalKAccount
								.getInstance().getSession(), CldBllUtil
								.getInstance().getAppid(), CldBllUtil
								.getInstance().getBussinessid(), mobile,
						longitude, latitude, isLoop ? 1 : 0);
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
				if (errRes.errCode == 0) {
					CldSapKMessage.parsePPtMessage(strRtn);
				}
				CldLog.d("ols", errRes.errCode + "_ppt_rec_ppt");
				CldLog.d("ols", errRes.errMsg + "_ppt_rec_ppt");
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
			}
			if (errRes.errCode == 403 || 0 == list.size()) { // û�л�ȡ����Ϣ������ʱ5����ִ��һ��
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				continue;
			}
			if (errRes.errCode == 0) {
				break;
			}
		}
		return errRes;
	}

	/**
	 * ��ȡ�ʵ��б�
	 * 
	 * @param isInit
	 *            �Ƿ��ǵ�һ����ȡ�ʵ��б���ʱ���и�����false
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:14:01
	 */
	public CldSapReturn getAreaList(boolean isInit) {
		CldSapReturn errRes = new CldSapReturn();
		if (isInit) {
			/**
			 * ����������һ����ȡ�ʵ�
			 */
			if (CldPhoneNet.isNetConnected()) {
				errRes = CldSapKMessage.getAreaList(0, null, null);
				String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
				ProtEggs protEggs = CldSapParser.parseJson(strRtn,
						ProtEggs.class, errRes);
				CldLog.d("ols", errRes.errCode + "_ppt_rec_egg");
				CldLog.d("ols", errRes.errMsg + "_ppt_rec_egg");
				CldErrUtil.handleErr(errRes);
				errCodeFix(errRes);
				if (null != protEggs) {
					if (errRes.errCode == 0) {
						/**
						 * ���³ɹ��������ʱ���
						 */
						CldSetting.put("ols_egg_utc", CldKAccount.getInstance()
								.getSvrTime());
						List<CldAreaEgg> list = new ArrayList<CldAreaEgg>();
						protEggs.protParse(list);
						CldDalKMessage.getInstance().setListEggs(list);
					}
				}
			}
		} else {
			/**
			 * ��ʱ��30s�ж��Ƿ����
			 */
			/** �ϴλ�ȡ�ʵ��б��ʱ��� */
			long lastEggUtc = CldSetting.getLong("ols_egg_utc");
			/** ��ǰʱ������ϴλ�ȡ�ʵ��б�ʱ������� >2h�Ÿ��� */
			long difUtc = CldKAccount.getInstance().getSvrTime() - lastEggUtc
					- DROP_EGGS_UTC;
			if (difUtc >= 0) {
				if (CldPhoneNet.isNetConnected()) {
					errRes = CldSapKMessage.getAreaList(0, null, null);
					String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
					ProtEggs protEggs = CldSapParser.parseJson(strRtn,
							ProtEggs.class, errRes);
					CldLog.d("ols", errRes.errCode + "_ppt_rec_egg");
					CldLog.d("ols", errRes.errMsg + "_ppt_rec_egg");
					CldErrUtil.handleErr(errRes);
					errCodeFix(errRes);
					if (null != protEggs) {
						if (errRes.errCode == 0) {
							/**
							 * ���³ɹ��������ʱ���
							 */
							CldSetting.put("ols_egg_utc", CldKAccount
									.getInstance().getSvrTime());
							List<CldAreaEgg> list = new ArrayList<CldAreaEgg>();
							protEggs.protParse(list);
							CldDalKMessage.getInstance().setListEggs(list);
						}
					}
				}
			} else {
				CldLog.d("ols", "the DifUTC < 2H:" + (difUtc + DROP_EGGS_UTC));
			}
		}
		return errRes;
	}

	/**
	 * �ж��Ƿ�ȵ���
	 * 
	 * @param x
	 *            ��ǰ����������X
	 * @param y
	 *            ��ǰ����������Y
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-4-8 ����4:14:14
	 */
	public boolean isInEggsArea(long x, long y) {
		List<CldAreaEgg> list = CldDalKMessage.getInstance().getListEggs();
		boolean isIn = false;
		if (list.size() > 0) {
			/**
			 * �������ʵ�
			 */
			for (int i = 0; i < list.size(); i++) {
				/**
				 * �������вʵ�
				 */
				CldAreaEgg egg = list.get(i);
				long nowUtc = CldKAccountAPI.getInstance().getSvrTime();
				/**
				 * ����ǰʱ���ڲʵ�ʱ�䷶Χ�ڣ������������������ ���ǲȵ���
				 */
				if (nowUtc >= egg.getStarttime() && nowUtc <= egg.getEndtime()) {
					if (x >= egg.getMinx() && x <= egg.getMaxx()
							&& y >= egg.getMiny() && y <= egg.getMaxy()) {
						/**
						 * ����ȵ��������ʵ��б��еĲʵ�ɾ��
						 */
						CldLog.d("ols", "InEggsArea");
						CldDalKMessage.getInstance().getListEggs().remove(i);
						i--;
						isIn = true;
						/**
						 * ���ﲻ���break ��Ϊ�˰��������������Ĳʵ������˵��� ��Ϊһ�����㣬�����������вʵ�
						 */
					}
				}
			}
		} else {
			/**
			 * û�������ʵ� Ҳ�����ڲʵ�����
			 */
		}
		return isIn;
	}

	/**
	 * �����봦��
	 * 
	 * @param res
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:14:33
	 */
	public void errCodeFix(CldSapReturn res) {
		switch (res.errCode) {
		case 402: {
			/**
			 * ��Կ����
			 */
			CldDalKMessage.getInstance().setCldKMKey("");
			initKey();
		}
			break;
		case 500: {
			/**
			 * sessionʧЧ�Զ���¼һ��ˢ��session
			 */
			CldKAccount.getInstance().sessionRelogin();
		}
			break;
		case 501:
			/**
			 * sessionʧЧ
			 */
			if (res.session.equals(CldDalKAccount.getInstance().getSession())) {
				/**
				 * ���ӿ�ʹ��session�͵�ǰ�ʻ����session��ͬ�ż�����
				 */
				if (!TextUtils.isEmpty(res.session)) {
					CldKAccountAPI.getInstance().sessionInvalid(501, 0);
				}
			}
			break;
		}
	}
}
