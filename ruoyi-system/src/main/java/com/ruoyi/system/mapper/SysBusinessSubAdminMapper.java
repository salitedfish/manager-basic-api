package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysBusinessSubAdminMapper {

    /**
     * 查询系统业务列表
     *
     * @param business 业务信息
     * @return 业务列表
     */
    public List<SysBusiness> selectBusinessSubAdminList(@Param("business") SysBusiness business, @Param("userId") String userId);

}
