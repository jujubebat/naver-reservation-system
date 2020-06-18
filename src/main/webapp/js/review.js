var page;

document.addEventListener("DOMContentLoaded", function () {

    SearchParams = new URLSearchParams(location.search)
    var id = SearchParams.get("id");

    page = new ReviewPage(id);
    page.initReviewPage();

});

function ReviewPage(id) {

    this.id = id;
    this.data = null;
    this.url = "http://localhost:8080/reservation/api/products/";
    this.curIndex = 0;
    this.image_ul = null;

}

ReviewPage.prototype.initReviewPage = function () {

    SearchParams = new URLSearchParams(location.search)
    this.id = SearchParams.get("id");
    this.getDisplayInfo();

}

ReviewPage.prototype.getDisplayInfo = function () {

    var oReq = new XMLHttpRequest();

    oReq.addEventListener("load", function () { //produect 디테일데이터 서버에서 받아옴
        page.data = JSON.parse(this.responseText);
        page.setCommentsGrade();
        page.setComments();
    });
    oReq.open("GET", this.url + this.id);
    oReq.send();

}

ReviewPage.prototype.setCommentsGrade = function () {

    this.data.averageScore = this.data.averageScore.toFixed(1);
    var template = document.querySelector("#commentsGrade").innerHTML;
    var bindTemplate = Handlebars.compile(template);
    var resultHTML = bindTemplate(this.data);

    document.querySelector(".short_review_area").insertAdjacentHTML('afterbegin', resultHTML);
    document.querySelector(".graph_value").style.width = this.data.averageScore / 5 * 100 + "%";

}

ReviewPage.prototype.setComments = function () {

    // 댓글 데이터 전처리 
    for (var i = 0; i < this.data.comments.length; i++) {
        this.data.comments[i].score = this.data.comments[i].score.toFixed(1)
        this.data.comments[i].reservationDate = this.data.comments[i].reservationDate.substr(0, 10).concat(" 방문").replace(/-/gi, ".");
        this.data.comments[i].reservationEmail = this.data.comments[i].reservationEmail.substr(0, 4).concat("****");
    }

    var template = document.querySelector("#review").innerHTML;
    template = template.replace("{{productDescription}}", this.data.displayInfo.productDescription);
    var bindTemplate = Handlebars.compile(template);
    var resultHTML = "";

    for (var i = 0; i < this.data.comments.length; i++) {
        resultHTML += bindTemplate(this.data.comments[i]);
    }

    document.querySelector(".list_short_review").insertAdjacentHTML('beforeend', resultHTML);

}