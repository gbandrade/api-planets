package br.com.planets.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;



public class HttpRequest {

	public HttpResponse get(String urlRequest) throws ClientProtocolException, IOException  {
		HttpGet httpGet = new HttpGet(urlRequest);
        HttpClient httpClient = HttpClientBuilder.create().build();
        httpGet.addHeader("accept", "application/json");
        HttpResponse response = httpClient.execute(httpGet);
        return response;
	}
	
	public JSONObject getResponseObject(HttpResponse response) throws IOException, JSONException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        return new JSONObject(stringBuilder.toString());
	}
	
}
