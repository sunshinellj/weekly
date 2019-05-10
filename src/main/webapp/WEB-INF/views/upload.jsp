<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
    <title>周报模板上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="js/jquery-ui/jquery-ui.theme.css" rel="stylesheet">
    <link href="js/dataTables/css/jquery.dataTables.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="css/common.css"/>
    <style type="text/css">
        #reportList td {
            text-align: center;
        }

        #bottom {
            position: absolute;
        }

        #file {
            width: 226px;
            height: 25px;
            position: relative;
        }

        #filetxt {

        }

        #filebtn {
            position: absolute;
        }

        #myfile {
            width: 60.67px;
            right: 5px;
            left: 180px;
            top: 0px;
            opacity: 0;
        }
    </style>
</head>

<body>

<form action="doUpload" method="post" id="uploadTemplate" name="uploadTemplate" enctype="multipart/form-data">
    <h2><font style="display:block; text-align:center" color="blue" size="5">周报模板上传</font></h2>

    <div style="margin-left:400px;margin-top:80px;width:1000px">
        <table>
            <colgroup>
                <col style="width: 10%;"></col>
                <col style="width: 5%;"></col>
                <col style="width: 20%;"></col>
                <col style="width: 20%;"></col>
                <col style="width: 40%;"></col>
            </colgroup>
            <tr>
                <td style="text-align: right">工作组:</td>
                <td><select style="width: 150px;" name="deptId"
                            class="select_box">
                    <c:forEach items="${deptList}" var="ic">
                        <option value="${ic.groupId}"
                            ${groupId == ic.groupId ? "selected" : ""}>${ic.groupName}</option>
                    </c:forEach>
                </select></td>
                <td style="width:33%"><input id="filetxt" type="text" value="未选择文件"/> <input class="weekly-button"
                                                                                             id="filebtn" type="button"
                                                                                             value="浏览"/> <input
                        id="myfile" name="myfile" type="file" accept="file/doc"/>
                </td>
                <td><font color="red" size="3"
                          style="WIDTH: 250px; text-align:left">${message}</font></td>
            </tr>
        </table>
    </div>
    <div style="margin-left:200px;width:1000px">
        <table align="center">
            <tr>
                <td>&nbsp;</td>
                <td><input class="weekly-button" name="upload" type="submit" value="上传周报" style="margin-left:80px;"/>

                    <input class="weekly-button" type="button" name="return" value="返回" onclick="doReturn()"/></td>
            </tr>
            <tr>
                <td style="width: 230px;">&nbsp;</td>
                <td>
                </td>

            </tr>

        </table>
    </div>
</form>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<input type=hidden id="contextPath" value=<%=request.getContextPath()%>/>
<script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript"
        src="js/dataTables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/report.js"></script>
<script type="text/javascript" src="js/template.js"></script>
</body>
</html>