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
        <link rel="stylesheet" href="/resources/css/video-info.css">
        <title>易度门管理平台</title>
    </head>
    <body>
        <table class="table table-hover table-striped table-responsive" frame="below">
            <thead>
                <tr>
                    <th class="table-col-file">编号</th>
                    <th class="table-col-title">标题</th>
                    <th class="table-col-index">序号</th>
                    <th class="table-col-date">拍摄日期</th>
                    <th class="table-col-id">荐</th>
                    <th class="table-col-opera">操作</th>
                </tr>
            </thead>
            <tbody id="table-content">
            </tbody>
        </table>
        <script src="/resources/js/video-publish.js"></script>
    </body>
</html>
