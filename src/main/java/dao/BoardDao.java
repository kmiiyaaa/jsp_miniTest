package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.BoardDto;
import dto.CommentDto;
import dto.MemberDto;

public class BoardDao {
	
	private String driverName = "com.mysql.jdbc.Driver";   // MYSQL JDBC 드라이버 이름 - mysql사용시 fix값 고정
	private String url = "jdbc:mysql://localhost:3306/jspdb";   // MYSQL이 설치된 서버의 주소(ip)와 연결할 db(스키마) 이름
	private String userName = "root";
	private String password = "12345";	
		
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public static final int PAGE_SIZE = 10;
	
	
	// 게시판 모든글 가져오기
	public List<BoardDto> boardList(int page) {
		
		String sql = "SELECT ROW_NUMBER() OVER (ORDER BY b.bnum ASC) AS bno, " +
				" b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, " +
				" b.bhit, b.bdate " +
				" FROM board b " +
				" LEFT JOIN members m ON b.memberid = m.memberid " +
				" ORDER BY b.bnum DESC " +
				"LIMIT ? OFFSET ?";
		
		List<BoardDto> bDtos =  new ArrayList<>();
		
		int offset = (page -1) * PAGE_SIZE;
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PAGE_SIZE);
			pstmt.setInt(2, offset);
			
			rs = pstmt.executeQuery();
			  
			while(rs.next()) {
				int bno = rs.getInt("bno");
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				String memberemail = rs.getString("memberemail");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				  
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid);
				memberDto.setMemberemail(memberemail);
				  
				BoardDto bDto = new BoardDto(bno, bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
				bDtos.add(bDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
		return bDtos;
	}
	
	// 글 개수
	public int countBoard() {
		String sql = "SELECT COUNT(*) FROM board";
		int count = 0;
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			  
			if(rs.next()) {
				count = rs.getInt(1);  
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return count;
	}
	
	
	// 글 상세보기
	public BoardDto contentView(String boardnum) {
		
		String sql = "SELECT ROW_NUMBER() OVER (ORDER BY b.bnum ASC) AS bno, " +
				" b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, " +
				" b.bhit, b.bdate " +
				" FROM board b " +
				" LEFT JOIN members m ON b.memberid = m.memberid " +
				" WHERE bnum=? ";
		
		BoardDto bDto = null;
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardnum);
			rs = pstmt.executeQuery();
			  
			if(rs.next()) {
				int bno = rs.getInt("bno");
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				String memberemail = rs.getString("memberemail");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				  
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid);
				memberDto.setMemberemail(memberemail);
				  
				bDto = new BoardDto(bno, bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
		return bDto;
	}

	// 글 하나 불러오기
	public BoardDto getBoardDetail(String num) {
		String sql = "SELECT * FROM board WHERE bnum=?";
		BoardDto dto = null;
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
		  
			if(rs.next()) {
				dto = new BoardDto();
				dto.setBnum(rs.getInt("bnum"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setMemberid(rs.getString("memberid"));
				dto.setBhit(rs.getInt("bhit"));
				dto.setBdate(rs.getString("bdate"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
		return dto;
	}
	
	// 검색된 글 개수
	public int countSearch(String searchType, String searchKeyword) {
		String sql = "SELECT COUNT(*) FROM board WHERE " + searchType + " LIKE ?";
		int count = 0;
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + searchKeyword + "%");
			rs = pstmt.executeQuery();
			  
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
		return count;
	}
	
	// 글쓰기
	public void boardWrite(String btitle, String bcontent, String memberid ) {
		String sql = "INSERT INTO board(btitle, bcontent, memberid, bhit) VALUES(?,?,?,0)"; 
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontent);
			pstmt.setString(3, memberid);
			  
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
	}
		
	// 글수정
	public void boardUpdate(String bnum, String btitle, String bcontent) {
		String sql = "UPDATE board SET btitle=?, bcontent=? WHERE bnum=?";
		
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontent);
			pstmt.setString(3, bnum);
			  
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
	}
		
	// 글 삭제
	public void boardDelete(String bnum) {
		String sql = "DELETE FROM board WHERE bnum=?";
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bnum);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
	}
			
	// 조회수 증가
	public void updateBhit(String bnum) {
		String sql = "UPDATE board SET bhit = bhit+1 WHERE bnum=?";
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bnum);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
	}
	

	
	//댓글
		public void commentWrite(String bnum, String memberid, String comment ) {
			String sql = "INSERT INTO comment(bnum,memberid,comment) VALUES(?,?,?)"; 
			
			
			
			try {
				Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
				conn = DriverManager.getConnection(url, userName, password);
				//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
				
				pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
				pstmt.setString(1, bnum);
				pstmt.setString(2, memberid);
				pstmt.setString(3, comment);			
				
				pstmt.executeUpdate(); //성공하면 sqlResult 값이 1로 변환
				// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
				
			} catch (Exception e) {
				System.out.println("DB 에러 발생! 게시판 댓글 등록 실패!");
				e.printStackTrace(); //에러 내용 출력
			}finally {
				closeAll();
			}
		}
	

	// 게시판 검색
	public List<BoardDto> searchBoardList(String searchKeyword, String searchType, int page) {
	
		String sql = "SELECT  ROW_NUMBER() over (ORDER BY bnum ASC) as bno, "
				   + "b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bdate, b.bhit "
		           + "FROM board b "
		           + "LEFT JOIN members m ON b.memberid = m.memberid"
		           + " WHERE " + searchType + " LIKE ?"   // + " WHERE b.btitle LIKE ?" // 제목만 검색시
		           + " ORDER BY bno DESC LIMIT ? OFFSET ?";
		
		List<BoardDto> bDtos = new ArrayList<>();
		int offset = (page-1) * PAGE_SIZE;
	 
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
		 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+ searchKeyword + "%");
			pstmt.setInt(2, PAGE_SIZE);
			pstmt.setInt(3, offset); //0,10,20,...	
			rs = pstmt.executeQuery();  // 모든 글 리스트(레코드) 반환
		  
			while(rs.next()) {
				int bno = rs.getInt("bno");
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				String memberemail = rs.getString("memberemail");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
			   
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid);
				memberDto.setMemberemail(memberemail);
			  
				BoardDto bDto = new BoardDto(bno, bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
				bDtos.add(bDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
		return bDtos;
	}
	
	// 해당 원글에 달린 댓글 가져오는 메서드
	public List<CommentDto> commentList(String bcnum) {
	
		String sql = "SELECT * FROM comment WHERE bnum=?";
				  
		List<CommentDto> commentDtos = new ArrayList<CommentDto>();		
	 
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(url, userName, password);
		 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,bcnum);
		
			rs = pstmt.executeQuery();  // 모든 글 리스트(레코드) 반환
		  
			while(rs.next()) {
				int cnum = rs.getInt("cnum");
				int bnum = rs.getInt("bnum");				
				String memberid = rs.getString("memberid");
				String comment = rs.getString("comment");				
				String cdate = rs.getString("cdate");				
				
				CommentDto commentDto = new CommentDto(cnum, bnum, memberid, comment, cdate);
				commentDtos.add(commentDto);
			}
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 목록 가져오기 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(rs != null) { //rs가 null 이 아니면 닫기(pstmt 닫기 보다 먼저 실행)
					rs.close();
				}				
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return commentDtos; //모든 글(bDto) 여러 개가 담긴 list인 bDtos를 반환
	}
	
	

	
	// 공통 close 메서드
	private void closeAll() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}