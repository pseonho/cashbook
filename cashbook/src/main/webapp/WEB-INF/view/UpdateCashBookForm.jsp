<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import ="vo.CashBook" %>
<%
	//컨트롤러 값 받기
	CashBook cashBook = (CashBook)request.getAttribute("cashBook");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>updateCashBookForm</title>
</head>
<body class="container">
	<h1>cashBook 수정</h1>
	<form method ="post" action ="<%=request.getContextPath()%>/UpdateCashBookController">
		<input type ="hidden" name="cashBookNo" value=<%=cashBook.getCashBookNo() %>>
		<table class="table table-bordered">
			<tr>
				<td>cashDate</td>
				<td>
					<input type = "date"  value ="<%=cashBook.getCashDate()%>" name ="cashDate" >
				</td>
			</tr>
			<tr>
				<td>kind</td>
				<td>
					<input type = "radio" name="kind" value="<%=cashBook.getKind()%>" checked="checked"><%=cashBook.getKind()%>
					<input type = "radio" name="kind" value="수입">수입 
					<input type = "radio" name="kind" value="지출">지출
				</td>
			</tr>
			<tr>
				<td>cash</td>
				<td>
					<input type = "number" name ="cash" value="<%=cashBook.getCash() %>" >
				</td>
			</tr>
			<tr>
				<td>memo</td>
				<td>
					<textarea rows="5" cols="50" name ="memo"><%=cashBook.getMemo() %></textarea>
				</td>
			</tr>
			<tr>
				<td colspan ="2">
					<button type = "submit" class="btn btn-outline-primary">수정</button>
				</td>
			</tr>
		</table>
	</form>
	<a href="<%=request.getContextPath()%>/CashBookOneController?cashBookNo=<%=cashBook.getCashBookNo()%>" type ="button" class="btn btn-outline-dark">수정취소</a>
	<a  href="<%=request.getContextPath()%>/CashBookListByMonthController" type ="button" class="btn btn-outline-dark">리스트</a>
</body>
</html>