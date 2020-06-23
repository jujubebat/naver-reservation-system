var page;

document.addEventListener("DOMContentLoaded", function () {

    page = new BookingLoginPage();
    page.initBookingLoginPage();

});

function BookingLoginPage() {
}


BookingLoginPage.prototype.initBookingLoginPage = function () {

    this.registercheckDataValidationEvent();

}

BookingLoginPage.prototype.checkDataValidation = function (form) { // 이메일 유효성 검사 함수.

    var emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{1,10}$/i;

    if (form.value.match(emailPattern) === null) {
        // console.log("이메일명 오류");
        return false;
    } else {
        return true;
    }
}

BookingLoginPage.prototype.registercheckDataValidationEvent = function () { // 유효성 검사 이벤트 등록 함수.

    document.querySelector(".login_input").onchange = function () { 

        var form = document.querySelector(".login_input");

        if (!page.checkDataValidation(form)) {
            alert("올바르지 않은 이메일 형식 입니다.\n example@naver.com 형식으로 입력해주세요.");
            document.querySelector(".login_btn").className = "login_btn confirm";
            document.querySelector(".login_btn").disabled = true; // 로그인 button 태그 활성화.
        } else {
            document.querySelector(".login_btn").className = "login_btn";
            document.querySelector(".login_btn").disabled = false; // 로그인 button 태그 비활성화.
        }
    }

}
