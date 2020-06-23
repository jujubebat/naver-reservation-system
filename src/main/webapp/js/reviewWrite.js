var page;

document.addEventListener("DOMContentLoaded", function () {

    SearchParams = new URLSearchParams(location.search)
    var productId = SearchParams.get("productId");
    var reservationInfoId = SearchParams.get("reservationInfoId");
    var productDescription = SearchParams.get("productDescription");

    page = new ReviewWritePage(productId, reservationInfoId, productDescription);
    page.initReviewWritePage();

});

function ReviewWritePage(productId, reservationInfoId, productDescription) {
    this.productId = productId;
    this.reservationInfoId = reservationInfoId;
    this.productDescription = productDescription;
    this.score = 0;
    this.comment = "";
    this.isPossible = false;
    this.postUrl = "http://localhost:8080/reservation/api/reservations/"
}


ReviewWritePage.prototype.initReviewWritePage = function () {
    starRankComponent = new StarRank();
    starRankComponent.registerStarRankEvent();

    this.setTitle();
    this.registerReviewFormEvent();
    this.registerFileExtensionCheck();
    this.registerDeleteFileEvent();
    this.registerCommentInputEvent();
    this.registerReviewRegistButtonEvent();
}

ReviewWritePage.prototype.setTitle = function () { // 리뷰 폼 이벤트
    
    document.querySelector(".title").innerText = this.productDescription;
   
}

ReviewWritePage.prototype.registerReviewFormEvent = function () { // 리뷰 폼 이벤트
    
    document.querySelector(".review_write_info").addEventListener("focus", function () {
        document.querySelector(".review_write_info").style.display = "none";
    });

    document.querySelector(".review_textarea").addEventListener("blur", function () {

        if(document.querySelector(".review_textarea").value.length > 0)
            document.querySelector(".review_write_info").style.display = "none";
        else
            document.querySelector(".review_write_info").style.display = "";
    });
   
}

ReviewWritePage.prototype.registerFileExtensionCheck = function () { // 파일 등록 이벤트 
    const elImage = document.querySelector("#reviewImageFileOpenInput");
    elImage.addEventListener("change", (evt) => {
        const image = evt.target.files[0];
        if (!page.valideImageType(image)) {
            console.warn("invalide image file type");
            return;
        }

        document.querySelector(".item").style.display = "";
        const elImage = document.querySelector(".thumb_img");
        elImage.src = window.URL.createObjectURL(image);
    })
}

ReviewWritePage.prototype.valideImageType = function (image) { // 파일 확장자 체크
    const result = (['image/png', 'image/jpg'].indexOf(image.type) > -1);
    return result;
}

ReviewWritePage.prototype.registerDeleteFileEvent = function (image) { // 업로드 파일 삭제 이벤트 
    document.querySelector(".spr_book").addEventListener("click", function () {
        document.querySelector("#reviewImageFileOpenInput").value = "";
        document.querySelector(".item").style.display = "none";
        const elImage = document.querySelector(".thumb_img");
        elImage.src = "";
    });
}

ReviewWritePage.prototype.registerCommentInputEvent = function () { // 업로드 파일 삭제 이벤트 

    document.querySelector(".review_textarea").onkeyup = function () { // 이름 유효성 검사 이벤트 등록

        if (!page.checkCommentLength(document.querySelector(".review_textarea").value)) { // 정규식으로 유효성 검사.
            document.querySelector(".review_textarea").value =
                document.querySelector(".review_textarea").value.substr(0, 400);
        }

        document.querySelector(".guide_review span").innerText =
            document.querySelector(".review_textarea").value.length;

        page.comment = document.querySelector(".review_textarea").value;

        page.checkDateValidation();

    }

}

ReviewWritePage.prototype.checkCommentLength = function (text) { // 유효성 검사 함수.

    var textLengthPattern = /^.{1,400}$/;

    if (text.match(textLengthPattern) === null) {
        return false;
    } else {
        return true; // text 가 1글자 이상 400글자 이하라면 true 반환.
    }

}

ReviewWritePage.prototype.checkDateValidation = function () { // 유효성 검사 함수.

    if (page.score > 0 && page.comment.length > 0) {
        document.querySelector(".bk_btn_wrap").className = "bk_btn_wrap";
        page.isPossible = true;
    } else {
        document.querySelector(".bk_btn_wrap").className = "bk_btn_wrap disable";
        page.isPossible = false;
    }

}

ReviewWritePage.prototype.postReservationUserComment = function () {


    const formData = new FormData();
    formData.append('file', document.querySelector("#reviewImageFileOpenInput").files[0]);

    console.log(formData);

    const obj = {
        body: formData,
        headers: {
           // 'Content-Type': 'multipart/form-data', // 파일 업로드을 위해 다음과 같이 설정하면, 스프링 서버에서 the request was rejected because no multipart boundary was found 오류가남 왜그러지
        },
        method: 'POST',
        mode : 'no-cors'
    }

    fetch(page.postUrl+page.reservationInfoId+'/'+"comments?comment="+page.comment+"&productId="+page.productId+"&score="+page.score, obj)
        .then(alert("댓글이 등록 되었습니다."))
        .then(window.history.back());
   
}


ReviewWritePage.prototype.registerReviewRegistButtonEvent = function () {

    document.querySelector(".bk_btn").addEventListener("click", function(){

        if(page.isPossible){
            page.postReservationUserComment();
        }

    });
   
}

function StarRank() {
}

StarRank.prototype.registerStarRankEvent = function () {
    document.querySelector(".rating").addEventListener("click", function (evt) {

        if (evt.target.className === "rating_rdo" || evt.target.className === "rating_rdo checked") {
            ratingStarList = document.querySelectorAll(".rating_rdo");

            var score = evt.target.value;

            // [질문] : 별점 처리를 할때 아래와 같이 모두 지우고  다시 채워넣는 식으로 구현했는데 이는 효율적이지 않나요? 이렇게 작성해도 무방한가요?

            // 별점 다 지우기.
            for (var i = 0, len = ratingStarList.length; i < len; i++) {
                ratingStarList[i].className = "rating_rdo";
            }

            // value 이하일경우 다 색칠하기.
            for (var i = 0, len = ratingStarList.length; i < len; i++) {
                if (ratingStarList[i].value > score) continue;
                ratingStarList[i].className = "rating_rdo checked";
            }

            // 숫자를 value로 채우기.
            console.log(score);
            document.querySelector(".star_rank").innerText = score;

            // 점수 글씨색 검은색으로 만들기.
            document.querySelector(".star_rank").style.color = "black";

            // page 객체에 현재 점수값 넣기.
            page.score = score;

            page.checkDateValidation();

        }
        
    });

}