package org.jeecgframework.web.system.pojo.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 部门机构表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_depart")
public class TSDepart extends IdEntity implements java.io.Serializable {
	private TSDepart TSPDepart;//上级部门
	@Excel(name = "部门名称")
	private String departname;//部门名称
	@Excel(name = "部门描述")
	private String description;//部门描述
	@Excel(name = "机构编码")
    private String orgCode;//机构编码
	@Excel(name = "机构类型编码")
    private String orgType;//机构编码
	@Excel(name = "电话")
	private String mobile;//电话
	@Excel(name = "传真")
	private String fax;//传真
	@Excel(name = "地址")
	private String address;//地址
	@Excel(name = "开通日期 ")
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date startDate;//开通日期 
	@Excel(name = "结束日期 ")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endDate;//结束日期 
	@Excel(name = "状态")
	private Integer status;//状态
	@Excel(name = "账户余额")
	private BigDecimal balance;//账户余额
	private List<TSDepart> TSDeparts = new ArrayList<TSDepart>();//下属部门
	
	/**
	 * startDate
	 * @return  the startDate
	*/
	@Column(name ="START_DATE",nullable=true)
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * endDate
	 * @return  the endDate
	*/
	@Column(name ="END_DATE",nullable=true)
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * status
	 * @return  the status
	*/
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * balance
	 * @return  the balance
	*/
	@Column(name ="BALANCE",nullable=true,precision=19,scale=2)
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal Balance) {
		this.balance = balance;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentdepartid")
	public TSDepart getTSPDepart() {
		return this.TSPDepart;
	}

	public void setTSPDepart(TSDepart TSPDepart) {
		this.TSPDepart = TSPDepart;
	}

	@Column(name = "departname", nullable = false, length = 100)
	public String getDepartname() {
		return this.departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPDepart")
	public List<TSDepart> getTSDeparts() {
		return TSDeparts;
	}

	public void setTSDeparts(List<TSDepart> tSDeparts) {
		TSDeparts = tSDeparts;
	}

    @Column(name = "org_code", length = 64)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "org_type", length = 1)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

	@Column(name = "mobile", length = 32)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "fax", length = 32)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}