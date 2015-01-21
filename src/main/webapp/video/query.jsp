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
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
        <link rel="stylesheet" href="/resources/css/iconFont.css">
        <link rel="stylesheet" href="/resources/css/app.css">
        <title>视频高级查询</title>
    </head>
    <body>
        <div class="panel inline-block margin5">
            <div class="panel-header bg-lightBlue fg-white">基础条件</div>
            <div class="panel-content">
                <div>
                    <h5>文件编号</h5>
                    <span>从</span>
                    <div class="input-control text size2">
                        <input type="text" name="file" placeholder="小"><button class="btn-clear"></button>
                    </div>
                    <span>到</span>
                    <div class="input-control text size2">
                        <input type="text" name="file2" placeholder="大"><button class="btn-clear"></button>
                    </div>
                </div>
                <div>
                    <h5>标题：</h5>
                    <div class="input-control text">
                        <input type="text" name="title" placeholder="包含字符"><button class="btn-clear"></button>
                    </div>
                </div>
                <div>
                    <h5>视频时长：</h5>
                    <span>从</span>
                    <div class="input-control text size2">
                        <input class="form-control" name="duration" placeholder="短（分钟）"><button class="btn-clear"></button>
                    </div>
                    <span>到</span>
                    <div class="input-control text size2">
                        <input class="form-control" name="duration2" placeholder="长（分钟）"><button class="btn-clear"></button>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel inline-block margin5">
            <div class="panel-header bg-emerald fg-white">扩展条件</div>
            <div class="panel-content">
                <h5>拍摄日期：</h5>
                <span>从</span>
                <div class="input-control text size2" data-role="datepicker" data-format="yyyy年mm月dd日" data-locale="zhCN">
                    <input type="text" name="shootTime" placeholder="早"><button class="btn-date"></button>
                </div>
                <span>到</span>
                <div class="input-control text size2" data-role="datepicker" data-format="yyyy年mm月dd日" data-locale="zhCN">
                    <input type="text" name="shootTime2" placeholder="晚"><button class="btn-date"></button>
                </div>
                <h5>发布时间：</h5>
                <span>从</span>
                <div class="input-control text size2" data-role="datepicker" data-format="yyyy年mm月dd日" data-locale="zhCN">
                    <input type="text" name="pubDate" placeholder="早"><button class="btn-date"></button>
                </div>
                <span>到</span>
                <div class="input-control text size2" data-role="datepicker" data-format="yyyy年mm月dd日" data-locale="zhCN">
                    <input type="text" name="pubDate2" placeholder="晚"><button class="btn-date"></button>
                </div>
                <h5>发布序号：</h5>
                <span>从</span>
                <div class="input-control text size2">
                    <input type="text" name="sort" placeholder="小"><button class="btn-clear"></button>
                </div>
                <span>到</span>
                <div class="input-control text size2">
                    <input type="text" name="sort2" placeholder="大"><button class="btn-clear"></button>
                </div>
            </div>
        </div>
        <div class="panel inline-block margin5">
            <div class="panel-header">其它</div>
            <div class="panel-content">
                <h5>描述信息：</h5>
                <div class="input-control text size4">
                    <input type="text" name="descrpition" placeholder="包含字符"><button class="btn-clear"></button>
                </div>
                <h5>备注信息：</h5>
                <div class="input-control text">
                    <input type="text" name="note" placeholder="包含字符"><button class="btn-clear"></button>
                </div>
                <h5>分级信息：</h5>
                <div class="input-control text">
                    <input type="text" name="grade" placeholder="包含字符"><button class="btn-clear"></button>
                </div>
            </div>
        </div>
        <div class="panel inline-block margin5">
            <div class="panel-content">
                <span>以</span>
                <div class="input-control select">
                    <select>
                        <option value="id">默认</option>
                        <option value="file">文件编号</option>
                        <option value="duration">视频时长</option>
                        <option value="sort">发布序号</option>
                        <option value="shootTime">拍摄日期</option>
                        <option value="pubDate">发布时间</option>
                    </select>
                </div>
                <span>字段</span>
                <div class="input-control select">
                    <select>
                        <option value="false">升序</option>
                        <option value="true">降序</option>
                    </select>
                </div>
                <span>排列</span>
            </div>
        </div>
        <hr>
        <div>
            <button  type="submit" class="default large place-right">
                <i class="fa fa-filter"></i>
                提交查询
            </button>
        </div>
        <script src="/resources/js/metro.min.js"></script>
        <script src="/resources/js/video/query.js"></script>
    </body>
</html>
