<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>员工信息维护页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="js/jquery-ui/jquery-ui.theme.css" rel="stylesheet">
    <link href="js/dataTables/css/jquery.dataTables.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/employee.css"/>
</head>

<body>
<form method="post" id="employee">

    <div>
        <table id="selectTable" align="center">
            <colgroup>
                <col style="width: 10%;"></col>
                <col style="width: 15%;"></col>
                <col style="width: 10%;"></col>
                <col style="width: 15%;"></col>
                <col style="width: 20%;"></col>
            </colgroup>
            <tr>
                <td align="right">员工号:</td>
                <td><input name="userId" type="text" value="${userId}"/></td>
                <td width="200px" style="color: red;">
                </td>
            </tr>
            <tr>
                <td align="right">员工姓名:</td>
                <td><input name="userName" type="text" value="${userName}"/></td>
                <td align="right">部门:</td>
                <td><select name="deptId" class="select_box" id="deptId">
                    <c:forEach items="${deptList}" var="ic">
                        <option value="${ic.groupId}"
                            ${groupId == ic.groupId ? "selected" : ""}>${ic.groupName}</option>
                    </c:forEach>
                </select></td>
                <td align="right">
                    <input class="weekly-button" id="search" type="button" value="查询"></td>
            </tr>
        </table>
    </div>
    <div style="height: 73%">
        <table id="employeeList" class="display">
            <thead>
            <tr>

                <th>员工号</th>
                <th>员工姓名</th>
                <th>所属部门</th>
                <th>修改</th>
                <th>删除</th>
                <th style="display:none"></th>
                <th style="display:none"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userInfoList}" var="list" varStatus="status">
                <tr>
                    <td>${list.userId}</td>
                    <td>${list.userName}</td>
                    <td>${list.groupName}</td>
                    <td><input type="button" class="weekly-button edit" value="修改"></td>
                    <td><input type="button" class="weekly-button delete" value="删除"></td>
                    <td style="display:none">${list.groupId}</td>
                    <td style="display:none">${list.authority}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="btnReport">
        <input type='button' id="return" value='返回' class="weekly-button"/>
        <input type="button" value="新建" onclick="doAddEmployee()"
               class="weekly-button"/>
        <a style="color: red;"><c:if test="${message!=''&&message!=null}">
            <fmt:message key="${message}"/>
        </c:if></a>
    </div>

    <input type="hidden" id="contextPath" value=<%=request.getContextPath()%>/>
    <input type="hidden" id="flag" name="flag"/>
    <input type="hidden" id="userNo" name="userNo"/>
    <%--当前登录用户ID--%>
    <input type="hidden" id="loginUserId" value="${session_user_info.userId}"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript"
            src="js/jquery-ui/jquery-ui-i18n-mod.js"></script>
    <script type="text/javascript"
            src="js/dataTables/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.js"></script>
    <script type="text/javascript" src="js/employee.js"></script>


    <!--新增、编辑员工窗口-->
    <div id="divLoginWindow">
        <table id="00" style="width: 100%;" border="0" cellpadding="2"
               cellspacing="0">
            <colgroup>
                <col style="width: 20%;"></col>
                <col style="width: 45%;"></col>
            </colgroup>
            <tr style="background-color: #4C8DDC; border-bottom: #4C8DDC solid 2px">
                <td style="height: 35px;">&nbsp;添加员工</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>

            <tr>
                <td align="right">员工号:*</td>
                <td><input type="text" name="employeeId" id="employeeId"/></td>
            </tr>
            <tr>
                <td align="right">员工姓名:*</td>
                <td><input type="text" name="employeeName" id="employeeName"/></td>
            </tr>
            <tr>
                <td align="right">所属部门:</td>
                <td><select name="groupId" class="select_box" id="groupId">
                    <c:forEach items="${deptList}" var="ic">
                        <option value="${ic.groupId}"
                            ${groupId == ic.groupId ? "selected" : ""}>${ic.groupName}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td align="right">权限:</td>
                <td><select name="authority" class="select_box" id="authority">
                    <c:forEach items="${authorityList}" var="list">
                        <option value="${list.authorityId}">${list.authorityName}</option>
                    </c:forEach>
                </select></td>
            </tr>
        </table>
        <br/>

        <div id="bb" style="margin-left: 50px">
            <input type='button' id="saveBtn" value='保存' class="weekly-button"/>&nbsp;
            <input type="button" value='清空' id="clearBtn" class="weekly-button"/>&nbsp;
            <input type="button" value='关闭' id="closeBtn" class="weekly-button"/>
        </div>

    </div>
</form>
</body>
</html>
