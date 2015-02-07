package com.yidumen.cms.framework;

import com.jfinal.handler.Handler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 蔡迪旻
 */
public class SecurityHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.startsWith("/resource") || target.equals("/") || target.startsWith("/login")) {
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
        nextHandler.handle(target, request, response, isHandled);
    }

}
