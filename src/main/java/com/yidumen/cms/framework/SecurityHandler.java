package com.yidumen.cms.framework;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author 蔡迪旻
 */
public final class SecurityHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.startsWith("/resource") || target.startsWith("/wechat") || target.equals("/") || target.startsWith("/login")) {
            nextHandler.handle(target, request, response, isHandled);
            return;
        }
        final HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            try {
                response.sendRedirect("/");
                isHandled[0] = true;
            } catch (IOException ex) {
            }
            return;
        }
    }

}
