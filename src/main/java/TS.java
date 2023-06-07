import com.facebook.presto.jdbc.internal.okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import org.json.JSONObject;

public class TS {

    public static void main(String[] args) {
ValueSetSearch();
    }

    public static String GetToken() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ontology.scot.nhs.uk/authorisation/auth/realms/terminology/protocol/openid-connect/token"))
                .header("Content-Type","application/x-www-form-urlencoded")
                .method("POST",HttpRequest.BodyPublishers.ofString("grant_type=client_credentials&client_id=connectathon-consumer&client_secret=client_secret"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;

        String jsonBody = response.body();
        JSONObject jsonObject = new JSONObject(jsonBody);
        String access_token = jsonObject.getString("access_token");

        return access_token;
    }

    public static void ValueSetSearch() {
        String token = GetToken();
        String x = "v";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ontology.scot.nhs.uk/production1/fhir/ValueSet?name=phys"))
                .header("Authorization",token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;
        System.out.println(response.body());
    }

    public static void PostmanCollectionCode() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id={{client_id}}&client_secret={{client_secret}}");
        Request request = new Request.Builder()
                .url("https://ontology.scot.nhs.uk/authorisation/auth/realms/terminology/protocol/openid-connect/token")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
    }
}
