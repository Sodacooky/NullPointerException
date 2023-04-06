import requests from "@/api/requests";

export function searchQuestionByTime(keyword, page, isAsc) {
  return requests.get("/service-question/search/infoByTime", {
    params: { keyword, page, isAsc },
  });
}

export function searchQuestionBySubAmount(keyword, page, isAsc) {
  return requests.get("/service-question/search/infoBySubscriptionAmount", {
    params: { keyword, page, isAsc },
  });
}

export function searchQuestionByAnsAmount(keyword, page, isAsc) {
  return requests.get("/service-question/search/infoByAnswerAmount", {
    params: { keyword, page, isAsc },
  });
}

export function searchArticleByTime(keyword, page, isAsc) {
  return requests.get("/service-article/search/byTime", {
    params: { keyword, page, isAsc },
  });
}

export function searchArticleByApproval(keyword, page, isAsc) {
  return requests.get("/service-article/search/byApproval", {
    params: { keyword, page, isAsc },
  });
}

export function searchArticleByReplyAmount(keyword, page, isAsc) {
  return requests.get("/service-article/search/byReplyAmount", {
    params: { keyword, page, isAsc },
  });
}

export function searchUserByRegisterTime(keyword, page, isAsc) {
  return requests.get("/service-user/search/infoByRegisterTime", {
    params: { keyword, page, isAsc },
  });
}
