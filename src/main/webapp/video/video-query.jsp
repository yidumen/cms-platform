<%-- 
    Document   : video-query
    Created on : 2014-10-23, 17:47:11
    Author     : 蔡迪旻
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/resources/css/bootstrap-datetimepicker.min.css">
        <title>视频高级查询</title>
    </head>
    <body>
        <div class="form-horizontal">
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">文件编号：</label>
                <label class="col-xs-1 control-label">从</label>
                <div class="col-xs-2">
                    <input class="form-control" name="file" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control" name="file2" />
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">标题：</label>
                <label class="col-xs-1 control-label">包含</label>
                <div class="col-xs-5">
                    <input class="form-control" name="title" />
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">视频时长：</label>
                <label class="col-xs-1 control-label">从</label>
                <div class="col-xs-2">
                    <input class="form-control" name="duration" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control" name="duration2" />
                </div>
                <p class="form-control-static">分钟</p>
            </div>

            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">拍摄日期：</label>
                <label class="col-xs-1 control-label">从</label>
                <div class="col-xs-2">
                    <input class="form-control date date-shoot" data-font-awesome="true" name="shootTime"/>

                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control date date-shoot" data-font-awesome="true" name="shootTime2"/>
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">发布时间：</label>
                <label class="col-xs-1 control-label">从</label>
                <div class="col-xs-2">
                    <input class="form-control date date-pub" name="pubDate" data-font-awesome="true" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control date date-pub" name="pubDate2" data-font-awesome="true"/>
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">发布序号：</label>
                <label class="col-xs-1 control-label">从</label>
                <div class="col-xs-2">
                    <input class="form-control" name="sort" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control" name="sort2" />
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">描述信息：</label>
                <label class="col-xs-1 control-label">包含</label>
                <div class="col-xs-5">
                    <input class="form-control" name="descrpition" />
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">备注信息：</label>
                <label class="col-xs-1 control-label">包含</label>
                <div class="col-xs-5">
                    <input class="form-control" name="note" />
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">分级信息：</label>
                <label class="col-xs-1 control-label">包含</label>
                <div class="col-xs-5">
                    <input class="form-control" name="grade" />
                </div>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 control-label">文件大小：</label>
                <label class="col-xs-1 control-label">720p 从</label>
                <div class="col-xs-2">
                    <input class="form-control" name="SHD1" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control" name="SHD2" />
                </div>
                <p class="form-control-static">MB</p>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-2 col-xs-offset-1 control-label">480p 从</label>
                <div class="col-xs-2">
                    <input class="form-control" name="HD1" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control" name="HD2" />
                </div>
                <p class="form-control-static">MB</p>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-1 col-xs-offset-2 control-label">360p 从</label>
                <div class="col-xs-2">
                    <input class="form-control" name="SD1" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control" name="SD2" />
                </div>
                <p class="form-control-static">MB</p>
            </div>
            <div class="form-group form-group-sm">
                <label class="col-xs-1 col-xs-offset-2 control-label">180p 从</label>
                <div class="col-xs-2">
                    <input class="form-control" name="FLOW1" />
                </div>
                <label class="col-xs-1 control-label">到</label>
                <div class="col-xs-2">
                    <input class="form-control" name="FLOW2" />
                </div>
                <p class="form-control-static">MB</p>
            </div>
            <div class="form-group">
                <div class="col-xs-offset-3">
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </div>
        </div>
        <script src="/resources/js/bootstrap-datetimepicker.min.js"></script>
        <script src="/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
        <script src="/resources/js/video-query.js"></script>
    </body>
</html>
