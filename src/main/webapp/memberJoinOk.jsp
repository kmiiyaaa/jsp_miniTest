<%@page import="dto.MemberDto"%>
<%@page import="dto.BoardDto"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<% 
MemberDto memberDto = new MemberDto();
memberDto.setMemberid(request.getParameter("memberid"));
memberDto.setMemberpw(request.getParameter("memberpw"));
memberDto.setMembername(request.getParameter("membername"));
memberDto.setMemberemail(request.getParameter("memberemail"));



			MemberDao memberDao = new MemberDao();
			
			int idCheck = memberDao.confirmId(memberDto.getMemberid());
			
			if(idCheck == MemberDao.MEMBER_ID_EXISTENT) { //이미 아이디 존재->가입불가
				out.println("<script>alert('이미 아이디가 존재합니다.');history.go(-1);</script>");	
			} else {
				int result = memberDao.insertMember(memberDto);
				// 1(성공) 또는 0(실패) 반환
				if(result == MemberDao.MEMBER_JOIN_SUCCESS) {
					out.println("<script>alert('회원 가입 성공!');</script>");
					
				} else {
					out.println("<script>alert('회원 가입 실패!');history.go(-1);</script>");
				}
			}


%>

</body>
</html>