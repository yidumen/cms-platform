<%-- 
    Document   : video-manager
    Created on : 2014-10-17, 17:12:47
    Author     : 蔡迪旻
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core">
    <head>
        <%@include file="/WEB-INF/jspf/meta.jspf" %>
        <%@include file="/WEB-INF/jspf/header.jspf" %>
        <link rel="stylesheet" href="/resources/css/video-info.css">
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navigate.jspf" %>
        <div class="container-fluid">
            <div class="table-counter">
                <label for="count" class="text-primary control-label">每页显示</label>
                <input id="count" class="input-table-counter" type="text" onblur="setCookie('page.size', this.value)">
                <a class="btn btn-primary btn-sm pull-right" href="#"><i class="icon-filter"></i> 过滤</a>
            </div>
            <table class="table table-hover table-striped table-responsive">
                <thead>
                    <tr>
                        <th class="table-col-file">编号</th>
                        <th class="table-col-title">标题</th>
                        <th class="table-col-index">序号</th>
                        <th class="table-col-duration">时长</th>
                        <th class="table-col-date">拍摄日期</th>
                        <th class="table-col-date">发布日期</th>
                        <th class="table-col-column">栏目</th>
                        <th class="table-col-id">荐</th>
                        <th class="table-col-status">状态</th>
                        <th class="table-col-opera">操作</th>
                    </tr>
                </thead>
                <tbody id="table-content">
                </tbody>
            </table>

        </div>
        <%@include file="/WEB-INF/jspf/bootstrap-js.jspf" %>
        <script src="/resources/js/video-manager.js"></script>
    </body>
</html>
