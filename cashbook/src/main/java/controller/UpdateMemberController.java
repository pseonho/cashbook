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

@WebServlet("/UpdateMemberController")
public class UpdateMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// 접속허가체크
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) {
			// 이미 로그인이 되어 있는 상태라면
			response.sendRedirect(request.getContextPath()+"/LoginController");
			return;
		}
	
	// 요청값 분석(c)
	request.setCharacterEncoding("utf-8");
	String memberId = request.getParameter("memberId");
	String memberPw = request.getParameter("memberPw");
	String name = request.getParameter("name");


	
	// 디버깅
	System.out.println("updateMemberController before : " + memberId);
	System.out.println("updateMemberController before : " + memberPw);
	System.out.println("updateMemberController before : " + name);

	
	// 한번에 보내기
	Member member = new Member();
	member.setMemberId(memberId);
	member.setMemberPw(memberPw);
	member.setName(name);

	
	// 뷰로 보낼준비
	request.setAttribute("member", member);
	
	// 뷰 포워딩(c)
	request.getRequestDispatcher("/WEB-INF/view/UpdateMemberForm.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");
	
	// 1) request 분석(C)
	String memberId = request.getParameter("memberId");
	String memberPw = request.getParameter("memberPw");
	String name = request.getParameter("name");
	
	
	// 비번 틀릴경우 아뒤변경 안할경우
	if(!memberPw.equals("1234") && memberId.equals("")) {
		response.sendRedirect(request.getContextPath()+"/SelectMemberOneController");
		return;
	}
	
	// 디버깅
	System.out.println("updateMemberController After : " + memberId);
	System.out.println("updateMemberController After : " + memberPw);
	System.out.println("updateMemberController After : " + name);

	// 메서드 이용하여 모델값 구하기(M)
	Member member = new Member();
	member.setMemberId(memberId);
	member.setMemberPw(memberPw);
	member.setName(name);

	
	MemberDao memberDao = new MemberDao();
	int row = memberDao.updateMemberController(member);
	System.out.println("수정row " + row);
	
	// 원래있던곳으로 돌아가기(뷰로 돌아가기)
	if (row==1) { //성공시 SelectMemberOnecontroller으로 돌려보냄
        System.out.println("수정성공");
        response.sendRedirect(request.getContextPath()+"/SelectMemberOneController");
        return;
     }else if(row==0) {// row==0이면 영향받은 행이 없으므로 (row 기본값 -1), 비밀번호 오류
        System.out.println("수정실패");
        response.sendRedirect(request.getContextPath()+"/UpdateMemberController?msg=fail!!");
        
     }else if (row==-1) {//row가 -1이면 sql이 작동 안함
        System.out.println("수정예외발생");
        response.sendRedirect(request.getContextPath()+"/UpdateMemberController?msg=exception!!");
     }
	}

}