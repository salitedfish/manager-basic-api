package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Api("分级管理-用户管理")
@RestController
@RequestMapping("/system/user/subAdmin")
public class SysUserSubAdminController extends BaseController {

    @Autowired
    private ISysUserSubAdminService userSubAdminService;

    @Autowired
    private ISysDeptSubAdminService deptSubAdminService;

    @Autowired
    private ISysRoleSubAdminService roleSubAdminService;

    @Autowired
    private ISysPostSubAdminService postSubAdminService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPostService postService;

    /**
     * 获取用户列表
     */
//    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:list')")
    @ApiOperation("分级管理-获取用户列表")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = userSubAdminService.selectUserList(user);
        return getDataTable(list);
    }

//    /**
//     * 导出用户列表
//     * @param response
//     * @param user
//     */
//    @Log(title = "分级管理-用户管理", businessType = BusinessType.EXPORT)
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:export')")
//    @ApiOperation("分级管理-导出用户列表")
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SysUser user)
//    {
//        List<SysUser> list = userSubAdminService.selectUserList(user);
//        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//        util.exportExcel(response, list, "用户数据");
//    }

    /**
     * 根据用户编号获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:query')")
    @ApiOperation("分级管理-根据用户编号获取详细信息")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) String userId)
    {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> srs = roleSubAdminService.selectRoleAll();
        ajax.put("roles", srs);
        List<SysPost> sps = postSubAdminService.selectPostAll();
        ajax.put("posts", sps);
        if (StringUtils.isNotNull(userId))
        {
            SysUser sysUser = userService.selectUserById(userId);
            List<String> postIds = postService.selectPostListByUserId(userId).stream().filter(id -> {
                Boolean exits = false;
                for(SysPost sp: sps) {
                    if(sp.getPostId().equals(id)) {
                        exits = true;
                        break;
                    }
                }
                 return exits;
            }).collect(Collectors.toList());
            List<Long> roleIds = sysUser.getRoles().stream().map(SysRole::getRoleId).filter(id -> {
                Boolean exits = false;
                for(SysRole sr: srs) {
                    if(sr.getRoleId().equals(id)) {
                        exits = true;
                        break;
                    }
                }
                return exits;
            }).collect(Collectors.toList());

            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postIds);
            ajax.put("roleIds", roleIds);
        }
        return ajax;
    }

//    /**
//     * 新增用户
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:add')")
//    @Log(title = "分级管理-用户管理", businessType = BusinessType.INSERT)
//    @ApiOperation("分级管理-新增用户")
//    @PostMapping
//    public AjaxResult add(@Validated @RequestBody SysUser user)
//    {
//        if (!userService.checkUserNameUnique(user))
//        {
//            return error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
//        }
//        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
//        {
//            return error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
//        }
//        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
//        {
//            return error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
//        }
//        user.setCreateBy(getUsername());
//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
//        return success(userService.insertUser(user));
//    }
//
//    /**
//     * 修改用户
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:edit')")
//    @Log(title = "分级管理-用户管理", businessType = BusinessType.UPDATE)
//    @ApiOperation("分级管理-修改用户")
//    @PutMapping
//    public AjaxResult edit(@Validated @RequestBody SysUser user)
//    {
//        userService.checkUserAllowed(user);
//        if (!userService.checkUserNameUnique(user))
//        {
//            return error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
//        }
//        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
//        {
//            return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
//        }
//        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
//        {
//            return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
//        }
//        user.setUpdateBy(getUsername());
//        return success(userSubAdminService.updateUser(user));
//    }

//    /**
//     * 删除用户
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:remove')")
//    @Log(title = "分级管理-用户管理", businessType = BusinessType.DELETE)
//    @ApiOperation("分级管理-删除用户")
//    @DeleteMapping("/{userIds}")
//    public AjaxResult remove(@PathVariable String[] userIds)
//    {
//        if (ArrayUtils.contains(userIds, getUserId()))
//        {
//            return error("当前用户不能删除");
//        }
//        return toAjax(userService.deleteUserByIds(userIds));
//    }
//
//    /**
//     * 重置密码
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:resetPwd')")
//    @Log(title = "分级管理-用户管理", businessType = BusinessType.UPDATE)
//    @ApiOperation("分级管理-重置密码")
//    @PutMapping("/resetPwd")
//    public AjaxResult resetPwd(@RequestBody SysUser user)
//    {
//        userService.checkUserAllowed(user);
//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
//        user.setUpdateBy(getUsername());
//        return toAjax(userService.resetPwd(user));
//    }

//    /**
//     * 状态修改
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:edit')")
//    @Log(title = "分级管理-用户管理", businessType = BusinessType.UPDATE)
//    @ApiOperation("分级管理-状态修改")
//    @PutMapping("/changeStatus")
//    public AjaxResult changeStatus(@RequestBody SysUser user)
//    {
//        userService.checkUserAllowed(user);
//        user.setUpdateBy(getUsername());
//        return success(userService.updateUserStatus(user));
//    }
//
//
//    /**
//     * 获取部门树列表
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:list')")
//    @ApiOperation("分级管理-获取部门树列表")
//    @GetMapping("/deptTree")
//    public AjaxResult deptTree(SysDept dept)
//    {
//        return success(deptSubAdminService.selectDeptTreeList(dept));
//    }

//    /**
//     * 根据用户编号获取授权角色
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:query')")
//    @ApiOperation("分级管理-根据用户编号获取授权角色")
//    @GetMapping("/authRole/{userId}")
//    public AjaxResult authRole(@PathVariable("userId") String userId)
//    {
//        AjaxResult ajax = AjaxResult.success();
//        SysUser user = userService.selectUserById(userId);
//        List<SysRole> roles = roleSubAdminService.selectRolesByUserId(userId);
//        ajax.put("user", user);
//        ajax.put("roles", roles);
//        return ajax;
//    }
//
//    /**
//     * 用户授权角色
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:subAdmin:edit')")
//    @Log(title = "分级管理-用户管理", businessType = BusinessType.GRANT)
//    @ApiOperation("分级管理-用户授权角色")
//    @PutMapping("/authRole")
//    public AjaxResult insertAuthRole(String userId, Long[] roleIds)
//    {
//        userSubAdminService.insertUserAuth(userId, roleIds);
//        return success();
//    }
}
