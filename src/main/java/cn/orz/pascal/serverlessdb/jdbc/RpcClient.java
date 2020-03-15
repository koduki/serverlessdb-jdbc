package cn.orz.pascal.serverlessdb.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author koduki
 */
public class RpcClient {

    private static HttpRequest.BodyPublisher ofFormData(Map<String, String> data) {
        var builder = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    public static List<Map> callQuery(String sql) throws IOException, JsonProcessingException, InterruptedException {
        var params = new HashMap<String, String>();
        params.put("sql", sql);

        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .POST(ofFormData(params))
                .uri(URI.create("http://172.28.134.216:8080/sql/execute_query"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var json = response.body();
        var mapper = new ObjectMapper();
        var result = Arrays.asList(mapper.readValue(json, Map[].class));

        return result;
    }
}
