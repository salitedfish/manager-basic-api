package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.system.mapper.SysBusinessMapper;
import com.ruoyi.system.service.ISysBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  业务 业务层处理
 */
@Service
public class SysBusinessServiceImpl implements ISysBusinessService {
    @Autowired
    private SysBusinessMapper businessMapper;

    /**
     * 根据用户查询系统业务列表
     *
     * @param business 业务信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysBusiness> selectBusinessList(SysBusiness business, String userId) {
        List<SysBusiness> menuList = null;
        // 管理员显示所有业务信息
//        if (SysUser.isAdmin(userId))
//        {
            menuList = businessMapper.selectBusinessList(business);
//        }
//        else
//        {
//            business.getParams().put("userId", userId);
//            menuList = businessMapper.selectBusinessListByUserId(business);
//        }
        return menuList;
    };
}