import com.facebook.presto.jdbc.internal.okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class TS {

    public static void main(String[] args) {
GetToken();
    }

    public static String GetToken() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ontology.scot.nhs.uk/authorisation/auth/realms/terminology/protocol/openid-connect/token"))
                .header("Content-Type","application/x-www-form-urlencoded")
                .method("POST",HttpRequest.BodyPublishers.ofString("grant_type=client_credentials&client_id=connectathon-consumer&client_secret=xxx"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;
      // System.out.println(response.body());
        String body = response.body();
        return body;
    }

    public static void ValueSetSearch() {
        String token = GetToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ontology.scot.nhs.uk/production1/fhir/Valueset?name=mental"))
                .header("Authorization",token)
                .method("GET",null)
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;
        // System.out.println(response.body());
        String body = response.body();

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
