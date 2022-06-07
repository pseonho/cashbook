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

@WebServlet("/DeleteMemberController")
public class DeleteMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {;
	// 세션- 아이디 값 가져오기
	HttpSession session = request.getSession();
	String sessionMemberId = (String) session.getAttribute("sessionMemberId");
	if(sessionMemberId == null) {
		// 로그인이 아니면 로그인으로 보낸다
		response.sendRedirect(request.getContextPath()+"/LoginController");
		return;
	}
	
	request.setCharacterEncoding("utf-8");
	String memberId = request.getParameter("memberId");
	System.out.println("DeleteMemberController doGet : " + memberId);	// 디버깅
	
	// member 초기화
	Member member = new Member();
	member.setMemberId(memberId);
	
	// 초기화 한 member 뷰로
	request.setAttribute("member", member);
	request.getRequestDispatcher("/WEB-INF/view/DeleteMember.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// 1) request 
	String memberId = request.getParameter("memberId");
	String memberPw = request.getParameter("memberPw");
	
	//memberid, pw 디버깅
	System.out.println("DeleteMemberController doPost : " + memberId);
	System.out.println("DeleteMemberController doPost : " + memberPw);
	
	// 모델값 구하기
	MemberDao memberDao = new MemberDao();
	int row = memberDao.deleteMember(memberId, memberPw);
	System.out.println("탈퇴row " + row);
	
	// 뷰로 보내기
	if (row==1) { //성공시 SelectMemberOnecontroller으로 돌려보냄
        System.out.println("탈퇴성공");
        response.sendRedirect(request.getContextPath()+"//LogoutController");
        return;
     }else if(row==0) {// row==0이면 기본값 -1가 아니므로 오류
        System.out.println("탈퇴실패");
        response.sendRedirect(request.getContextPath()+"/UpdateMemberController");
        
	 	}
	}
}