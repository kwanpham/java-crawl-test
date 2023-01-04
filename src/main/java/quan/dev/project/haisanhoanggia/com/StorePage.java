package quan.dev.project.haisanhoanggia.com;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
public class StorePage {

    @Test
    public void crawlStorePage() throws IOException {

        Document docMain = Jsoup.connect("https://haisanhoanggia.com/collections/all").get();

        Element productList = docMain.getElementsByClass("product-list").get(0);

        Elements listProductItem = productList.getElementsByClass("product-block");

        for (Element e : listProductItem) {

            String detailProductlink = e.getElementsByTag("a").get(0).attr("href");
            log.info(detailProductlink);


        }

    }

    @Test
    public void crawlDetailProduct() throws IOException {

        Document docMain = Jsoup.connect("https://haisanhoanggia.com/products/tom-hum-bong-1").get();

        Element productDiv = docMain.getElementById("product").getElementsByClass("product-detail-main").get(0);

        String title = productDiv.getElementsByClass("product-title").get(0).getElementsByTag("h1").get(0).text();

        log.info(title);

        String shortDes = productDiv.getElementsByClass("short-des").get(0).html();

        log.info(shortDes);

        String price = productDiv.getElementById("price-preview").getElementsByTag("span").get(0).text();

        log.info(price);

        Elements priceSaleDiv = productDiv.getElementById("price-preview").getElementsByTag("del");

        if (!priceSaleDiv.isEmpty()) {
            log.info(priceSaleDiv.get(0).text());
        }



        String longDesc = productDiv.getElementById("tab-1").child(0).html();

        log.info(longDesc);

        String thumbImageLink = productDiv.getElementsByClass("product-thumb").get(0).child(0).attr("src");

        log.info(thumbImageLink);

        Elements listImage = productDiv.getElementById("slider").children();


        for (Element e : listImage) {
            log.info(e.getElementsByTag("img").get(0).attr("src"));
        }



    }
}
