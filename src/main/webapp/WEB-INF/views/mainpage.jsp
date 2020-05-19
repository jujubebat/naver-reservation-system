<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <link rel="favicon" href="../reservation/img/favicon.ico">
    <title>네이버 예약</title>
    <link rel="stylesheet" href="../reservation/css/bookinglogin.css">
    
</head>

<body>
    <div id="container">
        <div class="header">
            <header class="header_tit">
                <h1 class="logo">
                    <a href="" class="lnk_logo" title="네이버"> <span class="spr_bi ico_n_logo">네이버</span></a>
                    <a href="" class="lnk_logo" title="예약"> <span class="spr_bi ico_bk_logo">예약</span></a>
                </h1>
                <a href="" class="btn_my"> <span class="viewReservation" title="예약확인">예약확인</span> </a>
            </header>
        </div>
        <hr>
        <div class="event">
            <div class="section_visual">
                <div class="group_visual">
                    <div class="container_visual">
                        <ul class="visual_img">
                        </ul>
                    </div>
                </div>
            </div>
            <div class="section_event_tab">
                <ul class="event_tab_lst tab_lst_min">
                    <li class="item">
                        <a class="anchor" data-category="0">전체리스트 </a>
                    </li>
                    <li class="item">
                        <a class="anchor" data-category="1">전시 </a>
                    </li>
                    <li class="item">
                        <a class="anchor" data-category="2">뮤지컬 </a>
                    </li>
                    <li class="item">
                        <a class="anchor" data-category="3">콘서트 </a>
                    </li>
                    <li class="item">
                        <a class="anchor" data-category="4">클래식</a>
                    </li>
                    <li class="item">
                        <a class="anchor" data-category="5">연극</a>
                    </li>
                </ul>
            </div>
            <div class="section_event_lst">
                <p class="event_lst_txt">바로 예매 가능한 행사가 <span class="pink">{totalCount}개</span>있습니다</p>
                <div class="wrap_event_box">
                    <ul class="lst_event_box" id="lst_event_box_1">
                    </ul>
                    <ul class="lst_event_box" id="lst_event_box_2">
                    </ul>
                    <div class="more">
                        <button class="btn" style="">더보기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer>
        <div class="gototop">
            <a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span></a>
        </div>
        <div class="footer">
            <p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및 환불 등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
            <span class="copyright">© NAVER Corp.</span>
        </div>
    </footer>

    <script src="../reservation/js/mainpage.js"></script>
    <script type="rv-template" id="promotionItem">
        <li><img src="http://localhost:8080/reservation/{url}"/></li>
    </script>

    <script type="rv-template" id="itemList">
        <li class="item">
            <a href="detail.html?id={id}" class="item_book">
                <div class="item_preview">
                    <img alt="{description}" class="img_thumb" src="http://localhost:8080/reservation/{url}">
                    <span class="img_border"></span>
                </div>
                <div class="event_txt">
                    <h4 class="event_txt_tit"> <span>{description}</span> <small class="sm">{placeName}</small> </h4>
                    <p class="event_txt_dsc">{content}</p>
                </div>
            </a>
        </li>
    </script>
</body>
</html>
