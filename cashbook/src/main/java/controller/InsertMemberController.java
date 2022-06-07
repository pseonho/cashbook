package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/InsertMemberController")
public class InsertMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/InsertMemberForm.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) request
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		
		// 디버깅
		System.out.println("InsertMemberController " + memberId);
		System.out.println("InsertMemberController  " + memberPw);
		System.out.println("InsertMemberController " + name);
		System.out.println("InsertMemberController " + age);
		
		// 2) 모델값 넣기
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setName(name);
		member.setAge(age);
		
		MemberDao memberDao = new MemberDao();
		int row= memberDao.insertMember(member);
		
		
		if(row == 1) {
			response.sendRedirect(request.getContextPath()+"/LoginController");
			return;
		} else {
			response.sendRedirect(request.getContextPath()+"/InsertMemberController");
		}
	}

}