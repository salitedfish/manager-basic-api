package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.service.ISysBusinessService;
import com.ruoyi.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 业务相关
 */
@Api("业务相关")
@RestController
@RequestMapping("/system/business")
public class SysBusinessController extends BaseController {

    @Autowired
    private ISysBusinessService businessService;

    /**
     * 获取业务列表
     */
    @ApiOperation("获取业务权限列表")
    @PreAuthorize("@ss.hasPermi('system:business:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBusiness business)
    {
        startPage();
        List<SysBusiness> businesses = businessService.selectBusinessList(business, getUserId());
        return getDataTable(businesses);
    }
}