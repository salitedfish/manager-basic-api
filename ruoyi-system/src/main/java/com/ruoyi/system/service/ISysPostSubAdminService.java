package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysPost;

import java.util.List;

public interface ISysPostSubAdminService {

    /**
     * 查询子管理员可查询的所有岗位
     * @return
     */
    public List<SysPost> selectPostAll();
}
