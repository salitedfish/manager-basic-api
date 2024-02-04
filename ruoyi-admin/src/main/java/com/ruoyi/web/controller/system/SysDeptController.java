package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.NotBlankException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.system.domain.SysDeptRole;
import com.ruoyi.system.service.ISysDeptSubAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;

/**
 * 部门信息
 * 
 * @author ruoyi
 */
@Api("部门管理")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysDeptSubAdminService deptSubAdminService;

    /**
     * 获取部门列表
     */
    @ApiOperation("获取部门列表")
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept)
    {
        List<SysDept> depts = new ArrayList<>();
        if(StringUtils.isNotNull(dept.getSubAdmin())) {
            depts = deptSubAdminService.selectDeptSubAdminList(new SysDept());
        } else {
            depts = deptService.selectDeptList(dept);
        }
        return success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @ApiOperation("查询部门列表（排除节点）")
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) String deptId, Boolean subAdmin)
    {
        List<SysDept> depts = new ArrayList<>();
        if(StringUtils.isNotNull(subAdmin)) {
            depts = deptSubAdminService.selectDeptSubAdminList(new SysDept());
        } else {
            depts = deptService.selectDeptList(new SysDept());
        }
        depts.removeIf(d -> d.getDeptId().equals(deptId) || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */

    @ApiOperation("根据部门编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable String deptId)
    {
        deptService.checkDeptDataScope(deptId);
        return success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @ApiOperation("新增部门")
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept)
    {
        if (!deptService.checkDeptNameUnique(dept))
        {
            throw new ServiceException(MessageUtils.message("dept.deptName.exists", new Object[] { dept.getDeptName() }));
        }
        if (!deptService.checkDeptCodeUnique(dept))
        {
            throw new ServiceException(MessageUtils.message("dept.deptCode.exists", new Object[] { dept.getDeptCode() }));
        }
        dept.setCreateBy(getUsername());
        if(StringUtils.isNotNull(dept.getSubAdmin())) {
            return toAjax(deptSubAdminService.insertDeptSubAdmin(dept));
        } else {
            return toAjax(deptService.insertDept(dept));
        }
    }

    /**
     * 修改部门
     */
    @ApiOperation("修改部门")
//    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @PreAuthorize("@ss.hasAnyPermi('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept)
    {
        String deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept))
        {
            throw new ServiceException(MessageUtils.message("dept.deptName.exists", new Object[] { dept.getDeptName() }));
        }
        if (!deptService.checkDeptCodeUnique(dept))
        {
            throw new ServiceException(MessageUtils.message("dept.deptCode.exists", new Object[] { dept.getDeptCode() }));
        }
        else if (dept.getParentId().equals(deptId))
        {
            throw new ServiceException(MessageUtils.message("dept.parent.cannot.self"));
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0)
        {
            throw new ServiceException(MessageUtils.message("dept.has.children.used"));
        }
        dept.setUpdateBy(getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @ApiOperation("删除")
//    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @PreAuthorize("@ss.hasAnyPermi('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable String deptId)
    {
        if (deptService.hasChildByDeptId(deptId))
        {
            return warn(MessageUtils.message("dept.delete.has.children"));
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return warn(MessageUtils.message("dept.delete.has.user"));
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 获取包含人员的部门树
     */
    @ApiOperation("查询包含人员的部门树")
    @GetMapping("/treeWithUser")
    public AjaxResult selectDeptTreeListWithUser(SysDept dept)
    {
        List<TreeSelect> list = new ArrayList<>();
        if(StringUtils.isNotNull(dept.getSubAdmin())) {
            list = deptSubAdminService.selectDeptTreeListWithUser(dept);
        } else {
            list = deptService.selectDeptTreeListWithUser(dept);
        }

        return success(list);
    }

    @ApiOperation("查询部门关联的角色列表")
    @GetMapping("/role/list")
    public TableDataInfo selectDeptRoleList(SysDeptRole sdr) {
        startPage();
        List<SysRole> list = new ArrayList<>();
        if(StringUtils.isNotNull(sdr.getSubAdmin())) {
            list = deptSubAdminService.selectRoleListByDept(sdr);
        } else {
            list = deptService.selectRoleListByDept(sdr);
        }
        return getDataTable(list);
    }

    @ApiOperation("新增部门关联的角色列表")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping("/role/add")
    public AjaxResult insertDeptRoleList(@RequestBody SysDeptRole sdr) {
        if(StringUtils.isBlank(sdr.getDeptId())) {
            throw new NotBlankException("deptId");
        }
        if(StringUtils.isEmpty(sdr.getRoleIds())) {
            throw new NotBlankException("roleIds");
        }
        return success(deptService.insertDeptRole(sdr));
    }

    @ApiOperation("删除部门关联的角色")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/role/delete")
    public AjaxResult deleteDeptRoleList(@RequestBody SysDeptRole sdr) {
        if(StringUtils.isBlank(sdr.getDeptId())) {
            throw new NotBlankException("deptId");
        }
        if(StringUtils.isEmpty(sdr.getRoleIds())) {
            throw new NotBlankException("roleIds");
        }
        return success(deptService.deleteDeptRoleList(sdr));
    }
}
