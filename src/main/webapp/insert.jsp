<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>MI 정형외과 :) </title>
    <link rel="stylesheet" href="css/boardStyle.css">
    <link rel="stylesheet" href="css/main.css">
</head>
<body>
<%@ include file="include/header.jsp" %>
  

    <main>
    <div class="intro1">
    저희 정형외과 홈페이지를 방문해주셔서 감사합니다.<br>  환자분들의 건강한 삶을 위해 최선을 다하겠습니다</div><br><br>
        <!-- 대표원장 소개 섹션 -->
    <section class="profile">
        <!-- 사진 -->
        <div class="photo">
            <img src="images/mj.jpg" alt="김미진 대표원장 사진">
        </div>

        <!-- 텍스트 정보 -->
        <div class="info">
            <h2>김미진 대표원장</h2>
            <h3>정형외과 전문의</h3>
            <p class="bio">
                환자의 건강과 빠른 회복을 최우선으로 생각하며, 전문성과 따뜻한 진료를 바탕으로 최선을 다하고 있습니다.
            </p>

            <div class="career">
                <h4>약력</h4>
                <ul>
                    <li>00대학교 의과대학 졸업</li>
                    <li>00대학교병원 정형외과 전문의 수료</li>
                    <li>대한정형외과학회 정회원</li>
                    <li>현 MI 정형외과 대표원장</li>
                </ul>
            </div>
        </div>
    </section>
    <!-- 소개 섹션 -->
    <section class="intro-sections">
        <div class="card">
            <h3>진료과목</h3>
            <p>
                정형외과<br>
                척추 클리닉<br>
                관절 클리닉<br>
                재활치료
            </p>
        </div>
        <div class="card">
            <h3>진료안내</h3>
            <p>
                평일: 09:00 ~ 18:00<br>
                토요일: 09:00 ~ 13:00<br>
                (일요일·공휴일 휴무)<br>
                점심시간: 12:30 ~ 13:30
            </p>
        </div>
        <div class="card">
            <h3>문의게시판</h3>
            <p>
                온라인으로 상담 및 문의가 가능합니다.<br>
                <a href="inquiry.do" class="btn">바로가기</a>
            </p>
        </div>
    </section>


</main>

<%@ include file="include/footer.jsp" %>

</body>
</html>