<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>创建周报</title>
    <link href="js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="js/jquery-ui/jquery-ui.theme.css" rel="stylesheet">
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/newReport.css"/>
</head>
<style type="text/css">
    .text input[type=text] {
        width: 95%;
        margin-left: 8px;
    }
</style>
<body>
<form method="post" id="report">
    <br/>

    <div align="center">
        <div style='width: 45%; padding-left: 0px;' align='left'>
            <jsp:useBean id="now" class="java.util.Date"/>
            <fmt:formatDate value="${now}" type="both" dateStyle="long"
                            pattern="yyyy" var="y"/>
            <select name="year" id="year">
                <option value="${y-1}" ${y-1 == year ? "selected" : ""}>${y-1}</option>
                <option value="${y}" ${y == year ? "selected" : ""}>${y}</option>
                <option value="${y+1}" ${y+1 == year ? "selected" : ""}>${y+1}</option>
            </select> 年 <select id="month" name="month">
            <c:forEach var="m" begin="1" end="12">
                <option value="${m}" ${m == month ? "selected" : ""}>${m}</option>
            </c:forEach>
        </select> 月 &nbsp;第<select name="issue" id="issue">
            <c:forEach var="i" begin="1" end="${countIssue}">
                <option value="${i}" ${i == issue ? "selected" : ""}>${i}</option>
            </c:forEach>
        </select> 期 &nbsp;&nbsp;<span id="timeZone"></span>
        </div>
        <br/>

        <div id="outerBox" align='left'>
            <hr>

            <h1 align="center">部门工作周报表</h1>

            <div style='width: 60%; padding-left: 0px;' align='left'>
                <h2>${groupName}&nbsp;${userName}</h2>
            </div>
            <c:forEach items="${tempPart}" var="t1" varStatus="s1">
                <c:set var="isDoing" value="0"/>
                <hr>
                <div id="innerBox">
                    <h3>${t1.partName}</h3>
                    <c:forEach items="${tempPoint}" var="t2" varStatus="s2">
                        <c:if test="${isDoing==0}">
                            <c:choose>
                                <c:when test="${t1.partId==t2.partId}">
                                    <div class="box">
                                        <p class="title" style="height: 20px;">${t2.pointName}
                                            <img src="js/dataTables/images/sort_asc.png" class="btn1"/>
                                            <img src="js/dataTables/images/sort_desc.png" class="btn2"/>
                                        </p>

                                        <div class="text">
                                            <table id="${s1.index}${s2.index}"
                                                   style='width: 100%; table-layout: fixed'>
                                                <colgroup>
                                                    <col style="width: 90%;"></col>
                                                    <col style="width: 5%;"></col>
                                                    <col style="width: 5%;"></col>
                                                </colgroup>

                                                <tr id="${s1.index}${s2.index}tRow0" class="CaseRow"
                                                    style="width:95%;height: 110px;">
                                                    <td><span class="left" style="vertical-align:top;font-size:20px;"><a
                                                            class="index">1</a>.</span>
                                                        <textarea maxlength="500" style="height: 100%;resize: none;"
                                                                  class="txt1" name="${t2.pointId}"></textarea></td>
                                                    <td class="add1"><img src="images/plus.png"
                                                                          name="add1"/></td>
                                                    <td class="remove1" style="display: none"><img
                                                            src="images/minus.png"
                                                            name="remove1"/></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <div style="width: 95%; height: 20%;border:1px solid #A9A9A9">
										<textarea name="${t1.partId}" maxlength="500"
                                                  style="width: 100%; height: 85%;resize: none;border-style:none; "
                                                  onkeyup="checkWord(this);"
                                                  onmousedown="checkWord(this);"></textarea>
                                        <span id="wordCheck" style="margin-left: 96%;color: red;">500</span>
                                    </div>

                                    <c:set var="isDoing" value="1"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </div>
            </c:forEach>
            <div id="timeBottom">${time}</div>
        </div>
        <br/>

        <div style='width: 40%;'>
            <input type='button' class="weekly-button" id="saveAndReturn"
                   value='保存'/>&nbsp; <input type='button' class="weekly-button" id="return" value='返回'/>&nbsp; <a
                id="msg" style="color: red; text-align: left;">
            <c:if test="${message!=''&&message!=null}">
                <fmt:message key="${message}"/>
            </c:if>
        </a>
        </div>
    </div>
    <input type=hidden id="contextPath"
           value=<%=request.getContextPath()%>/> <input type="hidden"
                                                        value="${userId}" id="user"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript"
            src="js/dataTables/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/newReport.js"></script>
</form>
</body>
</html>
