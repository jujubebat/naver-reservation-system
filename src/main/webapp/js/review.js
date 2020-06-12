document.addEventListener("DOMContentLoaded", function () {
    reviewPageObj.initReviewPage();
});

var reviewPageObj = {
    id: 0,
    curIndex: 0,
    data: null,
    image_ul: null,

    initReviewPage() {
        SearchParams = new URLSearchParams(location.search)
        this.id = SearchParams.get("id");
        this.getProducts();
    },

    getProducts() {
        var oReq = new XMLHttpRequest();
        oReq.addEventListener("load", function () { //produect 디테일데이터 서버에서 받아옴
            reviewPageObj.data = JSON.parse(this.responseText);
            reviewPageObj.setCommentsGrade();
            reviewPageObj.setComments();
        });
        oReq.open("GET", "http://localhost:8080/reservation/api/products/" + this.id);
        oReq.send();
    },

    // 댓글 점수 templating
    setCommentsGrade() {
        this.data.averageScore = this.data.averageScore.toFixed(1);
        var template = document.querySelector("#commentsGrade").innerHTML;
        var bindTemplate = Handlebars.compile(template);
        var resultHTML = bindTemplate(this.data);

        document.querySelector(".short_review_area").insertAdjacentHTML('afterbegin', resultHTML);
        document.querySelector(".graph_value").style.width = this.data.averageScore / 5 * 100 + "%";
    },

    // 댓글 templating 
    setComments() {
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
    },
}
