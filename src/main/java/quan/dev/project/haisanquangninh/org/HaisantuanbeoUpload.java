package quan.dev.project.haisanquangninh.org;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import quan.dev.project.util.JsonUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class HaisantuanbeoUpload {

    @Test
    public void uploadImageToHaSanTuanBeo() throws IOException {

        String imageUrl = "http://fashion3.ninhbinhweb.biz/wp-content/uploads/2020/03/icon-messenger.png.pagespeed.ce_.sSebhnGGgP.png";
        BufferedImage image = ImageIO.read(new URL(imageUrl.replaceAll(" ", "%20")));
        ImageIO.write(image, FilenameUtils.getExtension(imageUrl), new File("tmp/image/" + FilenameUtils.getName(imageUrl)));

        String mime = "";
        String extension = FilenameUtils.getExtension(imageUrl);
        if (extension.equalsIgnoreCase("png")) {
            mime = "image/png";
        }
        if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg")) {
            mime = "image/jpeg";
        }

        CloseableHttpClient client = HttpClients.createDefault();

        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("file", Files.newInputStream(Paths.get("tmp/image/" + FilenameUtils.getName(imageUrl))), ContentType.create(mime), FilenameUtils.getName(imageUrl))
                .build();

        HttpPost httpPost = new HttpPost("https://haisantuanbeo.com/wp-json/wp/v2/media");
        httpPost.setEntity(entity);
        httpPost.setHeader("Authorization" , "Basic YWRtaW46bGlsYW1hMTk5Ng==");

        CloseableHttpResponse httpResponse = client.execute(httpPost);
        log.info("Status:" + httpResponse.getCode());
        log.info("Content:");
        String content = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        log.info("--------------------------------------------------------");
        log.info(content);
    }

    @Test
    public void crawlProductFromHaisaiquangninhToHaisantuanbeo() throws IOException, ParseException {


        for (int i = 1; i <= 1; i++) {
            Document docMain = Jsoup.connect(String.format("http://haisanquangninh.org/danhmuc/Hai-san-kho/1.html")).get();

            Element productWrapper = docMain.getElementsByClass("product-wrapper").get(0);

            Elements listProductItem = productWrapper.getElementsByClass("product-item");

            for (Element e : listProductItem) {
                String detailProductlink = e.getElementsByTag("a").get(0).attr("href");
                log.info("Crawl detail url: " + detailProductlink);
                Document docMainDetailProdcut = Jsoup.connect("http://haisanquangninh.org/" + detailProductlink).get();

                Element detailProduct = docMainDetailProdcut.getElementsByClass("detail-product").get(0);
                String productTile = detailProduct.getElementsByTag("h1").get(0).text();
                log.info(productTile);

//                String result = Jsoup.connect("https://haisantuanbeo.com/wp-json/wc/v3/products?search="+productTile)
//                        .header("Content-Type", "application/json")
//                        .header("Accept", "application/json")
//                        .followRedirects(true)
//                        .ignoreHttpErrors(true)
//                        .ignoreContentType(true)
//                        .userAgent("Mozilla/5.0 AppleWebKit/537.36 (KHTML," +
//                                " like Gecko) Chrome/45.0.2454.4 Safari/537.36")
//                        .header("Authorization", "Basic Y2tfZmZmZGFlMTliOTA0N2U4ZGE4MjgxNzcyNmZiZTU4YzBlZmI4OTcxMzpjc18wN2NjZTkwZTIzNjRmY2M5MTFiNzVkMWE1NjUzZDRjM2FmZDJlMGZj")
//                        .method(Connection.Method.GET)
//                        .execute().body();
//                log.info("result search " + result);
//                if (!result.equals("[]")) {
//                    continue;
//                }


                String productPrice = detailProduct.getElementsByClass("product_price").get(0).text();
                log.info(productPrice);

                String shortDesc = detailProduct.getElementsByClass("desc-title").get(0).nextSibling().toString();
                log.info(shortDesc);

                String longDesc = docMainDetailProdcut.getElementById("mota").children().html();
                log.info(longDesc);

                longDesc = longDesc.substring(longDesc.indexOf(">")+1);
                longDesc = longDesc.replace("src=\"/data/dulieu/images/" , "src=\"https://haisanquangninh.org/data/dulieu/images/");
                longDesc = longDesc.replace("http://haisanquangninh.org/baiviet/Lien-he/5.html" , "https://haisantuanbeo.com");
                longDesc = longDesc.replace("Cửa hàng hải sản Quảng Ninh" , "Cửa hàng hải sản Tuấn Béo");
                longDesc = longDesc.replace("số 26, ngách 8/11/36/105 Lê Quang Đạo, tổ 5, Phú Đô, Nam Từ Liêm, Hà Nội." , "P. Trích Sài, Bưởi, Tây Hồ, Hà Nội");
                longDesc = longDesc.replace("097 113 88 89 - 0916 695 087" , "093 422 69 99");
                longDesc = longDesc.replace("<a href=\"http://haisanquangninh.org/danhmuc/Hai-san-tuoi-song/2.html\">http://haisanquangninh.org</a>" , "<a href=\"https://haisantuanbeo.com\">http://haisantuanbeo.com</a>");
                longDesc = longDesc.replace("haisanquangninh.org@gmail.com" , "haisantuanbeo.com@gmail.com");
                longDesc = longDesc.replace("<a href=\"https://www.facebook.com/haisanquangninh.org\">https://www.facebook.com/haisanquangninh.org</a>" , "<a href=\"https://www.facebook.com/tuanbeohaisan\">https://www.facebook.com/tuanbeohaisan</a>");

                String imageUrl = docMainDetailProdcut.getElementById("zoomparent").getElementsByTag("img").get(0).attr("src");

                if (!imageUrl.contains("haisanquangninh.org")) {
                    imageUrl = "http://haisanquangninh.org/" + imageUrl;
                }


                BufferedImage image = ImageIO.read(new URL(imageUrl.replaceAll(" ", "%20")));
                ImageIO.write(image, FilenameUtils.getExtension(imageUrl), new File("tmp/image/" + FilenameUtils.getName(imageUrl)));

                File file = new File("tmp/image/" + FilenameUtils.getName(imageUrl));
                String mime = "";
                String extension = FilenameUtils.getExtension(file.getName());
                if (extension.equalsIgnoreCase("png")) {
                    mime = "image/png";
                }
                if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg")) {
                    mime = "image/jpeg";
                }

                CloseableHttpClient client1 = HttpClients.createDefault();
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("file", Files.newInputStream(file.toPath()), ContentType.create(mime), file.getName())
                        .build();

                HttpPost httpPost = new HttpPost("https://haisantuanbeo.com/wp-json/wp/v2/media");
                httpPost.setEntity(entity);
                httpPost.setHeader("Authorization" , "Basic YWRtaW46bGlsYW1hMTk5Ng==");

                CloseableHttpResponse httpResponse = client1.execute(httpPost);
                String content = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
                log.info("--------------------------------------------------------");
                log.info(content);


                JsonNode responseImageNode = new ObjectMapper().readTree(content);
                String newImageUrl = JsonUtils.getStringVal(responseImageNode , "/guid/raw");
                log.info("newImageUrl: " + newImageUrl);
                JsonNode productRequestNode = new ObjectMapper().readTree("{\"name\":\"\",\"type\":\"\",\"regular_price\":\"\",\"description\":\"\",\"short_description\":\"\",\"categories\":[{\"id\":\"\"}],\"images\":[{\"src\":\"\"}]}");

                int category = 0;
                String productTileLower = productTile.toLowerCase();
                if (productTileLower.contains("bạch tuộc") || productTileLower.contains("mực")) {
                    category = 77;
                }
                if (productTileLower.contains("bào ngư") || productTileLower.contains("hầu")) {
                    category = 76;
                }
                if (productTileLower.contains("cá")) {
                    category = 74;
                }
                if (productTileLower.contains("cua") || productTileLower.contains("ghẹ")) {
                    category = 73;
                }
                if (productTileLower.contains("nghêu") || productTileLower.contains("sò") || productTileLower.contains("ốc")) {
                    category = 75;
                }
                if (productTileLower.contains("tôm") || productTileLower.contains("bề bề")) {
                    category = 72;
                }


                JsonUtils.setVal(productRequestNode , "/name" , productTile);
                JsonUtils.setVal(productRequestNode , "/type" , "simple");
                JsonUtils.setVal(productRequestNode , "/regular_price" , productPrice.replace("." , "").replace("VND" , ""));
                JsonUtils.setVal(productRequestNode , "/description" , longDesc);
                JsonUtils.setVal(productRequestNode , "/short_description" , shortDesc);
                JsonUtils.setVal(productRequestNode , "/categories/0/id" , Integer.toString(category), 1);
                JsonUtils.setVal(productRequestNode, "/images/0/src", newImageUrl, 1);


                CloseableHttpClient client2 = HttpClients.createDefault();
                HttpPost httpPost2 = new HttpPost("https://haisantuanbeo.com/wp-json/wc/v3/products");
                log.info("Requset: " + productRequestNode.toPrettyString());
                StringEntity entity2 = new StringEntity(productRequestNode.toPrettyString() , StandardCharsets.UTF_8);
                httpPost2.setEntity(entity2);
                httpPost2.setHeader("Content-type", "application/json");
                httpPost2.setHeader("Authorization", "Basic Y2tfZmZmZGFlMTliOTA0N2U4ZGE4MjgxNzcyNmZiZTU4YzBlZmI4OTcxMzpjc18wN2NjZTkwZTIzNjRmY2M5MTFiNzVkMWE1NjUzZDRjM2FmZDJlMGZj");
                CloseableHttpResponse response2 = client2.execute(httpPost2);
                log.info("Response: " + EntityUtils.toString(response2.getEntity()));
                client2.close();
            }
        }
    }

    @Test
    public void replaceLongDescHaiSanQuangNinhToHaiSanTuanBeo() throws IOException {
        String text = Files.readString(Paths.get("test/longDesc.txt"));
       // System.out.println(text);
        text = text.substring(text.indexOf(">")+1);
        text = text.replace("src=\"/data/dulieu/images/" , "src=\"https://haisanquangninh.org/data/dulieu/images/");
        text = text.replace("http://haisanquangninh.org/baiviet/Lien-he/5.html" , "https://haisantuanbeo.com");
        text = text.replace("Cửa hàng hải sản Quảng Ninh" , "Cửa hàng hải sản Tuấn Béo");
        text = text.replace("số 26, ngách 8/11/36/105 Lê Quang Đạo, tổ 5, Phú Đô, Nam Từ Liêm, Hà Nội." , "P. Trích Sài, Bưởi, Tây Hồ, Hà Nội");
        text = text.replace("097 113 88 89 - 0916 695 087" , "093 422 69 99");
        text = text.replace("<a href=\"http://haisanquangninh.org/danhmuc/Hai-san-tuoi-song/2.html\">http://haisanquangninh.org</a>" , "<a href=\"https://haisantuanbeo.com\">http://haisantuanbeo.com</a>");
        text = text.replace("haisanquangninh.org@gmail.com" , "haisantuanbeo.com@gmail.com");
        text = text.replace("<a href=\"https://www.facebook.com/haisanquangninh.org\">https://www.facebook.com/haisanquangninh.org</a>" , "<a href=\"https://www.facebook.com/tuanbeohaisan\">https://www.facebook.com/tuanbeohaisan</a>");
        System.out.println(text);

    }
}