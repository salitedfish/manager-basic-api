package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.mapper.SysPostSubAdminMapper;
import com.ruoyi.system.service.ISysPostSubAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPostSubAdminServiceImpl implements ISysPostSubAdminService {

    @Autowired
    private SysPostSubAdminMapper postSubAdminMapper;

    /**
     * 查询子管理员可查询的所有岗位
     * @return
     */
    @Override
    public List<SysPost> selectPostAll() {
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return postSubAdminMapper.selectPostAll(userId);
    };
}
