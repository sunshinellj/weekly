 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<link rel="stylesheet" href="css/common.css" />
	<link rel="stylesheet" href="css/template.css" />	
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>周报模板上传成功</title>

</head>
<body>
<div align="center"><h2><font color="blue" size="3"> ${fileName}上传成功！</font></h2></div>
<form method="post" id="saveTemplateForm">
	<div align="center">
		<div style='width: 40%; padding-left: 0px;' align='left'>
			<h4>${selectedGroup}</h4>
		<input type="hidden" name="sGroup" id="sGroup" value="${selectedGroup}">
		<input type="hidden" name="sId" id="sId" value="${selectedId}">
		</div>
	</div>
		<div align="center">	
		<div id="outerBox" align='left'>
				<c:forEach items="${tempPart}" var="t1" varStatus="s1">
					<c:set var="isDoing" value="0" />
					<hr
						style="height: 1px; border: none; border-top: 1px solid #000000;" />
					<div id="innerBox">
					<div class="lablePartBox" id="lablePart${s1.index}">
						<h3>${t1.partName}</h3>
					</div>
					<div class="textPartBox" id="textPart${s1.index}">
						<h3><input type="text" name="textPart${s1.index}" id="textPart${s1.index}" value="${t1.partName}" maxlength="50" onkeyup="if(this.value.length>50) alert('最多只能输入50个字');"
						/></h3>
					</div>	
					<c:forEach items="${tempPoint}" var="t2" varStatus="s2">
						<c:if test="${isDoing==0}">
							<c:choose>
								<c:when test="${t1.partId==t2.partId}">
									<div class="lablePointBox" id="lablePoint${s2.index}">
										<p>${t2.pointName}</p>
									</div>
									<div class="textPointBox" id="textPoint${s2.index}">
										<input type="text" name="textPoint${s2.index}" id="textPoint${s2.index}" value="${t2.pointName}" maxlength="50" onkeyup="if(this.value.length>50) alert('最多只能输入50个字');"
										/>
									</div>									
								</c:when>
							</c:choose>
						</c:if>
					</c:forEach>
					</div>
				</c:forEach>
				<textarea  name="${t1.partId}" style="margin-left: 4px;width: 98%; height: 12%;" readOnly="readonly"></textarea>
					<c:set var="isDoing" value="1" />
			<div style="text-align: right; margin-right: 10px; margin-bottom: 10px;margin-top: 10px;">${year}年${month}月${day}日</div>
		</div>
		<div style="width: 40%;">
				<!--  <input class="weekly-button" type="button" value='修改' id="editTemplate" style="margin-top:4px;display:none;" onclick="doEdit()"/>&nbsp;&nbsp;&nbsp;
				<input class="weekly-button" type='button' value='保存' id="saveTemplate" style="margin-top:4px;display:none;"/>&nbsp;&nbsp;&nbsp;-->
				<input class="weekly-button" type='button' value='返回' id="returnUpload" style="margin-top:6px" onclick="doReturnUpload()"/>
			</div>
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
		<input type=hidden id="contextPath" value=<%=request.getContextPath()%>/>
		<script type="text/javascript"
			src="js/dataTables/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="js/template.js"></script>
	</div>
</form>
</body>
</html>