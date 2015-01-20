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
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
        <link rel="stylesheet" href="/resources/css/signin.css">
        <link rel="stylesheet" href="/resources/css/app.css">
        <title>易度门管理平台</title>
    </head>
    <body class="metro">
        <div class="login-panel center-screen border shadow padding20">
            <h3>请登录</h3>
            <form method="post">
                <div class="input-control text">
                    <input id="username" type="text" name="username" placeholder="输入手机或邮箱" required autofocus>
                    <button class="btn-clear"></button>
                </div>
                <div class="input-control password">
                    <input id="password" type="password" name="password" placeholder="输入密码" required>
                    <button class="btn-reveal"></button>
                </div>
                <div class="input-control checkbox">
                    <label>
                        <input id="autologin" type="checkbox" value="remember-me">
                        <span class="check"></span>
                        <span>自动登录</span>
                    </label>
                </div>
                <button type="submit" class="info bg-hover-darkCyan large place-right">登 录</button>
            </form>
        </div>
        <script src="/resources/js/jquery/jquery.min.js"></script>
        <script src="/resources/js/metro.min.js"></script>
        <script src="/resources/js/cms.js"></script>
        <script src="/resources/js/md5-min.js"></script>
        <script src="/resources/js/login.js"></script>
    </body>
</html>
