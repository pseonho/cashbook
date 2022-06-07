package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashBookDao;

@WebServlet("/CashBookListByMonthController")
public class CashBookListByMonthController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     HttpSession session = request.getSession();
	      String sessionMemberId = (String)session.getAttribute("sessionMemberId");
	      if(sessionMemberId == null) {
	         // 로그인 되지 않은 경우
	         response.sendRedirect(request.getContextPath()+"/LoginController");
	         return;
	      }

		//1) 월별 가계부 리스트 요청 처리 분석
		//요청값이 없을 경우 오늘 날짜가 기준
		Calendar now = Calendar.getInstance();
		//연도 설정
		int year = now.get(Calendar.YEAR);
			if(request.getParameter("year")!=null && !request.getParameter("year").equals("")) {
				year = Integer.parseInt(request.getParameter("year"));
			}
		//달 설정
		int month = now.get(Calendar.MONTH)+1; //Calendar.month값은 -1값이다
			if(request.getParameter("month")!=null && !request.getParameter("month").equals("")) {
				month = Integer.parseInt(request.getParameter("month"));
			}if(month==0) {// 페이징으로 달이 0이되면 연도 변경
				month =12;
				year= year-1;
			}else if(month==13) {// 페이징으로 달이 13이되면 연도 변경
				month = 1;
				year= year+1;
			}
			System.out.println(year +"<-year CashBookListByMonthController");
			System.out.println(month +"<-month CashBookListByMonthController");
			//			// 오늘 날짜
			//			int today = now.get(Calendar.DATE);
			//			System.out.println(today+"<-today CashBookListByMonthController");

		//캘린더api 모델을 불러온 컨트롤러로 볼수있다
		//이달의 1일의 요일을 구하여 달력 앞쪽 빈칸 수 구하기
		Calendar firstDay = Calendar.getInstance(); 
		firstDay.set(Calendar.YEAR, year); // 연도를 페이지의 연도에 맞게 변경
		firstDay.set(Calendar.MONTH, month-1); // 월을 페이지의 월에 맞게 변경
		firstDay.set(Calendar.DATE, 1); // 일을 1일로 변경
		int dayOfWeek =firstDay.get(Calendar.DAY_OF_WEEK);//일요일 = 1, 월요일 = 2 ... 토요일=7 
		System.out.println(dayOfWeek+"<-dayOfWeek CashBookListByMonthController");//디버깅
		int startBlank = dayOfWeek -1;//일요일 =0, 월요일 = 1... 토=6
		System.out.println(startBlank+"<-startBlank CashBookListByMonthController");//디버깅
		//이달 마지막 날짜
		int endDay = firstDay.getActualMaximum(Calendar.DATE); //firstDay의 연,달의 제일 큰숫자 -> 마지막 날짜
		//달력 뒷쪽의 빈칸 수 구하기 - 앞 빈칸 + 마지막 날짜_뒷빈칸이 7의 배수가 되도록 -> 7-(앞빈칸+마지막날의나머지)
		int endBlank = 0;
			if((startBlank+endDay)%7 !=0) { //7로 나누어 떨어지면 뒤에빈칸 없음 -> 0으로 유지
				endBlank = 7-((startBlank+endDay)%7);
			}
			System.out.println(endBlank+"<-endBlank CashBookListByMonthController");//디버깅
		//총 행의 수
		int totalTd = startBlank + endBlank + endDay;
		System.out.println(totalTd+"<-totalTd CashBookListByMonthController");//디버깅
		
		//2)모델값(월별 가계부 리스트)을 반환하는 비지니스 로직(모델) 호출
		CashBookDao cashBookDao = new CashBookDao();
		List<Map<String,Object>> cashBookList = cashBookDao.selectcashBookListbyMonth(year, month);
		/*
		 달력출력에 필요한 모델값(list, year, month)
		 */
		request.setAttribute("cashBookList", cashBookList);//데이터베이스에서 반환된 모델값
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		
		request.setAttribute("startBlank", startBlank);//앞쪽 빈칸 수
		request.setAttribute("endBlank", endBlank);//뒤쪽 빈칸 수
		request.setAttribute("endDay", endDay);//출력월의 마지막 일
		request.setAttribute("totalTd", totalTd);//전체 td수
		
		//3)뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashBookListByMonth.jsp").forward(request, response);
	} 
}