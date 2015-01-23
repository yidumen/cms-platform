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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
        <link rel="stylesheet" href="/resources/css/iconFont.css">
        <link rel="stylesheet" href="/resources/css/app.css">
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

            <div class="tile-group two">
                <div class="tile-group-title">视频管理</div>
                <hr class="ntm">
                <a class="tile double bg-darkOrange" href="/video/info">
                    <div class="tile-content icon"><i class="icon-film"></i></div>
                    <div class="brand">
                        <span class="label">所有视频</span>
                    </div>
                </a>
                <a class="tile bg-green" href="/video/query">
                    <div class="tile-content icon"><i class="icon-search"></i></div>
                    <div class="brand">
                        <span class="label">高级查询</span>
                    </div>
                </a>
                <a class="tile bg-darkTeal" href="/video/manager">
                    <div class="tile-content icon"><i class="icon-database"></i></div>
                    <div class="brand">
                        <span class="label">信息维护</span>
                    </div>
                </a>
                <a class="tile bg-darkBlue" href="/video/create">
                    <div class="tile-content icon"><i class="icon-pencil"></i></div>
                    <div class="brand">
                        <span class="label">新增视频</span>
                    </div>
                </a>
                <a class="tile bg-darkBlue" href="/video/publish">
                    <div class="tile-content icon"><i class="icon-globe"></i></div>
                    <div class="brand">
                        <span class="label">发布视频</span>
                    </div>
                </a>
            </div>
        </div>
        <script src="/resources/js/jquery/jquery.min.js"></script>
        <script src="/resources/js/jquery/jquery.widget.min.js"></script>
        <script src="/resources/js/metro.min.js"></script>
        <script src="/resources/js/video/video-pub.js"></script>
    </body>
</html>
