<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript">
function btn_click() {
	var form = document.m_selectOne_form;
	form.action = "m_updateOK.do";
	form.submit();
}

$(function() {
	console.log("ready...");
	$.ajax({
		type : "post",
		url : "http://localhost:8090/cart/m_CtCuJsonSelect.do",
		data : {
			memberNum : "${vo.memberNum}"
		},
		dataType : "json",
		success : function(list, status, xhr) {
			console.log(list);
			$("#result").empty();
			$('<tr></tr>').html(
					'<td align = \"center\">&nbsp;&nbsp;num&nbsp;&nbsp;</td>'+
					'<td align = \"center\">&nbsp;&nbsp;ID&nbsp;&nbsp;</td>'+
					'<td align = \"center\">&nbsp;&nbsp;PW&nbsp;&nbsp;</td>'+
					'<td align = \"center\">&nbsp;&nbsp;Auth&nbsp;&nbsp;</td>'+
					'<td align = \"center\">&nbsp;&nbsp;delete&nbsp;&nbsp;</td>'
			).appendTo('#result');
			$.each(list, function(i, obj) {
				console.log(obj.num);
				$("#result").append(function() {
					var str = "<tr id=\""+obj.memberNum+"\">";
					str += "<td align = \"center\">" + obj.num + "</td>";
					str += "<td align = \"center\">" + obj.name + "</td>";
					str += "<td align = \"center\">" + obj.price + "</td>";
					str += "<td align = \"center\"><img src=\"resources/uploadimg/thumb_"+obj.imgName+"\"/></td>";
					str += "<td align = \"center\">" + obj.ctCuCheck + "</td>";
					str += "<td align = \"center\">" + obj.count + "</td>";
					str += "</tr>";
					return str;
				});
			});
		}
	});
});
</script>
</head>
<body>
	<h1>인원수정페이지입니다!</h1>
	<a href="home.do">home</a>&nbsp;||
	<a href="m_insert.do">인원추가</a>
	<a href="m_selectAll.do">회원목록보기</a>&nbsp;||
	<a href="ct_insert.do">상품추가</a>
	<a href="ct_selectAll.do">상품목록보기</a>&nbsp;||
	<a href="cu_insert.do">쿠폰추가</a>
	<a href="cu_selectAll.do">쿠폰목록보기</a>&nbsp;||
	<a href="m_logout.do">로그아웃</a>
	<hr>
		 <h3>${user_id} welcome!</h3>
	<hr>
		<form name = "m_selectOne_form" methos = "post"><br>
		<table><tr><td><input type="hidden" name="memberNum" value="${vo.memberNum}"/>num: ${vo.memberNum}</td></tr>
		<tr><td><input type="hidden" name="memberID" value="${vo.memberID}"/>ID: ${vo.memberID}</td></tr>
		<tr><td>PW: <input type="password" name="memberPW" value="${vo.memberPW}"></td></tr>
		<tr><td><input type="hidden" name="memberAuth" value="${vo.memberAuth }"></td></tr></table>
<!-- 		<input type="file" name="mulitpartFile" >			 -->
		</form>
		<table><tr><td><input type="button" value="업데이트" onclick='btn_click();'/>&nbsp;
		</tr></table>
</body>
</html>