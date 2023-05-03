import requests from "@/api/requests";

export class ReadingApi {
  static getArticle(articleId) {
    return requests.get("/service-article/public/getArticle", {
      params: { articleId: articleId },
    });
  }

  static getArticleReply(articleId, page) {
    return requests.get("/service-article/public/getReplyOf", {
      params: { articleId: articleId, page: page },
    });
  }

  static getArticleApprovalAmount(articleId) {
    return requests.get("/service-article/public/getApprovalAmountOf", {
      params: { articleId: articleId },
    });
  }

  static getQuestionInfo(questionId) {
    return requests.get("/service-question/public/getQuestionInfo", {
      params: { questionId: questionId },
    });
  }

  static getQuestionText(questionId) {
    return requests.get("/service-question/public/getQuestionText", {
      params: { questionId: questionId },
    });
  }

  static getQuestionAnswerAmount(questionId) {
    return requests.get("/service-question/public/getAnswerAmountOf", {
      params: { questionId },
    });
  }

  static getQuestionSubscriptionAmount(questionId) {
    return requests.get("/service-question/public/getSubscriptionAmountOf", {
      params: { questionId: questionId },
    });
  }

  static getQuestionAnswerByTime(questionId, page, isAsc) {
    return requests.get("/service-question/public/getAnswerByTimeOf", {
      params: { questionId: questionId, page: page, isAsc: isAsc },
    });
  }

  static getQuestionAnswerByApproval(questionId, page, isAsc) {
    return requests.get("/service-question/public/getAnswerByApprovalOf", {
      params: { questionId: questionId, page: page, isAsc: isAsc },
    });
  }
}
