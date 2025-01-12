package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.system.service.*;
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
import org.springframework.web.multipart.MultipartFile;
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

/**
 * 用户信息
 * 
 * @author ruoyi
 */
@Api("用户管理")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysUserSubAdminService userSubAdminService;

    @Autowired
    private ISysRoleSubAdminService roleSubAdminService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysDeptSubAdminService deptSubAdminService;

    @Autowired
    private ISysPostService postService;

    /**
     * 获取用户列表
     */
    @ApiOperation("获取用户列表")
    @PreAuthorize("@ss.hasAnyPermi('system:user:list,system:subAdminUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = new ArrayList<>();
        if(StringUtils.isNotNull(user.getSubAdmin())) {
            list = userSubAdminService.selectUserList(user);
        } else {
            list = userService.selectUserList(user);
        }
        return getDataTable(list);
    }

    @ApiOperation("导出用户列表")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasAnyPermi('system:user:export,system:subUser:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user)
    {
        List<SysUser> list = new ArrayList<>();
        if(StringUtils.isNotNull(user.getSubAdmin())) {
            list = userSubAdminService.selectUserList(user);
        } else {
            list = userService.selectUserList(user);
        }
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @ApiOperation("导入用户")
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasAnyPermi('system:user:import,system:subUser:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, boolean subAdmin) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName, subAdmin);
        return success(message);
    }

    @ApiOperation("下载模版")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @ApiOperation("根据用户编号获取详细信息")
    @PreAuthorize("@ss.hasAnyPermi('system:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) String userId)
    {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            userService.checkUserDataScope(userId);
            SysUser sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @ApiOperation("新增用户")
    @PreAuthorize("@ss.hasAnyPermi('system:user:add,system:subUser:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (!userService.checkUserNameUnique(user))
        {
            throw new ServiceException(MessageUtils.message("user.userName.exists", new Object[] { user.getUserName() }));
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            throw new ServiceException(MessageUtils.message("user.phone.exists", new Object[] { user.getPhonenumber() }));
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            throw new ServiceException(MessageUtils.message("user.email.exists", new Object[] { user.getEmail() }));
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改用户")
    @PreAuthorize("@ss.hasAnyPermi('system:user:edit,system:subUser:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (!userService.checkUserNameUnique(user))
        {
            throw new ServiceException(MessageUtils.message("user.userName.exists", new Object[] { user.getUserName() }));
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            throw new ServiceException(MessageUtils.message("user.phone.exists", new Object[] { user.getPhonenumber() }));
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            throw new ServiceException(MessageUtils.message("user.email.exists", new Object[] { user.getEmail() }));
        }
        user.setUpdateBy(getUsername());

        if(StringUtils.isNotNull(user.getSubAdmin())) {
            return success(userSubAdminService.updateUser(user));
        } else {
            return success(userService.updateUser(user));
        }
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @PreAuthorize("@ss.hasAnyPermi('system:user:remove,system:subUser:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        if (ArrayUtils.contains(userIds, getUserId()))
        {
            return error(MessageUtils.message("user.delete.current.user"));
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @ApiOperation("重置密码")
    @PreAuthorize("@ss.hasAnyPermi('system:user:resetPwd,system:subUser:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        if(StringUtils.isNotNull(user.getSubAdmin())) {
            userSubAdminService.checkUserDataScope(user.getUserId());
        } else {
            userService.checkUserDataScope(user.getUserId());
        }
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @ApiOperation("状态修改")
    @PreAuthorize("@ss.hasAnyPermi('system:user:edit,system:subUser:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        if(StringUtils.isNotNull(user.getSubAdmin())) {
            userSubAdminService.checkUserDataScope(user.getUserId());
        } else {
            userService.checkUserDataScope(user.getUserId());
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @ApiOperation("根据用户编号获取授权角色")
    @PreAuthorize("@ss.hasAnyPermi('system:authUserRole:list,system:authSubUserRole:list')")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") String userId, Boolean subAdmin)
    {

        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = new ArrayList<>();
        if(StringUtils.isNotNull(subAdmin)) {
            roles = roleSubAdminService.selectRolesByUserId(userId);
        } else {
            roles = roleService.selectRolesByUserId(userId);
            roles = SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());
        }
        ajax.put("user", user);
        ajax.put("roles", roles);
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @ApiOperation("用户授权角色")
    @PreAuthorize("@ss.hasAnyPermi('system:authUserRole:list,system:authSubUserRole:list')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(String userId, Long[] roleIds, Boolean subAdmin)
    {
        if(StringUtils.isNotNull(subAdmin)) {
            userSubAdminService.checkUserDataScope(userId);
            userSubAdminService.insertUserAuth(userId, roleIds);
        } else {
            userService.checkUserDataScope(userId);
            userService.insertUserAuth(userId, roleIds);
        }
        return success();
    }

    /**
     * 获取部门树列表
     */
    @ApiOperation("获取部门树列表")
    @PreAuthorize("@ss.hasAnyPermi('system:user:list,system:subAdminUser:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept)
    {
        if(StringUtils.isNotNull(dept.getSubAdmin())) {
            return success(deptSubAdminService.selectDeptTreeList(dept));
        } else {
            return success(deptService.selectDeptTreeList(dept));
        }
    }
}
