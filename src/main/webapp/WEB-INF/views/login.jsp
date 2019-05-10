<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/login.css"/>
    <title>周报管理系统</title>
</head>
<body>
<form id="loginForm" method="post" action="loginCheck">
    <div id="sysName">
        <h2>周报管理系统</h2>
    </div>
    <div id="login">
        <label>员工号：</label>
			<span>
				<input id="userId" name="userId" type="text" size="25" value="00"/>
			</span>
        <span class="weekly-error" style="display: none;">${message}</span>
    </div>
    <div class="button">
        <input type="hidden" id="message" name="message" value="${message}">
        <input id='log' type="submit" class="weekly-button" value="员工登录"/>
    </div>
    <br/>
</form>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>

</body>
</html>
