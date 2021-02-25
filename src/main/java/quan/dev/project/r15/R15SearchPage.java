package quan.dev.project.r15;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class R15SearchPage {

    public  void crawlPage() throws IOException {

        Document docMain = Jsoup.connect("https://www.r18.com/common/search/order=new/pagesize=120/unit=USD/page=1/").get();
        String title = docMain.title();

        Element cmnListProduct01 = docMain.getElementsByClass("cmn-list-product01").get(0);

        Elements listProductLiTag = cmnListProduct01.getElementsByTag("li");

        getDataFromListProduct(listProductLiTag);

        Elements listLiPage = docMain.getElementsByClass("col04").get(0).getElementsByTag("li");

        getLastPage(listLiPage);
    }

    private void getDataFromListProduct(Elements listProduct) {

        for (Element element : listProduct) {
            element = element.child(0);
            System.out.println("link : " + element.attr("href"));
            Element img = element.getElementsByTag("img").get(0);
            System.out.println("code " + img.attr("alt"));
        }

    }

    private void getLastPage(Elements liTag) {

        Element liTagLastPage = liTag.last().previousElementSibling();
        System.out.println(liTagLastPage.child(0).text());

    }

    public static void main(String[] args) throws IOException {
        new R15SearchPage().crawlPage();
    }

}
