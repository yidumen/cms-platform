package com.yidumen.cms.view.controller;

import com.yidumen.cms.service.VideoService;
import com.yidumen.dao.entity.Video;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Controller
@RequestMapping("/video")
@SessionAttributes(value = {"page.current"})
public final class VideoController {

    private final static transient Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private transient VideoService service;

    @RequestMapping("/manager")
    public String manager(Model model) {
        model.addAttribute("page.current", 0);
        return "video-manager";
    }

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<Video> videoList(@CookieValue("page.size") int pageSize,
                                 @ModelAttribute("page.current") String currentPage,
                                 final Model model) {
        log.debug("pageSize is {}", pageSize);
        log.debug("current page is {}", currentPage);
        model.addAttribute("page.current", currentPage + 1);
        return service.nextPage(Integer.parseInt(currentPage), pageSize);
    }

}
