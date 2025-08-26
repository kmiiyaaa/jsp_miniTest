<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="dto.MemberDto" %>
<%
    MemberDto member = (MemberDto) request.getAttribute("memberDto"); 
 //컨트롤러에서 request.setAttribute("memberDto", member) 했기 때문에  memberDto 로 바로 접근 가능
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
<link rel="stylesheet" href="css/boardStyle.css">
<link rel="stylesheet" href="css/edit.css">
</head>
<body>
<%@ include file="include/header.jsp" %>
<h2>회원정보 수정</h2>
<hr>

<form action="memberEditOk.do" method="post">
    아이디 : <input type="text" name="memberid" value="<%=member.getMemberid()%>" readonly><br><br>
    이름 : <input type="text" name="membername" value="<%=member.getMembername()%>"><br><br>
    이메일 : <input type="email" name="memberemail" value="<%=member.getMemberemail()%>"><br><br>
    비밀번호 : <input type="password" name="memberpw" value="<%=member.getMemberpw()%>"><br><br>
    <input type="submit" value="수정하기">
</form>

<c:if test="${not empty msg}">
    <p style="color:red;">${msg}</p>
</c:if>

<%@ include file="include/footer.jsp" %>
</body>
</html>