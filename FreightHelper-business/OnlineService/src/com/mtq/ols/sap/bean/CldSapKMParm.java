/*
 * @Title CldSapKMParm.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.sap.bean;

import java.io.Serializable;



/**
 * ��Ϣϵͳ������
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:52:17
 */
public class CldSapKMParm implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** ��ϢId */
	private long messageId;
	/** ���� */
	private String title;
	/** �������ͣ�1����Ӫ��2���նˣ�. */
	private int createType;
	/** 1Ϊ���͸��û���Ϣ��2Ϊ���͸��豸��Ϣ. */
	private int receiveObject;
	/** ��Ϣ����1:���Ϣ;2:������Ϣ;3:������Ϣ;11:POI;12:·��;13:·��;14:·��;15:һ��ͨ. */
	private int msgType;
	/** ������ID. */
	private long createuserid;
	/** ����������. */
	private String createuser;
	/** �����߰󶨵��ֻ���. */
	private String usermobile;
	/** ����ʱ��. */
	private String createtime;
	/** ��Ϣ����ʱ��. */
	private long downloadTime;
	/** ״̬��1������ ��2�������أ�3�����Ķ���. */
	private int status;
	/** λ����Ϣ. */
	private SharePoiParm poiMsg;
	/** ·����Ϣ. */
	private ShareRouteParm routeMsg;
	/** ��Ӫ��Ϣ. */
	private ShareOperateParm operateMsg;
	/** һ��ͨ��Ϣ. */
	private ShareAKeyCallParm aKeyCallMsg;
	/** ������. */
	private String hyperlink;
	/** Ӧ������. */
	private int apptype;
	/** ����������ʽ. */
	private int poptype;
	/** ͼƬ���� ָ���������Բ��� */
	private String imageurl;
	
	private String imglink;
	
	/** ��·���� ָ���������Բ��� */
	private String roadname;
	/** ��Ϣ����ģ��(1:K��;2:WEB��ͼ;3:һ��ͨ) */
	private int module;

	/**
	 * Instantiates a new cld sap km parm.
	 */
	public CldSapKMParm() {
		msgType = -1;
		// module = -1;
		createType = -1;
		receiveObject = -1;
		messageId = -1;
		title = "";
		createuserid = -1;
		createuser = "";
		usermobile = "";
		createtime = "";
		hyperlink = "";
		apptype = -1;
		poptype = -1;
		imageurl = "";
		imglink = "";
		roadname = "";
		downloadTime = 0;
		status = 0;
		poiMsg = new SharePoiParm();
		routeMsg = new ShareRouteParm();
		operateMsg = new ShareOperateParm();
		aKeyCallMsg = new ShareAKeyCallParm();
	}

	/**
	 * Gets the msg type.
	 * 
	 * @return the msg type
	 */
	public int getMsgType() {
		return msgType;
	}

	/**
	 * Sets the msg type.
	 * 
	 * @param msgType
	 *            the new msg type
	 */
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	/**
	 * Gets the module.
	 * 
	 * @return the module
	 */
	public int getModule() {
		return module;
	}

	/**
	 * Sets the module.
	 * 
	 * @param module
	 *            the new module
	 */
	public void setModule(int module) {
		this.module = module;
	}

	/**
	 * Gets the poi msg.
	 * 
	 * @return the poi msg
	 */
	public SharePoiParm getPoiMsg() {
		return poiMsg;
	}

	/**
	 * Sets the poi msg.
	 * 
	 * @param poiMsg
	 *            the new poi msg
	 */
	public void setPoiMsg(SharePoiParm poiMsg) {
		this.poiMsg = poiMsg;
	}

	/**
	 * Gets the route msg.
	 * 
	 * @return the route msg
	 */
	public ShareRouteParm getRouteMsg() {
		return routeMsg;
	}

	/**
	 * Sets the route msg.
	 * 
	 * @param routeMsg
	 *            the new route msg
	 */
	public void setRouteMsg(ShareRouteParm routeMsg) {
		this.routeMsg = routeMsg;
	}

	/**
	 * Gets the creates the type.
	 * 
	 * @return the creates the type
	 */
	public int getCreateType() {
		return createType;
	}

	/**
	 * Sets the creates the type.
	 * 
	 * @param createType
	 *            the new creates the type
	 */
	public void setCreateType(int createType) {
		this.createType = createType;
	}

	/**
	 * Gets the receive object.
	 * 
	 * @return the receive object
	 */
	public int getReceiveObject() {
		return receiveObject;
	}

	/**
	 * Sets the receive object.
	 * 
	 * @param receiveObject
	 *            the new receive object
	 */
	public void setReceiveObject(int receiveObject) {
		this.receiveObject = receiveObject;
	}

	/**
	 * Gets the message id.
	 * 
	 * @return the message id
	 */
	public long getMessageId() {
		return messageId;
	}

	/**
	 * Sets the message id.
	 * 
	 * @param messageId
	 *            the new message id
	 */
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	/**
	 * Gets the operate msg.
	 * 
	 * @return the operate msg
	 */
	public ShareOperateParm getOperateMsg() {
		return operateMsg;
	}

	/**
	 * Sets the operate msg.
	 * 
	 * @param operateMsg
	 *            the new operate msg
	 */
	public void setOperateMsg(ShareOperateParm operateMsg) {
		this.operateMsg = operateMsg;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the createuser.
	 * 
	 * @return the createuser
	 */
	public String getCreateuser() {
		return createuser;
	}

	/**
	 * Sets the createuser.
	 * 
	 * @param createuser
	 *            the new createuser
	 */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	/**
	 * Gets the createtime.
	 * 
	 * @return the createtime
	 */
	public String getCreatetime() {
		return createtime;
	}

	/**
	 * Sets the createtime.
	 * 
	 * @param createtime
	 *            the new createtime
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the hyperlink.
	 * 
	 * @return the hyperlink
	 */
	public String getHyperlink() {
		return hyperlink;
	}

	/**
	 * Sets the hyperlink.
	 * 
	 * @param hyperlink
	 *            the new hyperlink
	 */
	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	/**
	 * Gets the imageurl.
	 * 
	 * @return the imageurl
	 */
	public String getImageurl() {
		return imageurl;
	}

	/**
	 * Sets the imageurl.
	 * 
	 * @param imageurl
	 *            the new imageurl
	 */
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
	public String getImglink() {
		return imglink;
	}

	public void setImglink(String imglink) {
		this.imglink = imglink;
	}

	/**
	 * Gets the roadname.
	 * 
	 * @return the roadname
	 */
	public String getRoadname() {
		return roadname;
	}

	/**
	 * Sets the roadname.
	 * 
	 * @param roadname
	 *            the new roadname
	 */
	public void setRoadname(String roadname) {
		this.roadname = roadname;
	}

	/**
	 * Gets the a key call msg.
	 * 
	 * @return the a key call msg
	 */
	public ShareAKeyCallParm getaKeyCallMsg() {
		return aKeyCallMsg;
	}

	/**
	 * Sets the a key call msg.
	 * 
	 * @param aKeyCallMsg
	 *            the new a key call msg
	 */
	public void setaKeyCallMsg(ShareAKeyCallParm aKeyCallMsg) {
		this.aKeyCallMsg = aKeyCallMsg;
	}

	/**
	 * Gets the createuserid.
	 * 
	 * @return the createuserid
	 */
	public long getCreateuserid() {
		return createuserid;
	}

	/**
	 * Sets the createuserid.
	 * 
	 * @param createuserid
	 *            the new createuserid
	 */
	public void setCreateuserid(long createuserid) {
		this.createuserid = createuserid;
	}

	/**
	 * Gets the apptype.
	 * 
	 * @return the apptype
	 */
	public int getApptype() {
		return apptype;
	}

	/**
	 * Sets the apptype.
	 * 
	 * @param apptype
	 *            the new apptype
	 */
	public void setApptype(int apptype) {
		this.apptype = apptype;
	}

	/**
	 * Gets the poptype.
	 * 
	 * @return the poptype
	 */
	public int getPoptype() {
		return poptype;
	}

	/**
	 * Sets the poptype.
	 * 
	 * @param poptype
	 *            the new poptype
	 */
	public void setPoptype(int poptype) {
		this.poptype = poptype;
	}

	/**
	 * Gets the download time.
	 * 
	 * @return the download time
	 */
	public long getDownloadTime() {
		return downloadTime;
	}

	/**
	 * Sets the download time.
	 * 
	 * @param downloadTime
	 *            the new download time
	 */
	public void setDownloadTime(long downloadTime) {
		this.downloadTime = downloadTime;
	}

	/**
	 * Gets the usermobile.
	 * 
	 * @return the usermobile
	 */
	public String getUsermobile() {
		return usermobile;
	}

	/**
	 * Sets the usermobile.
	 * 
	 * @param usermobile
	 *            the new usermobile
	 */
	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	/**
	 * λ����Ϣ
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:52:38
	 */
	public static class SharePoiParm implements Serializable {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		/** Poi���� */
		private String name;
		/** Poi����(�� x,y,name) */
		private String poi;
		/** ��Ϣ����Ŀ���kuid. */
		private long target;

		/**
		 * Instantiates a new share poi parm.
		 */
		public SharePoiParm() {
			name = "";
			poi = "";
			target = -1;
		}

		/**
		 * Instantiates a new share poi parm.
		 * 
		 * @param name
		 *            poi����
		 * @param poi
		 *            poi�㣨x,y��
		 * @param target
		 *            ��Ϣ����Ŀ���kuid
		 */
		public SharePoiParm(String name, String poi, long target) {
			this.name = name;
			this.poi = poi;
			this.target = target;
		}

		/**
		 * Gets the name.
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the name.
		 * 
		 * @param name
		 *            the new name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the poi.
		 * 
		 * @return the poi
		 */
		public String getPoi() {
			return poi;
		}

		/**
		 * Sets the poi.
		 * 
		 * @param poi
		 *            the new poi
		 */
		public void setPoi(String poi) {
			this.poi = poi;
		}

		/**
		 * Gets the target.
		 * 
		 * @return the target
		 */
		public long getTarget() {
			return target;
		}

		/**
		 * Sets the target.
		 * 
		 * @param target
		 *            the new target
		 */
		public void setTarget(long target) {
			this.target = target;
		}
	}

	/**
	 * ·����Ϣ
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:52:49
	 */
	public static class ShareRouteParm implements Serializable {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		/** ·������. */
		private String name;
		/** ����������(�� x,y,name). */
		private String start;
		/** Ŀ�ĵ�����(�� x,y,name). */
		private String end;
		/** ���ɵأ�������,�ָ������������;�ָ�����x,y,name;x,y,name. */
		private String routePoint;
		/**
		 * �رܵأ������;�ָ�����%d,%d,%d,name; %d,%d,%d,name; %d,%d,%d,name(x,y,uid,name)
		 */
		private String avoidPoint;
		/** �滮����(ϵͳ�Ƽ�=1;��������=2;һ���·����=4;��������=8;���߸���=16;����ģʽ=32). */
		private int conditionCode;
		/** ��������(�����շ�=1;�����ֶ�=2;�����շ�վ=4;�����ͷ=8). */
		private int aviodCondition;
		/** �ر�����(�رܸ���=1;�ر��ֶ�=2;�ر��շ�վ=4;�رܵ�ͷ=8;�رܸ߼�=16). */
		private int forbidCondition;
		/** ��ͼ�汾��. */
		private String mapVer;
		/** ��Ϣ����Ŀ���kuid. */
		private long target;

		/**
		 * Instantiates a new share route parm.
		 */
		public ShareRouteParm() {
			name = "";
			start = "";
			end = "";
			routePoint = "";
			avoidPoint = "";
			conditionCode = -1;
			aviodCondition = -1;
			forbidCondition = -1;
			target = -1;
			mapVer = "";
		}

		/**
		 * Instantiates a new share route parm.
		 * 
		 * @param name
		 *            ·������
		 * @param start
		 *            ������(X,Y,name)
		 * @param end
		 *            Ŀ�ĵ�(X,Y,name)
		 * @param routePoint
		 *            ���ɵأ�������,�ָ������������;�ָ�����x,y,name;x,y,name
		 * @param avoidPoint
		 *            �رܵأ������;�ָ�����%d,%d,%d,name,%d;%d,%d,name,%d,%d,%d,name;%d,%
		 *            d,%d,name(x,y,uid,name)
		 * @param conditionCode
		 *            �滮����(ϵͳ�Ƽ�=1;��������=2;һ���·����=4;��������=8;���߸���=16;����ģʽ=32)
		 * @param aviodCondition
		 *            ��������(�����շ�=1;�����ֶ�=2;�����շ�վ=4;�����ͷ=8)
		 * @param forbidCondition
		 *            �ر�����(�رܸ���=1;�ر��ֶ�=2;�ر��շ�վ=4;�رܵ�ͷ=8;�رܸ߼�=16)
		 * @param mapVer
		 *            ��ͼ�汾��
		 * @param target
		 *            ��Ϣ����Ŀ���kuid
		 */
		public ShareRouteParm(String name, String start, String end,
				String routePoint, String avoidPoint, int conditionCode,
				int aviodCondition, int forbidCondition, String mapVer,
				long target) {
			this.name = name;
			this.start = start;
			this.end = end;
			this.routePoint = routePoint;
			this.avoidPoint = avoidPoint;
			this.conditionCode = conditionCode;
			this.aviodCondition = aviodCondition;
			this.forbidCondition = forbidCondition;
			this.target = target;
			this.mapVer = mapVer;
		}

		/**
		 * Gets the name.
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the name.
		 * 
		 * @param name
		 *            the new name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the start.
		 * 
		 * @return the start
		 */
		public String getStart() {
			return start;
		}

		/**
		 * Sets the start.
		 * 
		 * @param start
		 *            the new start
		 */
		public void setStart(String start) {
			this.start = start;
		}

		/**
		 * Gets the end.
		 * 
		 * @return the end
		 */
		public String getEnd() {
			return end;
		}

		/**
		 * Sets the end.
		 * 
		 * @param end
		 *            the new end
		 */
		public void setEnd(String end) {
			this.end = end;
		}

		/**
		 * Gets the route point.
		 * 
		 * @return the route point
		 */
		public String getRoutePoint() {
			return routePoint;
		}

		/**
		 * Sets the route point.
		 * 
		 * @param routePoint
		 *            the new route point
		 */
		public void setRoutePoint(String routePoint) {
			this.routePoint = routePoint;
		}

		/**
		 * Gets the avoid point.
		 * 
		 * @return the avoid point
		 */
		public String getAvoidPoint() {
			return avoidPoint;
		}

		/**
		 * Sets the avoid point.
		 * 
		 * @param avoidPoint
		 *            the new avoid point
		 */
		public void setAvoidPoint(String avoidPoint) {
			this.avoidPoint = avoidPoint;
		}

		/**
		 * Gets the condition code.
		 * 
		 * @return the condition code
		 */
		public int getConditionCode() {
			return conditionCode;
		}

		/**
		 * Sets the condition code.
		 * 
		 * @param conditionCode
		 *            the new condition code
		 */
		public void setConditionCode(int conditionCode) {
			this.conditionCode = conditionCode;
		}

		/**
		 * Gets the aviod condition.
		 * 
		 * @return the aviod condition
		 */
		public int getAviodCondition() {
			return aviodCondition;
		}

		/**
		 * Sets the aviod condition.
		 * 
		 * @param aviodCondition
		 *            the new aviod condition
		 */
		public void setAviodCondition(int aviodCondition) {
			this.aviodCondition = aviodCondition;
		}

		/**
		 * Gets the forbid condition.
		 * 
		 * @return the forbid condition
		 */
		public int getForbidCondition() {
			return forbidCondition;
		}

		/**
		 * Sets the forbid condition.
		 * 
		 * @param forbidCondition
		 *            the new forbid condition
		 */
		public void setForbidCondition(int forbidCondition) {
			this.forbidCondition = forbidCondition;
		}

		/**
		 * Gets the map ver.
		 * 
		 * @return the map ver
		 */
		public String getMapVer() {
			return mapVer;
		}

		/**
		 * Sets the map ver.
		 * 
		 * @param mapVer
		 *            the new map ver
		 */
		public void setMapVer(String mapVer) {
			this.mapVer = mapVer;
		}

		/**
		 * Gets the target.
		 * 
		 * @return the target
		 */
		public long getTarget() {
			return target;
		}

		/**
		 * Sets the target.
		 * 
		 * @param target
		 *            the new target
		 */
		public void setTarget(long target) {
			this.target = target;
		}
	}

	/**
	 * ��Ӫ��Ϣ
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:53:05
	 */
	public static class ShareOperateParm implements Serializable {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		/** ��Ϣ����. */
		private String content;

		/**
		 * Instantiates a new share operate parm.
		 */
		public ShareOperateParm() {
			content = "";
		}

		/**
		 * Instantiates a new share operate parm.
		 * 
		 * @param content
		 *            the content
		 */
		public ShareOperateParm(String content) {
			this.content = content;
		}

		/**
		 * Gets the content.
		 * 
		 * @return the content
		 */
		public String getContent() {
			return content;
		}

		/**
		 * Sets the content.
		 * 
		 * @param content
		 *            the new content
		 */
		public void setContent(String content) {
			this.content = content;
		}
	}

	/**
	 * һ��ͨ��Ϣ
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:53:15
	 */
	public static class ShareAKeyCallParm implements Serializable {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		/** The action. */
		private String action;
		/** ��ʼ��. */
		private AKeyCallPoint startPoint;
		/** �յ�. */
		private AKeyCallPoint endPoint;
		/** �ƾ���. */
		private AKeyCallPoint routePoint;
		/** ��ܵ�. */
		private AKeyCallPoint avoidPoint;
		/** ����ģʽ. */
		private String navigationMode;
		/** ��ǰ����ID. */
		private String proxy_Id;

		/**
		 * Instantiates a new share a key call parm.
		 */
		public ShareAKeyCallParm() {
			this.action = "";
			this.startPoint = new AKeyCallPoint();
			this.endPoint = new AKeyCallPoint();
			this.routePoint = new AKeyCallPoint();
			this.avoidPoint = new AKeyCallPoint();
			this.navigationMode = "";
			this.proxy_Id = "";
		}

		/**
		 * Instantiates a new share a key call parm.
		 * 
		 * @param action
		 *            the action
		 * @param startPoint
		 *            the start point
		 * @param endPoint
		 *            the end point
		 * @param routePoint
		 *            the route point
		 * @param avoidPoint
		 *            the avoid point
		 * @param navigationMode
		 *            the navigation mode
		 * @param proxy_Id
		 *            the proxy_ id
		 */
		public ShareAKeyCallParm(String action, AKeyCallPoint startPoint,
				AKeyCallPoint endPoint, AKeyCallPoint routePoint,
				AKeyCallPoint avoidPoint, String navigationMode, String proxy_Id) {
			this.action = action;
			this.startPoint = startPoint;
			this.endPoint = endPoint;
			this.routePoint = routePoint;
			this.avoidPoint = avoidPoint;
			this.navigationMode = navigationMode;
			this.proxy_Id = proxy_Id;
		}

		/**
		 * Gets the action.
		 * 
		 * @return the action
		 */
		public String getAction() {
			return action;
		}

		/**
		 * Sets the action.
		 * 
		 * @param action
		 *            the new action
		 */
		public void setAction(String action) {
			this.action = action;
		}

		/**
		 * Gets the start point.
		 * 
		 * @return the start point
		 */
		public AKeyCallPoint getStartPoint() {
			return startPoint;
		}

		/**
		 * Sets the start point.
		 * 
		 * @param startPoint
		 *            the new start point
		 */
		public void setStartPoint(AKeyCallPoint startPoint) {
			this.startPoint = startPoint;
		}

		/**
		 * Gets the end point.
		 * 
		 * @return the end point
		 */
		public AKeyCallPoint getEndPoint() {
			return endPoint;
		}

		/**
		 * Sets the end point.
		 * 
		 * @param endPoint
		 *            the new end point
		 */
		public void setEndPoint(AKeyCallPoint endPoint) {
			this.endPoint = endPoint;
		}

		/**
		 * Gets the route point.
		 * 
		 * @return the route point
		 */
		public AKeyCallPoint getRoutePoint() {
			return routePoint;
		}

		/**
		 * Sets the route point.
		 * 
		 * @param routePoint
		 *            the new route point
		 */
		public void setRoutePoint(AKeyCallPoint routePoint) {
			this.routePoint = routePoint;
		}

		/**
		 * Gets the avoid point.
		 * 
		 * @return the avoid point
		 */
		public AKeyCallPoint getAvoidPoint() {
			return avoidPoint;
		}

		/**
		 * Sets the avoid point.
		 * 
		 * @param avoidPoint
		 *            the new avoid point
		 */
		public void setAvoidPoint(AKeyCallPoint avoidPoint) {
			this.avoidPoint = avoidPoint;
		}

		/**
		 * Gets the navigation mode.
		 * 
		 * @return the navigation mode
		 */
		public String getNavigationMode() {
			return navigationMode;
		}

		/**
		 * Sets the navigation mode.
		 * 
		 * @param navigationMode
		 *            the new navigation mode
		 */
		public void setNavigationMode(String navigationMode) {
			this.navigationMode = navigationMode;
		}

		/**
		 * Gets the proxy id.
		 * 
		 * @return the proxy id
		 */
		public String getProxyId() {
			return proxy_Id;
		}

		/**
		 * Sets the proxy id.
		 * 
		 * @param proxy_Id
		 *            the new proxy id
		 */
		public void setProxyId(String proxy_Id) {
			this.proxy_Id = proxy_Id;
		}

		/**
		 * һ��ͨ��Ϣ Point
		 * 
		 * @author Zhouls
		 * @date 2015-3-5 ����3:56:36
		 */
		public static class AKeyCallPoint implements Serializable {
			/** The Constant serialVersionUID. */
			private static final long serialVersionUID = 1L;
			/** POI����. */
			private String name;
			/** ����. */
			private String longitude;
			/** γ��. */
			private String latitude;

			/**
			 * Instantiates a new a key call point.
			 */
			public AKeyCallPoint() {
				this.name = "";
				this.longitude = "0.0";
				this.latitude = "0.0";
			}

			/**
			 * Instantiates a new a key call point.
			 * 
			 * @param name
			 *            the name
			 * @param longitude
			 *            the longitude
			 * @param latitude
			 *            the latitude
			 */
			public AKeyCallPoint(String name, String longitude, String latitude) {
				this.name = name;
				this.longitude = longitude;
				this.latitude = latitude;
			}

			/**
			 * Gets the name.
			 * 
			 * @return the name
			 */
			public String getName() {
				return name;
			}

			/**
			 * Sets the name.
			 * 
			 * @param name
			 *            the new name
			 */
			public void setName(String name) {
				this.name = name;
			}

			/**
			 * Gets the longitude.
			 * 
			 * @return the longitude
			 */
			public String getLongitude() {
				return longitude;
			}

			/**
			 * Sets the longitude.
			 * 
			 * @param longitude
			 *            the new longitude
			 */
			public void setLongitude(String longitude) {
				this.longitude = longitude;
			}

			/**
			 * Gets the latitude.
			 * 
			 * @return the latitude
			 */
			public String getLatitude() {
				return latitude;
			}

			/**
			 * Sets the latitude.
			 * 
			 * @param latitude
			 *            the new latitude
			 */
			public void setLatitude(String latitude) {
				this.latitude = latitude;
			}
		}

		/**
		 * �ʵ���Ϣ
		 * 
		 * @author Zhouls
		 * @date 2015-3-5 ����3:56:47
		 */
		public static class CldAreaEgg {
			/** The areaid. */
			private long areaid;
			/** The type. */
			private int type;
			/** The starttime. */
			private long starttime;
			/** The endtime. */
			private long endtime;
			/** The regioncode. */
			private long regioncode;
			/** The minx. */
			private long minx;
			/** The miny. */
			private long miny;
			/** The maxx. */
			private long maxx;
			/** The maxy. */
			private long maxy;

			/**
			 * Instantiates a new cld area egg.
			 */
			public CldAreaEgg() {
				areaid = 0;
				type = 0;
				starttime = 0;
				endtime = 0;
				regioncode = 0;
				minx = 0;
				miny = 0;
				maxx = 0;
				maxy = 0;
			}

			/**
			 * Gets the areaid.
			 * 
			 * @return the areaid
			 */
			public long getAreaid() {
				return areaid;
			}

			/**
			 * Sets the areaid.
			 * 
			 * @param areaid
			 *            the new areaid
			 */
			public void setAreaid(long areaid) {
				this.areaid = areaid;
			}

			/**
			 * Gets the type.
			 * 
			 * @return the type
			 */
			public int getType() {
				return type;
			}

			/**
			 * Sets the type.
			 * 
			 * @param type
			 *            the new type
			 */
			public void setType(int type) {
				this.type = type;
			}

			/**
			 * Gets the starttime.
			 * 
			 * @return the starttime
			 */
			public long getStarttime() {
				return starttime;
			}

			/**
			 * Sets the starttime.
			 * 
			 * @param starttime
			 *            the new starttime
			 */
			public void setStarttime(long starttime) {
				this.starttime = starttime;
			}

			/**
			 * Gets the endtime.
			 * 
			 * @return the endtime
			 */
			public long getEndtime() {
				return endtime;
			}

			/**
			 * Sets the endtime.
			 * 
			 * @param endtime
			 *            the new endtime
			 */
			public void setEndtime(long endtime) {
				this.endtime = endtime;
			}

			/**
			 * Gets the regioncode.
			 * 
			 * @return the regioncode
			 */
			public long getRegioncode() {
				return regioncode;
			}

			/**
			 * Sets the regioncode.
			 * 
			 * @param regioncode
			 *            the new regioncode
			 */
			public void setRegioncode(long regioncode) {
				this.regioncode = regioncode;
			}

			/**
			 * Gets the minx.
			 * 
			 * @return the minx
			 */
			public long getMinx() {
				return minx;
			}

			/**
			 * Sets the minx.
			 * 
			 * @param minx
			 *            the new minx
			 */
			public void setMinx(long minx) {
				this.minx = minx;
			}

			/**
			 * Gets the miny.
			 * 
			 * @return the miny
			 */
			public long getMiny() {
				return miny;
			}

			/**
			 * Sets the miny.
			 * 
			 * @param miny
			 *            the new miny
			 */
			public void setMiny(long miny) {
				this.miny = miny;
			}

			/**
			 * Gets the maxx.
			 * 
			 * @return the maxx
			 */
			public long getMaxx() {
				return maxx;
			}

			/**
			 * Sets the maxx.
			 * 
			 * @param maxx
			 *            the new maxx
			 */
			public void setMaxx(long maxx) {
				this.maxx = maxx;
			}

			/**
			 * Gets the maxy.
			 * 
			 * @return the maxy
			 */
			public long getMaxy() {
				return maxy;
			}

			/**
			 * Sets the maxy.
			 * 
			 * @param maxy
			 *            the new maxy
			 */
			public void setMaxy(long maxy) {
				this.maxy = maxy;
			}
		}
	}
}
