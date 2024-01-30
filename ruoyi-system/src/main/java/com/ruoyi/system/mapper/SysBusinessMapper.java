package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysBusiness;

import java.util.List;

public interface SysBusinessMapper {

    /**
     * 查询系统业务列表
     *
     * @param business 业务信息
     * @return 业务列表
     */
    public List<SysBusiness> selectBusinessList(SysBusiness business);

    /**
     * 根据用户查询系统业务列表
     *
     * @param business 业务信息
     * @return 业务列表
     */
    public List<SysBusiness> selectBusinessListByUserId(SysBusiness business);
}
