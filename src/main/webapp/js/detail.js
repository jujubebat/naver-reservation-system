var page;

document.addEventListener("DOMContentLoaded", function () {

   SearchParams = new URLSearchParams(location.search)
   var id = SearchParams.get("id");

   page = new DetailPage(id);
   page.initDetailPage();

});

function DetailPage(id) {

   this.id = id;
   this.data = null;
   this.url = "http://localhost:8080/reservation/api/products/";
   this.curIndex = 0;
   this.image_ul = null;

}

DetailPage.prototype.initDetailPage = function () {

   this.getDisplayInfo();
   this.registerToggleEvent();
   this.registerTabUI_Event();

}

DetailPage.prototype.getDisplayInfo = function () {

   var oReq = new XMLHttpRequest();

   oReq.addEventListener("load", function () { //produect 디테일데이터 서버에서 받아옴
      page.data = JSON.parse(this.responseText);
      page.setDetailPage();
   });

   oReq.open("GET", this.url + this.id);
   oReq.send();

}

DetailPage.prototype.setDetailPage = function () {

   check2 = this.data;

   this.setDetailImage(); // 상단 슬라이드 이미지 배치
   document.querySelector(".store_details p").innerText = this.data.displayInfo.productContent;  // 상단 상세정보 배치
   this.setCommentsGrade(); // 댓글 점수 배치
   if (this.data.comments.length > 0)
      this.setComments(3); // 댓글 배치
   this.setEventInfo();// 이벤트 정보 배치
   this.setReserveBox(); // 예약하기 배치 
   this.setMoreReviewBox(); // 예매자 한줄평 더보기 배치
   this.setTabUI(); // 하단 tabUI 배치

}

DetailPage.prototype.setDetailImage = function () {

   var template = document.querySelector("#detailImage").innerHTML;
   var bindTemplate = Handlebars.compile(template);
   var resultHTML = "";

   for (var i = 0, len = this.data.productImages.length; i < len; i++) {
      resultHTML += bindTemplate(this.data.productImages[i]);
   }

   document.querySelector(".visual_img").innerHTML = resultHTML;
   document.querySelector("#nameCard").innerText = document.querySelector("#nameCard").innerText.replace("{nameCard}", this.data.displayInfo.productDescription);

   this.image_ul = document.querySelector(".visual_img");
   this.image_ul.style.width = (this.image_ul.offsetWidth * (len + 1)) + "px";
   this.curIndex = 1;

   // 전체 페이지 수 세팅
   document.querySelectorAll(".figure_pagination .num")[1].querySelector("span").innerText = this.data.productImages.length;

   // 현재 페이지
   document.querySelector(".figure_pagination .num").innerText = this.curIndex;

   // 이미자가 한 개면 화살표 없애기
   if (this.data.productImages.length === 1) {
      document.querySelector(".spr_book2.ico_arr2_lt").className = "spr_book2 ico_arr2_lt off";
      document.querySelector(".spr_book2.ico_arr2_rt").className = "spr_book2 ico_arr2_rt off";
   }

   this.registerRightSlideImageEvent(len);
   this.registerLeftSlideImageEvent(len);

}

DetailPage.prototype.registerRightSlideImageEvent = function (len) {

   rightButton = document.querySelector(".btn_nxt");

   rightButton.addEventListener("click", function (evt) {
      this.image_ul.style.transition = "transform 0.08s";
      this.image_ul.style.transform = "translate3d(-" + document.querySelector(".visual_img .img_thumb").width * (this.curIndex) + "px, 0px, 0px)";
      this.curIndex++;
      document.querySelector(".figure_pagination .num").innerText = this.curIndex;
      if (this.curIndex === len + 1) {
         this.curIndex = 1;
         document.querySelector(".figure_pagination .num").innerText = this.curIndex;
         this.image_ul.style.transition = "transform all 0s";
         this.image_ul.style.transform = "translate3d(-" + 0 + "px, 0px, 0px)";
      }
   }.bind(this)); // 이벤트 리스너는 비동기로 처리된다. 따라서 bind로 현재 this인 defailPageObj를 등록해준다. 

}

DetailPage.prototype.registerLeftSlideImageEvent = function (len) {

   leftButton = document.querySelector(".btn_prev");

   leftButton.addEventListener("click", function (evt) {
      this.image_ul.style.transition = "transform 0.08s";
      this.curIndex--;

      if (this.curIndex <= 0) {
         this.curIndex = len;
         document.querySelector(".figure_pagination .num").innerText = this.curIndex;
         this.image_ul.style.transition = "transform all 0s";
         this.image_ul.style.transform = "translate3d(-" + 423 * (this.curIndex - 1) + "px, 0px, 0px)";
      } else {
         document.querySelector(".figure_pagination .num").innerText = this.curIndex;
         this.image_ul.style.transform = "translate3d(-" + 423 * (this.curIndex - 1) + "px, 0px, 0px)";
      }
   }.bind(this));

}

