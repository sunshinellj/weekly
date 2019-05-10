$(document)
		.ready(
				function() {
					var monthValue = $("#month").val();
					var yearValue = $("#year").val();
					var issueValue = $("#issue").val();
					var userId = $('input[id=user]').val();
					// var userId = "000002";
					var aj = $.ajax({
						url : 'isHaveAjax',// 跳转到
						// action
						data : {
							month : monthValue,
							year : yearValue,
							issue : issueValue,
							userId : userId
						},
						type : 'post',
						cache : false,
						dataType : 'text',
						success : function(data) {
							// alert(data);
							var str = data.split("~");
							var timeZone = "";
							timeZone = "起 " + str[0] + " 至 " + str[1];
							if (str[2] == "exist") {
								alert("该份周报已经存在，请重新选择");
								$(".text input").attr("readonly", "readonly");
								$("textarea").attr("readonly", "readonly");
								$("#saveAndReturn").hide();
							} else {
								$(".text input").removeAttr("readonly");
								$("textarea").removeAttr("readonly");
								$("#saveAndReturn").show();
							}
							$("#timeZone").html(timeZone);

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							// view("异常！");
							alert("异常！");
						}
					});
					// 设置当月份改变时，期数重置为1
					$("#month").change(function() {
						$("#issue").get(0).selectedIndex = 0;
					});
					// Ajax
					$("#year,#month,#issue")
							.change(
									function() {
										var monthValue = $("#month").val();
										var yearValue = $("#year").val();
										var issueValue = $("#issue").val();
										var userId = $('input[id=user]').val();
										var aj = $
												.ajax({
													url : 'isHaveAjax',// 跳转到
													// action
													data : {
														month : monthValue,
														year : yearValue,
														issue : issueValue,
														userId : userId
													},
													type : 'post',
													cache : false,
													dataType : 'text',
													success : function(data) {
														// alert(data);
														var str = data
																.split("~");
														var timeZone = "";
														timeZone = "起 "
																+ str[0]
																+ " 至 "
																+ str[1];
														if (str[2] == "exist") {
															alert("该份周报已经存在，请重新选择");
															$(".text input")
																	.attr(
																			"readonly",
																			"readonly");
															$("textarea").attr(
																	"readonly",
																	"readonly");
															$("#saveAndReturn")
																	.hide();
														} else {
															$(".text input")
																	.removeAttr(
																			"readonly");
															$("textarea")
																	.removeAttr(
																			"readonly");
															$("#saveAndReturn")
																	.show();
														}
														// 根据不同的月，进行期数的绑定
														var optNum = $("#issue")[0].options.length;
														var monthCount = str[3];
														if (monthCount > optNum) {
															for (var i = optNum + 1; i <= monthCount; i++) {
																$("#issue")
																		.append(
																				"<option value='"
																						+ i
																						+ "'>"
																						+ i
																						+ "</option>");
															}
														}
														if (monthCount < optNum) {
															for (var i = optNum; i > monthCount; i--) {
																$(
																		"#issue option:last")
																		.remove();
															}
														}

														$("#timeZone").html(
																timeZone);

													},
													error : function(
															XMLHttpRequest,
															textStatus,
															errorThrown) {
														// view("异常！");
														alert("异常！");
													}
												});
									});

					// 输入框的收缩展开和上下箭头的切换
					$(".btn2").hide();
					$(".btn1").click(
							function() {
								$(this).hide();
								$(this).next(".btn2").show();
								$(this).parent("p.title").next("div.text")
										.slideToggle();
							});
					$(".btn2").click(
							function() {
								$(this).hide();
								$(this).prev(".btn1").show();
								$(this).parent("p.title").next("div.text")
										.slideToggle();
							});

					/**
					 * 保存并返回
					 */
					$('#saveAndReturn').click(function() {
						$("#report").attr("action", "saveAndReturn");
						$("#report").submit();
					});
					$('#return').click(
							function() {
								var contextPath = $("#contextPath").val() + "/returnSearch_reportList";
								 window.location.href = contextPath;
							});
					$('#editAndReturn').click(function() {
						$("#editReport").attr("action", "editAndReturn");
						$("#editReport").submit();
					});
					$('#isHave').click(function() {
						// alert("aaa");
						$("#report").attr("action", "isHave");
						$("#report").submit();
					});

					$("#datepicker1").datepicker({
						// appendText: '(yyyy-mm-dd)',
						changeYear : true,
						changeMonth : true,
						currentText : 'Today'
					});
					$("#datepicker2").datepicker({
						// appendText: '(yyyy-mm-dd)',
						changeYear : true,
						changeMonth : true,
						currentText : 'Today'
					});

					$.extend($.fn.dataTable.defaults, {
						"searching" : false
					});

					$('.add1')
							.click(
									function() {
										// 得到表格
										var vTb = $(this).parent().parent()
												.parent();
										// 所有的数据行有一个.CaseRow的Class,得到数据行的大小
										var vNum = $(this).parent().parent()
												.parent().find(".CaseRow")
												.size();
										if (vNum >= 15) {
											return;
										}
										// 最后一行行号
										var lastNum = vNum - 1;
										// 表格有多少个数据行
										var v_tableId = vTb.attr("id");
										var v_last = "#" + v_tableId + " #"
												+ v_tableId + "tRow" + lastNum;
										var vTr = $(v_last);
										// 得到表格中的第一行数据
										var vTrClone = vTr.clone(true);
										// 设置bottom class
										vTr.find(".add1").css("display",
												"none");
										vTr.find(".remove1").css("display",
												"none");
										// 创建第一行的副本对象vTrClone
										vTrClone[0].id = v_tableId + "tRow"
												+ vNum;
										vTrClone.find(".remove1").css(
												"display", "inline");
										vTrClone.find(".txt1").val("");
										var a = vTrClone.find(".index").text();
										var s = parseInt(a) + 1;
										vTrClone.find(".index").text(s);
										// 把副本单元格对象添加到表格下方
										vTrClone.appendTo(vTb);

									});

					$('.remove1').click(
							function() {
								// 表格有多少个数据行
								var vNum = $(this).parent().parent().parent()
										.find(".CaseRow").size();
								if (vNum <= 1) {
									alert('请至少留1行');
									return;
								}

								// 得到点击的按钮对象
								var vbtnDel = $(this);
								// 得到父tr对象
								var vTb = $(this).parent().parent().parent();
								var v_tableId = vTb.attr("id");
								var vTr = vbtnDel.parent("tr");
								if (vTr.attr("id") == v_tableId + "tRow0") {
									// 第一行是克隆的基础，不能删除
									alert('第一行不能删除!');
									return;
								} else {
									vTr.remove();
									var prevNum = vNum - 2;
									var v_preId = "#" + v_tableId + " #"
											+ v_tableId + "tRow" + prevNum;
									var prevTr = $(v_preId);
									prevTr.find(".add1").css("display",
											"inline-block");
									vTr.find(".remove1").css("display",
											"none");
									if (prevNum <= 0) {
										prevTr.find(".remove1").css(
												"display", "none");
									} else {
										prevTr.find(".remove1").css(
												"display", "inline-block");
									}
								}
							});

					// 下载
					$("#downLoad").click(function() {
						//alert("aaa");
						$("#editReport").attr("action", "download");
						$("#editReport").submit();
					});
				});

//输入字符的统计
var maxstrlen = 500;
function Q(s) { return document.getElementById(s); }

function checkWord(c) {
	len = maxstrlen;
	var str = c.value;
	myLen = getStrleng(str);
	var wck = Q("wordCheck");

	if (myLen > len * 2) {
		c.value = str.substring(0, i + 1);
	}
	else {
		wck.innerHTML = Math.floor((len * 2 - myLen) / 2);
	}
}

function getStrleng(str) {
	myLen = 0;
	i = 0;
	for (; (i < str.length) && (myLen <= maxstrlen * 2); i++) {
		myLen += 2;
	}
	return myLen;
}