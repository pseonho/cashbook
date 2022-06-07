<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>InsertCashBook</title>
</head>
<body class ="container">
	<h1>insertCashBook</h1>
	<form method ="post" action="<%=request.getContextPath()%>/InsertCashBookController">
		<!-- 연도와 달력값 보내기 -->
		<input type ="hidden" name="year" value="<%=request.getParameter("year")%>">
		<input type ="hidden" name="month" value="<%=request.getParameter("month")%>">
		<table class="table table-bordered">
			<tr>
				<td>cashDate</td>
				<td>
					<input type = "text" readonly="readonly" value ="<%=(String)request.getAttribute("cashDate")%>" name ="cashDate" >
				</td>
			</tr>
			<tr>
				<td>kind</td>
				<td>
					<input type = "radio" name="kind" value="수입">수입 
					<input type = "radio" name="kind" value="지출">지출
				</td>
			</tr>
			<tr>
				<td>cash</td>
				<td>
					<input type = "number" name ="cash" >
				</td>
			</tr>
			<tr>
				<td>memo</td>
				<td>
					<textarea rows="5" cols="50" name ="memo" ></textarea>
				</td>
			</tr>
			<tr>
				<td colspan ="2">
					<button type = "submit" class="btn btn-success">입력</button>
				</td>
			</tr>
		</table>
		<a  href="<%=request.getContextPath()%>/CashBookListByMonthController" type ="button" class="btn btn-secondary">리스트</a>
	</form>
</body>
</html>