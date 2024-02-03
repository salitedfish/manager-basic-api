package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

public class SysSubAdminBusiness extends BaseEntity {

    private String userId;

    private String businessId;

    private List<String> businessIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public List<String> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(List<String> businessIds) {
        this.businessIds = businessIds;
    }
}
