package com.yidumen.cms.view.controller;

import com.jfinal.core.Controller;
import com.yidumen.cms.dao.Account;
import com.yidumen.cms.dao.constant.AccountGroup;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.decode;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public final class LoginController extends Controller {

    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private final UserService service;

    public LoginController() {
        service = ServiceFactory.generateUserService();
    }

    public void index() {
        final String username = getCookie("username"), password = getCookie("password");
        if (username != null && password != null) {
            try {
                login(decode(username, "utf-8"), password);
            } catch (UnsupportedEncodingException e) {
                render("login.html");
            }
        } else {
            render("login.html");
        }
    }

    private void login(String username, String password) {
        LOG.debug("用户 {} 登录，密码 {}", username, password);
        try {
            Account user = service.verify(username, password);
            setSessionAttr("user", user.get("id"));
            redirect("/platform");
        } catch (AccountNotFoundException | FailedLoginException ex) {
            LOG.debug(ex.getMessage());
            redirect("/");
        }
    }

    public void login() {
        final String username = getPara("username"), password = getPara("password");
        login(username, password);
    }

    public void signout() {
        final HttpSession session = getSession(false);
        if (session != null) {
            session.invalidate();
        }
        this.removeCookie("username");
        this.removeCookie("password");
        redirect("/");
    }
    
    public void platform() {
        final Long id = getSessionAttr("user");
        final Account user = service.find(id);
        setAttr("name", user.get("nickname"));
        setAttr("group",AccountGroup.getNameByOrdinal((int) user.get("userGroup")));
        render("framework.html");
    }

}
