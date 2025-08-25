<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="css/boardStyle.css">
    
</head>
<body>

<%@ include file="include/header.jsp" %>
<main>
        <div class="login-container">
            <form action="loginOk.do" method="post">
                <input type="text" name="id" placeholder="아이디" required>
                <input type="password" name="pw" placeholder="비밀번호" required>

                <div class="login-links">
                    <a href="#">아이디찾기</a>
                    <a href="#">비밀번호찾기</a>
                    <a href="memberJoin.jsp">회원가입</a>
                </div>

                <button type="submit">로그인</button>

                <div class="login_links" align="center">
                    <c:if test="${param.msg == 1}">
                        <p style="color:red;">아이디 또는 비밀번호가 잘못 되었습니다.</p>
                    </c:if>
                    <c:if test="${param.msg == 2}">
                        <p style="color:red;">로그인 한 유저만 글쓰기 가능</p>
                    </c:if>
                    <c:if test="${param.msg == 3}">
										    <p style="color:green;">로그아웃 되었습니다.</p>
										</c:if>
                </div>
            </form>
        </div>
    </main>


 <%@ include file="include/footer.jsp" %>
 
</body>
</html>