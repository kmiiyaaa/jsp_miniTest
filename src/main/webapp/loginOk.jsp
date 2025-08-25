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
	
		String mid = request.getParameter("loginid");
		String mpw = request.getParameter("loginpw");
		
		MemberDao memberDao = new MemberDao();
		int loginResult = 	memberDao.loginCheck(mid, mpw);  // 1 반환되면 로그인성공처리, 0 반환시 로그인실패처리
	
		
		if(loginResult == MemberDao.LOGIN_SUCESS) {
			session.setAttribute("sid", mid );  // 로그인 성공한 아이디 set
			out.println("<script>alert('회원 로그인 성공');location.href='insert.jsp'</script>");
			
		} else {
			out.println("<script>alert('회원 로그인 실패');history.go(-1);</script>");
		}
		
	%>


</body>
</html>