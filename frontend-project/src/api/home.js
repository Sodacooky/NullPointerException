import requests, { baseUrl } from "@/api/requests";

export class HomeApi {
  static getLatestQuestion(page, queryTime) {
    return requests.get("/service-question/public/home/latest", {
      params: { page: page, queryTime: queryTime },
    });
  }

  static getWeeklyQuestion() {
    return requests.get("/service-question/public/home/weekly");
  }

  static getMonthlyQuestion() {
    return requests.get("/service-question/public/home/monthly");
  }

  static getLatestArticle(page, queryTime) {
    return requests.get("/service-article/public/home/latest", {
      params: { page: page, queryTime: queryTime },
    });
  }

  static getWeeklyArticle() {
    return requests.get("/service-article/public/home/weekly");
  }

  static getMonthlyArticle() {
    return requests.get("/service-article/public/home/monthly");
  }

  static getSiteState() {
    return requests.get("/service-miscellaneous/public/home/siteState");
  }

  static getAds() {
    return requests.get("/service-miscellaneous/public/home/adv");
  }

  static getAdsImageUrl(filename) {
    return baseUrl + "/service-miscellaneous/public/home/advImage/" + filename;
  }

  static getHotCategories() {
    return requests.get("/service-miscellaneous/public/home/hotCategories");
  }

  static getAnnouncement() {
    return requests.get("/service-miscellaneous/public/home/announcement");
  }
}
