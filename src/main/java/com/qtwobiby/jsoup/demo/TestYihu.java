package com.qtwobiby.jsoup.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/11.
 */
public class TestYihu {

    public static void main(String[] args) throws IOException {
        String regEx_source = "[^文章来源：|:][\\S]+";
        Pattern sourcePattern = Pattern.compile(regEx_source);

        String regEx_date = "[^发布时间：|:][\\S]+";
        Pattern datePattern = Pattern.compile(regEx_date);

        HealthInformation healthInformation = new HealthInformation();
        healthInformation.setType(1);
        healthInformation.setIsHot(1);
        healthInformation.setStatus(1);
//        healthInformation.setTitle(element.getElementsByTag("img").first().attr("alt"));
//        healthInformation.setSourceUrl(element.attr("href"));
//        healthInformation.setSourceUrlMd5(MD5.toMD5(element.attr("href")));

//        String imgSrc = element.getElementsByTag("img").first().attr("src");
//        healthInformation.setCoverImageUrl(uploadPictureFile(imgSrc));
        String href = "http://www.yihu.com/YihuArticle/1424.shtml";
        Document document = Jsoup.connect(href).userAgent("Mozilla").get();
        Elements sourceAndDate = document.select("c-888 c-f12");

        Matcher sourceMatcher = sourcePattern.matcher(sourceAndDate.get(0).text());
        if (sourceMatcher.find()) {
            healthInformation.setSource(sourceMatcher.group());
        } else {
            healthInformation.setSource(sourceAndDate.get(0).text());
        }

        Matcher dateMatcher = datePattern.matcher(sourceAndDate.get(1).text());
        SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd");

        Date pubDate = null;
        if (dateMatcher.find()) {
            try {
                pubDate = sdfFrom.parse(dateMatcher.group().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            pubDate = new Date();
        }
        healthInformation.setPublishedTime(sdfTo.format(pubDate));
        Elements articleDetails = document.getElementsByClass("article-detail");
        for (Element articleDetail : articleDetails) {
            Elements imgElements = articleDetail.select("img");
            for (Element imgElement : imgElements) {
                System.out.println(imgElement.attr("src"));
//                String imgUrl = img_url + uploadPictureFile(imgElement.attr("src"));
//                System.out.println(imgUrl);
//                imgElement.attr("src", imgUrl);
            }

            Elements aElements = articleDetail.select("a[href]");
            for (Element aElement : aElements) {
                aElement.removeAttr("href");
            }
        }
        healthInformation.setContent(articleDetails.toString());
        healthInformation.setComment("");
        healthInformation.setVsitorCount(0);
        healthInformation.setThumbUpCount(0);
        System.out.println(healthInformation.toString());
    }
}
