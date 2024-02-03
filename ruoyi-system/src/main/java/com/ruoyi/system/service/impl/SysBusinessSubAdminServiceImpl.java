package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysBusinessMapper;
import com.ruoyi.system.mapper.SysBusinessSubAdminMapper;
import com.ruoyi.system.service.ISysBusinessSubAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysBusinessSubAdminServiceImpl implements ISysBusinessSubAdminService {
    @Autowired
    private SysBusinessSubAdminMapper businessSubAdminMapper;

    /**
     * 查询系统业务列表
     *
     * @param business 业务信息
     * @return 业务列表
     */
    @Override
    public List<SysBusiness> selectBusinessSubAdminList(SysBusiness business) {

        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return businessSubAdminMapper.selectBusinessSubAdminList(business, userId);
    };
}
