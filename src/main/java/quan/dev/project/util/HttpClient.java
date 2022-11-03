package quan.dev.project.util;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;

import java.io.IOException;

public class HttpClient {


    public static CloseableHttpResponse post(String url , HttpEntity httpEntity , Header ...header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(httpEntity);
        httpPost.setHeaders();
        httpPost.setHeader("Authorization", "Basic YWRtaW46bGlsYW1hMTk5Ng==");

        return client.execute(httpPost);

    }

}
