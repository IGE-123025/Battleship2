package battleship.ai.utils;

import java.io.*;
import java.net.*;

public class LLMClient {

    public static String call (String prompt) throws Exception {

        URL url = new URL("http://localhost:8000/play");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        String safePrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        String json = "{\"prompt\": \"" + safePrompt + "\"}";

        try(OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        return br.readLine();
    }
}
