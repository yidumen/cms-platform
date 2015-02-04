<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : video-publish
    Created on : 2014-10-25, 14:42:59
    Author     : 蔡迪旻
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
        <link rel="stylesheet" href="/resources/css/iconFont.css">
        <link rel="stylesheet" href="/resources/css/app.css">
        <title>易度门管理平台</title>
    </head>
    <body class="metro">
        <div id="navigate">
            <h1 class="no-margin"><a class="text-muted" href="javascript:window.history.back(); "><span class="icon-arrow-left-3 smaller on-right"></span></a></h1>
            <div class="breadcrumbs">
                <ul>
                    <li><a href="/platform"><span class="icon-home icon"></span>平台</a></li>
                    <li><a>视频管理</a></li>
                    <li><a>视频发布</a></li>
                    <li class="active"><a>发布管理</a></li>
                    <li></li>
                </ul>
            </div>
            <div id="toolbar" class="text-right">
                <i class="icon-help text-success on-left-more padding10"></i><span class="icon-globe on-left padding5">发布视频</span><span class="icon-new on-left padding5">批处理下载</span><span class="icon-remove">删除信息</span>
            </div>
        </div>
        <hr class="ntm">
        <table id="video-table" class="table striped hovered dataTable" frame="below">
            <thead>
                <tr>
                    <th style="width: 56px;">编号</th>
                    <th style="width: auto;">标题</th>
                    <th style="width: 48px;">序号</th>
                    <th style="width: 100px;">拍摄日期</th>
                    <th style="width: 30px;">荐</th>
                    <th style="width: 48px;">状态</th>
                    <th style="width: 100px;">操作</th>
                </tr>
            </thead>
            <tbody id="table-content"></tbody>
        </table>
        <div class="hide">
            <p>需要为该视频生成一个发布序号吗？</p>
            <div>已经生成序号<div class="input-control text size1"><input type="text" id="sort"></div></div>
        </div>
        <script src="/resources/js/jquery/jquery.min.js"></script>
        <script src="/resources/js/jquery/jquery.dataTables.min.js"></script>
        <script src="/resources/js/jquery/jquery.widget.min.js"></script>
        <script src="/resources/js/metro.min.js"></script>
        <script src="/resources/js/video/publish.js"></script>
    </body>
</html>