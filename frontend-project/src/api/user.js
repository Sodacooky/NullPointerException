import requests, { baseUrl } from "@/api/requests";

export function getLoginStateUrl() {
  return baseUrl + "/service-user/getLoginState";
}

export function getCurrentUser() {
  return requests.get("/service-user/operation/getCurrentUser");
}

export function getUserInfo(userId) {
  return requests.get("/service-user/getUserInfo", {
    params: { userId: userId },
  });
}

export function getUserAvatarUrl(avatarFilename) {
  if (avatarFilename === undefined || avatarFilename === "default") {
    return "/default.jpg";
  } else {
    return baseUrl + "/service-user/avatar/" + avatarFilename;
  }
}

export function getUserQuestion(userId, page) {
  return requests.get("/service-question/getQuestionInfoPublishedBy", {
    params: { userId: userId, page: page },
  });
}

export function getUserAnswer(userId, page) {
  return requests.get("/service-question/getAnswerPublishedBy", {
    params: { userId: userId, page: page },
  });
}

export function getUserArticle(userId, page) {
  return requests.get("/service-article/getArticlePublishedBy", {
    params: { userId: userId, page: page },
  });
}
