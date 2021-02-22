package addmefast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.*;
import java.util.List;


/**
 * Created by https://github.com/kwanpham
 */
public class Addmefast {
    public static void main(String[] args) throws Exception {
        String url = "http://addmefast.com/pinterest-folllowers-pinterest-repins-likes";


        Addmefast http = new Addmefast();

        // make sure cookies is turn on
        CookieManager cm = new CookieManager(null , CookiePolicy.ACCEPT_ALL );
        CookieManager.setDefault(cm);

        // 1. Send a "GET" request, so that you can extract the form's data.
        String page = http.GetPageContent(url);

        http.sendPost("http://addmefast.com/pinterest-folllowers-pinterest-repins-likes" , "email=camxuctroll@gmail.com&password=okvippro&login_button=Login");


        String page1 = http.GetPageContent("http://addmefast.com/welcome");



        Document doc = Jsoup.parse(page1);


        // Google form id
        Elements points = doc.getElementsByClass("points_count");
        Element point = points.get(0);

        System.out.println(point.text());
    }

    private List<String> cookies;
    private HttpURLConnection conn;

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    private void setRequestHeader(HttpURLConnection c) {


        c.setRequestProperty("Accept" ,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        c.setRequestProperty("Accept-Encoding" ,"gzip, deflate");
        c.setRequestProperty("Accept-Language" , "en-US,en;q=0.5");
        c.setRequestProperty("Cache-Control" ,"max-age=0");
        c.setRequestProperty("Connection" , "keep-alive");
        c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        c.setRequestProperty("Host","http://addmefast.com");
        c.setRequestProperty("Origin","http://addmefast.com");
        c.setRequestProperty("Referer","http://addmefast.com/login");
        c.setRequestProperty("Upgrade-Insecure-Requests" ,"1");
        c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
        c.setUseCaches(false);


        c.setDoOutput(true);
        c.setDoInput(true);
    }


    private String GetPageContent(String url) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        // default is GET
        conn.setRequestMethod("GET");

        conn.setUseCaches(false);

        // act like a browser
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//        if (cookies != null) {
//            for (String cookie : this.cookies) {
//                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//            }
//        }
        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Get the response cookies
        setCookies(conn.getHeaderFields().get("Set-Cookie"));

        return response.toString();

    }

    private void sendPost(String url, String postParams) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();


        // Acts like a browser
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        setRequestHeader(conn);
        conn.setRequestProperty("Content-Length" , postParams.length()+"");
//        for (String cookie : this.cookies) {
//            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//        }
//
//        for (String cookie : this.cookies) {
//            System.out.println(cookie);
//        }


        // Send post request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);

//        InputStream in = conn.getInputStream();
//        GZIPInputStream gzis=new GZIPInputStream(in);
//        InputStreamReader reader = new InputStreamReader(gzis);
//        BufferedReader br = new BufferedReader(reader);
//
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = br.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//        System.out.println(response.toString());

    }


}
