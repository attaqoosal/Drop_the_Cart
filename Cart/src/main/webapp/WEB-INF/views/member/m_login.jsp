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
	var form = document.login_form;
	
	if(str=="login"){
		login_form.action = "m_loginOK.do";
	}
	else{
		return false;
	}
	form.submit();
}
</script>
</head>
<body>
	<h1>로그인!</h1>
	<div>
		<form name="login_form" method="post">
			<table>
				<tr>
					<td>ID:</td>
					<td><input type="text" name="memberID" value="admin" /></td>
				</tr>
				<tr>
					<td>PW:</td>
					<td><input type="password" name="memberPW" /></td>
				</tr>
			</table>
		</form>
		<table>
			<tr>
				<td><input type="button" value="로그인"
					onclick='btn_click("login");' /></td>
				<td><button onclick="location.href='m_insert.do'">회원가입</button></td>
			</tr>
		</table>
	</div>
</body>
</html>