package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDeptRole;
import com.ruoyi.system.domain.SysPostRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;

/**
 * 岗位信息操作处理
 * 
 * @author ruoyi
 */
@Api("岗位管理")
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController
{
    @Autowired
    private ISysPostService postService;

    /**
     * 获取岗位列表
     */
    @ApiOperation("获取岗位列表")
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPost post)
    {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @ApiOperation("导出岗位列表")
    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post)
    {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @ApiOperation("根据岗位编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable String postId)
    {
        return success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @ApiOperation("新增岗位")
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(getUsername());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @ApiOperation("修改岗位")
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(getUsername());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @ApiOperation("删除岗位")
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable String[] postIds)
    {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<SysPost> posts = postService.selectPostAll();
        return success(posts);
    }

    @ApiOperation("查询岗位关联的角色列表")
    @GetMapping("/role/list")
    public TableDataInfo selectPostRoleList(SysPostRole spr) {
        startPage();
        List<SysRole> list = postService.selectRoleListByPost(spr);
        return getDataTable(list);
    }

    @ApiOperation("新增岗位关联的角色列表")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping("/role/add")
    public AjaxResult insertPostRoleList(@RequestBody SysPostRole spr) {
        if(StringUtils.isEmpty(spr.getRoleIds())) {
            return error("角色列表不能为空");
        }
        return toAjax(postService.insertPostRoleList(spr));
    }

    @ApiOperation("删除岗位关联的角色")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/role/delete")
    public AjaxResult deletePostRoleList(@RequestBody SysPostRole spr) {
        if(StringUtils.isEmpty(spr.getRoleIds())) {
            return error("角色列表不能为空");
        }
        return toAjax(postService.deletePostRoleList(spr));
    }
}
