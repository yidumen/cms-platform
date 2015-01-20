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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>创建视频</title>
        <link rel="stylesheet" href="/resources/css/bootstrap-datetimepicker.min.css">
    </head>
    <body>
        <div class="row">
            <div class="col-xs-4 col-xs-offset-2">
                <form:form id="form" action="/video/add" commandName="video">
                    <div class="form-group">
                        <label>文件编号<small class="text-danger">（必填）</small></label>
                        <form:input path="file" cssClass="form-control input-sm" required="true" />
                    </div>
                    <div class="form-group">
                        <label>视频标题<small class="text-danger">（必填）</small></label>
                        <form:input path="title" cssClass="form-control input-sm" required="true" />
                    </div>
                    <div class="form-group">
                        <label>拍摄日期<small class="text-danger">（必填）</small></label>
                        <form:input id="pub-shoot" data-font-awesome="true" path="shootTime" cssClass="form-control input-sm" required="true" />
                    </div>
                    <div class="form-group">
                        <label>发布序号</label>
                        <form:input path="sort" cssClass="form-control input-sm" />
                        <span class="help-block">为0则忽略此序号</span>
                    </div>
                    <div class="form-group">
                        <label>推荐度</label>
                        <form:input path="recommend" cssClass="form-control input-sm" />
                        <span class="help-block">为0则为不推荐</span>
                    </div>
                    <div class="form-group">
                        <label>视频描述（简短内容摘要）</label>
                        <form:textarea path="descrpition" cssClass="form-control input-sm" />
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <form:input path="note" cssClass="form-control input-sm" />
                    </div>
                    <div class="form-group">
                        <label>分级信息</label>
                        <form:input path="grade" cssClass="form-control input-sm" />
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">提交</button>
                        </div>
                </form:form>
            </div>
        </div>
        <script src="/resources/js/bootstrap-datetimepicker.min.js"></script>
        <script src="/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
        <script src="/resources/js/video-pub.js"></script>
    </body>
</html>
