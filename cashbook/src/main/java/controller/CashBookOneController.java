package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;
import vo.CashBook;
@WebServlet("/CashBookOneController")
public class CashBookOneController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//널체크
		if(request.getParameter("cashBookNo")==null) {//cashBookNo 가 null이면 CashBookListByMonthController으로 보냄
		  response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController?msg=null");
		  return;
		
		}
		
		//요청값 처리
		int cashBookNo = Integer.parseInt(request.getParameter("cashBookNo"));
		System.out.println(cashBookNo+"<-cashBookNo CashBookOneController");
		
		//Dao.CashBookDao DB값 요청
		CashBookDao cashBookDao = new CashBookDao();
		CashBook cashBook = new CashBook();
		cashBook= cashBookDao.selectCashBookOne(cashBookNo); //상세보기 값 요청
		request.setAttribute("cashBook", cashBook);
		request.getRequestDispatcher("/WEB-INF/view/CashBookOne.jsp").forward(request, response);
	}

}   