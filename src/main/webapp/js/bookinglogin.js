var page;

document.addEventListener("DOMContentLoaded", function () {

    page = new BookingLoginPage();
    page.initBookingLoginPage();

});

function BookingLoginPage() {
    this.isRight = false; // 이메일 유효성 검사 flag.
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
            alert("올바르지 않은 이메일 형식 입니다.");
            page.isRight = false;
            document.querySelector(".login_btn").className = "login_btn confirm";
            document.querySelector(".login_btn").disabled = true; // 로그인 버튼 활성화.
        } else {
            page.isRight = true;
            document.querySelector(".login_btn").className = "login_btn";
            document.querySelector(".login_btn").disabled = false; // 로그인 버튼 비활성화.
        }
    }

}
