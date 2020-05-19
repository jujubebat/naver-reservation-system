var data;
var totalCount;
var start = 0;
var categoryId = 0;
var resultHTML_1 = "";
var resultHTML_2 = "";
var image_ul = document.querySelector(".visual_img");

document.addEventListener("DOMContentLoaded", function () {
   init();
   getPromotions();
   tabButtonEventRegister();
   moreButtonEventRegister();
});

function init() {
   getProducts();
   document.querySelectorAll('.item > .anchor')[0].className = "anchor active";
}

function getPromotions() {
   var oReq = new XMLHttpRequest();
   oReq.addEventListener("load", function () {
      data = JSON.parse(this.responseText);
      var resultHTML = "";
      var promotionItem = document.querySelector("#promotionItem").innerHTML;
      for (var i = 0, len = data.items.length; i < len; i++) {
         resultHTML += promotionItem.replace("{url}", data.items[i].productImageUrl);
      }
      document.querySelector(".container_visual > ul").innerHTML = resultHTML;
      promotionSlidAanimation(data.items.length);
   });
   oReq.open("GET", "http://localhost:8080/reservation/api/promotions");
   oReq.send();
}

function promotionSlidAanimation(len) {
   image_ul.style.width = (image_ul.offsetWidth * (len + 1)) + "px";
   slideImg(len);
}

function slideImg(len) {
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

function tabButtonEventRegister() {
   tab = document.querySelector('.section_event_tab');
   tab.addEventListener("click", function (evt) {   
      if (evt.toElement.className == "anchor") {

         for (var i = 0; i < 6; i++) {
            if (evt.path[2].querySelectorAll('.item > .anchor')[i].className == "anchor active")
               evt.path[2].querySelectorAll('.item > .anchor')[i].className = "anchor";
         }

         evt.toElement.className = "anchor active";
         document.querySelector('.more .btn').style.cssText = "";
         id = evt.toElement.dataset.category;
         categoryId = id;
         start = 0;
         resultHTML_1 = "";
         resultHTML_2 = "";
         getProducts(id);
      }
   });
}

function getProducts(id) {
   var oReq = new XMLHttpRequest();
   oReq.addEventListener("load", function () {
      data = JSON.parse(this.responseText);
      totalCount = data["totalCount"];
      start += data["items"].length;

      if (start >= totalCount)
         document.querySelector('.more .btn').style.cssText = "display: none;";

      setProductsElements(data);
   });

   if (categoryId == 0) {
      oReq.open("GET", "http://localhost:8080/reservation/api/products?start=" + start);
   }
   else
      oReq.open("GET", "http://localhost:8080/reservation/api/products?categoryId=" + id + "&start=" + start);
   oReq.send();
}

function setProductsElements(data) {
   document.querySelector(".pink").innerText = totalCount + "개";
   var itemList = document.querySelector("#itemList").innerHTML;

   for (var i = 0, len = data.items.length; i < len; i++) {
      var tmp = "";
      tmp += itemList.replace("{id}", data.items[i].productId)
         .replace("{description}", data.items[i].productDescription)
         .replace("{url}", data.items[i].productImageUrl)
         .replace("{description}", data.items[i].productDescription)
         .replace("{placeName}", data.items[i].placeName)
         .replace("{content}", data.items[i].productContent);

      if (i % 2 == 0)
         resultHTML_1 += tmp;
      else
         resultHTML_2 += tmp;
   }

   document.querySelector("#lst_event_box_1").innerHTML = resultHTML_1;
   document.querySelector("#lst_event_box_2").innerHTML = resultHTML_2;
}

function moreButtonEventRegister() {
   button = document.querySelector('.more .btn');
   button.addEventListener("click", function (evt) {
      getProducts(categoryId);
   });
}