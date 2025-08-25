<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>문의 게시판</title>
    <link rel="stylesheet" href="css/boardStyle.css">
</head>
<body>
<%@ include file="include/header.jsp" %>

<h2 style="text-align: center;">문의 게시판</h2>
<hr>
<div class="btn-box">
    <a href="write.do"><button class="btn">글쓰기</button></a>
</div>

<!-- 검색 영역 -->
<form action="inquiry.do" method="get" class="search-form">
    <select name="searchType">
        <option value="btitle" ${searchType=='btitle' ? 'selected':''}>제목</option>
        <option value="memberid" ${searchType=='memberid' ? 'selected':''}>작성자</option>
        <option value="bcontent" ${searchType=='bcontent' ? 'selected':''}>내용</option>
    </select>
    <input type="text" name="searchKeyword" value="${searchKeyword}" placeholder="검색어를 입력하세요">
    <button type="submit">검색</button>
</form>

<!-- 게시판 리스트 -->
<table class="board-table">
    <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>조회수</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="bDto" items="${bDtos}">
            <tr>
                <td>${bDto.bno}</td>
                <td>
                    <c:choose>
                        <c:when test="${fn:length(bDto.btitle) > 35}">
                            <a href="content.do?bnum=${bDto.bnum}">${fn:substring(bDto.btitle, 0, 35)}...</a>
                        </c:when>
                        <c:otherwise>
                            <a href="content.do?bnum=${bDto.bnum}">${bDto.btitle}</a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${bDto.memberid}</td>
                <td>${bDto.bdate}</td>
                <td>${bDto.bhit}</td>
            </tr>
        </c:forEach>

        <c:if test="${empty bDtos}">
            <tr>
                <td colspan="5" style="text-align:center;">등록된 글이 없습니다.</td>
            </tr>
        </c:if>
    </tbody>
</table>

<!-- 페이지네이션 -->
<div class="pagination">
    <!-- 첫번째 페이지 -->
    <c:if test="${currentPage > 1}">
        <a href="inquiry.do?page=1&searchType=${searchType}&searchKeyword=${searchKeyword}">◀◀</a>
    </c:if>

    <!-- 이전 그룹 -->
    <c:if test="${startPage > 1}">
        <a href="inquiry.do?page=${startPage - 1}&searchType=${searchType}&searchKeyword=${searchKeyword}">◀</a>
    </c:if>

    <!-- 페이지 번호 출력 -->
    <c:forEach begin="${startPage}" end="${endPage}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}">
                <a class="active" href="inquiry.do?page=${i}&searchType=${searchType}&searchKeyword=${searchKeyword}">${i}</a>
            </c:when>
            <c:otherwise>
                <a href="inquiry.do?page=${i}&searchType=${searchType}&searchKeyword=${searchKeyword}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <!-- 다음 그룹 -->
    <c:if test="${endPage < totalPage}">
        <a href="inquiry.do?page=${endPage + 1}&searchType=${searchType}&searchKeyword=${searchKeyword}">▶</a>
    </c:if>

    <!-- 마지막 페이지 -->
    <c:if test="${currentPage < totalPage}">
        <a href="inquiry.do?page=${totalPage}&searchType=${searchType}&searchKeyword=${searchKeyword}">▶▶</a>
    </c:if>
</div>

<%@ include file="include/footer.jsp" %>
</body>
</html>