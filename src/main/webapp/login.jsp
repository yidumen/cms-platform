<%-- 
    Document   : login
    Created on : 2014-10-14, 0:51:03
    Author     : 蔡迪旻
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="/resources/css/signin.css">
        <link rel="stylesheet" href="/resources/css/default.css">
        <title>易度门管理平台</title>
    </head>
    <body>
        <div class="panel panel-primary login-panel center-screen">
            <div class="panel-body">
                <form class="form-signin" method="post">
                    <h2 class="form-signin-heading">请登录</h2>
                    <input id="username" class="form-control" type="text" name="username" placeholder="输入手机或邮箱" required="true" autofocus="true">
                    <input id="password" class="form-control" type="password" name="password" placeholder="输入密码" required="true" />
                    <div class="checkbox">
                        <label>
                            <input id="autologin" type="checkbox" value="remember-me"> 自动登录
                        </label>
                    </div>
                    <button type="submit" class="btn btn-block btn-lg btn-primary">登 录</button>
                </form>
            </div>
        </div>
        <script src="/resources/js/jquery-2.1.1.min.js"></script>
        <script src="/resources/js/bootstrap.min.js"></script>
        <script src="/resources/js/cms.js"></script>
        <script src="/resources/js/md5-min.js"></script>
        <script src="/resources/js/login.js"></script>
    </body>
</html>
