<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
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
      <span><strong>이메일:</strong> ${boardDto.memberDto != null ? boardDto.memberDto.memberemail : ''}</span>
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

  <c:if test="${not empty sessionScope.sid and (sessionScope.sid == boardDto.memberid or sessionScope.sid == 'admin')}">
      <a href="modify.do?bnum=${boardDto.bnum}"><button class="btn">수정</button></a>
      <a href="delete.do?bnum=${boardDto.bnum}"><button class="btn btn-delete">삭제</button></a>
  </c:if>
</div>
  </div>

	<div>
			<hr>
			<h3>댓글</h3>
			<hr>
		<form action="commentOk.do">
  		<input type="hidden" name="bnum" value="${boardDto.bnum}"><!-- 원글의 번호 반드시 전송 -->
  		<input type="text" name="comment" size="80">
  		<input type="submit" value="작성완료">
  	</form>
	</div>
	<div>
		<hr>
		<c:forEach items="${commentDtos }" var="commentDto">
			<h4>작성자 : ${commentDto.memberid }</h4>
			<h4>내용 : ${commentDto.comment }</h4>
			<h4>작성일시 : ${commentDto.cdate }</h4>
		</c:forEach>
	</div>
<%@ include file="include/footer.jsp" %>
</main>


</body>
</html>