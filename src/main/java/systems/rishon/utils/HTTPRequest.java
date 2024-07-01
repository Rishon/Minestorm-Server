package systems.rishon.utils;

import java.net.URI;
import java.net.http.HttpRequest;

public class HTTPRequest {

    public static String getHTTPRequest(String url) {
        // Send a GET request
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        return request.toString();
    }

    public static String postHTTPRequest(String url, String body) {
        // Send a POST request
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(body)).build();
        return request.toString();
    }

}
