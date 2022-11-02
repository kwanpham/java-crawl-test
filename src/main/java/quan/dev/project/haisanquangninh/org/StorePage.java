package quan.dev.project.haisanquangninh.org;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class StorePage {

    @Test
    public void crawlStorePage() throws IOException {

        Document docMain = Jsoup.connect("http://haisanquangninh.org/danhmuc/Hai-san-tuoi-song/2/p_1.html").get();

        Element productWrapper = docMain.getElementsByClass("product-wrapper").get(0);

        Elements listProductItem = productWrapper.getElementsByClass("product-item");

        for (Element e : listProductItem) {

            String detailProductlink = e.getElementsByTag("a").get(0).attr("href");
            log.info(detailProductlink);
        }

    }

    @Test
    public void crawlDetailProductPage() throws IOException {
        Document docMain = Jsoup.connect("http://haisanquangninh.org/sanpham/Tom-Van/44.html").get();
        Element detailProduct = docMain.getElementsByClass("detail-product").get(0);
        String productTile = detailProduct.getElementsByTag("h1").get(0).text();
        log.info(productTile);

        String productPrice = detailProduct.getElementsByClass("product_price").get(0).text();
        log.info(productPrice);

        String shortDesc = detailProduct.getElementsByClass("desc-title").get(0).nextSibling().toString();
        log.info(shortDesc);

        String longDesc = docMain.getElementById("mota").children().html();
        log.info(longDesc);

        String imageUrl = "http://haisanquangninh.org/" + docMain.getElementById("zoomparent").getElementsByTag("img").get(0).attr("src");

        BufferedImage image = ImageIO.read(new URL(imageUrl));

        ImageIO.write(image, FilenameUtils.getExtension(imageUrl),new File("tmp/image/" + FilenameUtils.getName(imageUrl)));

    }


}