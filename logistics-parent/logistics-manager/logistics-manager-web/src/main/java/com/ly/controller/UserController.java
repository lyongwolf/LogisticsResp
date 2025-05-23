package com.ly.controller;

import com.github.pagehelper.PageInfo;
import com.ly.dto.UserDto;
import com.ly.pojo.Role;
import com.ly.pojo.User;
import com.ly.service.IRoleService;
import com.ly.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户管理
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/query")
    public String query(UserDto userDto, Model model) throws Exception {
        PageInfo<User> pageModel = userService.queryByPage(userDto);
        model.addAttribute("pageModel", pageModel);
        return "user/user";
    }

    @RequestMapping("/userDispatch")
    public String userDispatch(Integer userId, Model model) throws Exception {
        if (userId != null && userId > 0) {
            // 当前请求是要更新用户信息，需要根据用户编号查询出用户的详细信息
            User user = userService.queryById(userId);
            model.addAttribute("user", user);

            // 查询出当前用户具有的角色数据
            List<Integer> ownerRoleIds = userService.queryUserRoleIds(userId);
            model.addAttribute("ownerRoleIds", ownerRoleIds);
        }
        // 查询所有角色信息
        List<Role> roles = roleService.query(new Role());
        model.addAttribute("roles", roles);

        return "user/updateUser";
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(UserDto dto) throws Exception {
        // 1.保存用户信息
        // 2.保存角色和用户的关联关系
        Integer count = userService.saveOrUpdate(dto);
        return "redirect:/user/query";
    }

    @RequestMapping("/checkUserName")
    @ResponseBody
    public String checkUserName(User user) throws Exception {
        List<User> list = userService.query(user);
        if (list == null || list.size() == 0) {
            // 表示根据提交的账号查询不到数据，说明账号不存在
            return "1";
        }
        return "0";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(Integer userId) throws Exception {
        userService.deleteUser(userId);
        return "redirect:/user/query";
    }

}
