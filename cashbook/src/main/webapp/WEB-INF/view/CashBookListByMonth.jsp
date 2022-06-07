<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>CashBookListByMonth</title>
</head>
<body class = "container">
<%
	//컨트롤러 값 받기
	List<Map<String,Object>> cashBookList = (List<Map<String,Object>>)request.getAttribute("cashBookList");
	int year = (int)request.getAttribute("year");
	int month = (int)request.getAttribute("month");
	int startBlank = (int)request.getAttribute("startBlank");//앞쪽 빈칸 수
	int endBlank =(int)request.getAttribute("endBlank");//뒤쪽 빈칸 수
	int endDay = (int)request.getAttribute("endDay");//출력월의 마지막 일
	int totalTd = (int)request.getAttribute("totalTd");//전체 td수
	//디버깅
	System.out.println(cashBookList.size() +"<-list.size() CashBookListByMonth.jsp");
	System.out.println(year +"<--year CashBookListByMonth.jsp");
	System.out.println(month +"<--month CashBookListByMonth.jsp");
	System.out.println(startBlank +"<--startBlank CashBookListByMonth.jsp");
	System.out.println(endBlank +"<--endBlank CashBookListByMonth.jsp");
	System.out.println(endDay +"<--endDay CashBookListByMonth.jsp");
	System.out.println(totalTd +"<--totalTd CashBookListByMonth.jsp");
%>
<div>
		<a href= "<%=request.getContextPath()%>/SelectMemberOneController"><%=session.getAttribute("sessionMemberId")%>님 반갑습니다.
		<a href="<%=request.getContextPath() %>/LogoutController">로그아웃</a>
</div>

<div>
	<a href="<%=request.getContextPath() %>/TagListController">tags</a>
</div>

	<h2><%=year%>년 <%=month %>월</h2>
	<a href = "<%=request.getContextPath()%>/CashBookListByMonthController?year=<%=year%>&month=<%=month-1%>"><button type="button" class="btn btn-outline-primary">이전달</button></a>
	<a href = "<%=request.getContextPath()%>/CashBookListByMonthController?year=<%=year%>&month=<%=month+1%>"><button type="button" class="btn btn-outline-primary">다음달</button></a>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th class="text-danger">일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th class="text-primary">토</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<%
					for (int i=1;i<=totalTd;i=i+1){
						if((i-startBlank)>0 && (i-startBlank)<= endDay){
							String c =""; // class에 들어갈 색깔
							if(i%7==1){
								c ="text-danger";
							}else if(i%7==0){
								c ="text-primary";
							}
				%>
						<td class="<%=c%>">
							<%=(i-startBlank)%><!-- 날짜출력 -->
						<!-- 추가입력 버튼 -->
						<a href="<%=request.getContextPath()%>/InsertCashBookController?year=<%=year %>&month=<%=month %>&day=<%=i-startBlank%>" class="btn btn-light btn-sm">입력</a>
						<!-- 해당 날짜의 cashBook 목록 출력 -->
						<%
							for(Map m : cashBookList){
								if((i-startBlank)==(Integer)m.get("day")){
						%>
									<div>
										<a href="<%=request.getContextPath()%>/CashBookOneController?cashBookNo=<%=m.get("cashBookNo")%>">
											[<%=m.get("kind") %>]
											<%=m.get("cash") %>원
											<%=m.get("memo")%>
										</a>
									</div>
				<%					
								}
							}
				%>
							</td>
				<%
						}else{
				%>
							<td>&nbsp;</td><!-- 빈칸출력 -->
				<%
						}
						if(i<totalTd && i%7==0){
				%>
							</tr><tr><!-- 새로운 행 추가 -->
				<%
						}
					}
				%>
			</tr>
		</tbody>
	</table>
	
</body>
</html>