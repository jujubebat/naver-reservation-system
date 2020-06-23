var page;

document.addEventListener("DOMContentLoaded", function () {

    SearchParams = new URLSearchParams(location.search)
    var id = SearchParams.get("id");

    page = new ReservePage(id);
    page.initReservePage();

});

function ReservePage(id) {

    this.id = id;
    this.data = null;
    this.getUrl = "http://localhost:8080/reservation/api/products/";
    this.postUrl = "http://localhost:8080/reservation/api/reservations?reservationEmail=";
    this.getRandomDateUrl = "http://localhost:8080/reservation/api/randomDate";

    this.reservationInfo = {
        displayInfoId: 0,
        prices: [],
        productId: 0,
        reservationEmail: "",
        reservationName: "",
        reservationTelephone: "",
        reservationYearMonthDay: ""
    }

    this.isAgree = false;
    this.reservationInfoisVaild = false;
    this.ticketLimitNumPerKind = 10; // 티켓 종류당 최대로 살 수 있는 개수
    this.ticketCountInfo;
    this.reservationDate;
}

ReservePage.prototype.initReservePage = function () {

    SearchParams = new URLSearchParams(location.search)
    this.id = SearchParams.get("id");
    this.getDisplayInfo();
    // 랜덤 예약 날짜를 서버에서 받아온다. (플젝 특성상 그냥 임의로 예약날짜 구하기 위해서 만든것)
    // 오늘 날짜기준 1~5 범위의 날짜를 서버에서 랜덤으로 보내줌. 
    //this.getReservationDate();

}

ReservePage.prototype.getDisplayInfo = function () {

    var oReq = new XMLHttpRequest();

    oReq.addEventListener("load", function () {
        page.data = JSON.parse(this.responseText);
        page.getReservationDate();
    });

    oReq.open("GET", this.getUrl + this.id);
    oReq.send();

}

ReservePage.prototype.getReservationDate = function () {

    var oReq = new XMLHttpRequest();

    oReq.addEventListener("load", function () {
        var dateData = JSON.parse(this.responseText);
        page.reservationDate = dateData.reservationDate;
        page.setReservePage();
    });

    oReq.open("GET", this.getRandomDateUrl);
    oReq.send();

}

ReservePage.prototype.setReservePage = function () {

    this.setTitleBox();
    this.setDisplayImage();
    this.setDisplayInfo();
    this.setPriceInfo();
    this.registerRerserveButtonEvent();
    this.registerUserAgreementMoreButtonEvent();
    this.registerUserAgreementCustomMoreButtonEvent();
    this.registercheckDataValidationEvent();
    this.registerUserAgreementButtonEvent();

}

ReservePage.prototype.setTitleBox = function () {

    var template = document.querySelector("#titleBox").innerHTML; // handler 로직
    var bindTemplate = Handlebars.compile(template);
    var resultHTML = bindTemplate(this.data.displayInfo);
    document.querySelector(".top_title").insertAdjacentHTML('beforeend', resultHTML);

}

ReservePage.prototype.setDisplayImage = function () {

    var template = document.querySelector("#displayImage").innerHTML; // handler 로직
    var bindTemplate = Handlebars.compile(template);
    var resultHTML = bindTemplate(this.data.productImages[0]);
    document.querySelector(".group_visual").innerHTML = resultHTML;

}

