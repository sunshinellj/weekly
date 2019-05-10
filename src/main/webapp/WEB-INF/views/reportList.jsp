<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>周报查询页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="js/jquery-ui/jquery-ui.theme.css" rel="stylesheet">
    <link href="js/dataTables/css/jquery.dataTables.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/reportList.css"/>

    <style type="text/css">
        h2 {
            margin: 0;
            padding: 0;
        }

        #reportList td {
            text-align: center;
        }

        #btnReport {
            margin-top: 20px;
        }

        input[type=text] {
            width: 80%;
        }

        select {
            width: 80%;
        }

        #selectTable {
            width: 1000px;
            margin-left: 280px;
        }
    </style>

</head>

<body>
<form method="post" id="searchReport">
    <h2>
        <font color="blue" size="5">欢迎 ${userName} 进入周报系统</font>
    </h2>

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
                <td><input name="rptId"  type="text"/>
                    <%--<input name="rptId" value="${reporterId}" type="text"/>--%>
                </td>

                <td width="200px" style="color: red;"><c:if
                        test="${msgs!=''&&msgs!=null}">
                    <fmt:message key="${msgs}"/>
                </c:if></td>
            </tr>
            <tr>
                <td align="right">员工姓名:</td>
                <td>
                    <%--<input name="rptName" value="${reporter}" type="text"/>--%>
                    <input name="rptName"  type="text"/>
                </td>

                <td align="right">部门:</td>
                <td><select name="deptId" class="select_box" id="deptId">
                    <c:forEach items="${deptList}" var="ic">
                        <option value="${ic.groupId}"
                            ${groupId == ic.groupId ? "selected" : ""}>${ic.groupName}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td align="right">开始日期:</td>
                <td><input name="startDate" value="${startDate}"
                           id="datepicker1" class="select_box" type="text"
                           readOnly="readonly"></td>
                <td align="right">结束日期:</td>
                <td><input name="endDate" value="${endDate}" id="datepicker2"
                           class="select_box" type="text" readOnly="readonly"></td>
                <td align="right"><input class="weekly-button" id="search"
                                         type="button" value="查询"></td>
                <td align="right"><input class="weekly-button" id="logout"
                                         type="button" value="退出"></td>
            </tr>
        </table>
    </div>
    <div style="height: 73%">
        <table id="reportList" class="display">
            <thead>
            <tr>
                <th><input id="checkOrder" onclick="doCheck()"
                           type="checkbox"/></th>
                <th>周报用户</th>
                <th>所属部门</th>
                <th>创建时间</th>
                <th>文件</th>
                <th>比较</th>
                <th>修改</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportList}" var="list" varStatus="status">
                <tr>
                    <td><input class="check" type="checkbox" name="chekBox"
                               value="${list.fileNo}"/></td>
                    <td>${list.reporter}</td>
                    <td>${list.groupName}</td>
                    <td><fmt:formatDate pattern="yyyy/MM/dd"
                                        value="${list.reportTime}"/></td>
                    <td>${list.fileName}</td>
                    <td><input type="button" class="weekly-button" value="比较"
                               onclick="doReportCompare(${list.fileNo})"/></td>
                    <td><input class="weekly-button" type="button"
                               class="weekly-button" value="修改"
                               onclick="doReportEdit(${list.fileNo})"></td>
                    <td><input class="weekly-button" type="button"
                               class="weekly-button" value="删除"
                               onclick="doDelete(${list.fileNo})"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="btnReport">
        <input type='button' id="new" value='创建周报' class="weekly-button"/>&nbsp;
        <c:if test="${authority=='1' or authority=='2'}">
            <input type="button" value='上传模板' onclick="doInitUpload()"
                   class="weekly-button"/>&nbsp;
            <input type="button" value='合并周报' onclick="doCombine()"
                   class="weekly-button"/>&nbsp;
            <input type="button" value="系统维护" onclick="doReportGuide()"
                   class="weekly-button"/>
            <a style="color: red;"><c:if
                    test="${msgTemp!=''&&msgTemp!=null}">
                <fmt:message key="${msgTemp}"/>
            </c:if></a>
        </c:if>
    </div>

    <input type="hidden" name="fileNo" id="fileId"/> <input
        type="hidden" name="fileNos" id="fileNos"/> <input type=hidden
                                                           id="contextPath" value=<%=request.getContextPath()%>/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript"
            src="js/jquery-ui/jquery-ui-i18n-mod.js"></script>
    <script type="text/javascript"
            src="js/dataTables/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/report.js"></script>
</form>
</body>
</html>
