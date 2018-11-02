package com.mtq.ols.module.delivery.bean;

import java.util.ArrayList;
import java.util.List;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;
import com.mtq.ols.module.delivery.CldSapKDeliveryParam.Goods;

/**
 * �˵�����ʵ����
 * @author ligangfan
 *
 */
@Table("orderdetail")
public class MtqDeliOrderDetail {

	@PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public long id;
	/** �ͻ�����**/
	@Column("_cust_orderid")
	public String cust_orderid;
	/** Ҫ���ʹ�ʱ��(��)**/
	@Column("_reqtime")
	public  long reqtime;
	/** Ҫ���ʹ�ʱ��(ֹ)**/
	@Column("_reqtime_e")
	public  long reqtime_e;
	/** ������**/
	@Column("_send_name")
	public String send_name;
	/** �����˵绰**/
	@Column("_send_phone")
	public String send_phone;
	/** �����˵�ַ **/
	@Column("_send_addr")
	public String send_addr;
	/** ������K�� **/
	@Column("_send_kcode")
	public String send_kcode;
	/** �ջ���**/
	@Column("_receive_name")
	public String receive_name;
	/** �ջ��˵绰**/
	@Column("_receive_phone")
	public String receive_phone;
	/** �ջ��˵�ַ**/
	@Column("_receive_addr")
	public String receive_addr;
	/** �ջ���K��**/
	@Column("_receive_kcode")
	public String receive_kcode;
	/** ������֪**/
	@Column("_note")
	public String note;
	/** ���ϴ���Ƭ�� **/
	@Column("_photo_nums")
	public int photo_nums;
	/** ���ϴ��ص��� **/
	@Column("_receipt_nums")
	public int receipt_nums;
	/** ���ϴ���Ƭ�� **/
	@Column("_can_photo_nums")
	public int can_photo_nums;
	/** ���ϴ��ص��� **/
	@Column("_can_receipt_nums")
	public int can_receipt_nums;
	
	
	/**���ϴ��ص���ͼƬ��ţ���Сд��,�����ţ��ָ�**/
	@Column("_receipt_kcode")
	public String receipt_ids;
	/**���ϴ���Ƭ��ͼƬ��ţ���Сд��,�����ţ��ָ�*/
	@Column("_photo_kcode")
	public String photo_ids;
	
	@Column("_task_id")
	public String taskId;
	
	@Column("_corp_id")
	public String corpId;
	
	
	/** ����**/
	@Mapping(Relation.OneToMany)
	public ArrayList<MtqGoodDetail> goods;
	
}