ReservePage.prototype.setPriceInfo = function () {

    var template = document.querySelector("#priceInfo").innerHTML; // handler 로직
    var bindTemplate = Handlebars.compile(template);
    var resultHTML = "";

    for (var i = 0, len = this.data.productPrices.length; i < len; i++) {
        resultHTML += bindTemplate(this.data.productPrices[i]);
    }

    document.querySelector(".ticket_body").innerHTML = resultHTML;

    document.querySelectorAll(".qty")

    for (var i = 0, len = document.querySelectorAll(".qty").length; i < len; i++) {
        var type = document.querySelectorAll(".qty")[i].querySelector(".product_amount span").innerText;
        document.querySelectorAll(".qty")[i].querySelector(".product_amount span").innerText = this.getPriceType(type);
    }

    // 수량 증가 버튼 이벤트 등록
    var plusButtons = document.querySelectorAll(".btn_plus_minus.spr_book2.ico_plus3");
    for (var i = 0, len = plusButtons.length; i < len; i++) {
        this.registerTicketButtonEvent(plusButtons[i], true);
    }

    var plusButtons = document.querySelectorAll(".btn_plus_minus.spr_book2.ico_minus3");
    for (var i = 0, len = plusButtons.length; i < len; i++) {
        this.registerTicketButtonEvent(plusButtons[i], false);
    }

    // 티켓 카운트를 저장하는 동적 배열 생성후 초기화.
    this.ticketCountInfo = new Map();

    for (var i = 0, len = this.data.productPrices.length; i < len; i++) {
        this.ticketCountInfo.set(this.data.productPrices[i].productPriceId, 0);
    }
}

ReservePage.prototype.setDisplayInfo = function () {

    var template = document.querySelector("#displayInfo").innerHTML; // handler 로직
    var bindTemplate = Handlebars.compile(template);
    var resultHTML = bindTemplate(this.data.displayInfo);

    document.querySelector(".section_store_details").innerHTML = resultHTML;

    document.querySelector("#reservationDate").innerText = page.reservationDate; // 하단 예매내용 날짜 넣어주기.
}

