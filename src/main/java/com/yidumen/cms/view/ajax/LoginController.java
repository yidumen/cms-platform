package com.yidumen.cms.view.ajax;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 蔡迪旻
 *         2015年12月19日
 */
@RestController
@RequestMapping("login")
public class LoginController {
    @RequestMapping("remember")
    public Map<String, Object> checkRemember(HttpServletRequest request) {
        final Map<String, Object> result = new HashMap<>();
        final Principal principal = request.getUserPrincipal();
        if (principal != null) {
            result.put("logined", true);
            result.put("username", principal.getName());
        }
        return result;
    }
}
