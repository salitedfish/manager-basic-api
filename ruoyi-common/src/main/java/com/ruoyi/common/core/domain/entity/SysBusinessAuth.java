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
     * 是否只查传递的组织，不查关联组织
     */
    private Boolean currentOrg;

    @Override
    public String toString() {
        return "SysBusinessAuth{" +
                "id=" + id +
                ", businessId='" + businessId + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", businessName='" + businessName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgType='" + orgType + '\'' +
                ", orgName='" + orgName + '\'' +
                ", manageOrgId='" + manageOrgId + '\'' +
                ", manageOrgType='" + manageOrgType + '\'' +
                ", manageOrgName='" + manageOrgName + '\'' +
                ", deptFullPathId='" + deptFullPathId + '\'' +
                ", currentOrg=" + currentOrg +
                '}';
    }

    public Boolean getCurrentOrg() {
        return currentOrg;
    }

    public void setCurrentOrg(Boolean currentOrg) {
        this.currentOrg = currentOrg;
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
