package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashBookDao;
import vo.CashBook;

@WebServlet("/InsertCashBookController")
public class InsertCashBookController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 값 요청
		HttpSession session = request.getSession();
	    String sessionMemberId = (String)session.getAttribute("sessionMemberId");
	    //로그인이 안되어있을 경우 LoginController로 보냄
	    if(sessionMemberId == null) {
	        response.sendRedirect(request.getContextPath()+"/LoginController");
	        return;
	      }
		//request 분석
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String cashDate = year+"-"+month+"-"+day;
		System.out.println(cashDate +"<-cashDate InsertCashBookController"); //디버깅
		request.setAttribute("cashDate", cashDate);
		request.getRequestDispatcher("/WEB-INF/view/InsertCashBookForm.jsp").forward(request, response);// get 방식은 InsertCashBook.jsp페이지를 서비스 =C-V방식
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 값 요청
		HttpSession session = request.getSession();
	    String sessionMemberId = (String)session.getAttribute("sessionMemberId");
	    //로그인이 안되어있을 경우 LoginController로 보냄ㅏ
	    if(sessionMemberId == null) {
	        response.sendRedirect(request.getContextPath()+"/LoginController");
	        return;
	      }

		//request 분석
		request.setCharacterEncoding("UTF-8"); // 폼값(post)에 한글이 있는데 한글이 utf-8방식으로 인코딩 되었다.
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String cashDate = request.getParameter("cashDate"); 
		System.out.println(cashDate +"<-cashDate insertCashBookController");
		String kind = request.getParameter("kind");
		System.out.println(kind +"<-kind insertCashBookController");
		int cash = Integer.parseInt(request.getParameter("cash"));
		System.out.println(cash +"<-cash insertCashBookController");
		String memo = request.getParameter("memo");
		System.out.println(memo +"<-memo insertCashBookController");
		//태그 값 구하기
		List<String> hashtag = new ArrayList<String>(); //tag가 들어갈 list
		String memo2 = memo.replace("#"," #"); //##방지 #을 " #"으로 바꾸는 문자열을 새로만들어서 memo2에 저장
		String[] arr = memo2.split(" "); // memo를 " "토큰으로 나눔
		for(String s : arr) {
			if(s.startsWith("#")) { //문장이 #으로 시작하면
				String temp = s.replace("#",""); //#은 모두 공백으로 바꾼후 temp에 임시 저장한 후
				if(!temp.equals("")) {//temp가 빈칸이 아니라면
				hashtag.add(temp);//리스트에 저장
				}
			}
		}
		//디버깅
		System.out.println(hashtag.size()+"hashtag.size InsertCashBookController.dopost");
		for(String h : hashtag) {
			System.out.println(h+"<--hashtag InsertCashBookController.dopost");
		}
		//
		CashBook cashBook = new CashBook();
		cashBook.setCashDate(cashDate);
		cashBook.setKind(kind);
		cashBook.setCash(cash);
		cashBook.setMemo(memo);
		cashBook.setMemberId(sessionMemberId);
		
		//Dao에 모델 값 요청
		CashBookDao cashBookDao = new CashBookDao();
		cashBookDao.insertCashbook(cashBook,hashtag);
		//완료후 CashBookListByMonthController
		response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController?year="+year+"&month="+month);
//		System.out.println(hashtagRow);
//		if (hashtagRow == hashtag.size()) { // 해쉬태그 테이블에 태그 수많큼 입력이 성공하면 listcontroller로 돌려보냄
//		}else {//다르다면 실패, InsertCashBookForm.jsp 로 넘어감
//			request.getRequestDispatcher("/WEB-INF/view/InsertCashBookForm.jsp").forward(request, response);
		}
	}