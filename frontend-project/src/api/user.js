import requests, { baseUrl } from "@/api/requests";

export class UserApi {
  static getCurrentUser() {
    return requests.get("/service-user/operation/getCurrentUser");
  }

  static getUserInfo(userId) {
    return requests.get("/service-user/getUserInfo", {
      params: { userId: userId },
    });
  }

  static getUserAvatarUrl(avatarFilename) {
    if (avatarFilename === undefined || avatarFilename === "default") {
      return "/default.jpg";
    } else {
      return baseUrl + "/service-user/avatar/" + avatarFilename;
    }
  }

  static getUserQuestion(userId, page) {
    return requests.get("/service-question/getQuestionInfoPublishedBy", {
      params: { userId: userId, page: page },
    });
  }

  static getUserAnswer(userId, page) {
    return requests.get("/service-question/getAnswerPublishedBy", {
      params: { userId: userId, page: page },
    });
  }

  static getUserArticle(userId, page) {
    return requests.get("/service-article/getArticlePublishedBy", {
      params: { userId: userId, page: page },
    });
  }
}
