package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDeptRole;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysDeptSubAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 部门信息
 *
 * @author ruoyi
 */
@Api("分级管理-部门管理")
@RestController
@RequestMapping("/system/dept/subAdmin")
public class SysDeptSubAdminController extends BaseController {

    @Autowired
    private ISysDeptSubAdminService deptSubAdminService;

    @Autowired
    private ISysDeptService deptService;

//    /**
//     * 获取部门列表
//     */
//    @ApiOperation("分级管理-获取部门列表")
////    @PreAuthorize("@ss.hasPermi('system:dept:subAdmin:list')")
//    @GetMapping("/list")
//    public AjaxResult list(SysDept dept)
//    {
//        List<SysDept> depts = deptSubAdminService.selectDeptSubAdminList(dept);
//        return success(depts);
//    }
//
//    /**
//     * 查询部门列表（排除节点）
//     */
//    @ApiOperation("分级管理-查询部门列表（排除节点）")
////    @PreAuthorize("@ss.hasPermi('system:dept:subAdmin:list')")
//    @GetMapping("/list/exclude/{deptId}")
//    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) String deptId)
//    {
//        List<SysDept> depts = deptSubAdminService.selectDeptSubAdminList(new SysDept());
//        depts.removeIf(d -> d.getDeptId().equals(deptId) || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
//        return success(depts);
//    }
//
//    /**
//     * 新增保存部门信息
//     *
//     * @param dept 部门信息
//     * @return 结果
//     */
//    @ApiOperation("分级管理-新增保存部门信息")
////    @PreAuthorize("@ss.hasPermi('system:dept:subAdmin:add')")
//    @Log(title = "分级管理-部门管理", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@Validated @RequestBody SysDept dept)
//    {
//        if (!deptService.checkDeptNameUnique(dept))
//        {
//            return error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
//        }
//        if (!deptService.checkDeptCodeUnique(dept))
//        {
//            return error("新增部门'" + dept.getDeptCode() + "'失败，部门编码已存在");
//        }
//        dept.setCreateBy(getUsername());
//        return success(deptSubAdminService.insertDeptSubAdmin(dept));
//    }

//    /**
//     * 修改部门
//     */
//    @ApiOperation("分级管理-修改部门")
////    @PreAuthorize("@ss.hasPermi('system:dept:subAdmin:edit')")
//    @Log(title = "分级管理-部门管理", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@Validated @RequestBody SysDept dept)
//    {
//        String deptId = dept.getDeptId();
//        if (!deptService.checkDeptNameUnique(dept))
//        {
//            return error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
//        }
//        if (!deptService.checkDeptCodeUnique(dept))
//        {
//            return error("修改部门'" + dept.getDeptCode() + "'失败，部门编码已存在");
//        }
//        else if (dept.getParentId().equals(deptId))
//        {
//            return error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
//        }
//        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0)
//        {
//            return error("该部门包含未停用的子部门！");
//        }
//        dept.setUpdateBy(getUsername());
//        return toAjax(deptService.updateDept(dept));
//    }

//    /**
//     * 删除部门
//     */
//    @ApiOperation("分级管理-删除部门")
////    @PreAuthorize("@ss.hasPermi('system:dept:subAdmin:remove')")
//    @Log(title = "分级管理-部门管理", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{deptId}")
//    public AjaxResult remove(@PathVariable String deptId)
//    {
//        if (deptService.hasChildByDeptId(deptId))
//        {
//            return warn("存在下级部门,不允许删除");
//        }
//        if (deptService.checkDeptExistUser(deptId))
//        {
//            return warn("部门存在用户,不允许删除");
//        }
//        deptService.checkDeptDataScope(deptId);
//        return toAjax(deptService.deleteDeptById(deptId));
//    }

}
