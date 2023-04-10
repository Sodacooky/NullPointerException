import requests from "@/api/requests";

export function getArticle(articleId) {
  return requests.get("/service-article/getArticle", {
    params: { articleId: articleId },
  });
}

export function getArticleReply(articleId, page) {
  return requests.get("/service-article/getReplyOf", {
    params: { articleId: articleId, page: page },
  });
}

export function getArticleApprovalAmount(articleId) {
  return requests.get("/service-article/getApprovalAmountOf", {
    params: { articleId: articleId },
  });
}

export function getQuestionInfo(questionId) {
  return requests.get("/service-question/getQuestionInfo", {
    params: { questionId: questionId },
  });
}

export function getQuestionText(questionId) {
  return requests.get("/service-question/getQuestionText", {
    params: { questionId: questionId },
  });
}

export function getQuestionAnswerAmount(questionId) {
  return requests.get("/service-question/getAnswerAmountOf", {
    params: { questionId: questionId },
  });
}

export function getQuestionSubscriptionAmount(questionId) {
  return requests.get("/service-question/getSubscriptionAmountOf", {
    params: { questionId: questionId },
  });
}

export function getQuestionAnswerByTime(questionId, page, isAsc) {
  return requests.get("/service-question/getAnswerByTimeOf", {
    params: { questionId: questionId, page: page, isAsc: isAsc },
  });
}

export function getQuestionAnswerByApproval(questionId, page, isAsc) {
  return requests.get("/service-question/getAnswerByApprovalOf", {
    params: { questionId: questionId, page: page, isAsc: isAsc },
  });
}
