import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class CustomerUser {

    private static final String HOST = "https://www.n11.com/";
    private static final double MIN_WAIT_TIME = 0.1;
    private static final double MAX_WAIT_TIME = 0.2;
    private static List<String> popularKeywords;
    private static List<String> specificKeywords;
    private static List<String> generalKeywords;
    private static Random random = new Random();

    static {
        popularKeywords = readFile("data/popular_keywords");
        specificKeywords = readFile("data/specific_keywords");
        generalKeywords = readFile("data/general_keywords");
    }

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        for (int i = 0; i < 100; i++) {
            int randomNum = random.nextInt(100);
            if (randomNum < 25) {
                getProducts(client, randomChoice(popularKeywords), "get_products_with_popular");
            } else if (randomNum < 40) {
                getProducts(client, randomChoice(specificKeywords), "get_products_with_specific");
            } else {
                getProducts(client, randomChoice(generalKeywords), "get_products_with_general");
            }
        }
    }

    private static void getProducts(CloseableHttpClient client, String keyword, String name) throws IOException {
        String url = HOST + "arama?q=" + keyword;
        System.out.println(url);
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "temp");
        try (CloseableHttpResponse response = client.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                System.out.println("Request successful: " + name)
            }
