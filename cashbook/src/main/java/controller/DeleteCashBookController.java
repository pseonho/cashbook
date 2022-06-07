package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;

@WebServlet("/DeleteCashBookController")
public class DeleteCashBookController  extends HttpServlet {
	
		protected void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
			// cashbookOne에서 cashbookNo받아오기
			int cashBookNo = Integer.parseInt(request.getParameter("cashBookNo"));
			System.out.println(cashBookNo);	// 디버깅
			
			CashBookDao cashbookDao = new CashBookDao();
			cashbookDao.deleteCashBook(cashBookNo);
			
			response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController");
		}
		
	}