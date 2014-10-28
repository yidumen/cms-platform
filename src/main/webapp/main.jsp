<%-- 
    Document   : main
    Created on : 2014-10-14, 15:44:06
    Author     : 蔡迪旻
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="/resources/css/font-awesome.min.css">
        <link rel="stylesheet" href="/resources/css/default.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <title>易度门管理平台</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <span class="navbar-brand">易度门管理平台</span>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">视频管理 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li role="presentation" class="dropdown-header">信息查询</li>
                            <li><a href="/video/manager">全部视频列表</a></li>
                            <li><a href="/video/query">高级条件查询</a></li>
                            <li role="presentation" class="divider"></li>
                            <li role="presentation" class="dropdown-header">发布与维护</li>
                            <li><a href="/video/create">添加新视频信息</a></li>
                            <li><a href="/video/publish">发布视频</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <div id="container" class="container-fluid">
        </div>
        <div id="block" class="modal" role="dialog">
            <div class="modal-dialog modal-sm block">
                <div class="modal-content">
                    <div class="modal-body">
                        <span class="icon-spin icon-spinner icon-2x"></span> 请稍候，正在向服务器请求数据... ...
                    </div>
                </div>
            </div>
        </div>
        <script src="/resources/js/jquery-2.1.1.min.js"></script>
        <script src="/resources/js/bootstrap.min.js"></script>
        <script src="/resources/js/cms.js"></script>
    </body>
</html>
