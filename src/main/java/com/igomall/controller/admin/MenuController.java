package com.igomall.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.entity.Menu;
import com.igomall.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/save")
    public Message Save(Menu menu,Long parentId){
        menu.setParent(menuService.find(parentId));
        if(isValid(menu)){
            return Message.error("参数错误");
        }
        if(menu.isNew()){
            menu.setTreePath(null);
            menu.setGrade(null);
            menu.setChildren(null);
            menu.setPermissions(null);
            menuService.save(menu);
        }else {
            menu.setParent(menuService.find(parentId));
            if (menu.getParent() != null) {
                Menu parent = menu.getParent();
                if (parent.equals(parent)) {
                    return Message.error("参数错误！");
                }
                List<Menu> children = menuService.findChildren(parent, true, null);
                if (children != null && children.contains(parent)) {
                    return Message.error("参数错误！");
                }
            }
            menuService.update(menu, "treePath", "grade", "children", "permissions");
        }

        return Message.success("操作成功");
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    public Menu edit(Long id) {
        return menuService.find(id);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public List<Menu> list() {
        return menuService.findTree();
    }

    /**
     * 列表
     */
    @PostMapping("/tree")
    @JsonView(Menu.TreeView.class)
    public List<Menu> tree() {
        return menuService.findTree();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Message delete(Long id) {
        Menu menu = menuService.find(id);
        if (menu == null) {
            return ERROR_MESSAGE;
        }
        Set<Menu> children = menu.getChildren();
        if (children != null && !children.isEmpty()) {
            return Message.error("存在下级菜单无法删除");
        }
        menuService.delete(id);
        return SUCCESS_MESSAGE;
    }
}
