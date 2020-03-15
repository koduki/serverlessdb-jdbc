import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public  HttpRequest.BodyPublisher ofFormData(Map<String, String> data) {
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

var sql = System.getenv().get("SQL");
System.out.println(sql);

var data = new HashMap<String, String>();
data.put("sql", sql);

var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()
var request = HttpRequest.newBuilder().POST(ofFormData(data)).uri(URI.create("http://localhost:8080/sql/execute_query")).header("Content-Type", "application/x-www-form-urlencoded").build()
var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

System.out.println(response.body());

/exit