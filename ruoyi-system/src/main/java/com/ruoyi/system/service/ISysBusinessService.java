package com.ruoyi.system.service;

import java.util.List;
import java.util.Set;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.system.domain.vo.RouterVo;

/**
 * 业务 业务层
 *
 * @author ruoyi
 */
public interface ISysBusinessService
{
    /**
     * 根据用户查询系统业务列表
     *
     * @param business 业务信息
     * @return 菜单列表
     */
    public List<SysBusiness> selectBusinessList(SysBusiness business);
}
