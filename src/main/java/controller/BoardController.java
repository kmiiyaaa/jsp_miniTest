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


@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
			 
			 
			 // 검색어가 있으면 검색된 글 수 카운트
			 if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) {
				 
				 totalContent = boardDao.countSearch(searchType, searchKeyword);
				 bDtos = boardDao.searchBoardList(searchKeyword, searchType);
				 
				 request.setAttribute("searchType", searchType);
				 request.setAttribute("searchKeyword", searchKeyword);
			 } else {
				 totalContent = boardDao.countBoard();
				 bDtos = boardDao.boardList();
			 }
				 
		
			 request.setAttribute("bDtos", bDtos);
			 
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
			
			if(boardDto.getMemberid().equals(sid)) {
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
			 
			 viewPage="contentView.do";
			 
		// 글내용 보기
		 } else if (comm.equals("/content.do")) {
				
				String bnum = request.getParameter("bnum");
				
				boardDao.updateBhit(bnum);  // 조회수 증가
				
				BoardDto boardDto =boardDao.contentView(bnum);
				
				
				if(boardDto == null) {  // 해당글 존재하지 않는 경우
					response.sendRedirect("contentView.jsp?msg=1");
					return;
				}
				
				request.setAttribute("boardDto", boardDto);
				
				viewPage="contentView.jsp";
				
		// 글 삭제	
		 } else if(comm.equals("/delete.do")) {
			 
			 String bnum = request.getParameter("bnum");
			 session = request.getSession();
			 
			 String sid = (String)session.getAttribute("sid");
			 BoardDto boardDto = boardDao.contentView(bnum);
			 
			 if(boardDto.getMemberid().equals(sid)) {  // 로그인 한 상태
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
			 
		 } else if (comm.equals("loginOk.do")) {
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
			 
		 } else if(comm.equals("index.do")) {  // 아마도 회원정보?
			 viewPage="index.jsp";
			 
		 } else {
			 viewPage="insert.jsp";
		 }

	
			
			    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			    dispatcher.forward(request, response);
			}
	

	       

}
