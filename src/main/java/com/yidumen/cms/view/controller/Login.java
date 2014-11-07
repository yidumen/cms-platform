package com.yidumen.cms.view.controller;

import com.yidumen.cms.service.UserService;
import com.yidumen.dao.entity.Account;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Controller
@RequestMapping("")
public class Login {

    private final static Logger LOG = LoggerFactory.getLogger(Login.class);
    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public String welcome(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "login";
        }
        String username = null, password = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                try {
                    username = URLDecoder.decode(cookie.getValue(), "utf-8");
                } catch (UnsupportedEncodingException ex) {
                    return "login";
                }
            }
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
        }
        if (username != null && password != null) {
            return login(request.getSession(), username, password);
        } else {
            return "login";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(final HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        LOG.debug("用户 {} 登录，密码 {}", username, password);
        try {
            Account user = service.verify(username, password);
            session.setAttribute("user", user.getId());
            return "redirect:platform";
        } catch (AccountNotFoundException | FailedLoginException ex) {
            LOG.debug(ex.getMessage());
            return "login";
        }
    }

}
