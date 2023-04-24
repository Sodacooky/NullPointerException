import requests, { baseUrl } from "@/api/requests";

export class HomeApi {
  static getLatestQuestion(page, queryTime) {
    return requests.get("/service-question/home/latest", {
      params: { page: page, queryTime: queryTime },
    });
  }

  static getWeeklyQuestion() {
    return requests.get("/service-question/home/weekly");
  }

  static getMonthlyQuestion() {
    return requests.get("/service-question/home/monthly");
  }

  static getLatestArticle(page, queryTime) {
    return requests.get("/service-article/home/latest", {
      params: { page: page, queryTime: queryTime },
    });
  }

  static getWeeklyArticle() {
    return requests.get("/service-article/home/weekly");
  }

  static getMonthlyArticle() {
    return requests.get("/service-article/home/monthly");
  }

  static getSiteState() {
    return requests.get("/service-miscellaneous/home/siteState");
  }

  static getAds() {
    return requests.get("/service-miscellaneous/home/adv");
  }

  static getAdsImageUrl(filename) {
    return baseUrl + "/service-miscellaneous/home/advImage/" + filename;
  }

  static getHotCategories() {
    return requests.get("/service-miscellaneous/home/hotCategories");
  }

  static getAnnouncement() {
    return requests.get("/service-miscellaneous/home/announcement");
  }
}
