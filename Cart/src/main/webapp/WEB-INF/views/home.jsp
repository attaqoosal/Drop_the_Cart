<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
	<title>Home</title>
</head>
<body>
	<c:choose>
	<c:when test="${user_id != null and user_auth == 'on'}">
	<h1>하늘에서 신상이 내린다면</h1>
	<a href="home.do">home</a>
	<a href="ctcu_json_selectAll.do">json</a>&nbsp;||
	<a href="m_insert.do">인원추가</a>
	<a href="m_selectAll.do">회원목록보기</a>&nbsp;||
	<a href="ct_insert.do">상품추가</a>
	<a href="ct_selectAll.do">상품목록보기</a>&nbsp;||
	<a href="cu_insert.do">쿠폰추가</a>
	<a href="cu_selectAll.do">쿠폰목록보기</a>
	<a href="m_logout.do">로그아웃</a>
	<hr>
		 <h3>${user_id} welcome!</h3>
	<hr>
	</c:when>
	<c:when test="${user_id != null and user_auth == 'off'}">
	<h1>하늘에서 신상이 내린다면</h1>
	<a href="m_logout.do">로그아웃</a>
	<hr>
		 <h3>${user_id} welcome</h3>
	<hr>
	</c:when>
	<c:otherwise>
	<h1>하늘에서 신상이 내린다면</h1>
	<hr><br><a href="m_login.do">로그인</a><br><hr>
	</c:otherwise>
	</c:choose>
	<img width="200px" height="300px" src="resources/background.png">
	<hr>
</body>
</html>
