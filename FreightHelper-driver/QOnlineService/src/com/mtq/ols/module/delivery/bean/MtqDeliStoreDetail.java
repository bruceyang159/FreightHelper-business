package com.mtq.ols.module.delivery.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.CldDeliTaskOrders;

/**
 * �˻��������ʵ����
 * @author ligangfan
 *
 */
@Table("storedetail")
public class MtqDeliStoreDetail {

//	@PrimaryKey(AssignType.AUTO_INCREMENT)
//    @Column("_id")
//    public long id;
	/** ���͵��� */
	@PrimaryKey(AssignType.BY_MYSELF)
	@Column("_waybill")
	public String waybill;
	/** �ŵ�ID */
	@Column("_storeid")
	public String storeid;
	/** λ��X(����������) */
	@Column("_storex")
	public long storex;
	/** λ��Y(����������) */
	@Column("_storey")
	public long storey;
	/** �˻������� */
	@Column("_storename")
	public String storename;
	/** �˻����ַ */
	@Column("_storeaddr")
	public String storeaddr;
	/** �ջ��� */
	@Column("_linkman")
	public String linkman;
	/** �ջ��˵绰 */
	@Column("_linkphone")
	public String linkphone;
	/** �ͻ�˳�� */
	@Column("_store_sort")
	public int storesort;
	/** ���ʱ�䣨ֻ������ɲ���ֵ */
	@Column("_finish_time")
	public long finishtime;
	/** �ͻ�˵�� */
	@Column("_store_mark")
	public String storemark;
	/** �ͻ�״̬��0-�ȴ��ͻ���1-���������У�2-��������ͣ�3-��ͣ�ͻ� �� */
	@Column("_store_status")
	public int storestatus;
	/** �������ͣ�1�ͻ�/3���/4�س̣� */
	@Column("_optype")
	public int optype;
	/** ͣ��λ��X(����������) */
	@Column("_stopx")
	public long stopx;
	/** ͣ��λ��Y(����������) */
	@Column("_stopy")
	public long stopy;
	/** �Ƿ���Ҫ�տ0-����Ҫ��1-��Ҫ�� */
	@Column("_pay_mode")
	public int pay_mode;
	/** �տʽ����ѡ����տʽ��û����Ϊ�գ� */
	@Column("_pay_method")
	public String pay_method;
	/** ʵ�ս����յ��Ľ�û����Ϊ0�� */
	@Column("_real_amount")
	public float real_amount;
	/** Ӧ�ս�û����Ϊ0�� */
	@Column("_total_amount")
	public float total_amount;
	/** �˻�ԭ����ѡ����˻�ԭ��û����Ϊ�գ� */
	@Column("_return_desc")
	public String return_desc;
	/** ���һ�θ���ʱ�� */
	@Column("_ddtime")
	public long dttime;
	/** �ͻ�����**/
	@Column("_cust_order_id")
	public String cust_orderid;
	/** �Ƿ���Ҫ�ص� **/
	@Column("_is_receipt")
	public int is_receipt;
	/** �˵��������� **/	
	@Column("_assist_url")
	public String assist_url;
	
	/**�ͻ�����**/
	@Column("_taskdate")
	public String taskdate;

	/** �ͻ���ά�� **/
	/** �����ͻ�״̬��0-�ȴ��ͻ���1-���������У�2-��������ͣ�3-��ͣ�ͻ� �� */
	@Column("_locak_storestatus")
	public int local_storestatus;
//	/** �������˻���ID **/
//	@Column("_taskID")
//	public String taskID;
	/** �տ��˵�� **/
	@Column("_pay_remark")
	public String pay_remark;
//	/** ��ҵID**/
//	@Column("_corpid")
//	public String corpId;
	/** ����ʱ�� */
	@Column("_send_time")
	public long sendtime;
	/** ���ɻص�ʱ�� **/
	@Column("_receipt_time")
	public  long receiptTime;
	
//	/**��ǰ���վ*/
//	@Column("_pdeliver")
//	public String pdeliver;
//	/**��ǰ�ջ�վ*/
//	@Column("_preceipt")
//	public String preceipt;
	
	@Column("_task_id")
	public String taskId;
	
	@Column("_corp_id")
	public String corpId;
	
	/**orders��Ϣ**/
//	@Mapping(Relation.OneToOne)
//	public MtqDeliOrderDetail orders;
	
	
//	public String photo_ids;
//	
//	public long reqtime;
//	public long reqtime_e;
//	
//	
//	public String receipt_ids;
//	public String send_name;
//	public String send_phone;
//	public String send_addr;
//	public String send_kcode;
//	
//	
//	public String receive_name;
//	public String receive_phone;
//	public String receive_addr;
//	public String receive_kcode;
	
	
	
}
