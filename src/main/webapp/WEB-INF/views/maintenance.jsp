<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>周报维护页</title>
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

        #btnEditReport {
            margin-top: 20px;
        }

        input[type=text] {
            width: 80%;
        }

        select {
            width: 80%;
        }

    </style>

</head>

<body>
<form method="post" id="searchEvent">
    <h2>
        <font color="blue" size="5">事件维护页面</font>
    </h2>
    <br/>

    <div style="height: 73%">
        <table id="reportList" class="display">
            <thead>
            <tr>
                <th>起始时间</th>
                <th>截止时间</th>
                <th style="width: 757px">事件说明</th>
                <th style="width: 37px">修改</th>
                <th style="width: 37px">删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${eventList}" var="list" varStatus="status">
                <tr>
                    <td><fmt:formatDate pattern="yyyy/MM/dd"
                                        value="${list.beginDate}"/></td>
                    <td><fmt:formatDate pattern="yyyy/MM/dd"
                                        value="${list.endDate}"/></td>
                    <td>${list.commentValue}</td>
                    <td><input class="weekly-button" type="button"
                               class="weekly-button" value="修改"
                               onclick="doEventEdit()"></td>
                    <td><input class="weekly-button" type="button"
                               class="weekly-button" value="删除"
                            ></td>
                </tr>

            </c:forEach>
            </tbody>
        </table>
    </div>
    <div>
        <input type='button' id="return" value='返回' class="weekly-button"/>
        <input type="button" value="添加事件" onclick="doAddEvent()"
               class="weekly-button"/> <a style="color: red;"><c:if
            test="${message!=''&&message!=null}">
        <fmt:message key="${message}"/>
    </c:if></a>
    </div>
    <input type=hidden
           id="contextPath" value=<%=request.getContextPath()%>/>
    <input type="hidden" name="deleteEventStartDate" id="deleteEventStartDate"/>
    <input type="hidden" name="deleteEventEndDate" id="deleteEventEndDate"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript"
            src="js/jquery-ui/jquery-ui-i18n-mod.js"></script>
    <script type="text/javascript"
            src="js/dataTables/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/maintenance.js"></script>
    <div id="divLoginWindow">

        <table id="divLoginTable" style="width: 100%;" border="0" cellpadding="2"
               cellspacing="0">
            <colgroup>
                <col style="width: 10%;"></col>
                <col style="width: 15%;"></col>
                <col style="width: 10%;"></col>
                <col style="width: 15%;"></col>
            </colgroup>
            <tr
                    style="background-color: #4C8DDC; border-bottom: #4C8DDC solid 2px">
                <td style="height: 35px;">&nbsp;添加事件</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td align="left">&nbsp;开始日期:</td>
                <td><input name="eventStartDate" value="${initStartDate}"
                           id="datepicker3" class="select_box" type="text"
                           readOnly="readonly"></td>
                <td align="left">结束日期:</td>
                <td><input name="eventEndDate" value="${initEndDate}"
                           id="datepicker4" class="select_box" type="text"
                           readOnly="readonly"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><span style="font-size: 12px; color: red;">（只能输入周一对应的日期）</span></td>
                <td>&nbsp;</td>
            </tr>
        </table>
        &nbsp;事件说明：<br/>
			<textarea name="eventTextArea" maxlength="500"
                      style="width: 91%; height: 100px; resize: none; margin-left: 20px; margin-top: 12px;"></textarea>

        <div id="btnReport" style="margin-left: 300px">
            <input type='button' id="saveBtn" value='保存' class="weekly-button"/>&nbsp;
            <input type="button" value='清空' id="clearBtn" class="weekly-button"/>&nbsp;
            <input type="button" value='关闭' id="closeBtn" class="weekly-button"/>
        </div>

    </div>

    <div id="divEditWindow">

        <table id="divEditTable" style="width: 100%;" border="0" cellpadding="2"
               cellspacing="0">
            <colgroup>
                <col style="width: 10%;"></col>
                <col style="width: 15%;"></col>
                <col style="width: 10%;"></col>
                <col style="width: 15%;"></col>
            </colgroup>
            <tr
                    style="background-color: #4C8DDC; border-bottom: #4C8DDC solid 2px">
                <td style="height: 35px;">&nbsp;修改事件</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td align="left">&nbsp;开始日期:</td>
                <td><input name="eventEditStartDate" value="${initStartDate}"
                           id="datepicker5" class="select_box" type="text"
                           readOnly="readonly"></td>
                <td align="left">结束日期:</td>
                <td><input name="eventEditEndDate" value="${initEndDate}"
                           id="datepicker6" class="select_box" type="text"
                           readOnly="readonly"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><span style="font-size: 12px; color: red;">（只能输入周一对应的日期）</span></td>
                <td>&nbsp;</td>
            </tr>
        </table>
        &nbsp;事件说明：<br/>
			<textarea name="eventEditTextArea" id="eventEditTextArea" maxlength="500"
                      style="width: 91%; height: 100px; resize: none; margin-left: 20px; margin-top: 12px;"></textarea>

        <div id="btnEditReport" style="margin-left: 300px">
            <input type='button' id="saveEditBtn" value='保存' class="weekly-button"/>&nbsp;
            <input type="button" value='关闭' id="closeEditBtn" class="weekly-button"/>
        </div>

    </div>
</form>
</body>
</html>
