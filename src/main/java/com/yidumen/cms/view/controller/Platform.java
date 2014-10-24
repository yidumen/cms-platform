package com.yidumen.cms.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author 蔡迪旻
 */
@Controller
public class Platform {
    @RequestMapping("/platform")
    public String framework() {
        return "main";
    }
}
