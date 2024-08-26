<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 리스트</title>
</head>
<body>
<h3>파일 리스트 목록</h3>
<tr><th>번호</th> <th>제목</th> <th>작성자</th> <th>날짜</th> <th>첨부파일수</th> </tr>
<c:forEach var="l" items="${list}" varStatus="status">
	<tr>
	<div>
	<td> ${l.unum}</td>	
	<td> ${l.title}</td>		
	<td>${l.writer}</td>	
	<fmt:formatDate var="date" value="${l.rdate}" pattern="yyyy-MM-dd"/>
	<td>${date}</td>
	<td>${l.count}</td>		
	</div>
	</tr>
</c:forEach>
</body>
</html>