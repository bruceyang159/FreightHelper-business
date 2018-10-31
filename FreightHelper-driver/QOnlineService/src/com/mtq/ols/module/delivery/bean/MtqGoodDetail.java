package com.mtq.ols.module.delivery.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * ������Ϣ����ʵ����
 * @author ligangfan
 *
 */
@Table("good")
public class MtqGoodDetail {

	@PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public long id;
	/**��������**/
	@Column("_name")
	public String name;
	/**��������**/
	@Column("_amount")
	public int amount;
	/**ɨ������**/
	@Column("_scan_cnt")
	public int scan_cnt;
	
	/**����������**/
	@Column("_bar_code")
	public String bar_code;
	
	/**��λ**/
	@Column("_unit")
	public String unit;
	/**������ **/
	@Column("_pack")
	public String pack;
	/**����**/
	@Column("_weight")
	public String weight;
	/**���**/
	@Column("_volume")
	public String volume;
	
}
