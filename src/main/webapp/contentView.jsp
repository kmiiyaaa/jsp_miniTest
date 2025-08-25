<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>게시글 상세</title>
  
  <link rel="stylesheet" href="css/boardDetail.css">
</head>
<body>
<%@ include file="include/header.jsp" %>

<main class="container">
  <div class="post-card">
    <!-- 메타 정보 영역: 번호, 작성자, 이메일, 작성일, 조회수 -->
    <div class="meta-box">
      <span><strong>번호:</strong> ${boardDto.bnum}</span> 
      <span><strong>작성자:</strong> ${boardDto.memberid}</span>
      <span><strong>이메일:</strong> ${boardDto.memberDto.memberemail}</span>
      <span><strong>작성일:</strong> ${boardDto.bdate}</span>
      <span><strong>조회수:</strong> ${boardDto.bhit}</span> 
    </div>

    <hr>

    <!-- 글 제목 -->
    <h1 class="post-title">${boardDto.btitle}</h1>

    <!-- 본문 내용 -->
    <div class="content-box">
      ${boardDto.bcontent}
    </div>

    <!-- 버튼 영역 -->
    <div class="btn-box">
      <a href="inquiry.do"><button class="btn">글목록보기</button></a>
      <a href="modify.do?bnum=${boardDto.bnum}"><button class="btn">수정</button></a>
      <a href="delete.do?bnum=${boardDto.bnum}"><button class="btn btn-delete">삭제</button></a>
    </div>
  </div>
</main>

<%@ include file="include/footer.jsp" %>
</body>
</html>