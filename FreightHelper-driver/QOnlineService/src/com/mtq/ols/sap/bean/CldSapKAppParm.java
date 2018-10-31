package com.mtq.ols.sap.bean;

import java.util.List;

import com.mtq.ols.sap.parse.CldKMessageParse.ProtRecMsg.ProtMsgType;


public class CldSapKAppParm {
	
	public static class MtqAppInfo {
		/** Ӧ��ͼ��URL */
		public String app_icon; 
		/** Ӧ������ */
		public String app_name; 
		/** Ӧ�ð汾���� */
		public String ver_name; 
		/** apk���ص�ַ */
		public String app_url; 
		/** �������� */
		public String upgrade_desc; 
		/** ��װ����С����λ���ֽڣ� */
		public int pack_size; 
		/** Ӧ�ð汾���� */
		public int ver_code; 
		/** Ӧ�ð��� */
		public String pack_name; 
		/** �Ƿ�Ĭ��װ 0����; 1����  */
		public int quiesce; 
		/** ���ش��� */
		public int down_times; 
		/** �Ƿ���Ҫ��֤ 0����; 1����  */
		public int validate; 
		/** Ӧ������ */
		public String app_desc; 
	}
	
	public static class MtqAppInfoNew {
		
		public int version;
		public int publishtime;
		public int filesize;
		//ǿ��������ʶ�� 1���ǣ�0����
		public int upgradeflag;
		public int expiredtime;
		
		//���ر�ʶ��1�����أ�2��ɾ��
		public int downloadtype;
		//ѹ����ʶ��1��ѹ����0����ѹ��
		public int zipflag;
		public String filepath;
		public String mark;
	}
	
	public static class MtqLogoTips {
		/** ��·�� */
		public String rooturl; 
		
		public List<MtqLogoList> logolist;
	}
	
	public static class MtqLogoList {
		
		/** logo�汾��  */
		public long logo_prover; 
		/** tips�汾��  */
		public long tips_prover; 
		/** logo����ʱ��  */
		public long logo_timeout; 
		/** tps����ʱ��  */
		public long tips_timeout; 
		/** LogoͼƬ��ַ  */
		public String logo_url; 
		/** TipsͼƬ��ַ�б�  */
		public String[] tips_url; 
		/** ͣ��ʱ��(��) */
		public int stop_time; 
		/** ����ģʽ: 1-�Զ���ʧ; 2-�ֶ�����  */
		public int pic_mode; 
		/** ������ť: 1-Բ��; 2-����; 3-�ް�ť  */
		public int pic_button; 
	}
}
