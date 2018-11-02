/*
 * @Title CldDalKConfig.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:59
 * @version 1.0
 */
package com.mtq.ols.dal;

import java.util.HashMap;
import java.util.Map;
import com.mtq.ols.sap.bean.CldSapKConfigParm.CldDomainConfig;
import com.mtq.ols.sap.bean.CldSapKConfigParm.CldKCloudConfig;
import com.mtq.ols.sap.bean.CldSapKConfigParm.CldPosUpConfig;
import com.mtq.ols.sap.bean.CldSapKConfigParm.CldThirdOpenConfig;
import com.mtq.ols.sap.bean.CldSapKConfigParm.CldWebUrlConfig;
import com.mtq.ols.sap.parse.CldKConfigParse.ProtConfig;
import com.mtq.ols.tools.CldSapParser;

/**
 * �������ݲ�
 * 
 * @author Zhouls
 * @date 2014-10-28 ����11:46:29
 */
public class CldDalKConfig {

	/** �������� */
	private CldDomainConfig domainConfig;
	/** WebUrl����. */
	private CldWebUrlConfig webUrlConfig;
	/** // �������ӿڿ�������. */
	private CldThirdOpenConfig thirdPartConfig;
	/** λ���ϱ����ýӿ� */
	private CldPosUpConfig posReportConfig;
	/** K������ */
	private CldKCloudConfig kcCloundConfig;
	/** �洢�������map */
	private Map<String, String> jsonMap;

	private static CldDalKConfig cldDalKConfig;

	/**
	 * Gets the single instance of CldDalKConfig.
	 * 
	 * @return single instance of CldDalKConfig
	 */
	public static CldDalKConfig getInstance() {
		if (null == cldDalKConfig) {
			cldDalKConfig = new CldDalKConfig();
		}
		return cldDalKConfig;
	}

	/**
	 * Instantiates a new cld dal k config.
	 */
	private CldDalKConfig() {
		domainConfig = new CldDomainConfig();
		webUrlConfig = new CldWebUrlConfig();
		thirdPartConfig = new CldThirdOpenConfig();
		posReportConfig = new CldPosUpConfig();
		kcCloundConfig = new CldKCloudConfig();
		jsonMap = new HashMap<String, String>();
	}

	/**
	 * ������˷��ص�json �������������������
	 * 
	 * @param protConfig
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-19 ����5:22:59
	 */
	public void loadConfig(ProtConfig protConfig) {
		if (null != protConfig.getItem()) {
			for (int i = 0; i < protConfig.getItem().size(); i++) {
				if (null != protConfig.getItem().get(i)) {
					long clastype = protConfig.getItem().get(i).getClasstype();
					if (clastype == 1001001100) {
						domainConfig.protParse(protConfig.getItem().get(i));
						jsonMap.put("1001001100",
								CldSapParser.objectToJson(domainConfig));
					} else if (clastype == 1001003000) {
						webUrlConfig.protParse(protConfig.getItem().get(i));
						jsonMap.put("1001003000",
								CldSapParser.objectToJson(webUrlConfig));
					} else if (clastype == 1003001200) {
						thirdPartConfig.protParse(protConfig.getItem().get(i));
						jsonMap.put("1003001200",
								CldSapParser.objectToJson(thirdPartConfig));
					} else if (clastype == 2002001000) {
						posReportConfig.protParse(protConfig.getItem().get(i));
						jsonMap.put("2002001000",
								CldSapParser.objectToJson(posReportConfig));
					} else if (clastype == 2004001000) {
						kcCloundConfig.protParse(protConfig.getItem().get(i));
						jsonMap.put("2004001000",
								CldSapParser.objectToJson(kcCloundConfig));
					}
				}
			}
		}
	}

	public Map<String, String> getJsonMap() {
		return jsonMap;
	}

	public CldDomainConfig getDomainConfig() {
		return domainConfig;
	}

	public void setDomainConfig(CldDomainConfig domainConfig) {
		this.domainConfig = domainConfig;
	}

	public CldWebUrlConfig getWebUrlConfig() {
		return webUrlConfig;
	}

	public void setWebUrlConfig(CldWebUrlConfig webUrlConfig) {
		this.webUrlConfig = webUrlConfig;
	}

	public CldThirdOpenConfig getThirdPartConfig() {
		return thirdPartConfig;
	}

	public void setThirdPartConfig(CldThirdOpenConfig thirdPartConfig) {
		this.thirdPartConfig = thirdPartConfig;
	}

	public CldPosUpConfig getPosReportConfig() {
		return posReportConfig;
	}

	public void setPosReportConfig(CldPosUpConfig posReportConfig) {
		this.posReportConfig = posReportConfig;
	}

	public CldKCloudConfig getKcCloundConfig() {
		return kcCloundConfig;
	}

	public void setKcCloundConfig(CldKCloudConfig kcCloundConfig) {
		this.kcCloundConfig = kcCloundConfig;
	}
}
