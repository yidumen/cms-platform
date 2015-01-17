<%-- 
    Document   : main
    Created on : 2014-10-14, 15:44:06
    Author     : 蔡迪旻
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/metro-bootstrap.css">
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
    <body class="metro">
        <nav class="navigation-bar white border">
            <div class="navigation-bar-content">
                <a class="element" href="/platform">易度门管理平台</a>
                <ul class="element-menu">
                    <c:forEach var="menu" items="${menus}">
                        <li>
                            <a href="#" class="dropdown-toggle">${menu.name}</a>
                            <ul class="dropdown-menu" data-role="dropdown">
                                <c:forEach var="group" items="${menu.items}">
                                    <c:if test="${group.permission >= permission}">
                                        <li class="menu-title">${group.name}</li>
                                            <c:forEach var="item" items="${group.items}">
                                            <li><a href="${item.uri}">${item.name}</a></li>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </nav>
        <div id="container" class="container">
        </div>
        <script src="/resources/js/jquery/jquery.min.js"></script>
        <script src="/resources/js/cms.js"></script>
        <script src="/resources/js/jquery/jquery.widget.min.js"></script>
        <script src="/resources/js/metro.min.js"></script>
    </body>
</html>
