package com.mtq.ols.module.delivery.bean;

import java.util.ArrayList;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

/**
 * �˻�����Ϣʵ���ࣨ����Ϣ,�����˵�������Ϣ��
 * @author ligangfan
 *
 */
@Table("delitask")
public class MtqDeliTask {

	//@PrimaryKey(AssignType.AUTO_INCREMENT)
   // @Column("_id")
   // public long id;
	
	
	/** ��������ID */
	
	@PrimaryKey(AssignType.BY_MYSELF)
	@Column("_task_id")
	public String taskid;
	/** �ͻ����� */
	@Column("_task_date")
	public long taskdate;
	/** ����ʱ�� */
	@Column("_send_time")
	public long sendtime;
	/** �˻�״̬��0���˻�1�˻���2�����3��ͣ״̬4��ֹ״̬ �� */
	@Column("_status")
	public int status;
	/** ������ҵID */
	@Column("_corp_id")
	public String corpid;
	/** ��ҵ���� */
	@Column("_corp_name")
	public String corpname;
	/** �˻������� */
	@Column("_store_count")
	public int store_count;
	/** ��������� */
	@Column("_finish_count")
	public int finish_count;
	/** �Ѷ�δ��״̬0δ�� 1 �Ѷ� */
	@Column("_read_status")
	public int readstatus;
	/** ���һ�θ���ʱ�� */
	@Column("_dttime")
	public long dttime;
	/**����ʱ�䣬�����˵�id�������������������ʹ�ʱ�䣨���������˵��Ĺ��ڣ�*/
	@Mapping(Relation.OneToMany)
	public ArrayList<MtqRequestTime> req_times;
	/**�г̾���*/
	@Column("_distance")
	public int distance;
	/**��������*/
	@Column("_freight_type")
	public int freight_type;
	/**��ǰ���վ*/
	@Column("_pdeliver")
	public String pdeliver;
	/**��ǰ�ջ�վ*/
	@Column("_preceipt")
	public String preceipt;
	/**����������*/
	@Column("_gamount")
	public String gamount;
	/**����������*/
	@Column("_gweight")
	public String gweight;
	/**���������*/
	@Column("_gvolume")
	public String gvolume;
	
}
