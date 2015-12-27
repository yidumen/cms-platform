package com.yidumen.cms.view;

import com.yidumen.cms.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 蔡迪旻
 *         2015年11月28日
 */
@Controller
@RequestMapping("/video/exportExcel")
public class ExportExcelController {
    @Autowired
    private VideoService service;

    @RequestMapping(method = RequestMethod.GET)
    public String exportExcel(Model model) {
        model.addAttribute("videos", service.getVideos());
        return "clipInfo";
    }
}
