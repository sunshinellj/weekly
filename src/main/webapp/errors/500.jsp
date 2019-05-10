<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>500 页面出错</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<link rel="shortcut icon" href="/p2p-web/template/1/business/_files/images/changan.png" type="image/x-icon">
	<style>
	html { padding:0; margin:0;  width:100%; height:100%; }
	body { position:relative; padding:0; margin:0; background:#F3F3F3; width:100%; height:100%; min-width:1024px; }
	.errorPageImg { position:absolute; left:50%; top:50%; display:block; width:622px; height:395px; margin:-197px 0 0 -311px; }
	.errorPageImg img { position:relative; z-index:1; display:block; width:100%; height:100%; }
	.backBtn,
	.backBtn:hover,
	.backBtn:visited,
	.backBtn:active { position:absolute; z-index:2; left:418px; top:338px; display:block; width:88px; height:28px; }
	.backHome,
	.backHome:hover,
	.backHome:visited,
	.backHome:active { position:absolute; z-index:2; left:511px; top:338px; display:block; width:88px; height:28px; }
	</style>
</head>

<body>
	<div class="errorPageImg">
		<img src="/p2p-web/template/1/business/_files/images/errorPage2.jpg"/>
		<a class="backBtn" href="javascript:history.back();"></a>
		<a class="backHome" href="/cajj-cms"></a>
	</div>
</body>
</html>