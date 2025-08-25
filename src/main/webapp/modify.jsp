<%@page import="dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%
	BoardDto boardDto = (BoardDto) request.getAttribute("boardDto");
	boolean isEdit = (boardDto != null);

	if(request.getParameter("error") != null) {
    out.println("<script>alert('수정 또는 삭제 권한이 없는 글입니다.');history.go(-1);</script>");
    return;  // 페이지 실행 중단
}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title><%= isEdit ? "글 수정" : "글 작성" %></title>
<link rel="stylesheet" href="css/edit.css">
</head>
<body>
<%@ include file="include/header.jsp" %>

<main class="board-container">
    <h2><%= isEdit ? "게시글 수정" : "게시글 작성" %></h2>

    <form action="<%= isEdit ? "modifyOk.do" : "writeOk.do" %>" method="post" class="edit-form">
        <% if(isEdit) { %>
            <input type="hidden" name="bnum" value="<%= boardDto.getBnum() %>">
        <% } %>

        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="btitle" value="<%= isEdit ? boardDto.getBtitle() : "" %>" required />
        </div>

        <div class="form-group">
            <label for="author">작성자</label>
            <input type="text" id="author" name="memberid" value="<%= isEdit ? boardDto.getMemberid() : "" %>" readonly />
        </div>

        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" name="bcontent" rows="10" required><%= isEdit ? boardDto.getBcontent() : "" %></textarea>
        </div>

        <div class="form-buttons">
            <button type="submit" class="btn btn-primary"><%= isEdit ? "수정 저장" : "등록" %></button>
            <a href="javascript:history.go(-1)" class="btn btn-secondary">취소</a>
        </div>
    </form>
</main>

<%@ include file="include/footer.jsp" %>
</body>
</html>