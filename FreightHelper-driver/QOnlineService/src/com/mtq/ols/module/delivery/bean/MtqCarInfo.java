package com.mtq.ols.module.delivery.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * ������Ϣʵ����
 * @author ligangfan
 *
 */
@Table("carinfo")
public class MtqCarInfo {

	@PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public long id;
	/** ���߼��� */
	@Column("_tht")
	public String tht;
	/** ������ */
	@Column("_twh")
	public String twh;
	/** ���ؼ���*/
	@Column("_twt")
	public String twt;
	/** ���ͼ��� */ 
	@Column("_tvt")
	public String tvt;
	/** ��������*/
	@Column("_tlnt")
	public String tlnt;
	
}
