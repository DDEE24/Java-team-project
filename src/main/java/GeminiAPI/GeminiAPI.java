package GeminiAPI;

import okhttp3.*;
import com.google.gson.*;

import java.io.IOException;

public class GeminiAPI {
    private static final String API_KEY = "";
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY;

    private static final OkHttpClient client = new OkHttpClient();

    public static String ask(String prompt) throws IOException {
        JsonObject textPart = new JsonObject();
        textPart.addProperty("text", prompt);

        JsonObject content = new JsonObject();
        JsonArray partsArray = new JsonArray();
        partsArray.add(textPart);
        content.add("parts", partsArray);

        JsonObject bodyJson = new JsonObject();
        JsonArray contentsArray = new JsonArray();
        contentsArray.add(content);
        bodyJson.add("contents", contentsArray);

        RequestBody body;
        body = RequestBody.create(
                bodyJson.toString(),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("HTTP Error: " + response.code());

            JsonObject res = JsonParser.parseString(response.body().string()).getAsJsonObject();
            return res.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();
        }
    }
}
