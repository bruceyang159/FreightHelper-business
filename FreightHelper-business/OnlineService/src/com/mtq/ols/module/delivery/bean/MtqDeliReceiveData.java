package com.mtq.ols.module.delivery.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;


/**
 * ���»��˵㷵�ص�ǰ����ջ�վ��Ϣ
 * @author ligangfan
 *
 */
@Table("delireceiveinfo")
public class MtqDeliReceiveData {
	
	@PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public long id;
	/**��ǰ���վ*/
	@Column("_pdeliver")
	public String pdeliver;
	/**��ǰ�ջ�վ*/
	@Column("_preceipt")
	public String preceipt;
	/**��������*/
	@Column("_freight_type")
	public int freight_type;
	

}
