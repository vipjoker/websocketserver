package client;

import javafx.application.Platform;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by Makhobey Oleh on 6/2/16.
 * email: tajcig@ya.ru
 */
public class Client {

    private static Client INSTANCE;
    private HttpClient mClient;
    ExecutorService executorService;
    private Client() {
        init();
    }

    public static Client getInstance() {
        if (INSTANCE == null) INSTANCE = new Client();
        return INSTANCE;
    }

    private void init() {
        mClient = HttpClientBuilder.create().build();

        executorService = Executors.newFixedThreadPool(5);

    }

    public void get(String url, Consumer<String> consumer ) {

        HttpGet request = new HttpGet(url);
        executorService.execute(()->processRequest(request,consumer));
    }


    private void processRequest(HttpUriRequest request,Consumer<String> consumer) {

        StringBuilder builder = new StringBuilder();

        try {
            HttpResponse response = mClient.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(()-> consumer.accept(builder.toString()));


    }


    public void post() {
        HttpPost post = new HttpPost("http://restUrl");
        StringEntity input = null;
        try {
            input = new StringEntity("product");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(input);
       // void processRequest(post);


    }

    public void postWithParams() {
        HttpPost post = new HttpPost("http://restUrl");
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("name", "value")); //you can as many name value pair as you want in the list.
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       // processRequest(post);

    }
}
