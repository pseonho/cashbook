<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*"%>
<%@ page import="dao.*"%>
<%
	// 정보 가져오기
	Member member = (Member)request.getAttribute("member"); 
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MemberOne</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h1 class="text-center">회원정보</h1>
		
				<table class="table table-bordered">			
			<tr>
				<td>아이디</td>
				<td><%=member.getMemberId()%></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><%=member.getName()%></td>
			</tr>
			<tr>
				<td>나이</td>
				<td><%=member.getAge()%></td>
			</tr>
		</table>
		<div>
			<button class="btn btn-outline-dark"><a href="<%=request.getContextPath()%>/UpdateMemberController?memberId=<%=member.getMemberId()%>">수정<a/></button> 
			<button class="btn btn-outline-dark"><a href="<%=request.getContextPath()%>/DeleteMemberController">회원탈퇴<a/></button>
		</div>
	</div>
</body>
</html>