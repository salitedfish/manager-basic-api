package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysPost;

import java.util.List;

public interface SysPostSubAdminMapper {

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    public List<SysPost> selectPostAll(String userId);
}
