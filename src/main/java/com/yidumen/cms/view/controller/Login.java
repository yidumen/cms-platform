package com.yidumen.cms.view.controller;

import com.yidumen.dao.entity.CmsUser;
import java.io.Serializable;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Named("login")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class Login implements Serializable {

    private CmsUser user;
    public Login() {
        user = new CmsUser();
    }

    public String login() {
        if (user.getUsername().equals("admin") && user.getPassword().equals("admin")) {
            return "framework?faces-redirect=true";
        } else {
            return null;
        }
    }

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
}
