<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>修改周报</title>
    <link href="js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="js/jquery-ui/jquery-ui.theme.css" rel="stylesheet">
    <link href="js/dataTables/css/jquery.dataTables.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/newReport.css"/>
    <style type="text/css">
    </style>
</head>

<body>
<br/>

<form id="editReport" method="post">
    <div align="center">

        <div style='width: 45%; padding-left: 0px;' align='left'>
            <input id="year" style="width: 50px; text-align: center" type="text"
                   name="year" value="${year}" readOnly="readonly"/>年 <input
                id="month" style="width: 50px; text-align: center" type="text"
                name="month" value="${month}" readOnly="readonly"/>月 &nbsp;第<input
                id="issue" style="width: 50px; text-align: center" type="text"
                name="issue" value="${issue}" readOnly="readonly"/>期 &nbsp;&nbsp;<span
                id="timeZone" style="width: 300px; display: inline-block;"></span>
        </div>
        <br/>

        <div id="outerBox" align='left'>
            <hr
                    style="height: 1px; border: none; border-top: 1px solid #000000;"/>
            <h1 align="center">部门工作周报表</h1>

            <div style='width: 60%; padding-left: 0px;' align='left'>
                <h2>${groupName}&nbsp;${userName}</h2>
            </div>
            <c:forEach items="${tempPart}" var="t1" varStatus="s1">
                <c:set var="isDoing" value="0"/>
                <hr
                        style="height: 1px; border: none; border-top: 1px solid #000000;"/>
                <div id="innerBox">
                    <h3>${t1.partName}</h3>

                    <c:forEach items="${tempPoint}" var="t2" varStatus="s2">
                        <c:if test="${isDoing==0}">
                            <c:choose>
                                <c:when test="${t1.partId==t2.partId}">
                                    <p class="title">${t2.pointName}
                                        <img src="js/dataTables/images/sort_asc.png" class="btn1"/>
                                        <img src="js/dataTables/images/sort_desc.png" class="btn2"/>
                                    </p>

                                    <div class="text">
                                        <c:set var="index" value="1" scope="page"/>
                                        <table id="table${s1.index}${s2.index}"
                                               style='width: 100%; table-layout: fixed'>
                                            <colgroup>
                                                <col style="width: 90%;"></col>
                                                <col style="width: 5%;"></col>
                                                <col style="width: 5%;"></col>
                                            </colgroup>
                                            <c:forEach items="${reportListNew}" var="list"
                                                       varStatus="s3">

                                                <c:if
                                                        test="${list.partId==t2.partId && list.pointId==t2.pointId && list.workNote!=''&& list.workNote!=null}">
                                                    <tr id="${s3.index}tRow" class="CaseRow">
                                                        <td><span class="left"
                                                                  style="vertical-align: top; font-size: 20px;"> <a
                                                                class="index"><c:out value="${index}"/></a>.
															</span> <c:set var="index" value="${index + 1}"
                                                                           scope="page"/>
																<textarea style="width: 95%; height: 110; resize: none;"
                                                                          name="${t2.pointId}">${list.workNote}</textarea>
                                                        </td>
                                                        <td class="add1" style="display: none;"></td>
                                                        <td class="remove1" style="display: none;"></td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                        </table>
                                        <table id="${s1.index}${s2.index}"
                                               style='width: 100%; table-layout: fixed'>
                                            <colgroup>
                                                <col style="width: 90%;"></col>
                                                <col style="width: 5%;"></col>
                                                <col style="width: 5%;"></col>
                                            </colgroup>
                                            <tr id="${s1.index}${s2.index}tRow0" class="CaseRow">
                                                <td><span class="left"
                                                          style="vertical-align: top; font-size: 20px;"><a
                                                        class="index"><c:out value="${index}"/></a>.</span> <textarea
                                                        style="width: 95%; height: 110;"
                                                        class="txt1" name="${t2.pointId}"></textarea></td>
                                                <td class="add1"><img src="images/plus.png"
                                                                      name="add1"/></td>
                                                <td class="remove1" style="display: none;"><img
                                                        src="images/minus.png" name="remove1"/></td>
                                            </tr>
                                        </table>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <div style="width: 95%; height: 20%;border:1px solid #A9A9A9">
                                        <c:forEach items="${reportListNoPoint }" var="list2">
											<textarea name="${t1.partId}"
                                                      style="width: 100%; height: 85%;resize: none;border-style:none; "
                                                      maxlength="500" onkeyup="checkWord(this);"
                                                      onmousedown="checkWord(this);">${list2.workNote}</textarea>
                                            <span id="wordCheck" style="margin-left: 96%;color: red;">500</span>

                                        </c:forEach>
                                    </div>
                                    <c:set var="isDoing" value="1"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </div>
            </c:forEach>
            <div
                    style="text-align: right; margin-right: 10px; margin-bottom: 10px;">${time}</div>
        </div>
        <br/>

        <div style='width: 40%;'>
            <input type='button' id="editAndReturn" value='保存'
                   class="weekly-button"/>&nbsp; <input type='button'
                                                        class="weekly-button" id="return" value='返回'/>&nbsp; <input
                type="button" id="downLoad" value='下载' class="weekly-button"/>
            <a id="msg" style="color: red; text-align: left;"> <c:if
                    test="${message!=''&&message!=null}">
                <fmt:message key="${message}"/>
            </c:if>
            </a>
        </div>
    </div>
    <input type=hidden id="contextPath"
           value=<%=request.getContextPath()%>/> <input type="hidden" value="${userId}" id="user"/> <input type="hidden"
                                                                                                           value="${fileNo}"
                                                                                                           name="fileNo"/>
    <input
            type="hidden"
            name="fileNos" id="fileNos" value="${fileNo}"/> <input
        type="hidden" name="deptId" value="${deptId}"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript"
            src="js/dataTables/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/newReport.js"></script>
</form>
</body>
</html>