ReservePage.prototype.registerTicketButtonEvent = function (plusButton, isAdd) {

    plusButton.addEventListener("click", function (evt) {

        var ticketNumber = evt.path[3].dataset.indexNumber;
        var ticketPrice = parseInt(evt.path[3].querySelector(".price").innerText);

        if (isAdd) {

            evt.path[1].querySelector("input").value++;
            evt.path[3].querySelector(".individual_price").style.color = "black"
            evt.path[3].querySelector(".btn_plus_minus").className = "btn_plus_minus spr_book2 ico_minus3"
            evt.path[3].querySelector(".count_control_input").className = "count_control_input";

            if (evt.path[1].querySelector("input").value >= page.ticketLimitNumPerKind) {
                evt.path[1].querySelector("input").value = page.ticketLimitNumPerKind;
                evt.path[3].querySelector(".ico_plus3").className = "btn_plus_minus spr_book2 ico_plus3 disabled"
            }

            page.ticketCountInfo.set(ticketNumber, parseInt(evt.path[1].querySelector("input").value));

        }
        else {

            evt.path[1].querySelector("input").value--;
            evt.path[3].querySelector(".ico_plus3").className = "btn_plus_minus spr_book2 ico_plus3"

            if (evt.path[1].querySelector("input").value <= 0) {
                evt.path[3].querySelector(".individual_price").style.color = "#BBBBBB"
                evt.path[3].querySelector(".btn_plus_minus").className = "btn_plus_minus spr_book2 ico_minus3 disabled"
                evt.path[3].querySelector(".count_control_input").className = "count_control_input disabled";

                evt.path[1].querySelector("input").value = 0;
            }

            page.ticketCountInfo.set(ticketNumber, parseInt(evt.path[1].querySelector("input").value));
        }

        evt.path[2].querySelector(".total_price").innerText = (ticketPrice * page.ticketCountInfo.get(ticketNumber)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        var priceElements = document.querySelectorAll(".qty");

        var ticketTotalCount = 0;
        var ticketTotalPrice = 0;

        for (var i = 0, len = priceElements.length; i < len; i++) {
            ticketTotalPrice += parseInt(priceElements[i].querySelector(".total_price").innerText.replace(",",""));
            ticketTotalCount += parseInt(priceElements[i].querySelector(".count_control_input").value);
        }

        document.querySelector("#totalCount").innerText = ticketTotalCount;
        document.querySelector("#totalPrice").innerText = ticketTotalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        

        page.checkReservationInfoValidation(); // 티켓 개수 변할때마다 예약가능한지 검사
    });

}

ReservePage.prototype.getPriceType = function (type) {
    if (type == "A")
        return "성인";
    else if (type == "Y")
        return "청소년";
    else if (type == "B")
        return "유아";
    else if (type == "S")
        return "셋트";
    else if (type == "D")
        return "장애인";
    else if (type == "C")
        return "지역주민";
    else if (type == "E")
        return "얼리버드";
    else if (type == "V")
        return "VIP";
    else if (type == "R")
        return "R석";
    else if (type == "B")
        return "B석";
    else if (type == "S")
        return "S석";
    else if (type == "D")
        return "평일";
}

ReservePage.prototype.registerUserAgreementMoreButtonEvent = function () { // 개인정보 수집 및 이용 동의 더보기 이벤트

    var Button = document.querySelectorAll(".btn_agreement")[0];

    Button.addEventListener("click", function () {

        if (document.querySelector(".useragreement_details").style.display === "") {
            document.querySelector(".useragreement_details").style.display = "none";
        }
        else {
            document.querySelector(".useragreement_details").style.display = "";
        }

    });

}

ReservePage.prototype.registerUserAgreementCustomMoreButtonEvent = function () { // 개인정보 제3자 제공 동의 더보기 이벤트

    var Button = document.querySelectorAll(".btn_agreement")[1];

    Button.addEventListener("click", function () {

        if (document.querySelector(".custom_details_wrap").style.display === "") {
            document.querySelector(".custom_details_wrap").style.display = "none";
        } else {
            document.querySelector(".custom_details_wrap").style.display = "";
        }

    });

}

ReservePage.prototype.setRerservationInfo = function () { // 현재 입력된 데이터를 객체로 만듬.

    this.reservationInfo.displayInfoId = this.data.displayInfo.displayInfoId;
    this.reservationInfo.productId = page.data.displayInfo.productId;
    this.reservationInfo.reservationName = document.querySelector("#name").value;
    this.reservationInfo.reservationEmail = document.querySelector("#email").value;
    this.reservationInfo.reservationTelephone = document.querySelector("#tel").value;
    this.reservationInfo.reservationYearMonthDay = this.reservationDate;

    var priceList = document.querySelectorAll(".qty");

    for (var i = 0, len = priceList.length; i < len; i++) {

        this.reservationInfo.prices.push({
            count: priceList[i].querySelector(".count_control_input ").value,
            productPriceId: priceList[i].dataset.indexNumber,
            reservationInfoId: 0,
            reservationInfoPriceId: 0
        });

    }

}

ReservePage.prototype.postReservationInfo = function () {

    const obj = {
        body: JSON.stringify(this.reservationInfo),
        headers: {
            'Content-Type': 'application/json',
        },
        method: 'POST'
    }

    fetch(page.postUrl, obj)
        .then(alert("예매가 완료 되었습니다.\n사이트 오른쪽 상단의 '예약확인'을 눌러 예매 내역을 조회할 수 있습니다."))
        .then(window.location.href = 'mainpage.html');
}

ReservePage.prototype.registerRerserveButtonEvent = function () { // 예약 버튼 이벤트 등록.

    var rerserveButton = document.querySelector(".bk_btn_wrap");

    rerserveButton.addEventListener("click", function () {
        if (page.reservationInfoisVaild) {// 예약하기 플래그가 활성화 돼있으면
            page.setRerservationInfo(); // form 데이터를 자바스크립트 객체로 만들고.
            page.postReservationInfo(); // 데이터를 서버로 보낸다.
        } 
    });

}

ReservePage.prototype.checkReservationInfoValidation = function () {  // 현재 입력된 값 검사.

    var nameForm = document.querySelector("#name")
    var telForm = document.querySelector(".tel")
    var emailForm = document.querySelector(".email")
    var selectedTicketCount = 0;

    page.ticketCountInfo.forEach((value, key, mapObject) => selectedTicketCount += value); // map에 저장된 티켓 개수 카운트

    if (page.isAgree  // 이용자 약관 전체 동의 눌렸고,
        && page.checkDataValidation(nameForm, "name") // 이름 유효성 검사 통과했고,
        && page.checkDataValidation(telForm, "tel") // 전화번호 유효성 검사 통과했고,
        && page.checkDataValidation(emailForm, "email") // 이메일 유효성 검사 통과했고,
        && selectedTicketCount > 0) { // 티켓이 1개 이상 선택됐다면, 예약하기 버튼 활성화.

        document.querySelector(".bk_btn_wrap").className = "bk_btn_wrap"; // 예약하기 버튼 활성화
        page.reservationInfoisVaild = true;

    } else {
        page.reservationInfoisVaild = false;
        document.querySelector(".bk_btn_wrap").className = "bk_btn_wrap disable"; // 예약하기 버튼 비활성화
    }

}

// 이용약관 전체 동의버튼 눌렀을때 이용자 약관 전체동의가 됐는데, 데이터 유효성검사, 티켓 개수 1개 이상 일경우 
// 예약하기 버튼 활성화, 예약 플래그(reservationInfoisVaild) 활성화
ReservePage.prototype.registerUserAgreementButtonEvent = function () {

    document.querySelector('.agreement .label').addEventListener("click", function (e) {
        page.isAgree = !page.isAgree;
        page.checkReservationInfoValidation();
    });

}

ReservePage.prototype.checkDataValidation = function (form, type) { // 유효성 검사 함수.

    if (type === "name") {

        var specialPattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

        if (form.value.match(specialPattern) === null) {
            return true;
        } else { // 예 매 자 명 오 류
            return false;
        }

    }
    else if (type === "tel") {

        var phonePattern = /^\d{3}-\d{3,4}-\d{4}$/;
        var telPattern = /^(0(2|3[1-3]|4[1-4]|5[1-5]|6[1-4]))-(\d{3,4})-(\d{4})$/;

        if (form.value.match(phonePattern) != null || form.value.match(telPattern) != null) { // 전 화 번 호 명 오 류
            return true;
        } else {
            return false;
        }

    } else if (type === "email") {

        var emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

        if (form.value.match(emailPattern) === null) { // 이 메 일 명 오 류
            return false;
        } else {
            return true;
        }

    }

}

ReservePage.prototype.registercheckDataValidationEvent = function () { // 유효성 검사 이벤트 등록 함수.

    document.querySelector("#name").onchange = function () { // 이름 유효성 검사 이벤트 등록
        var form = document.querySelector("#name");

        if (!page.checkDataValidation(form, "name"))
            alert("예매자명이 올바르지 않습니다.\n 특수문자를 제외한 예매자명을 입력해주세요.");
        else
            form.style.color = "black";

        page.checkReservationInfoValidation(); // 값 입력이 종료될때마다 유효성 검사. 
    }

    document.querySelector(".tel").onchange = function () { // 전화번호 유효성 검사 이벤트 등록
        var form = document.querySelector(".tel");

        if (!page.checkDataValidation(form, "tel"))
            alert("연락처가 올바르지 않습니다.\n 010-XXXX-XXX 또는 지역 번호 형식으로 입력해주세요. ");
        else
            form.style.color = "black";

        page.checkReservationInfoValidation();
    }

    document.querySelector(".email").onchange = function () { // 이메일 유효성 검사 이벤트 등록
        var form = document.querySelector(".email");

        if (!page.checkDataValidation(form, "email"))
            alert("이메일이 올바르지 않습니다.\n example@naver.com 형식으로 입력해주세요. ");
        else
            form.style.color = "black";

        page.checkReservationInfoValidation();
    }

}
