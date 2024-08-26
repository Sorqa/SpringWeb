<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구구단 보기</title>
</head>
<body>
<h3>구구단 보세요</h3>
<c:forEach var="gugu" items="${list}">
<div>${gugu}<div>
</c:forEach> 
</body>
</html>