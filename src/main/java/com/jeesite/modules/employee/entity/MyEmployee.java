/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.employee.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.sys.entity.Company;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Office;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 员工表Entity
 * @author kyrie
 * @version 2018-05-18
 */
@Table(name="${_prefix}sys_employee", alias="a", columns={
		@Column(
				name="emp_code", attrName="empCode", label="员工编码", isPK=true
		),
		@Column(
				name="emp_name", attrName="empName", label="员工姓名", queryType=QueryType.LIKE
		),
		@Column(
				name="emp_name_en", attrName="empNameEn", label="英文名", queryType=QueryType.LIKE
		),
		@Column(
				name = "office_code",
				attrName = "office.officeCode",
				label = "机构编码",
				isQuery = false
		), @Column(
		name = "office_name",
		attrName = "office.officeName",
		label = "机构名称",
		isQuery = false
), @Column(
		name = "company_code",
		attrName = "company.companyCode",
		label = "公司编码",
		isQuery = false
),@Column(
		name = "company_name",
		attrName = "company.companyName",
		label = "公司名称",
		isQuery = false
),@Column(
		name="flag", attrName="flag", label="flag"
),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
}, joinTable = {@JoinTable(
		type = JoinTable.Type.LEFT_JOIN,
		entity = Office.class,
		alias = "o",
		on = "o.office_code = a.office_code",
		columns = {@Column(
				includeEntity = Office.class
		)}
), @JoinTable(
		type = JoinTable.Type.LEFT_JOIN,
		entity = Company.class,
		alias = "c",
		on = "c.company_code = a.company_code",
		columns = {@Column(
				includeEntity = Company.class
		)}
)}, orderBy="a.update_date DESC"
)
@Alias("MyEmployee")
public class MyEmployee extends DataEntity<MyEmployee> {

	private static final long serialVersionUID = 1L;
	private String empCode;		// 员工编码
	private String empName;		// 员工姓名
	private String empNameEn;		// 英文名
	private Office office;			//部门信息表
	private Company company;		//公司表
	private String flag;		// flag
	private Employee employee;
	private String RoleCode;
	public MyEmployee() {
		this(null);
	}

	public MyEmployee(String id){
		super(id);
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getRoleCode() {
		return RoleCode;
	}

	public void setRoleCode(String roleCode) {
		RoleCode = roleCode;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	@NotBlank(message="员工姓名不能为空")
	@Length(min=0, max=100, message="员工姓名长度不能超过 100 个字符")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Length(min=0, max=100, message="英文名长度不能超过 100 个字符")
	public String getEmpNameEn() {
		return empNameEn;
	}

	public void setEmpNameEn(String empNameEn) {
		this.empNameEn = empNameEn;
	}
	@NotNull(
			message = "归属机构不能为空"
	)
	public Office getOffice() {
		if (this.office == null) {
			this.office = new Office();
		}

		return this.office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@NotNull(
			message = "归属公司不能为空"
	)
	public Company getCompany() {
		if (this.company == null) {
			this.company = new Company();
		}

		return this.company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	@NotBlank(message="flag不能为空")
	@Length(min=0, max=1, message="flag长度不能超过 1 个字符")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "MyEmployee{" +
				"empCode='" + empCode + '\'' +
				", empName='" + empName + '\'' +
				", empNameEn='" + empNameEn + '\'' +
				", office=" + office +
				", company=" + company +
				", flag='" + flag + '\'' +
				", createBy='" + createBy + '\'' +
				", updateByName='" + updateByName + '\'' +
				", updateBy='" + updateBy + '\'' +
				", updateDate=" + updateDate +
				", remarks='" + remarks + '\'' +
				", lastUpdateDateTime=" + lastUpdateDateTime +
				", createDate=" + createDate +
				", status='" + status + '\'' +
				", createByName='" + createByName + '\'' +
				'}';
	}
}