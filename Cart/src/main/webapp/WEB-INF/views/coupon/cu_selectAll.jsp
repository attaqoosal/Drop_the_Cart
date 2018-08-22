<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 목록</title>
</head>
<body>
	<h1>쿠폰목록입니다!</h1>
	<a href="home.do">home</a>&nbsp;||
	<a href="m_insert.do">인원추가</a>
	<a href="m_selectAll.do">회원목록보기</a>&nbsp;||
	<a href="ct_insert.do">상품추가</a>
	<a href="ct_selectAll.do">상품목록보기</a>&nbsp;||
	<a href="cu_insert.do">쿠폰추가</a>
	<a href="cu_selectAll.do">쿠폰목록보기</a>&nbsp;||
	<a href="m_logout.do">로그아웃</a>
	<hr>
	<h3>${user_id}welcome!</h3>
	<hr>
	<table border="2">
		<thead>
			<tr>
				<th>번호</th>
				<th>쿠폰 번호</th>
				<th>쿠폰 명</th>
				<th>할인 가격</th>
				<th>상품/쿠폰체크</th>
				<th>이미지</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vo" items="${list}" varStatus="i">
				<tr>
					<td><center>${i.index+1}</center></td>
					<td><center>
							<a href="cu_selectOne.do?num=${vo.num}">${vo.num}</a>
						</center></td>
					<td><center>${vo.name}</center></td>
					<td><center>${vo.price}</center></td>
					<td><center>${vo.ctCuCheck}</center></td>
					<td><img alt="index image"
						src="resources/uploadimg/thumb_${vo.imgName}"></td>
					<td><a href="cu_deleteOK.do?num=${vo.num}">delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>