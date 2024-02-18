package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.*;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.NotBlankException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.ISysSubAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 分级管理
 */
@Api("分级管理")
@RestController
@RequestMapping("/system/subAdmin")
public class SysSubAdminController extends BaseController {

    @Autowired
    private ISysSubAdminService subAdminService;

    /**
     * 子管理员部门相关的增删查
     */
    @ApiOperation("分级管理-查询分配的部门列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:list')")
    @GetMapping("/dept/list")
    public TableDataInfo deptList(SysSubAdminDept subAdminDept) {
        startPage();
        List<SysDept> list = subAdminService.selectSubAdminDept(subAdminDept);
        return getDataTable(list);
    };

    @ApiOperation("分级管理-添加分配的部门列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:add')")
    @Log(title = "分级管理", businessType = BusinessType.INSERT)
    @PostMapping("/dept/add")
    public AjaxResult deptAdd(@RequestBody SysSubAdminDept subAdminDept) {
        if(StringUtils.isBlank(subAdminDept.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminDept.getDeptIds())) {
            throw new NotBlankException("deptIds");
        }
        int i = subAdminService.insertSubAdminDept(subAdminDept);
        return success(i);
    };

    @ApiOperation("分级管理-删除分配的部门列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:remove')")
    @Log(title = "分级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/dept/delete")
    public AjaxResult deptDelete(@RequestBody SysSubAdminDept subAdminDept) {
        if(StringUtils.isBlank(subAdminDept.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminDept.getDeptIds())) {
            throw new NotBlankException("deptIds");
        }
        int i = subAdminService.deleteSubAdminDept(subAdminDept);
        return success(i);
    };

    /**
     * 子管理员岗位相关的增删查
     */
    @ApiOperation("分级管理-查询分配的岗位列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:list')")
    @GetMapping("/post/list")
    public TableDataInfo postList(SysSubAdminPost subAdminPost) {
        startPage();
        List<SysPost> list = subAdminService.selectSubAdminPost(subAdminPost);
        return getDataTable(list);
    };

    @ApiOperation("分级管理-添加分配的岗位列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:add')")
    @Log(title = "分级管理", businessType = BusinessType.INSERT)
    @PostMapping("/post/add")
    public AjaxResult postAdd(@RequestBody SysSubAdminPost subAdminPost) {
        if(StringUtils.isBlank(subAdminPost.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminPost.getPostIds())) {
            throw new NotBlankException("postIds");
        }
        int i = subAdminService.insertSubAdminPost(subAdminPost);
        return success(i);
    };

    @ApiOperation("分级管理-删除分配的岗位列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:remove')")
    @Log(title = "分级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/post/delete")
    public AjaxResult postDelete(@RequestBody SysSubAdminPost subAdminPost) {
        if(StringUtils.isBlank(subAdminPost.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminPost.getPostIds())) {
            throw new NotBlankException("postIds");
        }
        int i = subAdminService.deleteSubAdminPost(subAdminPost);
        return success(i);
    };

    /**
     * 子管理员角色相关的增删查
     */
    @ApiOperation("分级管理-查询分配的角色列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:list')")
    @GetMapping("/role/list")
    public TableDataInfo roleList(SysSubAdminRole subAdminRole) {
        startPage();
        List<SysRole> list = subAdminService.selectSubAdminRole(subAdminRole);
        return getDataTable(list);
    };

    @ApiOperation("分级管理-添加分配的角色列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:add')")
    @Log(title = "分级管理", businessType = BusinessType.INSERT)
    @PostMapping("/role/add")
    public AjaxResult roleAdd(@RequestBody SysSubAdminRole subAdminRole) {
        if(StringUtils.isBlank(subAdminRole.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminRole.getRoleIds())) {
            throw new NotBlankException("roleIds");
        }
        int i = subAdminService.insertSubAdminRole(subAdminRole);
        return success(i);
    };

    @ApiOperation("分级管理-删除分配的角色列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:remove')")
    @Log(title = "分级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/role/delete")
    public AjaxResult postDelete(@RequestBody SysSubAdminRole subAdminRole) {
        if(StringUtils.isBlank(subAdminRole.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminRole.getRoleIds())) {
            throw new NotBlankException("roleIds");
        }
        int i = subAdminService.deleteSubAdminRole(subAdminRole);
        return success(i);
    };

    /**
     * 子管理员业务相关的增删查
     */
    @ApiOperation("分级管理-查询分配的业务列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:list')")
    @GetMapping("/business/list")
    public TableDataInfo businessList(SysSubAdminBusiness subAdminBusiness) {
        startPage();
        List<SysBusiness> list = subAdminService.selectSubAdminBusiness(subAdminBusiness);
        return getDataTable(list);
    };

    @ApiOperation("分级管理-添加分配的业务列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:add')")
    @Log(title = "分级管理", businessType = BusinessType.INSERT)
    @PostMapping("/business/add")
    public AjaxResult businessAdd(@RequestBody SysSubAdminBusiness subAdminBusiness) {
        if(StringUtils.isBlank(subAdminBusiness.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminBusiness.getBusinessIds())) {
            throw new NotBlankException("businessIds");
        }
        int i = subAdminService.insertSubAdminBusiness(subAdminBusiness);
        return success(i);
    };

    @ApiOperation("分级管理-删除分配的业务列表")
    @PreAuthorize("@ss.hasAnyPermi('system:subAdmin:remove')")
    @Log(title = "分级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/business/delete")
    public AjaxResult businessDelete(@RequestBody SysSubAdminBusiness subAdminBusiness) {
        if(StringUtils.isBlank(subAdminBusiness.getUserId())) {
            throw new NotBlankException("userId");
        }
        if(StringUtils.isEmpty(subAdminBusiness.getBusinessIds())) {
            throw new NotBlankException("businessIds");
        }
        int i = subAdminService.deleteSubAdminBusiness(subAdminBusiness);
        return success(i);
    };
}