DetailPage.prototype.setCommentsGrade = function () {

   this.data.averageScore = this.data.averageScore.toFixed(1); // 댓글 평균점수 전처리
   var template = document.querySelector("#commentsGrade").innerHTML; // handler 로직
   var bindTemplate = Handlebars.compile(template);
   var resultHTML = bindTemplate(this.data);

   document.querySelector(".short_review_area").insertAdjacentHTML('afterbegin', resultHTML); // 댓글 점수 엘리먼트 배치
   document.querySelector(".graph_value").style.width = this.data.averageScore / 5 * 100 + "%"; // 퍼센트 환산 별점 표시

}

DetailPage.prototype.setComments = function (count) {

   // 댓글 데이터 전처리 
   for (var i = 0, len = this.data.comments.length; i < len; i++) {
      this.data.comments[i].score = this.data.comments[i].score.toFixed(1);
      this.data.comments[i].reservationDate = this.data.comments[i].reservationDate.substr(0, 10).concat(". 방문").replace(/-/gi, ".");
      this.data.comments[i].reservationEmail = this.data.comments[i].reservationEmail.substr(0, 4).concat("****");
   }

   var template = document.querySelector("#review").innerHTML; // handler 로직
   var bindTemplate = Handlebars.compile(template);
   var resultHTML = "";

   for (var i = 0; i < count; i++) {
      resultHTML += bindTemplate(this.data.comments[i]);
   }

   document.querySelector(".list_short_review").insertAdjacentHTML('beforeend', resultHTML); // 댓글 엘리먼트 배치

}

DetailPage.prototype.setEventInfo = function () {

   var template = document.querySelector("#eventInfo").innerHTML; // handler 로직
   var bindTemplate = Handlebars.compile(template);
   var resultHTML = "";

   for (var i = 0, len = this.data.productPrices.length; i < len; i++) {
      if (this.data.productPrices[i].discountRate === 0) continue;
      resultHTML += bindTemplate(this.data.productPrices[i]);
   }

   if (resultHTML === "")
      resultHTML = "할인 정보가 없습니다."

   document.querySelector(".event_info .in_dsc").insertAdjacentHTML("afterbegin", resultHTML);

}

DetailPage.prototype.setReserveBox = function () {

   var template = document.querySelector("#reserve").innerHTML; // handler 로직
   var bindTemplate = Handlebars.compile(template);
   var resultHTML = bindTemplate(this.data.displayInfo);
   document.querySelector(".section_review_list").insertAdjacentHTML('beforeend', resultHTML);

}

DetailPage.prototype.setMoreReviewBox = function () {

   var template = document.querySelector("#moreReview").innerHTML; // handler 로직
   var bindTemplate = Handlebars.compile(template);
   var resultHTML = bindTemplate(this.data.displayInfo);
   document.querySelector(".section_btn").insertAdjacentHTML('beforeend', resultHTML);

}

DetailPage.prototype.setTabUI = function () {

   // 하단 Tab UI 상세정보
   document.querySelector(".detail_info_lst .in_dsc").innerText = this.data.displayInfo.productContent;

   // 하단 Tab UI 오시는길 세팅
   var template = document.querySelector("#locationImage").innerHTML;
   var bindTemplate = Handlebars.compile(template);
   var resultHTML = bindTemplate(this.data.displayInfoImage);
   document.querySelector(".detail_location").innerHTML = resultHTML;

   // 하단 Tab UI 오시는길 정보 세팅
   template = document.querySelector("#locationInfo").innerHTML;
   bindTemplate = Handlebars.compile(template);
   resultHTML = bindTemplate(this.data.displayInfo);
   document.querySelector(".box_store_info").insertAdjacentHTML('beforeend', resultHTML);

}

DetailPage.prototype.registerToggleEvent = function () {

   $(".section_store_details").click(function (evt) {

      $("a.bk_more._open").toggle();
      $("a.bk_more._close").toggle();

      if (document.querySelector("a.bk_more._open").style.display === "none") {
         document.querySelector(".store_details").className = "store_details"; //글 길게 보이기.
      } else {
         document.querySelector(".store_details").className = "store_details close3"; //글 짧게 보이기.
      }

   });

}

DetailPage.prototype.registerTabUI_Event = function () {

   tabButton = document.querySelector(".info_tab_lst");

   tabButton.addEventListener("click", function (evt) {

      if (evt.path[2].className === "item active _detail") {

         document.querySelectorAll(".anchor")[0].className = "anchor active";
         document.querySelectorAll(".anchor")[1].className = "anchor";
         document.querySelector(".detail_area_wrap").className = "detail_area_wrap"; //상세정보 표시
         document.querySelector(".detail_location").className = "detail_location hide";

      } else if (evt.path[2].className === "item _path") {

         document.querySelectorAll(".anchor")[0].className = "anchor";
         document.querySelectorAll(".anchor")[1].className = "anchor active";
         document.querySelector(".detail_location").className = "detail_location"; //오시는길 표시
         document.querySelector(".detail_area_wrap").className = "detail_area_wrap hide"; //상세정보 

      }

   });

}
