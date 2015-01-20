<%-- 
    Document   : main
    Created on : 2014-10-14, 15:44:06
    Author     : 蔡迪旻
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
        <link rel="stylesheet" href="/resources/css/iconFont.css">
        <link rel="stylesheet" href="/resources/css/app.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <title>易度门管理平台</title>
    </head>
    <body class="metro">
        <div class="tile-area tile-area-dark">
            <h1 class="tile-area-title fg-white">易度门管理平台</h1>
            <div class="user-id">
                <div class="user-id-image">
                    <span class="icon-user"></span>
                </div>
                <div class="user-id-name">
                    <span class="first-name">${name}</span>
                    <span class="last-name">${group}</span>
                </div>
            </div>

            <div class="tile-group three">
                <div class="tile-group-title">视频管理</div>
                <a class="tile double double-vertical bg-darkBlue" href="/video/manager">
                    <div class="tile-status">
                        <span class="name">所有视频</span>
                    </div>
                </a>
            </div>
        </div>
        <script src="/resources/js/jquery/jquery.min.js"></script>
        <script src="/resources/js/navigate.js"></script>
        <script src="/resources/js/jquery/jquery.widget.min.js"></script>
        <script src="/resources/js/metro.min.js"></script>
    </body>
</html>
