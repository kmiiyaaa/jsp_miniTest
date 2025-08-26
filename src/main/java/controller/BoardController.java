package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.BoardDao;
import dao.MemberDao;
import dto.BoardDto;
import dto.CommentDto;
import dto.MemberDto;


@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final int PAGE_GROUP_SIZE = 10 ; 
       
    public BoardController() {
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		String conpath = request.getContextPath();
		String comm = uri.substring(conpath.length());
		System.out.println(comm);
		
		String viewPage = null;
		BoardDao boardDao = new BoardDao();
		MemberDao memberDao = new MemberDao();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		HttpSession session = null;

	
		 if (comm.equals("/inquiry.do")) {  
			 String searchType = request.getParameter("searchType");
			 String searchKeyword = request.getParameter("searchKeyword");
			 int totalContent = boardDao.countBoard();
			 int page = 1;
			 
			 

		if(request.getParameter("page") == null) {   // 참이면 링크타고 게시판으로 들어온 경우
			page = 1;  // 무조건 첫페이지를 보여주게 됨
				
		} else { //유저가 보고 싶은 페이지 번호를 클릭한 경우
			page = Integer.parseInt(request.getParameter("page"));  //유저가 클릭한 유저가 보고 싶어하는 페이지의 번호
		}
							
			 
	 // 검색어가 있으면 검색된 글 수 카운트
		if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) {
				 
		 totalContent = boardDao.countSearch(searchType, searchKeyword);
		 bDtos = boardDao.searchBoardList(searchKeyword, searchType, page);
				 
		 request.setAttribute("searchType", searchType);
		request.setAttribute("searchKeyword", searchKeyword);
		} else {
		 totalContent = boardDao.countBoard();
		 bDtos = boardDao.boardList(page);
		 
		 
	 }
		
		int totalPage = (int) Math.ceil((double)totalContent / BoardDao.PAGE_SIZE ) ;
		int startPage = (((page-1) / PAGE_GROUP_SIZE) * PAGE_GROUP_SIZE)+1 ;
		int endPage = startPage + (PAGE_GROUP_SIZE -1); // 게시글 없는 페이지까지 출력된다
		
		
		
		//마지막 페이지 그룹인 경우에는 실제마지막 페이지로 표시
		// 글갯수 437 - 44페이지 , 실제 endpage는 44로변경
		if (endPage > totalPage) {
			endPage = totalPage;  //  totalPage = 실제 마지막 페이지값 (437 -> 44)
		}
	
				 
      request.setAttribute("bDtos", bDtos);  // 위에 둘중하나 경우를 request 객체에 싣고 포워딩해주는거
	  request.setAttribute("currentPage", page);
	  request.setAttribute("totalPage", totalPage);
	  request.setAttribute("startPage", startPage);
      request.setAttribute("endPage", endPage); // 페이지 그룹 출력시 마지막 페이지번호	
		
			 
			 viewPage = "inquiryBoard.jsp";
			 
		 
		 // 글쓰기 폼 이동
		 }else if(comm.equals("/write.do")) {
			 
			 session = request.getSession();
			 String sid = (String)session.getAttribute("sid");
			 
			 if(sid != null) {
				 viewPage = "write.jsp";
			 } else {
				 response.sendRedirect("login.do?msg=2");
				 return;
			 }
			 
			 
		//글 수정 폼 이동
		 } else if(comm.equals("/modify.do")) {
			 
			 request.setCharacterEncoding("utf-8");
			 
			 session = request.getSession(false);  // 기존 세션 가져오기 , 없으면 null
			 String sid = (String) session.getAttribute("sid");
			 
			String bnum = request.getParameter("bnum");
			BoardDto boardDto = boardDao.contentView(bnum);
			
			if(boardDto.getMemberid().equals(sid) || "admin".equals(sid)) {
				request.setAttribute("boardDto", boardDto);
				viewPage =  "modify.jsp";
			} else {
				response.sendRedirect("modify.jsp?msg=1");
				return;			 
		 }	 

	// 수정후 페이지 이동
		 } else if(comm.equals("/modifyOk.do")) {
			 
			 String bnum = request.getParameter("bnum");
			 String btitle = request.getParameter("btitle");
			 String memberid = request.getParameter("memberid");
			 String bcontent = request.getParameter("bcontent");
			 
			 boardDao.boardUpdate(bnum, btitle, bcontent);
			 
			 BoardDto boardDto = boardDao.getBoardDetail(bnum);
			 request.setAttribute("boardDto", boardDto);
			 
			 viewPage="content.do";
			 
		// 글내용 보기
		 } else if (comm.equals("/content.do")) {
				
			 String bnum = request.getParameter("bnum");
			    boardDao.updateBhit(bnum);  // 조회수 증가
			    
			    BoardDto boardDto = boardDao.contentView(bnum);
			    
			    if(boardDto == null) {  // 해당글 존재하지 않는 경우
			        response.sendRedirect("contentView.jsp?msg=1");
			        return;
			    }
			    
			    // 게시글 DTO 전달
			    request.setAttribute("boardDto", boardDto);

			    // 댓글 목록 가져오기 추가!
			    List<CommentDto> commentDtos = boardDao.commentList(bnum);
			    request.setAttribute("commentDtos", commentDtos);
			    
			    viewPage="contentView.jsp";
				
		// 글 삭제	
		 } else if(comm.equals("/delete.do")) {
			 
			 String bnum = request.getParameter("bnum");
			 session = request.getSession();
			 
			 String sid = (String)session.getAttribute("sid");
			 BoardDto boardDto = boardDao.contentView(bnum);
			 
			 if(boardDto.getMemberid().equals(sid) || "admin".equals(sid)) {  // 로그인 한 상태
				 boardDao.boardDelete(bnum);
				 viewPage="inquiry.do";
			 } else {
				 response.sendRedirect("modify.jsp?error=1");
				 return;
			 }
			 			
			 viewPage="inquiry.do";
			 
			 
// 글 입력
		 } else if(comm.equals("/writeOk.do")){
			 
			    String btitle = request.getParameter("btitle");
			    String bcontent = request.getParameter("bcontent");
			    
			    // 세션에서 로그인한 ID 가져오기
			    session = request.getSession(false);
			    String memberid = (String) session.getAttribute("sid");
			    
			    if(memberid == null) { // 로그인 안 되어있으면
			        response.sendRedirect("login.do?msg=2");
			        return;
			    }
			    
			    boardDao.boardWrite(btitle, bcontent, memberid);
			    
			    response.sendRedirect("inquiry.do");
			    return;
			 
		// 로그인 페이지로 이동
		 }	else if(comm.equals("/login.do")) {
			 
			 viewPage ="login.jsp";
			 
		 } else if (comm.equals("/loginOk.do")) {
			 request.setCharacterEncoding("utf-8");
			 
			 String loginId = request.getParameter("id");
			 String loginPw = request.getParameter("pw");
			 
			 int loginFlag = memberDao.loginCheck(loginId, loginPw);
			 if(loginFlag ==1) {
				 session = request.getSession();
				 session.setAttribute("sid", loginId);
			 } else {
				 response.sendRedirect("login.do?msg=1");
				 return;
			 }
			 
			 viewPage = "inquiry.do";
			 
		 } else if (comm.equals("/logout.do")) {
				    session = request.getSession(false);
				    if(session != null) {
				        session.invalidate(); // 세션 전체 삭제
				    }
				    response.sendRedirect("login.do?msg=3"); // 로그아웃 완료 메시지
				    return;
				}
		 
		  
			 
	// 회원정보 수정 폼 이동
		 else if(comm.equals("/memberEdit.do")) {
			    session = request.getSession(false); // 기존 세션 가져오기
			    String sid = (session != null) ? (String) session.getAttribute("sid") : null;

			    if (sid != null) {
			        MemberDto member = memberDao.getMember(sid);
			        request.setAttribute("memberdto", member);
			        viewPage = "memberEdit.jsp";
			        
			    } else {
			        response.sendRedirect("login.do?msg=2");
			        return;
			    }
		     }

		 // 회원정보 수정 완료
		 else if (comm.equals("/memberEditOk.do")) {

			 request.setCharacterEncoding("utf-8");
			  // 수정된 데이터 처리
	            String memberid = request.getParameter("memberid");
	            String name = request.getParameter("membername");
	            String email = request.getParameter("memberemail");
	            String pw = request.getParameter("memberpw");

	            MemberDto member = new MemberDto(memberid, name, email, pw);
	            int result = memberDao.updateMember(member);

	            if (result > 0) {
	                request.setAttribute("msg", "회원정보가 수정되었습니다.");
	                viewPage = "memberEdit.jsp"; // 다시 수정페이지 or 마이페이지
	            } else {
	                request.setAttribute("msg", "수정 실패. 다시 시도해주세요.");
	                viewPage = "memberEdit.jsp";
	            }
	        
		 } else if(comm.equals("/commentOk.do")) { //댓글 등록요청
 request.setCharacterEncoding("utf-8");
			 
			 String memberid = request.getParameter("memberid");        // form에서 보낸 데이터 꺼내기
			 String memberpw = request.getParameter("memberpw");
			 String membername = request.getParameter("membername");
			 String memberemail = request.getParameter("memberemail");
			 
			 MemberDto member = new MemberDto(memberid, memberpw, membername, memberemail);     // DTO에 담기
			 		
			int result = memberDao.updateMember(member);   	// DAO 호출해서 DB 업데이트 실행
					 
			 if (result > 0) {
			        request.setAttribute("msg", "회원정보가 수정되었습니다.");
			    } else {
			        request.setAttribute("msg", "수정 실패. 다시 시도해주세요.");
			    }
			 
			 request.setAttribute("memberDto", member);   // 다시 수정 페이지로 이동 (수정된 값 보여주기)
			 viewPage = "memberEdit.jsp";
			 
			
		 } else {
			 viewPage="insert.jsp";
		 }

	
			
			    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			    dispatcher.forward(request, response);
			}
	

	       

}
