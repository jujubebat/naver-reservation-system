<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="utf-8">
<meta name="description"
	content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<title>네이버 예약</title>
<link rel="stylesheet" href="../reservation/css/bookinglogin.css">
</head>

<body>
	<div id="container">
		<!-- [D] 예약하기로 들어오면 header에 fade 클래스 추가로 숨김 -->
		<div class="header fade">
			<header class="header_tit">
				<h1 class="logo">
					<a href="#" class="lnk_logo" title="네이버"> <span
						class="spr_bi ico_n_logo">네이버</span>
					</a> <a href="#" class="lnk_logo" title="예약"> <span
						class="spr_bi ico_bk_logo">예약</span>
					</a>
				</h1>
				<a href="#" class="btn_my"> <span title="예약확인">예약확인</span>
				</a>
			</header>
		</div>
		<div class="ct">
			<div class="wrap_review_list">
				<div class="review_header">
					<div class="top_title gr">
						<!-- 이렇게 하면 뒤로가기 쉽게 구현 가능-->
						<a href="#" class="btn_back" onClick="history.back()"> <i
							class="fn fn-backward1"></i>
						</a>
						<h2>
							<a class="title" href="#">오디컴퍼니 주식회사</a>
						</h2>
					</div>
				</div>
				<div class="section_review_list">
					<div class="review_box">
						<h3 class="title_h3">예매자 한줄평</h3>
						<div class="short_review_area">
							<ul class="list_short_review">
								<p class="guide">
									<i class="spr_book2 ico_bell"></i> <span>네이버 예약을 통해 실제
										방문한 이용자가 남긴 평가입니다.</span>
								</p>
						</div>
					</div>
				</div>
			</div>
			<hr>
		</div>
		<footer>
			<div class="gototop">
				<a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span>
				</a>
			</div>
			<div id="footer" class="footer">
				<p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및
					환불 등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
				<span class="copyright">© NAVER Corp.</span>
			</div>
		</footer>

		<script src="../reservation/js/review.js"></script>

		<!-- handler 라이브러리 -->
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"
			integrity="sha256-usTqAE1ywvdMtksWzdeWzD75MsfJN0h0U7y2NtZL3N0="
			crossorigin="anonymous"></script>

		<!-- 댓글점수 template -->
		<script type="rv-template" id="commentsGrade">
            <div class="grade_area"> <span class="graph_mask"> <em class="graph_value"></em> </span> <strong
                class="text_value"> <span>{{averageScore}}</span>
                <em class="total">5.0</em> </strong> 
                {{#if comments}}
                <span class="join_count"><em class="green">{{comments.length}}</em> 등록</span> 
                {{/if}}
            </div>
        </script>

		<!-- 댓글 template -->
		<script type="rv-template" id="review">
            <li class="list_item">
                <div>
                    <div class="review_area">
                        {{#if commentImages}}
                        <!-- comments에 commentImages가 있다면-->
                        {{#each commentImages}}
                        <!-- commentImages의 url을 통해 댓글당 이미지들을 templating 해준다. -->
                        <div class="thumb_area">
                            <a href="#" class="" title="이미지 크게 보기"> <img width="90" height="90" class="img_vertical_top"
                                    src="http://localhost:8080/reservation/{{saveFileName}}" alt="리뷰이미지"> </a> 
                        </div>
                        {{/each}}
                        {{/if}}
                        <h4 class="resoc_name">{{productDescription}}</h4>
                        <p class="review">{{comment}}</p>
                    </div>
                    <div class="info_area">
                        <div class="review_info"> <span class="grade">{{score}}</span> <span class="name">{{reservationEmail}}</span>
                                <span class="date">{{reservationDate}}</span>
                        </div>
                    </div>
                </div>
            </li>
        </script>
</body>

</html>