package quan.dev.project.coinmarketcap.com;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPage {

    @Test
    public void MainPageCrawlListCoin() throws IOException {
        Document docMain = Jsoup.parse(new File("src/main/java/quan/dev/project/coinmarketcap/com/main.txt"));

        List<Element> listTr = docMain.getElementsByTag("tbody").get(0).getElementsByTag("tr");

        List<MoneyObject> moneyObjects = new ArrayList<>();

        for (Element trTag : listTr) {
            String idMoneyRaw = trTag.getElementsByTag("img").get(0).attr("src");
            String idMoney = FilenameUtils.getBaseName(idMoneyRaw);
          //  System.out.println(FilenameUtils.getBaseName(idMoney));

            String moneyName = trTag.getElementsByTag("td").get(2).getElementsByTag("p").get(0).text();
         //   System.out.println(moneyName);

            MoneyObject m = new MoneyObject();
            m.setText(idMoney);
            m.setValue(moneyName);

            moneyObjects.add(m);

        }

        System.out.println(new ObjectMapper().writeValueAsString(moneyObjects));

    }

    @Data
    public class MoneyObject {
        public String text;
        public String value;
    }

}
