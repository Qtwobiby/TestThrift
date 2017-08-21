package com.qtwobiby.jsoup.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;

/**
 * Created by Administrator on 2017/8/4.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        String str = "[^文章来源：|:][\\S]+";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (int i = 63; i < 90; i++) {
            Thread.sleep(10000);
            System.out.println("-----" + i + "-----");
            String pathName = "C:\\Users\\Administrator\\Desktop\\123.jpg";
            HttpGet httpGet = new HttpGet("http://news.91160.com/wenda/yiwenyida/list_{0}_1.html".replace("{0}",String.valueOf(i)));

//        HttpGet httpGet = new HttpGet("http://news.91160.com/muying/huaiyun/index.html");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "utf-8");
                response.close();
                System.out.println(">>>>>>>>" + i);
            }

        }
/*        HttpGet httpGet = new HttpGet("http://news.91160.com/yishengshuo/list_{0}_1.html".replace("{0}","2"));

//        HttpGet httpGet = new HttpGet("http://news.91160.com/muying/huaiyun/index.html");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, "utf-8");
        response.close();

        Document doc = Jsoup.parse(content);

        Elements itemElements = doc.getElementsByClass("post_item");
        System.out.println("====输出post_item====");
        for (Element element : itemElements) {
            System.out.println(element.html());
            System.out.println("--------");
        }

//        Elements hrefElements = doc.select("[class=\"add_240_120\"]");
        Elements hrefElements = doc.select("a[href]");
        for (Element element : hrefElements) {
            System.out.println("url：" + element.attr("href"));
//            System.out.println("pic：" + element.getElementsByTag("img").first().attr("src"));
//            System.out.println("url：" + element.getElementsByTag("img").first().attr("alt"));
        }*/
    }
}
