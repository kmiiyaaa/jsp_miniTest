<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link rel="stylesheet" href="css/join.css">
</head>
<body>
<%@ include file="include/header.jsp" %>

<main class="container">
    <h2 class="page-title">회원가입</h2>
    <hr>
    <form action="memberJoinOk.jsp" method="post" class="join-form">
        <label for="memberid">아이디 <input type="text" name="memberid" id="memberid" required></label>
        

        <label for="memberpw">비밀번호  <input type="password" name="memberpw" id="memberpw" required></label>

        <label for="membername">이름 <input type="text" name="membername" id="membername" required></label>
       

        <label for="memberemail">이메일 <input type="email" name="memberemail" id="memberemail" required></label>
        

        <div class="form-buttons">
            <input type="submit" value="회원가입">
            <input type="reset" value="다시작성">
        </div>
    </form>
</main>

<%@ include file="include/footer.jsp" %>
</body>
</html>