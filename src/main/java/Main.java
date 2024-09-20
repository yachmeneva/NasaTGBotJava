import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello maven project");
        String token = "ZNuIJp2oMb7GRrIzd7egHatrhTLt2bhbdiX0jucC";
        String date = "2024-09-18";
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=" + token + "&date=" + date);
        CloseableHttpResponse response = httpclient.execute(request);

        //Scanner sc = new Scanner(response.getEntity().getContent());
        //System.out.println(sc.nextLine());

        ObjectMapper mapper = new ObjectMapper();
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
        System.out.println(answer.url);

        HttpGet imgRequest = new HttpGet(answer.url);
        CloseableHttpResponse imgResponse = httpclient.execute(imgRequest);

        String[] arr = answer.url.split("/");
        String fileName = arr[arr.length - 1];
        System.out.println(fileName);

        FileOutputStream file = new FileOutputStream("img/" + fileName);
        imgResponse.getEntity().writeTo(file);
    }
}
