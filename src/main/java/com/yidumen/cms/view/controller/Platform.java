package com.yidumen.cms.view.controller;

import com.yidumen.cms.service.UserService;
import com.yidumen.cms.view.model.MenuModel;
import com.yidumen.dao.entity.Account;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author 蔡迪旻
 */
@Controller
public class Platform {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("platform")
    public String framework(Model model, HttpSession session) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
        model.addAttribute("menus", context.getBean("menus"));
        final Long id = (Long) session.getAttribute("user");
        int permission = userService.find(id).getUserGroup().ordinal();
        model.addAttribute("permission", permission);
        return "/main";
    }
}
