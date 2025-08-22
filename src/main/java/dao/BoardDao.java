package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.BoardDto;
import dto.MemberDto;

public class BoardDao {
	
	private String driverName = "com.mysql.jdbc.Driver";   // MYSQL JDBC 드라이버 이름 - mysql사용시 fix값 고정
	private String url = "jdbc:mysql://localhost:3306/jspdb";   // MYSQL이 설치된 서버의 주소(ip)와 연결할 db(스키마) 이름
	private String userName = "root";
	private String password = "12345";	
		
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	//게시판에 모든글 가져오기
	public List<BoardDto> boardList() {
	
	String sql = "SELECT * FROM board b LEFT JOIN members m ON b.memberid = m.memberid";
	
	List<BoardDto> boardDto = new ArrayList<BoardDto>();
	
	try {
		
	  Class.forName(driverName); 
	  conn = DriverManager.getConnection(url, userName, password);
	  pstmt = conn.prepareStatement(sql);
	  
	  rs = pstmt.executeQuery();
	  
	  while(rs.next()) {
		  
		  int bnum = rs.getInt("bnum");
		  String btitle = rs.getString("btitle");
		  String bcontent = rs.getString("bcontent");
		  String memberid = rs.getString("memberid");
		  Stirng memberemail = rs.getString("memberemail");
		  int bhit = rs.getInt("bhit");
		  String bdate = rs.getString("bdate");
		 // int bno : 게시판 보이는 숫자 정렬
		  
		  MemberDto memberDto = new MemberDto();
		  
		  
	  }
	  
	
	} catch(Exception e){
	  
	  e.printStackTrace();
	  System.out.println("게시글 불러오기 실패");
		
		
	} finally {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
	}	
	

}
