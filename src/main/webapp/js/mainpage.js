document.addEventListener("DOMContentLoaded", function () {
   tabUserInterfaceObj.getProducts(0);
   document.querySelectorAll('.item > .anchor')[0].className = "anchor active";
   promotionObj.getPromotions();
   tabUserInterfaceObj.tabButtonEventRegister();
   tabUserInterfaceObj.getProductsEventRegister();
});

var promotionObj = {

   getPromotions() {

      var oReq = new XMLHttpRequest();
      oReq.addEventListener("load", function () {
         data = JSON.parse(this.responseText);
         var resultHTML = "";
         var promotionItem = document.querySelector("#promotionItem").innerHTML;

         for (var i = 0, len = data.items.length; i < len; i++) {
            resultHTML += promotionItem.replace("{url}", data.items[i].productImageUrl);
         }

         document.querySelector(".container_visual > ul").innerHTML = resultHTML;

         promotionObj.slidePromotionImage(data.items.length);
      });

      oReq.open("GET", "http://localhost:8080/reservation/api/promotions");
      oReq.send();
   },

   slidePromotionImage(len) {
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

}

var tabUserInterfaceObj = {
   totalCount : 0,
   categoryId : 0,
   start : 0,
   leftColumElements : "",
   rightColumElements : "",
   data : null,

   getProducts(categoryId) {

      var oReq = new XMLHttpRequest();

      oReq.addEventListener("load", function () {
         tabUserInterfaceObj.data = JSON.parse(this.responseText);
         tabUserInterfaceObj.totalCount = tabUserInterfaceObj.data["totalCount"];
         tabUserInterfaceObj.start += tabUserInterfaceObj.data["items"].length;

         if (tabUserInterfaceObj.start >= tabUserInterfaceObj.totalCount)
            document.querySelector('.more .btn').style.cssText = "display: none;";

         tabUserInterfaceObj.setProductElements(tabUserInterfaceObj.data);
         
      });

      if (categoryId == 0) {
         oReq.open("GET", "http://localhost:8080/reservation/api/products?start=" + tabUserInterfaceObj.start);
      }
      else
         oReq.open("GET", "http://localhost:8080/reservation/api/products?categoryId=" + this.categoryId + "&start=" + tabUserInterfaceObj.start);
      oReq.send();
   },

   getProductsEventRegister() {
      button = document.querySelector('.more .btn');
      button.addEventListener("click", function (evt) {
         tabUserInterfaceObj.getProducts(tabUserInterfaceObj.categoryId);
      });
   },

   setProductElements(data) {
      document.querySelector(".pink").innerText = tabUserInterfaceObj.totalCount + "개"; //카테코리별 product 개수 표시
      var itemList = document.querySelector("#itemList").innerHTML;

      for (var i = 0, len = data.items.length; i < len; i++) {
         var tmp = "";
         tmp += itemList.replace("{id}", data.items[i].productId)
            .replace("{description}", data.items[i].productDescription)
            .replace("{url}", data.items[i].productImageUrl)
            .replace("{description}", data.items[i].productDescription)
            .replace("{placeName}", data.items[i].placeName)
            .replace("{content}", data.items[i].productContent);

         if (i % 2 == 0) {
            this.leftColumElements += tmp;
         }
         else {
            this.rightColumElements += tmp;
         }

      }

      document.querySelector("#lst_event_box_1").innerHTML = this.leftColumElements;
      document.querySelector("#lst_event_box_2").innerHTML = this.rightColumElements;
   },

   tabButtonEventRegister() {
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

            tabUserInterfaceObj.categoryId = evt.toElement.dataset.category;

            tabUserInterfaceObj.start = 0;
            tabUserInterfaceObj.leftColumElements = "";
            tabUserInterfaceObj.rightColumElements = "";

            tabUserInterfaceObj.getProducts(tabUserInterfaceObj.categoryId);
         }
      });

   }
}


