<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>周报系统维护页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <style>
        body {
            margin: 0;
            padding: 0 0 12px 0;
            font-size: 12px;
            line-height: 22px;
            font-family: "\5b8b\4f53", "Arial Narrow";
            background: #fff;
        }

        form, ul, li, p, h1, h2, h3, h4, h5, h6 {
            margin: 0;
            padding: 0;
        }

        input, select {
            font-size: 12px;
            line-height: 16px;
        }

        img {
            border: 0;
        }

        ul, li {
            list-style-type: none;
        }

        a {
            color: #00007F;
            text-decoration: none;
        }

        a:hover {
            color: #bd0a01;
            text-decoration: underline;
        }

        .box {
            width: 150px;
            margin-top: 20px;
            margin-left: 20px;
        }

        .menu {
            overflow: hidden;
            border-color: #C4D5DF;
            border-style: solid;
            border-width: 0 1px 1px;
        }

        /* lv1 */
        .menu li.level1 a {
            display: block;
            height: 28px;
            line-height: 28px;
            background: #EBF3F8;
            font-weight: 700;
            color: #5893B7;
            text-indent: 14px;
            border-top: 1px solid #C4D5DF;
        }

        .menu li.level1 a:hover {
            text-decoration: none;
        }

        .menu li.level1 a.current {
            background: #B1D7EF;
        }

        /* lv2 */
        .menu li ul {
            overflow: hidden;
        }

        .menu li ul.level2 {
            display: none;
        }

        .menu li ul.level2 li a {
            display: block;
            height: 28px;
            line-height: 28px;
            background: #ffffff;
            font-weight: 400;
            color: #42556B;
            text-indent: 18px;
            border-top: 0px solid #ffffff;
            overflow: hidden;
        }

        .menu li ul.level2 li a:hover {
            color: #f60;
        }
    </style>

</head>
<body>

<div style="margin-top:80px;margin-left:20px">
    <h2>
        <font color="blue" size="5">周报系统维护页面</font>
    </h2>
</div>
<div class="box">
    <ul class="menu">
        <li class="level1">
            <a href="" rel="external nofollow">系统维护</a>
            <ul class="level2">
                <li><a href="employee" rel="external nofollow">员工信息维护</a></li>
                <li><a href="initMaintenance" rel="external nofollow">添加事件维护</a></li>
            </ul>
        </li>
    </ul>
</div>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="js/reportGuide.js"></script>
</body>
</html>