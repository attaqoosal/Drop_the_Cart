<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	console.log("ready...");
	$.ajax({
 		type : "post",
		url : "http://localhost:8090/cart/m_json_selectAll.do",
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
					str += "<td align = \"center\"><a href=\"m_selectOne.do?memberNum="+obj.memberNum+"\">" + obj.memberNum + "</a></td>";
					str += "<td align = \"center\">" + obj.memberID + "</td>";
					str += "<td align = \"center\">" + obj.memberPW + "</td>";
					str += "<td align = \"center\">" + obj.memberAuth + "</td>";
					str += "<td align = \"center\"><a href=\"m_deleteOK.do?memberNum="+obj.memberNum+"\">delete</a></td>";
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
	<h1>인원목록입니다!</h1>
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
	<table id="result" border="1"></table>
	<hr>
</body>
</html>