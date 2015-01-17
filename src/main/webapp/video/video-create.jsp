<%-- 
    Document   : video-pub
    Created on : 2014-10-24, 9:15:56
    Author     : 蔡迪旻
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>创建视频</title>
        <link rel="stylesheet" href="/resources/css/default.css">
    </head>
    <body>
        <form:form id="form" action="/video/add" commandName="video">
            <div class="panel inline-block margin5 top-align">
                <div class="panel-header bg-darkRed fg-white">必填项</div>
                <div class="panel-content">
                    <h5>文件编号</h5>
                    <div class="input-control text size4">
                        <form:input path="file" required="true" />
                    </div>
                    <h5>视频标题</h5>
                    <div class="input-control text">
                        <form:input path="title" required="true" />
                    </div>
                    <h5>拍摄日期</h5>
                    <div class="input-control text" data-role="datepicker" data-format="yyyy年mm月dd日" data-locale="zhCN">
                        <form:input path="shootTime" required="true" /><button class="btn-date"></button>
                    </div>
                </div>
            </div>
            <div class="panel inline-block margin5">
                <div class="panel-header">选填项</div>
                <div class="panel-content">
                    <h5>发布序号</h5>
                    <div class="input-control text size4 block">
                        <form:input path="sort" />
                    </div>
                    <span>为0则忽略此序号</span>
                    <h5>推荐度</h5>
                    <div class="input-control text">
                        <form:input path="recommend" />
                        <span class="help-block">为0则为不推荐</span>
                    </div>
                    <h5>视频描述（简短内容摘要）</h5>
                    <div class="input-control textarea">
                        <form:textarea path="descrpition" />
                    </div>
                    <h5>备注</h5>
                    <div class="input-control text">
                        <form:input path="note" />
                    </div>
                    <h5>分级信息</h5>
                    <div class="input-control text">
                        <form:input path="grade" />
                    </div>
                </div>
            </div>
            <hr>
            <button type="submit" class="default large place-right">
                <i class="fa fa-check"></i>
                提交
            </button>
        </form:form>
        <script src="/resources/js/metro.min.js"></script>
        <script src="/resources/js/video-pub.js"></script>
    </body>
</html>
