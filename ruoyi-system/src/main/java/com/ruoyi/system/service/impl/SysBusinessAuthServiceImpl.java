package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessAuthOrgType;
import com.ruoyi.common.enums.SelectTreeNodeType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.SysBusinessAuthMapper;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysBusinessAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysBusinessAuthServiceImpl implements ISysBusinessAuthService {

    @Autowired
    private SysBusinessAuthMapper businessAuthMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDeptMapper deptMapper;


    /**
     * 根据权限信息查询业务权限列表
     */
    public List<SysBusinessAuth> selectBusinessAuthList(SysBusinessAuth sbam) {
        String orgType = sbam.getOrgType();
        String orgId = sbam.getOrgId();
        if(StringUtils.isNotNull(orgId) && StringUtils.isNull(sbam.getDeptFullPathId())) {
            if(orgType.equals(BusinessAuthOrgType.DEPT.getValue())) {
                SysDept dept = deptMapper.selectDeptById(orgId);
                sbam.setDeptFullPathId(dept.getAncestors() + "," + dept.getDeptId());
            }
            if(orgType.equals(BusinessAuthOrgType.USER.getValue())) {
                SysUser user = userMapper.selectUserById(orgId);
                SysDept dept = deptMapper.selectDeptById(user.getDeptId());
                sbam.setDeptFullPathId(dept.getAncestors() + "," + dept.getDeptId());
            }
        }

        List<SysBusinessAuth> sbas = businessAuthMapper.selectBusinessAuthList(sbam);

        return sbas;
    };

    /**
     * 新增业务权限
     */
    public int insertBusinessAuth(List<SysBusinessAuth> sysBusinessAuthList) {
        List<SysBusinessAuth> sbas = new ArrayList<>();
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        for(SysBusinessAuth sba: sysBusinessAuthList) {
            SysBusinessAuth sbaNew = sba;
            // 先判断之前有没有同样的数据
            // 如果没有再新增
            sbaNew.setCreateBy(userName);
            sbas.add(sbaNew);
        }
        return businessAuthMapper.insertBusinessAuth(sbas);
    };

    /**
     * 删除业务授权列表
     */
    public int deleteBusinessAuth(List<Long> businessAuthIds) {

        return businessAuthMapper.deleteBusinessAuth(businessAuthIds);
    };

//    /**
//     * 根据用户查询业务权限列表
//     */
//    public List<SysBusinessAuth> getBusinessAuthListByUser(SysUser user) {
//        List<SysBusinessAuth> sbas = businessAuthMapper.getBusinessAuthListByUser(user);
//        return sbas;
//    };

//    /**
//     * 补全业务权限列表
//     */
//    public List<SysBusinessAuth> genBusinessAuthList(List<SysBusinessAuth> sbas) {
//        for(SysBusinessAuth sba: sbas) {
//            String deptFullPathId = sba.getDeptFullPathId();
//            if(sba.getOrgType().equals(SelectTreeNodeType.USER.getValue())) {
//                sba.setFullPathId(deptFullPathId + "," + sba.getOrgId());
//            } else {
//                sba.setFullPathId(deptFullPathId);
//            }
//        }
//        return sbas;
//    };


}
