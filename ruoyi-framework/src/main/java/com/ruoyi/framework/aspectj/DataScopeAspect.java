package com.ruoyi.framework.aspectj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessAuthOrgType;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.SysBusinessAuthMapper;
import com.ruoyi.system.mapper.SysConfigMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.context.PermissionContextHolder;

/**
 * 数据过滤处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class DataScopeAspect
{
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Autowired
    private SysBusinessAuthMapper businessAuthMapper;

    @Autowired
    private RedisCache redisCache;


    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) throws Throwable
    {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin())
            {
                // 获取登录用户的数据权限
                List<SysBusinessAuth> sbas = loginUser.getBusinessAuthList();
                // 获取需要过滤的业务标识
                String businessCode = controllerDataScope.businessCode();
                // 根据业务标识过滤有哪些业务权限
                List<SysBusinessAuth> currentAuths = sbas.stream().filter(sba->sba.getBusinessCode().equals(businessCode)).collect(Collectors.toList());
                // 将人员和部门的业务权限区分开
                Set<String> deptIds = currentAuths.stream().filter(sba->sba.getManageOrgType().equals(BusinessAuthOrgType.DEPT.getValue())).map(sba->sba.getManageOrgId()).collect(Collectors.toSet());
                Set<String> userIds = currentAuths.stream().filter(sba->sba.getManageOrgType().equals(BusinessAuthOrgType.USER.getValue())).map(sba->sba.getManageOrgId()).collect(Collectors.toSet());
                // 构建过滤sql字符串
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias(), controllerDataScope.createAlias(),deptIds, userIds);
//                String permission = StringUtils.defaultIfEmpty(controllerDataScope.permission(), PermissionContextHolder.getContext());
//                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(),
//                        controllerDataScope.userAlias(), permission);
            }
        }
    }

    /**
     * 数据范围过滤（二开）
     * @param joinPoint
     * @param user
     * @param deptAlias
     * @param userAlias
     * @param createAlias
     * @param deptIds
     * @param userIds
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias, String createAlias, Set<String> deptIds, Set<String> userIds) {

        StringBuilder sqlString = new StringBuilder();
        List<String> conditions = new ArrayList<>();

        // 通过部门去过滤
        if(StringUtils.isNotEmpty(deptAlias)) {
            for(String deptId: deptIds) {
               sqlString.append(StringUtils.format(" OR {}.dept_id = '{}'", deptAlias, deptId));
               conditions.add(deptId);
            }
        }

        // 通过人员id去过滤
        if(StringUtils.isNotEmpty(userAlias)) {
            for(String userId: userIds) {
                sqlString.append(StringUtils.format(" OR {}.user_id = '{}'",userAlias, userId));
//                sqlString.append(StringUtils.format("OR {}.user_id = '{}' OR {}.create_by IN (SELECT user_name FROM sys_user WHERE user_id = '{}')",userAlias, userId, userAlias, userId));
                conditions.add(userId);
            }
        }

        // 通过创建人id去过滤
        if(StringUtils.isNotEmpty(createAlias)) {
            for(String userId: userIds) {
                sqlString.append(StringUtils.format(" OR {}.create_id = '{}'",createAlias, userId));
                conditions.add(userId);
            }
        }

        // 如果conditions没有，则说明用户没有任何业务权限，排除所有数据
        if(StringUtils.isEmpty(conditions)) {
            sqlString.append(StringUtils.format(" OR 1 = 0 "));
        }

        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
        {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
        }

    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user 用户
     * @param deptAlias 部门别名
     * @param userAlias 用户别名
     * @param permission 权限字符
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias, String permission)
    {


        StringBuilder sqlString = new StringBuilder();
        List<String> conditions = new ArrayList<String>();

        for (SysRole role : user.getRoles())
        {
            String dataScope = role.getDataScope();
            if (!DATA_SCOPE_CUSTOM.equals(dataScope) && conditions.contains(dataScope))
            {
                continue;
            }
            if (StringUtils.isNotEmpty(permission) && StringUtils.isNotEmpty(role.getPermissions())
                    && !StringUtils.containsAny(role.getPermissions(), Convert.toStrArray(permission)))
            {
                continue;
            }
            if (DATA_SCOPE_ALL.equals(dataScope))
            {
                sqlString = new StringBuilder();
                conditions.add(dataScope);
                break;
            }
            else if (DATA_SCOPE_CUSTOM.equals(dataScope))
            {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
                        role.getRoleId()));
            }
            else if (DATA_SCOPE_DEPT.equals(dataScope))
            {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            }
            else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))
            {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        deptAlias, user.getDeptId(), user.getDeptId()));
            }
            else if (DATA_SCOPE_SELF.equals(dataScope))
            {
                if (StringUtils.isNotBlank(userAlias))
                {
                    sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
                }
                else
                {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(StringUtils.format(" OR {}.dept_id = 0 ", deptAlias));
                }
            }
            conditions.add(dataScope);
        }

        // 多角色情况下，所有角色都不包含传递过来的权限字符，这个时候sqlString也会为空，所以要限制一下,不查询任何数据
        if (StringUtils.isEmpty(conditions))
        {
            sqlString.append(StringUtils.format(" OR {}.dept_id = 0 ", deptAlias));
        }

        if (StringUtils.isNotBlank(sqlString.toString()))
        {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
            {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint)
    {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
        {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }
}
