package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.NotBlankException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.service.ISysBusinessAuthService;
import com.ruoyi.system.service.ISysBusinessAuthSubAdminService;
import com.ruoyi.system.service.ISysBusinessSubAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务权限配置
 */
@Api("业务权限配置")
@RestController
@RequestMapping("/system/businessAuth")
public class SysBusinessAuthController extends BaseController {
    @Autowired
    private ISysBusinessAuthService businessAuthService;

    @Autowired
    private ISysBusinessAuthSubAdminService businessAuthSubAdminService;



    /**
     * 获取业务权限列表
     */
    @ApiOperation("查询业务权限列表")
    @PreAuthorize("@ss.hasAnyPermi('system:permissionQuery:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBusinessAuth sysBusinessAuth)
    {
        startPage();
        List<SysBusinessAuth> list = new ArrayList<>();
        if(StringUtils.isNotNull(sysBusinessAuth.getSubAdmin())) {
            list = businessAuthSubAdminService.selectBusinessAuthList(sysBusinessAuth);
        } else {
            list = businessAuthService.selectBusinessAuthList(sysBusinessAuth);
        }

        return getDataTable(list);
    }

    /**
     * 新增业务权限列表
     */
    @ApiOperation("新增业务权限列表")
    @Log(title = "业务权限", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult insertBusinessAuth(@RequestBody List<SysBusinessAuth> sysBusinessAuthList) {
        if(StringUtils.isEmpty(sysBusinessAuthList)) {
            throw new NotBlankException("list");
        }
        for(SysBusinessAuth sba: sysBusinessAuthList) {
            if(StringUtils.isBlank(sba.getBusinessId())) {
                throw new NotBlankException("businessId");
            }
            if(StringUtils.isBlank(sba.getOrgId())) {
                throw new NotBlankException("orgId");
            }
            if(StringUtils.isBlank(sba.getOrgType())) {
                throw new NotBlankException("orgType");
            }
            if(StringUtils.isBlank(sba.getManageOrgId())) {
                throw new NotBlankException("manageOrgId");
            }
            if(StringUtils.isBlank(sba.getManageOrgType())) {
                throw new NotBlankException("manageOrgType");
            }
        }
        return success(businessAuthService.insertBusinessAuth(sysBusinessAuthList));
    }

    /**
     * 取消业务权限授权
     */
    @ApiOperation("删除业务授权列表")
    @Log(title = "业务权限", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult deleteBusinessAuth(@RequestBody List<Long> businessAuthIds) {
        if(StringUtils.isEmpty(businessAuthIds)) {
            throw new NotBlankException("list");
        }
        return success(businessAuthService.deleteBusinessAuth(businessAuthIds));
    }
}
