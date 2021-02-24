package quan.dev.project.r15;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

public class R15Test {

    public static void main( String[] args ) throws IOException {
        Document docMain = Jsoup.connect("https://www.r18.com/videos/vod/movies/detail/-/id=kavr00099/?dmmref=video.movies.popular&i3_ref=list&i3_ord=8").get();
        String title = docMain.title();
        System.out.println("Title : " + title);

        Element productDetailsPage = docMain.getElementsByClass("product-details-page").get(0);

        String titleMovile = productDetailsPage.getElementsByTag("cite").get(0).text();
        System.out.println(titleMovile);

        Element productDetails = productDetailsPage.getElementsByClass("product-details").get(0);

        Element dl1 = productDetails.getElementsByTag("dl").get(0);

        Element dl2 = productDetails.getElementsByTag("dl").get(1);

        getDataDlTag(dl1);
        getDataDlTag(dl2);

        Element productBox = productDetails.getElementsByClass("product-box").get(0);

        getDataProductBox(productBox);

        String detailSinglePicture = productDetailsPage.getElementsByClass("detail-single-picture").get(0).getElementsByTag("img").get(0).attr("src");
        System.out.println(detailSinglePicture);

        Elements previewGrid = productDetailsPage.getElementsByClass("preview-grid").get(0).getElementsByTag("li");
        getSmailImage(previewGrid);



    }

    private static void getDataDlTag(Element dl) {

        Elements ddTags = dl.getElementsByTag("dd");

        for (Element e : ddTags) {
            System.out.println(e.text());
        }

    }

    private static void getDataProductBox(Element productBox) {

        Element actressList = productBox.getElementsByAttributeValue("data-type" , "actress-list").get(0);

        Elements aList = actressList.getElementsByTag("a");

        for (Element a : aList) {
            System.out.println(a.getElementsByTag("span").first().text());
            System.out.println(a.attr("href"));
        }

        Element productCategoriesList = productBox.getElementsByClass("product-categories-list").first();

        Elements spanCategoriesList = productCategoriesList.getElementsByTag("a");

        for (Element a : spanCategoriesList) {
            System.out.println(a.text());
        }

    }

    private static void getSmailImage(Elements previewGrid) {
        for (Element a : previewGrid) {
            System.out.println(a.getElementsByTag("img").first().attr("data-original"));
            System.out.println(a.attr("href"));
        }
    }


}
