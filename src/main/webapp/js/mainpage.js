var page;

document.addEventListener("DOMContentLoaded", function () {
   page = new MainPage();
   page.initMainPage();
});

function MainPage() {

   this.totalCount = 0;
   this.categoryId = 0;
   this.start = 0;
   this.leftColumElements = "";
   this.rightColumElements = "";
   this.data = null;
   this.getPromotionsUrl = "http://localhost:8080/reservation/api/promotions";
   this.getProductsUrl = "http://localhost:8080/reservation/api/products";

}

MainPage.prototype.initMainPage = function () {
   this.getProducts(0);
   this.getPromotions();
   this.registerTabButtonEvent();
   this.registerGetProductsEvent();
}

MainPage.prototype.getPromotions = function () {

   var oReq = new XMLHttpRequest();

   oReq.addEventListener("load", function () {
      data = JSON.parse(this.responseText);

      var template = document.querySelector("#promotionItem").innerHTML;
      var bindTemplate = Handlebars.compile(template);
      var resultHTML = "";

      for (var i = 0, len = data.items.length; i < len; i++) {
         resultHTML += bindTemplate(data.items[i]);
      }

      document.querySelector(".container_visual > ul").innerHTML = resultHTML;
      page.slidePromotionImage(data.items.length);
   });

   oReq.open("GET", this.getPromotionsUrl);
   oReq.send();
   
}

MainPage.prototype.slidePromotionImage = function (len) {

   var image_ul = document.querySelector(".visual_img");
   image_ul.style.width = (image_ul.offsetWidth * (len + 1)) + "px";
   var curIndex = 0;

   setInterval(() => {
      image_ul.style.transition = "transform 2s ease-out";
      image_ul.style.transform = "translate3d(-" + 419 * (curIndex + 1) + "px, 0px, 0px)";
      curIndex++;

      if (curIndex === len) {
         curIndex = 0;
         image_ul.style.transition = "";
         image_ul.style.transform = "translate3d(-" + 0 + "px, 0px, 0px)";
      }
   }, 2000);

}

MainPage.prototype.getProducts = function (categoryId) {

   var oReq = new XMLHttpRequest();

   oReq.addEventListener("load", function () {

      page.data = JSON.parse(this.responseText);
      page.totalCount = page.data["totalCount"];
      page.start += page.data["items"].length;

      if (page.start >= page.totalCount)
         document.querySelector('.more .btn').style.cssText = "display: none;";

      page.setProductElements(page.data);

   });

   if (categoryId == 0) {
      oReq.open("GET", this.getProductsUrl + "?start=" + this.start);
   }
   else
      oReq.open("GET", this.getProductsUrl + "?categoryId=" + this.categoryId + "&start=" + this.start);

   oReq.send();

}

MainPage.prototype.registerGetProductsEvent = function () {

   button = document.querySelector('.more .btn');
   button.addEventListener("click", function (evt) {
      page.getProducts(page.categoryId);
   });

}

MainPage.prototype.setProductElements = function (data) {

   document.querySelector(".pink").innerText = this.totalCount + "개"; //카테코리별 product 개수 표시
   var template = document.querySelector("#itemList").innerHTML;
   var bindTemplate = Handlebars.compile(template);

   for (var i = 0, len = data.items.length; i < len; i++) {
      tmp = bindTemplate(data.items[i]);

      if (i % 2 == 0) {
         this.leftColumElements += tmp;
      }
      else {
         this.rightColumElements += tmp;
      }
   }

   document.querySelector("#lst_event_box_1").innerHTML = this.leftColumElements;
   document.querySelector("#lst_event_box_2").innerHTML = this.rightColumElements;

}


MainPage.prototype.registerTabButtonEvent = function () {

   tabElements = document.querySelector('.section_event_tab'); //tabElement 획득

   tabElements.addEventListener("click", function (evt) {
      if (evt.toElement.className == "anchor") {

         //이번에 클릭된 anchor만 초록색으로 표시  
         for (var i = 0; i < 6; i++) {
            if (evt.path[2].querySelectorAll('.item > .anchor')[i].className == "anchor active")
               evt.path[2].querySelectorAll('.item > .anchor')[i].className = "anchor";
         }

         evt.toElement.className = "anchor active";
         document.querySelector('.more .btn').style.cssText = ""; //더보기 버튼 보이기
         page.categoryId = evt.toElement.dataset.category;
         page.start = 0;
         page.leftColumElements = "";
         page.rightColumElements = "";

         page.getProducts(page.categoryId);
      }
   });

}