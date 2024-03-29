<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<div class="header">
			<header class="header_tit">
				<h1 class="logo">
					<a href="./mainpage.html" class="lnk_logo" title="네이버"> <span
						class="spr_bi ico_n_logo">네이버</span>
					</a> <a href="./mainpage.html" class="lnk_logo" title="예약"> <span
						class="spr_bi ico_bk_logo">예약</span>
					</a>
				</h1>


				<!-- [질문] myreservation 최초 진입시 세션값을 읽어 올 수 없어서 다음과 같이 처리했습니다.
				dom 이 먼저 로드된 이후 ajax로 세션을 얻는 순서인데, 먼저 ajax로 세션을 얻어온뒤 아래 jstl 코드를 템플릿화 하여
				넣어주는 식으로 했었는데 jstl 문법이 포함된 코드는 html에서 읽히지 않았습니다. 이 문제로 여러가지를 고민 했습니다.
				먼저 로그인 세션 처리를 한뒤 해당 page를 부르는 방법이 있었지만 기존 back End 이메일 로그인 세션 처리 컨트롤러가  두개로 나눠져야해서
				그냥 아래와 같이 구현했습니다.이를 더 깔끔하게 처리하고 싶은데 어떻게 하면 될까요? -->

				<c:if test="${not empty reservationEmail}">
					<a href="./myreservation.html?reservationEmail=${reservationEmail}"
						class="btn_my"> <span class="viewReservation" title="예약확인">${reservationEmail}</span></a>
				</c:if>

				<c:if test="${empty reservationEmail}">
					<a href="./myreservation.html?reservationEmail=" class="btn_my">
						<span class="viewReservation" title="예약확인"></span>
					</a>
				</c:if>


			</header>


		</div>
		<hr>
		<div class="ct">
			<div class="section_my">
				<!-- 예약 현황 -->
				<div class="my_summary">
					<ul class="summary_board">
						<li class="item">
							<!--[D] 선택 후 .on 추가 link_summary_board --> <a href="#"
							class="link_summary_board on"> <i class="spr_book2 ico_book2"></i>
								<em class="tit">전체</em> <span class="figure">0</span>
						</a>
						</li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_book_ss"></i> <em class="tit">이용예정</em>
								<span class="figure">0</span>
						</a></li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_check"></i> <em class="tit">이용완료</em> <span
								class="figure">0</span>
						</a></li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_back"></i> <em class="tit">취소·환불</em> <span
								class="figure">0</span>
						</a></li>
					</ul>
				</div>
				<!--// 예약 현황 -->

				<!-- 내 예약 리스트 -->
				<div class="wrap_mylist">
					<ul class="list_cards" ng-if="bookedLists.length > 0">

						<!-- 예약 확정 -->
						<li class="card confirmed">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
										<i class="spr_book2 ico_check2"></i> <span class="tit">예약
											확정</span>
									</div>
									<div class="right"></div>
								</div>
							</div>

							<div class="card_item_list"></div>

						</li>

						<!-- 이용 완료 -->
						<li class="card used">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
										<i class="spr_book2 ico_check2"></i> <span class="tit">이용
											완료</span>
									</div>
									<div class="right"></div>
								</div>
							</div>

							<div class="card_item_list"></div> <!--
							<article class="card_item">
								<a href="#" class="link_booking_details">
									<div class="card_item_list">
										<div class="card_body">
											<div class="left"></div>
											<div class="middle">
												<div class="card_detail">

													<div class="err"> <i class="spr_book ico_info_nolist"></i>
														<h1 class="tit">예약 리스트가 없습니다</h1>
													</div>

												</div>
											</div>
										</div>
									</div>
								</a>
							</article>
						</li> --> <!-- 취소된 예약 완료 -->
						<li class="card used cancel">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
										<i class="spr_book2 ico_cancel"></i> <span class="tit">취소된
											예약</span>
									</div>
									<div class="right"></div>
								</div>
							</div>

							<div class="card_item_list"></div>

						</li>
					</ul>
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

	<!-- 취소 팝업 -->
	<!-- [D] 활성화 display:block, 아니오 버튼 or 닫기 버튼 클릭 시 숨김 display:none; -->
	<div class="popup_booking_wrapper" style="display: none;" data="0">
		<div class="dimm_dark" style="display: block"></div>
		<div class="popup_booking refund">
			<h1 class="pop_tit">
				<span>서비스명/상품명</span> <small class="sm">2000.0.00.(월)2000.0.00.(일)</small>
			</h1>
			<div class="nomember_alert">
				<p>
				<h3>취소하시겠습니까?</h3>
				</p>
			</div>
			<div class="pop_bottom_btnarea">
				<div class="btn_gray">
					<a href="#" class="btn_bottom" tabindex="0"><span>아니오</span></a>
				</div>
				<div class="btn_gray">
					<a href="#" class="btn_bottom" tabindex="0"><span>예</span></a>
				</div>
			</div>
			<!-- 닫기 -->
			<a href="#" class="popup_btn_close" title="close"> <i
				class="spr_book2 ico_cls"></i>
			</a>
			<!--// 닫기 -->
		</div>
	</div>
	<!--// 취소 팝업 -->

	<script src="../reservation/js/myreservation.js"></script>

	<!-- jQuery 라이브러리 -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<!-- handler 라이브러리 -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"
		integrity="sha256-usTqAE1ywvdMtksWzdeWzD75MsfJN0h0U7y2NtZL3N0="
		crossorigin="anonymous"></script>

	<!-- 예약 리스트가 없습니다. -->
	<script type="rv-template" id="emptyReservationInfo">
		<article class="card_item">
			<a href="#" class="link_booking_details">
				<div class="card_body">
					<div class="left"></div>
					<div class="middle">
						<div class="card_detail">

							<div class="err"> <i class="spr_book ico_info_nolist"></i>
								<h1 class="tit">예약 리스트가 없습니다</h1>
							</div>

						</div>
					</div>
				</div>
			</a>
		</article>
    </script>

	<!-- 상세한 예약 정보 -->
	<script type="rv-template" id="detailReservationInfo">
		<article class="card_item">
			<a href="#" class="link_booking_details">
				<div class="card_body">
					<div class="left"></div>
					<div class="middle">
						<div class="card_detail">
							<em class="booking_number">No.{{reservationInfoId}}</em>
							<h4 class="tit">{{displayInfo.productDescription}}</h4>
							<ul class="detail">
								<li class="item">
									<span class="item_tit">일정</span>
									<em class="item_dsc">
										{{displayInfo.openingHours}}
									</em>
								</li>
								<!--
								<li class="item">
									<span class="item_tit">내역</span>

									<em class="item_dsc">
										내역이 없습니다.
									</em>

								</li>
								-->

								<li class="item">
									<span class="item_tit">장소</span>
									<em class="item_dsc">
										{{displayInfo.placeName}}
									</em>
								</li>
								<li class="item">
									<span class="item_tit">업체</span>
									<em class="item_dsc">
										업체명이 없습니다.
									</em>
								</li>
							</ul>
							<div class="price_summary">
								<span class="price_tit">결제 예정금액</span>
								<em class="price_amount">
									<span>{{currencyFormat totalPrice}}</span>
									<span class="unit">원</span>
								</em>
							</div>
							<!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->

							{{#if cancelYn}}
							
							{{else}}

								{{#isExpired reservationDate productId reservationInfoId displayInfo.productDescription}}

								
								{{/isExpired}}
						
							{{/if}}

						</div>
					</div>
				</div>
			</a>
		</article>
	</script>

</body>

</html>

