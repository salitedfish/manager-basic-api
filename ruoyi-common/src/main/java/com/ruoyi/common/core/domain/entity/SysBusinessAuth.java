package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

public class SysBusinessAuth extends BaseEntity {
    private Integer id;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 业务编号
     */
    private String businessCode;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织类型
     * 0：部门，1: 人员, 2: 岗位
     */
    private String orgType;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 管理的组织id
     */
    private String manageOrgId;

    /**
     * 组织编码
     */
    private String manageOrgCode;

    /**
     * 管理的组织类型
     */
    private String manageOrgType;

    /**
     * 管理的组织名称
     */
    private String manageOrgName;

    /**
     * 组织的部门全路径
     */
    private String deptFullPathId;


    /**
     * 是否查询继承的组织权限
     */
    private Boolean extendsOrg;

    /** 子管理员 */
    private Boolean subAdmin;

    @Override
    public String toString() {
        return "SysBusinessAuth{" +
                "id=" + id +
                ", businessId='" + businessId + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", businessName='" + businessName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", orgType='" + orgType + '\'' +
                ", orgName='" + orgName + '\'' +
                ", manageOrgId='" + manageOrgId + '\'' +
                ", manageOrgCode='" + manageOrgCode + '\'' +
                ", manageOrgType='" + manageOrgType + '\'' +
                ", manageOrgName='" + manageOrgName + '\'' +
                ", deptFullPathId='" + deptFullPathId + '\'' +
                ", extendsOrg=" + extendsOrg +
                '}';
    }

    public Boolean getSubAdmin() {
        return subAdmin;
    }

    public void setSubAdmin(Boolean subAdmin) {
        this.subAdmin = subAdmin;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getManageOrgCode() {
        return manageOrgCode;
    }

    public void setManageOrgCode(String manageOrgCode) {
        this.manageOrgCode = manageOrgCode;
    }

    public Boolean getExtendsOrg() {
        return extendsOrg;
    }

    public void setExtendsOrg(Boolean extendsOrg) {
        this.extendsOrg = extendsOrg;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getManageOrgName() {
        return manageOrgName;
    }

    public void setManageOrgName(String manageOrgName) {
        this.manageOrgName = manageOrgName;
    }

    public String getDeptFullPathId() {
        return deptFullPathId;
    }

    public void setDeptFullPathId(String deptFullPathId) {
        this.deptFullPathId = deptFullPathId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getManageOrgId() {
        return manageOrgId;
    }

    public void setManageOrgId(String manageOrgId) {
        this.manageOrgId = manageOrgId;
    }

    public String getManageOrgType() {
        return manageOrgType;
    }

    public void setManageOrgType(String manageOrgType) {
        this.manageOrgType = manageOrgType;
    }
}
