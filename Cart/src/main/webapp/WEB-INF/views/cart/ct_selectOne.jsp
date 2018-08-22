<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 변경</title>
</head>
<body>
	<h1>상품수정페이지입니다!</h1>
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
	<c:choose>
		<c:when test="${param.nulled=='1'}">
			<h3>정보를 모두 입력하세요!!!</h3>
		</c:when>
		<c:when test="${param.fail=='1'}">
			<h3>업로드 실패!</h3>
		</c:when>
	</c:choose>
	<img src="resources/uploadimg/${vo2.imgName}" width="400"  height="400"/>
	<form:form modelAttribute="vo" action="ct_updateOK.do" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="num" value="${vo2.num}"/>num: ${vo2.num}<br><br>
		상품명: &nbsp;<input type="text" name="name" value="${vo2.name}" />
		<br><br>
		상품가격: &nbsp;<input type="text" name="price" value="${vo2.price}" />
		<br><br>
		상품/쿠폰체크: ${vo2.ctCuCheck}&nbsp;<input type="hidden" name="ctCuCheck" value="${vo2.ctCuCheck}" />
		<br><br>
		이미지: &nbsp;<input type="file" name="multipartFile" />
		<br><br>
		<input type="submit" value="전송">
	</form:form>
</body>
</html>