<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>문의 게시판</title>
    <link rel="stylesheet" href="css/boardStyle.css">
</head>
<body>
<%@ include file="include/header.jsp" %>

    <!-- main body -->
   
    <main>
        <h2>문의 게시판</h2>
        <hr>
        <table class="board-table">
            <tr>
                <th>No</th>
                <th>제목</th>
                <th>작성자</th>
                <th>이메일</th>
                <th>작성일</th>
                <th>조회</th>
            </tr>
            <tr>
                <td>1</td>
                <td>무릎 통증 문의</td>
                <td>미진</td>
                <td>MJ@example.com</td>
                <td>2025-08-22</td>
                <td>12</td>
            </tr>
            <tr>
                <td>2</td>
                <td>어깨 수술 상담</td>
                <td>하임</td>
                <td>CHOI@example.com</td>
                <td>2025-08-21</td>
                <td>8</td>
            </tr>
            <tr>
                <td>2</td>
                <td>어깨 수술 상담</td>
                <td>경미</td>
                <td>km@example.com</td>
                <td>2025-08-21</td>
                <td>8</td>
            </tr>
            <tr>
                <td>2</td>
                <td>어깨 수술 상담</td>
                <td>룰루</td>
                <td>kim@example.com</td>
                <td>2025-08-21</td>
                <td>8</td>
            </tr>
        </table>
    </main>

<%@ include file="include/footer.jsp" %>
</body>
</html>