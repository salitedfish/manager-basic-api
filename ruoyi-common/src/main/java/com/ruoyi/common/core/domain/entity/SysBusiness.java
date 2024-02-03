package com.ruoyi.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务权限表 sys_business
 *
 * @author ruoyi
 */
public class SysBusiness extends BaseEntity
{
    private String businessId;

    private String businessCode;

    private String businessName;

    /** 菜单状态（0正常 1停用） */
    private String status;

    private Boolean subAdmin;

    public Boolean getSubAdmin() {
        return subAdmin;
    }

    public void setSubAdmin(Boolean subAdmin) {
        this.subAdmin = subAdmin;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
