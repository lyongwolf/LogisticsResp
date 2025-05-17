package com.ly.controller;

import com.ly.pojo.BasicData;
import com.ly.service.IBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 基础数据
 */
@Controller
@RequestMapping("/basic")
public class BasicController {

    @Autowired
    private IBasicService basicService;

    @RequestMapping("/query")
    public String basic(BasicData data, Model model) throws Exception {
        List<BasicData> list = basicService.query(data);
        model.addAttribute("list", list);
        return "basic/basic";
    }

    /**
     * 跳转到表单页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/basicDispatch")
    public String basicDispatch(Integer id, Model model) throws Exception {
        if (id != null) {
            // 是更新操作 ：根据编号查询出对应的基础数据
            BasicData basicData = basicService.queryById(id);
            model.addAttribute("basicData", basicData);
        }
        // 查询出所有的大类数据
        List<BasicData> parents = basicService.queryAllParentData();
        model.addAttribute("parents", parents);
        return "basic/updateBasic";
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(BasicData data) throws Exception {
        if (data.getBaseId() != null && data.getBaseId() > 0) {
            // 说明是更新操作
            basicService.updateBasicData(data);
        } else {
            // 说明是添加操作
            basicService.addBasicData(data);
        }
        return "redirect:/basic/query";
    }
}
