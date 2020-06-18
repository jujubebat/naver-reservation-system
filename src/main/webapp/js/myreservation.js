var page;

document.addEventListener("DOMContentLoaded", function () {
    
    SearchParams = new URLSearchParams(location.search)
    var email = SearchParams.get("reservationEmail");

    page = new MyReservationPage(email);
    page.initMyReservationPage();

});

function MyReservationPage(email) {
    this.data = null;
    this.email = email;
    this.getUrl = "http://localhost:8080/reservation/api/reservations?reservationEmail=";
    this.putUrl = "http://localhost:8080/reservation/api/reservations/";
    this.confirmedRservationCount = 0;
    this.usedRservationCount = 0;
    this.canceledRservationCount = 0;
    this.tagetReservationId = 0;
}

MyReservationPage.prototype.initMyReservationPage = function () {

    this.registerHandlbarHelperFcuntion();
    this.getReservationInfos();
}

MyReservationPage.prototype.registerHandlbarHelperFcuntion = function () {

    Handlebars.registerHelper("isExpired", function (reservationDate) {

        var reservMonth = parseInt(reservationDate[5] + reservationDate[6]);
        var reservDay = parseInt(reservationDate[8] + reservationDate[9]);

        var curDate = new Date();
        var curMonth = curDate.getMonth() + 1;
        var curDay = curDate.getDate();

        if(reservMonth < curMonth) 
            return new Handlebars.SafeString('<button class="reservation_review">' + "예매자 리뷰 남기기" + "</button>");
        else if(reservMonth == curMonth){
            if(reservDay <= curDay){
                return new Handlebars.SafeString('<button class="reservation_review">' + "예매자 리뷰 남기기" + "</button>");
            }else{
                return new Handlebars.SafeString('<button class="booking_cancel">' + "예매 취소" + "</button>");
            }
        }else {
            return new Handlebars.SafeString('<button class="booking_cancel">' + "예매 취소" + "</button>");
        }

    });

}

MyReservationPage.prototype.getReservationInfos = function () {

    var oReq = new XMLHttpRequest();

    oReq.addEventListener("load", function () {
        page.data = JSON.parse(this.responseText);
        page.setReservations();
        page.setReservationCount();
        page.registerCancelPopUpEvent();
    });

    oReq.open("GET", this.getUrl + this.email);
    oReq.send();
}

MyReservationPage.prototype.setReservations = function () {

    this.confirmedRservationCount = 0;
    this.usedRservationCount = 0;
    this.canceledRservationCount = 0;

    var template = document.querySelector("#detailReservationInfo").innerHTML; // handler 로직
    var bindTemplate = Handlebars.compile(template);
    var confirmedRservation = "";
    var usedRservation = "";
    var canceledRservation = "";

    for (var i = 0, len = page.data.reservations.length; i < len; i++) {

        if (this.data.reservations[i].cancelYn) { // 취소된 예약일 경우.
            canceledRservation += bindTemplate(this.data.reservations[i]);
            this.canceledRservationCount++;

        } else { // 취소된 예약이 아닐 경우 -> 예약 확정 예약과 예약 완료 예약 구분.

            if(this.isExpired(this.data.reservations[i].reservationDate)){ // 예약일자가 오늘 날짜 이상이라면 -> 이용 완료로 간주.
                usedRservation += bindTemplate(this.data.reservations[i]);
                this.usedRservationCount++;
            }else{ // 예약일자가 오늘 날짜 미만이라면 -> 예약 확정으로 간주.
                confirmedRservation += bindTemplate(this.data.reservations[i]);
                this.confirmedRservationCount++;
            }

        }

    }

    var emptyReservation = document.querySelector("#emptyReservationInfo").innerHTML; // handler 로직

    if (this.confirmedRservationCount === 0)
        document.querySelectorAll(".card")[0].querySelector(".card_item_list").innerHTML = emptyReservation;
    else
        document.querySelectorAll(".card")[0].querySelector(".card_item_list").innerHTML = confirmedRservation;

    if(this.usedRservationCount === 0)
        document.querySelectorAll(".card")[1].querySelector(".card_item_list").innerHTML = emptyReservation;
    else
        document.querySelectorAll(".card")[1].querySelector(".card_item_list").innerHTML = usedRservation;

    if (this.canceledRservationCount === 0)
        document.querySelectorAll(".card")[2].querySelector(".card_item_list").innerHTML = emptyReservation;
    else
        document.querySelectorAll(".card")[2].querySelector(".card_item_list").innerHTML = canceledRservation;

}


MyReservationPage.prototype.setReservationCount = function () { // 상단 예매 현황 Count 세팅

    document.querySelectorAll(".figure")[0].innerText = this.data.reservations.length;
    document.querySelectorAll(".figure")[1].innerText = this.confirmedRservationCount;
    document.querySelectorAll(".figure")[3].innerText = this.canceledRservationCount;

}

MyReservationPage.prototype.registerCancelPopUpEvent = function () {

    $(".card_detail").click(function (evt) { // 취소 버튼 누르면, 팝업창 셋팅
        if (evt.target.className == "booking_cancel") {
            page.tagetReservationId = evt.delegateTarget.querySelector("em").innerText.replace(/[^0-9]/g, '');
            document.querySelector(".pop_tit span").innerText = evt.delegateTarget.querySelector(".tit").innerText;
            document.querySelector(".pop_tit .sm").innerText = "\n[예정 일정]\n" + evt.delegateTarget.querySelector(".item_dsc").innerText;
            $(".popup_booking_wrapper").show();
        }
    });

    document.querySelectorAll(".pop_bottom_btnarea .btn_bottom")[1].addEventListener("click", function (evt) { // 예 버튼
        console.log(evt);
        page.cancleReservation(page.tagetReservationId);
        $(".popup_booking_wrapper").hide();
    });

    document.querySelectorAll(".pop_bottom_btnarea .btn_bottom")[0].addEventListener("click", function (evt) { // 아니오 버튼
        $(".popup_booking_wrapper").hide();
    });

}

MyReservationPage.prototype.cancleReservation = function (id) {

    var oReq = new XMLHttpRequest();

    oReq.addEventListener("load", function () {
        page.data = JSON.parse(this.responseText);
        page.initMyReservationPage();
    });

    oReq.open("PUT", this.putUrl + id);
    oReq.send();

}

MyReservationPage.prototype.isExpired = function (reservationDate) {

    console.log(reservationDate);

    var reservMonth = parseInt(reservationDate[5] + reservationDate[6]);
    var reservDay = parseInt(reservationDate[8] + reservationDate[9]);

    var curDate = new Date();
    var curMonth = curDate.getMonth() + 1;
    var curDay = curDate.getDate();

    if(reservMonth < curMonth) 
        return true;
    else if(reservMonth == curMonth){
        if(reservDay <= curDay){
            return true;
        }else{
            return false;
        }
    }else {
        return true;
    }

}