package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysBusinessAuthSubAdminMapper;
import com.ruoyi.system.mapper.SysBusinessSubAdminMapper;
import com.ruoyi.system.service.ISysBusinessAuthSubAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysBusinessAuthSubAdminServiceImpl implements ISysBusinessAuthSubAdminService {

    @Autowired
    private SysBusinessAuthSubAdminMapper businessAuthSubAdminMapper;


    /**
     * 根据业务权限信息查询业务权限列表
     */
    public List<SysBusinessAuth> selectBusinessAuthList(SysBusinessAuth sbam) {
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return businessAuthSubAdminMapper.selectBusinessAuthList(sbam, userId);
    };
}
