<%-- 
    Document   : video-manager
    Created on : 2014-10-17, 17:12:47
    Author     : 蔡迪旻
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>视频列表</title>
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
        <link rel="stylesheet" href="/resources/css/iconFont.css">
        <link rel="stylesheet" href="/resources/css/app.css">
        <link rel="stylesheet" href="/resources/css/video-info.css">
    </head>
    <body class="metro">
        <h1 class="no-margin text-muted"><a href="javascript:window.history.back(); "><span class="icon-arrow-left-3 smaller on-right"></span></a></h1>
        <div id="breadcrumb" class="breadcrumbs">
            <ul>
                <li><a href="/platform"><span class="icon-home icon"></span>平台</a></li>
                <li><a>视频管理</a></li>
                <li class="active"><a>所有视频信息</a></li>
                <li></li>
            </ul>
        </div>
        <hr class="ntm">
        <div>
            <table id="video-table" class="table striped hovered dataTable" frame="below">
                <thead>
                    <tr>
                        <th style="width: 56px;">编号</th>
                        <th style="width: auto;">标题</th>
                        <th style="width: 48px;">序号</th>
                        <th style="width: 80px;">时长</th>
                        <th style="width: 100px;">拍摄日期</th>
                        <th style="width: 156px;">发布/更新 时间</th>
                        <th style="width: 30px;">荐</th>
                        <th style="width: 48px;">状态</th>
                        <th style="width: 100px;">操作</th>
                    </tr>
                </thead>
                <tbody id="table-content"></tbody>
            </table>
            <div id="amodal" class="hide">
                <table cellpadding="10">
                    <tr>
                        <td><strong>记录标识：</strong></td><td id="itemId" class="text-muted"></td>
                        <td><strong>视频编号：</strong></td><td id="file" class="text-muted"></td>
                        <td><strong>播放时长：</strong></td><td id="dur" class="text-muted"></td>
                    </tr>
                    <tr>
                        <td><strong>发布序号：</strong></td><td id="sort" class="text-muted"></td>
                        <td><strong>分级信息：</strong></td><td id="grade" class="text-muted"></td>
                        <td><strong>拍摄日期：</strong></td><td id="shoot" class="text-muted"></td>
                    </tr>
                    <tr>
                        <td class="text-right"><strong>推荐度：</strong></td><td id="recommend" class="text-muted"></td>
                        <td><strong>当前状态：</strong></td><td id="status" class="text-muted"></td>
                        <td><strong>发布时间：</strong></td><td id="pub" class="text-muted"></td>
                    </tr>
                    <tr>
                        <td class="text-right"><strong>所属栏目：</strong></td><td id="col" class="text-muted" colspan="5"></td>
                    </tr>
                    <tr>
                        <td class="text-right"><strong>标签：</strong></td><td id="tag" class="text-muted" colspan="5"></td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top"><strong>视频描述：</strong></td><td id="desc" class="text-muted" colspan="5"></td>
                    </tr>
                    <tr>
                        <td class="text-right" style="vertical-align: top"><strong>备注：</strong></td><td id="note" class="text-muted" colspan="5"></td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top"><strong>文件信息：</strong></td><td colspan="5">
                            <table class="table bordered">
                                <thead>
                                    <tr>
                                        <td>清晰度</td>
                                        <td>文件大小</td>
                                    </tr>
                                </thead>
                                <tbody id="ext" class="text-muted"></tbody>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <script src="/resources/js/jquery/jquery.min.js"></script>
        <script src="/resources/js/jquery/jquery.dataTables.min.js"></script>
        <script src="/resources/js/jquery/jquery.widget.min.js"></script>
        <script src="/resources/js/metro.min.js"></script>
        <script src="/resources/js/video-manager.js"></script>
    </body>
</html>
