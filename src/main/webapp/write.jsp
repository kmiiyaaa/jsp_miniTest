<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>새 글쓰기</title>

</head>
<body>
<link rel="stylesheet" href="css/boardStyle.css">
<%@ include file="include/header.jsp" %>

<main class="container">
  <h1>새 글 작성</h1>
  <div class="panel">
    <form method="post" class="table" style="padding:1rem;">
      <div class="row" style="flex-direction:column; align-items:flex-start;">
        <label>제목</label>
        <input type="text" name="btitle" placeholder="제목을 입력하세요"
               style="width:100%; padding:0.5rem; border-radius:8px; border:1px solid #444; background:#111; color:#eee;">
      </div>

      <div class="row" style="flex-direction:column; align-items:flex-start;">
        <label>작성자</label>
        <input type="text" name="memberid" value=${sessionScope.sid} readonly="readonly"
               style="width:100%; padding:0.5rem; border-radius:8px; border:1px solid #444; background:#111; color:#eee;">
      </div>

      <div class="row" style="flex-direction:column; align-items:flex-start;">
        <label>내용</label>
        <textarea name="bcontent" rows="10" placeholder="내용을 입력하세요"
                  style="width:100%; padding:0.5rem; border-radius:8px; border:1px solid #444; background:#111; color:#eee;"></textarea>
      </div>

      <div style="margin-top:1rem; text-align:right;">
        <button type="submit" class="btn">등록하기</button>
        <a href="inquiry.do" class="btn">취소</a>
      </div>
    </form>
  </div>

</main>
</body>
</html>