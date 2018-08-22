<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript">
function btn_click(str) {
	var form = document.insert_form;
	
	if(str=="OK"){
		insert_form.action = "m_insertOK.do";
	}
	else{
		return false;
	}
	
	form.submit();
}
</script>
</head>
<body>
	<c:choose>
		<c:when test="${user_id != null and user_auth == 'on'}">
			<h1>인원 등록 페이지입니다!</h1>
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
			</c:when>
		<c:otherwise>
	<h1>회원가입 페이지입니다!</h1>
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${param.nulled=='1'}">
	<h3>You Must Entered password</h3>
	</c:when>
	<c:when test="${param.fail=='1'}">
	<h3>insert Fail...</h3>
	</c:when>
	</c:choose>
	<div>
		<form name="insert_form" method="post">
			<table>
				<tr>
					<td align="center">ID:</td>
					<td><input type="text" name="memberID" value="admin" /></td>
				</tr>
				<tr>
					<td align="center">PW:</td>
					<td><input type="password" name="memberPW"/></td>
				</tr>
				<tr>
					<td><input type="hidden" name="memberAuth" /></td>
				</tr>
			</table>
		</form>
		<table>
			<tr>
				<td><input type="button" value="확인"
					onclick='btn_click("OK");' /></td>
				<td><button onclick="location.href='m_login.do'">취소</button></td>
			</tr>
		</table>
	</div>
</body>
</html>