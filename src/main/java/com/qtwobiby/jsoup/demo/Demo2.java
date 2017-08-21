package com.qtwobiby.jsoup.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/11.
 */
public class Demo2 {

    public static void main(String[] args) {
        /*String str = "文章来源:医护网";
        String regEx_source = "[^文章来源：|:][\\S]+";
        Pattern sourcePattern = Pattern.compile(regEx_source);
        Matcher sourceMatcher = sourcePattern.matcher(str);
        System.out.println(sourceMatcher.find());
        System.out.println(sourceMatcher.group());*/

        String strDate = "发布时间：2017年6月6号";
        String regEx_date = "[^发布时间：|:][\\S]+";
        Pattern datePattern = Pattern.compile(regEx_date);
        Matcher dateMatcher = datePattern.matcher(strDate);
        System.out.println(dateMatcher.find());
        System.out.println(dateMatcher.group());
        SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy年MM月dd号");
        SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdfFrom.parse(dateMatcher.group().toString());
            System.out.println(date);
            System.out.println(sdfTo.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
