package app.controllers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestApiController {

    private String apiUrl;

    private String auth;

    public RestApiController(String username, String apikey, String apiUrl) {
        this.apiUrl = apiUrl;
        this.auth = new StringBuffer("ApiKey ").append(username).append(":").append(apikey).toString();
    }

    public JSONObject doPostRequest(String jsonData) {
        HttpPost post = new HttpPost(apiUrl);
        post.setHeader("Authorization", auth);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setHeader("X-Stream", "true");

        return executePostRequest(jsonData, post);
    }

    public JSONObject doGetRequest(String url) {
        HttpGet get = new HttpGet(apiUrl + url + "/");
        get.setHeader("Authorization", auth);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("Accept", "application/json");
        get.setHeader("X-Stream", "true");

        return executeGetRequest(get);
    }

    private JSONObject executeGetRequest(HttpGet httpGet) {
        try {
            return executeGetHttpRequest(httpGet);
        } catch (IOException e) {
            System.out.println("Error appeared when reading HTTP response: " + e);
        } catch (ParseException e) {
            System.out.println("Error appeared while parsing the response JSON: " + e);
        } finally {
            httpGet.releaseConnection();
        }

        return null;
    }

    private JSONObject executeGetHttpRequest(HttpGet httpGet) throws IOException, ParseException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpGet);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        System.out.println("Response json : " + result.toString());

        return (JSONObject) new JSONParser().parse(result.toString());
    }

    private JSONObject executePostRequest(String jsonData, HttpPost httpPost) {
        try {
            return executePostHttpRequest(jsonData, httpPost);
        } catch (IOException e) {
            System.out.println("Error appeared when reading HTTP response: " + e);
        } catch (ParseException e) {
            System.out.println("Error appeared while parsing the response JSON: " + e);
        } finally {
            httpPost.releaseConnection();
        }

        return null;
    }

    private JSONObject executePostHttpRequest(String jsonData, HttpPost httpPost) throws IOException, ParseException {
        httpPost.setEntity(new StringEntity(jsonData));
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        System.out.println("Post parameters : " + jsonData);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        System.out.println("Response json : " + result.toString());

        return (JSONObject) new JSONParser().parse(result.toString());
    }


}
