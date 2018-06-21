/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.employee.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.apache.tika.parser.microsoft.ExcelExtractor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelField.Align;
import com.jeesite.common.utils.excel.annotation.ExcelField.Type;
import com.jeesite.modules.sys.entity.Company;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Office;



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

	@ExcelField(title="员工编号",align=Align.CENTER,sort=1)
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	@NotBlank(message="员工姓名不能为空")
	@Length(min=0, max=100, message="员工姓名长度不能超过 100 个字符")
	@ExcelField(title="员工姓名",align=Align.CENTER,sort=10)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Length(min=0, max=100, message="英文名长度不能超过 100 个字符")
	@ExcelField(title="英文名",align=Align.CENTER,sort=20)
	public String getEmpNameEn() {
		return empNameEn;
	}

	public void setEmpNameEn(String empNameEn) {
		this.empNameEn = empNameEn;
	}
	@NotNull(
			message = "归属机构不能为空"
	)
	
	@ExcelField(title="部门名称",align=Align.CENTER ,sort=80)
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
	@ExcelField(title="公司名称",align=Align.CENTER ,sort=90)
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
	@ExcelField(title="flag",align=Align.CENTER ,dictType="js_sys_empFlag",sort=100)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@ExcelField(title="状态",align=Align.CENTER ,sort=110,dictType="sys_status")
	public String getStatus() {
		return super.getStatus();
	}
	
	@ExcelField(title="创建者",align=Align.CENTER ,sort=130)
	public String getCreateBy() {
		return super.getCreateBy();
	}
	
	
	@ExcelField(title="创建时间",align=Align.CENTER ,sort=150,dataFormat="yyyy-MM-dd hh:mm:ss")
	public Date getCreateDate() {
		return super.getCreateDate();
	}
	
	@ExcelField(title="修改者",align=Align.CENTER ,sort=200)
	public String getUpdateBy() {
		return super.getUpdateBy();
	}
	
	
	@ExcelField(title="修改时间",align=Align.CENTER ,sort=230,dataFormat="yyyy-MM-dd hh:mm:ss")
	public Date getUpdateDate() {
		return super.getUpdateDate();
	}
	
	@ExcelField(title="备注信息",align=Align.CENTER ,sort=250)
	public String getRemarks() {
		return super.getRemarks();
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