<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
	<%
		List<Map<String, Object>>list = (List<Map<String, Object>>)request.getAttribute("list");
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");

		int startBlank = (Integer)request.getAttribute("startBlank");
		int endDay = (Integer)request.getAttribute("endDay");
		int totalTd = (Integer)request.getAttribute("totalTd");
		int endBlank = (Integer)request.getAttribute("endBlank");

		System.out.println(startBlank + "<-startBlank CashbookListByMonth.jsp"  );
		System.out.println(endDay + "<-endDay CashbookListByMonth.jsp"  );
		System.out.println(totalTd + "<-totalTd CashbookListByMonth.jsp"  );
		System.out.println(endBlank + "<-endBlank CashbookListByMonth.jsp"  );
		
		System.out.println(list.size()+"list.size() CashbookListByMonth.jsp");
		System.out.println(y+"y CashbookListByMonth.jsp");
		System.out.println(m+"m CashbookListByMonth.jsp");
	 %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashBookListByMonth</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>

	<h2><%=y%>년 <%=m%>월</h2>


	<div>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m-1%>">이전 달</a>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m+1%>">다음 달</a> 
	</div>
	
	<!-- 
		1)  이번달 1일의 요일 firstDay 
		2) 요일 -> startBlank -> 일 0, 월 1, 화 2, ... 토 6칸
	    3) 이번달 마지막 날짜
	    4) endBlank - > totalBlank
	  +
		  5) td의 갯수  1~totalBlank
	    6) 가계부 list
	 
	 -->
<table class="table table-bordered table-striped">
      <thead>
         <tr>
            <th>일</th>
            <th>월</th>
            <th>화</th>
            <th>수</th>
            <th>목</th>
            <th>금</th>
            <th>토</th>
         </tr>
      </thead>
      <tbody>
         <tr>
            <%
               for(int i=1; i<=totalTd; i+=1) {
                  if((i-startBlank) > 0 && (i-startBlank) <= endDay) {
                     String c = "";
                     if(i%7==0) {
                        c = "text-primary";
                     } else if(i%7==1) {
                        c = "text-danger";
                     }
            %>
                     <td class="<%=c%>">
                        <%=i-startBlank%>
                        <a href="<%=request.getContextPath()%>/InsertCashBookController?y=<%=y%>&m=<%=m%>&d=<%=i-startBlank%>" class="btn btn-light">입력</a>
                     	<div>
                                     	<!-- 해당 날짜의 cashbook 목록 출력 -->
                                     	<%
                                     		//해당 날짜의 cashbook 목록 출력
                                     		for(Map map : list){
                                     			if ((Integer)map.get("day")==(i-startBlabk)){
                                     				%>
                                     				
                                     			}
                                     		}
                                     	%>
                     	</div>
                     
                     
                     </td>
            <%
                  } else {
            %>
                     <td>&nbsp;</td>
            <%         
                  }
                  if(i<totalTd && i%7==0) {
            %>
                     </tr><tr><!-- 새로운 행을 추가시키기 위해 -->
            <%         
                  }
               }
            %>
         </tr>
      </tbody>
   </table>
   <%
   	for(Map map : list){
   %>
   	<div>
   			<%= map.get("cashbookNo")  %>
   			<%= map.get("day")  %>
   			<%= map.get("kind")  %>
   			<%= map.get("cash")  %>
   			<%= map.get("memo")  %>
   			
   	</div>
   <%
   	}
   %>
   
</body>
</html>