package com.zaihua.pachong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 9:55
 */
public class StocksPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {

        try {
            //获取tbody
            Document doc = Jsoup.parse(page.getHtml().toString());
            Element ele = doc.select("div").select("#quotesearch").first();
            Elements ul1 = ele.select("ul").first().select("li");
            Elements ul2 = ele.select("ul").last().select("li");

            StocksListService.szs.clear();
            StocksListService.shs.clear();

            //sh
            for(Element element : ul1) {
                String url = element.select("a").first().attributes().get("href");
                String text = element.text();

                String[] urls = url.split("/");
                String code = urls[urls.length -1].replace(".html", "");

                String num = code.replace("sh", "");

                if (num.startsWith("6")) {
                    StocksListService.shs.put(code, text);
                }
            }


            for(Element element : ul2) {
                String url = element.select("a").first().attributes().get("href");
                String text = element.text();

                String[] urls = url.split("/");
                String code = urls[urls.length -1].replace(".html", "");

                String num = code.replace("sz", "");

                if ((num.startsWith("0") && !num.startsWith("03")) || num.startsWith("3")) {
                    StocksListService.szs.put(code, text);
                }
            }

        } catch (Exception e) {

        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
