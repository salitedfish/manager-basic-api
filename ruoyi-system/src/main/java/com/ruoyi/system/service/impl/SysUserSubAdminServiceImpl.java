package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserPostMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.mapper.SysUserSusAdminMapper;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserSubAdminServiceImpl implements ISysUserSubAdminService {

    @Autowired
    private SysUserSusAdminMapper userSusAdminMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysRoleSubAdminService roleSubAdminService;

    @Autowired
    private ISysPostSubAdminService postSubAdminService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserList(SysUser user)
    {
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return userSusAdminMapper.selectUserList(user, userId);
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(String userId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException(MessageUtils.message("user.action.user.no.auth"));
            }
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user)
    {
        updateUserRole(user.getUserId(), user.getRoleIds());
        updateUserPost(user.getUserId(), user.getPostIds());
        return userMapper.updateUser(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param ids 角色组
     */
    @Override
    public void insertUserAuth(String userId, Long[] ids) {
        updateUserRole(userId, ids);

    };

    public void updateUserRole(String userId, Long[] ids){
        SysUser sysUser = userService.selectUserById(userId);

        // 构建要修改的角色
        List<Long> roleIdList = new ArrayList<>();
        List<Long> srs = roleSubAdminService.selectRoleAll().stream().map(SysRole::getRoleId).collect(Collectors.toList());
        List<Long> roleIds = sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList());
        for(Long id: roleIds) {
            Boolean exits = false;
            for(Long i: srs) {
                if(id.longValue() == i.longValue()) {
                    exits = true;
                    break;
                }
            }
            if(!exits) {
                roleIdList.add(id);
            }
        }
        roleIdList.addAll(Arrays.asList(ids));
        sysUser.setRoleIds(roleIdList.toArray(new Long[roleIdList.size()]));

        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        userService.insertUserRole(sysUser);
    };

    public void updateUserPost(String userId, String[] ids){

        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);

        List<String> postIdList = new ArrayList<>();
        List<String> sps = postSubAdminService.selectPostAll().stream().map(SysPost::getPostId).collect(Collectors.toList());
        List<String> postIds = postService.selectPostListByUserId(userId);
        for(String id: postIds) {
            Boolean exits = false;
            for(String i: sps) {
                if(id.equals(i)) {
                    exits = true;
                    break;
                }
            }
            if(!exits) {
                postIdList.add(id);
            }
        }
        postIdList.addAll(Arrays.asList(ids));
        sysUser.setPostIds(postIdList.toArray(new String[postIdList.size()]));


        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        userService.insertUserPost(sysUser);
    }

}
