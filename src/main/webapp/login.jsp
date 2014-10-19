<%-- 
    Document   : login
    Created on : 2014-10-14, 0:51:03
    Author     : 蔡迪旻
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <%@include file="WEB-INF/jspf/meta.jspf" %>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <link rel="stylesheet" href="/resources/css/signin.css">
        <title>易度门管理平台</title>
    </head>
    <body>
        <div class="panel panel-primary center-screen login-panel">
            <div class="panel-body">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#phone" data-toggle="tab">手机登录</a></li>
                    <li><a href="#email" data-toggle="tab">邮箱登录</a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div class="tab-pane active" id="phone">
                        <form:form cssClass="form-signin" commandName="user" method="POST">
                            <h2 class="form-signin-heading">请登录</h2>
                            <form:input cssClass="form-control" path="phone" placeholder="输入登录手机" required="true" autofocus="true" />
                            <form:password cssClass="form-control" path="password" placeholder="输入密码" required="true" />
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="remember-me"> 自动登录
                                </label>
                            </div>
                            <button type="submit" class="btn btn-block btn-lg btn-primary">登 录</button>
                        </form:form>
                    </div>
                    <div class="tab-pane" id="email">
                        <form:form commandName="user" cssClass="form-signin" method="POST">
                            <h2 class="form-signin-heading">请登录</h2>
                            <form:input cssClass="form-control" type="email" path="phone" placeholder="输入登录邮箱" required="true" />
                            <form:password cssClass="form-control" path="password" placeholder="输入密码" required="true" />
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="remember-me"> 自动登录
                                </label>
                            </div>
                            <button type="submit" class="btn btn-block btn-lg btn-primary">登 录</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="WEB-INF/jspf/bootstrap-js.jspf" %>
    </body>
</html>
