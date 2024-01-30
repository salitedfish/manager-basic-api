package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.SysDeptRole;
import com.ruoyi.system.domain.SysPostRole;
import com.ruoyi.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.mapper.SysPostMapper;
import com.ruoyi.system.mapper.SysUserPostMapper;
import com.ruoyi.system.service.ISysPostService;

/**
 * 岗位信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysPostServiceImpl implements ISysPostService
{
    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询岗位信息集合
     * 
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post)
    {
        return postMapper.selectPostList(post);
    }

    /**
     * 查询所有岗位
     * 
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll()
    {
        return postMapper.selectPostAll();
    }

    /**
     * 通过岗位ID查询岗位信息
     * 
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(String postId)
    {
        SysPost sp = postMapper.selectPostById(postId);
        SysPostRole spr = new SysPostRole();
        spr.setPostId(postId);
        List<SysRole> roles = selectRoleListByPost(spr);
        sp.setRoles(roles);
        return sp;
    }

    /**
     * 根据用户ID获取岗位选择框列表
     * 
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<String> selectPostListByUserId(String userId)
    {
        return postMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostNameUnique(SysPost post)
    {
        String postId = StringUtils.isNull(post.getPostId()) ? "" : post.getPostId();
        SysPost info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && (!info.getPostId().equals(postId)))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostCodeUnique(SysPost post)
    {
        String postId = StringUtils.isNull(post.getPostId()) ? "" : post.getPostId();
        SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && (!info.getPostId().equals(postId)))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(String postId)
    {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 删除岗位信息
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int deletePostById(String postId)
    {
        return postMapper.deletePostById(postId);
    }

    /**
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    @Override
    public int deletePostByIds(String[] postIds)
    {
        for (String postId : postIds)
        {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0)
            {
                throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int insertPost(SysPost post)
    {
        post.setPostId(IdUtils.fastUUID());
        // 新增岗位关联角色
//        insertPostRole(post);
        return postMapper.insertPost(post);
    }

    /**
     * 修改保存岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int updatePost(SysPost post)
    {
        // 删除岗位关联角色
//        deletePostRole(post);
        // 新增岗位关联角色
//        insertPostRole(post);

        return postMapper.updatePost(post);
    }

    /**
     * 新增岗位关联角色
     * @param spr
     */
    public int insertPostRoleList(SysPostRole spr) {

        List<SysPostRole> sdrs = new ArrayList<>();
            for(Long roleId: spr.getRoleIds()) {
                SysPostRole sdr = new SysPostRole();
                sdr.setPostId(spr.getPostId());
                sdr.setRoleId(roleId);
                sdr.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserName());
                // 先判断之前有没有同样的数据
                // 如果没有再新增
                sdrs.add(sdr);
            }
           return postMapper.insertPostRole(sdrs);
    };

    /**
     * 删除岗位和全部角色的关联
     * @param post
     */
    public void deletePostRole(SysPost post) {
        postMapper.deletePostRole(post);
    };

    /**
     * 通过角色id删除岗位关联的角色
     * @param sdr
     */
    public int deletePostRoleList(SysPostRole sdr) {
        List<SysPostRole> sdrs = new ArrayList<>();
        for(Long roleId: sdr.getRoleIds()) {
            SysPostRole sd = new SysPostRole();
            sd.setPostId(sdr.getPostId());
            sd.setRoleId(roleId);
            sdrs.add(sd);
        }
        return postMapper.deletePostRoleList(sdrs);

    };


    /**
     * 查询岗位对应的角色列表
     * @param spr
     * @return
     */
    public List<SysRole> selectRoleListByPost(SysPostRole spr) {
        return postMapper.selectRoleListByPost(spr);
    };
}
