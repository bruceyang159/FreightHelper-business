package com.mtq.ols.module.delivery.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * ��Ϣʵ����
 * @author ligangfan
 *
 */
@Table("message")
public class MtqMessage{
	
	public static final String COL_MESSAGE_ID = "_messageid";
	public static final String COL_TITLE = "_title";
	public static final String COL_CONTENT = "_content";
	public static final String COL_CREATE_TYPE = "_create_type";
	public static final String COL_RECEIVE_OBJECT = "_receive_object";
	public static final String COL_MSG_TYPE = "_msg_type";
	public static final String COL_CREATE_USER_ID = "_create_user_id";
	public static final String COL_CREATE_USER_NAME = "_create_user_name";
	public static final String COL_USER_MOBILE = "_user_mobile";
	public static final String COL_CREATE_TIME = "_create_time";
	public static final String COL_DOWNLOAD_TIME = "_download_time";
	public static final String COL_STATUS = "_status";
	public static final String COL_APP_TYPE = "_app_type";
	
	/**note������ֶα���Ҫ��ӣ������޷��������ݵĲ������*/
//	@PrimaryKey(AssignType.AUTO_INCREMENT)
//    @Column("_id")
//    protected long id;
	/** ��ϢId */
	@PrimaryKey(AssignType.BY_MYSELF)
	@Column(COL_MESSAGE_ID)
	private long messageId;
	/** ���� */
	@Column(COL_TITLE)
	private String title;
	/**��Ϣ����*/
	@Column(COL_CONTENT)
	private String content;
	/** �������ͣ�1����Ӫ��2���նˣ�. */
	@Column(COL_CREATE_TYPE)
	private int createType;
	/** 1Ϊ���͸��û���Ϣ��2Ϊ���͸��豸��Ϣ. */
	@Column(COL_RECEIVE_OBJECT)
	private int receiveObject;
	/** ��Ϣ����1:���Ϣ;2:������Ϣ;3:������Ϣ;11:POI;12:·��;13:·��;14:·��;15:һ��ͨ.��99��������Ϣ��101��֪ͨ��Ϣ */
	@Column(COL_MSG_TYPE)
	private int msgType;
	/** ������ID. */
	@Column(COL_CREATE_USER_ID)
	private long createuserid;
	/** ����������. */
	@Column(COL_CREATE_USER_NAME)
	private String createusername;
	/** �����߰󶨵��ֻ���. */
	@Column(COL_USER_MOBILE)
	private String usermobile;
	/** ����ʱ��. */
	@Column(COL_CREATE_TIME)
	private String createtime;
	/** ��Ϣ����ʱ��. */
	@Column(COL_DOWNLOAD_TIME)
	private long downloadTime;
	/** ״̬��1������ ��2�������أ�3�����Ķ���. */
	@Column(COL_STATUS)
	private int status;
	/** Ӧ������. */
	@Column(COL_APP_TYPE)
	private int apptype;
	
//	public long getId(){
//		return this.id;
//	}
//	
//	public void setId(long id){
//		this.id = id;
//	}
	
	public long getMessageId(){
		return this.messageId;
	}
	
	public void setMessageId(long messageId){
		this.messageId = messageId;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public int getCreateType(){
		return this.createType;
	}
	
	public void setCreateType(int createType){
		this.createType = createType;
	}
	
	public int getReceiveObject(){
		return this.receiveObject;
	}
	
	public void setReceiveObject(int receiveObject){
		this.receiveObject = receiveObject;
	}
	
	public int getMsgType(){
		return this.msgType;
	}
	
	public void setMsgType(int msgType){
		this.msgType = msgType;
	}
	
	public long getCreateUserId(){
		return this.createuserid;
	}
	
	public void setCreateUserId(long createUserId){
		this.createuserid = createUserId;
	}
	
	public String getCreateUserName(){
		return this.createusername;
	}
	
	public void setCreateUserName(String createUserName){
		this.createusername = createUserName;
	}
	
	public String getUserMobile(){
		return this.usermobile;
	}
	
	public void setUserMobile(String userMobile){
		this.usermobile = userMobile;
	}
	
	public String getCreateTime(){
		return this.createtime;
	}
	
	public void setCreateTime(String createTime){
		this.createtime = createTime;
	}
	
	public long getDownloadTime(){
		return this.downloadTime;
	}
	
	public void setDownloadTime(long downloadTime){
		this.downloadTime = downloadTime;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getAppType(){
		return apptype;
	}
	
	public void setAppType(int appType){
		this.apptype = appType;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Message{" + "messageId = " + messageId + ", title = " + title + ", content = " + content +
				", createType = " + createType + ", receiveObject = " + receiveObject + 
				", msgType = " + msgType + ", createuserid = " + createuserid + ", " + ", createusername = " + 
				createusername + ", usermobile = " + usermobile + ", createtime = " + createtime +
				", downloadTime = " + downloadTime + ", status = " + status + ", apptype = " + apptype +
				super.toString()
				+ "}";
	}
}




