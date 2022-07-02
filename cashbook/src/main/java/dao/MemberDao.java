package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Member;

public class MemberDao {
	//회원가입
	//회원수정
	//회원탈퇴
	//회원정보
	
	// 로그인
	public String selectMemberByIdPw(Member member) {
		String memberId =null; //로그인 실패시 null이 리턴
		//DB 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//쿼리 작성
		String sql ="SELECT * FROM member "
				+ "		WHERE member_id=? "
				+ "		AND member_pw=PASSWORD(?)";
		//DB에 값 요청
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","mariadb1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			System.out.println("selectMemberByIdPw"+member);
			rs =stmt.executeQuery();
			if(rs.next()) {
				memberId=rs.getString("member_id");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						//DB자원 반납
						rs.close();
						stmt.close();
						conn.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
		
		return memberId;
	}
		//회원가입
		public int insertMember(Member member) {
			
			int row=0;
			Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql ="INSERT INTO member(member_id  , member_pw  , name  , age, create_date) VALUES ( ?, PASSWORD(?), ?, ?,  NOW())";
		    try {
		    	Class.forName("org.mariadb.jdbc.Driver");
		        conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","mariadb1234");
		        stmt = conn.prepareStatement(sql);
		        System.out.println("insertMember sql- " + stmt);	//디버깅
		        stmt.setString(1, member.getMemberId());
		        stmt.setString(2, member.getMemberPw());
		        stmt.setString(3, member.getName());
		        stmt.setInt(4, member.getAge());
		        row = stmt.executeUpdate();
		        System.out.println(row+"<--row");
				if(row == 1) {
					System.out.println("입력성공");
				} else {
					System.out.println("입력실패");
				}
		    } catch (Exception e) {
		        e.printStackTrace();
		     } finally {
		        try {
		       
					stmt.close();
					conn.close();
		        } catch (SQLException e) {
		           e.printStackTrace();
		        }
		     }
		    return row;
		}

	// 회원수정
			public int updateMemberController(Member member) {
			// 데이터베이스 자원 준비
			int row = 0;
			Connection conn = null;
			PreparedStatement stmt = null;	
			String sql = "UPDATE member SET member_pw = PASSWORD(?), name = ?, age = ?  WHERE member_id = ?";
				try {
					Class.forName("org.mariadb.jdbc.Driver");
					System.out.println("드라이버 로딩 성공"); //디버깅
					conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","mariadb1234");
					stmt = conn.prepareStatement(sql);
					System.out.println("sql  pdateMemberController : " + stmt);	//디버깅
			        stmt.setString(1, member.getMemberPw());
			        stmt.setString(2, member.getName());
			        stmt.setString(3,member.getMemberId());
			        stmt.setInt(4, member.getAge());
			        row = stmt.executeUpdate();
			        if(row == 1) { // 디버깅
						System.out.println("수정성공");
					} else {
						System.out.println("수정실패");
					}
				} catch (Exception e) {
			        e.printStackTrace();
			     } finally {
			        try {
			           conn.close();
			        } catch (SQLException e) {
			           e.printStackTrace();
			        }
			     }
				return row;
			}
			
			 public Member SelectMemberOne(String memberId) {
				 Member member = null;
				 // 자원 준비
				 Connection conn = null;
				 PreparedStatement stmt = null;
				 ResultSet rs = null;	
				 
				 String sql = "SELECT member_id memberId, name FROM member WHERE member_id = ?";
				 
				 try {
					Class.forName("org.mariadb.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","mariadb1234");
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, memberId);
					rs = stmt.executeQuery();
					
					while(rs.next()) {
						member = new Member();
						member.setMemberId(rs.getString("memberId"));
						member.setName(rs.getString("name"));
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				 
				 return member;
			 }
			
			// 회원 탈퇴
			public int deleteMember(String memberId, String memberPw) {
				int row = 0;
				// 데이터베이스 자원
				Connection conn = null;
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				ResultSet rs = null;
				
				 String sql = "SELECT cashbook_no cashbookNo FROM cashbook"
					 		+ " WHERE member_id=?";
						
				 String sql2 = "DELETE FROM cashbook"
				 		+ " WHERE member_id=?";
				 
				 String sql3 = "DELETE FROM member"
				 		+ " WHERE member_id=? AND member_pw=PASSWORD(?)";
				try {		
					Class.forName("org.mariadb.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","mariadb1234");
					conn.setAutoCommit(false); // 오토커밋 X
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, memberId);
					stmt.setString(2, memberPw);
					
					row = stmt.executeUpdate();
				
				
			} catch (Exception e) {
		        e.printStackTrace();
			  } finally {
			        try {
			           conn.close();
			        } catch (SQLException e) {
			           e.printStackTrace();
			        }
			     }
				return row;
			}
			//member 상세보기
			public Member selectMemberOne(String memberId) {
				Member member = new Member();
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				//selectMemberOne 쿼리
			    String sql ="SELECT member_id memberId, member_pw memberPw, name Name, age Age, create_date createDate FROM member WHERE member_id = ?";

				try {
					Class.forName("org.mariadb.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","mariadb1234");
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, memberId);
					System.out.println(memberId);
					rs = stmt.executeQuery();
					System.out.println("executeQuery " + rs);			
				
					if(rs.next()) {
						member = new Member();
						member.setMemberId(rs.getString("memberId"));
						member.setName(rs.getString("Name"));
						member.setAge(rs.getInt("Age"));
						member.setCreateDate(rs.getString("createDate"));
						System.out.println("selectMemberOne---->" + member);
					}
						
				} catch (Exception e) {
					e.printStackTrace();
			
					} finally {
					try {
						//DB자원 반납
						
						stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return member;
			}
}

