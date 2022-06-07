package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashtagDao {
	 public List<Map<String,Object>> selectTagRankList() {
	      List<Map<String,Object>> list = new ArrayList<>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      try {
	         /*
	             SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) ranking
	            FROM 
	            (SELECT tag, COUNT(*) cnt
	            FROM hashtag
	            GROUP BY tag) t
	          */
	         Class.forName("org.mariadb.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         String sql = "SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) rank"
	               + "            FROM"
	               + "            (SELECT tag, COUNT(*) cnt"
	               + "            FROM hashtag"
	               + "            GROUP BY tag) t";
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("tag", rs.getString("tag"));
	            map.put("cnt", rs.getInt("t.cnt"));
	            map.put("rank", rs.getInt("rank"));
	            list.add(map);
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
	      return list;
	   }

//TagCategoryController
		public List<HashMap<String, Object>> selectTagCategoryRankList(String tag) {
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				/*
				 	SELECT h.tag, c.cashbook_no, c.cash, c.kind, c.memo, c.cash_date 
					FROM hashtag h
					INNER JOIN cashbook c
					ON h.cashbook_no = c.cashbook_no
					WHERE tag = ? AND c.cashbook_no = ?
				 */
				conn = DriverManager.getConnection("jdbc:mariadb://3.39.254.208/cashbook","root","mariadb1234");
				String sql = "SELECT h.tag tag, c.cashbook_no cashbookNo, c.cash cash, c.kind, c.memo, c.cash_date cashDate "
						+ "				FROM hashtag h "
						+ "				INNER JOIN cashbook c "
						+ "				ON h.cashbook_no = c.cashbook_no "
						+ "				WHERE tag = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, tag);
				rs = stmt.executeQuery();
				while(rs.next())  {
					HashMap<String,Object> map = new HashMap<>();
					map.put("tag", rs.getString("tag"));
					map.put("cashbookNo", rs.getInt("cashbookNo"));
					map.put("cash", rs.getInt("cash"));
					map.put("kind", rs.getString("kind"));
					map.put("memo", rs.getString("memo"));
					map.put("cashDate", rs.getString("cashDate"));
					list.add(map);
				}
				System.out.println("selectTagCategoryRankList list.size : " + list.size());
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
				
			return list;
		}
}
