<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>周报对比</title>
    <link href="js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="js/jquery-ui/jquery-ui.theme.css" rel="stylesheet">
    <link rel="stylesheet" href="css/compareReport.css"/>
</head>
<style type="text/css">
    .text input[type=text] {
        width: 95%;
    }
</style>
<body>
<form method="post" id="report">

    <div align="center">
        <table style="width: 1200px;">
            <tr>
                <td><p style="font-size: 30px; color: #000000; font-family:宋体">${groupName}&nbsp;&nbsp;${userName}</p>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="width:50%"><p style="font-size: 25px; color: #000000; font-family:宋体">上周&nbsp;&nbsp;&nbsp;&nbsp;${preDateTime}</p>
                </td>
                <td><p style="font-size: 25px; color: #000000; font-family:宋体">本周&nbsp;&nbsp;&nbsp;&nbsp;${dateTime}</p>
                </td>
            </tr>
        </table>

        <table border="1" cellspacing="0" cellpadding="1"
               style="width: 1200px;border-left-style:none; border-bottom-style:none;border-right-style:none;border-top-style:none">
            <colgroup>
                <col style="width: 50%;"></col>
                <col style="width: 50%;"></col>
            </colgroup>
            <tr>
                <td valign="top">
                    <c:forEach items="${preTempPart}" var="t1" varStatus="s1">
                        <c:set var="isDoing" value="0"/>
                        <div style="margin-top:4px">
                            <h3>${t1.partName}</h3>

                            <c:forEach items="${preTempPoint}" var="t2" varStatus="s2">
                                <c:if test="${isDoing==0}">
                                    <c:choose>
                                        <c:when test="${t1.partId==t2.partId}">
                                            <p class="preTitle">${t2.pointName}</p>

                                            <div class="text" id="createPreTable${s2.index}">
                                                <c:set var="index" value="1" scope="page"/>
                                                <table class="main" style="width: 100%; table-layout: fixed"
                                                       id="oldTable${s2.index}">
                                                    <c:forEach items="${reportListOld}" var="list"
                                                               varStatus="s3">
                                                        <c:if
                                                                test="${list.partId==t2.partId && list.pointId==t2.pointId && list.workNote!=''&& list.workNote!=null}">
                                                            <tr id="tr${s3.index}">
                                                                <td style="padding-left:10px"><span class="index"
                                                                                                    style="vertical-align: top;font-size:20px"> <c:out
                                                                        value="${index}"/>.</span>
                                                                    <c:set var="index"
                                                                           value="${index + 1}" scope="page"/> <textarea
                                                                            name="${t2.pointId}" id="oldArea${s3.index}"
                                                                            readOnly="readOnly"
                                                                            style="font-size:15px;height:100px;width:95%;border:0px;overflow-y:hidden;resize:none;outline:none;"/>${list.workNote}</textarea>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${preReportListNoPoint}" var="list2">
											<textarea name="${t1.partId}" id="oldTextarea"
                                                      style="font-size:15px;margin-left:20px;width: 95%; height: 100px;overflow-y:hidden;border:0px;resize:none;outline:none;"
                                                      maxlength="500" readOnly="readOnly">${list2.workNote}</textarea>
                                            </c:forEach>
                                            <c:set var="isDoing" value="1"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:forEach>
                    <div id="timeBottom">${oldTime}</div>
                </td>
                <td valign="top">
                    <c:forEach items="${tempPart}" var="t1" varStatus="s1">
                        <c:set var="isDoing" value="0"/>

                        <div style="margin-top:4px">
                            <h3>${t1.partName}</h3>

                            <c:forEach items="${tempPoint}" var="t2" varStatus="s2">
                                <c:if test="${isDoing==0}">
                                    <c:choose>
                                        <c:when test="${t1.partId==t2.partId}">
                                            <p class="title">${t2.pointName}</p>

                                            <div class="text" id="createTable${s2.index}">
                                                <c:set var="index" value="1" scope="page"/>
                                                <table class="main" style="width: 100%; table-layout: fixed"
                                                       id="newTable${s2.index}">
                                                    <c:forEach items="${reportListNew}" var="list"
                                                               varStatus="s3">
                                                        <c:if
                                                                test="${list.partId==t2.partId && list.pointId==t2.pointId && list.workNote!=''&& list.workNote!=null}">
                                                            <tr>
                                                                <td style="padding-left:10px"><span class="index"
                                                                                                    style="vertical-align: top;font-size: 20px"> <c:out
                                                                        value="${index}"/>.</span>
                                                                    <c:set var="index"
                                                                           value="${index + 1}" scope="page"/> <textarea
                                                                            name="${t2.pointId}" id="newArea${s3.index}"
                                                                            readOnly="readOnly"
                                                                            style="font-size:15px;height:100px;width:95%;border:0px;resize:none;overflow-y:hidden;outline:none;"/>${list.workNote}</textarea>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                        </c:when>

                                        <c:otherwise>
                                            <c:forEach items="${reportListNoPoint }" var="list2">
											<textarea name="${t1.partId}" id="newTextarea"
                                                      style="font-size:15px;margin-left:20px;width: 95%; height: 100px;border:0px;overflow-y:hidden;resize:none;outline:none;"
                                                      maxlength="500" readOnly="readOnly">${list2.workNote}</textarea>

                                            </c:forEach>
                                            <c:set var="isDoing" value="1"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:forEach>
                    <div id="timeBottom">${time}</div>
                </td>
            </tr>
        </table>
    </div>
    <div style="width: 40%;margin-left:80%">
        <input class="weekly-button" type='button' value='返回' id="returnCompare" style="margin-top:4px"
               onclick="doReturnCompare()"/>
    </div>
</form>
<input type=hidden id="contextPath" value=<%=request.getContextPath()%>/>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="js/compareReport.js"></script>
</body>
</html>