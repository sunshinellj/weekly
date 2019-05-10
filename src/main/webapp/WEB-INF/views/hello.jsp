<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="js/jquery-ui/jquery-ui.css" rel="stylesheet">
<link href="js/jquery-ui/jquery-ui.theme.css" rel="stylesheet">
<link href="js/dataTables/css/jquery.dataTables.min.css"
	rel="stylesheet">

<style>
.text-center {
	text-align: center
}
</style>

<title>Hello</title>
</head>
<body>
	<div class="centent">
		<select multiple="multiple" id="select1"
			style="width: 100px; height: 160px;">
			<option value="1">选项1</option>
			<option value="2">选项2</option>
			<option value="3">选项3</option>
			<option value="4">选项4</option>
			<option value="5">选项5</option>
			<option value="6">选项6</option>
			<option value="7">选项7</option>
		</select>
		<div>
			<span id="add">选中添加到右边&gt;&gt;</span> <span id="add_all">全部添加到右边&gt;&gt;</span>
		</div>
	</div>

	<div class="centent">
		<select multiple="multiple" id="select2"
			style="width: 100px; height: 160px;">
			<option value="8">选项8</option>
		</select>
		<div>
			<span id="remove">&lt;&lt;选中删除到左边</span> <span id="remove_all">&lt;&lt;全部删除到左边</span>
		</div>
	</div>

	<div>
		<c:if test="${msgs!=null}">
			<fmt:message key="${msgs}" />
		</c:if>
	</div>

	<div id="datepicker"></div>

	<table id="example" class="display" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>Name</th>
				<th>Position</th>
				<th>Office</th>
				<th>Age</th>
				<th>Start date</th>
				<th>Salary</th>
			</tr>
		</thead>

		<tfoot>
			<tr>
				<th>Name</th>
				<th>Position</th>
				<th>Office</th>
				<th>Age</th>
				<th>Start date</th>
				<th>Salary</th>
			</tr>
		</tfoot>

		<tbody>
			<c:forEach var="x" begin="1" end="50" step="1" varStatus="varStatus">
				<tr>
					<td>Tiger Nixon ${varStatus.index}</td>
					<td><input type='checkbox' id='selectAll'></input></td>
					<td>Edinburgh${varStatus.index}</td>
					<td>${varStatus.index}</td>
					<td>2011/04/25</td>
					<td>$${varStatus.index}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<table id="tblData">
		<tr>
			<td>ID</td>
		</tr>
		<tr id="tRow0" class="CaseRow">
			<td><input type="text" id="txtID" name="txtID0" /></td>
		</tr>
		<tr id="tRow1" class="CaseRow">
			<td><input type="text" id="txtTitle" name="txtTitle0" /></td>
		</tr>
		<tr id="tRow2" class="CaseRow">
			<td><input type="text" id="txtSmallClassName"
				name="txtSmallClassName0" /></td>
		</tr>
		<tr id="tRow3" class="CaseRow">
			<td><input type="text" id="txtAuthor" name="txtAuthor0" /></td>
		</tr>
		<tr id="tRow4" class="CaseRow">
			<td><input type="text" id="txtUpdateTime" name="txtUpdateTime0" />
			</td>
			<td id="add1"><input type="button" name="add1" value="新增1" /></td>
			<td id="remove1" style="display: none"><input type="button"
				name="remove1" value="删除1" /></td>
		</tr>
	</table>


	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery-ui-i18n-mod.js"></script>
	<script type="text/javascript"
		src="js/dataTables/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/hello.js"></script>

</body>
</html>
