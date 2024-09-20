import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static String getImageOfTheDayURL(String nasaURL) {
        try {
            HttpGet request = new HttpGet(nasaURL);
            CloseableHttpResponse response = httpclient.execute(request);
            ObjectMapper mapper = new ObjectMapper();
            NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
            return answer.url;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static void downloadImageOfTheDay(String imgURL) {
        try {
            HttpGet imgRequest = new HttpGet(imgURL);
            CloseableHttpResponse imgResponse = httpclient.execute(imgRequest);

            String[] arr = imgURL.split("/");
            String fileName = arr[arr.length - 1];
            System.out.println(fileName);

            FileOutputStream file = new FileOutputStream("img/" + fileName);
            imgResponse.getEntity().writeTo(file);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
