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

    private final String url;
    private final String dbname;

    public RpcClient(String url, String dbname) {
        this.url = url;
        this.dbname = dbname;
    }

    public List<Map> callExecuteQuery(String sql) throws IOException, JsonProcessingException, InterruptedException {
        var response = call("execute_query", sql);
        var json = response.body();
        var mapper = new ObjectMapper();
        var result = Arrays.asList(mapper.readValue(json, Map[].class));

        return result;
    }

    public int callExecuteUpdate(String sql) throws IOException, JsonProcessingException, InterruptedException {
        var response = call("execute_update", sql);
        var result = Integer.parseInt(response.body());

        return result;
    }

    private HttpResponse<String> call(String method, String sql) throws InterruptedException, IOException {
        var params = new HashMap<String, String>();
        params.put("sql", sql);
        params.put("dbname", this.dbname);

        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .POST(buildFormDataFrom(params))
                .uri(URI.create(String.format(this.url + "/sql/" + method)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private HttpRequest.BodyPublisher buildFormDataFrom(Map<String, String> data) {
        var builder = new StringBuilder();
        for (var entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
