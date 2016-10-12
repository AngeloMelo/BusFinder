package br.com.asm.busfinder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

public class AppGluRequester {
	
	private static final String APPGLU_BASE_URL = "https://api.appglu.com/v1/queries/";
	private static final String APPGLU_USR = "WKD4N7YMA1uiM8V";
	private static final String APPGLU_PSW = "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68";
	
	public static JSONArray findRoutesByStopName(String key) throws IOException, JSONException {
		
		URL url = new URL(APPGLU_BASE_URL + "findRoutesByStopName/run");
		String params = "{\"params\": {\"stopName\": \"%" + key + "%\"}}";
		
		String strResult = postRequest(url, params);
			
		JSONObject jsonObject = new JSONObject(strResult); 
		return jsonObject.getJSONArray("rows");
	}
	
	
	public static JSONArray findStopsByRouteId(int routeId) throws JSONException, IOException{
		
		URL url = new URL(APPGLU_BASE_URL + "findStopsByRouteId/run");
		String params = "{\"params\": {\"routeId\":"+ routeId + "}}";
		
		String strResult = postRequest(url, params);
		
		JSONObject jsonObject = new JSONObject(strResult); 
		return jsonObject.getJSONArray("rows");
	}
	
	
	public static JSONArray findDeparturesByRouteId(int routeId) throws IOException, JSONException {
		
		URL url = new URL(APPGLU_BASE_URL + "/findDeparturesByRouteId/run");
		String params = "{\"params\": {\"routeId\":"+ routeId + "}}";
		
		String strResult = postRequest(url, params);
		
		JSONObject jsonObject = new JSONObject(strResult); 
		return jsonObject.getJSONArray("rows");
	}

	
	private static String postRequest(URL url, String params)throws IOException{
		
		String result = null;
		
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); 
		urlConnection.setRequestProperty("Accept", "application/json"); 
		urlConnection.setRequestProperty("X-AppGlu-Environment", "staging");
		urlConnection.setRequestProperty("Authorization", "Basic " + getAuthField()); 
		
		OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
	    writer.write(params);
	    writer.close();				
    
		int status = urlConnection.getResponseCode();
		
		if(status == 200)
		{
			InputStream inputStream = urlConnection.getInputStream();
			
			Scanner scanner = new Scanner(inputStream, "UTF-8");
			result = scanner.useDelimiter("\\A").next();
			
			scanner.close();
			
			urlConnection.disconnect();
		}			
		else if (status <= 499) 
		{
			//get the error message from connections error stream
            BufferedInputStream in = new BufferedInputStream(urlConnection.getErrorStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) 
            {
                sb.append(line);
            }
            
            throw new IOException(sb.toString());
        }			
		
		return result;
	}


	private static String getAuthField() {

		String usrPas = APPGLU_USR + ":" + APPGLU_PSW;
		return Base64.encodeToString(usrPas.getBytes(),Base64.DEFAULT);
	}
}