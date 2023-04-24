import requests from "@/api/requests";

export class SearchApi {
  static searchQuestionByTime(keyword, page, isAsc) {
    return requests.get("/service-question/search/infoByTime", {
      params: { keyword, page, isAsc },
    });
  }

  static searchQuestionBySubAmount(keyword, page, isAsc) {
    return requests.get("/service-question/search/infoBySubscriptionAmount", {
      params: { keyword, page, isAsc },
    });
  }

  static searchQuestionByAnsAmount(keyword, page, isAsc) {
    return requests.get("/service-question/search/infoByAnswerAmount", {
      params: { keyword, page, isAsc },
    });
  }

  static searchArticleByTime(keyword, page, isAsc) {
    return requests.get("/service-article/search/byTime", {
      params: { keyword, page, isAsc },
    });
  }

  static searchArticleByApproval(keyword, page, isAsc) {
    return requests.get("/service-article/search/byApproval", {
      params: { keyword, page, isAsc },
    });
  }

  static searchArticleByReplyAmount(keyword, page, isAsc) {
    return requests.get("/service-article/search/byReplyAmount", {
      params: { keyword, page, isAsc },
    });
  }

  static searchUserByRegisterTime(keyword, page, isAsc) {
    return requests.get("/service-user/search/infoByRegisterTime", {
      params: { keyword, page, isAsc },
    });
  }
}
