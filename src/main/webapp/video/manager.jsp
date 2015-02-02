<%-- Document : manager Created on : 2015-1-22, 17:11:52 Author : 蔡迪旻 --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <meta name="format-detection" content="telephone=no">
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
        <link rel="stylesheet" href="/resources/css/iconFont.css">
        <link rel="stylesheet" href="/resources/css/app.css">
        <title>视频信息维护</title>
    </head>

    <body class="metro">
        <div id="navigate">
            <h1 class="no-margin"><a class="text-muted" href="javascript:window.history.back(); "><span class="icon-arrow-left-3 smaller on-right"></span></a></h1>
            <div class="breadcrumbs">
                <ul>
                    <li><a href="/platform"><span class="icon-home icon"></span>平台</a>
                    </li>
                    <li><a>视频管理</a>
                    </li>
                    <li class="active"><a>信息维护</a>
                    </li>
                    <li></li>
                </ul>
            </div>
        </div>
        <hr class="ntm nbm">
        <div id="search-toolbar" class="toolbar transparent margin5">`
            <button data-search='{"column":2,"input":"聊天室","regex":false}'><span class="icon-search on-left"></span>聊天室栏目</button>
            <button data-search='{"column":2,"input":"[^(聊天室)]","regex":true}'><span class="icon-search on-left"></span>其它栏目</button>
            <button data-search='{"column":6,"input":"从未发布","regex":false}'><span class="icon-search on-left"></span>新增信息</button>
            <span class="divider"></span>
            <button data-search='{"column":3,"input":"^[^0]\\d*$","regex":true}'><span class="icon-search on-left"></span>有序号</button>
            <button data-search='{"column":3,"input":"^0$","regex":true}'><span class="icon-search on-left"></span>无序号</button>
            <span class="divider"></span>
            <button data-search='{"column":7,"input":"^[^0]\\d*$","regex":true}'><span class="icon-search on-left"></span>推荐</button>
            <span class="divider"></span>
            <button data-search='{"column":8,"input":"发布","regex":false}'><span class="icon-search on-left"></span>已发布</button>
            <button data-search='{"column":8,"input":"存档","regex":false}'><span class="icon-search on-left"></span>已存档</button>
            <button data-search='{"column":8,"input":"审核","regex":false}'><span class="icon-search on-left"></span>待审核</button>
        </div>
        <div>
            <div id="edit-panel" class="span2 pos-abs" style="left: 300px">
                <div class="notice marker-on-bottom bg-white border bd-dark" style="padding: 1px">
                    <div class="fluent-menu">
                        <div class="tabs-content no-border" style="height: 80px">
                            <div class="tab-panel">
                                <div class="tab-panel-group">
                                    <a id="full-edit" class="fluent-big-button" href="/video/edit/"><span class="icon-newspaper"></span>完整编辑</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <table id="video-table" class="table striped hovered dataTable" frame="below">
                <thead>
                    <tr>
                        <th style="width: 70px;">编号</th>
                        <th style="width: auto;">标题</th>
                        <th style="width: 80px;">栏目</th>
                        <th style="width: 60px;">序号</th>
                        <th style="width: 80px;">时长</th>
                        <th style="width: 100px;">拍摄日期</th>
                        <th style="width: 156px;">发布/更新 时间</th>
                        <th style="width: 80px;">推荐度</th>
                        <th style="width: 60px;">状态</th>
                    </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <script src="/resources/js/jquery/jquery.min.js"></script>
        <script src="/resources/js/jquery/jquery.dataTables.min.js"></script>
        <script src="/resources/js/jquery/jquery.widget.min.js"></script>
        <script src="/resources/js/metro.min.js"></script>
        <script src="/resources/js/video/manager.js"></script>
    </body>
</html>