package com.ruoyi.framework.web.service;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.enums.BusinessAuthOrgType;
import com.ruoyi.system.service.ISysBusinessAuthService;
import com.ruoyi.system.service.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;

import java.util.HashSet;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysBusinessAuthService businessAuthService;

    @Autowired
    private ISysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByUserName(username);
        // 设置用户全部的角色，包括继承的角色
        user.setRoles(roleService.selectALLRolesByUser(user));
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException(MessageUtils.message("user.not.exists"));
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

        passwordService.validate(user);

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        SysBusinessAuth sba = new SysBusinessAuth();
        sba.setOrgId(user.getUserId());
        sba.setOrgType(BusinessAuthOrgType.USER.getValue());
        sba.setDeptFullPathId(user.getDeptFullPathId());
        sba.setExtendsOrg(true);
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user), businessAuthService.selectBusinessAuthList(sba));
    }
}
