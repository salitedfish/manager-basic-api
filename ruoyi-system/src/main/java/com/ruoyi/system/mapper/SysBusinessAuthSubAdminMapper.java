package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysBusinessAuthSubAdminMapper {

    /**
     * 根据业务权限信息查询业务权限列表
     */
    public List<SysBusinessAuth> selectBusinessAuthList(@Param("businessAuth")SysBusinessAuth sba,@Param("userId") String userId);
}
