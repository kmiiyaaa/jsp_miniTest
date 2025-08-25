<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/boardStyle.css">
</head>
<body>

		<div class="top-bar">
  		통증으로부터 해방! MI정형외과 입니다 :)
		</div>
<header>
    <div class="logo">MI정형외과</div>
    <nav>
       <ul>
           <li><a href="insert.jsp">홈</a></li>
           <li><a href="#">진료 안내</a></li>
           <li><a href="inquiry.do">문의 게시판</a></li>
           <!-- 로그인 여부에 따른 표시 -->
            <!-- 로그인 여부 확인 -->
            <c:choose>
                <c:when test="${not empty sessionScope.sid}">
                    <!-- 로그인 상태 -->
                    <li><a href="memberEdit.do">회원정보수정</a></li>
                    <li><a href="logout.do">로그아웃</a></li>
                </c:when>
                <c:otherwise>
                    <!-- 로그아웃 상태 -->
                    <li><a href="login.do">로그인</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
   </nav>
</header>
</body>
</html>