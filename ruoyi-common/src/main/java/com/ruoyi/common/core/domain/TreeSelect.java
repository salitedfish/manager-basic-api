package com.ruoyi.common.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.SelectTreeNodeType;

/**
 * Treeselect树结构实体类
 * 
 * @author ruoyi
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 节点ID全路径 */
    private String fullPathId;

    /**
     * 节点类型
     */
    private String type;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.fullPathId = dept.getAncestors() + "," + dept.getDeptId();
        this.type = SelectTreeNodeType.DEPT.getValue();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysUser user)
    {
        this.id = user.getUserId();
        this.label = user.getNickName();
        this.fullPathId = user.getDeptFullPathId() + "," + user.getUserId();
        this.type = SelectTreeNodeType.USER.getValue();
    }

    public TreeSelect(SysDept dept, List<SysUser> users)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.fullPathId = dept.getAncestors() + "," + dept.getDeptId();
        this.type = SelectTreeNodeType.DEPT.getValue();
        this.children = dept.getChildren().stream().map(d -> new TreeSelect(d, users)).collect(Collectors.toList());
        // 遍历人员，如果是在这个部门的，则挂在这个部门下面
        for(SysUser user: users) {
            if(this.id.equals(user.getDeptId())) {
                this.children.add(new TreeSelect(user));
            }
        }
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId().toString();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }



    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getFullPathId() {
        return fullPathId;
    }

    public void setFullPathId(String fullPathId) {
        this.fullPathId = fullPathId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
